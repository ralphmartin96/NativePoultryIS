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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

import cz.msebera.android.httpclient.Header;

public class CreateBreederDialog extends DialogFragment {

    private static final String TAG = "CreateFamilyDialog";
    private EditText estimated_date_of_hatch,outside_date_transferred,outside_male_quantity, outside_female_quantity, quantity,female_quantity, date_transferred,male_wingband2,female_wingband2, male_wingband, female_wingband;

    private Button mActionOk;
    DatabaseHelper myDb;
    Switch in_or_out;
    LinearLayout within_system, outside_system;
    Calendar calendar, calendar2,calendar3;
    Spinner outside_generation_spinner, outside_line_spinner, outside_family_spinner, outside_place_to_pen, place_to_pen, generation_spinner, line_spinner, family_spinner, replacement_spinner,female_generation_spinner, female_line_spinner, female_family_spinner, female_replacement_spinner;
    DatabaseHelper mydb;
    Random random = new Random();
    boolean isSystemOutside;
    String replacement_tag_2, replacement_pen_number, female_replacement_pen_number;
    Integer replacement_pen_2, female_replacement_pen_2;
    String  female_replacement_tag_2;
    ArrayList<Pen> arrayListPen_2= new ArrayList();
    ArrayList<Pen> arrayListPen_3= new ArrayList();
    ArrayList<Pen> arrayListPen_4= new ArrayList();
    ArrayList<Pen> arrayListPen_5= new ArrayList();
    Integer pen_id;
    Integer batching_week2=null;
    String formatted=null;
    String brooder_tag2=null;
    Context context;
    String farmid=null;
    Integer farm_id=0;
    ArrayList<Breeders> arrayListBreeder = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_create_breeder, container, false);
        mActionOk = view.findViewById(R.id.action_ok);
        context = getActivity();
        //breeder_pen = getArguments().getString("breeder_pen");
        myDb = new DatabaseHelper(getContext());

        in_or_out = view.findViewById(R.id.in_or_out);
        within_system = view.findViewById(R.id.within_system);
        outside_system = view.findViewById(R.id.outside_system);
        estimated_date_of_hatch = view.findViewById(R.id.estimated_date_of_hatch);
        outside_date_transferred= view.findViewById(R.id.outside_date_transferred);

        outside_generation_spinner = view.findViewById(R.id.outside_generation_spinner);
        outside_line_spinner = view.findViewById(R.id.outside_line_spinner);
        outside_family_spinner = view.findViewById(R.id.outside_family_spinner);
        outside_place_to_pen = view.findViewById(R.id.outside_place_to_pen);
        outside_male_quantity = view.findViewById(R.id.outside_male_quantity);
        outside_female_quantity = view.findViewById(R.id.outside_female_quantity);


        generation_spinner = view.findViewById(R.id.generation_spinner);
        line_spinner = view.findViewById(R.id.line_spinner);
        family_spinner = view.findViewById(R.id.family_spinner);
        replacement_spinner = view.findViewById(R.id.replacement_spinner);
        female_generation_spinner = view.findViewById(R.id.female_generation_spinner);
        female_line_spinner = view.findViewById(R.id.female_line_spinner);
        female_family_spinner = view.findViewById(R.id.female_family_spinner);
        female_replacement_spinner = view.findViewById(R.id.female_replacement_spinner);
        place_to_pen = view.findViewById(R.id.place_to_pen);
        quantity = view.findViewById(R.id.quantity);
        female_quantity = view.findViewById(R.id.female_quantity);
        date_transferred = view.findViewById(R.id.date_transferred);

        //breeder_code = view.findViewById(R.id.breeder_code);
        male_wingband = view.findViewById(R.id.male_wingband);
        female_wingband = view.findViewById(R.id.female_wingband);
        male_wingband2 = view.findViewById(R.id.male_wingband2);
        female_wingband2 = view.findViewById(R.id.female_wingband2);


        loadSpinnerDataForPen();
        loadSpinnerDataForGeneration();
        loadSpinnerDataForWithinGeneration();
        loadSpinnerDataForFemaleWithinGeneration();

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


        generation_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selected_generation = generation_spinner.getSelectedItem().toString();
                loadSpinnerDataForWithinLine(selected_generation);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        line_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selected_generation = generation_spinner.getSelectedItem().toString();
                String selected_line = line_spinner.getSelectedItem().toString();
                loadSpinnerDataForWithinFamily(selected_line, selected_generation);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        family_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selected_generation = generation_spinner.getSelectedItem().toString();
                String selected_line = line_spinner.getSelectedItem().toString();
                String selected_family = family_spinner.getSelectedItem().toString();
                loadSpinnerDataForWithinReplacement(selected_family,selected_line, selected_generation);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        female_generation_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selected_generation = female_generation_spinner.getSelectedItem().toString();
                loadSpinnerDataForFemaleWithinLine(selected_generation);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        female_line_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selected_generation = female_generation_spinner.getSelectedItem().toString();
                String selected_line = female_line_spinner.getSelectedItem().toString();
                loadSpinnerDataForFemaleWithinFamily(selected_line, selected_generation);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        female_family_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selected_generation = female_generation_spinner.getSelectedItem().toString();
                String selected_line = female_line_spinner.getSelectedItem().toString();
                String selected_family = female_family_spinner.getSelectedItem().toString();
                loadSpinnerDataForFemaleWithinReplacement(selected_family,selected_line, selected_generation);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        outside_place_to_pen.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selected_generation = outside_place_to_pen.getSelectedItem().toString();
                //loadSpinnerDataForLine(selected_generation);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        outside_system.setVisibility(View.GONE);
        in_or_out.setChecked(false);
        in_or_out.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {

                if (isChecked) {
                    within_system.setVisibility(View.GONE);
                    outside_system.setVisibility(View.VISIBLE);
                    isSystemOutside = true;

                } else {
                    within_system.setVisibility(View.VISIBLE);
                    outside_system.setVisibility(View.GONE);
                    isSystemOutside = false;

                }

            }
        });


        ///GET BATCHING WEEK FROM DATABASE
        Cursor cursor1 = myDb.getAllDataFromFarms(farm_id);
        cursor1.moveToFirst();

        if(cursor1.getCount() != 0){

            batching_week2 = cursor1.getInt(4);

        }


        estimated_date_of_hatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int selectedYear, int selectedMonth, int selectedDay) {
                        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
                        if(batching_week2 == null){
                            batching_week2 = 0;
                        }
                        calendar.set(selectedYear,selectedMonth,selectedDay+7*batching_week2);
                        formatted = format1.format(calendar.getTime());
                        selectedMonth++;
                        estimated_date_of_hatch.setText(selectedYear + "-" + selectedMonth + "-" + selectedDay);
                    }
                }, year, month, day);
                mDatePicker.show();

            }
        });



        outside_date_transferred.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar2 = Calendar.getInstance();
                int year = calendar2.get(Calendar.YEAR);
                int month = calendar2.get(Calendar.MONTH);
                int day = calendar2.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int selectedYear, int selectedMonth, int selectedDay) {
                        selectedMonth++;
                        outside_date_transferred.setText(selectedYear + "-" + selectedMonth + "-" + selectedDay);
                        calendar2.set(selectedYear,selectedMonth,selectedDay);
                    }
                }, year, month, day);
                mDatePicker.show();

            }
        });






        date_transferred.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar3 = Calendar.getInstance();
                int year = calendar3.get(Calendar.YEAR);
                int month = calendar3.get(Calendar.MONTH);
                int day = calendar3.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int selectedYear, int selectedMonth, int selectedDay) {
                        selectedMonth++;
                        date_transferred.setText(selectedYear + "-" + selectedMonth + "-" + selectedDay);
                        calendar3.set(selectedYear,selectedMonth,selectedDay);
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

        Cursor cursor = myDb.getAllDataFromFarms(farm_id);
        cursor.moveToFirst();
        if(cursor.getCount() != 0){
            farmid = cursor.getString(2);
        }


        boolean isNetworkAvailable = isNetworkAvailable();
        mActionOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int m = random.nextInt(100);

                //IBIG SABIHIN, HINDI MO NA KAILANGAN NG CHECKER KUNG MERON BA SA DATABASE NG BREEDER GIVEN NG GEN LINE FAM
                    //NEXT TIME MO NA AYUSIN
                if(isSystemOutside){
                    if(!estimated_date_of_hatch.getText().toString().isEmpty() && !outside_generation_spinner.getSelectedItem().toString().isEmpty() && !outside_male_quantity.getText().toString().isEmpty() && !outside_female_quantity.getText().toString().isEmpty()){
                    ////DATABASE OPERATIONSSS

                        Integer familyID = null;
                        Cursor cursor_familyID = myDb.getFamilyID(outside_family_spinner.getSelectedItem().toString(),outside_line_spinner.getSelectedItem().toString(),outside_generation_spinner.getSelectedItem().toString());
                        cursor_familyID.moveToFirst();
                        if(cursor_familyID.getCount() != 0){
                            familyID = cursor_familyID.getInt(0);
                        }
                        boolean isInserted = myDb.insertDataBreeder(familyID, null,outside_date_transferred.getText().toString(),null);
                        //kunin yung selected pen tapos kung 0 yung content niya, ay
                        if(isNetworkAvailable) {


                            RequestParams requestParams = new RequestParams();
                            requestParams.add("family_id", familyID.toString());
                            requestParams.add("date_added", outside_date_transferred.getText().toString());
                            requestParams.add("deleted_at", null);

                            API_addBreeder(requestParams);

                        }


                        Cursor cursor_pen = myDb.getAllDataFromPenWhere(outside_place_to_pen.getSelectedItem().toString());
                        cursor_pen.moveToFirst();
                        int total = cursor_pen.getInt(4);
                        int current = cursor_pen.getInt(5);
                        //update pen count
                        boolean isPenUpdated = myDb.updatePen(outside_place_to_pen.getSelectedItem().toString(), "Layer", total,Integer.parseInt(outside_male_quantity.getText().toString())+Integer.parseInt(outside_female_quantity.getText().toString())+current);

                        //updatePen
                        Integer current_pen9 = Integer.parseInt(outside_male_quantity.getText().toString())+Integer.parseInt(outside_female_quantity.getText().toString())+current;
                        if(isNetworkAvailable()){

                            RequestParams requestParams = new RequestParams();
                            requestParams.add("pen_number", outside_place_to_pen.getSelectedItem().toString());
                            requestParams.add("pen_current", current_pen9.toString());



                            API_editPenCount(requestParams);
                        }

                        Cursor cursor = myDb.getIDFromBreedersWhere(familyID);
                        cursor.moveToFirst();
                        Integer id = cursor.getInt(0);
                        Cursor cursor5 = myDb.getAllDataFromPenWhere(outside_place_to_pen.getSelectedItem().toString());
                        cursor5.moveToFirst();
                        if(cursor5.getCount() != 0){
                            pen_id = cursor5.getInt(0);
                        }
                        brooder_tag2 = generateBrooderTag();
                        boolean isInventoryInserted = myDb.insertDataBreederInventory(id, pen_id, brooder_tag2, formatted,Integer.parseInt(outside_male_quantity.getText().toString()), Integer.parseInt(outside_female_quantity.getText().toString()),Integer.parseInt(outside_female_quantity.getText().toString())+Integer.parseInt(outside_male_quantity.getText().toString()),outside_date_transferred.getText().toString(),null, farmid.toString()+outside_generation_spinner.getSelectedItem().toString()+outside_line_spinner.getSelectedItem().toString()+outside_family_spinner.getSelectedItem().toString()+outside_date_transferred.getText().toString(),"["+ male_wingband2.getText().toString()+"]", "["+female_wingband2.getText().toString()+"]");
                        Integer id_0=null;
                        Cursor cursor2 = myDb.getDataFromBreederInvWhereTag(brooder_tag2);
                        cursor2.moveToFirst();
                        if(cursor2.getCount() != 0){
                            id_0 = cursor2.getInt(0);
                        }
                        if(isNetworkAvailable){
                            Integer breeder_total = Integer.parseInt(outside_female_quantity.getText().toString())+Integer.parseInt(outside_male_quantity.getText().toString());
                            RequestParams requestParams = new RequestParams();
                           // requestParams.add("id", id_0.toString());
                            requestParams.add("breeder_id", id.toString());
                            requestParams.add("pen_id", pen_id.toString());
                            requestParams.add("breeder_tag", brooder_tag2);
                            requestParams.add("batching_date", formatted);
                            requestParams.add("number_male", outside_male_quantity.getText().toString());
                            requestParams.add("number_female", outside_female_quantity.getText().toString());
                            requestParams.add("total", breeder_total.toString());
                            requestParams.add("last_update", outside_date_transferred.getText().toString());
                            requestParams.add("deleted_at", null);
                            requestParams.add("breeder_code", farmid.toString()+outside_generation_spinner.getSelectedItem().toString()+outside_line_spinner.getSelectedItem().toString()+outside_family_spinner.getSelectedItem().toString()+outside_date_transferred.getText().toString());
                            requestParams.add("male_wingband", "["+male_wingband2.getText().toString() + "]");
                            requestParams.add("female_wingband", "["+female_wingband2.getText().toString()+ "]");


                            API_addBreederInventory(requestParams);
                        }




                        if(isInserted == true && isInventoryInserted == true){
                            Toast.makeText(getActivity(),"Breeder added to database", Toast.LENGTH_SHORT).show();
                            Intent intent_line = new Intent(getActivity(), CreateBreeders.class);
                            startActivity(intent_line);
                            getDialog().dismiss();
                        }else{
                            Toast.makeText(getActivity(),"Breeder not added to database", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(getContext(), "Please fill empty entries", Toast.LENGTH_SHORT).show();
                    }


                }else{
                    if(!generation_spinner.getSelectedItem().toString().isEmpty() && !quantity.getText().toString().isEmpty() && !female_quantity.getText().toString().isEmpty() && !date_transferred.getText().toString().isEmpty()){
                        Integer familyID = null;
                        Integer female_familyID = null;
                        Cursor cursor_familyID = myDb.getFamilyID(family_spinner.getSelectedItem().toString(),line_spinner.getSelectedItem().toString(),generation_spinner.getSelectedItem().toString());
                        cursor_familyID.moveToFirst();
                        if(cursor_familyID.getCount() != 0){
                            familyID = cursor_familyID.getInt(0);
                        }
                        Cursor cursor_female_familyID = myDb.getFamilyID(female_family_spinner.getSelectedItem().toString(),female_line_spinner.getSelectedItem().toString(),female_generation_spinner.getSelectedItem().toString());
                        cursor_female_familyID.moveToFirst();
                        if(cursor_female_familyID.getCount() != 0){
                            female_familyID = cursor_female_familyID.getInt(0);
                        }
                        boolean isInserted = myDb.insertDataBreeder(familyID, female_familyID,date_transferred.getText().toString(),null);
                        if(isNetworkAvailable) {


                            RequestParams requestParams = new RequestParams();
                            requestParams.add("family_id", familyID.toString());
                            requestParams.add("female_family_id", female_familyID.toString());
                            requestParams.add("date_added", date_transferred.getText().toString());
                            requestParams.add("deleted_at", null);

                            API_addBreeder(requestParams);

                        }


                        //put string operations here
                        String text = replacement_spinner.getSelectedItem().toString();
                        String[] tokens = text.split(Pattern.quote(" | "));
                        String replacement_tag_raw = tokens[0];

                        String delims_2 = ": ";
                        String[] replacement_info = replacement_tag_raw.split(delims_2);
                        replacement_tag_2 = replacement_info[1];







                        Integer maleCount = Integer.parseInt(quantity.getText().toString());
                        Integer femaleCount = Integer.parseInt(female_quantity.getText().toString());


                        Cursor cursor1 = myDb.getDataFromReplacementInventoryWhereTag(replacement_tag_2);

                        cursor1.moveToFirst();
                        Integer id1 = cursor1.getInt(0);
                        replacement_pen_2 = cursor1.getInt(2);
                        Integer current_male_count = cursor1.getInt(5);
                        Integer current_male_total = cursor1.getInt(7);

                        Integer new_male_count = current_male_count - maleCount;
                        Integer new_male_total = current_male_total - maleCount;



                        //update the current male and female count of the replacement inventory
                        boolean isUpdated = myDb.updateReplacementInventoryMaleCountAndTotal(replacement_tag_2, new_male_count, new_male_total);
                        if(isNetworkAvailable()){

                            RequestParams requestParams = new RequestParams();
                            requestParams.add("replacement_inv_id", id1.toString());
                            requestParams.add("male", new_male_count.toString());
                            requestParams.add("total_", new_male_total.toString());


                            API_editReplacementInventoryMaleFemale(requestParams);
                        }

                        //updatePen


                        //update Pen where the replacement inventory was transferred from
                        Cursor cursor3 = myDb.getAllDataFromPen();
                        cursor3.moveToFirst();
                        if(cursor3.getCount() == 0){
                        }else{
                            do{
                                if(cursor3 != null){
                                    Pen pen = new Pen(cursor3.getInt(0),cursor3.getString(2), cursor3.getString(3), cursor3.getInt(4), cursor3.getInt(5), cursor3.getInt(1), cursor3.getInt(6));
                                    arrayListPen_2.add(pen);
                                }
                            }while (cursor3.moveToNext());
                        }

                        //replacement_pen_2 is the id of the selected pen, find its corresponding pen_number from the database
                        Cursor cursor7 = myDb.getAllDataFromPenWhereID(replacement_pen_2);
                        cursor7.moveToFirst();
                        Integer total_2=0;
                        Integer current_2=0;
                        if (cursor7.getCount() != 0){
                            replacement_pen_number = cursor7.getString(2);
                            total_2 = cursor7.getInt(4);
                            current_2 = cursor7.getInt(5);
                        }

                        //UPDATE PEN (BAWASAN YUNG YUNG BROODER PEN KUNG SAN GALING YUNG BROODER INVENTORY
                        boolean isBrooderPenUpdated = myDb.updatePen(replacement_pen_number, "Grower",  total_2, current_2-maleCount);

                        //updatePen
                        Integer current_pen0 = current_2-maleCount;
                        if(isNetworkAvailable()){

                            RequestParams requestParams = new RequestParams();
                            requestParams.add("pen_number", replacement_pen_number);
                            requestParams.add("pen_current", current_pen0.toString());



                            API_editPenCount(requestParams);
                        }








                        ////////FOR FEMALE REPLACEMENT


                        //put string operations for female breeder here
                        String text2 = female_replacement_spinner.getSelectedItem().toString();
                        String[] tokens_2 = text2.split(Pattern.quote(" | "));
                        String female_replacement_tag_raw = tokens_2[0];

                        String[] female_replacement_info = female_replacement_tag_raw.split(delims_2);
                        female_replacement_tag_2 = female_replacement_info[1];



                        Cursor cursor2 = myDb.getDataFromReplacementInventoryWhereTag(female_replacement_tag_2);
                        cursor2.moveToFirst();
                        Integer id3 = cursor2.getInt(0);
                        female_replacement_pen_2 = cursor2.getInt(2);
                        Integer current_female_count = cursor2.getInt(6);
                        Integer current_female_total = cursor2.getInt(7);
                        Integer new_female_count = current_female_count - femaleCount;
                        Integer new_female_total = current_female_total - femaleCount;


                        boolean isUpdatedFemale = myDb.updateReplacementInventoryFemaleCountAndTotal(female_replacement_tag_2,new_female_count, new_female_total);
                        if(isNetworkAvailable()){

                            RequestParams requestParams = new RequestParams();
                            requestParams.add("replacement_inv_id", id3.toString());
                            requestParams.add("female", new_female_count.toString());
                            requestParams.add("total_", new_female_total.toString());


                            API_editReplacementInventoryMaleFemale(requestParams);
                        }

                        //updateFemale
                        Cursor cursor4 = myDb.getAllDataFromPen();
                        cursor4.moveToFirst();
                        if(cursor4.getCount() == 0){
                        }else{
                            do{
                                if(cursor4 != null){
                                    Pen pen = new Pen(cursor4.getInt(0),cursor4.getString(2), cursor4.getString(3), cursor4.getInt(4), cursor4.getInt(5), cursor4.getInt(1), cursor4.getInt(6));
                                    arrayListPen_4.add(pen);
                                }
                            }while (cursor4.moveToNext());
                        }


                        //replacement_pen_2 is the id of the selected pen, find its corresponding pen_number from the database
                        Cursor cursor6 = myDb.getAllDataFromPenWhereID(female_replacement_pen_2);
                        cursor6.moveToFirst();
                        Integer total_3 =0;
                        Integer current_3=0;
                        if (cursor6.getCount() != 0){
                            female_replacement_pen_number = cursor6.getString(2);
                            total_3 = cursor6.getInt(4);
                            current_3 = cursor6.getInt(5);
                        }



                        //UPDATE PEN (BAWASAN YUNG YUNG BROODER PEN KUNG SAN GALING YUNG BROODER INVENTORY
                        boolean isBrooderPenUpdated_2 = myDb.updatePen(female_replacement_pen_number, "Grower", total_3,  current_3-femaleCount);
                        //updateReplacementPen
                        Integer current_pen2 = current_3-femaleCount;
                        if(isNetworkAvailable()){

                            RequestParams requestParams = new RequestParams();
                            requestParams.add("pen_number", female_replacement_pen_number);
                            requestParams.add("pen_current", current_pen2.toString());



                            API_editPenCount(requestParams);
                        }



                        Cursor cursor_pen = myDb.getAllDataFromPenWhere(place_to_pen.getSelectedItem().toString());
                        cursor_pen.moveToFirst();
                        int total = cursor_pen.getInt(4);
                        int current = cursor_pen.getInt(5);
                        //update pen count
                        boolean isPenUpdated = myDb.updatePen(place_to_pen.getSelectedItem().toString(), "Layer", total,(Integer.parseInt(quantity.getText().toString())+Integer.parseInt(female_quantity.getText().toString())+current));
                       // updatePenBreeder
                        Integer current_pen = (Integer.parseInt(quantity.getText().toString())+Integer.parseInt(female_quantity.getText().toString())+current);
                        if(isNetworkAvailable()){

                            RequestParams requestParams = new RequestParams();
                            requestParams.add("pen_number", place_to_pen.getSelectedItem().toString());
                            requestParams.add("pen_current", current_pen.toString());



                            API_editPenCount(requestParams);
                        }


                        Cursor cursor = myDb.getIDFromBreedersWhere(familyID);
                        cursor.moveToFirst();
                        Integer id = cursor.getInt(0);

                        Cursor cursor5 = myDb.getAllDataFromPenWhere(place_to_pen.getSelectedItem().toString());
                        cursor5.moveToFirst();
                        if(cursor5.getCount() != 0){
                            pen_id = cursor5.getInt(0);
                        }
                        brooder_tag2 = generateBrooderTag();

                        boolean isInventoryInserted = myDb.insertDataBreederInventory(id, pen_id, brooder_tag2, formatted,Integer.parseInt(quantity.getText().toString()), Integer.parseInt(female_quantity.getText().toString()),Integer.parseInt(female_quantity.getText().toString())+Integer.parseInt(quantity.getText().toString()),date_transferred.getText().toString(),null, farmid.toString()+generation_spinner.getSelectedItem().toString()+line_spinner.getSelectedItem().toString()+family_spinner.getSelectedItem().toString()+date_transferred.getText().toString(), "["+male_wingband.getText().toString()+"]", "["+female_wingband.getText().toString()+ "]");


                        Integer id_0=null;
                        Cursor cursor2_breeder = myDb.getDataFromBreederInvWhereTag(brooder_tag2);
                        cursor2_breeder.moveToFirst();
                        if(cursor2_breeder.getCount() != 0){
                            id_0 = cursor2_breeder.getInt(0);
                        }
                        if(isNetworkAvailable){
                            Integer breeder_total = Integer.parseInt(female_quantity.getText().toString())+Integer.parseInt(quantity.getText().toString());
                            RequestParams requestParams = new RequestParams();
                            //requestParams.add("id", id_0.toString());
                            requestParams.add("breeder_id", id.toString());
                            requestParams.add("pen_id", pen_id.toString());
                            requestParams.add("breeder_tag", brooder_tag2);
                            requestParams.add("batching_date", formatted);
                            requestParams.add("number_male", quantity.getText().toString());
                            requestParams.add("number_female", female_quantity.getText().toString());
                            requestParams.add("total", breeder_total.toString());
                            requestParams.add("last_update", date_transferred.getText().toString());
                            requestParams.add("deleted_at", null);
                            requestParams.add("breeder_code", farmid.toString()+generation_spinner.getSelectedItem().toString()+line_spinner.getSelectedItem().toString()+family_spinner.getSelectedItem().toString()+date_transferred.getText().toString());
                            requestParams.add("male_wingband", "["+male_wingband.getText().toString()+ "]");
                            requestParams.add("female_wingband", "["+female_wingband.getText().toString()+ "]");




                            API_addBreederInventory(requestParams);
                        }
                        if(isInserted == true && isInventoryInserted == true){
                            Toast.makeText(getActivity(),"Breeder added to database", Toast.LENGTH_SHORT).show();
                            Intent intent_line = new Intent(getActivity(), CreateBreeders.class);
                            startActivity(intent_line);

                        }else{
                            Toast.makeText(getActivity(),"Breeder not added to database", Toast.LENGTH_SHORT).show();
                        }

                    }else{
                        Toast.makeText(getContext(), "Please fill empty entries", Toast.LENGTH_SHORT).show();
                    }
                ///within system
                }



            }

        });

        return view;
    }
    private void API_editReplacementInventoryMaleFemale(RequestParams requestParams){
        APIHelper.editReplacementInventoryMaleFemale("editReplacementInventoryMaleFemale", requestParams, new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response){
       //         Toast.makeText(context, "Successfully edited replacement male and female", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonResponse, Object response){

                Toast.makeText(context, "Failed to edit replacement male and female", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable{
                return null;
            }
        });
    }
    private void API_editPenCount(RequestParams requestParams){
        APIHelper.editPenCount("editPenCount", requestParams, new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response){
//                Toast.makeText(getContext(), "Successfully edited pen count", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonResponse, Object response){

                Toast.makeText(getContext(), "Failed to edit pen count", Toast.LENGTH_SHORT).show();
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
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) this.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
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





    private void loadSpinnerDataForWithinGeneration() {

        DatabaseHelper db = new DatabaseHelper(getContext());
        List<String> generations = db.getAllDataFromGenerationasList();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, generations);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        generation_spinner.setAdapter(dataAdapter);
    }
    public void loadSpinnerDataForWithinLine(String selected_generation){
        DatabaseHelper db = new DatabaseHelper(getContext());
        List<String> lines = db.getAllDataFromLineasList(selected_generation);
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, lines);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        line_spinner.setAdapter(dataAdapter2);
    }
    public void loadSpinnerDataForWithinFamily(String selected_line, String selected_generation){
        DatabaseHelper db = new DatabaseHelper(getContext());
        List<String> lines = db.getAllDataFromFamilyasList(selected_line, selected_generation);
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, lines);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        family_spinner.setAdapter(dataAdapter2);
    }

    public void loadSpinnerDataForWithinReplacement(String selected_family, String selected_line, String selected_generation){
        DatabaseHelper db = new DatabaseHelper(getContext());
        List<String> lines = db.getAllDataFromMaleReplacementasList(selected_family,selected_line, selected_generation);
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, lines);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        replacement_spinner.setAdapter(dataAdapter2);

    }




    private void loadSpinnerDataForFemaleWithinGeneration() {

        DatabaseHelper db = new DatabaseHelper(getContext());
        List<String> generations = db.getAllDataFromGenerationasList();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, generations);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        female_generation_spinner.setAdapter(dataAdapter);
    }
    public void loadSpinnerDataForFemaleWithinLine(String selected_generation){
        DatabaseHelper db = new DatabaseHelper(getContext());
        List<String> lines = db.getAllDataFromLineasList(selected_generation);
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, lines);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        female_line_spinner.setAdapter(dataAdapter2);
    }
    public void loadSpinnerDataForFemaleWithinFamily(String selected_line, String selected_generation){
        DatabaseHelper db = new DatabaseHelper(getContext());
        List<String> lines = db.getAllDataFromFamilyasList(selected_line, selected_generation);
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, lines);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        female_family_spinner.setAdapter(dataAdapter2);
    }

    public void loadSpinnerDataForFemaleWithinReplacement(String selected_family, String selected_line, String selected_generation){
        DatabaseHelper db = new DatabaseHelper(getContext());
        List<String> lines = db.getAllDataFromFemaleReplacementasList(selected_family,selected_line, selected_generation);
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, lines);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        female_replacement_spinner.setAdapter(dataAdapter2);

    }
    private void loadSpinnerDataForPen() {
        DatabaseHelper db = new DatabaseHelper(getContext());
        List<String> generations = db.getAllDataFromPenasList();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, generations);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        outside_place_to_pen.setAdapter(dataAdapter);
        place_to_pen.setAdapter(dataAdapter);
    }
    public void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
    private void API_addBreeder(RequestParams requestParams){
        APIHelper.addBreederFamily("addBreederFamily", requestParams, new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response){
//                Toast.makeText(getContext(), "Successfully added to web", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonResponse, Object response){

//                Toast.makeText(getContext(), "Failed to add to web", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable{
                return null;
            }
        });
    }
    private void API_addBreederInventory(RequestParams requestParams){
        APIHelper.addBreederInventory("addBreederInventory", requestParams, new BaseJsonHttpResponseHandler<Object>() {
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


}