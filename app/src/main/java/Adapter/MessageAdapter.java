package Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.infi_project.MessageActivity;
import com.example.infi_project.R;
import com.example.infi_project.data.model.Chat;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    public static final int msg_type_left=0;
    public static final int msg_type_right=1;



    private Context mcontext;
   // private ArrayList<String> profileNameList= new ArrayList<>();
    //private ArrayList<String> profilePhoneList= new ArrayList<>();

    //private ArrayList<String> profileImageList= new ArrayList<>();
    private List<Chat> mChats;
    String imageurl;
    FirebaseUser fuser;


    public MessageAdapter(Context mcontext,List<Chat> mChats,String imageurl){

        this.mChats= mChats;
        this.imageurl=imageurl;



        this.mcontext=mcontext;

    }

    @NonNull
    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType==msg_type_right){
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item_right,parent,false);
        MessageAdapter.ViewHolder chatcard=new MessageAdapter.ViewHolder(view);

        return chatcard;
            }
        else{
            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item_left,parent,false);
            MessageAdapter.ViewHolder chatcard=new MessageAdapter.ViewHolder(view);

            return chatcard;
        }

        }

    @Override
    public void onBindViewHolder(@NonNull final MessageAdapter.ViewHolder holder, final int position) {



        // holder.username.setText(user.getUserName());
        //if(user.getImage().equals("default"))
        //{
        //    holder.profile_image.setImageResource(R.mipmap.ic_launcher);
        //} else {


        Chat chat=mChats.get(position);
        holder.show_message.setText(chat.getMessage());

        if(imageurl.equals("default")){

            holder.profile_image.setImageResource(R.mipmap.ic_launcher);
        }
        else{
            Glide.with(mcontext).load(imageurl).into(holder.profile_image);
        }




    }

    @Override
    public int getItemCount() {
        return mChats.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{




        TextView show_message;
        CircleImageView profile_image;
        RelativeLayout ChatCardParent;
        public ViewHolder(View itemView){
            super(itemView);

            show_message=itemView.findViewById(R.id.show_message);
            profile_image=itemView.findViewById(R.id.profilemage);
            ChatCardParent=itemView.findViewById(R.id.ChartCardParent);



        }
    }

    @Override
    public int getItemViewType(int position) {
        fuser=FirebaseAuth.getInstance().getCurrentUser();

        if(mChats.get(position).getSender().equals(fuser.getPhoneNumber())){
            return msg_type_right;
        }
        else{
            return msg_type_left;
        }
    }
}
