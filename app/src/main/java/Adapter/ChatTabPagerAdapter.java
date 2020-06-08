package Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.infi_project.data.ChatTabFragments.NewConnectionsFragment;
import com.example.infi_project.data.ChatTabFragments.PrivateChannelsFragment;
import com.example.infi_project.data.ChatTabFragments.PrivateChatFragment;
import com.example.infi_project.data.ChatTabFragments.PrivateGroupsFragment;
import com.example.infi_project.data.ExploreTab;
import com.example.infi_project.data.FeedTab;
import com.example.infi_project.data.ProfileTab;
import com.example.infi_project.data.model.fragmentusers;

public class ChatTabPagerAdapter extends FragmentPagerAdapter {

    public ChatTabPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                return new PrivateChatFragment();
            case 1:
                return new PrivateGroupsFragment();
            case 2:
                return new PrivateChannelsFragment();
            case 3:
                return new NewConnectionsFragment();
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
            case 0: return "Chat";
            case 1: return "Groups";
            case 2: return "Channels";
            case 3: return  "Requests";
            default: return null;

        }
    }
}
