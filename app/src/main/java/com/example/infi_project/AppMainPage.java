package com.example.infi_project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.infi_project.data.ChatTab;
import com.example.infi_project.data.ExploreTab;
import com.example.infi_project.data.FeedTab;
import com.example.infi_project.data.ProfileTab;
import com.example.infi_project.data.SharedViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
import java.util.Collections;
import java.util.Objects;


public class AppMainPage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    String TAG="AppMainPage";

    public SharedViewModel viewModel;


    public TabLayout tabLayout;
    public ViewPager viewPager;
    public Toolbar toolbar;
    PagerAdapter pagerAdapter;
    ProgressBar progressBar;
    private ArrayList<String> interestNames = new ArrayList<String>();

    DatabaseReference RootRef;
    DatabaseReference reff;
    ImageView menU;

    String mobileText;

    //Drawer
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    String check;

    boolean checkView=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_main_page);

        viewModel=new ViewModelProvider(AppMainPage.this).get(SharedViewModel.class);



        toolbar = findViewById(R.id.myToolBar);
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.pager);
        pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        progressBar = findViewById(R.id.progressBarApp);
        tabLayout.setupWithViewPager(viewPager);


//        Intent appMainPage_intent = getIntent();
//        mobileText= appMainPage_intent.getStringExtra("mobileText");
        mobileText=FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();
        checkView=true;



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




        RootRef= FirebaseDatabase.getInstance().getReference();


        reff = FirebaseDatabase.getInstance().getReference().child("userDetails").child(mobileText);

        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("choiceSelected").getValue() != null) {
                    String interestSelected = dataSnapshot.child("choiceSelected").getValue().toString();
                    check=interestSelected;
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
                        String interestNoText=dataSnapshot.child("totalNoOfInterest").getValue().toString();
                        int interestNo=Integer.parseInt(interestNoText);
                        for (int i=0; i<interestNo; i++){
                            String interestNumber= String.valueOf(i);
                            String interest= dataSnapshot.child("userInterest").child(interestNumber).getValue().toString();
                            interestNames.add(interest);
                        }

                        //viewModel.setInterestNames(interestNames);


                    }
                }

//                viewModel.getInterestNames().observe(AppMainPage.this, new Observer<ArrayList<String>>() {
//                    @Override
//                    public void onChanged(ArrayList<String> strings) {
//                        interestNames.addAll(strings);
//                    }
//                });
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

//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        viewModel= ViewModelProviders.of(getActivity()).get(SharedViewModel.class);
//        viewModel.getInterestNames().observe(getViewLifecycleOwner(), new Observer<ArrayList<String>>() {
//            @Override
//            public void onChanged(ArrayList<String> strings) {
//                Collections.copy(interestNames,strings);
//                //interestNames=strings;
//            }
//        });
//
//    }







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
            case R.id.updateStatus: {
                UpdateStatus();
            }

        }
        return super.onOptionsItemSelected(item);
    }

    private void UpdateStatus() {
        AlertDialog.Builder builder = new AlertDialog.Builder(AppMainPage.this);
        builder.setTitle("Update Your Status");
        final EditText statusText= new EditText(AppMainPage.this);
        statusText.setHint("Available");

        RootRef.child("Status").child(mobileText).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    String oldStatus=dataSnapshot.getValue().toString();
                    statusText.setText(oldStatus);
                    statusText.setHint("");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        builder.setView(statusText);

        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String statusFinal=statusText.getText().toString();

                int statusLength= statusFinal.length();

                if (statusLength>20){
                    Toast.makeText(AppMainPage.this, "Max Possible length is 20\n Update Failed" ,Toast.LENGTH_SHORT).show();
                }
                else {
                    uploadStatus(statusFinal);
                }

            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void uploadStatus(String statusFinal){
        RootRef.child("Status").child(mobileText).setValue(statusFinal).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                    Toast.makeText(AppMainPage.this, "Status Updated Successfully", Toast.LENGTH_SHORT).show();
                else {
                    Toast.makeText(AppMainPage.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                    Log.i(TAG, task.getException().toString());
                }
            }
        });
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

    public ArrayList<String> sendInterest(){
        return interestNames;
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

        if(checkView) {
            String phone2= FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();

            reff = FirebaseDatabase.getInstance().getReference().child("userDetails").child(phone2);
            reff.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

//                if (check.isEmpty() || check.equals("false")) {
                    String userImage = Objects.requireNonNull(dataSnapshot.child("image").getValue()).toString();
                    String userName = Objects.requireNonNull(dataSnapshot.child("userName").getValue()).toString();
                    //String userAbout = dataSnapshot.child("about").getV
                    // alue().toString();

                    na.setText(userName);
                    Picasso.get().load(userImage).placeholder(R.drawable.profile_image).into(pict);

                    //userProfileStatus.setText(userStatus);
//                }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }

            });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        checkView=false;
    }
}
