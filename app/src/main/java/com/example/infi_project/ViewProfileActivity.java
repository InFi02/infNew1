package com.example.infi_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.infi_project.data.ProfileTab;

public class ViewProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);

        Intent parentIntent= getIntent();
        String phone= parentIntent.getStringExtra("phone");


        ProfileTab ldf = new ProfileTab ();
        Bundle args = new Bundle();
        args.putString("profileno", phone);
        ldf.setArguments(args);

        //Inflate the fragment
        // FragmentTransaction transaction = fragmentManager.beginTransaction();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container1, ldf).commit();

    }
}