package com.example.infi_project.data.model;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.infi_project.R;
import com.example.infi_project.data.ProfileTab;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import Adapter.Vertical_Recycler_View_Adapter;
import Models.HorizontalModel;
import Models.SpecificPost;
import Models.VerticalModel;

public class ProfileAll extends Fragment {

    private static final String TAG = "Profile_All_Fragment";
    RecyclerView verticalRecyclerView;
    Vertical_Recycler_View_Adapter adapter;

  private  ArrayList<VerticalModel> arrayListVertical;

    private ArrayList<String> mTitles = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();


    public ProfileAll() {

    }
    DatabaseReference reference;
    String mobileText;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        return inflater.inflate(R.layout.fragment_profile_all, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
      //getImages();
        ProfileTab frag=(ProfileTab) getParentFragment();
        assert frag != null;
        mobileText=frag.sendnumber();


        initVerticleRecyclerView();
    }

    private void initVerticleRecyclerView() {

        arrayListVertical=new ArrayList<>();

        verticalRecyclerView=(RecyclerView)getView().findViewById(R.id.recyclerview);
        verticalRecyclerView.setHasFixedSize(true);
        verticalRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));

        adapter=new Vertical_Recycler_View_Adapter(getContext(),arrayListVertical);
        verticalRecyclerView.setAdapter(adapter);

        setData();


    }

    private void setData() {
        reference=FirebaseDatabase.getInstance().getReference().child("specificPost").child(mobileText);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    arrayListVertical.clear();
                    for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                        VerticalModel verticalModel=new VerticalModel();
                        String title=snapshot.getKey();
                        System.out.println(title);
                        verticalModel.setTitle(title);
                        assert title != null;
                        DatabaseReference postInterest= reference.child(title).getRef();

                        ArrayList<HorizontalModel> arrayListHorizontal=new ArrayList<>();

                        postInterest.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot1) {
                                if (dataSnapshot1.exists()){
                                    arrayListHorizontal.clear();
                                    for (DataSnapshot snapshot1 : dataSnapshot1.getChildren()){
                                        HorizontalModel horizontalModel=new HorizontalModel();
                                        System.out.println("test test");
                                        SpecificPost post = snapshot1.getValue(SpecificPost.class);
//                                        System.out.println(dataSnapshot1.child("description").getValue().toString());
                                        assert post != null;
                                        horizontalModel.setTitle(post.getDescription());
                                        horizontalModel.setPost(post.getPostImage());
                                        System.out.println(post.getDescription());
                                        System.out.println(post.getPostImage());
//                                        System.out.println(dataSnapshot1.child("postImage").getValue().toString());

                                        arrayListHorizontal.add(horizontalModel);
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                        verticalModel.setArrayList(arrayListHorizontal);
                        arrayListVertical.add(verticalModel);


                    }
                    adapter.notifyDataSetChanged();

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


//        for(int i=1;i<=5;i++){
//
//            VerticalModel verticalModel=new VerticalModel();
//            verticalModel.setTitle("Title"+i);
//
//            ArrayList<HorizontalModel> arrayListHorizontal=new ArrayList<>();
//            for(int j=0;j<5;j++){
//
//                HorizontalModel horizontalModel=new HorizontalModel();
//                horizontalModel.setTitle("Description"+j);
//
//                arrayListHorizontal.add(horizontalModel);
//
//
//            }
//
//            verticalModel.setArrayList(arrayListHorizontal);
//            arrayListVertical.add(verticalModel);
//
//        }
//        adapter.notifyDataSetChanged();
    }

 /* private void getImages() {
        Log.d(TAG, "initImageBitmaps: preparing bitmaps.");

        mImageUrls.add("https://c1.staticflickr.com/5/4636/25316407448_de5fbf183d_o.jpg");
        mTitles.add("Havasu Falls");

        mImageUrls.add("https://i.redd.it/tpsnoz5bzo501.jpg");
        mTitles.add("Trondheim");

        mImageUrls.add("https://i.redd.it/qn7f9oqu7o501.jpg");
        mTitles.add("Portugal");

        mImageUrls.add("https://i.redd.it/j6myfqglup501.jpg");
        mTitles.add("Rocky Mountain National Park");


        mImageUrls.add("https://i.redd.it/0h2gm1ix6p501.jpg");
        mTitles.add("Mahahual");

        mImageUrls.add("https://i.redd.it/k98uzl68eh501.jpg");
        mTitles.add("Frozen Lake");


        mImageUrls.add("https://i.redd.it/glin0nwndo501.jpg");
        mTitles.add("White Sands Desert");

        mImageUrls.add("https://i.redd.it/obx4zydshg601.jpg");
        mTitles.add("Austrailia");

        mImageUrls.add("https://i.imgur.com/ZcLLrkY.jpg");
        mTitles.add("Washington");

        initRecyclerView();

    }*/

  /*private void initRecyclerView() {
        Log.d(TAG, "initRecyclerView: init recyclerview");

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = getView().findViewById(R.id.allrecycle);
        recyclerView.setLayoutManager(layoutManager);
        Profile_All_Recycle_Adapter adapter = new Profile_All_Recycle_Adapter(getContext(), mTitles, mImageUrls);
        recyclerView.setAdapter(adapter);
    }
   */
}

