package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.infi_project.R;

import java.util.ArrayList;
import java.util.List;

import Models.Post;


public class Profile_Highlight_Adapter extends  RecyclerView.Adapter<Profile_Highlight_Adapter.ViewHolder> {

   private Context context;
  private List<Post> mPosts;

  
  



    public Profile_Highlight_Adapter(Context context, List<Post> mPosts) {
        this.context = context;
        this.mPosts = mPosts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.photos_item,viewGroup,false);
        return new ViewHolder(view);
}
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {

        Post post=mPosts.get(i);


        Glide.with(context)
                .asBitmap()
                .load(post.getPostimage())
                .into(holder.post_image);



   }

    @Override
    public int getItemCount() {
        return mPosts.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder{


        public  ImageView post_image;

        public ViewHolder(@NonNull View itemView){
            super(itemView);

            post_image=itemView.findViewById(R.id.post_image);
        }
    }

}
