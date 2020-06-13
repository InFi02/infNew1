package com.example.infi_project;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.example.infi_project.data.PagerAdapterProfile;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ProfileForUsers extends Fragment {


    public Button Message;
    public Button Connect;


    public TabLayout tabLayout;
    public ViewPager viewPager;
    public Toolbar toolbar;
    PagerAdapterProfile pagerAdapterprofile;

    public ConstraintLayout cs;
    public FrameLayout fl;
    public static FragmentManager fragmentManager;

    String mobileText;
    public ImageView profile_pic;
    public TextView namee;
    DatabaseReference reff;
    String userImage;


    public ProfileForUsers() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        AppMainPage activity= (AppMainPage) getActivity();
        mobileText=activity.sendData();
        RetrieveUserInfo();
        return inflater.inflate(R.layout.fragment_profile_for_users, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        tabLayout=getView().findViewById(R.id.tabLayout1);
        viewPager=getView().findViewById(R.id.pager1);
        pagerAdapterprofile=new PagerAdapterProfile(getChildFragmentManager());
        viewPager.setAdapter(pagerAdapterprofile);


        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        fragmentManager=getChildFragmentManager();

        profile_pic = getView().findViewById(R.id.imageView10);
        namee = getView().findViewById(R.id.name);

        Message = getView().findViewById(R.id.messages);
        Connect = getView().findViewById(R.id.connects);


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


    private void RetrieveUserInfo() {

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
}




