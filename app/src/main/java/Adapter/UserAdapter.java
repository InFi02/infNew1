package Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.infi_project.MessageActivity;
import com.example.infi_project.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private Context mcontext;
   private ArrayList<String> profileNameList= new ArrayList<>();
    private ArrayList<String> profilePhoneList= new ArrayList<>();

    private ArrayList<String> profileImageList= new ArrayList<>();
    //private List<Users> musers;
    public UserAdapter(Context mcontext,ArrayList<String> profileNameList, ArrayList<String> profileImageList,ArrayList<String> profilePhoneList){

        this.profileNameList = profileNameList;
        this.profileImageList = profileImageList;
        this.profilePhoneList=profilePhoneList;
        this.mcontext=mcontext;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item,parent,false);
       ViewHolder chatcard=new ViewHolder(view);

        return chatcard;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {



       // holder.username.setText(user.getUserName());
        //if(user.getImage().equals("default"))
        //{
        //    holder.profile_image.setImageResource(R.mipmap.ic_launcher);
        //} else {

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

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent= new Intent(mcontext, MessageActivity.class);
                intent.putExtra("phone",profilePhoneList.get(position));
                intent.putExtra("USERNAME",profileNameList.get(position));
                intent.putExtra("ImageUrl",profileImageList.get(position));

                mcontext.startActivity(intent);


            }
        });

        //}



    }

    @Override
    public int getItemCount() {
        return profileNameList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{




        TextView username;
        CircleImageView profile_image;
        RelativeLayout ChatCardParent;
        public ViewHolder(View itemView){
            super(itemView);

            username=itemView.findViewById(R.id.usernamee);
            profile_image=itemView.findViewById(R.id.profilemage);
            ChatCardParent=itemView.findViewById(R.id.ChartCardParent);



        }
    }


}
