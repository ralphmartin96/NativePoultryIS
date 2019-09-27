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
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
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

public class CreateBrooderGrowthRecordDialog extends DialogFragment {

    private static final String TAG = "CreateFamilyDialog";
    private EditText brooder_growth_other_day,brooder_growth_date_added,brooder_growth_total_weight,brooder_growth_male_weight,brooder_growth_female_weight;
    private RadioGroup radio_group_collection_day;
    private RadioButton radioButton,radioButton2,radioButton3,  radioButtonNo,   radioButtonYes;
    private LinearLayout linear_total, linear_male, linear_female;
    Integer male_count, female_count, total_count;
    Float total_weight;
    Integer brooder_pen_id;
    boolean isSexingCheched, isOtherDayChecked;
    private Button mActionOk;
    DatabaseHelper myDb;
    Calendar calendar;
    ArrayList<Brooder_Inventory>arrayListBrooderInventory = new ArrayList<>();
    ArrayList<Brooder_Inventory>arrayList_temp = new ArrayList<>();
    Switch other_day_switch, sexing_switch;
    Float total1;

    Map<Integer, ArrayList<Float>> inventory_dictionary = new LinkedHashMap<Integer, ArrayList<Float>>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_create_brooder_growth_records, container, false);
        myDb = new DatabaseHelper(getContext());
        final String brooder_pen = getArguments().getString("Brooder Pen");


        other_day_switch = view.findViewById(R.id.brooder_switch_other_day);
        sexing_switch = view.findViewById(R.id.brooder_growth_sexing_done);
        brooder_growth_other_day = view.findViewById(R.id.brooder_growth_other_day);
        radio_group_collection_day = view.findViewById(R.id.radio_group_collection_day);
        brooder_growth_date_added = view.findViewById(R.id.brooder_growth_date_added);
        brooder_growth_total_weight = view.findViewById(R.id.brooder_growth_total_weight);
        brooder_growth_male_weight = view.findViewById(R.id.brooder_growth_male_weight);
        brooder_growth_female_weight = view.findViewById(R.id.brooder_growth_female_weight);
        linear_total = view.findViewById(R.id.linear_total);
        linear_male = view.findViewById(R.id.linear_male);
        linear_female = view.findViewById(R.id.linear_female);
        mActionOk = view.findViewById(R.id.action_ok);
        radio_group_collection_day = view.findViewById(R.id.radio_group_collection_day);
        radioButton = view.findViewById(R.id.radioButton);
        radioButton2 = view.findViewById(R.id.radioButton2);




        other_day_switch.setChecked(false);
        brooder_growth_other_day.setVisibility(View.GONE);
        radio_group_collection_day.setVisibility(View.VISIBLE);
        other_day_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {

                if (isChecked) {
                    brooder_growth_other_day.setVisibility(View.VISIBLE);
                    radio_group_collection_day.setVisibility(View.GONE);
                    isOtherDayChecked = true;


                } else {

                    brooder_growth_other_day.setVisibility(View.GONE);
                    radio_group_collection_day.setVisibility(View.VISIBLE);
                    isOtherDayChecked = false;

                }

            }
        });
        final int day_0 = 1000;
        final int day_21 = 1001;
        radioButton.setId(day_0);
        radioButton2.setId(day_21);



        sexing_switch.setChecked(false);
        linear_total.setVisibility(View.VISIBLE);
        linear_male.setVisibility(View.GONE);
        linear_female.setVisibility(View.GONE);
        sexing_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {

                if (isChecked) {
                    linear_total.setVisibility(View.GONE);
                    linear_male.setVisibility(View.VISIBLE);
                    linear_female.setVisibility(View.VISIBLE);
                    isSexingCheched = true;

                    //total_count = 0;
                    //male_count = Integer.parseInt(brooder_growth_male_weight.getText().toString());

                } else {

                    linear_total.setVisibility(View.VISIBLE);
                    linear_male.setVisibility(View.GONE);
                    linear_female.setVisibility(View.GONE);


                    isSexingCheched = false;



                }

            }
        });


        brooder_growth_date_added.setOnClickListener(new View.OnClickListener() {
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
                        brooder_growth_date_added.setText(selectedYear + "-" + selectedMonth + "-" + selectedDay);
                        calendar.set(selectedYear,selectedMonth,selectedDay);
                    }
                }, year, month, day);
                mDatePicker.show();

            }
        });

        Cursor cursor1 = myDb.getAllDataFromPenWhere(brooder_pen);
        cursor1.moveToFirst();
        if(cursor1.getCount() != 0){
            brooder_pen_id = cursor1.getInt(0);
        }




        mActionOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer brooder_growth_collection = new Integer(0);
                if(isOtherDayChecked){
                    brooder_growth_collection = Integer.parseInt(brooder_growth_other_day.getText().toString());
                }else{
                    int selectedDay = radio_group_collection_day.getCheckedRadioButtonId();
                    //////for radio button

                    switch (selectedDay) {
                        case day_0:
                            // the first RadioButton is checked.
                            brooder_growth_collection = 0;

                            break;
                        //other checks for the other RadioButtons ids from the RadioGroup
                        case day_21:
                            // the first RadioButton is checked.
                            brooder_growth_collection = 21;
                            break;
                        //other checks for the other RadioButtons ids from the RadioGroup

                        case -1:
                            brooder_growth_collection = 0;
                            // no RadioButton is checked inthe Radiogroup
                            break;
                    }
                }


                //////for radio button
//(brooder_growth_collection != 0 || !brooder_growth_other_day.getText().toString().isEmpty()) && !brooder_growth_date_added.getText().toString().isEmpty() && (!brooder_growth_total_weight.getText().toString().isEmpty() || !brooder_growth_male_weight.getText().toString().isEmpty() || !brooder_growth_female_weight.getText().toString().isEmpty()
                if((brooder_growth_collection != 1 || !brooder_growth_other_day.getText().toString().isEmpty())  && (!brooder_growth_total_weight.getText().toString().isEmpty() || !brooder_growth_male_weight.getText().toString().isEmpty() || !brooder_growth_female_weight.getText().toString().isEmpty())){

                    /////////////////////////////DATABASE OPERATIONS
                    Cursor cursor_brooder_inventory = myDb.getAllDataFromBrooderInventory(); //para sa pagstore ng data sa arraylist
                    cursor_brooder_inventory.moveToFirst();
                    if(cursor_brooder_inventory.getCount() == 0){
                        //show message
                        Toast.makeText(getActivity(),"No data.", Toast.LENGTH_SHORT).show();

                    }else {
                        do {

                            String deleted_at = cursor_brooder_inventory.getString(9);
                            if(deleted_at == null){
                                Brooder_Inventory brooder_inventory = new Brooder_Inventory(cursor_brooder_inventory.getInt(0),cursor_brooder_inventory.getInt(1), cursor_brooder_inventory.getInt(2), cursor_brooder_inventory.getString(3),cursor_brooder_inventory.getString(4), cursor_brooder_inventory.getInt(5), cursor_brooder_inventory.getInt(6),cursor_brooder_inventory.getInt(7), cursor_brooder_inventory.getString(8), cursor_brooder_inventory.getString(9));
                                arrayListBrooderInventory.add(brooder_inventory);

                            }

                        } while (cursor_brooder_inventory.moveToNext());
                    }

                    for (int i=0;i<arrayListBrooderInventory.size();i++){
                        if(arrayListBrooderInventory.get(i).getBrooder_inv_pen() == brooder_pen_id ){

                            arrayList_temp.add(arrayListBrooderInventory.get(i)); //ito na yung list ng inventory na nasa pen

                        }
                    }

                    //GETTING TOTAL BROODER INVENTORY COUNT
                    int count_inventory = 0;
                    int count_male = 0;
                    int count_female = 0;

                    for(int i=0;i<arrayList_temp.size();i++){
                        count_inventory = count_inventory+arrayList_temp.get(i).getBrooder_total_quantity();
                    }

                    for(int i=0;i<arrayList_temp.size();i++){
                        count_male = count_male+arrayList_temp.get(i).getBrooder_male_quantity();
                    }


                    for(int i=0;i<arrayList_temp.size();i++){
                        count_female = count_female+arrayList_temp.get(i).getBrooder_female_quantity();
                    }
                    //




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
                            arrayList2.add((float)arrayList_temp.get(i).getBrooder_total_quantity());
                            arrayList2.add((float)arrayList_temp.get(i).getBrooder_male_quantity());
                            arrayList2.add((float)arrayList_temp.get(i).getBrooder_female_quantity());
                            inventory_dictionary.put(arrayList_temp.get(i).getId(),arrayList2);
                            arrayList.add(arrayList_temp.get(i).getId());
                        }else if(arrayList.contains(arrayList_temp.get(i).getId())){


                            ArrayList<Float> arrayList3 = new ArrayList<>();
                            arrayList3.add(inventory_dictionary.get(arrayList_temp.get(i).getId()).get(0)+arrayList_temp.get(i).getBrooder_total_quantity());
                            arrayList3.add(inventory_dictionary.get(arrayList_temp.get(i).getId()).get(1)+arrayList_temp.get(i).getBrooder_male_quantity());
                            arrayList3.add(inventory_dictionary.get(arrayList_temp.get(i).getId()).get(2)+arrayList_temp.get(i).getBrooder_female_quantity());


                            inventory_dictionary.put(arrayList_temp.get(i).getId(),arrayList3);
                        }
                    }
                    //buffer.append("inventory_dictionary  "+inventory_dictionary.toString()+"\n\n");


                    ////important values:
                    //1. inventory_dictionary
                    //2. count_inventory


                    /////////////////////////////////////////////////////////////////

                    ///A. COMPUTING FOR THE MULTIPLIER

                    float maleWeight, femaleWeight, totalWeight = 0.0f;
                    if (isSexingCheched){

                        maleWeight = Float.parseFloat(brooder_growth_male_weight.getText().toString());
                        femaleWeight = Float.parseFloat(brooder_growth_female_weight.getText().toString());
                        totalWeight = (Float.parseFloat(brooder_growth_male_weight.getText().toString())+ Float.parseFloat(brooder_growth_female_weight.getText().toString()));
                        //totalWeight = 0.0f;

                    }else{
                        maleWeight = 0.0f;
                        femaleWeight = 0.0f;
                        totalWeight = Float.parseFloat(brooder_growth_total_weight.getText().toString());
                    }


                    float multiplierTotalWeight = totalWeight/count_inventory;
                    float multiplierMaleWeight = maleWeight/count_male;
                    float multiplierFemaleWeight = femaleWeight/count_female;

                    //float mutiplierRefused = feedRefused/count_inventory;


                    ///B. REPLACE THE VALUES OF THE KEYS IN inventory_dictionary WITH THEIR COUNT

                    ArrayList<Integer> arrayList1 = new ArrayList<>();
                    for (int i=0;i<arrayList_temp.size();i++){
                        if(arrayList1.contains(arrayList_temp.get(i).getId())){
                            //skip
                        }else{

                            ArrayList<Float> multiplier = new ArrayList<>();
                            multiplier.add(inventory_dictionary.get(arrayList_temp.get(i).getId()).get(0)*multiplierTotalWeight);
                            multiplier.add(inventory_dictionary.get(arrayList_temp.get(i).getId()).get(1)*multiplierMaleWeight);
                            multiplier.add(inventory_dictionary.get(arrayList_temp.get(i).getId()).get(2)*multiplierFemaleWeight);
                            inventory_dictionary.put(arrayList_temp.get(i).getId(),multiplier);
                            arrayList1.add(arrayList_temp.get(i).getId());
                        }
                    }




                    ///C. INSERTING FEEDING RECORD TO THE DATABASE BY BATCH

                    for ( Map.Entry<Integer, ArrayList<Float>> entry : inventory_dictionary.entrySet()) {
                        Integer key = entry.getKey();
                        Float total = entry.getValue().get(0);
                        Float male = entry.getValue().get(1);
                        Float female = entry.getValue().get(2);

                        for(int i=0;i<arrayList_temp.size();i++){
                            if (arrayList_temp.get(i).getId().equals(key)){
                                if(male == 0){
                                    total1 = total;
                                }else{
                                    total1 = female+male;
                                }
                                boolean isInserted = myDb.insertDataBrooderGrowthRecords(key,brooder_growth_collection,brooder_growth_date_added.getText().toString(),arrayList_temp.get(i).getBrooder_male_quantity(),male,arrayList_temp.get(i).getBrooder_female_quantity(),female,arrayList_temp.get(i).getBrooder_total_quantity(),total,null);
                                boolean isNetworkAvailable = isNetworkAvailable();
                                if(isNetworkAvailable){
                                    RequestParams requestParams = new RequestParams();
                                    requestParams.add("broodergrower_inventory_id", key.toString());
                                    requestParams.add("collection_day", brooder_growth_collection.toString());
                                    requestParams.add("date_collected", brooder_growth_date_added.getText().toString());
                                    requestParams.add("male_quantity", arrayList_temp.get(i).getBrooder_male_quantity().toString());
                                    requestParams.add("male_weight", male.toString());
                                    requestParams.add("female_quantity", arrayList_temp.get(i).getBrooder_female_quantity().toString());
                                    requestParams.add("female_weight", female.toString());

                                    requestParams.add("total_quantity", arrayList_temp.get(i).getBrooder_total_quantity().toString());

                                    requestParams.add("total_weight", total.toString());
                                    requestParams.add("deleted_at", null);


                                    while(!API_addBrooderGrowth(requestParams)){
                                        //do nothing while the functions is not returning true
                                    }
                                }
                                if(!isInserted){
                                    Toast.makeText(getContext(),"Error inserting record with Inventory id: "+key, Toast.LENGTH_SHORT).show();
                                    getDialog().dismiss();
                                }
                            }
                        }

                    }


                    Intent intent = new Intent(getActivity(), BrooderGrowthRecordsActivity.class);
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

    private boolean API_addBrooderGrowth(RequestParams requestParams){
        APIHelperAsync.addBrooderGrowth("addBrooderGrowth", requestParams, new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response){
               // Toast.makeText(getActivity(), "Successfully added brooder growth record to web", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonResponse, Object response){

               // Toast.makeText(getContext(), "Failed to add brooder growth record to web", Toast.LENGTH_SHORT).show();
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