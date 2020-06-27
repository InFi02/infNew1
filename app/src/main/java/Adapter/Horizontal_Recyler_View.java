package Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.infi_project.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import Models.HorizontalModel;

public class Horizontal_Recyler_View   extends RecyclerView.Adapter<Horizontal_Recyler_View.HorizontalRVViewHolder>{


    Context mContext;
    ArrayList<String> postList;
    String interestName;
    String phone;

    public Horizontal_Recyler_View(Context mContext, ArrayList<String> postList, String interestName,String phone) {
        this.mContext = mContext;
        this.postList = postList;
        this.interestName=interestName;
        this.phone=phone;
    }

    @NonNull
    @Override
    public HorizontalRVViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        System.out.println("Horizontal Recycler View");
        System.out.println(postList.size());

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_horizontal,parent,false);
        return new HorizontalRVViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HorizontalRVViewHolder holder, int position) {

//        System.out.println(horizontalModel.getTitle());
//        System.out.println("meow meow");
//        System.out.println("hr"+horizontalModel.getTitle());
//        holder.textViewTitle.setText(horizontalModel.getTitle());
//        Glide.with(mContext).load(horizontalModel.getPost()).into(holder.imageViewThumb);
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(mContext,horizontalModel.getTitle(),Toast.LENGTH_SHORT).show();
//            }
//        });

        DatabaseReference postRef= FirebaseDatabase.getInstance().getReference().child("specificPost").child(phone).child(interestName).child(postList.get(position)).getRef();
        postRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    Glide.with(mContext).load(dataSnapshot.child("postImage").getValue().toString()).into(holder.imageViewThumb);
                    holder.textViewTitle.setText(dataSnapshot.child("description").getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("HorizontalRecycle", databaseError.getMessage());
            }
        });

    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public class HorizontalRVViewHolder extends RecyclerView.ViewHolder{

        TextView textViewTitle;
        ImageView imageViewThumb;

        public HorizontalRVViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle=(TextView)itemView.findViewById(R.id.txtTitleHorizontal);
            imageViewThumb=(ImageView)itemView.findViewById(R.id.ivThumb);

        }
    }
}
