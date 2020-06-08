package com.example.infi_project.data;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.infi_project.AppMainPage;
import com.example.infi_project.ProfileListRecyclerViewAdapter;
import com.example.infi_project.R;
import com.example.infi_project.RecyclerViewAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;
import java.util.Vector;

import static androidx.recyclerview.widget.LinearLayoutManager.*;

public class ExploreTab extends Fragment implements RecyclerViewAdapter.AdapterCallback {

    public String mobileText;
    private ArrayList<String> interestNames= new ArrayList<>();
    private static final String TAG= "Explore:";
    public String interest_selected;

    private ProgressBar exploreProgressBar;

    private ArrayList<String> profileNameList= new ArrayList<>();
    private ArrayList<String> profileAboutList= new ArrayList<>();
    private ArrayList<String> profileImageList= new ArrayList<>();
    private ArrayList<String> profileNumberList= new ArrayList<>();

    private DatabaseReference reff;






    public ExploreTab() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppMainPage activity= (AppMainPage) getActivity();
        mobileText=activity.sendData();

        reff= FirebaseDatabase.getInstance().getReference().child("userDetails").child(mobileText);
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
//                    Iterable<DataSnapshot> snapshotIterator = dataSnapshot.getChildren();
//                    Iterator<DataSnapshot> iterator = snapshotIterator.iterator();
//                    int i = 0;
//                    while (iterator.hasNext()) {
//                        DataSnapshot next = (DataSnapshot) iterator.next();
//                        interestNames.add(Objects.requireNonNull(next.getValue()).toString());
//                        i = i + 1;
//                    }
                    String interestNoText=dataSnapshot.child("totalNoOfInterest").getValue().toString();
                    int interestNo=Integer.parseInt(interestNoText);
                    for (int i=0; i<interestNo; i++){
                        String interestNumber= String.valueOf(i);
                        String interest= dataSnapshot.child("userInterest").child(interestNumber).getValue().toString();
                        interestNames.add(interest);
                    }
                    interest_selected=interestNames.get(1);

                    initRecyclerView();
                    retrieveInterestedProfiles(interest_selected);
                }

                else {
                    Toast.makeText(getContext(), "DataSnapshot doesn't exist", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_explore_tab, container, false);
        exploreProgressBar=view.findViewById(R.id.exploreProgressBar);
        return  view;
    }

    @Override
    public void onViewCreated (View view, @Nullable Bundle savedInstanceState){


        initRecyclerView();
        initProfieRecyclerView();


    }




    @Override
    public void onMethodCallback(String interest) {
        interest_selected=interest;
        retrieveInterestedProfiles(interest_selected);

    }

    private void retrieveInterestedProfiles(String interest_selected){
        Log.d(TAG, "Getting profiles of a interest");
        exploreProgressBar.setVisibility(View.VISIBLE);
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference().child("interests").child(interest_selected);


        Query query = dbRef.orderByKey();

        ValueEventListener queryValueListener = new ValueEventListener() {

            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    profileImageList.clear();
                    profileNameList.clear();
                    profileAboutList.clear();
                    profileNumberList.clear();

                    if ((int)dataSnapshot.getChildrenCount()==1){
                        profileNameList.add("No User With Similar interest");
                        profileAboutList.add("");
                        profileImageList.add("");
                        initProfieRecyclerView();

                    }

                    Iterable<DataSnapshot> snapshotIterator = dataSnapshot.getChildren();
                    Iterator<DataSnapshot> iterator = snapshotIterator.iterator();

                    while (iterator.hasNext()) {
                        DataSnapshot next = (DataSnapshot) iterator.next();
                        Log.i(TAG, "Value = " + next.child("userPhone").getValue());
                        String value = Objects.requireNonNull(next.child("userPhone").getValue()).toString();
                        System.out.println("Value= "+value);
                        System.out.println("mobileText= "+mobileText);
                        if (!value.equals(mobileText)) {
                            profileNumberList.add(value);

                            reff = FirebaseDatabase.getInstance().getReference().child("userDetails").child(value);
                            reff.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot1) {
                                    if (dataSnapshot1.exists()) {
                                        String ImageUrl = Objects.requireNonNull(dataSnapshot1.child("image").getValue()).toString();
                                        String ProfileName = Objects.requireNonNull(dataSnapshot1.child("userName").getValue()).toString();

                                        profileImageList.add(ImageUrl);
                                        profileNameList.add(ProfileName);
                                        profileAboutList.add("To be uploaded");
                                    } else {
                                        Toast.makeText(getContext(), "User Details doesn't exist", Toast.LENGTH_SHORT).show();
                                    }
                                    initProfieRecyclerView();
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }
                        else continue;

                    }
                }

                else {
                    Toast.makeText(getContext(), "Selected interest has no database", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        query.addListenerForSingleValueEvent(queryValueListener);

    }


    private void initRecyclerView(){
        Log.d(TAG, "initRecyclerView");
        RecyclerView recyclerView = getView().findViewById(R.id.iconRecycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(getContext(), interestNames,this);
        recyclerView.setAdapter(adapter);


    }

    private void initProfieRecyclerView(){
        Log.d(TAG, "initProfileRecyclerView");
        RecyclerView profileRecyclerView= getView().findViewById(R.id.profileListRecycler);
        ProfileListRecyclerViewAdapter profileAdapter= new ProfileListRecyclerViewAdapter(getContext(),profileNameList,profileAboutList,profileImageList);
        profileRecyclerView.setAdapter(profileAdapter);
        profileRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        exploreProgressBar.setVisibility(View.GONE);

    }

    @Override
    public void onPause() {

        super.onPause();
        initProfieRecyclerView();

    }



}
