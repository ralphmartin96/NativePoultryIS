package com.example.cholomanglicmot.nativechickenandduck.ReplacementsDirectory;

import android.app.AlertDialog;
import android.content.Context;
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

import com.example.cholomanglicmot.nativechickenandduck.APIHelperAsync;
import com.example.cholomanglicmot.nativechickenandduck.DatabaseHelper;
import com.example.cholomanglicmot.nativechickenandduck.R;
import com.google.gson.Gson;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;


public class ReplacementGrowthRecordsActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private TextView brooder_pen_textView;


    DatabaseHelper myDb;
    RecyclerView recyclerView;
    RecyclerView.Adapter recycler_adapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Replacement_GrowthRecords> arrayListReplacementGrowthRecords = new ArrayList<>();
    ArrayList<Replacement_Inventory>arrayListReplacementInventory = new ArrayList<>();
    ArrayList<Replacement_Inventory>arrayList_temp = new ArrayList<>();
    FloatingActionButton create_brooder_feeding_records;
    Integer replacement_pen_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_replacement_growth_records);
        final String replacement_pen;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                replacement_pen = null;
            } else {
                replacement_pen = extras.getString("Replacement Pen");
            }
        } else {
            replacement_pen = (String) savedInstanceState.getSerializable("Replacement Pen");
        }

        final Bundle args = new Bundle();
        args.putString("Replacement Pen", replacement_pen);
        brooder_pen_textView = findViewById(R.id.brooder_pen);
        brooder_pen_textView.setText("Replacement Pen " + replacement_pen);
        mToolbar = findViewById(R.id.nav_action);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Replacement Growth Records");
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        create_brooder_feeding_records = findViewById(R.id.open_dialog);

        create_brooder_feeding_records.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateReplacementGrowthRecordDialog newFragment = new CreateReplacementGrowthRecordDialog();
                newFragment.setArguments(args);
                newFragment.show(getSupportFragmentManager(), "CreateReplacementGrowthRecordDialog");
            }
        });

        myDb = new DatabaseHelper(this);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewReplacementGrowthRecords);
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

        replacement_pen_id = myDb.getPenIDWithNumber(replacement_pen);

        local_getReplacementGrowth();

        recycler_adapter = new RecyclerAdapter_Replacement_Growth(arrayListReplacementGrowthRecords);
        recyclerView.setAdapter(recycler_adapter);
        recycler_adapter.notifyDataSetChanged();

    }

    private void local_getReplacementGrowth() {
        Cursor cursor_inventory = myDb.getDataFromReplacementInventoryWherePen(replacement_pen_id);
        cursor_inventory.moveToFirst();

        if (cursor_inventory.getCount() == 0) {
            Toast.makeText(this, "No data inventories.", Toast.LENGTH_LONG).show();
        } else {

            do {

                int replacement_inventory_id = cursor_inventory.getInt(0);

                Cursor cursor_growth = myDb.getAllDataFromReplacementGrowthWhereInvID(replacement_inventory_id);
                cursor_growth.moveToFirst();

                if (cursor_growth.getCount() != 0) {
                    do {
                        Replacement_GrowthRecords replacement_growthRecords = new Replacement_GrowthRecords(
                                cursor_growth.getInt(0),
                                cursor_growth.getInt(1),
                                cursor_growth.getInt(2),
                                cursor_growth.getString(3),
                                cursor_growth.getInt(4),
                                cursor_growth.getFloat(5),
                                cursor_growth.getInt(6),
                                cursor_growth.getFloat(7),
                                cursor_growth.getInt(8),
                                cursor_growth.getFloat(9),
                                cursor_growth.getString(10)
                        );
                        arrayListReplacementGrowthRecords.add(replacement_growthRecords);

                    } while (cursor_growth.moveToNext());
                } else
                    Toast.makeText(this, "No growth inventories.", Toast.LENGTH_LONG).show();

            } while (cursor_inventory.moveToNext());
        }
    }

    private void API_addReplacementGrowth(RequestParams requestParams){

        APIHelperAsync.addReplacementGrowth("addReplacementGrowth", requestParams, new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response){
                Toast.makeText(getApplicationContext(), "Successfully synced replacement growth record to web", Toast.LENGTH_SHORT).show();
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

    private void API_updateReplacementGrowth(){
        APIHelperAsync.getReplacementGrowth("getReplacementGrowth/", new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response){

                Gson gson = new Gson();
                JSONReplacementGrowth jsonBrooderInventory = gson.fromJson(rawJsonResponse, JSONReplacementGrowth.class);
                ArrayList<Replacement_GrowthRecords> arrayListBrooderFeedingWeb = jsonBrooderInventory.getData();


                ArrayList<Replacement_GrowthRecords> arrayListBrooderFeedingLocal = new ArrayList<>();

                Cursor cursor_brooder_feeding = myDb.getAllDataFromReplacementGrowthRecords();
                cursor_brooder_feeding.moveToFirst();
                if(cursor_brooder_feeding.getCount() != 0){
                    do {

                        Replacement_GrowthRecords brooderFeedingRecords =  new Replacement_GrowthRecords(cursor_brooder_feeding.getInt(0),cursor_brooder_feeding.getInt(1),null,cursor_brooder_feeding.getInt(2), cursor_brooder_feeding.getString(3),cursor_brooder_feeding.getInt(4), cursor_brooder_feeding.getFloat(5), cursor_brooder_feeding.getInt(6), cursor_brooder_feeding.getFloat(7),cursor_brooder_feeding.getInt(8), cursor_brooder_feeding.getFloat(9), cursor_brooder_feeding.getString(10));
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

                    Cursor cursor = myDb.getAllDataFromReplacementGrowthRecordsWhereGrowthID(id_to_sync.get(i));
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
                    requestParams.add("replacement_inventory_id", broodergrower_inventory_id.toString());
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

                    API_addReplacementGrowth(requestParams);



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

    private void API_getReplacementGrowth(){
        APIHelperAsync.getReplacementGrowth("getReplacementGrowth/", new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response){


                Gson gson = new Gson();
                JSONReplacementGrowth jsonBrooderInventory = gson.fromJson(rawJsonResponse, JSONReplacementGrowth.class);
                ArrayList<Replacement_GrowthRecords> arrayList_brooderInventory = jsonBrooderInventory.getData();


                for (int i = 0; i < arrayList_brooderInventory.size(); i++) {
                    //check if generation to be inserted is already in the database
                    Cursor cursor = myDb.getAllDataFromReplacementGrowthRecordsWhereGrowthID(arrayList_brooderInventory.get(i).getId());
                    cursor.moveToFirst();

                    if (cursor.getCount() == 0) {


                        boolean isInserted = myDb.insertDataBrooderGrowthRecordsWithID(arrayList_brooderInventory.get(i).getId(), arrayList_brooderInventory.get(i).getReplacement_growth_inventory_id(), arrayList_brooderInventory.get(i).getReplacement_growth_collection_day(), arrayList_brooderInventory.get(i).getReplacement_growth_date_collected(),arrayList_brooderInventory.get(i).getReplacement_growth_male_quantity(),arrayList_brooderInventory.get(i).getReplacement_growth_male_weight(),arrayList_brooderInventory.get(i).getReplacement_growth_female_quantity(),arrayList_brooderInventory.get(i).getReplacement_growth_female_weight(),arrayList_brooderInventory.get(i).getReplacement_growth_total_quantity(),arrayList_brooderInventory.get(i).getReplacement_growth_total_weight(),arrayList_brooderInventory.get(i).getReplacement_growth_deleted_at());
                        //Toast.makeText(BrooderInventoryActivity.this, "oyoyooyoy", Toast.LENGTH_SHORT).show();
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

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

}
