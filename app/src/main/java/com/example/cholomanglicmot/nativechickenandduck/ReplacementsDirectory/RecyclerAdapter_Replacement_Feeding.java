package com.example.cholomanglicmot.nativechickenandduck.ReplacementsDirectory;

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

import com.example.cholomanglicmot.nativechickenandduck.BroodersDirectory.Brooder_FeedingRecords;
import com.example.cholomanglicmot.nativechickenandduck.DatabaseHelper;
import com.example.cholomanglicmot.nativechickenandduck.R;

import java.util.ArrayList;

public class RecyclerAdapter_Replacement_Feeding extends RecyclerView.Adapter<RecyclerAdapter_Replacement_Feeding.RecyclerViewHolder> {


    ArrayList<Replacement_FeedingRecords> arrayListBrooderFeedingRecords = new ArrayList<>();

    RecyclerAdapter_Replacement_Feeding(ArrayList<Replacement_FeedingRecords> arrayListBrooderFeedingRecords  ){
        this.arrayListBrooderFeedingRecords = arrayListBrooderFeedingRecords;
        this.context = context;

    }

    Context context;


    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.replacement_feeding_row_layout,parent, false);
        context = parent.getContext();
        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(view);

        return recyclerViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, final int position) {

        final Replacement_FeedingRecords brooder_feedingRecords = arrayListBrooderFeedingRecords.get(position);
        final Bundle args = new Bundle();
        DatabaseHelper myDb;
        myDb = new DatabaseHelper(context);
        String brooder_tag=null;
        args.putInt("Replacement Inventory ID", brooder_feedingRecords.getReplacement_feeding_inventory_id());
        //args.putString("Replacement Tag", brooder_feedingRecords.getReplacement_feeding_tag());
        args.putInt("Replacement Feeding ID", brooder_feedingRecords.getId());
        args.putInt("Feeding ID", brooder_feedingRecords.getId());

        Cursor cursor = myDb.getAllDataFromReplacementInventoryWhereID(brooder_feedingRecords.getReplacement_feeding_inventory_id());
        cursor.moveToFirst();
        if(cursor.getCount() != 0){
            brooder_tag = cursor.getString(3);
        }


        holder.brooder_feeding_date.setText(brooder_feedingRecords.getReplacement_feeding_date_collected());
        holder.brooder_feeding_tag.setText(brooder_tag);
        holder.brooder_feeding_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentActivity activity = (FragmentActivity)(context);
                FragmentManager fm = activity.getSupportFragmentManager();
                ViewReplacementFeedingDialog alertDialog = new ViewReplacementFeedingDialog();
                alertDialog.setArguments(args);
                alertDialog.show(fm, "ViewReplacementFeedingDialog");
                notifyDataSetChanged();
            }
        });
        holder.brooder_feeding_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentActivity activity = (FragmentActivity)(context);
                FragmentManager fm = activity.getSupportFragmentManager();
                DeleteFeedingDialogReplacement alertDialog = new DeleteFeedingDialogReplacement();
                alertDialog.setArguments(args);
                alertDialog.show(fm, "DeleteFeedingDialogReplacement");
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


        ImageButton brooder_feeding_delete;
        ImageButton brooder_feeding_view;

        RecyclerViewHolder(View view){
            super(view);
            brooder_feeding_date = view.findViewById(R.id.replacement_feeding_date);
            brooder_feeding_tag = view.findViewById(R.id.replacement_feeding_tag);

            brooder_feeding_delete = view.findViewById(R.id.replacement_feeding_delete);
            brooder_feeding_view = view.findViewById(R.id.replacement_feeding_view);


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
