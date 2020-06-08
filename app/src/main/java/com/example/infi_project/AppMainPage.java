package com.example.infi_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.infi_project.data.ChatTab;
import com.example.infi_project.data.ExploreTab;
import com.example.infi_project.data.FeedTab;
import com.example.infi_project.data.ProfileTab;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class AppMainPage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    public TabLayout tabLayout;
    public ViewPager viewPager;
    public Toolbar toolbar;
    PagerAdapter pagerAdapter;
    ProgressBar progressBar;
    private ArrayList<String> interestNames = new ArrayList<>();
    DatabaseReference reff;
    ImageView menU;

    String mobileText;

    //Drawer
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_main_page);

        toolbar = findViewById(R.id.myToolBar);
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.pager);
        pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        progressBar = findViewById(R.id.progressBarApp);

        Intent appMainPage_intent = getIntent();
        mobileText = appMainPage_intent.getStringExtra("mobileText");



        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_menu);

        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        menU = findViewById(R.id.menuic);

        usinfo();

        menU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });


        tabLayout.setupWithViewPager(viewPager);




        reff = FirebaseDatabase.getInstance().getReference().child("userDetails").child(mobileText);
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("choiceSelected").getValue() != null) {
                    String interestSelected = dataSnapshot.child("choiceSelected").getValue().toString();
                    if (interestSelected != "true") {
                        Intent interest_intent = new Intent(AppMainPage.this, Interest_Part.class);
                        interest_intent.putExtra("mobileText", mobileText);
                        startActivity(interest_intent);
                        finish();
                    } else {
//                    Intent interest_intent= new Intent(AppMainPage.this, Interest_Part.class);
//                    Toast.makeText(AppMainPage.this, interestSelected+"aaaaaa", Toast.LENGTH_LONG).show();
//                    interest_intent.putExtra("mobileText", mobileText);
//                    startActivity(interest_intent);
//                    finish();
                        progressBar.setVisibility(View.GONE);
                        viewPager.setVisibility(View.VISIBLE);


                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {


            }

        });


//        tabLayout=(TabLayout)findViewById(R.id.tabLayout);
//        tabLayout.addTab(tabLayout.newTab().setText("Feed"));
//        tabLayout.addTab(tabLayout.newTab().setText("Chat"));
//        tabLayout.addTab(tabLayout.newTab().setText("Explore"));
//        tabLayout.addTab(tabLayout.newTab().setText("Profile"));
//        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
//
//        final ViewPager viewPager=(ViewPager)findViewById(R.id.pager);
//        final PagerAdapter adapter= new PagerAdapter (getSupportFragmentManager(), tabLayout.getTabCount());
//        viewPager.setAdapter(adapter);
//        viewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
//
//        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                viewPager.setCurrentItem(tab.getPosition());
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });

    }







    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){

            case R.id.logoutMenu: {
                Logout();
                break;
            }
            case R.id.ContactUs: {
                startActivity(new Intent(AppMainPage.this, Contact_Us.class));
                break;

            }

        }
        return super.onOptionsItemSelected(item);
    }

    private void Logout(){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(AppMainPage.this, MainActivity.class));
        finish();
        finishAffinity();
    }

    public String sendData(){
        return mobileText;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return true;
    }

    @Override
    public void onBackPressed() {

        if(drawerLayout.isDrawerVisible(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START);
        else
            super.onBackPressed();
    }

    private void usinfo() {
        View header=navigationView.getHeaderView(0);
        final ImageView pict = (ImageView) header.findViewById(R.id.p_pic);
        final TextView na = (TextView)header.findViewById(R.id.p_name);
        TextView abo =(TextView)header.findViewById(R.id.p_about);



        reff = FirebaseDatabase.getInstance().getReference().child("userDetails").child(mobileText);
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                String userImage = dataSnapshot.child("image").getValue().toString();
                String userName = dataSnapshot.child("userName").getValue().toString();
                //String userAbout = dataSnapshot.child("about").getV
                // alue().toString();

                na.setText(userName);
                Picasso.get().load(userImage).placeholder(R.drawable.profile_image).into(pict);

                //userProfileStatus.setText(userStatus);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
    }


}
