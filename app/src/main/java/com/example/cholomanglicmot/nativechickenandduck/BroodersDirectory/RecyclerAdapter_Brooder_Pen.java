package com.example.cholomanglicmot.nativechickenandduck.BroodersDirectory;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.cholomanglicmot.nativechickenandduck.R;

import java.util.ArrayList;

public class RecyclerAdapter_Brooder_Pen extends RecyclerView.Adapter<RecyclerAdapter_Brooder_Pen.RecyclerViewHolder> {

    ArrayList<Brooders_Pen> arrayList = new ArrayList<>();

    //Map<String, ArrayList<String>> brooder_inventory_dictionary = new HashMap<String, ArrayList<String>>();


    RecyclerView recyclerView;
    RecyclerView.Adapter recycler_adapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Brooder_Inventory> arrayList2 = new ArrayList<>();
    ArrayList<Brooder_Inventory> arrayListInventory = new ArrayList<>();
    ArrayList<Brooders> arrayListBrooders = new ArrayList<>();
    RecyclerAdapter_Brooder_Pen(ArrayList<Brooders_Pen> arrayList/*, ArrayList<Brooder_Inventory> arrayList2,ArrayList<Brooders> arrayListBrooders*/){
        this.arrayList = arrayList;
      /*  this.arrayList2 = arrayList2;
        this.arrayListBrooders = arrayListBrooders;*/
        this.context = context;



    }

    Context context;

    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.brooder_row_layout,parent, false);
        context = parent.getContext();
        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(view);
        //notifyDataSetChanged();
        return recyclerViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, final int position) {
        //notifyDataSetChanged();
        final Brooders_Pen broodersPen = arrayList.get(position);
        //final Brooder_Inventory brooder_inventory = arrayList2.get(position);


        final RecyclerViewHolder holder2 = holder;
        final Bundle args = new Bundle();
        args.putString("brooder_pen", broodersPen.getBrooder_pen_number());






        holder.brooder_pen_number.setText(broodersPen.getBrooder_pen_number());
        holder.brooder_pen_content.setText(broodersPen.getBrooder_pen_content().toString());
        holder.brooder_pen_free.setText(broodersPen.getBrooder_pen_free().toString());


        holder.brooder_inventory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



      //INTENT NALANG

                Intent intent_brooder_inventory = new Intent(context, BrooderInventoryActivity.class);
                intent_brooder_inventory.putExtra("Brooder Pen",broodersPen.getBrooder_pen_number());
                context.startActivity(intent_brooder_inventory);



            }
        });



        holder.brooder_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentActivity activity = (FragmentActivity)(context);
                FragmentManager fm = activity.getSupportFragmentManager();
                CreateBrooderDialog alertDialog = new CreateBrooderDialog();
                alertDialog.setArguments(args);
                alertDialog.show(fm, "CreateBrooderDialog");
                notifyDataSetChanged();


            }
        });

        holder.brooder_feeding_records.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_brooder_feeding_records = new Intent(context, BrooderFeedingRecordsActivity.class);
                intent_brooder_feeding_records.putExtra("Brooder Pen",broodersPen.getBrooder_pen_number());
                context.startActivity(intent_brooder_feeding_records);


            }
        });

        holder.brooder_growth_records.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_brooder_growth_records = new Intent(context, BrooderGrowthRecordsActivity.class);
                intent_brooder_growth_records.putExtra("Brooder Pen",broodersPen.getBrooder_pen_number());
                context.startActivity(intent_brooder_growth_records);

            }
        });


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }



    public static class RecyclerViewHolder extends RecyclerView.ViewHolder{
        TextView brooder_pen_number;
        TextView brooder_pen_content;
        TextView brooder_pen_free;
        HorizontalScrollView brooder_inventory_layout;
        ImageButton brooder_add;
        ImageButton brooder_inventory;
        ImageButton brooder_feeding_records;
        ImageButton brooder_growth_records;
        RecyclerView recyclerView;


        RecyclerViewHolder(View view){
            super(view);
            brooder_pen_number = view.findViewById(R.id.brooder_pen_number);
            brooder_pen_content = view.findViewById(R.id.brooder_pen_content);
            brooder_pen_free = view.findViewById(R.id.brooder_pen_free);
            brooder_add = view.findViewById(R.id.brooder_add);
            brooder_inventory = view.findViewById(R.id.brooder_inventory);
            brooder_feeding_records = view.findViewById(R.id.brooder_feeding_records);
            brooder_growth_records = view.findViewById(R.id.brooder_growth_records);





        }


    }
    public void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }


}
