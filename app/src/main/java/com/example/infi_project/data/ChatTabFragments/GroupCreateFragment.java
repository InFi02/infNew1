package com.example.infi_project.data.ChatTabFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.infi_project.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class GroupCreateFragment extends Fragment {

    public GroupCreateFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_group_create, container, false);
    }
}
