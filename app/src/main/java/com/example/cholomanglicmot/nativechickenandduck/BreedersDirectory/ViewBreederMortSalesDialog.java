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

public class ViewBreederMortSalesDialog extends DialogFragment {

    DatabaseHelper myDb;
    TextView date, category, type, male, female, total, price, reason, remarks,textView;

    Button update, save;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_view_mort_sales, container, false);
        final Integer breeder_inventory_idID = getArguments().getInt("Breeder Inventory ID");
        final Integer mort_id = getArguments().getInt("Breeder Mort and Sales ID");
        final String breeder_tag = getArguments().getString("Breeder Tag");

        myDb = new DatabaseHelper(getContext());

        date = view.findViewById(R.id.date);
        category = view.findViewById(R.id.category);
        type = view.findViewById(R.id.type);
        male = view.findViewById(R.id.male);
        female = view.findViewById(R.id.female);
        total = view.findViewById(R.id.total);
        textView = view.findViewById(R.id.textView);
        price = view.findViewById(R.id.price);
        reason = view.findViewById(R.id.reason);
        remarks = view.findViewById(R.id.remarks);
        save = view.findViewById(R.id.save);


        textView.setText(breeder_tag);

        Cursor cursor = myDb.getAllDataFromBreederMortSalesWhereID(mort_id);
        cursor.moveToFirst();
        if(cursor.getCount() != 0){
            Float price_ = cursor.getFloat(7);
            Integer male_ = cursor.getInt(8);
            Integer female_ = cursor.getInt(9);
            Integer total_ = cursor.getInt(10);

            date.setText(cursor.getString(1));
            type.setText(cursor.getString(5));
            category.setText(cursor.getString(6));

            date.setText(price_.toString());
            type.setText(cursor.getString(5));
            category.setText(cursor.getString(6));
            date.setText(cursor.getString(1));
            type.setText(cursor.getString(5));
            category.setText(cursor.getString(6));
            price.setText(price_.toString());
            male.setText(male_.toString());
            female.setText(female_.toString());
            total.setText(total_.toString());
            reason.setText(cursor.getString(11));
            remarks.setText(cursor.getString(12));
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