package com.example.cholomanglicmot.nativechickenandduck.ReplacementsDirectory;

import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cholomanglicmot.nativechickenandduck.APIHelper;
import com.example.cholomanglicmot.nativechickenandduck.BreedersDirectory.Breeder_PhenoMorphoView;
import com.example.cholomanglicmot.nativechickenandduck.DatabaseHelper;
import com.example.cholomanglicmot.nativechickenandduck.R;
import com.google.gson.Gson;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class ReplacementPhenoMorphoRecordsActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.Adapter recycler_adapter;
    RecyclerView.LayoutManager layoutManager;
    private TextView text_pen;
    DatabaseHelper myDb;
    private Toolbar mToolbar;
    ArrayList<Replacement_Inventory> arrayListReplacementInventory = new ArrayList<>();
    ArrayList<Replacement_Inventory> arrayList_temp = new ArrayList<>();
    Integer replacement_pen_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_replacement_pheno_morpho_records);

        String replacement_pen;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                replacement_pen= null;
            } else {
                replacement_pen= extras.getString("Replacement Pen");
            }
        } else {
            replacement_pen= (String) savedInstanceState.getSerializable("Replacement Pen");
        }

        text_pen = findViewById(R.id.pheno_title);
        text_pen.setText("Phenotypic & Morphometric Data | Pen "+replacement_pen);

        myDb = new DatabaseHelper(this);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerViewReplacementInventory);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        mToolbar = findViewById(R.id.nav_action);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Replacement Inventory");
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        ///////////////////////////////DATABASE

        replacement_pen_id = myDb.getPenIDWithNumber(replacement_pen);

        Cursor cursor_replacement_inv = myDb.getAllDataFromReplacementInventoryWherePenID(replacement_pen_id);
        cursor_replacement_inv.moveToFirst();

        if(cursor_replacement_inv.getCount() == 0){
            Toast.makeText(this, "No data inventories.", Toast.LENGTH_LONG).show();
        }else {

            do {
                Replacement_Inventory replacement_inventory = new Replacement_Inventory(
                        cursor_replacement_inv.getInt(0),
                        cursor_replacement_inv.getInt(1),
                        cursor_replacement_inv.getInt(2),
                        cursor_replacement_inv.getString(3),
                        cursor_replacement_inv.getString(4),
                        cursor_replacement_inv.getInt(5),
                        cursor_replacement_inv.getInt(6),
                        cursor_replacement_inv.getInt(7),
                        cursor_replacement_inv.getString(8),
                        cursor_replacement_inv.getString(9)
                );

                if (replacement_inventory.getReplacement_inv_deleted_at() == null)
                    arrayListReplacementInventory.add(replacement_inventory);

            } while (cursor_replacement_inv.moveToNext());

        }

        recycler_adapter = new RecyclerAdapter_Replacement_PhenoMorpho(arrayListReplacementInventory);
        recyclerView.setAdapter(recycler_adapter);
        recycler_adapter.notifyDataSetChanged();
    }

    private void API_addPhenoMorphos(RequestParams requestParams){
        APIHelper.addPhenoMorphos("addPhenoMorphos", requestParams, new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response){
                //    Toast.makeText(getContext(), "Successfully added to web", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonResponse, Object response){

               // Toast.makeText(getContext(), "Failed to add to web", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable{
                return null;
            }
        });
    }

    private void API_updatePhenoMorphos(){
        APIHelper.getPhenoMorphos("getPhenoMorphos/", new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response){

                Gson gson = new Gson();
                JSONPhenoMorphos jsonBrooderInventory = gson.fromJson(rawJsonResponse, JSONPhenoMorphos.class);
                ArrayList<Pheno_Morphos> arrayListBrooderFeedingWeb = jsonBrooderInventory.getData();


                ArrayList<Pheno_Morphos> arrayListBrooderFeedingLocal = new ArrayList<>();

                Cursor cursor_brooder_feeding = myDb.getAllDataFromPhenoMorphos();
                cursor_brooder_feeding.moveToFirst();
                if(cursor_brooder_feeding.getCount() != 0){
                    do {

                        Pheno_Morphos replacement_phenoMorphoView = new Pheno_Morphos(cursor_brooder_feeding.getInt(0), cursor_brooder_feeding.getInt(1), cursor_brooder_feeding.getInt(2), cursor_brooder_feeding.getInt(3), cursor_brooder_feeding.getString(4));
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

                    Cursor cursor = myDb.getAllDataFromPhenoMorphosWhereID(id_to_sync.get(i));
                    cursor.moveToFirst();
                    Integer id = cursor.getInt(0);
                    Integer replacement_inventory_id = cursor.getInt(1);
                    Integer breeder_inventory_id = cursor.getInt(2);
                    Integer values_id = cursor.getInt(3);
                    String deleted_at = cursor.getString(4);


                    RequestParams requestParams = new RequestParams();
                    requestParams.add("id", id.toString());
                    requestParams.add("replacement_inventory_id", replacement_inventory_id.toString());
                    requestParams.add("breeder_inventory_id", breeder_inventory_id.toString());
                    requestParams.add("values_id", values_id.toString());
                    requestParams.add("deleted_at", deleted_at);

                    //Toast.makeText(BrooderFeedingRecordsActivity.this, id_to_sync.get(i).toString(), Toast.LENGTH_SHORT).show();

                    API_addPhenoMorphos(requestParams);

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

                //Toast.makeText(getApplicationContext(), "Failed to fetch Brooders Inventory from web database ", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable{
                return null;
            }
        });
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) this.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
