package Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.infi_project.data.ChatTabFragments.Received_REQ;
import com.example.infi_project.data.ChatTabFragments.Sent_REQ;

public class Connection_Adapter extends FragmentPagerAdapter {

    public Connection_Adapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                return new Received_REQ();
            case 1:
                return new Sent_REQ();

            default:
                return null;

        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch(position){
            case 0: return "Received";
            case 1: return "Sent";
            default: return null;

        }
    }
}
