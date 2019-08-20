package com.example.cholomanglicmot.nativechickenandduck.BroodersDirectory;

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


public class BrooderGrowthRecordsActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private TextView brooder_pen_textView;


    DatabaseHelper myDb;
    RecyclerView recyclerView;
    RecyclerView.Adapter recycler_adapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Brooder_GrowthRecords> arrayListBrooderGrowthRecords = new ArrayList<>();
    ArrayList<Brooder_Inventory>arrayListBrooderInventory = new ArrayList<>();
    ArrayList<Brooder_Inventory>arrayList_temp = new ArrayList<>();
    FloatingActionButton create_brooder_feeding_records;
    Integer brooder_pen_id;

    Map<Integer, Integer> inventory_dictionary = new HashMap<Integer, Integer>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brooder_growth_records);
        final String brooder_pen;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                brooder_pen= null;
            } else {
                brooder_pen= extras.getString("Brooder Pen");
            }
        } else {
            brooder_pen= (String) savedInstanceState.getSerializable("Brooder Pen");
        }

        final Bundle args = new Bundle();
        args.putString("Brooder Pen",brooder_pen);
        brooder_pen_textView = findViewById(R.id.brooder_pen);
        brooder_pen_textView.setText("Brooder Pen " +brooder_pen);
        mToolbar = findViewById(R.id.nav_action);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Brooder Growth Records");
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        create_brooder_feeding_records = findViewById(R.id.open_dialog);

        create_brooder_feeding_records.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateBrooderGrowthRecordDialog newFragment = new CreateBrooderGrowthRecordDialog();
                newFragment.setArguments(args);
                newFragment.show(getSupportFragmentManager(), "CreateBrooderGrowthRecordDialog");
            }
        });

        myDb = new DatabaseHelper(this);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerViewBrooderFeedingRecords);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        ///////////////////////////////DATABASE
        Cursor cursor1 = myDb.getAllDataFromPenWhere(brooder_pen);
        cursor1.moveToFirst();
        if(cursor1.getCount() != 0){
            brooder_pen_id = cursor1.getInt(0);
        }

        boolean isNetworkAvailable = isNetworkAvailable();
        if (isNetworkAvailable) {
            //if internet is available, load data from web database


            //HARDCODED KASI WALA KA PANG DATABASE NA NANDUN EMAIL MO
            API_updateBrooderGrowth();
            API_getBrooderGrowth();


        }



                ////inventory
       /* Cursor cursor_brooder_inventory = myDb.getAllDataFromBrooderInventory(); //para sa pagstore ng data sa arraylist
        cursor_brooder_inventory.moveToFirst();
        if(cursor_brooder_inventory.getCount() == 0){
            //show message
            Toast.makeText(this,"No data.", Toast.LENGTH_LONG).show();

        }else {
            do {

                Brooder_Inventory brooder_inventory = new Brooder_Inventory(cursor_brooder_inventory.getInt(0),cursor_brooder_inventory.getInt(1), cursor_brooder_inventory.getInt(2), cursor_brooder_inventory.getString(3),cursor_brooder_inventory.getString(4), cursor_brooder_inventory.getInt(5), cursor_brooder_inventory.getInt(6),cursor_brooder_inventory.getInt(7), cursor_brooder_inventory.getString(8), cursor_brooder_inventory.getString(9));
                arrayListBrooderInventory.add(brooder_inventory);


            } while (cursor_brooder_inventory.moveToNext());
        }



        for (int i=0;i<arrayListBrooderInventory.size();i++){
            if(arrayListBrooderInventory.get(i).getBrooder_inv_pen() == brooder_pen_id) {

                arrayList_temp.add(arrayListBrooderInventory.get(i)); //ito na yung list ng inventory na nasa pen

            }
        }




        ////growth records

        Cursor cursor_brooder_growth_records = myDb.getAllDataFromBrooderGrowthRecords();
        cursor_brooder_growth_records.moveToFirst();

        if(cursor_brooder_growth_records.getCount() == 0){
            //show message
            Toast.makeText(this,"No data.", Toast.LENGTH_LONG).show();

        }else {

            do {
                String deleted_at = cursor_brooder_growth_records.getString(10);
                //                                                                        Integer id,                 Integer brooder_growth_inventory_id,String , Integer brooder_growth_collection_day,      String brooder_growth_date_collected,       Integer brooder_growth_male_quantity,           Float brooder_growth_male_weight,                  Integer brooder_growth_female_quantity, Float brooder_growth_female_weight,          Integer brooder_growth_total_quantity,      Float brooder_growth_total_weight,              String brooder_growth_deleted_at){
                for(int k=0;k<arrayList_temp.size();k++){
                    if(arrayList_temp.get(k).getBrooder_inv_brooder_id() == cursor_brooder_growth_records.getInt(1) && deleted_at == null){
                        Brooder_GrowthRecords brooderGrowthRecords = new Brooder_GrowthRecords(cursor_brooder_growth_records.getInt(0),cursor_brooder_growth_records.getInt(1),arrayList_temp.get(k).getBrooder_inv_brooder_tag(),cursor_brooder_growth_records.getInt(2), cursor_brooder_growth_records.getString(3),cursor_brooder_growth_records.getInt(4), cursor_brooder_growth_records.getFloat(5), cursor_brooder_growth_records.getInt(6), cursor_brooder_growth_records.getFloat(7),cursor_brooder_growth_records.getInt(8), cursor_brooder_growth_records.getFloat(9), cursor_brooder_growth_records.getString(10));
                        arrayListBrooderGrowthRecords.add(brooderGrowthRecords);


                    }
                }


            } while (cursor_brooder_growth_records.moveToNext());
        }




                                                                //pass dictionary   total count ng brooders arraylistngfeedingrecords
        recycler_adapter = new RecyclerAdapter_Brooder_Growth(arrayListBrooderGrowthRecords);
        recyclerView.setAdapter(recycler_adapter);
        recycler_adapter.notifyDataSetChanged();
*/
        Cursor cursor_inventory = myDb.getDataFromBrooderInventoryWherePen(brooder_pen_id);
        cursor_inventory.moveToFirst();
        if(cursor_inventory.getCount() == 0){
            //show message
            // Toast.makeText(this,"No data inventories.", Toast.LENGTH_LONG).show();

        }else {
            do {

                Brooder_Inventory brooder_inventory = new Brooder_Inventory(cursor_inventory.getInt(0),cursor_inventory.getInt(1), cursor_inventory.getInt(2), cursor_inventory.getString(3),cursor_inventory.getString(4), cursor_inventory.getInt(5), cursor_inventory.getInt(6),cursor_inventory.getInt(7), cursor_inventory.getString(8), cursor_inventory.getString(9));
                arrayListBrooderInventory.add(brooder_inventory);
            } while (cursor_inventory.moveToNext());
        }




        Cursor cursor_feeding = myDb.getAllDataFromBrooderGrowthRecords();
        cursor_feeding.moveToFirst();
        if(cursor_feeding.getCount() != 0){
            do{

                Brooder_GrowthRecords brooderFeedingRecords =  new Brooder_GrowthRecords(cursor_feeding.getInt(0),cursor_feeding.getInt(1),null,cursor_feeding.getInt(2), cursor_feeding.getString(3),cursor_feeding.getInt(4), cursor_feeding.getFloat(5), cursor_feeding.getInt(6), cursor_feeding.getFloat(7),cursor_feeding.getInt(8), cursor_feeding.getFloat(9), cursor_feeding.getString(10));
                arrayListBrooderGrowthRecords.add(brooderFeedingRecords);


                    /*    }
                    }*/
            }while(cursor_feeding.moveToNext());
        }

        ArrayList<Brooder_GrowthRecords> arrayList_final = new ArrayList<Brooder_GrowthRecords>();

        for(int i=0;i<arrayListBrooderInventory.size();i++){
            for(int k=0;k<arrayListBrooderGrowthRecords.size();k++){
                if(arrayListBrooderInventory.get(i).getId()==arrayListBrooderGrowthRecords.get(k).getBrooder_growth_inventory_id() && arrayListBrooderGrowthRecords.get(k).getBrooder_growth_deleted_at() == null){
                    arrayList_final.add(arrayListBrooderGrowthRecords.get(k));
                }
            }
        }



        recycler_adapter = new RecyclerAdapter_Brooder_Growth(arrayList_final);
        recyclerView.setAdapter(recycler_adapter);
        recycler_adapter.notifyDataSetChanged();

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


    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    private void API_addBrooderGrowth(RequestParams requestParams){

        APIHelper.addBrooderGrowth("addBrooderGrowth", requestParams, new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response){
                Toast.makeText(getApplicationContext(), "Successfully synced brooder growth record to web", Toast.LENGTH_SHORT).show();
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
    private void API_updateBrooderGrowth(){
        APIHelper.getBrooderGrowth("getBrooderGrowth/", new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response){

                Gson gson = new Gson();
                JSONBrooderGrowth jsonBrooderInventory = gson.fromJson(rawJsonResponse, JSONBrooderGrowth.class);
                ArrayList<Brooder_GrowthRecords> arrayListBrooderFeedingWeb = jsonBrooderInventory.getData();


                ArrayList<Brooder_GrowthRecords> arrayListBrooderFeedingLocal = new ArrayList<>();

                Cursor cursor_brooder_feeding = myDb.getAllDataFromBrooderGrowthRecords();
                cursor_brooder_feeding.moveToFirst();
                if(cursor_brooder_feeding.getCount() != 0){
                    do {

                        Brooder_GrowthRecords brooderFeedingRecords =  new Brooder_GrowthRecords(cursor_brooder_feeding.getInt(0),cursor_brooder_feeding.getInt(1),null,cursor_brooder_feeding.getInt(2), cursor_brooder_feeding.getString(3),cursor_brooder_feeding.getInt(4), cursor_brooder_feeding.getFloat(5), cursor_brooder_feeding.getInt(6), cursor_brooder_feeding.getFloat(7),cursor_brooder_feeding.getInt(8), cursor_brooder_feeding.getFloat(9), cursor_brooder_feeding.getString(10));
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

                    Cursor cursor = myDb.getAllDataFromBrooderGrowthRecordsWhereGrowthID(id_to_sync.get(i));
                    cursor.moveToFirst();
                    Integer id = cursor.getInt(0);
                    Integer broodergrower_inventory_id = cursor.getInt(1);
                    Integer collection_day = cursor.getInt(2);
                    String date_collected = cursor.getString(3);
                    Integer male_quantity = cursor.getInt(4);
                    Float male_weight = cursor.getFloat(5);
                    Integer female_quantity = cursor.getInt(6);
                    Float female_weight = cursor.getFloat(7);
                    Integer total = cursor.getInt(8);
                    Float total_weight = cursor.getFloat(9);
                    String deleted_at = cursor.getString(10);

                    RequestParams requestParams = new RequestParams();
                    requestParams.add("id", id.toString());
                    requestParams.add("broodergrower_inventory_id", broodergrower_inventory_id.toString());
                    requestParams.add("collection_day", collection_day.toString());
                    requestParams.add("date_collected", date_collected);
                    requestParams.add("male_quantity", male_quantity.toString());
                    requestParams.add("male_weight", male_weight.toString());
                    requestParams.add("female_quantity", female_quantity.toString());
                    requestParams.add("female_weight", female_weight.toString());
                    requestParams.add("total_quantity", total.toString());
                    requestParams.add("total_weight", total_weight.toString());
                    requestParams.add("deleted_at", deleted_at);


                    //Toast.makeText(BrooderFeedingRecordsActivity.this, id_to_sync.get(i).toString(), Toast.LENGTH_SHORT).show();

                    API_addBrooderGrowth(requestParams);



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
     private void API_getBrooderGrowth(){
        APIHelper.getBrooderGrowth("getBrooderGrowth/", new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response){

                Gson gson = new Gson();
                JSONBrooderGrowth jsonBrooderGrowth = gson.fromJson(rawJsonResponse, JSONBrooderGrowth.class);
                ArrayList<Brooder_GrowthRecords> arrayList_brooderInventory = jsonBrooderGrowth.getData();


                for (int i = 0; i < arrayList_brooderInventory.size(); i++) {
                    //check if generation to be inserted is already in the database
                    Cursor cursor = myDb.getAllDataFromBrooderGrowthRecordsWhereGrowthID(arrayList_brooderInventory.get(i).getId());
                    cursor.moveToFirst();

                    if (cursor.getCount() == 0) {


                        boolean isInserted = myDb.insertDataBrooderGrowthRecordsWithID(arrayList_brooderInventory.get(i).getId(), arrayList_brooderInventory.get(i).getBrooder_growth_inventory_id(), arrayList_brooderInventory.get(i).getBrooder_growth_collection_day(), arrayList_brooderInventory.get(i).getBrooder_growth_date_collected(),arrayList_brooderInventory.get(i).getBrooder_growth_male_quantity(),arrayList_brooderInventory.get(i).getBrooder_growth_male_weight(),arrayList_brooderInventory.get(i).getBrooder_growth_female_quantity(),arrayList_brooderInventory.get(i).getBrooder_growth_female_weight(),arrayList_brooderInventory.get(i).getBrooder_growth_total_quantity(),arrayList_brooderInventory.get(i).getBrooder_growth_total_weight(),arrayList_brooderInventory.get(i).getBrooder_growth_deleted_at());
                        //Toast.makeText(BrooderInventoryActivity.this, "oyoyooyoy", Toast.LENGTH_SHORT).show();
                    }

                }


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonResponse, Object response){

                Toast.makeText(getApplicationContext(), "Failed to fetch Brooders Growth Records from web database ", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable{
                return null;
            }
        });
    }
    @Override
    public void onBackPressed() {

        Intent intent_brooders = new Intent(this, CreateBrooders.class);
        startActivity(intent_brooders);

    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    public void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }


}
