package Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.infi_project.MessageActivity;
import com.example.infi_project.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class Sent_Req_Adapter extends RecyclerView.Adapter<Sent_Req_Adapter.ViewHolder> {

    private Context mcontext;
    private ArrayList<String> profileNameList= new ArrayList<>();
    private ArrayList<String> profilePhoneList= new ArrayList<>();

    private ArrayList<String> profileImageList= new ArrayList<>();
    String mobileText;

    public Sent_Req_Adapter(String mobileText,Context mcontext, ArrayList<String> profileNameList, ArrayList<String> profilePhoneList, ArrayList<String> profileImageList) {
        this.mcontext = mcontext;
        this.profileNameList = profileNameList;
        this.profilePhoneList = profilePhoneList;
        this.profileImageList = profileImageList;
        this.mobileText=mobileText;
    }


    @NonNull
    @Override
    public Sent_Req_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.sent_req_card,parent,false);
        ViewHolder card=new ViewHolder(view);

        return card;
    }

    @Override
    public void onBindViewHolder(@NonNull Sent_Req_Adapter.ViewHolder holder, int position) {


        if(profileImageList.get(position).equals("default")){
            holder.profile_image.setImageResource(R.mipmap.ic_launcher);
        } else {
            System.out.println("I am  the best");


            Glide.with(mcontext)
                    .asBitmap()
                    .load(profileImageList.get(position))
                    .into(holder.profile_image);
        }

        holder.username.setText(profileNameList.get(position));

        holder.WithDraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference checkreq;
                checkreq = FirebaseDatabase.getInstance().getReference().child("Connections");
                checkreq.child(mobileText).child("sent").child(profilePhoneList.get(position)).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {

                            checkreq.child(mobileText).child("sent").child(profilePhoneList.get(position)).removeValue();



                        }




                       // else{
                         //   Toast.makeText(getContext(),"You aren't connected yet",Toast.LENGTH_SHORT).show();
                        //}
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });








            }
        });
    }

    @Override
    public int getItemCount() {
        return profileNameList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView username;
        CircleImageView profile_image;
        RelativeLayout CardParent;
        Button WithDraw;
       // Button decline;


        public ViewHolder(View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.profileName);
            profile_image = itemView.findViewById(R.id.profileImage);
            WithDraw=itemView.findViewById(R.id.WithDraw);
           // decline=itemView.findViewById(R.id.Decline);

            CardParent = itemView.findViewById(R.id.Sent_ReqCard);


        }
    }
}

