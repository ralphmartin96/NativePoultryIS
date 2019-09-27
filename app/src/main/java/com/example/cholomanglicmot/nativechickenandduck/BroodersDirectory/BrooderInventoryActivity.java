package com.example.cholomanglicmot.nativechickenandduck.BroodersDirectory;

import android.app.AlertDialog;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.cholomanglicmot.nativechickenandduck.APIHelperAsync;
import com.example.cholomanglicmot.nativechickenandduck.DatabaseHelper;
import com.example.cholomanglicmot.nativechickenandduck.R;
import com.google.gson.Gson;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cz.msebera.android.httpclient.Header;

public class BrooderInventoryActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private TextView brooder_pen_textView;
    Integer pen_id;

    DatabaseHelper myDb;

    ArrayList<Brooder_Inventory> arrayListBrooderInventory = new ArrayList<>();
    ArrayList<Brooder_Inventory> arrayList_temp = new ArrayList<>();
    ArrayList<Brooder_Inventory> arrayList_brooderInventory = new ArrayList<>();

    Integer brooder_pen_id=null;


    RecyclerView recyclerView;
    RecyclerView.Adapter recycler_adapter;
    RecyclerView.LayoutManager layoutManager;


    Map<Integer, ArrayList<Brooder_Inventory>> brooder_id_dictionary = new HashMap<Integer, ArrayList<Brooder_Inventory>>();
    ArrayList<Brooder_Inventory> arrayList_brooder_inv_dict_list = new ArrayList<>();
    Integer farm_id;
    Integer fam_id=0;
    String farm_code=null;

    final String debugTag = "POULTRYDEBUGGER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brooder_inventory);
        String brooder_pen;

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
        brooder_pen_textView = findViewById(R.id.brooder_pen);
        brooder_pen_textView.setText("Brooder Pen " +brooder_pen);

        myDb = new DatabaseHelper(this);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerViewBrooderInventory);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        mToolbar = findViewById(R.id.nav_action);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Brooder Inventory");
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        local_getBrooderInventory(brooder_pen);

        recycler_adapter = new RecyclerAdapter_Brooder_Inventory(arrayList_temp, brooder_pen);
        recyclerView.setAdapter(recycler_adapter);
        recycler_adapter.notifyDataSetChanged();

    }

    private void local_getBrooderInventory(String brooder_pen){

        Cursor cursor_brooder_inventory = myDb.getAllDataFromBrooderInventory(); //para sa pagstore ng data sa arraylist
        cursor_brooder_inventory.moveToFirst();

        if(cursor_brooder_inventory.getCount() == 0){
            Toast.makeText(this,"No data inventories.", Toast.LENGTH_LONG).show();
        }else {
            do {
                Brooder_Inventory brooder_inventory = new Brooder_Inventory(
                        cursor_brooder_inventory.getInt(0),
                        cursor_brooder_inventory.getInt(1),
                        cursor_brooder_inventory.getInt(2),
                        cursor_brooder_inventory.getString(3),
                        cursor_brooder_inventory.getString(4),
                        cursor_brooder_inventory.getInt(5),
                        cursor_brooder_inventory.getInt(6),
                        cursor_brooder_inventory.getInt(7),
                        cursor_brooder_inventory.getString(8),
                        cursor_brooder_inventory.getString(9)
                );
                arrayListBrooderInventory.add(brooder_inventory);
            } while (cursor_brooder_inventory.moveToNext());
        }
        cursor_brooder_inventory.close();

        Cursor cursor1 = myDb.getAllDataFromPenWhere(brooder_pen);
        cursor1.moveToFirst();

        if(cursor1.getCount() != 0){
            brooder_pen_id = cursor1.getInt(0);
        }

        cursor1.close();

        for (Brooder_Inventory brooder_inv : arrayListBrooderInventory){

            if(brooder_inv.getBrooder_inv_pen().equals(brooder_pen_id)
                    && brooder_inv.getBrooder_inv_deleted_at() == null){
                arrayList_temp.add(brooder_inv);
            }

        }

    }

    private void API_getBrooderInventory(){
        APIHelperAsync.getBrooderInventory("getBrooderInventory/", new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response){

                Gson gson = new Gson();
                JSONBrooderInventory jsonBrooderInventory = gson.fromJson(rawJsonResponse, JSONBrooderInventory.class);
                arrayList_brooderInventory = jsonBrooderInventory.getData();

                for (int i = 0; i < arrayList_brooderInventory.size(); i++) {
                    //check if generation to be inserted is already in the database
                    Cursor cursor = myDb.getAllDataFromBrooderInventoryWhereID(arrayList_brooderInventory.get(i).getId());
                    cursor.moveToFirst();

                    if (cursor.getCount() == 0) {

                        boolean isInserted = myDb.insertDataBrooderInventoryWithID(
                                arrayList_brooderInventory.get(i).getId(),
                                arrayList_brooderInventory.get(i).getBrooder_inv_brooder_id(),
                                arrayList_brooderInventory.get(i).getBrooder_inv_pen(),
                                arrayList_brooderInventory.get(i).getBrooder_inv_brooder_tag(),
                                arrayList_brooderInventory.get(i).getBrooder_inv_batching_date(),
                                arrayList_brooderInventory.get(i).getBrooder_male_quantity(),
                                arrayList_brooderInventory.get(i).getBrooder_female_quantity(),
                                arrayList_brooderInventory.get(i).getBrooder_total_quantity(),
                                arrayList_brooderInventory.get(i).getBrooder_inv_last_update(),
                                arrayList_brooderInventory.get(i).getBrooder_inv_deleted_at()
                        );

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

    private void API_updateBrooderInventory(){

        APIHelperAsync.getBrooderInventory("getBrooderInventory/", new BaseJsonHttpResponseHandler<Object>() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response){

                Gson gson = new Gson();
                JSONBrooderInventory jsonBrooderInventory = gson.fromJson(rawJsonResponse, JSONBrooderInventory.class);
                ArrayList<Brooder_Inventory> arrayListBrooderInventoryWeb = jsonBrooderInventory.getData();

                ArrayList<Brooder_Inventory> arrayListBrooderInventoryLocal = new ArrayList<>();

                Cursor cursor_brooder_inventory = myDb.getAllDataFromBrooderInventory();
                cursor_brooder_inventory.moveToFirst();
                if(cursor_brooder_inventory.getCount() != 0){
                    do {

                        Brooder_Inventory brooder_inventory = new Brooder_Inventory(cursor_brooder_inventory.getInt(0),cursor_brooder_inventory.getInt(1), cursor_brooder_inventory.getInt(2), cursor_brooder_inventory.getString(3),cursor_brooder_inventory.getString(4), cursor_brooder_inventory.getInt(5), cursor_brooder_inventory.getInt(6),cursor_brooder_inventory.getInt(7), cursor_brooder_inventory.getString(8), cursor_brooder_inventory.getString(9));
                        arrayListBrooderInventoryLocal.add(brooder_inventory);
                    } while (cursor_brooder_inventory.moveToNext());
                }




                //arrayListBrooderInventoryLocal contains all data from local database
                //arrayListBrooderInventoryWeb   contains all data from web database

                //put the ID of each brooder inventory to another arraylist
                ArrayList<Integer> id_local = new ArrayList<>();
                ArrayList<Integer> id_web = new ArrayList<>();
                ArrayList<Integer> id_to_sync = new ArrayList<>();

                for(int i=0;i<arrayListBrooderInventoryLocal.size();i++){
                    id_local.add(arrayListBrooderInventoryLocal.get(i).getId());
                }
                for(int i=0;i<arrayListBrooderInventoryWeb.size();i++){
                    id_web.add(arrayListBrooderInventoryWeb.get(i).getId());
                }


                for (int i=0;i<id_local.size();i++){
                    if(!id_web.contains(id_local.get(i))){ //if id_web does not contain the current value of i, add it the an arraylist
                        id_to_sync.add(id_local.get(i));
                    }
                }


               for(int i=0;i<id_to_sync.size();i++){

                   Cursor cursor = myDb.getAllDataFromBrooderInventoryWhereID(id_to_sync.get(i));
                   cursor.moveToFirst();
                   Integer id = cursor.getInt(0);
                   Integer broodergrower_id = cursor.getInt(1);
                   Integer pen_id = cursor.getInt(2);
                   String broodergrower_tag = cursor.getString(3);
                   String batching_date = cursor.getString(4);
                   Integer number_male = cursor.getInt(5);
                   Integer number_female = cursor.getInt(6);
                   Integer total = cursor.getInt(7);
                   String  last_update = cursor.getString(8);
                   String deleted_at = cursor.getString(9);

                   RequestParams requestParams = new RequestParams();
                   requestParams.add("id", id.toString());
                   requestParams.add("broodergrower_id", broodergrower_id.toString());
                   requestParams.add("pen_id", pen_id.toString());
                   requestParams.add("broodergrower_tag", broodergrower_tag);
                   requestParams.add("batching_date", batching_date);
                   requestParams.add("number_male", number_male.toString());
                   requestParams.add("number_female", number_female.toString());
                   requestParams.add("total", total.toString());
                   requestParams.add("last_update", last_update);
                   requestParams.add("deleted_at", deleted_at);

                   Toast.makeText(BrooderInventoryActivity.this, id_to_sync.get(i).toString(), Toast.LENGTH_SHORT).show();

                   API_addBrooderInventory(requestParams);



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

    private void API_addBrooderInventory(RequestParams requestParams){
        APIHelperAsync.addBrooderInventory("addBrooderInventory", requestParams, new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response){
                Toast.makeText(getApplicationContext(), "Successfully synced brooder inventory to web", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonResponse, Object response){


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
        Intent intent_brooders = new Intent(BrooderInventoryActivity.this, CreateBrooders.class);

        startActivity(intent_brooders);

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
