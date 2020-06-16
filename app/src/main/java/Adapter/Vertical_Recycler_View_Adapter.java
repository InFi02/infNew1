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

import java.util.ArrayList;

import Models.HorizontalModel;
import Models.VerticalModel;

public class Vertical_Recycler_View_Adapter extends RecyclerView.Adapter<Vertical_Recycler_View_Adapter.ViewHolder> {


    Context context;
    ArrayList<VerticalModel>  arrayList;

    public Vertical_Recycler_View_Adapter(Context context, ArrayList<VerticalModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_vertical,parent,false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        VerticalModel verticalModel=arrayList.get(position);
        String title=verticalModel.getTitle();
        ArrayList<HorizontalModel> singleItem=verticalModel.getArrayList();

        holder.interest.setText(title);
        Horizontal_Recyler_View Adapter= new Horizontal_Recyler_View(context,singleItem);
        holder.recyclerView.setHasFixedSize(true);
        holder.recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false));

        holder.recyclerView.setAdapter(Adapter);


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
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





}
