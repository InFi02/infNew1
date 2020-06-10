package com.example.infi_project.data.ChatTabFragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.infi_project.R;
import com.example.infi_project.data.SharedViewModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * A simple {@link Fragment} subclass.
 */
public class GroupCreateFragment extends DialogFragment {

    String TAG="GroupCreateFragment";

    private EditText groupName;
    private RadioGroup interestRadioGroup;
    Button createButton;
    private int interestLength;

    private SharedViewModel viewModel;
    private ArrayList<String> interestNames= new ArrayList<>();


    public GroupCreateFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_group_create, container, false);
        interestRadioGroup=view.findViewById(R.id.radioGroupInterest);
        addRadioButtons();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        viewModel= ViewModelProviders.of(getActivity()).get(SharedViewModel.class);
        viewModel.getInterestNames().observe(getViewLifecycleOwner(), new Observer<ArrayList<String>>() {
            @Override
            public void onChanged(ArrayList<String> strings) {
                //System.out.println(strings.get(1));
                //Collections.copy(interestNames,strings);
                for (int i=0; i<strings.size();i++){
                    interestNames.add(strings.get(i));
                }
                Log.i(TAG, " "+interestNames.size());



            }
        });
        //System.out.println(interestNames.get(0));

        groupName=getView().findViewById(R.id.groupName);
        interestRadioGroup=getView().findViewById(R.id.radioGroupInterest);

        interestLength=interestNames.size();
        //addRadioButtons();


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel= ViewModelProviders.of(getActivity()).get(SharedViewModel.class);
        viewModel.getInterestNames().observe(getViewLifecycleOwner(), new Observer<ArrayList<String>>() {
            @Override
            public void onChanged(ArrayList<String> strings) {
                //System.out.println(strings.get(1));
//                Collections.copy(interestNames,strings);
                for (int i=0; i<strings.size();i++){
                    interestNames.add(strings.get(i));
                }
                Log.i(TAG, " "+interestNames.size());
                //interestNames=strings;
            }
        });

    }

    public void addRadioButtons(){
        interestRadioGroup.setOrientation(LinearLayout.VERTICAL);
        for (int i=0; i<interestNames.size();i++){
            RadioButton newButton= new RadioButton(getContext());
            newButton.setId(View.generateViewId());
            newButton.setText(interestNames.get(i));
            newButton.setTextColor(Color.parseColor("#FFFFFF"));
            interestRadioGroup.addView(newButton);
        }
    }
}
