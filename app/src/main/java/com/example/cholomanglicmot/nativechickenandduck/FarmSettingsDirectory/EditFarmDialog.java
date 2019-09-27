package com.example.cholomanglicmot.nativechickenandduck.FarmSettingsDirectory;

import android.app.AlertDialog;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cholomanglicmot.nativechickenandduck.APIHelperAsync;
import com.example.cholomanglicmot.nativechickenandduck.DatabaseHelper;
import com.example.cholomanglicmot.nativechickenandduck.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.Header;

public class EditFarmDialog extends DialogFragment {

    private static final String TAG = "EditFarmDialog";
    private EditText edit_farm_name, edit_farm_address, edit_batching_week;

    private Button mActionOk;
    DatabaseHelper myDb;
    StringBuffer buffer = new StringBuffer();
    Context context;
    String input_farm_name;
    String input_farm_address;
    Integer input_batching_week,id;
    Integer farm_id=0;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_edit_farm, container, false);
        mActionOk = view.findViewById(R.id.action_ok);
        edit_farm_name = view.findViewById(R.id.edit_farm_name);
        edit_farm_address = view.findViewById(R.id.edit_farm_address);
        edit_batching_week = view.findViewById(R.id.edit_batching_week);
        context = getActivity().getApplicationContext();
        myDb = new DatabaseHelper(getContext());

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


        String fname=null,faddress=null;
        Integer bweek=0;
        Cursor cursor = myDb.getAllDataFromFarms(farm_id);  //ang laman lang ng table na to ay isa
        cursor.moveToFirst();
        if(cursor.getCount() != 0){
            id = cursor.getInt(0);
            fname = cursor.getString(1);
            faddress = cursor.getString(3);
            bweek = cursor.getInt(4);
        }

        edit_farm_name.setHint(fname);
        edit_farm_address.setHint(faddress);
        edit_batching_week.setHint(bweek.toString());

        mActionOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edit_farm_name.getText().toString().isEmpty()){
                    input_farm_name = edit_farm_name.getHint().toString();
                }else{
                    input_farm_name = edit_farm_name.getText().toString();
                }

                if(edit_farm_address.getText().toString().isEmpty()){
                    input_farm_address = edit_farm_address.getHint().toString();
                }else{
                    input_farm_address = edit_farm_address.getText().toString();
                }

                if(edit_batching_week.getText().toString().isEmpty()){
                    input_batching_week = Integer.parseInt(edit_batching_week.getHint().toString());
                }else{
                    input_batching_week = Integer.parseInt(edit_batching_week.getText().toString());
                }

                 boolean isUpdated = myDb.updateFarm(id, input_farm_name, input_farm_address,input_batching_week);
                if(isNetworkAvailable()){

                    RequestParams requestParams = new RequestParams();
                    requestParams.add("farm_id", id.toString());
                    requestParams.add("farm_name", input_farm_name);
                    requestParams.add("farm_address", input_farm_address);
                    requestParams.add("farm_batching_week", input_batching_week.toString());



                    API_editFarm(requestParams);
                }


                if(isUpdated){
                    Toast.makeText(getActivity(), "Successfully edited farm", Toast.LENGTH_SHORT).show();
                    Intent intent_line = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent_line);
                }


            }

        });

        return view;
    }
    private void API_editFarm(RequestParams requestParams){
        APIHelperAsync.editFarm("editFarm", requestParams, new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response){
                Toast.makeText(context, "Successfully edited farm to web", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonResponse, Object response){

                Toast.makeText(context, "Failed to edit farm", Toast.LENGTH_SHORT).show();
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

    public void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }


}