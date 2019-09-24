package com.example.cholomanglicmot.nativechickenandduck.BreedersDirectory;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cholomanglicmot.nativechickenandduck.APIHelper;
import com.example.cholomanglicmot.nativechickenandduck.DatabaseHelper;
import com.example.cholomanglicmot.nativechickenandduck.R;
import com.example.cholomanglicmot.nativechickenandduck.ReplacementsDirectory.JSONPhenoMorphoValues;
import com.example.cholomanglicmot.nativechickenandduck.ReplacementsDirectory.Replacement_PhenoMorphoRecords;
import com.google.gson.Gson;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class BreederPhenoMorphoRecordsActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.Adapter recycler_adapter;
    RecyclerView.LayoutManager layoutManager;
    private TextView text_pen;
    DatabaseHelper myDb;
    private Toolbar mToolbar;
    ArrayList<Breeder_Inventory> arrayListBreederInventory = new ArrayList<>();
    ArrayList<Breeder_PhenoMorphoRecords>arrayList_temp = new ArrayList<>();
    Integer replacement_pen;
    String replacement_pen_number=null;
    String breeder_tag;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_replacement_pheno_morpho_records);
        myDb = new DatabaseHelper(getApplicationContext());

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                replacement_pen= null;
                breeder_tag = null;
            } else {
                replacement_pen= extras.getInt("Replacement Pen");
                breeder_tag = extras.getString("Breeder Tag");
            }
        } else {
            replacement_pen= (Integer) savedInstanceState.getSerializable("Replacement Pen");
            breeder_tag = (String) savedInstanceState.getSerializable("Breeder Tag");
        }

        Cursor cursor = myDb.getAllDataFromPenWhereID(replacement_pen);
        cursor.moveToFirst();
        if(cursor.getCount() != 0){
            replacement_pen_number = cursor.getString(2);
        }

        cursor.close();
        text_pen = findViewById(R.id.pheno_title);
        text_pen.setText("Phenotypic & Morphometric Data | Pen "+replacement_pen_number);

        myDb = new DatabaseHelper(this);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerViewReplacementInventory);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        mToolbar = findViewById(R.id.nav_action);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Breeder Phenotypic & Morphometric Data");
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        local_getPhenoMorphoValues();

        recycler_adapter = new RecyclerAdapter_Breeder_PhenoMorpho(arrayListBreederInventory);
        recyclerView.setAdapter(recycler_adapter);
        recycler_adapter.notifyDataSetChanged();

    }

    private void local_getPhenoMorphoValues() {

        Cursor cursor_inventory = myDb.getDataFromBreederInvWhereTag(breeder_tag);
        cursor_inventory.moveToFirst();

        if (cursor_inventory.getCount() != 0) {

            do {
                Breeder_Inventory breeder_inventory = new Breeder_Inventory(
                        cursor_inventory.getInt(0),
                        cursor_inventory.getInt(1),
                        cursor_inventory.getInt(2),
                        cursor_inventory.getString(3),
                        cursor_inventory.getString(4),
                        cursor_inventory.getInt(5),
                        cursor_inventory.getInt(6),
                        cursor_inventory.getInt(7),
                        cursor_inventory.getString(8),
                        cursor_inventory.getString(9),
                        cursor_inventory.getString(10),
                        cursor_inventory.getString(11),
                        cursor_inventory.getString(12)
                );

                arrayListBreederInventory.add(breeder_inventory);
            } while (cursor_inventory.moveToNext());

        }

        cursor_inventory.close();

    }


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) this.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void API_addPhenoMorphoValues(RequestParams requestParams){
        APIHelper.addPhenoMorphoValues("addPhenoMorphoValues", requestParams, new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response){
//                Toast.makeText(getContext(), "Successfully added to web", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonResponse, Object response){

                //Toast.makeText(getContext(), "Failed to add to web", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable{
                return null;
            }
        });
    }

    private void API_updatePhenoMorphoValues(){
        APIHelper.getPhenoMorphoValues("getPhenoMorphoValues/", new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response){

                Gson gson = new Gson();
                JSONPhenoMorphoValues jsonBrooderInventory = gson.fromJson(rawJsonResponse, JSONPhenoMorphoValues.class);
                ArrayList<Breeder_PhenoMorphoView> arrayListBrooderFeedingWeb = jsonBrooderInventory.getData();


                ArrayList<Breeder_PhenoMorphoView> arrayListBrooderFeedingLocal = new ArrayList<>();

                Cursor cursor_brooder_feeding = myDb.getAllDataFromPhenoMorphoRecords();
                cursor_brooder_feeding.moveToFirst();
                if(cursor_brooder_feeding.getCount() != 0){
                    do {

                        Breeder_PhenoMorphoView replacement_phenoMorphoView = new Breeder_PhenoMorphoView(cursor_brooder_feeding.getInt(0), cursor_brooder_feeding.getString(1), cursor_brooder_feeding.getString(2),cursor_brooder_feeding.getString(3),cursor_brooder_feeding.getString(4),cursor_brooder_feeding.getString(5),cursor_brooder_feeding.getString(6) );
                        arrayListBrooderFeedingLocal.add(replacement_phenoMorphoView);

                    } while (cursor_brooder_feeding.moveToNext());
                }



                //arrayListBrooderInventoryLocal contains all data from local database
                //arrayListBrooderInventoryWeb   contains all data from web database

                //put the ID of each brooder inventory to another arraylist
                ArrayList<Integer> id_local = new ArrayList<>();
                ArrayList<Integer> id_web = new ArrayList<>();
                ArrayList<Integer> id_to_sync = new ArrayList<>();

                for(int i=0;i<arrayListBrooderFeedingLocal.size();i++){
                    id_local.add(arrayListBrooderFeedingLocal.get(i).getId());
                }
                for(int i=0;i<arrayListBrooderFeedingWeb.size();i++){
                    id_web.add(arrayListBrooderFeedingWeb.get(i).getId());
                }


                for (int i=0;i<id_local.size();i++){
                    if(!id_web.contains(id_local.get(i))){ //if id_web does not contain the current value of i, add it the an arraylist
                        id_to_sync.add(id_local.get(i));
                    }
                }


                for(int i=0;i<id_to_sync.size();i++){

                    Cursor cursor = myDb.getAllDataFromPhenoMorphoRecordsWhereID(id_to_sync.get(i));
                    cursor.moveToFirst();
                    Integer id = cursor.getInt(0);
                    String gender = cursor.getString(1);
                    String tag = cursor.getString(2);
                    String phenotypic = cursor.getString(3);
                    String morphometric = cursor.getString(4);
                    String date_collected = cursor.getString(5);
                    String deleted_at = cursor.getString(6);


                    RequestParams requestParams = new RequestParams();
                    requestParams.add("id", id.toString());
                    requestParams.add("gender", gender);
                    requestParams.add("tag", tag);
                    requestParams.add("phenotypic", phenotypic);
                    requestParams.add("morphometric", morphometric);
                    requestParams.add("date_collected", date_collected);
                    requestParams.add("deleted_at", deleted_at);

                    //Toast.makeText(BrooderFeedingRecordsActivity.this, id_to_sync.get(i).toString(), Toast.LENGTH_SHORT).show();

                    API_addPhenoMorphoValues(requestParams);

                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonResponse, Object response){

                Toast.makeText(getApplicationContext(), "Failed to fetch Brooders Inventory from web database ", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable{
                return null;
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        Intent intent_brooders = new Intent(BreederPhenoMorphoRecordsActivity.this, CreateBreeders.class);

        startActivity(intent_brooders);
        return true;
    }

    @Override
    public void onBackPressed() {


        Intent intent_brooders = new Intent(BreederPhenoMorphoRecordsActivity.this, CreateBreeders.class);

        startActivity(intent_brooders);

    }

}
