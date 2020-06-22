package com.example.infi_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.infi_project.data.model.Chat;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import Adapter.MessageAdapter;
import de.hdodenhof.circleimageview.CircleImageView;

public class MessageActivity extends AppCompatActivity {

    CircleImageView profile_image;
    TextView username;

    public String userNameText;
    public String imageUrlText;
    public String phone;


    FirebaseUser user;
    DatabaseReference reference;
    DatabaseReference details;
    Intent intent;

    FirebaseUser fuser;

    ImageButton btnsend;
    EditText textsend;

    MessageAdapter messageAdapter;
    List<Chat> mchat;

    RecyclerView recyclerView;
    String mobileText;

    String mynumber;

    String oppno,usernamee,image;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        recyclerView=findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        //start 002:- written by PAYAL , can not find context
        // put under comment by Shivam Raj
//        intent=getIntent();
//        final String phone=intent.getStringExtra("phone");

        //ends 002;
//       Intent Message_intent = getIntent();
//        final String mobileText = Message_intent.getStringExtra("mobileText");

        fuser= FirebaseAuth.getInstance().getCurrentUser();
        mobileText=fuser.getPhoneNumber();
        mynumber=mobileText.toString();
        intent=getIntent();
        phone=intent.getStringExtra("phone");
        //Toast.makeText(MessageActivity.this,"pho",Toast.LENGTH_SHORT).show();

      /*  details=FirebaseDatabase.getInstance().getReference().child("userDetails").child(phone);
        details.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    //Toast.makeText(MessageActivity.this,"meow",Toast.LENGTH_SHORT).show();


                    userNameText= Objects.requireNonNull(dataSnapshot.child("userName").getValue()).toString();
                    imageUrlText=Objects.requireNonNull(dataSnapshot.child("image").getValue()).toString();
                    usernamee=userNameText.toString();
                    image=imageUrlText.toString();
                  //  Toast.makeText(MessageActivity.this,usernamee,Toast.LENGTH_SHORT).show();


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

       */
        userNameText=intent.getStringExtra("USERNAME");
        imageUrlText=intent.getStringExtra("ImageUrl");

        //Toast.makeText(MessageActivity.this,usernamee,Toast.LENGTH_SHORT).show();

        oppno=phone.toString();
        usernamee=userNameText.toString();
        image=imageUrlText.toString();
       // Uri uri=Uri.parse("https://www.google.com/url?sa=i&url=https%3A%2F%2Ftimesofindia.indiatimes.com%2Ftravel%2Fdestinations%2Fbangalore-in-pictures%2Fslideshow_list%2F54559212.cms&psig=AOvVaw39u5U0xXrEjdIK0SjjNzdA&ust=1592860998123000&source=images&cd=vfe&ved=0CAIQjRxqFwoTCIiUzuPrk-oCFQAAAAAdAAAAABAD");







        btnsend=findViewById(R.id.btn_send);
        textsend=findViewById(R.id.text_end);


        btnsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = textsend.getText().toString();
                if (!msg.equals(""))
                    sendMessage(mynumber, oppno, msg);

                else {
                    Toast.makeText(MessageActivity.this, "Write a message", Toast.LENGTH_SHORT).show();
                }
            }
        });

        profile_image=findViewById(R.id.profile_image);
        username=findViewById(R.id.usernameMessage);

        username.setText(userNameText);
        Glide.with(MessageActivity.this)
                .asBitmap()
                .load(image)
                .into(profile_image);

        readMessages(mynumber,oppno,image);



//        reference=FirebaseDatabase.getInstance().getReference("Users").child(phone);
//        reference.addValueEventListener(new ValueEventListener() {
//           @Override
//           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//               Users user=dataSnapshot.getValue(Users.class);
//               username.setText(user.getUserName());
//               if(user.getImage().equals("default")){
//                   profile_image.setImageResource(R.mipmap.ic_launcher);
//               } else
//               Glide.with(MessageActivity.this).load(user.getImage());
//
//               readMessages(mobileText,phone,user.getImage());
//
//
//           }
//
//
//
//
//
//
//
//           @Override
//           public void onCancelled(@NonNull DatabaseError databaseError) {
//
//           }
//       });

    }

    private void sendMessage(String sender,String receiver,String message){

        DatabaseReference reference= FirebaseDatabase.getInstance().getReference();
        HashMap<String,Object> hashMap=new HashMap<>();

        hashMap.put("receiver",receiver);
        hashMap.put("message",message);
        hashMap.put("sender",sender);

        reference.child("Chats").push().setValue(hashMap);
        textsend.setText("");


    }

     private void readMessages(final String myid, final String userid, final String imageurl){
        mchat=new ArrayList<>();
        reference=FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mchat.clear();
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){

                    Chat chat=snapshot.getValue(Chat.class);
                    if(chat.getReceiver().equals(myid) && chat.getSender().equals(userid) ||

                        chat.getReceiver().equals(userid) && chat.getSender().equals(myid)){
                        mchat.add(chat);



                    }

                    messageAdapter=new MessageAdapter(MessageActivity.this,mchat,imageurl);
                    recyclerView.setAdapter(messageAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
     }


}
