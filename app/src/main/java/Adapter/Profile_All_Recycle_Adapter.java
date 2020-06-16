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

import java.util.ArrayList;

public class Profile_All_Recycle_Adapter extends RecyclerView.Adapter<Profile_All_Recycle_Adapter.ViewHolder> {


    private static final String TAG = "Profile_All_Recycler";
    private ArrayList<String> mTitles=new ArrayList<>();
    private ArrayList<String> mImageUrls= new ArrayList<>();
    private Context mContext;

    public Profile_All_Recycle_Adapter(Context mContext,ArrayList<String> mTitles, ArrayList<String> mImageUrls) {
        this.mTitles = mTitles;
        this.mImageUrls = mImageUrls;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG,"onCreateViewHolder:called");
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list,parent,false);

        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.d(TAG,"onBindViewHolder:called");

        Glide.with(mContext)
                .asBitmap()
                .load(mImageUrls.get(position))
                .into(holder.post);

        holder.title.setText(mTitles.get(position));

        holder.post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"clicked on an Image");
                Toast.makeText(mContext,mTitles.get(position),Toast.LENGTH_SHORT).show();

            }
        });


    }

    @Override
    public int getItemCount() {
        return mImageUrls.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView post;
        TextView title;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            post=itemView.findViewById(R.id.imagecard);
            title=itemView.findViewById(R.id.titleprofile);

        }
    }
}
