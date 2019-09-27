package com.example.cholomanglicmot.nativechickenandduck.ReplacementsDirectory;

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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cholomanglicmot.nativechickenandduck.APIHelperAsync;
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


public class CreateReplacementDialog extends DialogFragment {
    private static final String TAG = "CreateReplacementFragment";
    private EditText replacement_total, replacement_date_added,replacement_date_hatch;
    private TextView add_replacement_to_pen_text;
    private Spinner line_spinner, generation_spinner, family_spinner, transfer_spinner;
    private RadioGroup radio_group_system_type;
    private RadioButton within, outside;
    private Button mActionOk;
    private Calendar calendar,calendar2;
    private Context context;
    private String replacement_pen;
    private Switch replacement_system_type;
    private LinearLayout linear2;
    ArrayList<Pen> arrayListPen= new ArrayList();
    ArrayList<Pen> arrayListPen_2= new ArrayList();
    ArrayList<Pen> arrayListPen_3= new ArrayList();
    ArrayList<Pen> arrayListPen2= new ArrayList();
    boolean isSwitchChecked;
    String brooder_tag_2, brooder_pen_2;
    Integer replacement_pen_id, replacement_pen_id2;
    String farm_id;
    Integer replacement_pen_total;
    Integer replacement_pen_current;
    DatabaseHelper myDb;
    Random random = new Random();
    Integer batching_week2;
    String formatted=null;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_create_replacement, container, false);


        ////////////
        FirebaseAuth mAuth;

        mAuth = FirebaseAuth.getInstance();

        FirebaseUser user = mAuth.getCurrentUser();

        String name = user.getDisplayName();

        String email = user.getEmail();

        Uri photo = user.getPhotoUrl();

        ///////////////////

        boolean isNetworkAvailable = isNetworkAvailable();
        //GET BATCHING WEEK
        //THEN GET TAG
        if(isNetworkAvailable){
            API_getFarmID(email);
        }
        mActionOk = view.findViewById(R.id.action_ok);
        replacement_pen = getArguments().getString("Replacement Pen");
        add_replacement_to_pen_text = view.findViewById(R.id.add_brooder_to_pen_text);
        add_replacement_to_pen_text.setText("Add Replacement to Pen "+ replacement_pen);
        replacement_system_type = view.findViewById(R.id.replacement_system_type);

        generation_spinner = (Spinner) view.findViewById(R.id.generation_spinner);
        line_spinner = (Spinner) view.findViewById(R.id.line_spinner);
        family_spinner = (Spinner) view.findViewById(R.id.family_spinner);

        replacement_total = view.findViewById(R.id.replacement_total);
        replacement_date_hatch = view.findViewById(R.id.replacement_date_hatch);
        replacement_date_added = view.findViewById(R.id.replacement_date_added);
        transfer_spinner = view.findViewById(R.id.transfer_spinner);
        linear2 = view.findViewById(R.id.linear2);


        myDb = new DatabaseHelper(getContext());




        loadSpinnerDataForGeneration();
        generation_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selected_generation = generation_spinner.getSelectedItem().toString();
                loadSpinnerDataForLine(selected_generation);
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
                loadSpinnerDataForFamily(selected_line, selected_generation);
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
                loadSpinnerDataForBrooder(selected_family,selected_line,selected_generation);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        replacement_system_type.setChecked(false);
        replacement_system_type.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {

                if (isChecked) {
                    linear2.setVisibility(View.GONE);
                    isSwitchChecked = true;


                } else {

                    linear2.setVisibility(View.VISIBLE);
                    isSwitchChecked = false;

                }

            }
        });




        replacement_date_hatch.setOnClickListener(new View.OnClickListener() {
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
                        if(batching_week2 == null){
                            batching_week2 = 0;
                        }
                        calendar2.set(selectedYear,selectedMonth,selectedDay+7*batching_week2);
                        formatted = format1.format(calendar2.getTime());
                        selectedMonth++;
                        replacement_date_hatch.setText(selectedYear + "-" + selectedMonth + "-" + selectedDay);

                    }
                }, year, month, day);
                mDatePicker.show();

            }
        });

        replacement_date_added.setOnClickListener(new View.OnClickListener() {
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
                        replacement_date_added.setText(selectedYear + "-" + selectedMonth + "-" + selectedDay);
                        calendar.set(selectedYear,selectedMonth,selectedDay);
                    }
                }, year, month, day);
                mDatePicker.show();

            }
        });




        ///GET BATCHING WEEK FROM DATABASE

        Cursor cursor = myDb.getAllDataFromPenWhere(replacement_pen);
        cursor.moveToFirst();
        if(cursor.getCount() != 0){
            replacement_pen_id = cursor.getInt(0);
        }


        mActionOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                String replacement_tag = generateBrooderTag();

                if(!generation_spinner.getSelectedItem().toString().isEmpty() && !line_spinner.getSelectedItem().toString().isEmpty() && !family_spinner.getSelectedItem().toString().isEmpty() && !replacement_total.getText().toString().isEmpty() && !replacement_date_added.getText().toString().isEmpty() ){

                    Cursor cursorBrooderChecker = myDb.replacementFamilyChecker(family_spinner.getSelectedItem().toString(),line_spinner.getSelectedItem().toString(),generation_spinner.getSelectedItem().toString());
                    cursorBrooderChecker.moveToFirst();

                   //if there are no currently existing replacement with the given fam, line, gen:
                    if(cursorBrooderChecker.getCount()==0){
                        Integer familyID = null;
                        Cursor cursor_familyID = myDb.getFamilyID(family_spinner.getSelectedItem().toString(),line_spinner.getSelectedItem().toString(),generation_spinner.getSelectedItem().toString());
                        cursor_familyID.moveToFirst();
                        if(cursor_familyID.getCount() != 0){
                            familyID = cursor_familyID.getInt(0);
                        }
                        boolean isInserted = myDb.insertDataReplacement(familyID,replacement_date_added.getText().toString(),null);

                        if(isNetworkAvailable) {


                            RequestParams requestParams = new RequestParams();
                            requestParams.add("family_id", familyID.toString());
                            requestParams.add("date_added", replacement_date_added.getText().toString());
                            requestParams.add("deleted_at", null);

                            API_addReplacement(requestParams);

                        }

                        //update replacement_pen of the newly added brooder
                        Cursor cursor_pen = myDb.getAllDataFromPenWhere(replacement_pen);
                        cursor_pen.moveToFirst();
                        Integer total=null;
                        Integer current = null;
                        if(cursor_pen.getCount() == 0){
                        }else{
                            total = cursor_pen.getInt(4);
                            current = cursor_pen.getInt(5);
                        }




                        //GET ID OF INSERTED BROODER

                        Cursor cursor = myDb.getIDFromReplacementsWhere(familyID);
                        cursor.moveToFirst();
                        Integer id = cursor.getInt(0);

                        //update pen count
                        boolean isPenUpdated = myDb.updatePen(replacement_pen, "Grower", total,Integer.parseInt(replacement_total.getText().toString())+current);

                        Integer current_count6 = Integer.parseInt(replacement_total.getText().toString())+current;
                        if(isNetworkAvailable()){

                            RequestParams requestParams = new RequestParams();
                            requestParams.add("pen_number", replacement_pen);
                            requestParams.add("pen_current", current_count6.toString());



                            API_editPenCount(requestParams);
                        }




            //If brooder inventories will be transferred to replacement pens
            //for within system only

                        if(!isSwitchChecked){
                            //get the tag of the selected brooder,
                            //bawasan mo kung gaano karami yung itatransfer
                            //kapag zero, the delete mo yung sa brooder (meaning nalipat na lahat sa replacement)
                            String text = transfer_spinner.getSelectedItem().toString();





                            String[] tokens = text.split(Pattern.quote(" | "));
                            String brooder_tag_raw = tokens[0];
                            String brooder_pen_raw = tokens[1];


                            String delims_2 = ": ";
                            String[] brooder_info = brooder_tag_raw.split(delims_2);
                            brooder_tag_2 = brooder_info[1];


                            String[] pen_info = brooder_pen_raw.split(delims_2);
                            brooder_pen_2 = pen_info[1];



                            Cursor cursor2 = myDb.getAllDataFromPenWhere(brooder_pen_2);
                            cursor2.moveToFirst();
                            if(cursor2.getCount() != 0){
                                replacement_pen_id2 = cursor2.getInt(0);
                            }


                            Integer totalCount = Integer.parseInt(replacement_total.getText().toString());
                            Cursor cursor1 = myDb.getDataFromBrooderInventoryWhereTag(brooder_tag_2);

                            cursor1.moveToFirst();
                            Integer id1 = cursor1.getInt(0);
                            Integer current_total = cursor1.getInt(7);
                            Integer new_total = current_total - totalCount;


                            //update specific brooder inventory's total count

                            boolean isUpdated = myDb.updateBrooderInventory(brooder_tag_2, new_total);
                            if(isNetworkAvailable()){

                                RequestParams requestParams = new RequestParams();
                                requestParams.add("brooder_inv_id", id1.toString());
                                requestParams.add("total_", new_total.toString());



                                API_editBrooderInventoryMaleFemale(requestParams);
                            }





                            //updating the brooder inventory's Pen

                            Cursor cursor3 = myDb.getAllDataFromPenWhere(brooder_pen_2);
                            Integer total_2=0;
                            Integer current_2=0;
                            cursor3.moveToFirst();
                            if(cursor3.getCount() != 0){
                                total_2=cursor3.getInt(4);
                                current_2 = cursor3.getInt(5);
                            }


                            //UPDATE PEN (BAWASAN YUNG YUNG BROODER PEN KUNG SAN GALING YUNG BROODER INVENTORY

                            boolean isBrooderPenUpdated = myDb.updatePen(brooder_pen_2, "Brooder",   total_2,current_2-Integer.parseInt(replacement_total.getText().toString()));
                            Integer current_count5 = current_2-Integer.parseInt(replacement_total.getText().toString());
                            if(isNetworkAvailable()){

                                RequestParams requestParams = new RequestParams();
                                requestParams.add("pen_number", brooder_pen_2);
                                requestParams.add("pen_current", current_count5.toString());



                                API_editPenCount(requestParams);
                            }
                            Cursor cursor_replacement_pen = myDb.getAllDataFromPenWhere(replacement_pen);
                            cursor_replacement_pen.moveToFirst();

                            if(cursor_replacement_pen.getCount() != 0){
                                //get total and capacity
                                //update total and capacity accordingly
                                //4 total
                                //5 current
                                replacement_pen_total = cursor_replacement_pen.getInt(4);
                                replacement_pen_current = cursor_replacement_pen.getInt(5);

                            }
                            boolean isReplacementPenUpdated = myDb.updatePen(replacement_pen, "Grower",   replacement_pen_total,replacement_pen_current+Integer.parseInt(replacement_total.getText().toString()));
                            Integer current_count4 = replacement_pen_current+Integer.parseInt(replacement_total.getText().toString());
                            if(isNetworkAvailable()){

                                RequestParams requestParams = new RequestParams();
                                requestParams.add("pen_number", replacement_pen);
                                requestParams.add("pen_current", current_count4.toString());



                                API_editPenCount(requestParams);
                            }
                            if(isUpdated){
                                Toast.makeText(getActivity(), "Updated brooder and brooder pen", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(getActivity(), "NOT UPDATEED brooder", Toast.LENGTH_SHORT).show();
                            }


                            boolean isInventoryInserted = myDb.insertDataReplacementInventory(id,replacement_pen_id, replacement_tag, formatted, 0,0,Integer.parseInt(replacement_total.getText().toString()),replacement_date_added.getText().toString(),null);


                            if(isNetworkAvailable) {


                                RequestParams requestParams = new RequestParams();
                                requestParams.add("replacement_id", id.toString());
                                requestParams.add("pen_id", replacement_pen_id2.toString());
                                requestParams.add("replacement_tag", replacement_tag);
                                requestParams.add("batching_date", formatted);
                                requestParams.add("number_male", "0");
                                requestParams.add("number_female", "0");
                                requestParams.add("total", replacement_total.getText().toString());
                                requestParams.add("last_update", replacement_date_added.getText().toString());
                                requestParams.add("deleted_at", null);

                                API_addReplacementInventory(requestParams);

                            }
                            if(isInventoryInserted == true && isPenUpdated == true){
                               // Toast.makeText(getActivity(), "Successfully added to database", Toast.LENGTH_SHORT).show();
                                Intent intent_line = new Intent(getActivity(), CreateReplacements.class);
                                startActivity(intent_line);

                            }else{
                                Toast.makeText(getActivity(), "Error in adding to the database", Toast.LENGTH_SHORT).show();
                            }

                        }else{


            /////////////////end of code block for within system





                            ///adding data outside system
                            boolean isInventoryInserted = myDb.insertDataReplacementInventory(id,replacement_pen_id, replacement_tag, formatted, 0,0,Integer.parseInt(replacement_total.getText().toString()),replacement_date_added.getText().toString(),null);
                            if(isNetworkAvailable) {


                                RequestParams requestParams = new RequestParams();
                                requestParams.add("replacement_id", id.toString());
                                requestParams.add("pen_id", replacement_pen_id.toString());
                                requestParams.add("replacement_tag", replacement_tag);
                                requestParams.add("batching_date", formatted);
                                requestParams.add("number_male", "0");
                                requestParams.add("number_female", "0");
                                requestParams.add("total", replacement_total.getText().toString());
                                requestParams.add("last_update", replacement_date_added.getText().toString());
                                requestParams.add("deleted_at", null);

                                API_addReplacementInventory(requestParams);

                            }
                            if(isInventoryInserted == true && isPenUpdated == true){
                                Toast.makeText(getActivity(), "Successfully added to database", Toast.LENGTH_SHORT).show();
                                Intent intent_line = new Intent(getActivity(), CreateReplacements.class);
                                startActivity(intent_line);

                            }else{
                                Toast.makeText(getActivity(), "Error in adding to the database", Toast.LENGTH_SHORT).show();
                            }

                        }
                        // boolean isInventoryInserted = myDb.insertDataBrooderInventory(id,brooder_pen, "QUEBAI"+generation_spinner.getSelectedItem().toString()+line_spinner.getSelectedItem().toString()+family_spinner.getSelectedItem().toString()+m, brooder_date_added.getText().toString()+m, null,null,Integer.parseInt(brooder_total_number.getText().toString()),null,null);


                    }else{ //KUNG MAY LAMAN YUNG DATABASE BASED SA VALUES KANINA

                        Cursor cursor_pen = myDb.getAllDataFromPenWhere(replacement_pen);
                        cursor_pen.moveToFirst();
                        Integer total=null;
                        Integer current=null;
                        if(cursor_pen.getCount() != 0){
                            total = cursor_pen.getInt(4);
                            current =  cursor_pen.getInt(5);
                        }


                        Integer brooder_id = cursorBrooderChecker.getInt(0);

                        boolean isPenUpdated = myDb.updatePen(replacement_pen, "Grower", total ,Integer.parseInt(replacement_total.getText().toString())+current);

                        Integer current_count3 = Integer.parseInt(replacement_total.getText().toString())+current;
                        if(isNetworkAvailable()){

                            RequestParams requestParams = new RequestParams();
                            requestParams.add("pen_number", replacement_pen);
                            requestParams.add("pen_current", current_count3.toString());



                            API_editPenCount(requestParams);
                        }


                        if(!isSwitchChecked){
                            //get the tag of the selected brooder,
                            //bawasan mo kung gaano karami yung itatransfer
                            //kapag zero, the delete mo yung sa brooder (meaning nalipat na lahat sa replacement)
                            String text = transfer_spinner.getSelectedItem().toString();




                            //String delims = " | ";
                            String[] tokens = text.split(Pattern.quote(" | "));
                            String brooder_tag_raw = tokens[0];
                            String brooder_pen_raw = tokens[1];


                            String delims_2 = ": ";
                            String[] brooder_info = brooder_tag_raw.split(delims_2);
                            brooder_tag_2 = brooder_info[1];


                            String[] pen_info = brooder_pen_raw.split(delims_2);
                            brooder_pen_2 = pen_info[1];





                            Integer totalCount = Integer.parseInt(replacement_total.getText().toString());

                            //dapat may cursor ka na kumukuha ng current value ng total ng brooder_inventory,
                            //then minus mo yung current total sa new total tas yun yung ipasa mo sa updateBrooderInventory


                            //updating brooder inventory's new values
                            Cursor cursor1 = myDb.getDataFromBrooderInventoryWhereTag(brooder_tag_2);

                            cursor1.moveToFirst();
                            Integer id = cursor1.getInt(0);
                            Integer current_total = cursor1.getInt(7);
                            Integer new_total = current_total - totalCount;

                            boolean isUpdated = myDb.updateBrooderInventory(brooder_tag_2, new_total);
                            if(isNetworkAvailable()){

                                RequestParams requestParams = new RequestParams();
                                requestParams.add("brooder_inv_id", id.toString());
                                requestParams.add("total_", new_total.toString());



                                API_editBrooderInventoryMaleFemale(requestParams);
                            }


                            //updating brooder pen's values
                            Cursor cursor3 = myDb.getAllDataFromPenWhere(brooder_pen_2);
                            cursor3.moveToFirst();
                            Integer total_2=0;
                            Integer current_2=0;
                            if(cursor3.getCount() == 0){
                            }else{
                               total_2 = cursor3.getInt(4);
                               current_2 = cursor3.getInt(5);
                            }






                            //UPDATE PEN (BAWASAN YUNG YUNG BROODER PEN KUNG SAN GALING YUNG BROODER INVENTORY

                            boolean isBrooderPenUpdated = myDb.updatePen(brooder_pen_2, "Brooder",   total_2,current_2-Integer.parseInt(replacement_total.getText().toString()));
                            Integer current_count2 = current_2-Integer.parseInt(replacement_total.getText().toString());
                            if(isNetworkAvailable()){

                                RequestParams requestParams = new RequestParams();
                                requestParams.add("pen_number", brooder_pen_2);
                                requestParams.add("pen_current", current_count2.toString());



                                API_editPenCount(requestParams);
                            }

                            Cursor cursor_replacement_pen = myDb.getAllDataFromPenWhere(replacement_pen);
                            cursor_replacement_pen.moveToFirst();


                            if(cursor_replacement_pen.getCount() != 0){

                                replacement_pen_total = cursor_replacement_pen.getInt(4);
                                replacement_pen_current = cursor_replacement_pen.getInt(5);

                            }


                            boolean isReplacementPenUpdated = myDb.updatePen(replacement_pen, "Grower",   replacement_pen_total,replacement_pen_current+Integer.parseInt(replacement_total.getText().toString()));
                            Integer current_count = replacement_pen_current+Integer.parseInt(replacement_total.getText().toString());
                            if(isNetworkAvailable()){

                                RequestParams requestParams = new RequestParams();
                                requestParams.add("pen_number", replacement_pen);
                                requestParams.add("pen_current", current_count.toString());



                                API_editPenCount(requestParams);
                            }
                            if(isBrooderPenUpdated){
                             //  Toast.makeText(getActivity(), "Updated Brooder pen", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(getActivity(), "NOT UPDATEED brooder", Toast.LENGTH_SHORT).show();
                            }






                            boolean isInventoryInserted = myDb.insertDataReplacementInventory(brooder_id,replacement_pen_id, replacement_tag, formatted, 0,0,Integer.parseInt(replacement_total.getText().toString()),replacement_date_added.getText().toString(),null);
                            ///adding data outside system
                            if(isNetworkAvailable) {


                                RequestParams requestParams = new RequestParams();
                                requestParams.add("replacement_id", brooder_id.toString());
                                requestParams.add("pen_id", replacement_pen_id.toString());
                                requestParams.add("replacement_tag", replacement_tag);
                                requestParams.add("batching_date", formatted);
                                requestParams.add("number_male", "0");
                                requestParams.add("number_female", "0");
                                requestParams.add("total", replacement_total.getText().toString());
                                requestParams.add("last_update", replacement_date_added.getText().toString());
                                requestParams.add("deleted_at", null);

                                API_addReplacementInventory(requestParams);

                            }
                            if(isInventoryInserted == true){
                               // Toast.makeText(getActivity(), "PLEASE WORK "+replacement_pen_id, Toast.LENGTH_SHORT).show();
                                Intent intent_line = new Intent(getActivity(), CreateReplacements.class);
                                startActivity(intent_line);

                            }else{
                                Toast.makeText(getActivity(), "Error in adding to the database", Toast.LENGTH_SHORT).show();
                            }

                        }else{
                            //   boolean isInventoryInserted = myDb.insertDataBrooderInventory(brooder_id,brooder_pen, "QUEBAI"+generation_spinner.getSelectedItem().toString()+line_spinner.getSelectedItem().toString()+family_spinner.getSelectedItem().toString()+m, brooder_date_added.getText().toString()+m, null,null,Integer.parseInt(brooder_total_number.getText().toString()),null,null);
                            boolean isInventoryInserted = myDb.insertDataReplacementInventory(brooder_id,replacement_pen_id, replacement_tag, formatted, 0,0,Integer.parseInt(replacement_total.getText().toString()),replacement_date_added.getText().toString(),null);
                            if(isNetworkAvailable) {


                                RequestParams requestParams = new RequestParams();
                                requestParams.add("replacement_id", brooder_id.toString());
                                requestParams.add("pen_id", replacement_pen_id.toString());
                                requestParams.add("replacement_tag", replacement_tag);
                                requestParams.add("batching_date", formatted);
                                requestParams.add("number_male", "0");
                                requestParams.add("number_female", "0");
                                requestParams.add("total", replacement_total.getText().toString());
                                requestParams.add("last_update", replacement_date_added.getText().toString());
                                requestParams.add("deleted_at", null);

                                API_addReplacementInventory(requestParams);

                            }
                            if(isInventoryInserted == true){
                                //Toast.makeText(getActivity(), "Successfully added to database", Toast.LENGTH_SHORT).show();/*String.format("%04d" , Integer.parseInt(mInput_generation_number.getText().toString()));*/
                                Intent intent_line = new Intent(getActivity(), CreateReplacements.class);
                                startActivity(intent_line);

                            }else{
                                Toast.makeText(getActivity(),"Replacement not added to database", Toast.LENGTH_SHORT).show();
                            }


                        }


                    }



                }else{
                    Toast.makeText(getActivity(), "Please fill any empty fields", Toast.LENGTH_SHORT).show();
                }
            }

        });


        return view;
    }
    private void API_editBrooderInventoryMaleFemale(RequestParams requestParams){
        APIHelperAsync.editBrooderInventoryMaleFemale("editBrooderInventoryMaleFemale", requestParams, new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response){
      //          Toast.makeText(context, "Successfully edited brooder male and female", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonResponse, Object response){

                Toast.makeText(context, "Failed to add Pen to web", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable{
                return null;
            }
        });
    }
    private void API_editPenCount(RequestParams requestParams){
        APIHelperAsync.editPenCount("editPenCount", requestParams, new BaseJsonHttpResponseHandler<Object>() {
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
    private String generateBrooderTag() {
        String tag= null;
        String code = null;
        String timestamp;


        Cursor cursor = myDb.getAllDataFromFarms(Integer.parseInt(farm_id));
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
//        Toast.makeText(context, tag, Toast.LENGTH_SHORT).show();
        return tag;

    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) this.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    private void API_addReplacement(RequestParams requestParams){
        APIHelperAsync.addReplacementFamily("addReplacementFamily", requestParams, new BaseJsonHttpResponseHandler<Object>() {
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
    private void API_getFarmID(String email){
        APIHelperAsync.getFarmID("getFarmID/" + email, new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response){


                farm_id = rawJsonResponse;

                farm_id = farm_id.replaceAll("\\[", "").replaceAll("\\]","");
                Cursor cursor1 = myDb.getAllDataFromFarms(Integer.parseInt(farm_id));
                cursor1.moveToFirst();

                if(cursor1.getCount() != 0){

                    batching_week2 = cursor1.getInt(4);

                }



                //  Toast.makeText(CreateGenerationsAndLines.this, "Generation and Lines loaded from database", Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonResponse, Object response){

//                Toast.makeText(context, "Failed to get Farm ID ", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable{
                return null;
            }
        });
    }
    private void API_addReplacementInventory(RequestParams requestParams){
        APIHelperAsync.addReplacementInventory("addReplacementInventory", requestParams, new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response){
                Toast.makeText(getActivity(), "Successfully added replacement inventory to web", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonResponse, Object response){

                Toast.makeText(getActivity(), "Failed to add replacement inventory to web", Toast.LENGTH_SHORT).show();
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
        generation_spinner.setAdapter(dataAdapter);



    }

    public void loadSpinnerDataForLine(String selected_generation){
        DatabaseHelper db = new DatabaseHelper(getContext());
        List<String> lines = db.getAllDataFromLineasList(selected_generation);
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, lines);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        line_spinner.setAdapter(dataAdapter2);
    }
    public void loadSpinnerDataForFamily(String selected_line, String selected_generation){
        DatabaseHelper db = new DatabaseHelper(getContext());
        List<String> lines = db.getAllDataFromFamilyasList(selected_line, selected_generation);
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, lines);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        family_spinner.setAdapter(dataAdapter2);
    }
    public void loadSpinnerDataForBrooder(String selected_family, String selected_line, String selected_generation){
        DatabaseHelper db = new DatabaseHelper(getContext());
        List<String> lines = db.getAllDataFromBroodersasList(selected_family, selected_line, selected_generation);
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, lines);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        transfer_spinner.setAdapter(dataAdapter2);
    }
    public void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

}