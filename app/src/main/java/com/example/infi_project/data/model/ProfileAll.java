package com.example.infi_project.data.model;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.infi_project.ProfileListRecyclerViewAdapter;
import com.example.infi_project.R;
import com.example.infi_project.RecyclerViewAdapter;

import java.util.ArrayList;

import Adapter.Horizontal_Recyler_View;
import Adapter.Profile_All_Recycle_Adapter;
import Adapter.Vertical_Recycler_View_Adapter;
import Models.HorizontalModel;
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

        for(int i=1;i<=5;i++){

            VerticalModel verticalModel=new VerticalModel();
            verticalModel.setTitle("Title"+i);

            ArrayList<HorizontalModel> arrayListHorizontal=new ArrayList<>();
            for(int j=0;j<5;j++){

                HorizontalModel horizontalModel=new HorizontalModel();
                horizontalModel.setTitle("Description"+j);

                arrayListHorizontal.add(horizontalModel);


            }

            verticalModel.setArrayList(arrayListHorizontal);
            arrayListVertical.add(verticalModel);

        }
        adapter.notifyDataSetChanged();
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

