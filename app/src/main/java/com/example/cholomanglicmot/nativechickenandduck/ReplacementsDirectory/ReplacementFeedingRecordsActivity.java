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

import com.example.cholomanglicmot.nativechickenandduck.APIHelper;
import com.example.cholomanglicmot.nativechickenandduck.DatabaseHelper;
import com.example.cholomanglicmot.nativechickenandduck.R;
import com.google.gson.Gson;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;


public class ReplacementFeedingRecordsActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private TextView replacement_pen_textView;
    Integer replacement_pen_id;

    DatabaseHelper myDb;

    RecyclerView recyclerView;
    RecyclerView.Adapter recycler_adapter;
    RecyclerView.LayoutManager layoutManager;

    ArrayList<Replacement_Inventory> arrayList_temp = new ArrayList<>();//create constructor first for brooder feeding records
    ArrayList<Replacement_FeedingRecords> arrayListReplacementFeedingRecords = new ArrayList<>();
    ArrayList<Replacement_Inventory> arrayListReplacementInventory = new ArrayList<>();
    ArrayList<Replacement_Inventory> arrayListReplacementInventory1 = new ArrayList<>();
    ArrayList<Replacements>arrayListReplacements = new ArrayList<>();

    FloatingActionButton create_replacement_feeding_records;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_replacement_feeding_records);
        final String replacement_pen;
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

        final Bundle args = new Bundle();
        args.putString("Replacement Pen",replacement_pen);
        StringBuffer buffer = new StringBuffer();

        replacement_pen_textView = findViewById(R.id.replacement_pen);
        replacement_pen_textView.setText("Replacement Pen " +replacement_pen);
        mToolbar = findViewById(R.id.nav_action);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Replacement Feeding Records");
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        create_replacement_feeding_records = findViewById(R.id.open_dialog);

        create_replacement_feeding_records.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateReplacementFeedingRecordDialog newFragment = new CreateReplacementFeedingRecordDialog();
                newFragment.setArguments(args);
                newFragment.show(getSupportFragmentManager(), "CreateReplacementFeedingRecordDialog");
            }
        });

        myDb = new DatabaseHelper(this);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerViewReplacementFeedingRecords);
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
                    create_replacement_feeding_records.show();

                } else if (dy > 0) {
                    create_replacement_feeding_records.hide();
                }
            }
        });

        replacement_pen_id = myDb.getPenIDWithNumber(replacement_pen);

        local_getReplacementFeeding();

        recycler_adapter = new RecyclerAdapter_Replacement_Feeding(arrayListReplacementFeedingRecords);
        recyclerView.setAdapter(recycler_adapter);
        recycler_adapter.notifyDataSetChanged();

    }

    private void local_getReplacementFeeding() {
        Cursor cursor_inventory = myDb.getDataFromReplacementInventoryWherePen(replacement_pen_id);
        cursor_inventory.moveToFirst();

        if (cursor_inventory.getCount() == 0) {
            Toast.makeText(this, "No data inventories.", Toast.LENGTH_LONG).show();
        } else {
            do {

                int replacement_inventory_id = cursor_inventory.getInt(0);

                Cursor cursor_feeding = myDb.getAllDataFromReplacementFeedingWhereInvID(replacement_inventory_id);
                cursor_feeding.moveToFirst();

                if (cursor_feeding.getCount() != 0) {

                    do {
                        Replacement_FeedingRecords brooderFeedingRecords = new Replacement_FeedingRecords(
                                cursor_feeding.getInt(0),
                                cursor_feeding.getInt(1),
                                cursor_feeding.getString(2),
                                null, cursor_feeding.getFloat(3),
                                cursor_feeding.getFloat(4),
                                cursor_feeding.getString(5),
                                cursor_feeding.getString(6)
                        );

                        arrayListReplacementFeedingRecords.add(brooderFeedingRecords);
                    } while (cursor_feeding.moveToNext());
                } else
                    Toast.makeText(this, "No feeding inventories.", Toast.LENGTH_LONG).show();

            } while (cursor_inventory.moveToNext());
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void API_addReplacementFeeding(RequestParams requestParams){

        APIHelper.addReplacementFeeding("addReplacementFeeding", requestParams, new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response){
                Toast.makeText(getApplicationContext(), "Successfully synced replacement feeding record to web", Toast.LENGTH_SHORT).show();
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

    private void API_updateReplacementFeeding(){
        APIHelper.getReplacementFeeding("getReplacementFeeding/", new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response){

                Gson gson = new Gson();
                JSONReplacementFeeding jsonBrooderInventory = gson.fromJson(rawJsonResponse, JSONReplacementFeeding.class);
                ArrayList<Replacement_FeedingRecords> arrayListBrooderFeedingWeb = jsonBrooderInventory.getData();


                ArrayList<Replacement_FeedingRecords> arrayListBrooderFeedingLocal = new ArrayList<>();

                Cursor cursor_brooder_feeding = myDb.getAllDataFromReplacementFeedingRecords();
                cursor_brooder_feeding.moveToFirst();
                if(cursor_brooder_feeding.getCount() != 0){
                    do {

                        Replacement_FeedingRecords brooderFeedingRecords = new Replacement_FeedingRecords(cursor_brooder_feeding.getInt(0),cursor_brooder_feeding.getInt(1), cursor_brooder_feeding.getString(2), null,cursor_brooder_feeding.getFloat(3),cursor_brooder_feeding.getFloat(4), cursor_brooder_feeding.getString(5), cursor_brooder_feeding.getString(6));
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

                    Cursor cursor = myDb.getAllDataFromReplacementFeedingRecordsWhereFeedingID(id_to_sync.get(i));
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
                    requestParams.add("replacement_inventory_id", broodergrower_inventory_id.toString());
                    requestParams.add("date_collected", date_collected);
                    requestParams.add("amount_offered", amount_offered.toString());
                    requestParams.add("amount_refused", amount_refused.toString());
                    requestParams.add("remarks", remarks);
                    requestParams.add("deleted_at", deleted_at);


                    //Toast.makeText(BrooderFeedingRecordsActivity.this, id_to_sync.get(i).toString(), Toast.LENGTH_SHORT).show();

                    API_addReplacementFeeding(requestParams);



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

    private void API_getReplacementFeeding(){
        APIHelper.getReplacementFeeding("getReplacementFeeding/", new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response){


                Gson gson = new Gson();
                JSONReplacementFeeding jsonBrooderInventory = gson.fromJson(rawJsonResponse, JSONReplacementFeeding.class);
                ArrayList<Replacement_FeedingRecords> arrayList_brooderInventory = jsonBrooderInventory.getData();


                for (int i = 0; i < arrayList_brooderInventory.size(); i++) {
                    //check if generation to be inserted is already in the database
                    Cursor cursor = myDb.getAllDataFromReplacementFeedingRecordsWhereFeedingID(arrayList_brooderInventory.get(i).getId());
                    cursor.moveToFirst();

                    if (cursor.getCount() == 0) {


                        boolean isInserted = myDb.insertDataReplacementFeedingRecordsWithID(arrayList_brooderInventory.get(i).getId(), arrayList_brooderInventory.get(i).getReplacement_feeding_inventory_id(), arrayList_brooderInventory.get(i).getReplacement_feeding_date_collected(), arrayList_brooderInventory.get(i).getReplacement_feeding_offered(),arrayList_brooderInventory.get(i).getReplacement_feeding_refused(),arrayList_brooderInventory.get(i).getReplacement_feeding_remarks(),arrayList_brooderInventory.get(i).getReplacement_feeding_deleted_at());

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
