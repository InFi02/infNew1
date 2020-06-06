package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.infi_project.R;
import com.example.infi_project.RecyclerViewAdapter;
import com.example.infi_project.Users;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private Context mcontext;
   private ArrayList<String> profileNameList= new ArrayList<>();
    private ArrayList<String> profileImageList= new ArrayList<>();
    //private List<Users> musers;
    public UserAdapter(Context mcontext,ArrayList<String> profileNameList, ArrayList<String> profileImageList){

        this.profileNameList = profileNameList;
        this.profileImageList = profileImageList;
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
    public void onBindViewHolder(@NonNull ViewHolder holder,final int position) {



       // holder.username.setText(user.getUserName());
        //if(user.getImage().equals("default"))
        //{
        //    holder.profile_image.setImageResource(R.mipmap.ic_launcher);
        //} else {



            Glide.with(mcontext)
                    .asBitmap()
                    .load(profileImageList)
                    .into(holder.profile_image);

        holder.username.setText(profileNameList.get(position));

        //}



    }

    @Override
    public int getItemCount() {
        return profileImageList.size();
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
