package com.example.cholomanglicmot.nativechickenandduck.GenerationsAndLinesDirectory;

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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.cholomanglicmot.nativechickenandduck.APIHelperAsync;
import com.example.cholomanglicmot.nativechickenandduck.DatabaseHelper;
import com.example.cholomanglicmot.nativechickenandduck.R;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.List;

import cz.msebera.android.httpclient.Header;

public class CreateLineDialog extends DialogFragment {

    private static final String TAG = "CreateFamilyDialog";
    private EditText line_number;
    private Spinner spinner;
    private Button mActionOk;
    DatabaseHelper myDb;
    public RecyclerAdapter_Generation adapter;
    StringBuffer buffer;
    Context context;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_create_line, container, false);
        mActionOk = view.findViewById(R.id.action_ok);
        line_number = view.findViewById(R.id.line_number);
        spinner = (Spinner) view.findViewById(R.id.line_spinner);
        context = getActivity().getApplicationContext();

        loadSpinnerData();

        myDb = new DatabaseHelper(getContext());


        mActionOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(!line_number.getText().toString().isEmpty()){
                    Cursor cursor_generation = myDb.getDataFromGenerationWhereGenNumber(spinner.getSelectedItem().toString());
                    cursor_generation.moveToFirst();
                    if(cursor_generation.getCount() != 0){
                        Integer generation_id = cursor_generation.getInt(0);
                        String line = line = String.format("%04d", Integer.parseInt(line_number.getText().toString()));

                        boolean isInserted = myDb.insertDataLine(line,1,generation_id);

                        if (isInserted) {
                            Toast.makeText(getActivity(),"Generation added to database", Toast.LENGTH_SHORT).show();
                            Intent intent_line = new Intent(getActivity(), CreateGenerationsAndLines.class);
                            startActivity(intent_line);
                            getDialog().dismiss();
                        }else{
                            Toast.makeText(getActivity(),"Generation not added to database", Toast.LENGTH_SHORT).show();
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
    private void API_addLine(RequestParams requestParams){
        APIHelperAsync.addLine("addLine", requestParams, new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response){
                Toast.makeText(context, "Successfully added Line to web", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonResponse, Object response){

                Toast.makeText(context, "Failed to add Line to web", Toast.LENGTH_SHORT).show();
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
    private void loadSpinnerData() {
        // database handler
        DatabaseHelper db = new DatabaseHelper(getContext());

        // Spinner Drop down elements
        List<String> generations = db.getAllDataFromGenerationasList();

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, generations);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);


    }
    public void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

}