package com.example.cholomanglicmot.nativechickenandduck.ReplacementsDirectory;

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

import cz.msebera.android.httpclient.Header;


public class ReplacementInventoryActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private TextView replacement_pen_textView;

    DatabaseHelper myDb;

    ArrayList<Replacement_Inventory> arrayListReplacementInventory = new ArrayList<>();
    ArrayList<Replacement_Inventory> arrayList_temp = new ArrayList<>();
    ArrayList<Replacements> arrayListReplacements = new ArrayList<>();
    ArrayList list = new ArrayList();
    Integer replacement_pen_id;

    RecyclerView recyclerView;
    RecyclerView.Adapter recycler_adapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Replacement_Inventory> arrayList_brooderInventory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_replacement_inventory);

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
        replacement_pen_textView = findViewById(R.id.replacement_pen);
        replacement_pen_textView.setText("Replacement Pen " +replacement_pen);

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

        replacement_pen_id = myDb.getPenIDWithNumber(replacement_pen);

        if (replacement_pen_id < 0) {
            Toast.makeText(getApplicationContext(), "No data", Toast.LENGTH_SHORT).show();
        } else {

            Cursor cursor_replacement_inv = myDb.getAllDataFromReplacementInventoryWherePenID(replacement_pen_id);
            cursor_replacement_inv.moveToFirst();

            if (cursor_replacement_inv.getCount() == 0) {
                Toast.makeText(this, "No data.", Toast.LENGTH_LONG).show();
            } else {
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

                    arrayListReplacementInventory.add(replacement_inventory);
                } while (cursor_replacement_inv.moveToNext());
            }

        }

        recycler_adapter = new RecyclerAdapter_Replacement_Inventory(arrayListReplacementInventory);
        recyclerView.setAdapter(recycler_adapter);
        recycler_adapter.notifyDataSetChanged();
    }
    private void API_updateReplacementInventory(){

        APIHelperAsync.getReplacementInventory("getReplacementInventory/", new BaseJsonHttpResponseHandler<Object>() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response){

                Gson gson = new Gson();
                JSONReplacementInventory jsonBrooderInventory = gson.fromJson(rawJsonResponse, JSONReplacementInventory.class);
                ArrayList<Replacement_Inventory> arrayListBrooderInventoryWeb = jsonBrooderInventory.getData();

                ArrayList<Replacement_Inventory> arrayListBrooderInventoryLocal = new ArrayList<>();

                Cursor cursor_brooder_inventory = myDb.getAllDataFromReplacementInventory();
                cursor_brooder_inventory.moveToFirst();
                if(cursor_brooder_inventory.getCount() != 0){
                    do {

                        Replacement_Inventory brooder_inventory = new Replacement_Inventory(cursor_brooder_inventory.getInt(0),cursor_brooder_inventory.getInt(1), cursor_brooder_inventory.getInt(2), cursor_brooder_inventory.getString(3),cursor_brooder_inventory.getString(4), cursor_brooder_inventory.getInt(5), cursor_brooder_inventory.getInt(6),cursor_brooder_inventory.getInt(7), cursor_brooder_inventory.getString(8), cursor_brooder_inventory.getString(9));
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

                    Cursor cursor = myDb.getAllDataFromReplacementInventoryWhereID(id_to_sync.get(i));
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
                    requestParams.add("replacement_id", broodergrower_id.toString());
                    requestParams.add("pen_id", pen_id.toString());
                    requestParams.add("replacement_tag", broodergrower_tag);
                    requestParams.add("batching_date", batching_date);
                    requestParams.add("number_male", number_male.toString());
                    requestParams.add("number_female", number_female.toString());
                    requestParams.add("total", total.toString());
                    requestParams.add("last_update", last_update);
                    requestParams.add("deleted_at", deleted_at);

                    //Toast.makeText(BrooderInventoryActivity.this, id_to_sync.get(i).toString(), Toast.LENGTH_SHORT).show();

                    API_addReplacementInventory(requestParams);



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
    private void API_addReplacementInventory(RequestParams requestParams){
        APIHelperAsync.addReplacementInventory("addReplacementInventory", requestParams, new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response){
                Toast.makeText(getApplicationContext(), "Successfully synced replacement inventory to web", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonResponse, Object response){

                Toast.makeText(getApplicationContext(), "Failed to sync replacement inventory to web", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable{
                return null;
            }
        });
    }

    private void API_getReplacementInventory(){
        APIHelperAsync.getReplacementInventory("getReplacementInventory/", new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response){

                Gson gson = new Gson();
                try{
                    JSONReplacementInventory jsonBrooderInventory = gson.fromJson(rawJsonResponse, JSONReplacementInventory.class);
                    arrayList_brooderInventory = jsonBrooderInventory.getData();

                    for (int i = 0; i < arrayList_brooderInventory.size(); i++) {
                        //check if generation to be inserted is already in the database
                        Cursor cursor = myDb.getAllDataFromReplacementInventoryWhereID(arrayList_brooderInventory.get(i).getId());
                        cursor.moveToFirst();

                        if (cursor.getCount() == 0) {


                            boolean isInserted = myDb.insertDataReplacementInventoryWithID(arrayList_brooderInventory.get(i).getId(), arrayList_brooderInventory.get(i).getReplacement_inv_replacement_id(), arrayList_brooderInventory.get(i).getReplacement_inv_pen(), arrayList_brooderInventory.get(i).getReplacement_inv_replacement_tag(),arrayList_brooderInventory.get(i).getReplacement_inv_batching_date(),arrayList_brooderInventory.get(i).getReplacement_male_quantity(),arrayList_brooderInventory.get(i).getReplacement_female_quantity(),arrayList_brooderInventory.get(i).getReplacement_total_quantity(), arrayList_brooderInventory.get(i).getReplacement_inv_last_update(), arrayList_brooderInventory.get(i).getReplacement_inv_deleted_at());

                        }

                    }

                }catch (Exception e){}

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonResponse, Object response){

                Toast.makeText(getApplicationContext(), "Failed to fetch Replacement Inventory from web database ", Toast.LENGTH_SHORT).show();
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
        Intent intent_brooders = new Intent(ReplacementInventoryActivity.this, CreateReplacements.class);

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
