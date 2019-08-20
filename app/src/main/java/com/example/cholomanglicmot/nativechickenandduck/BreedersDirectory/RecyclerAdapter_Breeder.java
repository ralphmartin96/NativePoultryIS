package com.example.cholomanglicmot.nativechickenandduck.BreedersDirectory;

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
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.cholomanglicmot.nativechickenandduck.DatabaseHelper;
import com.example.cholomanglicmot.nativechickenandduck.R;

import java.util.ArrayList;

public class RecyclerAdapter_Breeder extends RecyclerView.Adapter<RecyclerAdapter_Breeder.RecyclerViewHolder> {

    ArrayList<Breeder_Inventory> arrayList = new ArrayList<>();

    RecyclerAdapter_Breeder(ArrayList<Breeder_Inventory> arrayList){
        this.arrayList = arrayList;


    }

    Context context;

    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.breeder_row_layout,parent, false);
        context = parent.getContext();

        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(view);

        return recyclerViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, final int position) {

        final Breeder_Inventory breeders = arrayList.get(position);

        final Bundle args = new Bundle();
        args.putString("Breeder Tag", breeders.getBrooder_inv_brooder_tag());
        args.putInt("Breeder ID", breeders.getId());
        args.putInt("Breeder Pen ID", breeders.getBrooder_inv_pen());
        DatabaseHelper myDb;
        myDb = new DatabaseHelper(context);

        Integer brooder_id = arrayList.get(position).getBrooder_inv_brooder_id();

        Integer fam_id = myDb.getFamIDFromBreeders(brooder_id);
        String famLineGen = myDb.getFamLineGen(fam_id);
        String delims = " ";
        try{
            String[] tokens = famLineGen.split(delims);
            String fam = tokens[0];
            String line = tokens[1];
            String gen = tokens[2];

            holder.breeder_family.setText(fam);
            holder.breeder_line.setText(line);
            holder.breeder_generation.setText(gen);
        }catch (Exception e){}
            holder.breeder_number.setText(breeders.getBrooder_inv_brooder_tag());

            holder.breeder_male_count.setText(breeders.getBrooder_male_quantity().toString());
            holder.breeder_female_count.setText(breeders.getBrooder_female_quantity().toString());
            holder.breeder_total_count.setText(breeders.getBrooder_total_quantity().toString());

            holder.breeder_batching_date.setText(breeders.getBrooder_inv_batching_date());

            holder.add_breeders.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //createdialog
                    FragmentActivity activity = (FragmentActivity)(context);
                    FragmentManager fm = activity.getSupportFragmentManager();
                    AddBreederDialog alertDialog = new AddBreederDialog();
                    alertDialog.setArguments(args);
                    alertDialog.show(fm, "AddBreederDialog");
                    notifyDataSetChanged();

                }
            });

            holder.open_feeding_records.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //create
                    Intent intent_replacement_feeding_records = new Intent(context, BreederFeedingRecordsActivity.class);
                    intent_replacement_feeding_records.putExtra("Breeder Pen",breeders.getBrooder_inv_pen());
                    intent_replacement_feeding_records.putExtra("Breeder Tag",breeders.getBrooder_inv_brooder_tag());
                    intent_replacement_feeding_records.putExtra("Breeder Inventory ID",breeders.getId());
                    context.startActivity(intent_replacement_feeding_records);
                }
            });
            holder.open_egg_production_records.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //create
                    Intent intent_replacement_pheno_morpho_records = new Intent(context, EggProductionRecords.class);
                    intent_replacement_pheno_morpho_records.putExtra("Replacement Pen",breeders.getBrooder_inv_pen());
                    intent_replacement_pheno_morpho_records.putExtra("Breeder Tag",breeders.getBrooder_inv_brooder_tag());
                    intent_replacement_pheno_morpho_records.putExtra("Breeder Inventory ID",breeders.getId());
                    context.startActivity(intent_replacement_pheno_morpho_records);
                }
            });
            holder.open_hatchery_records.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //create
                    Intent intent_replacement_pheno_morpho_records = new Intent(context, HatcheryRecords.class);
                    intent_replacement_pheno_morpho_records.putExtra("Replacement Pen",breeders.getBrooder_inv_pen());
                    intent_replacement_pheno_morpho_records.putExtra("Breeder Tag",breeders.getBrooder_inv_brooder_tag());
                    context.startActivity(intent_replacement_pheno_morpho_records);
                }
            });
            holder.open_egg_quality_records.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //create
                    Intent intent_replacement_pheno_morpho_records = new Intent(context, EggQualityRecords.class);
                    intent_replacement_pheno_morpho_records.putExtra("Replacement Pen",breeders.getBrooder_inv_pen());
                    intent_replacement_pheno_morpho_records.putExtra("Breeder Tag",breeders.getBrooder_inv_brooder_tag());
                    context.startActivity(intent_replacement_pheno_morpho_records);
                }
            });
            holder.open_pheno_morpho_records.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //create
                    Intent intent_replacement_pheno_morpho_records = new Intent(context, BreederPhenoMorphoRecordsActivity.class);
                    intent_replacement_pheno_morpho_records.putExtra("Replacement Pen",breeders.getBrooder_inv_pen());
                    intent_replacement_pheno_morpho_records.putExtra("Breeder Tag",breeders.getBrooder_inv_brooder_tag());
                    context.startActivity(intent_replacement_pheno_morpho_records);
                }
            });
            holder.open_mortality_and_sales.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent_replacement_pheno_morpho_records = new Intent(context, MortalityAndSalesRecords.class);
                    intent_replacement_pheno_morpho_records.putExtra("Replacement Pen",breeders.getBrooder_inv_pen());
                    intent_replacement_pheno_morpho_records.putExtra("Breeder Tag",breeders.getBrooder_inv_brooder_tag());
                    intent_replacement_pheno_morpho_records.putExtra("Breeder ID",breeders.getBrooder_inv_brooder_id());
                    context.startActivity(intent_replacement_pheno_morpho_records);
                }
            });
            holder.cull_breeder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FragmentActivity activity = (FragmentActivity)(context);
                    FragmentManager fm = activity.getSupportFragmentManager();
                    CullBreederInventoryDialog alertDialog = new CullBreederInventoryDialog();
                    alertDialog.setArguments(args);
                    alertDialog.show(fm, "CullBreederInventoryDialog");
                    notifyDataSetChanged();
                }
            });
      //  }catch (Exception e){}


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }



    public static class RecyclerViewHolder extends RecyclerView.ViewHolder{
        TextView breeder_number;
        TextView breeder_family;
        TextView breeder_line;
        TextView breeder_generation;
        TextView breeder_male_count;
        TextView breeder_female_count;
        TextView breeder_total_count;
        TextView breeder_batching_date;

        ImageButton add_breeders;
        ImageButton open_feeding_records;
        ImageButton open_egg_production_records;
        ImageButton open_hatchery_records;
        ImageButton open_egg_quality_records;
        ImageButton open_pheno_morpho_records;
        ImageButton open_mortality_and_sales;
        ImageButton cull_breeder;


        RecyclerViewHolder(View view){
            super(view);
            breeder_number = view.findViewById(R.id.breeder_number);
            breeder_family = view.findViewById(R.id.breeder_family);
            breeder_line = view.findViewById(R.id.breeder_line);
            breeder_generation =  view.findViewById(R.id.breeder_generation);
            breeder_male_count =  view.findViewById(R.id.breeder_male_count);
            breeder_female_count =  view.findViewById(R.id.breeder_female_count);
            breeder_total_count =  view.findViewById(R.id.breeder_total_count);
            breeder_batching_date =  view.findViewById(R.id.breeder_batching_date);
            add_breeders =  view.findViewById(R.id.add_breeders);
            open_feeding_records =  view.findViewById(R.id.open_feeding_records);
            open_egg_production_records =  view.findViewById(R.id.open_egg_production_records);
            open_hatchery_records =  view.findViewById(R.id.open_hatchery_records);
            open_egg_quality_records =  view.findViewById(R.id.open_egg_quality_records);
            open_pheno_morpho_records =  view.findViewById(R.id.open_pheno_morpho_records);
            open_mortality_and_sales =  view.findViewById(R.id.open_mortality_and_sales);
            cull_breeder =  view.findViewById(R.id.cull_breeder);

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
