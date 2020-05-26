package com.example.infi_project;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.Objects;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileImageCaptureFragment extends Fragment {

    public String mobileText;
    private static final String TAG= "Profile Image Capture:";

    private Button add;
    private Button skip;
    private static final int galleryPick=1;
    private ImageView profile;
    private DatabaseReference RootRef;
    private StorageReference userProfileImagesReference;


    public ProfileImageCaptureFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Interest_Part activity= (Interest_Part) getActivity();
        assert activity != null;
        mobileText=activity.sendData();




    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_profile_image_capture, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {



        //boolean imageSelected;
        add=getView().findViewById(R.id.add);
        skip=getView().findViewById(R.id.skip);
        profile=getView().findViewById(R.id.ImageProfile);

        RootRef= FirebaseDatabase.getInstance().getReference();
        userProfileImagesReference= FirebaseStorage.getInstance().getReference().child("ProfileImages");



        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent galleryIntent = new Intent();
//                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
//                galleryIntent.setType("image/*");
//                startActivityForResult(galleryIntent,galleryPick);
                    Intent galleryIntent= new Intent();

//                    startActivityForResult(galleryIntent,galleryPick);

//                    Intent intent = new Intent();
                    galleryIntent.setType("image/*");
                    galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(galleryIntent,galleryPick);


//                    startActivityForResult(galleryIntent,galleryPick);

            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent= new Intent();
                galleryIntent.setType("image/*");
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(galleryIntent,galleryPick);


            }
        });

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent appMainPage_intent = new Intent(getContext(), AppMainPage.class);
                appMainPage_intent.putExtra("mobileText", mobileText);
                startActivity(appMainPage_intent);
                RootRef.child("userDetails").child(mobileText).child("image").setValue(null);

            }
        });


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("Image_Crop", String.valueOf(requestCode));
        try {
            if (requestCode == galleryPick && resultCode == RESULT_OK && data != null) {
                Uri ImageUri = data.getData();
                CropImage.activity(ImageUri)
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1, 1)
                        .start(Objects.requireNonNull(getContext()),this);

            }

            else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                assert result != null;
                Log.d("Image cropped", result.toString());
                if (resultCode == RESULT_OK) {

                    assert result != null;
                    Uri resultUri = result.getUri();
                    Log.d("Image Cropped2", resultUri.toString());


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
                                                            if (task.isSuccessful()) {
                                                                Toast.makeText(getContext(), "Image saved in database successfully", Toast.LENGTH_SHORT).show();
                                                                Intent appMainPage_intent = new Intent(getContext(), AppMainPage.class);
                                                                appMainPage_intent.putExtra("mobileText", mobileText);
                                                                startActivity(appMainPage_intent);
                                                                if(getActivity() != null) {
                                                                    getActivity().finish();
                                                                }

                                                            } else {
                                                                String message = task.getException().toString();
                                                                Toast.makeText(getContext(), "Error: " + message, Toast.LENGTH_SHORT).show();


                                                            }

                                                        }
                                                    });

                                        }
                                    });

                                }
                            });

                }
                else if (resultCode==CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                    Exception error=result.getError();
                }
            }

            else {
                Toast.makeText(getContext(), "You haven't picked Image",Toast.LENGTH_LONG).show();
            }

        }

        catch (Exception e){
            Toast.makeText(getContext(), "Something went wrong = "+e.getMessage(), Toast.LENGTH_LONG).show();
        }



    }
}
