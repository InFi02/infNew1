package com.example.infi_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Vector;

public class RegActivityTwo extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    public EditText username, password, email,mobileNo,iitbRollNo;
    public CheckBox tnc;
    public Button submitBtn;
    public TextView logIn, contactUs,tncStatement,dob;

    public String usernameText, passwordText, emailText, mobileNoText, iitbRollNoText, dobText;
    public Vector userInterest=new Vector();

    FirebaseDatabase rootNode;
    DatabaseReference reference, ConnectionReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_two);

        username=(EditText)findViewById(R.id.name);
        password=(EditText)findViewById(R.id.password);
        email=(EditText)findViewById(R.id.email);
        //mobileNo=(EditText)findViewById(R.id.phone);
        iitbRollNo=(EditText)findViewById(R.id.iitbroll);
        dob=findViewById(R.id.dob);



        tnc=(CheckBox)findViewById(R.id.checkBoxTnC);

        logIn=(TextView)findViewById(R.id.logInPage);
        contactUs=(TextView)findViewById(R.id.contactReg);
        tncStatement=findViewById(R.id.tnc);

        submitBtn=findViewById(R.id.signupBtn);


        usernameText=username.getText().toString();
        passwordText=password.getText().toString();
        emailText=email.getText().toString();

        //take mobile no. from previous activity
        Intent regIntent=getIntent();
        mobileNoText=regIntent.getStringExtra("mobileText");


        iitbRollNoText=iitbRollNo.getText().toString();
        dobText=dob.getText().toString();

//        submitBtn.setEnabled(false);



        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rootNode=FirebaseDatabase.getInstance();
                reference=rootNode.getReference("userDetails");
                ConnectionReference=rootNode.getReference("Connections");


                usernameText=username.getText().toString();
                passwordText=password.getText().toString();
                emailText=email.getText().toString();
                Intent regIntent=getIntent();
                mobileNoText=regIntent.getStringExtra("mobileText");
                iitbRollNoText=iitbRollNo.getText().toString();
                dobText=dob.getText().toString();

                if (usernameText.isEmpty() || passwordText.isEmpty() || emailText.isEmpty() || dobText.isEmpty() || mobileNoText.isEmpty() || iitbRollNoText.isEmpty()) {
                    Toast.makeText(RegActivityTwo.this, "Please Enter All the Details",Toast.LENGTH_LONG).show();
                }

                else if (tnc.isChecked()==false){
                    Toast.makeText(RegActivityTwo.this, "PLease Accept TnC", Toast.LENGTH_LONG).show();
                }
                else {

                    DatabaseReference mobileNoRef=reference.child(mobileNoText);
                    ValueEventListener eventListener=new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (!dataSnapshot.exists()){
                                Uri test=Uri.parse("https://firebasestorage.googleapis.com/v0/b/infiproject-88ce7.appspot.com/o/ProfileImages%2Fprofile_image.png?alt=media&token=8ef76099-7ffa-4ec6-ab46-3e2e218ce649");
                                String picky=test.toString();
                                Users userDetail = new Users(usernameText, mobileNoText, emailText, dobText, iitbRollNoText, passwordText, false,userInterest,0,picky);
                                reference.child(mobileNoText).setValue(userDetail).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(RegActivityTwo.this, "Registration Successful", Toast.LENGTH_LONG).show();
                                        //reference2.child(mobileNoText).setValue(false);
                                        Intent interestIntent =new Intent(RegActivityTwo.this, Interest_Part.class);
                                        interestIntent.putExtra("mobileText", mobileNoText);
                                        startActivity(interestIntent);
                                        finish();


                                    }
                                });


                            }
                            else {
                                Toast.makeText(RegActivityTwo.this, "Mobile Number already Registered", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Log.d("Tag", databaseError.getMessage());


                        }
                    };
                    mobileNoRef.addListenerForSingleValueEvent(eventListener);

                    DatabaseReference connectionMobile=ConnectionReference.child(mobileNoText);
                    ValueEventListener secondEventListener= new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            ConnectionReference.child(mobileNoText).child("sent").child("dummy").setValue("0");
                            ConnectionReference.child(mobileNoText).child("received").child("dummy").setValue("0");
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Log.d("RegConnectionError", databaseError.getMessage());
                        }
                    };
                    connectionMobile.addListenerForSingleValueEvent(secondEventListener);

//                    Users userDetail = new Users(usernameText, mobileNoText, emailText, dobText, iitbRollNoText, passwordText, false);
//                    reference.child(mobileNoText).setValue(userDetail).addOnCompleteListener(new OnCompleteListener<Void>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Void> task) {
//                            Toast.makeText(RegActivityTwo.this, "Registration Successful", Toast.LENGTH_LONG).show();
//                            reference2.child(mobileNoText).setValue(false);
//                            startActivity(new Intent(RegActivityTwo.this, Interest_Part.class));
//
//
//                        }
//                    });


                }



            }
        });


        loginSetListener();
        contactSetListener();
        dobOnClickListener();
    }

    public void loginSetListener(){
        FirebaseAuth.getInstance().signOut();
        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegActivityTwo.this, MainActivity.class));
                finish();
            }
        });
    }

    public void contactSetListener(){
        contactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegActivityTwo.this, Contact_Us.class));
            }
        });
    }
//
//    private void tncOnclickListener(){
//        tnc.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (tnc.isChecked()){
//                    submitBtn.setEnabled(true);
//                }
//                else {
//                    submitBtn.setEnabled(false);
//                }
//            }
//        });
//    }

    private void dobOnClickListener(){
        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();

            }
        });

    }

    public void showDatePickerDialog(){
        DatePickerDialog datePickerDialog=new DatePickerDialog(
                this,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)

        );
        datePickerDialog.show();
    }
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String dobFromCalendar=dayOfMonth+"/"+(month+1)+"/"+year;
        dob.setText(dobFromCalendar);
        dob.setTranslationZ(0.0f);
    }

    @Override
    public void onBackPressed() {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(RegActivityTwo.this, MainActivity.class));
        finishAffinity();
        finish();
    }

    public String sendData(){
        return mobileNoText;
    }

}
