package com.example.cholomanglicmot.nativechickenandduck.ReplacementsDirectory;

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

import com.example.cholomanglicmot.nativechickenandduck.APIHelperAsync;
import com.example.cholomanglicmot.nativechickenandduck.DatabaseHelper;
import com.example.cholomanglicmot.nativechickenandduck.R;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class ViewReplacementInventoryDialog extends DialogFragment {

    DatabaseHelper myDb;
    TextView textView, family_number, line_number, generation_number, brooder_male_count, brooder_female_count, brooder_total, brooder_date_added,batching_date;
    EditText edit_male_count, edit_female_count;
    Button update, save;
    List<String> famLineGen = new ArrayList<>();
    Context context;

    Integer fam_id = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_view_replacement_inventory, container, false);
        final String brooder_tag = getArguments().getString("Replacement Tag");
        final Integer brooder_pen = getArguments().getInt("Replacement Pen");
        final Integer brooder_id = getArguments().getInt("Replacement ID");
        Integer replacement_pen_id =null;
        myDb = new DatabaseHelper(getContext());
        context = getActivity();
        Integer fam_id = myDb.getFamIDFromReplacements(brooder_id);
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
        edit_female_count = view.findViewById(R.id.edit_female_count);
        edit_male_count = view.findViewById(R.id.edit_male_count);
        family_number = view.findViewById(R.id.family_number); //galing sa brooder table
        line_number = view.findViewById(R.id.line_number);//galing sa brooder table
        generation_number = view.findViewById(R.id.generation_number);//galing sa brooder table
        batching_date = view.findViewById(R.id.batching_date);
        brooder_male_count = view.findViewById(R.id.brooder_male_count);
        brooder_female_count = view.findViewById(R.id.brooder_female_count);
        brooder_total = view.findViewById(R.id.brooder_total);
        brooder_date_added = view.findViewById(R.id.brooder_date_added);//galing sa brooder table


        update = view.findViewById(R.id.update);
        save = view.findViewById(R.id.save);

        textView.setText(brooder_tag);


        //hanapin mo yung pen id ng brooder tag



        Cursor cursor = myDb.getDataFromReplacementInventoryWherePenAndID(brooder_tag, brooder_pen);
        cursor.moveToFirst();
        if(cursor.getCount() != 0){

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
                    boolean isUpdated = myDb.updateMaleFemaleReplacementCount(brooder_tag, Integer.parseInt(edit_male_count.getText().toString()),Integer.parseInt(edit_female_count.getText().toString()) );
                    Cursor cursor_tag = myDb.getDataFromReplacementInventoryWhereTag(brooder_tag);
                    cursor_tag.moveToFirst();
                    Integer replacement_id = cursor_tag.getInt(0);
                    if(isNetworkAvailable()){

                        RequestParams requestParams = new RequestParams();
                        requestParams.add("replacement_inv_id", replacement_id.toString());
                        requestParams.add("male", edit_male_count.getText().toString());
                        requestParams.add("female", edit_female_count.getText().toString());


                        API_editReplacementInventoryMaleFemale(requestParams);
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
    private void API_editReplacementInventoryMaleFemale(RequestParams requestParams){
        APIHelperAsync.editReplacementInventoryMaleFemale("editReplacementInventoryMaleFemale", requestParams, new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response){
                Toast.makeText(context, "Successfully edited replacement male and female", Toast.LENGTH_SHORT).show();
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