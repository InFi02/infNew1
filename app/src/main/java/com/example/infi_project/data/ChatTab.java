package com.example.infi_project.data;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.infi_project.PagerAdapter;
import com.example.infi_project.R;
import com.google.android.material.tabs.TabLayout;

import Adapter.ChatTabPagerAdapter;


public class ChatTab extends Fragment {

    private ChatTabPagerAdapter chatTabPagerAdapter;

    public ChatTab() {
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
        return inflater.inflate(R.layout.fragment_chat_tab, container, false);
    }

    @Override
    public void onViewCreated (View view, @Nullable Bundle savedInstanceState){
        chatTabPagerAdapter=new ChatTabPagerAdapter(getChildFragmentManager());
        TabLayout chatTabTabLayout = getView().findViewById(R.id.tabLayoutChatTab);
        ViewPager chatTabPager = getView().findViewById(R.id.pagerChatTab);
        ProgressBar chatTabProgressBar = getView().findViewById(R.id.progressBarChatTab);
        chatTabPager.setAdapter(chatTabPagerAdapter);
        chatTabTabLayout.setupWithViewPager(chatTabPager);








    }


}
