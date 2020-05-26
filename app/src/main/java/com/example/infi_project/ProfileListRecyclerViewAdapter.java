package com.example.infi_project;
// Shivam Raj

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.transition.Transition;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileListRecyclerViewAdapter  extends RecyclerView.Adapter<ProfileListRecyclerViewAdapter.ViewHolder> {
    private static final String TAG= "ProfileRecyclerAdapter";

    private ArrayList<String> profileNameList= new ArrayList<>();
    private ArrayList<String> profileAboutList= new ArrayList<>();
    private ArrayList<String> profileImageList= new ArrayList<>();
    private Context mContext;

    public ProfileListRecyclerViewAdapter(Context mContext, ArrayList<String> profileNameList, ArrayList<String> profileAboutList, ArrayList<String> profileImageList) {
        this.profileNameList = profileNameList;
        this.profileAboutList = profileAboutList;
        this.profileImageList = profileImageList;
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
                .load(profileAboutList.get(position))
                .into(holder.profileImage);

        holder.profileName.setText(profileNameList.get(position));
        holder.profileAbout.setText(profileAboutList.get(position));

        holder.profileCardParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: clicked on: "+profileNameList.get(position));

                Toast.makeText(mContext, profileNameList.get(position)+ " is Clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return profileImageList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        CircleImageView profileImage;
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
