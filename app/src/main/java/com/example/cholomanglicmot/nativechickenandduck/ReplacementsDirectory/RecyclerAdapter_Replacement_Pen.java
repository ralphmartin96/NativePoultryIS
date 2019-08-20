package com.example.cholomanglicmot.nativechickenandduck.ReplacementsDirectory;

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

public class RecyclerAdapter_Replacement_Pen extends RecyclerView.Adapter<RecyclerAdapter_Replacement_Pen.RecyclerViewHolder> {

    ArrayList<Replacement_Pen> arrayList = new ArrayList<>();

    //Map<String, ArrayList<String>> brooder_inventory_dictionary = new HashMap<String, ArrayList<String>>();



    RecyclerAdapter_Replacement_Pen(ArrayList<Replacement_Pen> arrayList){
        this.arrayList = arrayList;

        this.context = context;



    }

    Context context;

    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.replacement_row_layout,parent, false);
        context = parent.getContext();
        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(view);
        //notifyDataSetChanged();
        return recyclerViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, final int position) {
        //notifyDataSetChanged();
        final Replacement_Pen replacementPen = arrayList.get(position);
        //final Brooder_Inventory replacement_inventory = arrayList2.get(position);



        final Bundle args = new Bundle();
        args.putString("Replacement Pen", replacementPen.getReplacement_pen_number());






        holder.replacement_pen_number.setText(replacementPen.getReplacement_pen_number());
        holder.replacement_pen_content.setText(replacementPen.getReplacement_pen_content().toString());
        holder.replacement_pen_free.setText(replacementPen.getReplacement_pen_free().toString());


        holder.replacement_inventory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent_replacement_inventory = new Intent(context, ReplacementInventoryActivity.class);
                intent_replacement_inventory.putExtra("Replacement Pen",replacementPen.getReplacement_pen_number());
                context.startActivity(intent_replacement_inventory);


            }
        });



        holder.replacement_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentActivity activity = (FragmentActivity)(context);
                FragmentManager fm = activity.getSupportFragmentManager();
                CreateReplacementDialog alertDialog = new CreateReplacementDialog();
                alertDialog.setArguments(args);
                alertDialog.show(fm, "CreateReplacementDialog");
                notifyDataSetChanged();


            }
        });

        holder.replacement_feeding_records.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_replacement_feeding_records = new Intent(context, ReplacementFeedingRecordsActivity.class);
                intent_replacement_feeding_records.putExtra("Replacement Pen",replacementPen.getReplacement_pen_number());
                context.startActivity(intent_replacement_feeding_records);

            }
        });

        holder.replacement_pheno_morpho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_replacement_pheno_morpho_records = new Intent(context, ReplacementPhenoMorphoRecordsActivity.class);
                intent_replacement_pheno_morpho_records.putExtra("Replacement Pen",replacementPen.getReplacement_pen_number());
                context.startActivity(intent_replacement_pheno_morpho_records);

            }
        });

        holder.replacement_growth_records.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_replacement_growth_records = new Intent(context, ReplacementGrowthRecordsActivity.class);
                intent_replacement_growth_records.putExtra("Replacement Pen",replacementPen.getReplacement_pen_number());
                context.startActivity(intent_replacement_growth_records);

            }
        });


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }



    public static class RecyclerViewHolder extends RecyclerView.ViewHolder{
        TextView replacement_pen_number;
        TextView replacement_pen_content;
        TextView replacement_pen_free;
        HorizontalScrollView brooder_inventory_layout;
        ImageButton replacement_add;
        ImageButton replacement_inventory;
        ImageButton replacement_feeding_records;
        ImageButton replacement_growth_records;
        ImageButton replacement_pheno_morpho;
        RecyclerView recyclerView;


        RecyclerViewHolder(View view){
            super(view);
            replacement_pen_number = view.findViewById(R.id.replacement_pen_number);
            replacement_pen_content = view.findViewById(R.id.replacement_pen_content);
            replacement_pen_free = view.findViewById(R.id.replacement_pen_free);
            replacement_add = view.findViewById(R.id.replacement_add);
            replacement_inventory = view.findViewById(R.id.replacement_inventory);
            replacement_feeding_records = view.findViewById(R.id.replacement_feeding_records);
            replacement_pheno_morpho = view.findViewById(R.id.replacement_pheno_morpho);
            replacement_growth_records = view.findViewById(R.id.replacement_growth_records);





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
