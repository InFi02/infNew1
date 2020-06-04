package com.example.infi_project.data.model;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.infi_project.AppMainPage;
import com.example.infi_project.R;
import com.example.infi_project.data.ProfileTab;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;


import java.util.Objects;

import static android.app.Activity.RESULT_OK;


public class ProfileImagePicker extends DialogFragment  {

    private Button Edit;
    private Button Remove;
    private static final int galleryPick=1;
    private ImageView profile;
    private DatabaseReference RootRef;
    private StorageReference  userProfileImagesReference;
    public String mobileText;
    private ImageView profile_pic;




    public ProfileImagePicker() {
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

        AppMainPage activity= (AppMainPage) getActivity();
        mobileText=activity.sendData();

        ProfileTab frag=(ProfileTab) getParentFragment();
        profile_pic=frag.sData();

        return inflater.inflate(R.layout.fragment_profile_image_picker, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        Toast.makeText(getContext(), "Meow", Toast.LENGTH_SHORT).show();



        Edit=getView().findViewById(R.id.edit);
        Remove=getView().findViewById(R.id.remove);
        profile=getView().findViewById(R.id.ImageProfile);



        RootRef= FirebaseDatabase.getInstance().getReference();
        userProfileImagesReference= FirebaseStorage.getInstance().getReference().child("ProfileImages");

        RootRef = FirebaseDatabase.getInstance().getReference().child("userDetails").child(mobileText);
        RootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                String data = dataSnapshot.child("image").getValue().toString();
                Picasso.get().load(data).placeholder(R.drawable.profile_image).into(profile);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





        Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent();
                galleryIntent.setType("image/*");
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(galleryIntent,galleryPick);


            }
        });

        Remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable mDrawable=getResources().getDrawable(R.drawable.profile_image);
                profile.setImageDrawable(mDrawable);

            }
        });


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==galleryPick &&  resultCode==RESULT_OK && data!=null)
        {
            Uri ImageUri = data.getData();

            CropImage.activity(ImageUri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1,1)
                    .start(Objects.requireNonNull(getContext()),this);

        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            assert result != null;

            if(resultCode==RESULT_OK)
            {

                assert result != null;

                Uri resultUri = result.getUri();


                StorageReference filePath = userProfileImagesReference.child(mobileText + ".jpg");

                filePath.putFile(resultUri)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                final Task<Uri> firebaseUri = taskSnapshot.getStorage().getDownloadUrl();
                                firebaseUri.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        final String downloadUrl = uri.toString();

                                        RootRef.child("userDetails").child(mobileText).child("image")
                                                .setValue(downloadUrl)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if(task.isSuccessful()){
                                                            Toast.makeText(getContext(), "Profile Picture updated successfuly", Toast.LENGTH_SHORT).show();
                                                            Picasso.get().load(downloadUrl).placeholder(R.drawable.profile_image).into(profile);
                                                            Picasso.get().load(downloadUrl).placeholder(R.drawable.profile_image).into(profile_pic);

                                                           getDialog().dismiss();


                                                        }
                                                        else{
                                                            String message = task.getException().toString();
                                                            Toast.makeText(getContext(), "Error: " + message,Toast.LENGTH_SHORT).show();



                                                        }

                                                    }
                                                });

                                    }
                                });

                            }
                        });

            }
        }

    }
}
