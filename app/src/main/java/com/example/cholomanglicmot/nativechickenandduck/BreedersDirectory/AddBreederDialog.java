package com.example.cholomanglicmot.nativechickenandduck.BreedersDirectory;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cholomanglicmot.nativechickenandduck.APIHelper;
import com.example.cholomanglicmot.nativechickenandduck.DatabaseHelper;
import com.example.cholomanglicmot.nativechickenandduck.R;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.Header;

public class AddBreederDialog extends DialogFragment {

    private static final String TAG = "CreateGenerationDialog";
    private EditText male_quantity,female_quantity;

    private Button mActionOk;
    DatabaseHelper myDb;
    TextView textView;
    Context context;
    Integer breeder_id;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_add_breeder, container, false);
        final String breeder_tag = getArguments().getString("Breeder Tag");
        mActionOk = view.findViewById(R.id.action_ok);
        male_quantity = view.findViewById(R.id.male_quantity);
        female_quantity = view.findViewById(R.id.female_quantity);
        textView = view.findViewById(R.id.textView);
        context = getActivity();
        textView.setText("Add Breeders to "+ breeder_tag);

        myDb = new DatabaseHelper(getContext());

        mActionOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int current_male_count = 0;
                int current_female_count = 0;


                if(!female_quantity.getText().toString().isEmpty() || !male_quantity.getText().toString().isEmpty()){
                    Cursor cursor = myDb.getDataFromBreederInvWhereTag(breeder_tag);
                    cursor.moveToFirst();
                    if(cursor.getCount() != 0){
                        breeder_id = cursor.getInt(0);
                        current_male_count = cursor.getInt(5);
                        current_female_count = cursor.getInt(6);
                    }
                    cursor.close();
                    Integer new_male_count = Integer.parseInt(male_quantity.getText().toString())+ current_male_count;
                    Integer new_female_count = Integer.parseInt(female_quantity.getText().toString())+ current_female_count;
                    Integer new_total_count = new_female_count+new_male_count;

                    boolean isUpdated = myDb.updateMaleFemaleBreederCount(
                            breeder_tag,
                            new_male_count,
                            new_female_count,
                            new_total_count
                    );

//                    if(isNetworkAvailable()){
//
//                        RequestParams requestParams = new RequestParams();
//                        requestParams.add("breeder_inv_id", breeder_id.toString());
//                        requestParams.add("male", new_male_count.toString());
//                        requestParams.add("female", new_female_count.toString());
//                        requestParams.add("total_", new_total_count.toString());
//
//
//                        API_editBreederInventoryMaleFemale(requestParams);
//                    }

                    if (isUpdated) {
                        Toast.makeText(getActivity(), "Succesfully added to " + breeder_tag, Toast.LENGTH_SHORT).show();
                        Intent intent_line = new Intent(getContext(), CreateBreeders.class);
                        startActivity(intent_line);
                    }

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

    private void API_editBreederInventoryMaleFemale(RequestParams requestParams){
        APIHelper.editBreederInventoryMaleFemale("editBreederInventoryMaleFemale", requestParams, new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response){
                Toast.makeText(context, "Successfully edited breeder male and female", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonResponse, Object response){

                Toast.makeText(context, "Failed to add breeder to web", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable{
                return null;
            }
        });
    }

}