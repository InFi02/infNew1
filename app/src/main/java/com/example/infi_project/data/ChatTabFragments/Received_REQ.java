package com.example.infi_project.data.ChatTabFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
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
import com.example.infi_project.Users;
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

import Adapter.Received_Req_Adapter;

public class Received_REQ extends Fragment {

    private static final String TAG = "RECEIVED_REQ";
    private Received_Req_Adapter adapter;
    private List<Users> musers;
    String mobileText;
    String mynumber;


    private ArrayList<String> profileNameList= new ArrayList<>();
    private ArrayList<String> profileImageList= new ArrayList<>();
    private ArrayList<String> profileNumberList= new ArrayList<>();


    public Received_REQ() {
        // Required empty public constructor
    }

    private DatabaseReference reff;



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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_received__r_e_q, container, false);
    }

    private void readUsers() {

        // FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Connections").child(mobileText).child("received");

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


                        String value = Objects.requireNonNull(next.getKey());
                        // DataSnapshot next = (DataSnapshot) iterator.next();
                        if (!value.equals("dummy")) {
                            profileNumberList.add(value);
                            reff = FirebaseDatabase.getInstance().getReference().child("userDetails").child(value);
                            reff.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot1) {
                                    if (dataSnapshot1.exists()) {
                                        String ProfileName = Objects.requireNonNull(dataSnapshot1.child("userName").getValue()).toString();
                                        String ImageUrl = Objects.requireNonNull(dataSnapshot1.child("image").getValue()).toString();
                                        //Toast.makeText(getContext(),ImageUrl,Toast.LENGTH_SHORT).show();


                                        //  Toast.makeText(getContext(),ImageUrl,Toast.LENGTH_SHORT).show();
                                        profileImageList.add(ImageUrl);
                                        profileNameList.add(ProfileName);

                                    } else {
                                        Toast.makeText(getContext(), "User Details doesn't exist", Toast.LENGTH_SHORT).show();
                                    }
                                    initReceivedRecyclerView();
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }

                            });

                        }
                    }
                }


                }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        };
        query.addValueEventListener(queryValueListener);
    }

    private void initReceivedRecyclerView() {

        RecyclerView RecyclerView= getView().findViewById(R.id.ConnectionListRecycler);
        Received_Req_Adapter userAdapter= new Received_Req_Adapter(mobileText,getContext(),profileNameList,profileNumberList,profileImageList);
        RecyclerView.setAdapter(userAdapter);
        RecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


    }

}
