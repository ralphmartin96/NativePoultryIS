package com.example.cholomanglicmot.nativechickenandduck.GenerationsAndLinesDirectory;

import android.content.Context;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cz.msebera.android.httpclient.Header;

//TODO: localize db for cull
public class CullGenerationDialog extends DialogFragment {

    DatabaseHelper myDb;
    TextView textView, family_number, line_number, generation_number, brooder_male_count, brooder_female_count, brooder_total, brooder_date_added,batching_date;
    EditText edit_male_count, edit_female_count;
    Button no, yes;
    List<String> famLineGen = new ArrayList<>();
    Context context;
    Integer  pen_capacity, total;
    String pen_number;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_cull_brooder_inventory, container, false);

        final String generation_number = getArguments().getString("Generation Number");
        final Integer generation_id = getArguments().getInt("Generation ID");
        //Toast.makeText(getActivity(), generation_id.toString(), Toast.LENGTH_SHORT).show();
        context = getActivity();

        myDb = new DatabaseHelper(getContext());


        textView = view.findViewById(R.id.textView);


        no = view.findViewById(R.id.no);
        yes = view.findViewById(R.id.yes);

        textView.setText("Cull Generation "+generation_number+"?");

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getDialog().dismiss();

            }
        });

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean isGenerationCulled = myDb.cullGeneration(generation_id);

                if(isNetworkAvailable()){

                    API_cullGeneration(generation_id);
                }

                if(isGenerationCulled){
                    Intent intent_line = new Intent(getActivity(), CreateGenerationsAndLines.class);
                    startActivity(intent_line);
                }else{
                    Toast.makeText(getActivity(), "Failed to cull generation", Toast.LENGTH_SHORT).show();
                }


            }
        });


        return view;
    }

    private String getDate() {

        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());

        return formatter.format(date);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void API_cullGeneration(Integer generation_id){
        APIHelper.getGeneration("cullGeneration/"+generation_id, new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonResponse, Object response){

                // Toast.makeText(getApplicationContext(), "Failed ", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable{
                return null;
            }
        });
    }
}