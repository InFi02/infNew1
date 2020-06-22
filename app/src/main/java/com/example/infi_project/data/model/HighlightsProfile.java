package com.example.infi_project.data.model;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.infi_project.R;
import com.example.infi_project.data.ProfileTab;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


import Adapter.Profile_Highlight_Adapter;
import Models.Post;


public class HighlightsProfile extends Fragment {

    RecyclerView recyclerView;
    Profile_Highlight_Adapter adapter;
   List<Post> postList;

    FirebaseUser fuser;
    String mobileText;
    String profileid;
   

    public HighlightsProfile() {
       
    }

    
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       
        return inflater.inflate(R.layout.fragment_highlights_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ProfileTab frag=(ProfileTab) getParentFragment();
        mobileText=frag.sendnumber();

        //fuser= FirebaseAuth.getInstance().getCurrentUser();
        //mobileText=fuser.getPhoneNumber();
        profileid=mobileText.toString();

        initRecyclerView();


    }

    private void initRecyclerView() {

        recyclerView=(RecyclerView)getView().findViewById(R.id.recycler_view_highlight);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager=new GridLayoutManager(getContext(),3
        );
        recyclerView.setLayoutManager(linearLayoutManager);
        postList= new ArrayList<>();
        adapter=new Profile_Highlight_Adapter(getContext(),postList);
        recyclerView.setAdapter(adapter);
        MyPhotos();

    }

    private void MyPhotos(){

        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Posts");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               postList.clear();
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                   Post post= snapshot.getValue(Post.class);
                   // Toast.makeText(getContext(),post.getPublisher(),Toast.LENGTH_SHORT).show();
                   System.out.println(post.getPublisher());
                    if(post.getPublisher().equals(profileid)){
                        postList.add(post);

                    }
                }

                Collections.reverse(postList);
                adapter.notifyDataSetChanged();




            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
