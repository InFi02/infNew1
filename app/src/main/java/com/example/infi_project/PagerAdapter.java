package com.example.infi_project;

import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.infi_project.data.ChatTab;
import com.example.infi_project.data.ExploreTab;
import com.example.infi_project.data.FeedTab;
import com.example.infi_project.data.ProfileTab;
import com.example.infi_project.data.model.fragmentusers;

//public class PagerAdapter extends FragmentStatePagerAdapter {
//
//
//    int mNoOfTabs;
//
//    public PagerAdapter( FragmentManager fm, int NumberOfTabs){
//        super(fm);
//        this.mNoOfTabs=NumberOfTabs;
//    }
//    @Override
//    public Fragment getItem(int position) {
//        switch (position){
//            case 0:
//                FeedTab tab1= new FeedTab();
//                return tab1;
//            case 1:
//                ChatTab tab2= new ChatTab();
//                return tab2;
//            case 2:
//                ExploreTab tab3= new ExploreTab();
//                return tab3;
//            case 3:
//                ProfileTab tab4= new ProfileTab();
//                return tab4;
//            default:
//                return null;
//
//        }
//    }
//
//    @Override
//    public int getCount() {
//        return 0;
//    }
//}


public class PagerAdapter extends FragmentPagerAdapter {

    public PagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                return new FeedTab();
            case 1:
                return new ChatTab();
            case 2:
                return new ExploreTab();
            case 3:
                return new ProfileTab();
            default:
                return null;

        }
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch(position){
            case 0: return "Feed";
            case 1: return "Chat";
            case 2: return "Explore";
            case 3: return  "Profile";
            default: return null;

        }
    }
}