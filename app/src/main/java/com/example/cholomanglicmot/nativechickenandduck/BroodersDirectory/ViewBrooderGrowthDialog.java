package com.example.cholomanglicmot.nativechickenandduck.BroodersDirectory;

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

public class ViewBrooderGrowthDialog extends DialogFragment {

    DatabaseHelper myDb;
    TextView textView, date_collected, collection_day, male_count, male_weight, female_count, female_weight, total_count, total_weight;

    Button save;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_view_brooder_growth, container, false);
        final Integer brooder_ID = getArguments().getInt("Brooder Inventory ID");
        final String brooder_tag = getArguments().getString("Brooder Tag");
        final Integer brooder_growth_id = getArguments().getInt("Brooder Growth ID");


        myDb = new DatabaseHelper(getContext());

        String brooder_inv_string=null;


        textView = view.findViewById(R.id.textView);
        date_collected = view.findViewById(R.id.date_collected); //galing sa brooder table
        collection_day = view.findViewById(R.id.collection_day);//galing sa brooder table
        male_count = view.findViewById(R.id.male_count);//galing sa brooder table
        male_weight = view.findViewById(R.id.male_weight);//galing sa brooder table
        female_count = view.findViewById(R.id.female_count); //galing sa brooder table
        female_weight = view.findViewById(R.id.female_weight);//galing sa brooder table
        total_count = view.findViewById(R.id.total_count);//galing sa brooder table
        total_weight = view.findViewById(R.id.total_weight);//galing sa brooder tabl
        save = view.findViewById(R.id.save);


        Cursor cursor1 = myDb.getAllDataFromBrooderInventoryWhereID(brooder_ID);
        cursor1.moveToFirst();
        if(cursor1.getCount() != 0){
            brooder_inv_string = cursor1.getString(3);
        }
        textView.setText(brooder_inv_string);


        Cursor cursor = myDb.getAllDataFromBrooderGrowthRecordsWhereGrowthID(brooder_growth_id);
        cursor.moveToFirst();
        if(cursor.getCount() != 0){
            Integer male_count_string = cursor.getInt(4);
            Float male_weight_string = cursor.getFloat(5);
            Integer female_count_string = cursor.getInt(6);
            Float female_weight_string = cursor.getFloat(7);
            Integer total_count_string = cursor.getInt(8);
            Float total_weight_string = cursor.getFloat(9);
            Integer collection_day_string = cursor.getInt(2);


            date_collected.setText(cursor.getString(3));
            collection_day.setText(collection_day_string.toString());
            male_count.setText(male_count_string.toString());
            male_weight.setText(male_weight_string.toString());
            female_count.setText(female_count_string.toString());
            female_weight.setText(female_weight_string.toString());
            total_count.setText(total_count_string.toString());
            total_weight.setText(total_weight_string.toString());




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