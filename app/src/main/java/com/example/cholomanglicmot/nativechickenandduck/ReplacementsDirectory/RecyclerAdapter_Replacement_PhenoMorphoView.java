package com.example.cholomanglicmot.nativechickenandduck.ReplacementsDirectory;

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

import com.example.cholomanglicmot.nativechickenandduck.BroodersDirectory.Brooders;
import com.example.cholomanglicmot.nativechickenandduck.DatabaseHelper;
import com.example.cholomanglicmot.nativechickenandduck.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RecyclerAdapter_Replacement_PhenoMorphoView extends RecyclerView.Adapter<RecyclerAdapter_Replacement_PhenoMorphoView.RecyclerViewHolder> {

    ArrayList<Replacement_Inventory> arrayListInventory = new ArrayList<>();
    ArrayList<Brooders> arrayListBrooder = new ArrayList<>();
    DatabaseHelper myDb;
    Map<String, ArrayList<String>> brooder_inventory_dictionary = new HashMap<String, ArrayList<String>>();
    ArrayList<Brooders> arrayListBrooder2 = new ArrayList<>();
    //Integer position_pen_number;
    ArrayList<Replacement_PhenoMorphoView> arrayListReplacementInventory = new ArrayList<>();

    String brooder_pen_number;




    RecyclerAdapter_Replacement_PhenoMorphoView(ArrayList<Replacement_PhenoMorphoView> arrayListReplacementInventory){

        this.arrayListReplacementInventory = arrayListReplacementInventory;
        this.context = context;
       // this.brooder_inventory_dictionary = brooder_inventory_dictionary;


    }

    Context context;

    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.replacement_phenomorpho_view_row_layout,parent, false);
        context = parent.getContext();

        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(view);


        return recyclerViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, final int position) {


        final Replacement_PhenoMorphoView replacement_phenoMorphoView = arrayListReplacementInventory.get(position);
        final Bundle args = new Bundle();
        args.putInt("PhenoMorpho ID", replacement_phenoMorphoView.getId());

        args.putString("PhenoMorpho Tag", replacement_phenoMorphoView.getTag());
        args.putString("PhenoMorpho Gender", replacement_phenoMorphoView.getGender());
        args.putString("PhenoMorpho Pheno", replacement_phenoMorphoView.getPheno_record());
        args.putString("PhenoMorpho Morpho", replacement_phenoMorphoView.getMorpho_record());
        args.putString("PhenoMorpho Date", replacement_phenoMorphoView.getDate());


        holder.replacement_registry.setText(replacement_phenoMorphoView.getTag());
       /* holder.replacement_gender.setText(replacement_phenoMorphoView.getGender());

        holder.replacement_morpho.setText(replacement_phenoMorphoView.getMorpho_record());
        holder.replacement_pheno.setText(replacement_phenoMorphoView.getPheno_record());*/
        holder.replacement_date.setText(replacement_phenoMorphoView.getDate());
        holder.replacement_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentActivity activity = (FragmentActivity)(context);
                FragmentManager fm = activity.getSupportFragmentManager();
                CullPhenoMorphoDialog alertDialog = new CullPhenoMorphoDialog();
                alertDialog.setArguments(args);
                alertDialog.show(fm, "CullPhenoMorphoDialog");
                notifyDataSetChanged();
            }
        });
        holder.replacement_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentActivity activity = (FragmentActivity)(context);
                FragmentManager fm = activity.getSupportFragmentManager();
                ViewMorePhenoMorphoDialog alertDialog = new ViewMorePhenoMorphoDialog();
                alertDialog.setArguments(args);
                alertDialog.show(fm, "ViewMorePhenoMorphoDialog");
                notifyDataSetChanged();
            }
        });


    }


    @Override
    public int getItemCount() {
        return arrayListReplacementInventory.size();
    }



    public static class RecyclerViewHolder extends RecyclerView.ViewHolder{

        TextView replacement_registry;
        TextView replacement_gender;
        TextView replacement_pheno;
        TextView replacement_morpho;
        TextView replacement_date;
        ImageButton replacement_more;
        ImageButton replacement_delete;

        RecyclerViewHolder(View view){
            super(view);
            replacement_registry = view.findViewById(R.id.replacement_registry);
/*            replacement_gender = view.findViewById(R.id.replacement_gender);
            replacement_pheno = view.findViewById(R.id.replacement_pheno);
            replacement_morpho = view.findViewById(R.id.replacement_morpho);*/
            replacement_date = view.findViewById(R.id.replacement_date);

            replacement_delete = view.findViewById(R.id.replacement_delete);
            replacement_more = view.findViewById(R.id.replacement_more);
        /*    replacement_inventory_code = view.findViewById(R.id.replacement_inventory_code);


            replacement_inventory_number_male = view.findViewById(R.id.replacement_inventory_number_male);;
            replacement_inventory_number_female = view.findViewById(R.id.replacement_inventory_number_female);;
            replacement_inventory_view = view.findViewById(R.id.replacement_inventory_view);

            replacement_inventory_add = view.findViewById(R.id.replacement_inventory_add);*/






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
