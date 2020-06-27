package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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

public class GroupListAdapter extends RecyclerView.Adapter<GroupListAdapter.ViewHolder> {
    public Context mContext;
    public ArrayList<String> groupIdList= new ArrayList<String>();
    public String phone;


    public GroupListAdapter(Context mContext, ArrayList<String> groupIdList, String phone) {
        this.mContext = mContext;
        this.groupIdList = groupIdList;
        this.phone=phone;
    }

    @NonNull
    @Override
    public GroupListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.privategrouplist, parent, false);
        return new GroupListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DatabaseReference groupReference= FirebaseDatabase.getInstance().getReference().child("Groups").child("PeopleSpecific").child(phone).child(groupIdList.get(position)).getRef();

        groupReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    holder.groupName.setText(dataSnapshot.child("GroupName").getValue().toString());
//                    Glide.with(mContext).load(dataSnapshot.child("Image")).into(holder.groupImage);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }


    @Override
    public int getItemCount() {
        return groupIdList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{


        public ImageView groupImage;
        public TextView groupName;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            groupImage=itemView.findViewById(R.id.groupImage);
            groupName=itemView.findViewById(R.id.groupListName);
        }


    }
}
