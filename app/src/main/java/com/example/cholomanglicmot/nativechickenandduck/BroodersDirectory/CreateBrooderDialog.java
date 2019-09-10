package com.example.cholomanglicmot.nativechickenandduck.BroodersDirectory;

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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
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

import cz.msebera.android.httpclient.Header;

/*import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;*/

public class CreateBrooderDialog extends DialogFragment {

    private static final String TAG = "CreateBrooderFragment";
    private EditText brooder_estimated_date_of_hatch,brooder_date_added, brooder_total_number;
    private Spinner line_spinner, generation_spinner, family_spinner;
    Integer batching_week, batching_week2;
    String farm_id, brooder_tag, brooder_tag2;

    private Button mActionOk;
    private Calendar calendar,calendar2;
    private Context context;
    private String brooder_pen;
    Integer brooder_pen_id;
    Brooders_Pen brooders_pen;
    DatabaseHelper myDb;
    APIHelper APIHelper;
    Random random = new Random();
    ArrayList<Pen> arrayListPen = new ArrayList<>();
    ArrayList<Pen> arrayListPen2 = new ArrayList<>();
    String farmcode;
    String formatted=null;

    ArrayList<Brooders> arrayListBrooders = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_create_brooder, container, false);

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


        brooder_pen = getArguments().getString("brooder_pen");

        generation_spinner = (Spinner) view.findViewById(R.id.generation_spinner);
        line_spinner = (Spinner) view.findViewById(R.id.line_spinner);
        family_spinner = (Spinner) view.findViewById(R.id.family_spinner);
        brooder_estimated_date_of_hatch = view.findViewById(R.id.replacement_feeding_date_collected);
        brooder_date_added = view.findViewById(R.id.brooder_date_added);
        brooder_total_number = view.findViewById(R.id.total_brooder_number);
        context = getActivity();

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
        APIHelper = new APIHelper();
        myDb = new DatabaseHelper(getContext());

        brooder_estimated_date_of_hatch.setOnClickListener(new View.OnClickListener() {
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
                        brooder_estimated_date_of_hatch.setText(selectedYear + "-" + selectedMonth + "-" + selectedDay);

                    }
                }, year, month, day);
                mDatePicker.show();

            }
        });
        brooder_date_added.setOnClickListener(new View.OnClickListener() {
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
                        brooder_date_added.setText(selectedYear + "-" + selectedMonth + "-" + selectedDay);
                        calendar2.set(selectedYear,selectedMonth,selectedDay);
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

        ///GET BATCHING WEEK FROM DATABASE


        mActionOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringBuffer buffer = new StringBuffer();




                if(!generation_spinner.getSelectedItem().toString().isEmpty() && !line_spinner.getSelectedItem().toString().isEmpty() && !family_spinner.getSelectedItem().toString().isEmpty() && !brooder_total_number.getText().toString().isEmpty() &&!brooder_estimated_date_of_hatch.getText().toString().isEmpty() && !brooder_date_added.getText().toString().isEmpty() ){


                    //dapat ang ipapasa dito ay yung primary key ng family number
                    //gawa ka ng function na nagrereturn ng id ng family based sa selected line and generation. gayahin mo nalang yung sa spinner loader ng family

                    //Cursor cursorBrooderChecker = myDb.getAllDataFromBroodersWhere(generation_spinner.getSelectedItem().toString(),line_spinner.getSelectedItem().toString(),family_spinner.getSelectedItem().toString());
                    Cursor cursorBrooderChecker = myDb.familyChecker(family_spinner.getSelectedItem().toString(),line_spinner.getSelectedItem().toString(),generation_spinner.getSelectedItem().toString());
                    cursorBrooderChecker.moveToFirst();
                    //kapag wala pang laman

                    //isInternetAvailable -> API
                    //else, local saving

                    if(cursorBrooderChecker.getCount()==0){
                        Integer familyID = null;
                        Cursor cursor_familyID = myDb.getFamilyID(family_spinner.getSelectedItem().toString(),line_spinner.getSelectedItem().toString(),generation_spinner.getSelectedItem().toString());
                        cursor_familyID.moveToFirst();
                        if(cursor_familyID.getCount() != 0){
                            familyID = cursor_familyID.getInt(0);
                        }
                        boolean isInserted = myDb.insertDataBrooder(familyID,brooder_date_added.getText().toString(),null);


//                        if(isNetworkAvailable) {
//
//
//                            RequestParams requestParams = new RequestParams();
//                            requestParams.add("family_id", familyID.toString());
//                            requestParams.add("date_added", brooder_date_added.getText().toString());
//                            requestParams.add("deleted_at", null);
//
//                            API_addBrooder(requestParams);
//
//                        }


                        //GET ID OF INSERTED BROODER

                        Cursor cursor = myDb.getIDFromBroodersWhere(familyID);
                        cursor.moveToFirst();


                        Integer id = cursor.getInt(0);

                        brooder_tag2 = generateBrooderTag();
                        boolean isInventoryInserted = myDb.insertDataBrooderInventory(id,brooder_pen_id, brooder_tag2, formatted, null,null,Integer.parseInt(brooder_total_number.getText().toString()),brooder_date_added.getText().toString(),null);
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
                            requestParams.add("total", brooder_total_number.getText().toString());
                            requestParams.add("last_update", brooder_date_added.getText().toString());
                            requestParams.add("deleted_at", null);



                            API_addBrooderInventory(requestParams);
                        }

                        Integer total = 0;
                        Integer current = 0;

                        Cursor cur2 = myDb.getAllDataFromPenWhere(brooder_pen);
                        cur2.moveToFirst();
                        if(cur2.getCount() != 0){
                            total = cur2.getInt(4);
                            current = cur2.getInt(5);
                        }


                        boolean isPenUpdated = myDb.updatePen(brooder_pen, "Brooder", total,(Integer.parseInt(brooder_total_number.getText().toString())+current));
                        Integer current_count = (Integer.parseInt(brooder_total_number.getText().toString())+current);
                        if(isNetworkAvailable()){

                            RequestParams requestParams = new RequestParams();
                            requestParams.add("pen_number", brooder_pen);
                            requestParams.add("pen_current", current_count.toString());



                            API_editPenCount(requestParams);
                        }
                        if(isPenUpdated){
                           // Toast.makeText(getActivity(), "Successfully added to database", Toast.LENGTH_SHORT).show();
                            Intent intent_line = new Intent(getActivity(), CreateBrooders.class);
                            startActivity(intent_line);


                        }else{
                            Toast.makeText(getActivity(), "Error in adding to the database", Toast.LENGTH_SHORT).show();
                        }

                    }else{ //KUNG MAY LAMAN YUNG DATABASE BASED SA VALUES KANINA


                        Integer total = 0;
                        Integer current = 0;

                        Cursor cursor2 = myDb.getAllDataFromPenWhere(brooder_pen);
                        cursor2.moveToFirst();
                        if(cursor2.getCount() != 0){
                            total = cursor2.getInt(4);
                            current = cursor2.getInt(5);
                        }



                        Integer brooder_id = cursorBrooderChecker.getInt(0);
                        boolean isPenUpdated = myDb.updatePen(brooder_pen, "Brooder", total,(Integer.parseInt(brooder_total_number.getText().toString())+current));
                        Integer current_count = (Integer.parseInt(brooder_total_number.getText().toString())+current);
                        if(isNetworkAvailable()){

                            RequestParams requestParams = new RequestParams();
                            requestParams.add("pen_number", brooder_pen);
                            requestParams.add("pen_current", current_count.toString());



                            API_editPenCount(requestParams);
                        }
                        if(isPenUpdated == true){
                          //  Toast.makeText(getActivity(), "Successfully added to database", Toast.LENGTH_SHORT).show();/*String.format("%04d" , Integer.parseInt(mInput_generation_number.getText().toString()));*/
                            Intent intent_line = new Intent(getActivity(), CreateBrooders.class);
                            startActivity(intent_line);

                        }
                        brooder_tag2 = generateBrooderTag();

                        boolean isInventoryInserted = myDb.insertDataBrooderInventory(brooder_id,brooder_pen_id, brooder_tag2, formatted, null,null,Integer.parseInt(brooder_total_number.getText().toString()),brooder_date_added.getText().toString(),null);
                        Integer id_0=null;
                        Cursor cursor3 = myDb.getDataFromBrooderInventoryWhereTag(brooder_tag2);
                        cursor3.moveToFirst();
                        if(cursor3.getCount() != 0){
                            id_0 = cursor3.getInt(0);
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
                            requestParams.add("total", brooder_total_number.getText().toString());
                            requestParams.add("last_update", brooder_date_added.getText().toString());
                            requestParams.add("deleted_at", null);



                            API_addBrooderInventory(requestParams);
                        }



                        if(isInventoryInserted == true){
                          //  Toast.makeText(getActivity(), "Successfully added to database", Toast.LENGTH_SHORT).show();

                            getDialog().dismiss();
                        }else{
                            Toast.makeText(getActivity(),"Brooder not added to database", Toast.LENGTH_SHORT).show();
                        }
                        getDialog().dismiss();
                    }



                }else{
                    Toast.makeText(getActivity(), "Please fill any empty fields", Toast.LENGTH_SHORT).show();
                }



            }

        });


        return view;
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
        Toast.makeText(context, tag, Toast.LENGTH_SHORT).show();
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

       //         Toast.makeText(getContext(), "Failed to add to web", Toast.LENGTH_SHORT).show();
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
    private void API_getFarmID(String email){
        APIHelper.getFarmID("getFarmID/"+email, new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response){


                farm_id = rawJsonResponse;

                farm_id = farm_id.replaceAll("\\[", "").replaceAll("\\]","");

                API_getFarmBatchingWeek(farm_id);
                Cursor cursor1 = myDb.getAllDataFromFarms(Integer.parseInt(farm_id));
                cursor1.moveToFirst();

                if(cursor1.getCount() != 0){

                    batching_week2 = cursor1.getInt(4);

                }

                //  Toast.makeText(CreateGenerationsAndLines.this, "Generation and Lines loaded from database", Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonResponse, Object response){

              //  Toast.makeText(context, "Failed to get Farm ID ", Toast.LENGTH_SHORT).show();
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


    private void API_getFarmBatchingWeek(String farm_id){
        APIHelper.getFarmBatchingWeek("getFarmBatchingWeek/"+farm_id, new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response){



                batching_week = Integer.parseInt(rawJsonResponse.replaceAll("\\[", "").replaceAll("\\]",""));




            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonResponse, Object response){

                Toast.makeText(getActivity(), "Failed ", Toast.LENGTH_SHORT).show();
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
    public void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

}