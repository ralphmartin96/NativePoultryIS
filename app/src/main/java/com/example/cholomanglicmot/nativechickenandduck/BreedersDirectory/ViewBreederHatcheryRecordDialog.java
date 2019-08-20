package com.example.cholomanglicmot.nativechickenandduck.BreedersDirectory;

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
import android.widget.TextView;
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

import cz.msebera.android.httpclient.Header;

public class ViewBreederHatcheryRecordDialog extends DialogFragment {

    DatabaseHelper myDb;
    TextView date_set, quantity, age_in_weeks, no_of_fertile, date_hatched, no_hatched, textView;
    LinearLayout switch1, yes;
    EditText edit_quantity, edit_age_in_weeks, edit_no_of_fertile, edit_date_hatched, edit_no_hatched, edit_date_set;
    Calendar calendar, calendar2;
    Button update, save;
    Switch brooder_switch_other_day;
    Spinner outside_family_spinner, outside_line_spinner, outside_generation_spinner, pen;
    boolean isSwitchChecked = false;
    Random random = new Random();
    private String brooder_pen;
    ArrayList<Pen> arrayListPen = new ArrayList<>();
    ArrayList<Pen> arrayListPen2 = new ArrayList<>();
    Integer brooder_pen_id;
    Integer batching_week2;
    String formatted;
    Integer farm_id=0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_view_hatchery, container, false);
        final Integer breeder_inventory_idID = getArguments().getInt("Breeder Inventory ID");
        final Integer hatchery_id = getArguments().getInt("Breeder Hatchery ID");
        final String breeder_tag = getArguments().getString("Breeder Tag");


        myDb = new DatabaseHelper(getContext());
        brooder_switch_other_day = view.findViewById(R.id.brooder_switch_other_day);
        yes = view.findViewById(R.id.yes);
        switch1 = view.findViewById(R.id.switch1);
        date_set = view.findViewById(R.id.date_set);
        quantity = view.findViewById(R.id.quantity);
        edit_no_hatched = view.findViewById(R.id.edit_no_hatched);
        age_in_weeks = view.findViewById(R.id.age_in_weeks);
        no_of_fertile = view.findViewById(R.id.no_of_fertile);
        date_hatched = view.findViewById(R.id.date_hatched);
        no_hatched = view.findViewById(R.id.no_hatched);
        textView = view.findViewById(R.id.textView);
        outside_family_spinner = view.findViewById(R.id.outside_family_spinner);
        outside_line_spinner = view.findViewById(R.id.outside_line_spinner);
        outside_generation_spinner = view.findViewById(R.id.outside_generation_spinner);
        pen = view.findViewById(R.id.pen);



        edit_quantity = view.findViewById(R.id.edit_quantity);

        edit_no_of_fertile = view.findViewById(R.id.edit_no_of_fertile);
        edit_date_hatched = view.findViewById(R.id.edit_date_hatched);
        edit_date_set = view.findViewById(R.id.edit_date_set);



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












        brooder_switch_other_day.setChecked(false);
        brooder_switch_other_day.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    yes.setVisibility(View.VISIBLE);
                    isSwitchChecked = true;
                } else {
                    yes.setVisibility(View.GONE);
                    isSwitchChecked = false;
                }
            }
        });


        save = view.findViewById(R.id.save);


        textView.setText(breeder_tag);
        Cursor cursor = myDb.getAllDataFromBreederHatcheryWhereID(hatchery_id);
        cursor.moveToFirst();
        if (cursor.getCount() != 0) {

            Integer egg_set = cursor.getInt(4);
            Integer weeks = cursor.getInt(5);
            Integer fertile = cursor.getInt(6);
            Integer hatched = cursor.getInt(7);

////////////YUNG AGE IN WEEKS DAPAT ATA CINOCOMPUTE?????????
            date_set.setText(cursor.getString(2));
            quantity.setText(egg_set.toString());
            age_in_weeks.setText(weeks.toString());
            no_of_fertile.setText(fertile.toString());
            no_hatched.setText(hatched.toString());
            date_hatched.setText(cursor.getString(8));
        }

        edit_date_set.setOnClickListener(new View.OnClickListener() {
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
                        edit_date_set.setText(selectedDay + "/" + selectedMonth + "/" + selectedYear);
                        calendar.set(selectedYear, selectedMonth, selectedDay);
                    }
                }, year, month, day);
                mDatePicker.show();

            }
        });

        ///GET BATCHING WEEK FROM DATABASE
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

        Cursor cursor1 = myDb.getAllDataFromFarms(farm_id);
        cursor1.moveToFirst();

        if(cursor1.getCount() != 0){

            batching_week2 = cursor1.getInt(4);

        }
        edit_date_hatched.setOnClickListener(new View.OnClickListener() {
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
                        calendar.set(selectedYear,selectedMonth,selectedDay+7*batching_week2);
                        formatted = format1.format(calendar.getTime());
                        selectedMonth++;
                        edit_date_hatched.setText(selectedYear + "-" + selectedMonth + "-" + selectedDay);

                    }
                }, year, month, day);
                mDatePicker.show();

            }
        });


        update = view.findViewById(R.id.update);
        //if wala pang lama yung date hatched, dapat visible yung update button, else -> "gone"
        if (!date_hatched.getText().toString().isEmpty()) {
            update.setVisibility(View.GONE);
        }

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edit_quantity.getVisibility() == View.GONE) { //if unclicked
                    switch1.setVisibility(View.VISIBLE);
                    edit_date_set.setVisibility(View.VISIBLE);
                    edit_quantity.setVisibility(View.VISIBLE);
                    edit_date_hatched.setVisibility(View.VISIBLE);
                    edit_no_of_fertile.setVisibility(View.VISIBLE);
                    edit_no_hatched.setVisibility(View.VISIBLE);

                    edit_date_set.setText(date_set.getText().toString());
                    edit_quantity.setText(quantity.getText().toString());
                    edit_date_hatched.setText(date_hatched.getText().toString());
                    edit_no_of_fertile.setText(no_of_fertile.getText().toString());
                    edit_no_hatched.setText(no_hatched.getText().toString());


                    date_set.setVisibility(View.GONE);
                    quantity.setVisibility(View.GONE);
                    date_hatched.setVisibility(View.GONE);
                    no_of_fertile.setVisibility(View.GONE);
                    no_hatched.setVisibility(View.GONE);
                    update.setText("CANCEL");

                } else {
                    switch1.setVisibility(View.GONE);
                    edit_date_set.setVisibility(View.GONE);
                    edit_quantity.setVisibility(View.GONE);
                    edit_date_hatched.setVisibility(View.GONE);
                    edit_no_of_fertile.setVisibility(View.GONE);
                    edit_no_hatched.setVisibility(View.GONE);
                    yes.setVisibility(View.GONE);
                    brooder_switch_other_day.setChecked(false);
                    edit_date_set.setText(date_set.getText().toString());
                    edit_quantity.setText(quantity.getText().toString());
                    edit_date_hatched.setText(date_hatched.getText().toString());
                    edit_no_of_fertile.setText(no_of_fertile.getText().toString());
                    edit_no_hatched.setText(no_hatched.getText().toString());

                    date_set.setVisibility(View.VISIBLE);

                    quantity.setVisibility(View.VISIBLE);
                    date_hatched.setVisibility(View.VISIBLE);
                    no_of_fertile.setVisibility(View.VISIBLE);
                    no_hatched.setVisibility(View.VISIBLE);
                    update.setText("UPDATE");

                }
            }
        });
   /*     Cursor cursor_pen1 = myDb.getAllDataFromPenWhere(brooder_pen);
        if(cursor_pen1.getCount() != 0){
            brooder_pen_id = cursor_pen1.getInt(0);
        }*/
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (edit_quantity.getVisibility() == View.VISIBLE) {
                    long age_weeks_long;
                    int age_weeks = 0;
                    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                    try {
                        Date date1 = format.parse(edit_date_set.getText().toString());
                        try {
                            Date date2 = format.parse(edit_date_hatched.getText().toString());
                            long diff = date2.getTime() - date1.getTime();
                            age_weeks_long = (diff / (1000 * 60 * 60 * 24 * 7));
                            age_weeks = (int) age_weeks_long;
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
///////////////////////////////

                    if(isSwitchChecked) {
                        if (!edit_no_of_fertile.getText().toString().equals(0) && !edit_no_hatched.getText().toString().equals(0) && !edit_date_hatched.getText().toString().isEmpty() && !outside_generation_spinner.getSelectedItem().toString().isEmpty() && !outside_line_spinner.getSelectedItem().toString().isEmpty() && !outside_family_spinner.getSelectedItem().toString().isEmpty() && !pen.getSelectedItem().toString().isEmpty()) {
                            int m = random.nextInt(100); //GAWAN MO NG RANDOMIZER NA TULAD NG KAY SHANNON
                            //add in the system
                            ///////////////////////////////////test
                            brooder_pen = pen.getSelectedItem().toString();
                            Cursor cursorBrooderChecker = myDb.familyChecker(outside_family_spinner.getSelectedItem().toString(), outside_line_spinner.getSelectedItem().toString(), outside_generation_spinner.getSelectedItem().toString());
                            cursorBrooderChecker.moveToFirst();
                            //kapag wala pang laman

                            //isInternetAvailable -> API
                            //else, local saving
                            brooder_pen = pen.getSelectedItem().toString();
                            if (cursorBrooderChecker.getCount() == 0) {
                                Integer familyID = null;
                                Cursor cursor_familyID = myDb.getFamilyID(outside_family_spinner.getSelectedItem().toString(), outside_line_spinner.getSelectedItem().toString(), outside_generation_spinner.getSelectedItem().toString());
                                cursor_familyID.moveToFirst();
                                if (cursor_familyID.getCount() != 0) {
                                    familyID = cursor_familyID.getInt(0);
                                }
                                boolean isInserted = myDb.insertDataBrooder(familyID, edit_date_hatched.getText().toString(), null);

                                if(isNetworkAvailable()) {


                                    RequestParams requestParams = new RequestParams();
                                    requestParams.add("family_id", familyID.toString());
                                    requestParams.add("date_added", edit_date_hatched.getText().toString());
                                    requestParams.add("deleted_at", null);

                                    API_addBrooder(requestParams);

                                }
                                Integer total=0;
                                Integer current=0;
                                Integer brooder_pen_id=0;
                                Cursor cursor_pen = myDb.getAllDataFromPenWhere(brooder_pen);
                                cursor_pen.moveToFirst();
                                if (cursor_pen.getCount() != 0) {
                                    brooder_pen_id = cursor_pen.getInt(0);
                                    total = cursor_pen.getInt(4);
                                    current = cursor_pen.getInt(5);
                                }


                                //GET ID OF INSERTED BROODER

                                Cursor cursor1 = myDb.getIDFromBroodersWhere(familyID);
                                cursor1.moveToFirst();
                                Integer id = cursor1.getInt(0);


                                boolean isInventoryInserted = myDb.insertDataBrooderInventory(id, brooder_pen_id, generateBrooderTag(), formatted, null, null, Integer.parseInt(edit_no_hatched.getText().toString()), edit_date_hatched.getText().toString(), null);

                                boolean isPenUpdated = myDb.updatePen(brooder_pen, "Brooder", total,Integer.parseInt(edit_no_hatched.getText().toString()) + current);

                                Integer current_count = (Integer.parseInt(edit_no_hatched.getText().toString())+current);
                                if(isNetworkAvailable()){

                                    RequestParams requestParams = new RequestParams();
                                    requestParams.add("pen_number", brooder_pen);
                                    requestParams.add("pen_current", current_count.toString());



                                    API_editPenCount(requestParams);
                                }

                                if (isPenUpdated && isInventoryInserted && isInserted) {
                                    Toast.makeText(getActivity(), "Successfully added to database", Toast.LENGTH_SHORT).show();
                                    Intent intent_line = new Intent(getActivity(), HatcheryRecords.class);
                                    intent_line.putExtra("Breeder Tag", breeder_tag);
                                    startActivity(intent_line);


                                } else {
                                    Toast.makeText(getActivity(), "Error in adding to the database", Toast.LENGTH_SHORT).show();
                                }

                            } else { //KUNG MAY LAMAN YUNG DATABASE BASED SA VALUES KANINA

                                Cursor cursor_pen = myDb.getAllDataFromPenWhere(brooder_pen);
                                cursor_pen.moveToFirst();
                                Integer total=0;
                                Integer current=0;
                                Integer brooder_pen_id=0;
                                if(cursor_pen.getCount() != 0){
                                    brooder_pen_id = cursor_pen.getInt(0);
                                    total = cursor_pen.getInt(4);
                                    current = cursor_pen.getInt(5);
                                }



                                Integer brooder_id = cursorBrooderChecker.getInt(0);

                                boolean isPenUpdated = myDb.updatePen(brooder_pen, "Brooder", total,Integer.parseInt(edit_no_hatched.getText().toString()) + current);
                                Integer current_count = (Integer.parseInt(edit_no_hatched.getText().toString())+current);
                                if(isNetworkAvailable()){

                                    RequestParams requestParams = new RequestParams();
                                    requestParams.add("pen_number", brooder_pen);
                                    requestParams.add("pen_current", current_count.toString());



                                    API_editPenCount(requestParams);
                                }

                                String brooder_tag2 = generateBrooderTag();
                                boolean isInventoryInserted = myDb.insertDataBrooderInventory(brooder_id, brooder_pen_id, brooder_tag2, formatted, null, null, Integer.parseInt(edit_no_hatched.getText().toString()), edit_date_hatched.getText().toString(), null);
                                Integer id_0=null;
                                Cursor cursor3 = myDb.getDataFromBrooderInventoryWhereTag(brooder_tag2);
                                cursor3.moveToFirst();
                                if(cursor3.getCount() != 0){
                                    id_0 = cursor3.getInt(0);
                                }
                                if(isNetworkAvailable()){

                                    RequestParams requestParams = new RequestParams();
                                    requestParams.add("id", id_0.toString());
                                    requestParams.add("broodergrower_id", brooder_id.toString());
                                    requestParams.add("pen_id", brooder_pen_id.toString());
                                    requestParams.add("broodergrower_tag", brooder_tag2);
                                    requestParams.add("batching_date", formatted);
                                    requestParams.add("number_male", null);
                                    requestParams.add("number_female", null);
                                    requestParams.add("total", edit_no_hatched.getText().toString());
                                    requestParams.add("last_update", edit_date_hatched.getText().toString());
                                    requestParams.add("deleted_at", null);



                                    API_addBrooderInventory(requestParams);
                                }

                                if (isPenUpdated && isInventoryInserted) {
                                    Toast.makeText(getActivity(), "Successfully added to database", Toast.LENGTH_SHORT).show();/*String.format("%04d" , Integer.parseInt(mInput_generation_number.getText().toString()));*/
                                    Intent intent_line = new Intent(getActivity(), HatcheryRecords.class);
                                    intent_line.putExtra("Breeder Tag", breeder_tag);
                                    startActivity(intent_line);

                                } else {
                                    Toast.makeText(getActivity(), "Brooder not added to database", Toast.LENGTH_SHORT).show();
                                }
                                //   boolean isInventoryInserted = myDb.insertDataBrooderInventory(brooder_id,brooder_pen, "QUEBAI"+generation_spinner.getSelectedItem().toString()+line_spinner.getSelectedItem().toString()+family_spinner.getSelectedItem().toString()+m, brooder_date_added.getText().toString()+m, null,null,Integer.parseInt(brooder_total_number.getText().toString()),null,null);


                            }


                            ///////////////////////////////////endOfTest
                        } else {
                            Toast.makeText(getContext(), "Fill all forms to include brooder in the system", Toast.LENGTH_LONG).show();
                        }

                    }
//////////////////////////////
                    boolean isUpdated = myDb.updateHatcheryRecord(hatchery_id, edit_date_set.getText().toString(), null, Integer.parseInt(edit_quantity.getText().toString()), age_weeks, Integer.parseInt(edit_no_of_fertile.getText().toString()), Integer.parseInt(edit_no_hatched.getText().toString()), edit_date_hatched.getText().toString());
                    if (isUpdated == true) {
                        Intent intent = new Intent(getActivity(), HatcheryRecords.class);
                        intent.putExtra("Breeder Tag", breeder_tag);
                        startActivity(intent);

                        Toast.makeText(getContext(), "Updated hatchery record", Toast.LENGTH_SHORT).show();
                        getDialog().dismiss();
                    } else {
                        Toast.makeText(getContext(), "Error updating hatchery record", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    getDialog().dismiss();

                }


            }
        });


        return view;
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
       // Toast.makeText(context, tag, Toast.LENGTH_SHORT).show();
        return tag;

    }
    private void API_editPenCount(RequestParams requestParams){
        APIHelper.editPenCount("editPenCount", requestParams, new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response){
//                Toast.makeText(getContext(), "Successfully edited pen count", Toast.LENGTH_SHORT).show();
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
    private void API_addBrooder(RequestParams requestParams){
        APIHelper.addBrooderFamily("addBrooderFamily", requestParams, new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response){
                //      Toast.makeText(getContext(), "Successfully added to web", Toast.LENGTH_SHORT).show();
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
    private void loadSpinnerDataForGeneration() {

        DatabaseHelper db = new DatabaseHelper(getContext());
        List<String> generations = db.getAllDataFromGenerationasList();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, generations);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        outside_generation_spinner.setAdapter(dataAdapter);
    }

    public void loadSpinnerDataForLine(String selected_generation) {
        DatabaseHelper db = new DatabaseHelper(getContext());
        List<String> lines = db.getAllDataFromLineasList(selected_generation);
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, lines);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        outside_line_spinner.setAdapter(dataAdapter2);
    }

    public void loadSpinnerDataForFamily(String selected_line, String selected_generation) {
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
}