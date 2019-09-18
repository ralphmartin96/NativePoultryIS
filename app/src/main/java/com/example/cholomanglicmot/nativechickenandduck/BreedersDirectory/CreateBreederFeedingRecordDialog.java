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
import android.widget.TextView;
import android.widget.Toast;

import com.example.cholomanglicmot.nativechickenandduck.APIHelper;
import com.example.cholomanglicmot.nativechickenandduck.DatabaseHelper;
import com.example.cholomanglicmot.nativechickenandduck.R;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Map;

import cz.msebera.android.httpclient.Header;

public class CreateBreederFeedingRecordDialog extends DialogFragment {

    private EditText brooder_feeding_date_collected, brooder_feeding_record_offered,brooder_feeding_record_refused,brooder_feeding_record_remarks;
    private Button mActionOk;
    DatabaseHelper myDb;
    Calendar calendar;
    ArrayList<Breeder_Inventory>arrayListBrooderInventory = new ArrayList<>();
    ArrayList<Breeder_Inventory>arrayList_temp = new ArrayList<>();
    TextView textView;


    Map<Integer, ArrayList<Float>> inventory_dictionary = new LinkedHashMap<Integer, ArrayList<Float>>();


    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_create_breeder_feeding_records, container, false);
        myDb = new DatabaseHelper(getContext());
        final Integer breeder_pen = getArguments().getInt("breeder pen number");
        final String breeder_tag = getArguments().getString("Breeder Tag");


        brooder_feeding_date_collected = view.findViewById(R.id.brooder_feeding_date_collected);
        brooder_feeding_record_offered = view.findViewById(R.id.brooder_feeding_offered);
        brooder_feeding_record_refused = view.findViewById(R.id.brooder_feeding_record_refused);
        brooder_feeding_record_remarks = view.findViewById(R.id.brooder_feeding_record_remarks);
        textView = view.findViewById(R.id.textView);
        textView.setText("Feeding Records | "+breeder_tag);

        brooder_feeding_date_collected.setOnClickListener(new View.OnClickListener() {
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
                        brooder_feeding_date_collected.setText(selectedYear + "-" + selectedMonth + "-" + selectedDay);
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
                if(!brooder_feeding_date_collected.getText().toString().isEmpty() && !brooder_feeding_record_offered.getText().toString().isEmpty() && !brooder_feeding_record_refused.getText().toString().isEmpty()){
                    //kunin mo yung


////////////////////////

                    Cursor cursor_breeder_inventory = myDb.getAllDataFromBreederInventory(); //para sa pagstore ng data sa arraylist
                    cursor_breeder_inventory.moveToFirst();
                    if(cursor_breeder_inventory.getCount() == 0){
                        //show message

                      //  Toast.makeText(getActivity(),"Add Brooders first", Toast.LENGTH_LONG).show();

                    }else {
                        do {

                            Breeder_Inventory brooder_inventory = new Breeder_Inventory(cursor_breeder_inventory.getInt(0),cursor_breeder_inventory.getInt(1), cursor_breeder_inventory.getInt(2), cursor_breeder_inventory.getString(3),cursor_breeder_inventory.getString(4), cursor_breeder_inventory.getInt(5), cursor_breeder_inventory.getInt(6),cursor_breeder_inventory.getInt(7), cursor_breeder_inventory.getString(8), cursor_breeder_inventory.getString(9), cursor_breeder_inventory.getString(10), cursor_breeder_inventory.getString(11), cursor_breeder_inventory.getString(12));
                            arrayListBrooderInventory.add(brooder_inventory);


                        } while (cursor_breeder_inventory.moveToNext());
                    }

                     for (int i=0;i<arrayListBrooderInventory.size();i++){
                            if(arrayListBrooderInventory.get(i).getBreeder_inv_pen() == breeder_pen ){

                                arrayList_temp.add(arrayListBrooderInventory.get(i)); //ito na yung list ng inventory na nasa pen

                        }
                    }

                    //GETTING TOTAL BROODER INVENTORY COUNT
                    int count_inventory = 0;

                    for(int i=0;i<arrayList_temp.size();i++){
                        count_inventory = count_inventory+arrayList_temp.get(i).getBreeder_total_quantity();
                    }



                    ArrayList<Integer> arrayListBrooder = new ArrayList<>();
                    for(int i = 0;i<arrayList_temp.size();i++){
                        if(arrayListBrooder.contains(arrayList_temp.get(i).getBreeder_inv_breeder_id())){
                            //do nothing
                        }else{
                            arrayListBrooder.add(arrayList_temp.get(i).getBreeder_inv_breeder_id());
                        }
                    }

                    //create arraylist with 0 as initial elements which is given to inventory_dictionary
                    ArrayList<Float> float_zero = new ArrayList<>();
                    float_zero.add(0.0f);
                    float_zero.add(0.0f);
                    for(int i = 0;i<arrayListBrooder.size();i++){
                        inventory_dictionary.put(arrayListBrooder.get(i),float_zero);
                    }


                    StringBuffer buffer = new StringBuffer();
                    //populating inventory_dictionary
                    ArrayList<Integer> arrayList = new ArrayList<>();

                    for (int i=0;i<arrayList_temp.size();i++){
                        if(inventory_dictionary.get(arrayList_temp.get(i).getBreeder_inv_breeder_id()) != null && !arrayList.contains(arrayList_temp.get(i).getBreeder_inv_breeder_id())){
                            ArrayList<Float> arrayList2 = new ArrayList<>();
                            arrayList2.add((float)arrayList_temp.get(i).getBreeder_total_quantity());
                            arrayList2.add((float)arrayList_temp.get(i).getBreeder_total_quantity());
                            inventory_dictionary.put(arrayList_temp.get(i).getBreeder_inv_breeder_id(),arrayList2);
                            arrayList.add(arrayList_temp.get(i).getBreeder_inv_breeder_id());
                        }else if(arrayList.contains(arrayList_temp.get(i).getBreeder_inv_breeder_id())){


                            ArrayList<Float> arrayList3 = new ArrayList<>();
                            arrayList3.add(inventory_dictionary.get(arrayList_temp.get(i).getBreeder_inv_breeder_id()).get(0)+arrayList_temp.get(i).getBreeder_total_quantity());
                            arrayList3.add(inventory_dictionary.get(arrayList_temp.get(i).getBreeder_inv_breeder_id()).get(1)+arrayList_temp.get(i).getBreeder_total_quantity());


                            inventory_dictionary.put(arrayList_temp.get(i).getBreeder_inv_breeder_id(),arrayList3);
                        }
                    }
                   //buffer.append("inventory_dictionary  "+inventory_dictionary.toString()+"\n\n");


                    ////important values:
                    //1. inventory_dictionary
                    //2. count_inventory


                                            /////////////////////////////////////////////////////////////////

                    ///A. COMPUTING FOR THE MULTIPLIER
                    float feedOffered = Float.parseFloat(brooder_feeding_record_offered.getText().toString());
                    float feedRefused = Float.parseFloat(brooder_feeding_record_refused.getText().toString());
                    float multiplierOffered = feedOffered/count_inventory;
                    float mutiplierRefused = feedRefused/count_inventory;


                    ///B. REPLACE THE VALUES OF THE KEYS IN inventory_dictionary WITH THEIR COUNT

                    ArrayList<Integer> arrayList1 = new ArrayList<>();
                    for (int i=0;i<arrayList_temp.size();i++){
                        if(arrayList1.contains(arrayList_temp.get(i).getBreeder_inv_breeder_id())){
                            //skip
                        }else{

                            ArrayList<Float> multiplier = new ArrayList<>();
                            multiplier.add(inventory_dictionary.get(arrayList_temp.get(i).getBreeder_inv_breeder_id()).get(0)*multiplierOffered);
                            multiplier.add(inventory_dictionary.get(arrayList_temp.get(i).getBreeder_inv_breeder_id()).get(1)*mutiplierRefused);
                            inventory_dictionary.put(arrayList_temp.get(i).getBreeder_inv_breeder_id(),multiplier);
                            arrayList1.add(arrayList_temp.get(i).getBreeder_inv_breeder_id());
                        }
                    }





                    ///C. INSERTING FEEDING RECORD TO THE DATABASE BY BATCH
                    boolean isNetworkAvailable = isNetworkAvailable();
                    for ( Map.Entry<Integer, ArrayList<Float>> entry : inventory_dictionary.entrySet()) {
                        Integer key = entry.getKey();
                        Float valueOffered = entry.getValue().get(0);
                        Float valueRefused = entry.getValue().get(1);
                        // do something with key and/or tab
                        boolean isInserted = myDb.insertDataBreederFeedingRecords( key ,brooder_feeding_date_collected.getText().toString(), Float.parseFloat(brooder_feeding_record_offered.getText().toString()), Float.parseFloat(brooder_feeding_record_refused.getText().toString()),brooder_feeding_record_remarks.getText().toString(),null);


                        if(isNetworkAvailable){

                            RequestParams requestParams = new RequestParams();

                            requestParams.add("breeder_inventory_id", key.toString());
                            requestParams.add("date_collected", brooder_feeding_date_collected.getText().toString());
                            requestParams.add("amount_offered", valueOffered.toString());
                            requestParams.add("amount_refused", valueRefused.toString());
                            requestParams.add("remarks", brooder_feeding_record_remarks.getText().toString());
                            requestParams.add("deleted_at", null);



                            API_addBreederFeeding(requestParams);



                        }
                        if(isInserted){
                            //continue
                        }else{
                            Toast.makeText(getContext(),"Error inserting record with Inventory id: "+key, Toast.LENGTH_SHORT).show();
                            getDialog().dismiss();

                        }
                    }

                    Toast.makeText(getContext(),"Added to database", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getActivity(), BreederFeedingRecordsActivity.class);
                    intent.putExtra("Breeder Pen",breeder_pen);
                    intent.putExtra("Breeder Tag", breeder_tag);
                    startActivity(intent);






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

    private boolean API_addBreederFeeding(RequestParams requestParams){
        APIHelper.addBreederFeeding("addBreederFeeding", requestParams, new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response){
                Toast.makeText(getActivity(), "Successfully added brooder feeding record to web", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonResponse, Object response){

                 Toast.makeText(getActivity(), "Failed to add brooder feeding record to web", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable{
                return null;
            }
        });
        return true;
    }

}