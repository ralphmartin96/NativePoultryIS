package com.example.cholomanglicmot.nativechickenandduck.BreedersDirectory;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.example.cholomanglicmot.nativechickenandduck.APIHelper;
import com.example.cholomanglicmot.nativechickenandduck.DatabaseHelper;
import com.example.cholomanglicmot.nativechickenandduck.PensDirectory.Pen;
import com.example.cholomanglicmot.nativechickenandduck.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import cz.msebera.android.httpclient.Header;

public class CreateHatcheryRecordDialog extends DialogFragment{
    private EditText date_eggs_set,number_eggs_set,            fertile,    eggs_hatched,            date_eggs_hatched;
    Switch brooder_switch_other_day;
    LinearLayout yes;
    Spinner outside_generation_spinner,outside_line_spinner,outside_family_spinner,pen;

    private Button mActionOk;
    DatabaseHelper myDb;
    Calendar calendar,calendar2;
    ArrayList<Breeder_Inventory> arrayListBrooderInventory = new ArrayList<>();
    ArrayList<Breeder_Inventory>arrayList_temp = new ArrayList<>();
    boolean isIncludedInSystem = false;
    Random random = new Random();
    ArrayList<Pen> arrayListPen = new ArrayList<>();
    ArrayList<Pen> arrayListPen2 = new ArrayList<>();
    private String brooder_pen;
    Integer brooder_pen_id, batching_week2;
    String brooder_tag2,formatted;
    Integer age_weeks = 0;
    Integer farm_id=0;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_create_hatchery_records, container, false);
        myDb = new DatabaseHelper(getContext());
        final String breeder_tag = getArguments().getString("Breeder Tag");


        date_eggs_set= view.findViewById(R.id.date_eggs_set);
        number_eggs_set= view.findViewById(R.id.number_eggs_set);
        fertile= view.findViewById(R.id.fertile);
        eggs_hatched= view.findViewById(R.id.eggs_hatched);
        brooder_switch_other_day= view.findViewById(R.id.brooder_switch_other_day);
        yes= view.findViewById(R.id.yes);
        outside_generation_spinner= view.findViewById(R.id.outside_generation_spinner);
        outside_line_spinner= view.findViewById(R.id.outside_line_spinner);
        outside_family_spinner= view.findViewById(R.id.outside_family_spinner);
        date_eggs_hatched= view.findViewById(R.id.date_eggs_hatched);

        pen= view.findViewById(R.id.pen);

        loadSpinnerDataForPen();
        loadSpinnerDataForGeneration();
        outside_generation_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selected_generation = outside_generation_spinner.getSelectedItem().toString();
                loadSpinnerDataForLine(selected_generation);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        outside_line_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selected_generation = outside_generation_spinner.getSelectedItem().toString();
                String selected_line = outside_line_spinner.getSelectedItem().toString();
                loadSpinnerDataForFamily(selected_line, selected_generation);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        pen.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selected_generation = pen.getSelectedItem().toString();
                //loadSpinnerDataForLine(selected_generation);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        date_eggs_set.setOnClickListener(new View.OnClickListener() {
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
                        date_eggs_set.setText(selectedYear + "-" + selectedMonth + "-" + selectedDay);
                        calendar.set(selectedYear,selectedMonth,selectedDay);
                    }
                }, year, month, day);
                mDatePicker.show();

            }
        });


        FirebaseAuth mAuth;

        mAuth = FirebaseAuth.getInstance();

        FirebaseUser user = mAuth.getCurrentUser();

        String name = user.getDisplayName();

        String email = user.getEmail();

        Uri photo = user.getPhotoUrl();

        Cursor cursor_farm_id = myDb.getFarmIDFromUsers(email);
        cursor_farm_id.moveToFirst();
        if(cursor_farm_id.getCount() != 0){
            farm_id = cursor_farm_id.getInt(0);
        }

        ///GET BATCHING WEEK FROM DATABASE
        Cursor cursor1 = myDb.getAllDataFromFarms(farm_id);
        cursor1.moveToFirst();

        if(cursor1.getCount() != 0){

            batching_week2 = cursor1.getInt(4);

        }

        date_eggs_hatched.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar2 = Calendar.getInstance();
                int year = calendar2.get(Calendar.YEAR);
                int month = calendar2.get(Calendar.MONTH);
                int day = calendar2.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int selectedYear, int selectedMonth, int selectedDay) {
                        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
                        calendar2.set(selectedYear,selectedMonth,selectedDay+7*batching_week2);
                        formatted = format1.format(calendar2.getTime());
                        selectedMonth++;
                        date_eggs_hatched.setText(selectedYear + "-" + selectedMonth + "-" + selectedDay);

                    }
                }, year, month, day);
                mDatePicker.show();

            }
        });

        yes.setVisibility(View.GONE);
        brooder_switch_other_day.setChecked(false);

        brooder_switch_other_day.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {

                if (isChecked) {

                    yes.setVisibility(View.VISIBLE);
                    isIncludedInSystem = true;



                } else {


                    yes.setVisibility(View.GONE);
                    isIncludedInSystem = false;


                }

            }
        });
        boolean isNetworkAvailable = isNetworkAvailable();

        mActionOk = view.findViewById(R.id.action_ok);
        mActionOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!date_eggs_set.getText().toString().isEmpty()  && !number_eggs_set.getText().toString().isEmpty() ){





                    Integer number_fertile;
                    Integer hatched;
                    String date_hatched;


                    Cursor cursor = myDb.getAllDataFromBreederInventory();
                    cursor.moveToFirst();

                    if(cursor.getCount() != 0){
                        do{
                            Breeder_Inventory breeder_inventory = new  Breeder_Inventory(cursor.getInt(0), cursor.getInt(1), cursor.getInt(2), cursor.getString(3), cursor.getString(4), cursor.getInt(5), cursor.getInt(6), cursor.getInt(7), cursor.getString(8),cursor.getString(9),cursor.getString(10),cursor.getString(11),cursor.getString(12));
                            arrayListBrooderInventory.add(breeder_inventory);

                        }while(cursor.moveToNext());
                    }


                    for(int i=0;i<arrayListBrooderInventory.size();i++){
                        if(arrayListBrooderInventory.get(i).getBreeder_inv_breeder_tag().equals(breeder_tag)){
                            arrayList_temp.add(arrayListBrooderInventory.get(i));
                        }
                    }




////////////////////////

                    //allows null input values
                    if(fertile.getText().toString().isEmpty()){
                        number_fertile = 0;
                    }else{
                        number_fertile = Integer.parseInt(fertile.getText().toString());
                    }
                    if(eggs_hatched.getText().toString().isEmpty()){
                        hatched = 0;

                    }else{
                        hatched = Integer.parseInt(eggs_hatched.getText().toString());
                    }
                    if(date_eggs_hatched.getText().toString().isEmpty()){
                        date_hatched = "";
                    }else{
                        date_hatched = date_eggs_hatched.getText().toString();
                    }

                    long age_weeks_long;

                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

                    try {
                        Date date1 = format.parse(date_eggs_set.getText().toString());
                        Date date2 = format.parse(formatted);
                        long diff = date2.getTime() - date1.getTime();
                        age_weeks_long = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
                        age_weeks = (int) age_weeks_long/7;

                    } catch (ParseException e) {
                        e.printStackTrace();

                    }

                   // Toast.makeText(getActivity(), date_eggs_set.getText().toString()+"-"+formatted, Toast.LENGTH_SHORT).show();
                    //if data is complete and
                    if(isIncludedInSystem){
                        if(!fertile.getText().toString().equals(0) && !eggs_hatched.getText().toString().equals(0) && !date_eggs_hatched.getText().toString().equals("") && !outside_generation_spinner.getSelectedItem().toString().isEmpty() && !outside_line_spinner.getSelectedItem().toString().isEmpty() && !outside_family_spinner.getSelectedItem().toString().isEmpty() && !pen.getSelectedItem().toString().isEmpty()){
                            int m = random.nextInt(100); //GAWAN MO NG RANDOMIZER NA TULAD NG KAY SHANNON
                            //add in the system
                            ///////////////////////////////////test

                            Cursor cursorBrooderChecker = myDb.familyChecker(outside_family_spinner.getSelectedItem().toString(),outside_line_spinner.getSelectedItem().toString(),outside_generation_spinner.getSelectedItem().toString());
                            cursorBrooderChecker.moveToFirst();
                            //kapag wala pang laman

                            //isInternetAvailable -> API
                            //else, local saving
                            brooder_pen = pen.getSelectedItem().toString();
                            if(cursorBrooderChecker.getCount()==0){
                                Integer familyID = null;
                                Cursor cursor_familyID = myDb.getFamilyID(outside_family_spinner.getSelectedItem().toString(),outside_line_spinner.getSelectedItem().toString(),outside_generation_spinner.getSelectedItem().toString());
                                cursor_familyID.moveToFirst();
                                if(cursor_familyID.getCount() != 0){
                                    familyID = cursor_familyID.getInt(0);
                                }
                                boolean isInserted = myDb.insertDataBrooder(familyID,date_eggs_hatched.getText().toString(),null);

                                if(isNetworkAvailable) {


                                    RequestParams requestParams = new RequestParams();
                                    requestParams.add("family_id", familyID.toString());
                                    requestParams.add("date_added", date_eggs_hatched.getText().toString());
                                    requestParams.add("deleted_at", null);

                                    API_addBrooder(requestParams);

                                }

                                Cursor cursor_pen1 = myDb.getAllDataFromPenWhere(brooder_pen);
                                cursor_pen1.moveToFirst();
                                if(cursor_pen1.getCount() != 0){
                                    brooder_pen_id = cursor_pen1.getInt(0);
                                }


                                Cursor cursor_pen = myDb.getAllDataFromPen();
                                cursor_pen.moveToFirst();
                                if(cursor_pen.getCount() == 0){
                                }else{
                                    do{
                                        if(cursor_pen != null){
                                            Pen pen = new Pen(cursor_pen.getInt(0),cursor_pen.getString(2), cursor_pen.getString(3), cursor_pen.getInt(4), cursor_pen.getInt(5), cursor_pen.getInt(1), cursor_pen.getInt(6));
                                            arrayListPen.add(pen);
                                        }
                                    }while (cursor_pen.moveToNext());
                                }

                                for (int i = 0; i<arrayListPen.size();i++){
                                    if(arrayListPen.get(i).getPen_number().equals(brooder_pen)){
                                        arrayListPen2.add(arrayListPen.get(i));
                                    }
                                }

                                int total = arrayListPen2.get(0).getPen_inventory();
                                int current = arrayListPen2.get(0).getPen_capacity();

                                //GET ID OF INSERTED BROODER

                                Cursor cursor1 = myDb.getIDFromBroodersWhere(familyID);
                                cursor1.moveToFirst();



                                Integer id = cursor1.getInt(0);

                                // boolean isInventoryInserted = myDb.insertDataBrooderInventory(id,brooder_pen, "QUEBAI"+generation_spinner.getSelectedItem().toString()+line_spinner.getSelectedItem().toString()+family_spinner.getSelectedItem().toString()+m, brooder_date_added.getText().toString()+m, null,null,Integer.parseInt(brooder_total_number.getText().toString()),null,null);
                                brooder_tag2 = generateBrooderTag();
                                boolean isInventoryInserted = myDb.insertDataBrooderInventory(id,brooder_pen_id, brooder_tag2, formatted, null,null,Integer.parseInt(eggs_hatched.getText().toString()),date_eggs_hatched.getText().toString(),null);
                                boolean isIn = myDb.insertHatcheryRecords(arrayList_temp.get(0).getId(), date_eggs_set.getText().toString(), formatted, Integer.parseInt(number_eggs_set.getText().toString()),age_weeks,number_fertile,hatched,date_hatched,null);
                                Integer id_0=null;
                                Cursor cursor2 = myDb.getDataFromBrooderInventoryWhereTag(brooder_tag2);
                                cursor2.moveToFirst();
                                if(cursor2.getCount() != 0){
                                    id_0 = cursor2.getInt(0);
                                }


                                if(isNetworkAvailable){
                                    //boolean isInventoryInsertedOnline = myDb.insertDataBrooderInventory(id,brooder_pen_id, brooder_tag, brooder_estimated_date_of_hatch.getText().toString(), 0,0,Integer.parseInt(brooder_total_number.getText().toString()),null,null);

                                    RequestParams requestParams = new RequestParams();
                                    requestParams.add("id", id_0.toString());
                                    requestParams.add("broodergrower_id", id.toString());
                                    requestParams.add("pen_id", brooder_pen_id.toString());
                                    requestParams.add("broodergrower_tag", brooder_tag2);
                                    requestParams.add("batching_date",  formatted);
                                    requestParams.add("number_male", null);
                                    requestParams.add("number_female", null);
                                    requestParams.add("total", eggs_hatched.getText().toString());
                                    requestParams.add("last_update", date_eggs_hatched.getText().toString());
                                    requestParams.add("deleted_at", null);

                                    API_addBrooderInventory(requestParams);

                                   //boolean isIn = myDb.insertHatcheryRecords(arrayList_temp.get(0).getId(), date_eggs_set.getText().toString(), null, Integer.parseInt(number_eggs_set.getText().toString()),age_weeks,number_fertile,hatched,date_hatched,null);

                                    RequestParams requestParams1 = new RequestParams();
                                    //requestParams1.add("id", id_0.toString());
                                    requestParams1.add("breeder_inventory_id", arrayList_temp.get(0).getId().toString());
                                    requestParams1.add("date_eggs_set", date_eggs_set.getText().toString());
                                    requestParams1.add("batching_date",  formatted);
                                    requestParams1.add("number_eggs_set", number_eggs_set.getText().toString());
                                    requestParams1.add("week_of_lay", age_weeks.toString());
                                    requestParams1.add("number_fertile", number_fertile.toString());
                                    requestParams1.add("number_hatched", hatched.toString());
                                    requestParams1.add("date_hatched", date_hatched);
                                    requestParams1.add("deleted_at", null);

                                    API_addHatcheryRecords(requestParams1);

                                }
                                boolean isPenUpdated = myDb.updatePen(brooder_pen, "Brooder", Integer.parseInt(eggs_hatched.getText().toString())+total,current);
                                if(isPenUpdated  && isInventoryInserted && isInserted){
                                    Toast.makeText(getActivity(), "Successfully added to database", Toast.LENGTH_SHORT).show();
                                    Intent intent_line = new Intent(getActivity(), HatcheryRecords.class);
                                    intent_line.putExtra("Breeder Tag", breeder_tag);
                                    startActivity(intent_line);


                                }else{
                                    Toast.makeText(getActivity(), "Error in adding to the database", Toast.LENGTH_SHORT).show();
                                }

                            }else{ //KUNG MAY LAMAN YUNG DATABASE BASED SA VALUES KANINA

                                Cursor cursor_pen = myDb.getAllDataFromPen();
                                cursor_pen.moveToFirst();
                                if(cursor_pen.getCount() == 0){
                                    //
                                }else{
                                    do{
                                        if(cursor_pen != null){
                                            Pen pen = new Pen(cursor_pen.getInt(0),cursor_pen.getString(2), cursor_pen.getString(3), cursor_pen.getInt(4), cursor_pen.getInt(5), cursor_pen.getInt(1), cursor_pen.getInt(6));
                                            arrayListPen.add(pen);

                                        }

                                    }while(cursor_pen.moveToNext());
                                }


                                for (int i = 0; i<arrayListPen.size();i++){
                                    if(arrayListPen.get(i).getPen_number().equals(brooder_pen)){
                                        arrayListPen2.add(arrayListPen.get(i));
                                    }
                                }

                                int total = arrayListPen2.get(0).getPen_inventory();
                                int current = arrayListPen2.get(0).getPen_capacity();

                                brooder_pen = pen.getSelectedItem().toString();
                                Cursor cursor_pen1 = myDb.getAllDataFromPenWhere(brooder_pen);
                                cursor_pen1.moveToFirst();
                                if(cursor_pen1.getCount() != 0){
                                    brooder_pen_id = cursor_pen1.getInt(0);
                                }


                                brooder_tag2 = generateBrooderTag();
                                Integer brooder_id = cursorBrooderChecker.getInt(0);
                                boolean isPenUpdated = myDb.updatePen(brooder_pen, "Brooder", (Integer.parseInt(eggs_hatched.getText().toString())+total),current);
                                boolean isInventoryInserted = myDb.insertDataBrooderInventory(brooder_id,brooder_pen_id, brooder_tag2, formatted, null,null,Integer.parseInt(eggs_hatched.getText().toString()),date_eggs_hatched.getText().toString(),null);
                                boolean isInserted = myDb.insertHatcheryRecords(arrayList_temp.get(0).getId(), date_eggs_set.getText().toString(), null, Integer.parseInt(number_eggs_set.getText().toString()),age_weeks,number_fertile,hatched,date_hatched,null);

                                Integer id_0=null;
                                Cursor cursor2 = myDb.getDataFromBrooderInventoryWhereTag(brooder_tag2);
                                cursor2.moveToFirst();
                                if(cursor2.getCount() != 0){
                                    id_0 = cursor2.getInt(0);
                                }
                                if(isNetworkAvailable){

                                    RequestParams requestParams = new RequestParams();
                                    requestParams.add("id", id_0.toString());
                                    requestParams.add("broodergrower_id", brooder_id.toString());
                                    requestParams.add("pen_id", brooder_pen_id.toString());
                                    requestParams.add("broodergrower_tag", brooder_tag2);
                                    requestParams.add("batching_date", formatted);
                                    requestParams.add("number_male", null);
                                    requestParams.add("number_female", null);
                                    requestParams.add("total", eggs_hatched.getText().toString());
                                    requestParams.add("last_update", date_eggs_hatched.getText().toString());
                                    requestParams.add("deleted_at", null);

                                    API_addBrooderInventory(requestParams);

                                    RequestParams requestParams1 = new RequestParams();
                                    //requestParams1.add("id", id_0.toString());
                                    requestParams1.add("breeder_inventory_id", arrayList_temp.get(0).getId().toString());
                                    requestParams1.add("date_eggs_set", date_eggs_set.getText().toString());
                                    requestParams1.add("batching_date",  formatted);
                                    requestParams1.add("number_eggs_set", number_eggs_set.getText().toString());
                                    requestParams1.add("week_of_lay", age_weeks.toString());
                                    requestParams1.add("number_fertile", number_fertile.toString());
                                    requestParams1.add("number_hatched", hatched.toString());
                                    requestParams1.add("date_hatched", date_hatched);
                                    requestParams1.add("deleted_at", null);

                                    API_addHatcheryRecords(requestParams1);

                                }
                                if(isPenUpdated && isInventoryInserted){
                                    Toast.makeText(getActivity(), "Successfully added to database", Toast.LENGTH_SHORT).show();/*String.format("%04d" , Integer.parseInt(mInput_generation_number.getText().toString()));*/
                                    Intent intent_line = new Intent(getActivity(), HatcheryRecords.class);
                                    intent_line.putExtra("Breeder Tag", breeder_tag);
                                    startActivity(intent_line);

                                }else{
                                    Toast.makeText(getActivity(),"Brooder not added to database", Toast.LENGTH_SHORT).show();
                                }
                                //   boolean isInventoryInserted = myDb.insertDataBrooderInventory(brooder_id,brooder_pen, "QUEBAI"+generation_spinner.getSelectedItem().toString()+line_spinner.getSelectedItem().toString()+family_spinner.getSelectedItem().toString()+m, brooder_date_added.getText().toString()+m, null,null,Integer.parseInt(brooder_total_number.getText().toString()),null,null);




                            }



                            ///////////////////////////////////endOfTest
                        }else{
                            Toast.makeText(getContext(),"Fill all forms to include brooder in the system", Toast.LENGTH_LONG).show();
                        }
                    }else{

                        //dont include in the system
                        boolean isInserted = myDb.insertHatcheryRecords(arrayList_temp.get(0).getId(), date_eggs_set.getText().toString(), null, Integer.parseInt(number_eggs_set.getText().toString()),age_weeks,number_fertile,hatched,date_hatched,null);
                        if(isNetworkAvailable){
                            RequestParams requestParams1 = new RequestParams();
                            //requestParams1.add("id", id_0.toString());
                            requestParams1.add("breeder_inventory_id", arrayList_temp.get(0).getId().toString());
                            requestParams1.add("date_eggs_set", date_eggs_set.getText().toString());
                            requestParams1.add("batching_date",  formatted);
                            requestParams1.add("number_eggs_set", number_eggs_set.getText().toString());
                            requestParams1.add("week_of_lay", age_weeks.toString());
                            requestParams1.add("number_fertile", number_fertile.toString());
                            requestParams1.add("number_hatched", hatched.toString());
                            requestParams1.add("date_hatched", date_hatched);
                            requestParams1.add("deleted_at", null);

                            API_addHatcheryRecords(requestParams1);
                        }

                        if (isInserted == true){
                            //Toast.makeText(getContext(),"Hatchery Record added to database", Toast.LENGTH_LONG).show();
                            Intent intent_line = new Intent(getActivity(), HatcheryRecords.class);
                            intent_line.putExtra("Breeder Tag", breeder_tag);
                            startActivity(intent_line);


                        }else{
                            Toast.makeText(getContext(),"Error adding to database", Toast.LENGTH_LONG).show();
                        }
                    }








                }else{
                    Toast.makeText(getContext(),"Date Eggs Set and Number of Eggs Set required", Toast.LENGTH_LONG).show();
                }




            }

        });

        return view;

    }
    public static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
        long diffInMillies = date2.getTime() - date1.getTime();
        return timeUnit.convert(diffInMillies,TimeUnit.MILLISECONDS);
    }
    private void loadSpinnerDataForGeneration() {

        DatabaseHelper db = new DatabaseHelper(getContext());
        List<String> generations = db.getAllDataFromGenerationasList();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, generations);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        outside_generation_spinner.setAdapter(dataAdapter);

    }

    public void loadSpinnerDataForLine(String selected_generation){
        DatabaseHelper db = new DatabaseHelper(getContext());
        List<String> lines = db.getAllDataFromLineasList(selected_generation);
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, lines);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        outside_line_spinner.setAdapter(dataAdapter2);
    }
    public void loadSpinnerDataForFamily(String selected_line, String selected_generation){
        DatabaseHelper db = new DatabaseHelper(getContext());
        List<String> lines = db.getAllDataFromFamilyasList(selected_line, selected_generation);
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, lines);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        outside_family_spinner.setAdapter(dataAdapter2);
    }
    private void loadSpinnerDataForPen() {

        DatabaseHelper db = new DatabaseHelper(getContext());

        List<String> generations = db.getAllDataFromBrooderPenasList();

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, generations);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        pen.setAdapter(dataAdapter);


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
    private void API_addBrooderInventory(RequestParams requestParams){
        APIHelper.addBrooderInventory("addBrooderInventory", requestParams, new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response){
//                Toast.makeText(getActivity(), "Successfully added brooder inventory to web", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonResponse, Object response){

                Toast.makeText(getActivity(), "Failed to add brooder inventory to web", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable{
                return null;
            }
        });
    }

    private void API_addHatcheryRecords(RequestParams requestParams){
        APIHelper.addHatcheryRecords("addHatcheryRecords", requestParams, new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response){
//                Toast.makeText(getActivity(), "Successfully added brooder inventory to web", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonResponse, Object response){

                Toast.makeText(getActivity(), "Failed to add hatchery record to web", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable{
                return null;
            }
        });
    }
    private String generateBrooderTag() {
        String tag= null;
        String code = null;
        String timestamp;


        Cursor cursor = myDb.getAllDataFromFarms(farm_id);
        cursor.moveToFirst();
        if(cursor.getCount() != 0){
            code = cursor.getString(2);
        }
        Random rd = new Random();

        Date date= new Date();

        Long time = date.getTime();
        time = time/1000;
        int random = rd.nextInt(100);
        tag = code+Integer.toHexString(random)+time.toString();

        return tag;

    }

    private void API_addBrooder(RequestParams requestParams){
        APIHelper.addBrooderFamily("addBrooderFamily", requestParams, new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response){
                Toast.makeText(getContext(), "Successfully added to web", Toast.LENGTH_SHORT).show();
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