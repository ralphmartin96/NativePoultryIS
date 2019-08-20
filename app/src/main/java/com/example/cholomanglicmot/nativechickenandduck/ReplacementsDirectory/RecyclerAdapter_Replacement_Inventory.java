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
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.cholomanglicmot.nativechickenandduck.BroodersDirectory.Brooders;
import com.example.cholomanglicmot.nativechickenandduck.DatabaseHelper;
import com.example.cholomanglicmot.nativechickenandduck.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RecyclerAdapter_Replacement_Inventory extends RecyclerView.Adapter<RecyclerAdapter_Replacement_Inventory.RecyclerViewHolder> {

    ArrayList<Replacement_Inventory> arrayListInventory = new ArrayList<>();
    ArrayList<Brooders> arrayListBrooder = new ArrayList<>();
    DatabaseHelper myDb;
    Map<String, ArrayList<String>> brooder_inventory_dictionary = new HashMap<String, ArrayList<String>>();
    ArrayList<Brooders> arrayListBrooder2 = new ArrayList<>();
    //Integer position_pen_number;
    ArrayList<Replacement_Inventory> arrayListReplacementInventory = new ArrayList<>();

    String brooder_pen_number;




    RecyclerAdapter_Replacement_Inventory(ArrayList<Replacement_Inventory> arrayListReplacementInventory){

        this.arrayListReplacementInventory = arrayListReplacementInventory;
        this.context = context;
       // this.brooder_inventory_dictionary = brooder_inventory_dictionary;


    }

    Context context;

    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.replacement_inventory_row_layout,parent, false);
        context = parent.getContext();

        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(view);


        return recyclerViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, final int position) {


        final Replacement_Inventory replacement_inventory = arrayListReplacementInventory.get(position);
        final Bundle args = new Bundle();
        args.putString("Replacement Tag", replacement_inventory.getReplacement_inv_replacement_tag());
        args.putInt("Replacement Pen", replacement_inventory.getReplacement_inv_pen());
        args.putInt("Replacement ID", replacement_inventory.getReplacement_inv_replacement_id());






        holder.replacement_inventory_code.setText(replacement_inventory.getReplacement_inv_replacement_tag());
     /*   holder.replacement_inventory_family.setText(replacement_inventory.getReplacement_inv_family_number());
        holder.replacement_inventory_line.setText(replacement_inventory.getReplacement_inv_line_number());
        holder.replacement_inventory_gen.setText(replacement_inventory.getReplacement_inv_generation_number());*/
        holder.replacement_inventory_batch_date.setText(replacement_inventory.getReplacement_inv_batching_date());
      /*  holder.replacement_inventory_number_male.setText(replacement_inventory.getReplacement_male_quantity().toString());
        holder.replacement_inventory_number_female.setText(replacement_inventory.getReplacement_female_quantity().toString());
        holder.replacement_inventory_total.setText(replacement_inventory.getReplacement_total_quantity().toString());*/
     /*   holder.replacement_inventory_date_added.setText(replacement_inventory.getReplacement_date_added());*/
        //holder.replacement_inventory_last_update.setText(replacement_inventory.getReplacement_inv_last_update());
        holder.replacement_inventory_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentActivity activity = (FragmentActivity)(context);
                FragmentManager fm = activity.getSupportFragmentManager();
                ViewReplacementInventoryDialog alertDialog = new ViewReplacementInventoryDialog();
                alertDialog.setArguments(args);
                alertDialog.show(fm, "ViewReplacementInventoryDialog");
                notifyDataSetChanged();

            }
        });
        holder.replacement_inventory_mort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_replacement_pheno_morpho_records = new Intent(context, MortalityAndSalesRecordsReplacement.class);
                intent_replacement_pheno_morpho_records.putExtra("Brooder Pen ID",replacement_inventory.getReplacement_inv_pen());
                intent_replacement_pheno_morpho_records.putExtra("Brooder Tag",replacement_inventory.getReplacement_inv_replacement_tag());
                intent_replacement_pheno_morpho_records.putExtra("Brooder ID",replacement_inventory.getReplacement_inv_replacement_id());
                context.startActivity(intent_replacement_pheno_morpho_records);
            }
        });
        holder.replacement_inventory_cull.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentActivity activity = (FragmentActivity)(context);
                FragmentManager fm = activity.getSupportFragmentManager();
                CullReplacementInventoryDialog alertDialog = new CullReplacementInventoryDialog();
                alertDialog.setArguments(args);
                alertDialog.show(fm, "CullReplacementInventoryDialog");
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
        TextView replacement_inventory_family;
        TextView replacement_inventory_line;
        TextView replacement_inventory_gen;
        TextView replacement_inventory_batch_date;

        TextView replacement_inventory_date_added;
        TextView replacement_inventory_last_update;
        ImageButton replacement_inventory_view;
        ImageButton replacement_inventory_cull;
        ImageButton replacement_inventory_mort;

        TextView replacement_inventory_number_male;
        TextView replacement_inventory_number_female;
        TextView replacement_inventory_total;

        RecyclerViewHolder(View view){
            super(view);
            replacement_inventory_code = view.findViewById(R.id.replacement_inventory_code);
           /* replacement_inventory_family = view.findViewById(R.id.replacement_inventory_family);
            replacement_inventory_line = view.findViewById(R.id.replacement_inventory_line);
            replacement_inventory_gen = view.findViewById(R.id.replacement_inventory_gen);*/
            replacement_inventory_batch_date = view.findViewById(R.id.replacement_inventory_batch_date);;
         /*   replacement_inventory_date_added = view.findViewById(R.id.replacement_inventory_date_added);;
            replacement_inventory_last_update = view.findViewById(R.id.replacement_inventory_last_update);;*/
            replacement_inventory_mort = view.findViewById(R.id.replacement_inventory_mort);;
            replacement_inventory_cull = view.findViewById(R.id.replacement_inventory_cull);;
            replacement_inventory_view = view.findViewById(R.id.replacement_inventory_view);;

           /* replacement_inventory_number_male = view.findViewById(R.id.replacement_inventory_number_male);;
            replacement_inventory_number_female = view.findViewById(R.id.replacement_inventory_number_female);;*/
           //replacement_inventory_total = view.findViewById(R.id.replacement_inventory_total);;


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
