package com.example.cholomanglicmot.nativechickenandduck.BroodersDirectory;

import android.app.AlertDialog;
import android.content.Context;
import android.database.Cursor;
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

import com.example.cholomanglicmot.nativechickenandduck.DatabaseHelper;
import com.example.cholomanglicmot.nativechickenandduck.R;

import java.util.ArrayList;

public class RecyclerAdapter_Brooder_Feeding extends RecyclerView.Adapter<RecyclerAdapter_Brooder_Feeding.RecyclerViewHolder> {


    ArrayList<Brooder_FeedingRecords> arrayListBrooderFeedingRecords = new ArrayList<>();

    RecyclerAdapter_Brooder_Feeding(ArrayList<Brooder_FeedingRecords> arrayListBrooderFeedingRecords  ){
        this.arrayListBrooderFeedingRecords = arrayListBrooderFeedingRecords;
        this.context = context;

    }

    Context context;


    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.brooder_feeding_row_layout,parent, false);
        context = parent.getContext();
        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(view);

        return recyclerViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, final int position) {

        final Brooder_FeedingRecords brooder_feedingRecords = arrayListBrooderFeedingRecords.get(position);
        final Bundle args = new Bundle();
        DatabaseHelper myDb;
        myDb = new DatabaseHelper(context);
        String tag=null;
        String brooder_tag=null;





        args.putInt("Brooder Inventory ID", brooder_feedingRecords.getBrooder_feeding_inventory_id());
        //args.putString("Brooder Tag", brooder_feedingRecords.getBrooder_tag());
        args.putInt("Brooder Feeding ID", brooder_feedingRecords.getId());
        args.putInt("Feeding ID", brooder_feedingRecords.getId());

        Cursor cursor = myDb.getAllDataFromBrooderInventoryWhereID(brooder_feedingRecords.getBrooder_feeding_inventory_id());
        cursor.moveToFirst();
        if(cursor.getCount() != 0){
            brooder_tag = cursor.getString(3);
        }

        holder.brooder_feeding_date.setText(brooder_feedingRecords.getBrooder_feeding_date_collected());
        holder.brooder_feeding_tag.setText(brooder_tag);

        holder.view_feeding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentActivity activity = (FragmentActivity)(context);
                FragmentManager fm = activity.getSupportFragmentManager();
                ViewBrooderFeedingDialog alertDialog = new ViewBrooderFeedingDialog();
                alertDialog.setArguments(args);
                alertDialog.show(fm, "CreateBrooderDialog");
                notifyDataSetChanged();
            }
        });
        holder.brooder_feeding_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentActivity activity = (FragmentActivity)(context);
                FragmentManager fm = activity.getSupportFragmentManager();
                DeleteFeedingDialog alertDialog = new DeleteFeedingDialog();
                alertDialog.setArguments(args);
                alertDialog.show(fm, "DeleteFeedingDialog");
                notifyDataSetChanged();
            }
        });


    }

    @Override
    public int getItemCount() {
        return arrayListBrooderFeedingRecords.size();
    }



    public static class RecyclerViewHolder extends RecyclerView.ViewHolder{
        TextView brooder_feeding_date;
        TextView brooder_feeding_tag;
        TextView brooder_feeding_offered;
        TextView brooder_feeding_refused;
        TextView brooder_feeding_remarks;
        ImageButton view_feeding;

        ImageButton brooder_feeding_delete;

        RecyclerViewHolder(View view){
            super(view);
            brooder_feeding_date = view.findViewById(R.id.brooder_feeding_date);
            brooder_feeding_tag = view.findViewById(R.id.brooder_feeding_tag);
            brooder_feeding_offered = view.findViewById(R.id.brooder_feeding_offered);
            view_feeding = view.findViewById(R.id.view_feeding);
            //brooder_feeding_refused= view.findViewById(R.id.brooder_feeding_refused);
            //brooder_feeding_remarks= view.findViewById(R.id.brooder_feeding_remarks);
            brooder_feeding_delete = view.findViewById(R.id.brooder_feeding_delete);


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
