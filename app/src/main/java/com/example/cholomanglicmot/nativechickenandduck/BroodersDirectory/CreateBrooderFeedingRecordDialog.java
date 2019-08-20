package com.example.cholomanglicmot.nativechickenandduck.BroodersDirectory;

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
import com.google.gson.Gson;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Map;

import cz.msebera.android.httpclient.Header;

public class CreateBrooderFeedingRecordDialog extends DialogFragment {

    private static final String TAG = "CreateFamilyDialog";
    private EditText brooder_feeding_date_collected, brooder_feeding_record_offered,brooder_feeding_record_refused,brooder_feeding_record_remarks;
    private Button mActionOk;
    DatabaseHelper myDb;
    Calendar calendar;
    ArrayList<Brooder_Inventory>arrayListBrooderInventory = new ArrayList<>();
   // ArrayList<Brooder_Inventory>arrayList_temp = new ArrayList<>();
    Integer brooder_pen_id;
    boolean isSend = false;

    Map<Integer, ArrayList<Float>> inventory_dictionary = new LinkedHashMap<Integer, ArrayList<Float>>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_create_brooder_feeding_records, container, false);
        myDb = new DatabaseHelper(getContext());
        final String brooder_pen = getArguments().getString("brooder pen number");


        brooder_feeding_date_collected = view.findViewById(R.id.brooder_feeding_date_collected);
        brooder_feeding_record_offered = view.findViewById(R.id.brooder_feeding_offered);
        brooder_feeding_record_refused = view.findViewById(R.id.brooder_feeding_record_refused);
        brooder_feeding_record_remarks = view.findViewById(R.id.brooder_feeding_record_remarks);

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

        Cursor cursor = myDb.getAllDataFromPenWhere(brooder_pen);
        cursor.moveToFirst();
        if(cursor.getCount() != 0){
            brooder_pen_id = cursor.getInt(0);
        }


        mActionOk = view.findViewById(R.id.action_ok);
        mActionOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!brooder_feeding_date_collected.getText().toString().isEmpty() && !brooder_feeding_record_offered.getText().toString().isEmpty() && !brooder_feeding_record_refused.getText().toString().isEmpty()){
                    //kunin mo yung


////////////////////////

                    Cursor cursor_brooder_inventory = myDb.getDataFromBrooderInventoryWherePen(brooder_pen_id); //para sa pagstore ng data sa arraylist
                    cursor_brooder_inventory.moveToFirst();
                    if(cursor_brooder_inventory.getCount() == 0){
                        //show message

                        Toast.makeText(getActivity(),"Add Brooders first", Toast.LENGTH_LONG).show();

                    }else {
                        do {
                            String deleted_at = cursor_brooder_inventory.getString(9);
                            if(deleted_at == null){
                                Brooder_Inventory brooder_inventory = new Brooder_Inventory(cursor_brooder_inventory.getInt(0),cursor_brooder_inventory.getInt(1), cursor_brooder_inventory.getInt(2), cursor_brooder_inventory.getString(3),cursor_brooder_inventory.getString(4), cursor_brooder_inventory.getInt(5), cursor_brooder_inventory.getInt(6),cursor_brooder_inventory.getInt(7), cursor_brooder_inventory.getString(8), cursor_brooder_inventory.getString(9));
                                arrayListBrooderInventory.add(brooder_inventory);

                            }


                        } while (cursor_brooder_inventory.moveToNext());
                    }



                    //GETTING TOTAL BROODER INVENTORY COUNT
                    int count_inventory = 0;

                    for(int i=0;i<arrayListBrooderInventory.size();i++){
                        count_inventory = count_inventory+arrayListBrooderInventory.get(i).getBrooder_total_quantity();
                    }



                    ArrayList<Integer> arrayListBrooder = new ArrayList<>();
                    for(int i = 0;i<arrayListBrooderInventory.size();i++){
                        if(arrayListBrooder.contains(arrayListBrooderInventory.get(i).getId())){
                            //do nothing
                        }else{
                            arrayListBrooder.add(arrayListBrooderInventory.get(i).getId());
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

                    for (int i=0;i<arrayListBrooderInventory.size();i++){
                        if(inventory_dictionary.get(arrayListBrooderInventory.get(i).getId()) != null && !arrayList.contains(arrayListBrooderInventory.get(i).getId())){
                            ArrayList<Float> arrayList2 = new ArrayList<>();
                            arrayList2.add((float)arrayListBrooderInventory.get(i).getBrooder_total_quantity());
                            arrayList2.add((float)arrayListBrooderInventory.get(i).getBrooder_total_quantity());
                            inventory_dictionary.put(arrayListBrooderInventory.get(i).getId(),arrayList2);
                            arrayList.add(arrayListBrooderInventory.get(i).getId());
                        }else if(arrayList.contains(arrayListBrooderInventory.get(i).getId())){


                            ArrayList<Float> arrayList3 = new ArrayList<>();
                            arrayList3.add(inventory_dictionary.get(arrayListBrooderInventory.get(i).getId()).get(0)+arrayListBrooderInventory.get(i).getBrooder_total_quantity());
                            arrayList3.add(inventory_dictionary.get(arrayListBrooderInventory.get(i).getId()).get(1)+arrayListBrooderInventory.get(i).getBrooder_total_quantity());


                            inventory_dictionary.put(arrayListBrooderInventory.get(i).getId(),arrayList3);
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
                    for (int i=0;i<arrayListBrooderInventory.size();i++){
                        if(arrayList1.contains(arrayListBrooderInventory.get(i).getId())){
                            //skip
                        }else{

                            ArrayList<Float> multiplier = new ArrayList<>();
                            multiplier.add(inventory_dictionary.get(arrayListBrooderInventory.get(i).getId()).get(0)*multiplierOffered);
                            multiplier.add(inventory_dictionary.get(arrayListBrooderInventory.get(i).getId()).get(1)*mutiplierRefused);
                            inventory_dictionary.put(arrayListBrooderInventory.get(i).getId(),multiplier);
                            arrayList1.add(arrayListBrooderInventory.get(i).getId());
                        }
                    }




                    ///C. INSERTING FEEDING RECORD TO THE DATABASE BY BATCH
                    boolean isNetworkAvailable = isNetworkAvailable();
                    for ( Map.Entry<Integer, ArrayList<Float>> entry : inventory_dictionary.entrySet()) {
                        Integer key = entry.getKey();
                        Float valueOffered = entry.getValue().get(0);
                        Float valueRefused = entry.getValue().get(1);
                        // do something with key and/or tab
                        boolean isInserted = myDb.insertDataBrooderFeedingRecords(key ,brooder_feeding_date_collected.getText().toString(), valueOffered, valueRefused,brooder_feeding_record_remarks.getText().toString(),null);

                        if(isNetworkAvailable){

                            RequestParams requestParams = new RequestParams();

                            requestParams.add("broodergrower_inventory_id", key.toString());
                            requestParams.add("date_collected", brooder_feeding_date_collected.getText().toString());
                            requestParams.add("amount_offered", valueOffered.toString());
                            requestParams.add("amount_refused", valueRefused.toString());
                            requestParams.add("remarks", brooder_feeding_record_remarks.getText().toString());
                            requestParams.add("deleted_at", null);



                           /* while(!API_addBrooderFeeding(requestParams)){

                            }*/

                               API_addBrooderFeeding(requestParams);




                        }


                    /*    if(isInserted){
                            Toast.makeText(getContext(),"Added to database", Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(getContext(),"Error inserting record with Inventory id: "+key, Toast.LENGTH_SHORT).show();
                            getDialog().dismiss();

                        }*/
                    }


                    Intent intent = new Intent(getActivity(), BrooderFeedingRecordsActivity.class);
                    intent.putExtra("Brooder Pen",brooder_pen);
                    startActivity(intent);






                    getDialog().dismiss();
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

    private void API_addBrooderFeeding(RequestParams requestParams){

        APIHelper.addBrooderFeeding("addBrooderFeeding", requestParams, new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response){
                //Toast.makeText(getActivity(), "Successfully added brooder feeding record to web", Toast.LENGTH_SHORT).show();
                isSend = true;

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonResponse, Object response){

               // Toast.makeText(getActivity(), "Failed to add brooder feeding record to web", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable{
                return null;
            }
        });

    }
    private void API_updateBrooderFeeding( ){
        APIHelper.getBrooderFeeding("getBrooderFeeding/", new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response){

                Gson gson = new Gson();
                JSONBrooderFeeding jsonBrooderInventory = gson.fromJson(rawJsonResponse, JSONBrooderFeeding.class);
                ArrayList<Brooder_FeedingRecords> arrayList_brooderInventory = jsonBrooderInventory.getData();
                //ArrayList<Brooder_FeedingRecords> arrayList_temp1 = new ArrayList<>();
                ArrayList<Integer> arrayList_of_feeding_id = new ArrayList<>();
                for(int i=0;i<arrayList_brooderInventory.size();i++){
                    arrayList_of_feeding_id.add(arrayList_brooderInventory.get(i).getId());
                }

                Cursor cursor = myDb.getAllDataFromBrooderFeedingRecords();
                cursor.moveToFirst();
                if(cursor.getCount() != 0){
                    do{

                        for(int i=0;i<arrayList_brooderInventory.size();i++){
                            boolean contains_feeding = arrayList_of_feeding_id.contains(cursor.getInt(0));
                            if(!contains_feeding){
                                //if it doesnt contain the id, then add the feeding record with a id of the current cursor
                                Integer id = cursor.getInt(0);
                                Integer broodergrower_inventory_id = cursor.getInt(1);
                                String date_collected = cursor.getString(2);
                                Float amount_offered = cursor.getFloat(3);
                                Float amount_refused = cursor.getFloat(4);
                                String remarks = cursor.getString(5);
                                String deleted_at = cursor.getString(6);
                                RequestParams requestParams = new RequestParams();
                                requestParams.add("id", id.toString());
                                requestParams.add("broodergrower_inventory_id", broodergrower_inventory_id.toString());
                                requestParams.add("date_collected", date_collected);
                                requestParams.add("amount_offered", amount_offered.toString());
                                requestParams.add("amount_refused", amount_refused.toString());
                                requestParams.add("remarks", remarks);
                                requestParams.add("deleted_at", deleted_at);


                                API_addBrooderFeeding(requestParams);
                            /*    while(!API_addBrooderFeeding(requestParams)){

                                }*/
                            }
                        }



                    }while (cursor.moveToNext());
                }


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonResponse, Object response){

              //  Toast.makeText(getApplicationContext(), "Failed to fetch Brooders Inventory from web database ", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable{
                return null;
            }
        });
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) this.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


}