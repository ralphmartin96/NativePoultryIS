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
        type = view.findViewById(R.id.type); //galing sa brooder table
        male = view.findViewById(R.id.male);//galing sa brooder table
        female = view.findViewById(R.id.female);//galing sa brooder table
        total = view.findViewById(R.id.total);//galing sa brooder table
        textView = view.findViewById(R.id.textView);//galing sa brooder t
        price = view.findViewById(R.id.price);//galing sa brooder t
        reason = view.findViewById(R.id.reason);//galing sa brooder t
        remarks = view.findViewById(R.id.remarks);//galing sa brooder t
        save = view.findViewById(R.id.save);


        textView.setText(breeder_tag);

        /*
    public static final String TABLE_MORTALITY_AND_SALES = "mortality_and_sales";
    public static final String MORT_SALES_COL_0 = "ID";
    public static final String MORT_SALES_COL_1  = "MORT_AND_SALES_DATE";
    public static final String MORT_SALES_COL_2  = "MORT_AND_SALES_BREEDER_INV_ID";
    public static final String MORT_SALES_COL_3  = "MORT_AND_SALES_REPLACEMENT_INV_ID";
    public static final String MORT_SALES_COL_4  = "MORT_AND_SALES_BROODER_INV_ID";
    public static final String MORT_SALES_COL_5  = "MORT_AND_SALES_TYPE";

    public static final String MORT_SALES_COL_6  = "MORT_AND_SALES_CATEGORY";

    public static final String MORT_SALES_COL_7  = "MORT_AND_SALES_PRICE";

    public static final String MORT_SALES_COL_8 = "MORT_AND_SALES_MALE";
    public static final String MORT_SALES_COL_9  = "MORT_AND_SALES_FEMALE";
    public static final String MORT_SALES_COL_10  = "MORT_AND_SALES_TOTAL";

    public static final String MORT_SALES_COL_11  = "MORT_AND_SALES_REASON";
    public static final String MORT_SALES_COL_12  = "MORT_AND_SALES_REMARKS";
    public static final String MORT_SALES_COL_13  = "MORT_AND_SALES_DELETED_AT";*/

        Cursor cursor = myDb.getAllDataFromBreederMortSalesWhereID(mort_id);
        cursor.moveToFirst();
        if(cursor.getCount() != 0){
            Float price_ = cursor.getFloat(7);
            Integer male_ = cursor.getInt(8);
            Integer female_ = cursor.getInt(9);
            Integer total_ = cursor.getInt(10);

////////////YUNG AGE IN WEEKS DAPAT ATA CINOCOMPUTE?????????
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