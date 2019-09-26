package com.example.cholomanglicmot.nativechickenandduck.FamilyDirectory;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.cholomanglicmot.nativechickenandduck.APIHelper;
import com.example.cholomanglicmot.nativechickenandduck.DatabaseHelper;
import com.example.cholomanglicmot.nativechickenandduck.R;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.List;

import cz.msebera.android.httpclient.Header;

public class CreateFamilyDialog extends DialogFragment {

    private static final String TAG = "CreateFamilyDialog";
    private EditText family_number;
    private Spinner generation_spinner, line_spinner;
    private Button mActionOk;
    DatabaseHelper myDb;
    StringBuffer buffer = new StringBuffer();
    Context context;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_create_family, container, false);
        mActionOk = view.findViewById(R.id.action_ok);
        family_number = view.findViewById(R.id.brooder_feeding_offered);
        generation_spinner = (Spinner) view.findViewById(R.id.generation_spinner);
        context = getActivity().getApplicationContext();

        line_spinner = view.findViewById(R.id.line_spinner);
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
      
        myDb = new DatabaseHelper(getContext());

        mActionOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(!family_number.getText().toString().isEmpty()){
                    Cursor cursor_line = myDb.getDataFromLineWhereLineNumber(line_spinner.getSelectedItem().toString());
                    cursor_line.moveToFirst();
                    if(cursor_line.getCount() != 0){
                        Integer line_id = cursor_line.getInt(0);
                        String family = new String();
                        String line = new String();
                        String generation = new String();

                        family = String.format("%04d" , Integer.parseInt(family_number.getText().toString()));
                        line = line_spinner.getSelectedItem().toString();
                        generation = generation_spinner.getSelectedItem().toString();

                        Log.d("POULTRYDEBUGGER", family + " " + line + " " +generation);

                        boolean isInserted = myDb.insertDataFamily(family,1,line_id);

                        if(isInserted){
                            Toast.makeText(getActivity(),"Family added to database", Toast.LENGTH_SHORT).show();
                            Intent intent_family = new Intent(getActivity(), CreateFamilies.class);
                            startActivity(intent_family);
                            getDialog().dismiss();
                        }else{
                            Toast.makeText(getActivity(),"Family not added to database", Toast.LENGTH_SHORT).show();
                        }

                        isInserted = myDb.insertDataFamilyDisplay(line, family, 1, generation);

                        getDialog().dismiss();
                    }

                }else{
                    Toast.makeText(getActivity(), "Please fill any empty fields", Toast.LENGTH_SHORT).show();
                }



            }

        });

        return view;
    }
    private void API_addFamily(RequestParams requestParams){
        APIHelper.addFamily("addFamily", requestParams, new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response){
                Toast.makeText(context, "Successfully added Family to web", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonResponse, Object response){

                Toast.makeText(context, "Failed to add Family to web", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable{
                return null;
            }
        });
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    private void loadSpinnerDataForGeneration() {
        // database handler
        DatabaseHelper db = new DatabaseHelper(getContext());

        // Spinner Drop down elements
        List<String> generations = db.getAllDataFromGenerationasList();
      //  List<String> lines = db.getAllDataFromLineasList();

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, generations);
        /*ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, lines);*/


        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
      //  dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        generation_spinner.setAdapter(dataAdapter);
       // line_spinner.setAdapter(dataAdapter2);


    }

    public void loadSpinnerDataForLine(String selected_generation){
        DatabaseHelper db = new DatabaseHelper(getContext());
        List<String> lines = db.getAllDataFromLineasList(selected_generation);
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, lines);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        line_spinner.setAdapter(dataAdapter2);
    }
    public void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }


}