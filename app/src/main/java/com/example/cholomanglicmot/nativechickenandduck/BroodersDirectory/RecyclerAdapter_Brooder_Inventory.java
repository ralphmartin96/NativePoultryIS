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
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.cholomanglicmot.nativechickenandduck.DatabaseHelper;
import com.example.cholomanglicmot.nativechickenandduck.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RecyclerAdapter_Brooder_Inventory extends RecyclerView.Adapter<RecyclerAdapter_Brooder_Inventory.RecyclerViewHolder> {

    ArrayList<Brooder_Inventory> arrayListInventory = new ArrayList<>();
    ArrayList<Brooders> arrayListBrooder = new ArrayList<>();
    DatabaseHelper myDb;
    //Map<String, ArrayList<String>> brooder_inventory_dictionary = new HashMap<String, ArrayList<String>>();
    ArrayList<Brooders> arrayListBrooder2 = new ArrayList<>();
    //Integer position_pen_number;
    ArrayList<Brooder_Inventory> arrayList_temp = new ArrayList<>();
    Map<Integer, ArrayList<Brooder_Inventory>> brooder_id_dictionary = new HashMap<Integer, ArrayList<Brooder_Inventory>>();
    String brooder_pen_number;


    RecyclerAdapter_Brooder_Inventory(ArrayList<Brooder_Inventory> arrayListInventory, String brooder_pen_number){
        this.brooder_pen_number = brooder_pen_number;
        this.arrayListInventory = arrayListInventory;

        this.context = context;
       // this.brooder_inventory_dictionary = brooder_inventory_dictionary;


    }

    Context context;

    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.brooder_inventory_row_layout,parent, false);
        context = parent.getContext();

        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(view);


        return recyclerViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, final int position) {


        final Brooder_Inventory brooder_inventory = arrayListInventory.get(position);
        final Bundle args = new Bundle();
        args.putString("Brooder Tag", brooder_inventory.getBrooder_inv_brooder_tag());
        args.putInt("Brooder Pen ID", brooder_inventory.getBrooder_inv_pen());
        args.putInt("Brooder ID", brooder_inventory.getBrooder_inv_brooder_id());







        holder.brooder_inventory_code.setText(brooder_inventory.getBrooder_inv_brooder_tag());
      /*  holder.brooder_inventory_code.setText(brooder_inventory.getBrooder_inv_brooder_id().toString());

*/

            holder.brooder_inventory_batch_date.setText(brooder_inventory.getBrooder_inv_batching_date());
            holder.brooder_view_inventory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //create dialog box
                    //tapos sa loob ng box na yun, nandun yung lahat ng details
                    //tapos dun mo pwede i-edit yung number of males and females ng brooders
                    FragmentActivity activity = (FragmentActivity)(context);
                    FragmentManager fm = activity.getSupportFragmentManager();
                    ViewBrooderInventoryDialog alertDialog = new ViewBrooderInventoryDialog();
                    alertDialog.setArguments(args);
                    alertDialog.show(fm, "CreateBrooderDialog");
                    notifyDataSetChanged();

                }
            });

            holder.brooder_feeding_mort_sale.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent_replacement_pheno_morpho_records = new Intent(context, MortalityAndSalesRecordsBrooder.class);
                    intent_replacement_pheno_morpho_records.putExtra("Brooder Pen ID",brooder_inventory.getBrooder_inv_pen());
                    intent_replacement_pheno_morpho_records.putExtra("Brooder Tag",brooder_inventory.getBrooder_inv_brooder_tag());
                    intent_replacement_pheno_morpho_records.putExtra("Brooder ID",brooder_inventory.getBrooder_inv_brooder_id());
                    context.startActivity(intent_replacement_pheno_morpho_records);
                }
            });

            holder.brooder_feeding_cull.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FragmentActivity activity = (FragmentActivity)(context);
                    FragmentManager fm = activity.getSupportFragmentManager();
                    CullBrooderInventoryDialog alertDialog = new CullBrooderInventoryDialog();
                    alertDialog.setArguments(args);
                    alertDialog.show(fm, "CullBrooderInventoryDialog");
                    notifyDataSetChanged();

                }
            });






    }
    public int getUpdatedItemCount(ArrayList<Brooder_Inventory> arrayList_inventory){
        return arrayList_inventory.size();
    }

    @Override
    public int getItemCount() {
        return arrayListInventory.size();
    }



    public static class RecyclerViewHolder extends RecyclerView.ViewHolder{
        TextView brooder_inventory_code;
        TextView brooder_inventory_family;
        TextView brooder_inventory_line;
        TextView brooder_inventory_gen;
        TextView brooder_inventory_batch_date;

        TextView brooder_inventory_date_added;
        TextView brooder_inventory_last_update;
        TextView brooder_inventory_deleted;
        TextView brooder_inventory_mort;
        TextView brooder_inventory_cull;

        TextView brooder_inventory_number_male;
        TextView brooder_inventory_number_female;
        TextView brooder_inventory_total;

        ImageButton brooder_view_inventory;
        ImageButton brooder_edit;
        ImageButton brooder_feeding_mort_sale;
        ImageButton brooder_feeding_cull;


        RecyclerViewHolder(View view){
            super(view);
            brooder_inventory_code = view.findViewById(R.id.brooder_inventory_code);
           /* brooder_inventory_family= view.findViewById(R.id.brooder_inventory_family);
            brooder_inventory_line = view.findViewById(R.id.brooder_inventory_line);
            brooder_inventory_gen = view.findViewById(R.id.brooder_inventory_gen);*/
            brooder_inventory_batch_date = view.findViewById(R.id.brooder_inventory_batch_date);;
           /* brooder_inventory_date_added = view.findViewById(R.id.brooder_inventory_date_added);;
            brooder_inventory_last_update = view.findViewById(R.id.brooder_inventory_last_update);;
            brooder_inventory_mort = view.findViewById(R.id.brooder_inventory_mort);;
            brooder_inventory_cull = view.findViewById(R.id.brooder_inventory_cull);;

            brooder_inventory_number_male = view.findViewById(R.id.brooder_inventory_number_male);;
            brooder_inventory_number_female = view.findViewById(R.id.brooder_inventory_number_female);;
            brooder_inventory_total = view.findViewById(R.id.brooder_inventory_total);;*/
            brooder_view_inventory= view.findViewById(R.id.brooder_view_inventory);

            brooder_feeding_mort_sale= view.findViewById(R.id.brooder_feeding_mort_sale);;
            brooder_feeding_cull= view.findViewById(R.id.brooder_feeding_cull);;



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
