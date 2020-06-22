package com.example.infi_project;
// Shivam Raj

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.transition.Transition;
import com.example.infi_project.data.ProfileTab;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileListRecyclerViewAdapter  extends RecyclerView.Adapter<ProfileListRecyclerViewAdapter.ViewHolder> {
    private static final String TAG= "ProfileRecyclerAdapter";

    private ArrayList<String> profileNameList= new ArrayList<>();
    private ArrayList<String> profileAboutList= new ArrayList<>();
    private ArrayList<String> profileImageList= new ArrayList<>();
    private ArrayList<String> profileNumberList=new ArrayList<>();
    private Context mContext;

    public ProfileListRecyclerViewAdapter(Context mContext, ArrayList<String> profileNameList, ArrayList<String> profileAboutList, ArrayList<String> profileImageList,ArrayList<String> profileNumberList) {
        this.profileNameList = profileNameList;
        this.profileAboutList = profileAboutList;
        this.profileImageList = profileImageList;
        this.profileNumberList=profileNumberList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.profilelist, parent, false);
        ViewHolder holderProfileCard=new ViewHolder(view);
        return holderProfileCard;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder");

        Glide.with(mContext)
                .asBitmap()
                .load(profileImageList.get(position))
                .into(holder.profileImage);

        holder.profileName.setText(profileNameList.get(position));
        holder.profileAbout.setText(profileAboutList.get(position));

        holder.profileCardParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: clicked on: "+profileNameList.get(position));
                Toast.makeText(mContext, profileNameList.get(position)+ " is Clicked", Toast.LENGTH_SHORT).show();
                //to be removed:- start 001
               // Intent messageActivityIntent= new Intent(mContext, MessageActivity.class);
                //messageActivityIntent.putExtra("UserName", profileNameList.get(position));
                //messageActivityIntent.putExtra("ImageUrl", profileImageList.get(position));
                //messageActivityIntent.putExtra("phone", profileNumberList.get(position));
                //mContext.startActivity(messageActivityIntent);
                // to be removed :-end 001



                ProfileTab ldf = new ProfileTab ();
                Bundle args = new Bundle();
                args.putString("profileno", profileNumberList.get(position));
                ldf.setArguments(args);
                AppMainPage activity=(AppMainPage)v.getContext();

                //Inflate the fragment
               // FragmentTransaction transaction = fragmentManager.beginTransaction();
               activity.getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, ldf).addToBackStack(null).commit();




            }
        });
    }

    @Override
    public int getItemCount() {
        return profileImageList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        CircleImageView profileImage;
        //ImageView profileImage;
        TextView profileName;
        TextView profileAbout;
        RelativeLayout profileCardParent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            profileImage=itemView.findViewById(R.id.profileImage);
            profileName=itemView.findViewById(R.id.profileName);
            profileAbout=itemView.findViewById(R.id.profileAbout);
            profileCardParent=itemView.findViewById(R.id.profileCardParent);

        }
    }
}
