package com.example.infi_project.data;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.infi_project.AppMainPage;
import com.example.infi_project.ProfileForUsers;
import com.example.infi_project.R;
import com.example.infi_project.data.model.ProfileImagePicker;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


public class

ProfileTab extends Fragment {


    public Button Message;
    public Button Connect;

    public TabLayout tabLayout;
    public ViewPager viewPager;
    public Toolbar toolbar;
    PagerAdapterProfile pagerAdapterprofile;

    public ConstraintLayout cs;
    public FrameLayout fl;
    public static FragmentManager fragmentManager;
    public Button viewp;
    String mobileText;
    public ImageView profile_pic;
    public TextView namee;
    DatabaseReference reff;
    String userImage;
    String value;
    String number;

    public ProfileTab() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppMainPage activity = (AppMainPage) getActivity();
        mobileText = activity.sendData();


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        value = getArguments() != null ? getArguments().getString("profileno") : mobileText;

        return inflater.inflate(R.layout.fragment_profile_tab, container, false);
    }

    public void onViewCreated(View v, @Nullable Bundle savedInstanceState) {

        //  Toast.makeText(getContext(),value,Toast.LENGTH_SHORT).show();
        //Toast.makeText(getContext(),value,Toast.LENGTH_SHORT).show();

        tabLayout = getView().findViewById(R.id.tabLayout1);
        viewPager = getView().findViewById(R.id.pager1);
        pagerAdapterprofile = new PagerAdapterProfile(getChildFragmentManager());
        viewPager.setAdapter(pagerAdapterprofile);


        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        fragmentManager = getChildFragmentManager();
        // viewp = getView().findViewById(R.id.check);
        profile_pic = getView().findViewById(R.id.imageView10);
        namee = getView().findViewById(R.id.name);
        Message = getView().findViewById(R.id.check);
        Connect = getView().findViewById(R.id.connects);


        RetrieveUserInfo(value);



     /*   viewp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.add(R.id.fragment_container,new ProfileForUsers());
                transaction.commit();







            }
        });

      */


        profile_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                ProfileImagePicker myDialogFragment = new ProfileImagePicker();
                myDialogFragment.show(getChildFragmentManager(), "MyFragment");

                RetrieveUserInfo(mobileText);
                System.out.println("Hello World");
            }

        });


        Message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        Connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }














               /*FragmentTransaction transaction = fragmentManager.beginTransaction();
                Fragment newFragment = new ProfileImagePicker();
                transaction.add(R.id.fragment_container,newFragment,null);


                    // Replace whatever is in the fragment_container view with this fragment,
                    // and add the transaction to the back stack if needed

                transaction.addToBackStack(null);


                    // Commit the transaction
                transaction.commit();*/


    private void RetrieveUserInfo(String mobileText) {
        Toast.makeText(getContext(), mobileText, Toast.LENGTH_SHORT).show();
        System.out.println(mobileText);

        reff = FirebaseDatabase.getInstance().getReference().child("userDetails").child(mobileText);
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                userImage = dataSnapshot.child("image").getValue().toString();
                String userName = dataSnapshot.child("userName").getValue().toString();
                //String userAbout = dataSnapshot.child("about").getV
                // value().toString();

                Picasso.get().load(userImage).placeholder(R.drawable.profile_image).into(profile_pic);
                namee.setText(userName);
                //userProfileStatus.setText(userStatus);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
    }

    public ImageView sData() {
        return profile_pic;
    }


    public String sendnumber() {
        return value;
    }
}
















