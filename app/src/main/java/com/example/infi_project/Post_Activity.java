package com.example.infi_project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.infi_project.data.FeedTab;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.HashMap;

public class Post_Activity extends AppCompatActivity {

    Uri imageUri;
    String myUrl="";
    StorageTask uploadTask;
    StorageReference storageReference;

    ImageView close, image_added;
    TextView post;
    EditText description;

    CropImage.ActivityResult result;
    FirebaseUser fuser;
    String mobileText;
    String phone;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_);


        close=findViewById(R.id.close);
        image_added=findViewById(R.id.image_added);
        post=findViewById(R.id.post);
        description=findViewById(R.id.description);

        fuser= FirebaseAuth.getInstance().getCurrentUser();
        mobileText=fuser.getPhoneNumber();
        phone=mobileText.toString();

        storageReference= FirebaseStorage.getInstance().getReference("posts");

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(Post_Activity.this,AppMainPage.class);
                startActivity(intent);

                /*FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                Fragment newFragment = new FeedTab();
                transaction.add(R.id.fragment_container,newFragment,null);
                transaction.addToBackStack(null);
                transaction.commit();

                 */


            }
        });


        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();

            }
        });

        CropImage.activity()
                .setAspectRatio(1,1)
                .start(Post_Activity.this);


    }

    private String getFileExtension(Uri uri){
        ContentResolver contentResolver=getContentResolver();
        MimeTypeMap mime=MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(contentResolver.getType(uri));
    }


    private void uploadImage(){
        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Posting");
        progressDialog.show();

        Intent Message_intent = getIntent();
        final String mobileText = Message_intent.getStringExtra("mobileText");
        if(imageUri!=null){
            final StorageReference filereference=storageReference.child(System.currentTimeMillis()+"."+getFileExtension(imageUri));
            uploadTask=filereference.putFile(imageUri);
            uploadTask.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {
                    if(!task.isSuccessful()){
                        throw task.getException();
                    }

                    return filereference.getDownloadUrl();


                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if(task.isSuccessful()){
                        Uri downloadUri=task.getResult();
                        myUrl=downloadUri.toString();
                        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Posts");

                                String postid=reference.push().getKey();

                        HashMap<String,Object> hashMap=new HashMap<>();
                        hashMap.put("postid",postid);
                        hashMap.put("postimage",myUrl);
                        hashMap.put("publisher",phone);
                        hashMap.put("description",description.getText().toString());


                        reference.child(postid).setValue(hashMap);
                        progressDialog.dismiss();

                        Intent intent=new Intent(Post_Activity.this,AppMainPage.class);
                        startActivity(intent);

                      /*  FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        Fragment newFragment = new FeedTab();
                        transaction.add(R.id.fragment_container,newFragment,null);
                        transaction.addToBackStack(null);
                        transaction.commit();*/

                    }
                    else{
                        Toast.makeText(Post_Activity.this,"Failed",Toast.LENGTH_SHORT).show();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(Post_Activity.this,""+e.getMessage(),Toast.LENGTH_SHORT).show();

                }
            });
        } else{
            Toast.makeText(Post_Activity.this,"No Image Selected",Toast.LENGTH_SHORT).show();
        }


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode==RESULT_OK) {
            result = CropImage.getActivityResult(data);
            imageUri = result.getUri();
            image_added.setImageURI(imageUri);
        }
        else{
            Toast.makeText(this,"Something went wrong",Toast.LENGTH_SHORT).show();

            Intent intent=new Intent(Post_Activity.this,AppMainPage.class);
            startActivity(intent);

           /* FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            Fragment newFragment = new FeedTab();
            transaction.add(R.id.fragment_container,newFragment,null);
            transaction.addToBackStack(null);
            transaction.commit();

            */

        }


        }


    }
