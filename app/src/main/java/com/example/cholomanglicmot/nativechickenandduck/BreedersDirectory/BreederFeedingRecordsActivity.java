package com.example.cholomanglicmot.nativechickenandduck.BreedersDirectory;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cholomanglicmot.nativechickenandduck.APIHelper;
import com.example.cholomanglicmot.nativechickenandduck.DatabaseHelper;
import com.example.cholomanglicmot.nativechickenandduck.R;
import com.google.gson.Gson;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cz.msebera.android.httpclient.Header;


public class BreederFeedingRecordsActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private TextView brooder_pen_textView;


    DatabaseHelper myDb;
    RecyclerView recyclerView;
    RecyclerView.Adapter recycler_adapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Breeder_FeedingRecords> arrayListBrooderFeedingRecords = new ArrayList<>();//create constructor first for brooder feeding records
    ArrayList<Breeder_Inventory>arrayListBrooderInventory = new ArrayList<>();
    ArrayList<Breeder_Inventory>arrayList_temp = new ArrayList<>();
    FloatingActionButton create_brooder_feeding_records;
    Integer brooder_pen,breeder_inv_id;
    String breeder_tag;


    Map<Integer, Integer> inventory_dictionary = new HashMap<Integer, Integer>();
    ArrayList<Integer> list = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breeder_feeding_records);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                brooder_pen= null;
                breeder_tag = null;
                breeder_inv_id = null;
            } else {
                brooder_pen= extras.getInt("Breeder Pen");
                breeder_tag= extras.getString("Breeder Tag");
                breeder_inv_id= extras.getInt("Breeder Inventory ID");

            }
        } else {
            brooder_pen= (Integer) savedInstanceState.getSerializable("Breeder Pen");
            breeder_tag= (String) savedInstanceState.getSerializable("Breeder Tag");
            breeder_inv_id= (Integer) savedInstanceState.getSerializable("Breeder Inventory ID");
        }

        final Bundle args = new Bundle();
        args.putInt("breeder pen number",brooder_pen);
        args.putString("Breeder Tag", breeder_tag);
        brooder_pen_textView = findViewById(R.id.brooder_pen);
        brooder_pen_textView.setText("Breeder Tag | " +breeder_tag);
        mToolbar = findViewById(R.id.nav_action);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Breeder Feeding Records");
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        create_brooder_feeding_records = findViewById(R.id.open_dialog);

        create_brooder_feeding_records.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateBreederFeedingRecordDialog newFragment = new CreateBreederFeedingRecordDialog();
                newFragment.setArguments(args);
                newFragment.show(getSupportFragmentManager(), "CreateBreederFeedingRecordDialog");
            }
        });

        myDb = new DatabaseHelper(this);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerViewBrooderFeedingRecords);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                if (dy < 0) {
                    create_brooder_feeding_records.show();

                } else if (dy > 0) {
                    create_brooder_feeding_records.hide();
                }
            }
        });
        ///////////////////////////////DATABASE
        if(isNetworkAvailable()){
            API_getBreederFeeding();
        }

        String breeder_pen_number = null;
        Cursor cursor_pen = myDb.getAllDataFromPenWhereID(brooder_pen);
        cursor_pen.moveToFirst();
        if(cursor_pen.getCount() != 0 ){
            breeder_pen_number = cursor_pen.getString(2);
        }


        Cursor cur = myDb.getDataFromBreederInvWhereTag(breeder_tag);
        cur.moveToFirst();
        Integer bred = cur.getInt(0);
        cur.close();
        ArrayList<Breeder_FeedingRecords> arrayList_breeder_feeding = new ArrayList<>();
        Cursor cursor_feeding = myDb.getAllDataFromBreederFeedingRecords();
        cursor_feeding.moveToFirst();

        if(cursor_feeding.getCount() != 0){
            do{
                String deleted_at = cursor_feeding.getString(6);
                Integer breeder_inv_id1 = cursor_feeding.getInt(1);
                if(breeder_inv_id1 == bred && deleted_at == null){
                    Breeder_FeedingRecords breeder_feedingRecords = new Breeder_FeedingRecords(cursor_feeding.getInt(0), cursor_feeding.getInt(1), cursor_feeding.getString(2),breeder_tag,cursor_feeding.getFloat(3), cursor_feeding.getFloat(4),cursor_feeding.getString(5), cursor_feeding.getString(6)) ;
                    arrayList_breeder_feeding.add(breeder_feedingRecords);
                }
            }while(cursor_feeding.moveToNext());
        }
        cursor_feeding.close();
        recycler_adapter = new RecyclerAdapter_Breeder_Feeding(arrayList_breeder_feeding);
        recyclerView.setAdapter(recycler_adapter);
        recycler_adapter.notifyDataSetChanged();



    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    private void API_addBreederFeeding(RequestParams requestParams){

        APIHelper.addBreederFeeding("addBreederFeeding", requestParams, new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response){
                Toast.makeText(getApplicationContext(), "Successfully synced breeder feeding record to web", Toast.LENGTH_SHORT).show();
                //isSend = true;

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonResponse, Object response){

                // Toast.makeText(getActivity(), "Failed to add brooder feeding record to web", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable{
                return null;
            }
        });

    }
    private void API_updateBreederFeeding(){
        APIHelper.getBreederFeeding("getBreederFeeding/", new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response){

                Gson gson = new Gson();
                JSONBreederFeeding jsonBrooderInventory = gson.fromJson(rawJsonResponse, JSONBreederFeeding.class);
                ArrayList<Breeder_FeedingRecords> arrayListBrooderFeedingWeb = jsonBrooderInventory.getData();


                ArrayList<Breeder_FeedingRecords> arrayListBrooderFeedingLocal = new ArrayList<>();

                Cursor cursor_brooder_feeding = myDb.getAllDataFromBreederFeedingRecords();
                cursor_brooder_feeding.moveToFirst();
                if(cursor_brooder_feeding.getCount() != 0){
                    do {

                        Breeder_FeedingRecords brooderFeedingRecords = new Breeder_FeedingRecords(cursor_brooder_feeding.getInt(0),cursor_brooder_feeding.getInt(1), cursor_brooder_feeding.getString(2), null,cursor_brooder_feeding.getFloat(3),cursor_brooder_feeding.getFloat(4), cursor_brooder_feeding.getString(5), cursor_brooder_feeding.getString(6));
                        arrayListBrooderFeedingLocal.add(brooderFeedingRecords);

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

                    Cursor cursor = myDb.getAllDataFromBreederFeedingRecordsWhereFeedingID(id_to_sync.get(i));
                    cursor.moveToFirst();
                    Integer id = cursor.getInt(0);
                    Integer broodergrower_inventory_id = cursor.getInt(1);
                    String date_collected = cursor.getString(2);
                    Float amount_offered = cursor.getFloat(3);
                    Float amount_refused = cursor.getFloat(4);
                    String remarks = cursor.getString(5);
                    String deleted_at = cursor.getString(6);

                    RequestParams requestParams = new RequestParams();
                    requestParams.add("id", id.toString());
                    requestParams.add("broodergrower_inventory_id", broodergrower_inventory_id.toString());
                    requestParams.add("date_collected", date_collected);
                    requestParams.add("amount_offered", amount_offered.toString());
                    requestParams.add("amount_refused", amount_refused.toString());
                    requestParams.add("remarks", remarks);
                    requestParams.add("deleted_at", deleted_at);


                    //Toast.makeText(BrooderFeedingRecordsActivity.this, id_to_sync.get(i).toString(), Toast.LENGTH_SHORT).show();

                    API_addBreederFeeding(requestParams);



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
    private void API_getBreederFeeding(){
        APIHelper.getBreederFeeding("getBreederFeeding/", new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response){

                Gson gson = new Gson();
                JSONBreederFeeding jsonBreederInventory = gson.fromJson(rawJsonResponse, JSONBreederFeeding.class);
                ArrayList<Breeder_FeedingRecords> arrayList_brooderInventory = jsonBreederInventory.getData();


                for (int i = 0; i < arrayList_brooderInventory.size(); i++) {
                    //check if generation to be inserted is already in the database
                    Cursor cursor = myDb.getAllDataFromBreederFeedingRecordsWhereFeedingID(arrayList_brooderInventory.get(i).getId());
                    cursor.moveToFirst();

                    if (cursor.getCount() == 0) {


                        boolean isInserted = myDb.insertDataBreederFeedingRecordsWithID(arrayList_brooderInventory.get(i).getId(), arrayList_brooderInventory.get(i).getBrooder_feeding_inventory_id(), arrayList_brooderInventory.get(i).getBrooder_feeding_date_collected(), arrayList_brooderInventory.get(i).getBrooder_feeding_offered(),arrayList_brooderInventory.get(i).getBrooder_feeding_refused(),arrayList_brooderInventory.get(i).getBrooder_feeding_remarks(),arrayList_brooderInventory.get(i).getBrooder_feeding_deleted_at());

                    }

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
    public void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
    @Override
    public boolean onSupportNavigateUp() {
        Intent intent_brooders = new Intent(BreederFeedingRecordsActivity.this, CreateBreeders.class);
        startActivity(intent_brooders);
        return true;
    }

    @Override
    public void onBackPressed() {


        Intent intent_brooders = new Intent(BreederFeedingRecordsActivity.this, CreateBreeders.class);
        startActivity(intent_brooders);

    }


}
