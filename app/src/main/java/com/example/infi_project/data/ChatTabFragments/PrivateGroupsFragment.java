package com.example.infi_project.data.ChatTabFragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.infi_project.R;
import com.example.infi_project.data.model.ProfileImagePicker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class PrivateGroupsFragment extends Fragment {


    public PrivateGroupsFragment() {
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
        return inflater.inflate(R.layout.fragment_private_groups, container, false);
    }

    @Override
    public void onViewCreated (View view, @Nullable Bundle savedInstanceState){

        final FloatingActionButton fab = view.findViewById(R.id.fabGroups);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//                GroupCreateFragment myGroupFragment = new GroupCreateFragment();
                GroupCreateFragment myDialogFragment = new GroupCreateFragment();

                myDialogFragment.show(getChildFragmentManager(), "MyFragment");
//                FragmentTransaction fragmentTransaction=getChildFragmentManager().beginTransaction();
//                fragmentTransaction.add(R.id.group_fragment_container, new GroupCreateFragment());
//                fragmentTransaction.commit();
//                fab.setVisibility(View.GONE);



            }
        });


    }
}
