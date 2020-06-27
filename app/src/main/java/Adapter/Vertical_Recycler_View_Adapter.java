package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.infi_project.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import Models.HorizontalModel;
import Models.VerticalModel;

public class Vertical_Recycler_View_Adapter extends RecyclerView.Adapter<Vertical_Recycler_View_Adapter.ViewHolder> {


    Context context;
//    ArrayList<VerticalModel>  arrayList;
    String phone;
    Horizontal_Recyler_View adapter;
//    ArrayList<HorizontalModel> singleItem;

    ArrayList<String> postInterestList;
    ArrayList<ArrayList<String>> postIdList;


    public Vertical_Recycler_View_Adapter(Context context, ArrayList<String> postInterestList,ArrayList<ArrayList<String>> postIdList, String phone) {
        this.context = context;
        this.postInterestList = postInterestList;
        this.postIdList=postIdList;
        this.phone=phone;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_vertical,parent,false);



        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String interestName= postInterestList.get(position);
        holder.interest.setText(interestName);

        Horizontal_Recyler_View horizontalAdapter= new Horizontal_Recyler_View(context, postIdList.get(position), interestName, phone);
        holder.recyclerView.setHasFixedSize(true);
        holder.recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false));

        holder.recyclerView.setAdapter(horizontalAdapter);



    }

    @Override
    public int getItemCount() {
        return postInterestList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {

        RecyclerView recyclerView;
        TextView interest;
        public ViewHolder(View itemView){
            super(itemView);
            recyclerView=(RecyclerView)itemView.findViewById(R.id.allrecycle);
            interest=(TextView)itemView.findViewById(R.id.interest_head);



        }

    }

//    public void readSpecificPost(String title){
//        DatabaseReference specificPostRef= FirebaseDatabase.getInstance().getReference().child("specificPost").child(phone).child(title).getRef();
//        specificPostRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if (dataSnapshot.exists()){
//                    System.out.println("Profile All Debug");
//                    singleItem.clear();
////                    ArrayList<HorizontalModel> tempList=new ArrayList<HorizontalModel>();
//                    for ( DataSnapshot snapshot : dataSnapshot.getChildren()){
//                        HorizontalModel temp= new HorizontalModel();
//                        temp.setTitle(snapshot.child("description").getValue().toString());
//                        temp.setPost((snapshot.child("postImage").getValue().toString()));
//                        singleItem.add(temp);
//
//                    }
//
//                    adapter.notifyDataSetChanged();
//
//
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });


//    }





}
