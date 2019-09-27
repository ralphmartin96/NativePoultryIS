package com.example.cholomanglicmot.nativechickenandduck.ReplacementsDirectory;

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

import com.example.cholomanglicmot.nativechickenandduck.APIHelperAsync;
import com.example.cholomanglicmot.nativechickenandduck.DatabaseHelper;
import com.example.cholomanglicmot.nativechickenandduck.R;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Map;

import cz.msebera.android.httpclient.Header;

public class CreateReplacementFeedingRecordDialog extends DialogFragment {

    private static final String TAG = "CreateReplacementGrowthRecordDialog";
    private EditText replacement_feeding_date_collected, replacement_feeding_record_offered,replacement_feeding_record_refused,replacement_feeding_record_remarks;
    private TextView textView;
    private Button mActionOk;
    DatabaseHelper myDb;
    Calendar calendar;
    ArrayList<Replacement_Inventory> arrayListReplacementInventory = new ArrayList<>();
    ArrayList<Replacement_Inventory>arrayList_temp = new ArrayList<>();
    ArrayList<Replacement_Inventory>arrayList_temp1 = new ArrayList<>();
    ArrayList<Replacements> arrayListReplacements = new ArrayList<>();
    Integer replacement_pen_id;


    Map<Integer, ArrayList<Float>> inventory_dictionary = new LinkedHashMap<Integer, ArrayList<Float>>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_create_replacement_feeding_records, container, false);
        myDb = new DatabaseHelper(getContext());
        final String replacement_pen = getArguments().getString("Replacement Pen");
        final StringBuffer buffer = new StringBuffer();


        replacement_feeding_date_collected = view.findViewById(R.id.replacement_feeding_date_collected);
        replacement_feeding_record_offered = view.findViewById(R.id.replacement_feeding_record_offered);
        replacement_feeding_record_refused = view.findViewById(R.id.replacement_feeding_record_refused);
        replacement_feeding_record_remarks = view.findViewById(R.id.replacement_feeding_record_remarks);
        textView = view.findViewById(R.id.textView);

        textView.setText(replacement_pen+ " Feeding Records");

        replacement_feeding_date_collected.setOnClickListener(new View.OnClickListener() {
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
                        replacement_feeding_date_collected.setText(selectedYear + "-" + selectedMonth + "-" + selectedDay);
                        calendar.set(selectedYear,selectedMonth,selectedDay);
                    }
                }, year, month, day);
                mDatePicker.show();

            }
        });




        Cursor cursor = myDb.getAllDataFromPenWhere(replacement_pen);
        cursor.moveToFirst();
        if(cursor.getCount() != 0){
            replacement_pen_id = cursor.getInt(0);
        }



        mActionOk = view.findViewById(R.id.action_ok);
        mActionOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!replacement_feeding_date_collected.getText().toString().isEmpty() && !replacement_feeding_record_offered.getText().toString().isEmpty() && !replacement_feeding_record_refused.getText().toString().isEmpty()){


                    Cursor cursor_brooder_inventory = myDb.getAllDataFromReplacementInventory(); //para sa pagstore ng data sa arraylist
                    cursor_brooder_inventory.moveToFirst();
                    if(cursor_brooder_inventory.getCount() == 0){
                        //show message

                        Toast.makeText(getActivity(),"Add Replacements first", Toast.LENGTH_LONG).show();

                    }else {
                        do {
                            String deleted_at = cursor_brooder_inventory.getString(9);
                            if(deleted_at == null){
                                Replacement_Inventory brooder_inventory = new Replacement_Inventory(cursor_brooder_inventory.getInt(0),cursor_brooder_inventory.getInt(1), cursor_brooder_inventory.getInt(2), cursor_brooder_inventory.getString(3),cursor_brooder_inventory.getString(4), cursor_brooder_inventory.getInt(5), cursor_brooder_inventory.getInt(6),cursor_brooder_inventory.getInt(7), cursor_brooder_inventory.getString(8), cursor_brooder_inventory.getString(9));
                                arrayListReplacementInventory.add(brooder_inventory);
                            }



                        } while (cursor_brooder_inventory.moveToNext());
                    }

                    for (int i=0;i<arrayListReplacementInventory.size();i++){
                        if(arrayListReplacementInventory.get(i).getReplacement_inv_pen() == replacement_pen_id) {

                            arrayList_temp.add(arrayListReplacementInventory.get(i)); //ito na yung list ng inventory na nasa pen

                        }
                    }

                    //GETTING TOTAL BROODER INVENTORY COUNT
                    int count_inventory = 0;

                    for(int i=0;i<arrayList_temp.size();i++){
                        count_inventory = count_inventory+arrayList_temp.get(i).getReplacement_total_quantity();
                    }



                    ArrayList<Integer> arrayListBrooder = new ArrayList<>();
                    for(int i = 0;i<arrayList_temp.size();i++){
                        if(arrayListBrooder.contains(arrayList_temp.get(i).getId())){
                            //do nothing
                        }else{
                            arrayListBrooder.add(arrayList_temp.get(i).getId());
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
                        if(inventory_dictionary.get(arrayList_temp.get(i).getId()) != null && !arrayList.contains(arrayList_temp.get(i).getId())){
                            ArrayList<Float> arrayList2 = new ArrayList<>();
                            arrayList2.add((float)arrayList_temp.get(i).getReplacement_total_quantity());
                            arrayList2.add((float)arrayList_temp.get(i).getReplacement_total_quantity());
                            inventory_dictionary.put(arrayList_temp.get(i).getId(),arrayList2);
                            arrayList.add(arrayList_temp.get(i).getId());
                        }else if(arrayList.contains(arrayList_temp.get(i).getId())){


                            ArrayList<Float> arrayList3 = new ArrayList<>();
                            arrayList3.add(inventory_dictionary.get(arrayList_temp.get(i).getId()).get(0)+arrayList_temp.get(i).getReplacement_total_quantity());
                            arrayList3.add(inventory_dictionary.get(arrayList_temp.get(i).getId()).get(1)+arrayList_temp.get(i).getReplacement_total_quantity());


                            inventory_dictionary.put(arrayList_temp.get(i).getId(),arrayList3);
                        }
                    }
                    //buffer.append("inventory_dictionary  "+inventory_dictionary.toString()+"\n\n");


                    ////important values:
                    //1. inventory_dictionary
                    //2. count_inventory


                    /////////////////////////////////////////////////////////////////

                    ///A. COMPUTING FOR THE MULTIPLIER
                    float feedOffered = Float.parseFloat(replacement_feeding_record_offered.getText().toString());
                    float feedRefused = Float.parseFloat(replacement_feeding_record_refused.getText().toString());
                    float multiplierOffered = feedOffered/count_inventory;
                    float mutiplierRefused = feedRefused/count_inventory;


                    ///B. REPLACE THE VALUES OF THE KEYS IN inventory_dictionary WITH THEIR COUNT

                    ArrayList<Integer> arrayList1 = new ArrayList<>();
                    for (int i=0;i<arrayList_temp.size();i++){
                        if(arrayList1.contains(arrayList_temp.get(i).getId())){
                            //skip
                        }else{

                            ArrayList<Float> multiplier = new ArrayList<>();
                            multiplier.add(inventory_dictionary.get(arrayList_temp.get(i).getId()).get(0)*multiplierOffered);
                            multiplier.add(inventory_dictionary.get(arrayList_temp.get(i).getId()).get(1)*mutiplierRefused);
                            inventory_dictionary.put(arrayList_temp.get(i).getId(),multiplier);
                            arrayList1.add(arrayList_temp.get(i).getId());
                        }
                    }


                   // Toast.makeText(getActivity(), inventory_dictionary.toString(), Toast.LENGTH_SHORT).show();


                    ///C. INSERTING FEEDING RECORD TO THE DATABASE BY BATCH
                    ///C. INSERTING FEEDING RECORD TO THE DATABASE BY BATCH
                    boolean isNetworkAvailable = isNetworkAvailable();
                    for ( Map.Entry<Integer, ArrayList<Float>> entry : inventory_dictionary.entrySet()) {
                        Integer key = entry.getKey();
                        Float valueOffered = entry.getValue().get(0);
                        Float valueRefused = entry.getValue().get(1);
                        // do something with key and/or tab
                        boolean isInserted = myDb.insertDataReplacementFeedingRecords(key ,replacement_feeding_date_collected.getText().toString(), valueOffered, valueRefused,replacement_feeding_record_remarks.getText().toString(),null);


                        if(isNetworkAvailable){

                            RequestParams requestParams = new RequestParams();
                            requestParams.add("replacement_inventory_id", key.toString());
                            requestParams.add("date_collected", replacement_feeding_date_collected.getText().toString());
                            requestParams.add("amount_offered", valueOffered.toString());
                            requestParams.add("amount_refused", valueRefused.toString());
                            requestParams.add("remarks", replacement_feeding_record_remarks.getText().toString());
                            requestParams.add("deleted_at", null);



                            while(!API_addReplacementFeeding(requestParams)){
                                //do nothing
                            }

                        }


                        if(isInserted){
                            Toast.makeText(getContext(),"Added to database", Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(getContext(),"Error inserting record with Inventory id: "+key, Toast.LENGTH_SHORT).show();
                            getDialog().dismiss();

                        }
                    }

                   // API_updateBrooderFeeding();
                    Intent intent = new Intent(getActivity(), ReplacementFeedingRecordsActivity.class);
                    intent.putExtra("Replacement Pen",replacement_pen);
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
    private boolean API_addReplacementFeeding(RequestParams requestParams){
        APIHelperAsync.addReplacementFeeding("addReplacementFeeding", requestParams, new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response){
//                Toast.makeText(getActivity(), "Successfully added brooder feeding record to web", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonResponse, Object response){

//                 Toast.makeText(getActivity(), "Failed to add brooder feeding record to web", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable{
                return null;
            }
        });
        return true;
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) this.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


}