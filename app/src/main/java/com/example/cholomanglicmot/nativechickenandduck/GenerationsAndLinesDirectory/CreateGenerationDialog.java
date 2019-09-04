package com.example.cholomanglicmot.nativechickenandduck.GenerationsAndLinesDirectory;

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
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.cholomanglicmot.nativechickenandduck.APIHelper;
import com.example.cholomanglicmot.nativechickenandduck.DatabaseHelper;
import com.example.cholomanglicmot.nativechickenandduck.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.Header;

public class CreateGenerationDialog extends DialogFragment {

    private static final String TAG = "CreateGenerationDialog";
    private EditText mInput_generation_number,mInput_generation_status;
    private RadioGroup radioGroup;
    private RadioButton radioButton,radioButton2,radioButton3;
    public RecyclerView recyclerView;
    RecyclerView.Adapter recycler_adapter;

    private EditText mInput_pen_capacity;
    private Button mActionOk;
    DatabaseHelper myDb;
    String generation_number;
    Context context;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_create_generation, container, false);
        mActionOk = view.findViewById(R.id.action_ok);
        mInput_generation_number = view.findViewById(R.id.generation_no);
        context = getActivity().getApplicationContext();
        // find the radiobutton by returned id




        myDb = new DatabaseHelper(getContext());


        mActionOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(!mInput_generation_number.getText().toString().isEmpty()){
                    switch (mInput_generation_number.getText().toString().length()){
                        case 1:
                            generation_number = String.format("%04d" , Integer.parseInt(mInput_generation_number.getText().toString()));
                            break;
                        case 2:
                            generation_number = String.format("%04d" , Integer.parseInt(mInput_generation_number.getText().toString()));
                            break;
                        case 3:
                            generation_number = String.format("%04d" , Integer.parseInt(mInput_generation_number.getText().toString()));
                            break;
                        case 4:
                            generation_number = String.format("%04d" , Integer.parseInt(mInput_generation_number.getText().toString()));
                            break;
                        default:
                            break;

                    }

                    FirebaseAuth mAuth;

                    mAuth = FirebaseAuth.getInstance();

                    FirebaseUser user = mAuth.getCurrentUser();

                    String name = user.getDisplayName();

                    String email = user.getEmail();

                    Uri photo = user.getPhotoUrl();
                    ////sample farm_id pero dapat kukunin mo to sa database
                    Integer farm_id =0;
                    Cursor cursor_farm_id = myDb.getFarmIDFromUsers(email);
                    cursor_farm_id.moveToFirst();
                    if(cursor_farm_id.getCount() != 0){
                        farm_id = cursor_farm_id.getInt(0);
                    }

                    Integer is_active = 1;
                    //insert to local database
                    boolean isInserted = myDb.insertDataGeneration(farm_id, generation_number, Integer.parseInt(mInput_generation_number.getText().toString()), is_active);


                    //insert to web database

                    //GET farm_id from database
                    //check for internet connection
                    /**/
//                    boolean isNetworkAvailable = isNetworkAvailable();
//                    if(isNetworkAvailable){
//                        RequestParams requestParams = new RequestParams();
//                        requestParams.add("farm_id", farm_id.toString());
//                        requestParams.add("number", generation_number);
//                        requestParams.add("numerical_generation", mInput_generation_number.getText().toString());
//                        requestParams.add("is_active", is_active.toString());
//                        requestParams.add("deleted_at", null);
//
//                        API_addGeneration(requestParams);
//                    }


                    if(isInserted == true){
                        Toast.makeText(getActivity(),"Generation added to database", Toast.LENGTH_SHORT).show();
                        Intent intent_generation = new Intent(getActivity(), CreateGenerationsAndLines.class);
                        startActivity(intent_generation);

                        getDialog().dismiss();
                    }else{
                       // Toast.makeText(getActivity(),"Generation not added to database", Toast.LENGTH_SHORT).show();
                    }
                    getDialog().dismiss();
                }else{
                    Toast.makeText(getActivity(), "Please fill any empty fields", Toast.LENGTH_SHORT).show();
                }



            }

        });

        return view;
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    private void API_addGeneration(RequestParams requestParams){
        APIHelper.addGeneration("addGeneration", requestParams, new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response){
                Toast.makeText(context, "Successfully added Generation to web", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonResponse, Object response){

                Toast.makeText(context, "Failed to add Generation to web", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable{
                return null;
            }
        });
    }

}