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

import com.example.cholomanglicmot.nativechickenandduck.BroodersDirectory.Brooders;
import com.example.cholomanglicmot.nativechickenandduck.DatabaseHelper;
import com.example.cholomanglicmot.nativechickenandduck.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RecyclerAdapter_Breeder_PhenoMorpho extends RecyclerView.Adapter<RecyclerAdapter_Breeder_PhenoMorpho.RecyclerViewHolder> {

    DatabaseHelper myDb;
    ArrayList<Breeder_Inventory> arrayListReplacementInventory = new ArrayList<>();

    RecyclerAdapter_Breeder_PhenoMorpho(ArrayList<Breeder_Inventory> arrayListReplacementInventory){

        this.arrayListReplacementInventory = arrayListReplacementInventory;
        this.context = context;

    }

    Context context;

    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.replacement_phenomorpho_row_layout,parent, false);
        context = parent.getContext();

        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(view);


        return recyclerViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, final int position) {

        final Breeder_Inventory replacement_inventory = arrayListReplacementInventory.get(position);

        ///pass replacement tag to args
        final Bundle args = new Bundle();
        args.putInt("Replacement Pen", replacement_inventory.getBreeder_inv_pen());

        args.putString("Replacement Tag", replacement_inventory.getBreeder_inv_breeder_tag());

        holder.replacement_inventory_code.setText(replacement_inventory.getBreeder_inv_breeder_tag());
        holder.replacement_inventory_number_male.setText(replacement_inventory.getBreeder_male_quantity().toString());
        holder.replacement_inventory_number_female.setText(replacement_inventory.getBreeder_female_quantity().toString());
        holder.replacement_inventory_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_replacement_inventory_pheno_morpho = new Intent(context, BreederPhenoMorphoViewActivity.class);
                intent_replacement_inventory_pheno_morpho.putExtra("Replacement Tag",replacement_inventory.getBreeder_inv_breeder_tag());
                intent_replacement_inventory_pheno_morpho.putExtra("Breeder Inv ID",replacement_inventory.getBreeder_inv_breeder_id());
                intent_replacement_inventory_pheno_morpho.putExtra("Replacement Pen",replacement_inventory.getBreeder_inv_pen());
                context.startActivity(intent_replacement_inventory_pheno_morpho);

            }
        });

        holder.replacement_inventory_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //DIALOG

                FragmentActivity activity = (FragmentActivity)(context);
                FragmentManager fm = activity.getSupportFragmentManager();
                CreatePhenoMorphoDialogBreeder alertDialog = new CreatePhenoMorphoDialogBreeder();
                alertDialog.setArguments(args);
                alertDialog.show(fm, "CreatePhenoMorphoDialog");
                notifyDataSetChanged();


            }
        });

    }


    @Override
    public int getItemCount() {
        return arrayListReplacementInventory.size();
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder{
        TextView replacement_inventory_code;

        TextView replacement_inventory_number_male;
        TextView replacement_inventory_number_female;
        ImageButton replacement_inventory_view;
        ImageButton replacement_inventory_add;

        RecyclerViewHolder(View view){
            super(view);
            replacement_inventory_code = view.findViewById(R.id.replacement_inventory_code);

            replacement_inventory_number_male = view.findViewById(R.id.replacement_inventory_number_male);;
            replacement_inventory_number_female = view.findViewById(R.id.replacement_inventory_number_female);;
            replacement_inventory_view = view.findViewById(R.id.replacement_inventory_view);

            replacement_inventory_add = view.findViewById(R.id.replacement_inventory_add);

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
