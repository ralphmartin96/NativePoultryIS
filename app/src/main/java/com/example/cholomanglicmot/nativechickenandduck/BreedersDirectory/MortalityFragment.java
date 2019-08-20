package com.example.cholomanglicmot.nativechickenandduck.BreedersDirectory;


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
public class MortalityFragment extends Fragment {
    private Button account_submit_button;
    private TextView profile_id, text;
    private EditText date_died, male_death, female_death, remarks ;
    Spinner reason;
    DatabaseHelper myDb;
    Calendar calendar, calendar2;

    public MortalityFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mort, container, false);
        final Integer breeder_id = getArguments().getInt("Breeder ID");
        final String breeder_tag = getArguments().getString("Breeder Tag");
        myDb = new DatabaseHelper(getContext());




        date_died = view.findViewById(R.id.date_died);
        male_death = view.findViewById(R.id.male_death);
        female_death = view.findViewById(R.id.female_death);
        remarks = view.findViewById(R.id.remarks);
        reason = view.findViewById(R.id.spinner_reason);


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

                if(!date_died.getText().toString().isEmpty() && !male_death.getText().toString().isEmpty() && !female_death.getText().toString().isEmpty() && !reason.getSelectedItem().toString().isEmpty()){
                    boolean isInserted = myDb.insertDataMortalityAndSales(date_died.getText().toString(),breeder_id,null,null, "breeder", "died", null,Integer.parseInt(male_death.getText().toString()), Integer.parseInt(female_death.getText().toString()),Integer.parseInt(male_death.getText().toString())+Integer.parseInt(female_death.getText().toString()),reason.getSelectedItem().toString(), remarks.getText().toString(), null );

                    if(isNetworkAvailable()){
                        Integer total = Integer.parseInt(male_death.getText().toString())+Integer.parseInt(female_death.getText().toString());
                        RequestParams requestParams = new RequestParams();
                        //requestParams.add("id", id_0.toString());
                        requestParams.add("date", date_died.getText().toString());
                        requestParams.add("breeder_inventory_id", breeder_id.toString());
                        requestParams.add("type", "breeder");
                        requestParams.add("category", "died");
                        requestParams.add("male", male_death.getText().toString());
                        requestParams.add("female", female_death.getText().toString());
                        requestParams.add("total", total.toString());
                        requestParams.add("reason", reason.getSelectedItem().toString());
                        requestParams.add("remarks", remarks.getText().toString());




                        API_addMortalityAndSales(requestParams);
                    }
                    Cursor cursor = myDb.getDataFromBreederInvWhereTag(breeder_tag);
                    cursor.moveToFirst();
                    Integer male_death_ =  Integer.parseInt(male_death.getText().toString());
                    Integer female_death_ = Integer.parseInt(female_death.getText().toString());


                    Integer current_male_count = cursor.getInt(5);
                    Integer current_female_count = cursor.getInt(6);


                    Integer new_male_count = current_male_count - male_death_;
                    Integer new_female_count = current_female_count - female_death_;
                    Integer new_total_count = new_male_count + new_female_count;
                    //then update breeder_inventory database (subtract male and female death from current, update total from breeder_invetory too)
                    boolean isBreederInvUpdated = myDb.updateMaleFemaleBreederCount(breeder_tag, new_male_count,new_female_count, new_total_count);



                    if(isInserted == true && isBreederInvUpdated == true){
                        Toast.makeText(getActivity(),"Database updated", Toast.LENGTH_SHORT).show();
                         Intent intent_line = new Intent(getActivity(), MortalityAndSalesRecords.class);
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
