package com.example.cholomanglicmot.nativechickenandduck.BroodersDirectory;


import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cholomanglicmot.nativechickenandduck.APIHelper;
import com.example.cholomanglicmot.nativechickenandduck.DatabaseHelper;
import com.example.cholomanglicmot.nativechickenandduck.R;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.Calendar;

import cz.msebera.android.httpclient.Header;


/**
 * A simple {@link Fragment} subclass.
 */
public class MortalityFragmentBrooder extends Fragment {
    private Button account_submit_button;
    private TextView profile_id, text;
    private EditText date_died, male_death, female_death,total_death, remarks ;
    Spinner reason;
    DatabaseHelper myDb;
    Calendar calendar, calendar2;
    Integer male, female;
    LinearLayout total, male_and_female;
    boolean sexingDone=true;
    boolean isInserted;
    String brooder_pen_number2;

    public MortalityFragmentBrooder() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mort, container, false);
        final Integer breeder_id = getArguments().getInt("Brooder ID");
        final String breeder_tag = getArguments().getString("Brooder Tag");
        final Integer brooder_pen_number = getArguments().getInt("Brooder Pen ID");
        myDb = new DatabaseHelper(getContext());



        total = view.findViewById(R.id.total);
        male_and_female = view.findViewById(R.id.male_and_female);

        date_died = view.findViewById(R.id.date_died);
        male_death = view.findViewById(R.id.male_death);
        total_death = view.findViewById(R.id.total_death);
        female_death = view.findViewById(R.id.female_death);
        remarks = view.findViewById(R.id.remarks);
        reason = view.findViewById(R.id.spinner_reason);

        Cursor cursor = myDb.getDataFromBrooderInventoryWhereTag(breeder_tag);
        cursor.moveToFirst();
        if(cursor.getCount()!=0){
            male = cursor.getInt(5);
            female = cursor.getInt(6);
        }

        if((male == 0 || male == null) && (female == 0 || female == null)){
            male_and_female.setVisibility(View.GONE);
            total.setVisibility(View.VISIBLE);
            sexingDone = false;
        }
        date_died.setOnClickListener(new View.OnClickListener() {
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
                        date_died.setText(selectedYear + "-" + selectedMonth + "-" + selectedDay);
                        calendar.set(selectedYear,selectedMonth,selectedDay);
                    }
                }, year, month, day);
                mDatePicker.show();

            }
        });
        account_submit_button = view.findViewById(R.id.action_ok);
        account_submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!date_died.getText().toString().isEmpty() && (!male_death.getText().toString().isEmpty() || !female_death.getText().toString().isEmpty() || !total_death.getText().toString().isEmpty()) && !reason.getSelectedItem().toString().isEmpty()){
                    Cursor cursor = myDb.getDataFromBrooderInventoryWhereTag(breeder_tag);
                    cursor.moveToFirst();
                    Integer current_male_count = cursor.getInt(5);
                    Integer current_female_count = cursor.getInt(6);
                    Integer current_total_count = cursor.getInt(7);
                    if(sexingDone){
                        isInserted = myDb.insertDataMortalityAndSales(date_died.getText().toString(),null,null,breeder_id, "brooder", "died", null,Integer.parseInt(male_death.getText().toString()), Integer.parseInt(female_death.getText().toString()),Integer.parseInt(male_death.getText().toString())+Integer.parseInt(female_death.getText().toString()),reason.getSelectedItem().toString(), remarks.getText().toString(), null );
                        boolean isNetworkAvailable = isNetworkAvailable();
                        if(isNetworkAvailable){
                            Integer total = Integer.parseInt(male_death.getText().toString())+Integer.parseInt(female_death.getText().toString());
                            RequestParams requestParams = new RequestParams();
                            //requestParams.add("id", id_0.toString());
                            requestParams.add("date", date_died.getText().toString());
                            requestParams.add("brooder_inventory_id", breeder_id.toString());
                            requestParams.add("type", "brooder");
                            requestParams.add("category", "died");
                            requestParams.add("male", male_death.getText().toString());
                            requestParams.add("female", female_death.getText().toString());
                            requestParams.add("total", total.toString());
                            requestParams.add("reason", reason.getSelectedItem().toString());
                            requestParams.add("remarks", remarks.getText().toString());




                            API_addMortalityAndSales(requestParams);
                        }
                        Integer male_death_ =  Integer.parseInt(male_death.getText().toString());
                        Integer female_death_ = Integer.parseInt(female_death.getText().toString());





                        Integer new_male_count = current_male_count - male_death_;
                        Integer new_female_count = current_female_count - female_death_;
                        Integer new_total_count = new_male_count + new_female_count;
                        //then update breeder_inventory database (subtract male and female death from current, update total from breeder_invetory too)
                        boolean isBreederInvUpdated = myDb.updateMaleFemaleBrooderCount(breeder_tag, new_male_count,new_female_count, new_total_count);
                    }else{
                        isInserted = myDb.insertDataMortalityAndSales(date_died.getText().toString(),null,null,breeder_id, "brooder", "died", null,null, null,Integer.parseInt(total_death.getText().toString()),reason.getSelectedItem().toString(), remarks.getText().toString(), null );
                        boolean isNetworkAvailable = isNetworkAvailable();
                        if(isNetworkAvailable){

                            RequestParams requestParams = new RequestParams();
                            //requestParams.add("id", id_0.toString());
                            requestParams.add("date", date_died.getText().toString());
                            requestParams.add("brooder_inventory_id", breeder_id.toString());
                            requestParams.add("type", "brooder");
                            requestParams.add("category", "died");
                            requestParams.add("male", null);
                            requestParams.add("female", null);
                            requestParams.add("total", total_death.getText().toString());
                            requestParams.add("reason", reason.getSelectedItem().toString());
                            requestParams.add("remarks", remarks.getText().toString());




                            API_addMortalityAndSales(requestParams);
                        }

                        Integer total_death_ = Integer.parseInt(total_death.getText().toString());


                        Integer new_total_count = current_total_count - total_death_;
                        boolean isBreederInvUpdated = myDb.updateMaleFemaleBrooderCount(breeder_tag, current_male_count,current_female_count, new_total_count);

                    }


                    Cursor cursor1 = myDb.getAllDataFromPenWhereID(brooder_pen_number);
                    cursor1.moveToFirst();
                    if(cursor1.getCount() != 0){
                        brooder_pen_number2 = cursor1.getString(2);
                    }

                    if(isInserted){
                        Toast.makeText(getActivity(),"Database updated", Toast.LENGTH_SHORT).show();
                         Intent intent_line = new Intent(getActivity(), MortalityAndSalesRecordsBrooder.class);
                         intent_line.putExtra("Brooder Pen ID",brooder_pen_number);
                         intent_line.putExtra("Brooder Tag",breeder_tag);
                        intent_line.putExtra("Brooder ID",breeder_id);
                         startActivity(intent_line);
                    }else{
                        Toast.makeText(getActivity(),"Error", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(getActivity(),"Please fill empty fields", Toast.LENGTH_SHORT).show();
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

    private void API_addMortalityAndSales(RequestParams requestParams){
        APIHelper.addMortalityAndSales("addMortalityAndSales", requestParams, new BaseJsonHttpResponseHandler<Object>() {
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
