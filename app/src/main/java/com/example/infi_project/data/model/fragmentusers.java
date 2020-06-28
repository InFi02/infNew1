package com.example.infi_project.data.model;

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
import android.widget.Toast;

import com.example.infi_project.AppMainPage;
import com.example.infi_project.R;
import com.example.infi_project.RecyclerViewAdapter;
import com.example.infi_project.Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import Adapter.UserAdapter;

import static androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL;


public class fragmentusers extends Fragment  {

    String TAG="fragmentusers";

    private RecyclerView recyclerView;

    private UserAdapter userAdapter;
    private List<Users> musers;
    String mobileText;
    String mynumber;

    public boolean checkView=false;


    private ArrayList<String> profileNameList= new ArrayList<>();
    private ArrayList<String> profileImageList= new ArrayList<>();
    private ArrayList<String> profileNumberList= new ArrayList<>();

    private DatabaseReference reff;


    public fragmentusers() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        AppMainPage activity= (AppMainPage) getActivity();
        mobileText=activity.sendData();
        mynumber=mobileText.toString();
        readUsers();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        checkView=true;

        View view=inflater.inflate(R.layout.fragment_fragmentusers, container, false);
  // recyclerView=view.findViewById(R.id.ChatListRecycler);
   // recyclerView.setHasFixedSize(true);
    //recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));



    return view;
    }


    @Override
    public void onViewCreated (View view, @Nullable Bundle savedInstanceState){



        if (checkView) initChatRecyclerView();


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        checkView=false;
    }

    private void readUsers() {

        if (checkView) {

            // FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("userDetails");

            Query query = reference.orderByKey();

            ValueEventListener queryValueListener = new ValueEventListener() {


                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {

                        profileImageList.clear();
                        profileNameList.clear();
                        profileNumberList.clear();
                        Iterable<DataSnapshot> snapshotIterator = dataSnapshot.getChildren();
                        Iterator<DataSnapshot> iterator = snapshotIterator.iterator();

                        while (iterator.hasNext()) {
                            DataSnapshot next = (DataSnapshot) iterator.next();


                            Log.i(TAG, "Value = " + next.child("userPhone").getValue());
                            String value = Objects.requireNonNull(next.child("userPhone").getValue()).toString();
                            System.out.println("Value= " + value);
                            System.out.println("mobileText= " + mynumber);
                            if (!value.equals(mynumber)) {
                                profileNumberList.add(value);
                                reff = FirebaseDatabase.getInstance().getReference().child("userDetails").child(value);
                                reff.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot1) {
                                        if (dataSnapshot1.exists()) {
                                            String ProfileName = Objects.requireNonNull(dataSnapshot1.child("userName").getValue()).toString();
                                            String ImageUrl = Objects.requireNonNull(dataSnapshot1.child("image").getValue()).toString();
                                            //Toast.makeText(getContext(),ImageUrl,Toast.LENGTH_SHORT).show();


                                            profileImageList.add(ImageUrl);
                                            profileNameList.add(ProfileName);

                                        } else {
                                            Toast.makeText(getContext(), "User Details doesn't exist", Toast.LENGTH_SHORT).show();
                                        }
                                        initChatRecyclerView();
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }

                                });

                            } else continue;
                        }






                        /*for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Users user = snapshot.getValue(Users.class);

                            if (!user.getUserPhone().equals(mobileText))
                                musers.add(user);
                        }

                        userAdapter = new UserAdapter(getContext(), musers);
                        recyclerView.setAdapter(userAdapter);*/
                    }

                }


                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }


            };

            query.addValueEventListener(queryValueListener);
        }

    }

    private void initChatRecyclerView() {
        if (checkView) {

            RecyclerView chatRecyclerView = getView().findViewById(R.id.ChatListRecycler);
            UserAdapter userAdapter = new UserAdapter(getContext(), profileNameList, profileImageList, profileNumberList);
            chatRecyclerView.setAdapter(userAdapter);
            chatRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        }


    }





    @Override
    public void onPause() {

        super.onPause();
        initChatRecyclerView();

    }






}
