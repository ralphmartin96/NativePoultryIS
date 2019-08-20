package com.example.cholomanglicmot.nativechickenandduck.BreedersDirectory;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.cholomanglicmot.nativechickenandduck.BroodersDirectory.Brooder_FeedingRecords;
import com.example.cholomanglicmot.nativechickenandduck.R;

import java.util.ArrayList;

public class RecyclerAdapter_Hatchery_Record extends RecyclerView.Adapter<RecyclerAdapter_Hatchery_Record.RecyclerViewHolder> {


    ArrayList<Hatchery_Records> arrayListBrooderFeedingRecords = new ArrayList<>();

    RecyclerAdapter_Hatchery_Record(ArrayList<Hatchery_Records> arrayListBrooderFeedingRecords  ){
        this.arrayListBrooderFeedingRecords = arrayListBrooderFeedingRecords;
        this.context = context;

    }

    Context context;


    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hatchery_row_layout,parent, false);
        context = parent.getContext();
        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(view);



        return recyclerViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, final int position) {

        final Hatchery_Records hatchery_records = arrayListBrooderFeedingRecords.get(position);
        final Bundle args = new Bundle();
        args.putInt("Breeder Inventory ID", hatchery_records.getBreeder_inv_id());
        args.putInt("Breeder Hatchery ID", hatchery_records.getId());
        args.putString("Breeder Tag", hatchery_records.getTag());

        holder.date_set.setText(hatchery_records.getDate());
       /* holder.quantity.setText(hatchery_records.getEggs_set().toString());
        holder.age.setText("ID "+hatchery_records.getId().toString());
        holder.fertile.setText(hatchery_records.getFertile().toString());*/
       if(hatchery_records.getDate_hatched() != null){
           holder.hatched.setText(hatchery_records.getDate_hatched());
       }

       // holder.number_hatched.setText(hatchery_records.getHatched().toString());
        holder.view_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentActivity activity = (FragmentActivity)(context);
                FragmentManager fm = activity.getSupportFragmentManager();
                ViewBreederHatcheryRecordDialog alertDialog = new ViewBreederHatcheryRecordDialog();
                alertDialog.setArguments(args);
                alertDialog.show(fm, "ViewBreederHatcheryRecordDialog");
                notifyDataSetChanged();
            }
        });

        holder.deleted_at.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentActivity activity = (FragmentActivity)(context);
                FragmentManager fm = activity.getSupportFragmentManager();
                DeleteHatchery alertDialog = new DeleteHatchery();
                alertDialog.setArguments(args);
                alertDialog.show(fm, "DeleteHatchery");
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayListBrooderFeedingRecords.size();
    }



    public static class RecyclerViewHolder extends RecyclerView.ViewHolder{
        TextView date_set,quantity,age,fertile,hatched,number_hatched;
        ImageButton update, view_record;
        ImageButton deleted_at;



        RecyclerViewHolder(View view){
            super(view);
            date_set = view.findViewById(R.id.date_set);
           /* quantity = view.findViewById(R.id.quantity);
            age = view.findViewById(R.id.age);
            fertile = view.findViewById(R.id.fertile);*/
            hatched = view.findViewById(R.id.hatched);
            view_record = view.findViewById(R.id.view_record);

            date_set = view.findViewById(R.id.date_set);

            deleted_at = view.findViewById(R.id.delete);





        }


    }
    public void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
    public void updateArray(ArrayList<Brooder_FeedingRecords> data)
    {
        data.clear();
        data.addAll(data);
        notifyDataSetChanged();
    }


}
