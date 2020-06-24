package com.example.infi_project.data.ChatTabFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.infi_project.R;
import com.google.android.material.tabs.TabLayout;

import Adapter.Connection_Adapter;


public class NewConnectionsFragment extends Fragment {

    public TabLayout tabLayout;
    public ViewPager viewPager;
    public Toolbar toolbar;

    Connection_Adapter adapter;


    public NewConnectionsFragment() {
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
        return inflater.inflate(R.layout.fragment_new_connections, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tabLayout = getView().findViewById(R.id.tabLayoutChatTab);
        viewPager = getView().findViewById(R.id.pagerChatTab);
        adapter = new Connection_Adapter(getChildFragmentManager());
        viewPager.setAdapter(adapter);




        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
    }


}
