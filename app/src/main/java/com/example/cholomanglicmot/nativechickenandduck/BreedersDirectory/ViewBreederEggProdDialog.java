package com.example.cholomanglicmot.nativechickenandduck.BreedersDirectory;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.cholomanglicmot.nativechickenandduck.DatabaseHelper;
import com.example.cholomanglicmot.nativechickenandduck.R;

public class ViewBreederEggProdDialog extends DialogFragment {

    DatabaseHelper myDb;
    TextView textView, date_collected, intact, total_weight, average_weight, broken, rejected, female_inventory, remarks;

    Button save;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_view_egg_prod, container, false);
        final Integer breeder_inventory_idID = getArguments().getInt("Breeder Inventory ID");
        final Integer egg_prod_id = getArguments().getInt("Breeder Egg Prod ID");
        final String breeder_tag = getArguments().getString("Breeder Tag");
        final Float breeder_egg_prod_average_weight = getArguments().getFloat("Breeder Egg Prod Average Weight");




        myDb = new DatabaseHelper(getContext());


        textView = view.findViewById(R.id.textView);
        date_collected = view.findViewById(R.id.date_collected);
        intact = view.findViewById(R.id.intact); //galing sa brooder table
        total_weight = view.findViewById(R.id.total_weight);//galing sa brooder table
        remarks = view.findViewById(R.id.remarks);//galing sa brooder table
        average_weight = view.findViewById(R.id.average_weight);//galing sa brooder table
        broken = view.findViewById(R.id.broken);//galing sa brooder table
        rejected = view.findViewById(R.id.rejected);//galing sa brooder t
        save = view.findViewById(R.id.save);
        female_inventory = view.findViewById(R.id.female_inventory);

       /*
    public static final String TABLE_EGG_PRODUCTION = "egg_production";
    public static final String EGG_PRODUCTION_COL_0 = "ID";
    public static final String EGG_PRODUCTION_COL_1  = "EGG_PRODUCTION_BREEDER_INVENTORY_ID";
    public static final String EGG_PRODUCTION_COL_2  = "EGG_PRODUCTION_DATE";
    public static final String EGG_PRODUCTION_COL_3  = "EGG_PRODUCTION_EGGS_INTACT";
    public static final String EGG_PRODUCTION_COL_4  = "EGG_PRODUCTION_EGG_WEIGHT";
    public static final String EGG_PRODUCTION_COL_5  = "EGG_PRODUCTION_TOTAL_BROKEN";
    public static final String EGG_PRODUCTION_COL_6  = "EGG_PRODUCTION_TOTAL_REJECTS";
    public static final String EGG_PRODUCTION_COL_7 = "EGG_PRODUCTION_REMARKS";
    public static final String EGG_PRODUCTION_COL_8  = "EGG_PRODUCTION_DELETED_AT";
*/

        textView.setText(breeder_tag);
        average_weight.setText(breeder_egg_prod_average_weight.toString());
        Cursor cursor = myDb.getAllDataFromBreederEggProdWhereID(egg_prod_id);
        cursor.moveToFirst();
        if(cursor.getCount() != 0){
            Integer intact_ = cursor.getInt(3);
            Float total_egg_weight_ = cursor.getFloat(4);
            Integer broken_ = cursor.getInt(5);
            Integer rejects_ = cursor.getInt(6);


            date_collected.setText(cursor.getString(2));
            intact.setText(intact_.toString());
            total_weight.setText(total_egg_weight_.toString());
            broken.setText(broken_.toString());
            rejected.setText(rejects_.toString());
            remarks.setText(cursor.getString(7));
            try{
                female_inventory.setText(cursor.getInt(9));
            }catch (Exception e){}


        }



        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getDialog().dismiss();


            }
        });


        return view;
    }
}