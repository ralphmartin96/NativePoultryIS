package com.example.cholomanglicmot.nativechickenandduck.BroodersDirectory;

import android.content.Context;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.cholomanglicmot.nativechickenandduck.APIHelper;
import com.example.cholomanglicmot.nativechickenandduck.DatabaseHelper;
import com.example.cholomanglicmot.nativechickenandduck.R;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class ViewBrooderInventoryDialog extends DialogFragment {

    DatabaseHelper myDb;
    TextView textView, family_number, line_number, generation_number, brooder_male_count, brooder_female_count, brooder_total, brooder_date_added,batching_date ;
    EditText edit_male_count, edit_female_count;
    Button update, save;
    List<String> famLineGen = new ArrayList<>();
    Context context;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_view_brooder_inventory, container, false);

        final String brooder_tag = getArguments().getString("Brooder Tag");
        final Integer brooder_pen = getArguments().getInt("Brooder Pen ID");
        final Integer brooder_id = getArguments().getInt("Brooder ID");
        context = getActivity();

        myDb = new DatabaseHelper(getContext());

        Integer fam_id = myDb.getFamIDFromBrooders(brooder_id);

        String famLineGen = myDb.getFamLineGen(fam_id);
        String delims = " ";
        try{
            String[] tokens = famLineGen.split(delims);
            String fam = tokens[0];
            String line = tokens[1];
            String gen = tokens[2];
            family_number.setText(fam);
            line_number.setText(line);
            generation_number.setText(gen);
        }catch (Exception e){}


        textView = view.findViewById(R.id.textView);
        family_number = view.findViewById(R.id.family_number); //galing sa brooder table
        line_number = view.findViewById(R.id.line_number);//galing sa brooder table
        generation_number = view.findViewById(R.id.generation_number);//galing sa brooder table
        batching_date = view.findViewById(R.id.batching_date);
        brooder_male_count = view.findViewById(R.id.brooder_male_count);
        brooder_female_count = view.findViewById(R.id.brooder_female_count);
        brooder_total = view.findViewById(R.id.brooder_total);
        brooder_date_added = view.findViewById(R.id.brooder_date_added);//galing sa brooder table

        edit_male_count = view.findViewById(R.id.edit_male_count);
        edit_female_count = view.findViewById(R.id.edit_female_count);

        update = view.findViewById(R.id.update);
        save = view.findViewById(R.id.save);

        textView.setText(brooder_tag);

        Cursor cursor = myDb.getDataFromBrooderInventoryWherePenAndID(brooder_tag, brooder_pen);
        cursor.moveToFirst();
        if(cursor.getCount() != 0){
            //textView.setText(cursor.getString(3));
            batching_date.setText(cursor.getString(4));


            brooder_male_count.setText(cursor.getString(5));
            brooder_female_count.setText(cursor.getString(6));
            brooder_total.setText(cursor.getString(7));
            brooder_date_added.setText(cursor.getString(8));

        }

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edit_female_count.getVisibility() == View.GONE){
                    edit_male_count.setVisibility(View.VISIBLE);
                    edit_female_count.setVisibility(View.VISIBLE);
                    brooder_male_count.setVisibility(View.GONE);
                    brooder_female_count.setVisibility(View.GONE);
                    edit_female_count.setText(brooder_female_count.getText().toString());
                    edit_male_count.setText(brooder_male_count.getText().toString());
                    update.setText("CANCEL");

                }else{
                    edit_male_count.setVisibility(View.GONE);
                    edit_female_count.setVisibility(View.GONE);
                    brooder_male_count.setVisibility(View.VISIBLE);
                    brooder_female_count.setVisibility(View.VISIBLE);
                    edit_female_count.setText(brooder_female_count.getText().toString());
                    edit_male_count.setText(brooder_male_count.getText().toString());
                    update.setText("UPDATE");

                }

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edit_female_count.getVisibility() == View.VISIBLE){
                    boolean isUpdated = myDb.updateMaleFemaleCount(brooder_tag, Integer.parseInt(edit_male_count.getText().toString()),Integer.parseInt(edit_female_count.getText().toString()) );
                    Cursor cursor_tag = myDb.getDataFromBrooderInventoryWhereTag(brooder_tag);
                    cursor_tag.moveToFirst();
                    Integer brooder_id = cursor_tag.getInt(0);
                    if(isNetworkAvailable()){

                        RequestParams requestParams = new RequestParams();
                        requestParams.add("brooder_inv_id", brooder_id.toString());
                        requestParams.add("male", edit_male_count.getText().toString());
                        requestParams.add("female", edit_female_count.getText().toString());


                        API_editBrooderInventoryMaleFemale(requestParams);
                    }
                    if(isUpdated == true){
                        Toast.makeText(getContext(), "Updated male and female count", Toast.LENGTH_SHORT).show();
                        getDialog().dismiss();
                    }else{
                        Toast.makeText(getContext(), "Error updating male and female count", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    getDialog().dismiss();
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
    private void API_editBrooderInventoryMaleFemale(RequestParams requestParams){
        APIHelper.editBrooderInventoryMaleFemale("editBrooderInventoryMaleFemale", requestParams, new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response){
                Toast.makeText(context, "Successfully edited brooder male and female", Toast.LENGTH_SHORT).show();
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
}