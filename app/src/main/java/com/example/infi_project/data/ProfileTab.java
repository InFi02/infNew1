package com.example.infi_project.data;

import android.os.Bundle;

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
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.infi_project.R;
import com.example.infi_project.data.model.ProfileImagePicker;
import com.google.android.material.tabs.TabLayout;


public class ProfileTab extends Fragment {








    public TabLayout tabLayout;
    public ViewPager viewPager;
    public Toolbar toolbar;
    PagerAdapterProfile pagerAdapterprofile;
    public ImageView prof;
    public ConstraintLayout cs;
    public FrameLayout fl;
    public static FragmentManager fragmentManager;

    public ProfileTab() {
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
        return inflater.inflate(R.layout.fragment_profile_tab, container, false);
    }

    public void onViewCreated(View v, @Nullable Bundle savedInstanceState) {

        tabLayout=getView().findViewById(R.id.tabLayout1);
        viewPager=getView().findViewById(R.id.pager1);
        pagerAdapterprofile=new PagerAdapterProfile(getFragmentManager());
        viewPager.setAdapter(pagerAdapterprofile);
        prof=getView().findViewById(R.id.imageView10);

        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        fragmentManager=getFragmentManager();


        prof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /*cs.findViewById(R.id.linearContent);
                cs.setVisibility(View.INVISIBLE);
                fl.findViewById(R.id.fragment_container);
                fl.setVisibility(View.VISIBLE);

                */









                FragmentTransaction transaction = fragmentManager.beginTransaction();
                Fragment newFragment = new ProfileImagePicker();
                transaction.add(R.id.fragment_container,newFragment,null);


                    // Replace whatever is in the fragment_container view with this fragment,
                    // and add the transaction to the back stack if needed

                transaction.addToBackStack(null);


                    // Commit the transaction
                transaction.commit();





            }
        });



    }



}
