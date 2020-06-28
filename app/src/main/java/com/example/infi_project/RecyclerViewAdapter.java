package com.example.infi_project;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.infi_project.data.ExploreTab;
import com.example.infi_project.data.ProfileTab;

import java.util.ArrayList;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.viewHolder> {

    private static  final String TAG="RecyclerViewIconAdapter";

    public interface  AdapterCallback{
        void onMethodCallback(String interest);
    }



    private AdapterCallback mAdapterCallback;


    private ArrayList<String> interestNames= new ArrayList<>();
    private Context mContext;

    int selectedPosition=-1;


    public String interest_selected;


    //constructor

    public RecyclerViewAdapter(Context mContext, ArrayList<String> interestNames, AdapterCallback mAdapterCallback) {
        this.interestNames = interestNames;
        this.mContext = mContext;
        this.mAdapterCallback=mAdapterCallback;
    }



    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: called");
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.interestlist, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final viewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called");
        //Glide.with(mContext).asBitmap();
        holder.interest_name.setText(interestNames.get(position));

        if(selectedPosition==position) {
            holder.interest_name.setBackgroundColor(Color.parseColor("#FFAAA7A7"));
            holder.interestRecyclerCard.setCardBackgroundColor(Color.parseColor("#FFAAA7A7"));
            holder.itemView.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }
        else {
            holder.itemView.setBackgroundColor(Color.parseColor("#FFFFFF"));
            holder.interestRecyclerCard.setCardBackgroundColor(Color.parseColor("#FFF6F6D9"));
            holder.interest_name.setBackgroundColor(Color.parseColor("#FFF6F6D9"));
        }


        holder.interest_name.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        selectedPosition=position;
                        notifyDataSetChanged();


                        Log.d(TAG, "onClick: clicked "+interestNames.get(position));



                        //Toast.makeText(mContext, "Clicked"+interestNames.get(position), Toast.LENGTH_SHORT).show();
                        if (interestNames.get(position)!=null) {
                            interest_selected = interestNames.get(position);
                            //holder.interestRecyclerCard.setCardBackgroundColor(Color.rgb(235,243,232));
                            mAdapterCallback.onMethodCallback(interest_selected);
                        }
                        else {
                            Toast.makeText(mContext, "interestNames is null", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );


    }



    @Override
    public int getItemCount() {
        return interestNames.size();
    }

    class  viewHolder extends RecyclerView.ViewHolder{
        TextView interest_name;
        CardView interestRecyclerCard;

        viewHolder(@NonNull View itemView) {
            super(itemView);
            interest_name=itemView.findViewById(R.id.interest_name);
            interestRecyclerCard=itemView.findViewById(R.id.interestRecyclerCard);

        }
    }

}
