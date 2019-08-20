package com.example.cholomanglicmot.nativechickenandduck.BroodersDirectory;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cz.msebera.android.httpclient.Header;


public class BrooderFeedingRecordsActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private TextView brooder_pen_textView;


    DatabaseHelper myDb;
    RecyclerView recyclerView;
    RecyclerView.Adapter recycler_adapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Brooder_FeedingRecords> arrayListBrooderFeedingRecords = new ArrayList<>();//create constructor first for brooder feeding records
    ArrayList<Brooder_FeedingRecords> arrayListBrooderFeedingRecords2 = new ArrayList<>();//create constructor first for brooder feeding records
    ArrayList<Brooder_Inventory>arrayListBrooderInventory = new ArrayList<>();
    //ArrayList<Brooder_Inventory>arrayList_temp = new ArrayList<>();
    ArrayList<Brooder_Inventory>arrayList_temp2 = new ArrayList<>();
    FloatingActionButton create_brooder_feeding_records;
    Integer pen_id , brooder_pen_id;
    String brooder_pen;
    Integer farm_id;
    Integer fam_id=0;
    String farm_code=null;
    Map<Integer, Integer> inventory_dictionary = new HashMap<Integer, Integer>();
    ArrayList<Integer> list = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brooder_feeding_records);

        final Integer brooder_inv_id;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                brooder_pen= null;
                brooder_inv_id = null;
            } else {
                brooder_pen= extras.getString("Brooder Pen");
                brooder_inv_id = extras.getInt("Brooder Inventory ID");
            }
        } else {
            brooder_pen= (String) savedInstanceState.getSerializable("Brooder Pen");
            brooder_inv_id =(Integer) savedInstanceState.getSerializable("Brooder Inventory ID");
        }

        final Bundle args = new Bundle();
        args.putString("brooder pen number",brooder_pen);
        brooder_pen_textView = findViewById(R.id.brooder_pen);
        brooder_pen_textView.setText("Brooder Pen " +brooder_pen);
        mToolbar = findViewById(R.id.nav_action);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Brooder Feeding Records");
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        create_brooder_feeding_records = findViewById(R.id.open_dialog);

        create_brooder_feeding_records.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateBrooderFeedingRecordDialog newFragment = new CreateBrooderFeedingRecordDialog();
                newFragment.setArguments(args);
                newFragment.show(getSupportFragmentManager(), "CreateBrooderFeedingRecordDialog");
            }
        });



        myDb = new DatabaseHelper(this);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerViewBrooderFeedingRecords);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        ///////////////////////////////DATABASE

        FirebaseAuth mAuth;

        mAuth = FirebaseAuth.getInstance();

        FirebaseUser user = mAuth.getCurrentUser();

        String name = user.getDisplayName();

        String email = user.getEmail();

        Uri photo = user.getPhotoUrl();

        Cursor cursor_farm_id = myDb.getFarmIDFromUsers(email);
        cursor_farm_id.moveToFirst();
        if(cursor_farm_id.getCount() != 0){
            farm_id = cursor_farm_id.getInt(0);
        }
        Cursor cursor_code = myDb.getAllDataFromFarms(farm_id);
        cursor_code.moveToFirst();
        if(cursor_code.getCount() != 0){
            farm_code = cursor_code.getString(2);
        }

        Cursor cursor = myDb.getAllDataFromPenWhere(brooder_pen);
        cursor.moveToFirst();
        if(cursor.getCount()!=0){
            pen_id = cursor.getInt(0);
        }

        boolean isNetworkAvailable = isNetworkAvailable();
        if (isNetworkAvailable) {
            //if internet is available, load data from web database




            API_updateBrooderFeeding();
            API_getBrooderFeeding();


        }
        //Toast.makeText(this, pen_id.toString(), Toast.LENGTH_SHORT).show();
        Cursor cursor_inventory = myDb.getDataFromBrooderInventoryWherePen(pen_id);
        cursor_inventory.moveToFirst();
        if(cursor_inventory.getCount() == 0){
            //show message
           // Toast.makeText(this,"No data inventories.", Toast.LENGTH_LONG).show();

        }else {
            do {
                String breeder_tag = cursor_inventory.getString(3);

                if(breeder_tag.contains(farm_code)) {
                    Brooder_Inventory brooder_inventory = new Brooder_Inventory(cursor_inventory.getInt(0), cursor_inventory.getInt(1), cursor_inventory.getInt(2), cursor_inventory.getString(3), cursor_inventory.getString(4), cursor_inventory.getInt(5), cursor_inventory.getInt(6), cursor_inventory.getInt(7), cursor_inventory.getString(8), cursor_inventory.getString(9));
                    arrayListBrooderInventory.add(brooder_inventory);
                }
            } while (cursor_inventory.moveToNext());
        }




        Cursor cursor_feeding = myDb.getAllDataFromBrooderFeedingRecords();
            cursor_feeding.moveToFirst();
            if(cursor_feeding.getCount() != 0){
                do{

                    Brooder_FeedingRecords brooderFeedingRecords = new Brooder_FeedingRecords(cursor_feeding.getInt(0),cursor_feeding.getInt(1), cursor_feeding.getString(2), null,cursor_feeding.getFloat(3),cursor_feeding.getFloat(4), cursor_feeding.getString(5), cursor_feeding.getString(6));

                    arrayListBrooderFeedingRecords.add(brooderFeedingRecords);


                    /*    }
                    }*/
                }while(cursor_feeding.moveToNext());
            }

            ArrayList<Brooder_FeedingRecords> arrayList_final = new ArrayList<Brooder_FeedingRecords>();

            for(int i=0;i<arrayListBrooderInventory.size();i++){
                for(int k=0;k<arrayListBrooderFeedingRecords.size();k++){
                    if(arrayListBrooderInventory.get(i).getId()==arrayListBrooderFeedingRecords.get(k).getBrooder_feeding_inventory_id() && arrayListBrooderFeedingRecords.get(k).getBrooder_feeding_deleted_at() == null){
                        arrayList_final.add(arrayListBrooderFeedingRecords.get(k));
                    }
                }
            }



            recycler_adapter = new RecyclerAdapter_Brooder_Feeding(arrayList_final);
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

    private void API_addBrooderFeeding(RequestParams requestParams){

        APIHelper.addBrooderFeeding("addBrooderFeeding", requestParams, new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response){
                Toast.makeText(getApplicationContext(), "Successfully synced brooder feeding record to web", Toast.LENGTH_SHORT).show();
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
    private void API_updateBrooderFeeding(){
        APIHelper.getBrooderFeeding("getBrooderFeeding/", new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response){

                Gson gson = new Gson();
                JSONBrooderFeeding jsonBrooderInventory = gson.fromJson(rawJsonResponse, JSONBrooderFeeding.class);
                ArrayList<Brooder_FeedingRecords> arrayListBrooderFeedingWeb = jsonBrooderInventory.getData();


                ArrayList<Brooder_FeedingRecords> arrayListBrooderFeedingLocal = new ArrayList<>();

                Cursor cursor_brooder_feeding = myDb.getAllDataFromBrooderFeedingRecords();
                cursor_brooder_feeding.moveToFirst();
                if(cursor_brooder_feeding.getCount() != 0){
                    do {

                        Brooder_FeedingRecords brooderFeedingRecords = new Brooder_FeedingRecords(cursor_brooder_feeding.getInt(0),cursor_brooder_feeding.getInt(1), cursor_brooder_feeding.getString(2), null,cursor_brooder_feeding.getFloat(3),cursor_brooder_feeding.getFloat(4), cursor_brooder_feeding.getString(5), cursor_brooder_feeding.getString(6));
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

                    Cursor cursor = myDb.getDataFromBrooderFeedingRecordsWhereFeedingID(id_to_sync.get(i));
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

                    API_addBrooderFeeding(requestParams);



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
    private void API_getBrooderFeeding(){
        APIHelper.getBrooderFeeding("getBrooderFeeding/", new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response){

                Gson gson = new Gson();
                JSONBrooderFeeding jsonBrooderInventory = gson.fromJson(rawJsonResponse, JSONBrooderFeeding.class);
                ArrayList<Brooder_FeedingRecords> arrayList_brooderInventory = jsonBrooderInventory.getData();


                for (int i = 0; i < arrayList_brooderInventory.size(); i++) {
                    //check if generation to be inserted is already in the database
                    Cursor cursor = myDb.getAllDataFromBrooderFeedingRecordsWhereFeedingID(arrayList_brooderInventory.get(i).getId());
                    cursor.moveToFirst();

                    if (cursor.getCount() == 0) {


                        boolean isInserted = myDb.insertDataBrooderFeedingRecordsWithID(arrayList_brooderInventory.get(i).getId(), arrayList_brooderInventory.get(i).getBrooder_feeding_inventory_id(), arrayList_brooderInventory.get(i).getBrooder_feeding_date_collected(), arrayList_brooderInventory.get(i).getBrooder_feeding_offered(),arrayList_brooderInventory.get(i).getBrooder_feeding_refused(),arrayList_brooderInventory.get(i).getBrooder_feeding_remarks(),arrayList_brooderInventory.get(i).getBrooder_feeding_deleted_at());
                        //Toast.makeText(getApplicationContext(), "SUCCESS to fetch Brooders Inventory from web database ", Toast.LENGTH_SHORT).show();
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
