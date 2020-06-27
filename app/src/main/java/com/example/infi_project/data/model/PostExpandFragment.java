package com.example.infi_project.data.model;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.infi_project.R;


public class PostExpandFragment extends DialogFragment {

    public ImageView image_profile, post_image, like, comment;
    public TextView username, likes, publisher, description, comments;



    public PostExpandFragment() {
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
        return inflater.inflate(R.layout.fragment_post_expand, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        image_profile = getView().findViewById(R.id.image_profilePE);
        username = getView().findViewById(R.id.usernamePE);
        post_image = getView().findViewById(R.id.post_imagePE);
        like = getView().findViewById(R.id.likePE);
        comment = getView().findViewById(R.id.commentPE);
        // save = itemView.findViewById(R.id.save);
        likes = getView().findViewById(R.id.likesPE);
//            publisher = itemView.findViewById(R.id.publisher);
        description = getView().findViewById(R.id.descriptionPE);
        comments = getView().findViewById(R.id.commentsPE);
    }
}