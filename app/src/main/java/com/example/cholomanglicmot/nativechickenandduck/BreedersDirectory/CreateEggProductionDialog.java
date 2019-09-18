package com.example.cholomanglicmot.nativechickenandduck.BreedersDirectory;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cholomanglicmot.nativechickenandduck.APIHelper;
import com.example.cholomanglicmot.nativechickenandduck.DatabaseHelper;
import com.example.cholomanglicmot.nativechickenandduck.R;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.Calendar;

import cz.msebera.android.httpclient.Header;

public class CreateEggProductionDialog extends DialogFragment{
    private EditText date_collected, intact,weight,broken, rejected,remarks, female_inventory;
    private Button mActionOk;
    DatabaseHelper myDb;
    Calendar calendar;
    ArrayList<Breeder_Inventory> arrayListBrooderInventory = new ArrayList<>();
    ArrayList<Breeder_Inventory>arrayList_temp = new ArrayList<>();




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_create_egg_production_records, container, false);
        myDb = new DatabaseHelper(getContext());
        final String breeder_tag = getArguments().getString("Breeder Tag");


        date_collected= view.findViewById(R.id.date_collected);
        intact= view.findViewById(R.id.intact);
        weight= view.findViewById(R.id.weight);
        broken= view.findViewById(R.id.broken);
        rejected= view.findViewById(R.id.rejected);
        remarks= view.findViewById(R.id.remarks);
        female_inventory= view.findViewById(R.id.female_inventory);


        date_collected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int selectedYear, int selectedMonth, int selectedDay) {
                        selectedMonth++;
                        date_collected.setText(selectedYear + "-" + selectedMonth + "-" + selectedDay);
                        calendar.set(selectedYear,selectedMonth,selectedDay);
                    }
                }, year, month, day);
                mDatePicker.show();

            }
        });




        mActionOk = view.findViewById(R.id.action_ok);
        mActionOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!date_collected.getText().toString().isEmpty() && !intact.getText().toString().isEmpty() && !weight.getText().toString().isEmpty() && !broken.getText().toString().isEmpty() && !intact.getText().toString().isEmpty() && !rejected.getText().toString().isEmpty() ){
                    //kunin mo yung

                    //kunin mo muna yung id ng breeder_inv given yung tag

                    Cursor cursor = myDb.getAllDataFromBreederInventory();
                    cursor.moveToFirst();

                    if(cursor.getCount() != 0){
                        do{
                            Breeder_Inventory breeder_inventory = new  Breeder_Inventory(cursor.getInt(0), cursor.getInt(1), cursor.getInt(2), cursor.getString(3), cursor.getString(4), cursor.getInt(5), cursor.getInt(6), cursor.getInt(7), cursor.getString(8),cursor.getString(9), cursor.getString(10), cursor.getString(11), cursor.getString(12));
                            arrayListBrooderInventory.add(breeder_inventory);

                        }while(cursor.moveToNext());
                    }


                    for(int i=0;i<arrayListBrooderInventory.size();i++){
                        if(arrayListBrooderInventory.get(i).getBreeder_inv_breeder_tag().equals(breeder_tag)){
                            arrayList_temp.add(arrayListBrooderInventory.get(i));
                        }
                    }

////////////////////////
                    boolean isInserted = myDb.insertEggProductionRecords(arrayList_temp.get(0).getId(), date_collected.getText().toString(), Integer.parseInt(intact.getText().toString()), Float.parseFloat(weight.getText().toString()), Integer.parseInt(broken.getText().toString()), Integer.parseInt(rejected.getText().toString()), remarks.getText().toString(), null, female_inventory.getText().toString());

                    boolean isNetworkAvailable = isNetworkAvailable();
                    if(isNetworkAvailable){

                        RequestParams requestParams = new RequestParams();
                        //requestParams.add("id", id_0.toString());
                        requestParams.add("breeder_inventory_id", arrayList_temp.get(0).getId().toString());
                        requestParams.add("date_collected", date_collected.getText().toString());
                        requestParams.add("total_eggs_intact", intact.getText().toString());
                        requestParams.add("total_egg_weight", weight.getText().toString());
                        requestParams.add("total_broken", broken.getText().toString());
                        requestParams.add("total_rejects", rejected.getText().toString());
                        requestParams.add("remarks", remarks.getText().toString());
                        requestParams.add("deleted_at", null);
                        requestParams.add("female_inventory", female_inventory.getText().toString());



                        API_addEggProduction(requestParams);
                    }
                    if (isInserted == true){
                        Toast.makeText(getContext(),"Egg Production added to database", Toast.LENGTH_LONG).show();
                        Intent intent_line = new Intent(getActivity(), EggProductionRecords.class);
                        intent_line.putExtra("Breeder Tag", breeder_tag);
                        startActivity(intent_line);
                        getDialog().dismiss();
                    }else{
                        Toast.makeText(getContext(),"Error adding to database", Toast.LENGTH_LONG).show();
                    }







                }else{
                    Toast.makeText(getContext(),"Please fill any empty fields", Toast.LENGTH_LONG).show();
                }




            }

        });

        return view;
    }

    public void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) this.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    private void API_addEggProduction(RequestParams requestParams){
        APIHelper.addEggProduction("addEggProduction", requestParams, new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response){
//                Toast.makeText(getContext(), "Successfully added to web", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonResponse, Object response){

                Toast.makeText(getContext(), "Failed to add to web", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable{
                return null;
            }
        });
    }
}