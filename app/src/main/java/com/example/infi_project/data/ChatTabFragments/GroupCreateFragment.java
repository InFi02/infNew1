package com.example.infi_project.data.ChatTabFragments;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
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
import android.widget.Toast;

import com.example.infi_project.AppMainPage;
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
    private ArrayList<String> interestNames1= new ArrayList<String>();


    public GroupCreateFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel= new ViewModelProvider(requireActivity()).get(SharedViewModel.class);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_group_create, container, false);
        interestRadioGroup=view.findViewById(R.id.radioGroupInterest);
        //groupName.setText(interestNames.get(1));

        //viewModel= new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
//        viewModel.getInterestNames().observe(getViewLifecycleOwner(), new Observer<ArrayList<String>>() {
//            @Override
//            public void onChanged(ArrayList<String> strings) {
//
//                interestNames1.clear();
//                interestNames1.addAll(strings);
//            }
//
//        });
        AppMainPage activity= (AppMainPage) getActivity(); // should be replaced with better methods
        interestNames1.clear();
        assert activity != null;
        interestNames1.addAll(activity.sendInterest());


        interestLength=interestNames1.size();
        // addRadioButtons();
        //addRadioButtons();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        viewModel= ViewModelProviders.of(getActivity()).get(SharedViewModel.class);
////        viewModel.getInterestNames().observe(getViewLifecycleOwner(), new Observer<ArrayList<String>>() {
////            @Override
////            public void onChanged(ArrayList<String> strings) {
////                //System.out.println(strings.get(1));
////                //Collections.copy(interestNames,strings);
////                interestNames1.clear();
////                for (int i=0; i<strings.size();i++){
////                    interestNames1.add(strings.get(i));
////                }
////                Log.i(TAG, " "+interestNames1.size()+interestNames1.get(0));
////
////
////
////            }
////        });
        //System.out.println(interestNames.get(0));

        groupName=getView().findViewById(R.id.groupName);
//        interestRadioGroup=getView().findViewById(R.id.radioGroupInterest);
//        //groupName.setText(interestNames.get(1));
//
//        viewModel= new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
//        viewModel.getInterestNames().observe(getViewLifecycleOwner(), new Observer<ArrayList<String>>() {
//            @Override
//            public void onChanged(ArrayList<String> strings) {
//
//                interestNames1.clear();
//                interestNames1.addAll(strings);
//            }
//
//        });

        interestLength=interestNames1.size();
        addRadioButtons();


    }

//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        viewModel= ViewModelProviders.of(getActivity()).get(SharedViewModel.class);
//        viewModel.getInterestNames().observe(getViewLifecycleOwner(), new Observer<ArrayList<String>>() {
//            @Override
//            public void onChanged(ArrayList<String> strings) {
//                //System.out.println(strings.get(1));
////                Collections.copy(interestNames,strings);
//                interestNames1.clear();
//                interestNames1.addAll(strings);
//                Log.i(TAG, " "+interestNames1.size());
//                Toast.makeText(getContext(), interestNames1.size()+" ",Toast.LENGTH_SHORT).show();
//
//                //interestNames=strings;
//            }
//        });
//
//        addRadioButtons();
//
//    }

    private void addRadioButtons(){
        Toast.makeText(getContext(), interestNames1.size()+" ",Toast.LENGTH_SHORT).show();
        interestRadioGroup.setOrientation(LinearLayout.VERTICAL);
        for (int i=0; i<interestNames1.size();i++){
            RadioButton newButton= new RadioButton(getContext());
            newButton.setId(View.generateViewId());
            newButton.setText(interestNames1.get(i));
            newButton.setTextColor(Color.parseColor("#FFFFFF"));
            newButton.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
            interestRadioGroup.addView(newButton);
        }
    }
}
