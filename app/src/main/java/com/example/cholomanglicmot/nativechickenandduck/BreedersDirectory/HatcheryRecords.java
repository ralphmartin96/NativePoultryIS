package com.example.cholomanglicmot.nativechickenandduck.BreedersDirectory;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
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

public class HatcheryRecords extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private Toolbar mToolbar;
    private ImageButton create_pen;
    private Button show_data_button;
    private Button delete_pen_table;


    DatabaseHelper myDb;
    RecyclerView recyclerView;
    RecyclerView.Adapter recycler_adapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Hatchery_Records> arrayListHatcheryRecords = new ArrayList<>();
    ArrayList<Breeder_Inventory>arrayList_temp = new ArrayList<>();
    FloatingActionButton create_egg_prod;
    TextView replacement_pheno_inv_id;
    String breeder_tag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hatchery_records);
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                breeder_tag= null;
            } else {
                breeder_tag= extras.getString("Breeder Tag");
            }
        } else {
            breeder_tag= (String) savedInstanceState.getSerializable("Breeder Tag");
        }
        final Bundle args = new Bundle();
        args.putString("Breeder Tag",breeder_tag);

        replacement_pheno_inv_id = findViewById(R.id.replacement_pheno_inv_id);
        create_egg_prod = findViewById(R.id.open_dialog);
        replacement_pheno_inv_id.setText("Breeder Tag | "+ breeder_tag);
        mToolbar = findViewById(R.id.nav_action);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Hatchery Records");
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        create_egg_prod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateHatcheryRecordDialog newFragment = new CreateHatcheryRecordDialog();
                newFragment.setArguments(args);
                newFragment.show(getSupportFragmentManager(), "CreateHatcheryRecordDialog");
            }
        });

        myDb = new DatabaseHelper(this);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerViewHatcheryRecords);
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
                    create_egg_prod.show();

                } else if (dy > 0) {
                    create_egg_prod.hide();
                }
            }
        });

        local_getHatcheryRecords();

        recycler_adapter = new RecyclerAdapter_Hatchery_Record(arrayListHatcheryRecords);
        recyclerView.setAdapter(recycler_adapter);
        recycler_adapter.notifyDataSetChanged();

    }

    private void local_getHatcheryRecords(){

        Cursor cur = myDb.getDataFromBreederInvWhereTag(breeder_tag);
        cur.moveToFirst();
        Integer inventory_id = cur.getInt(0);

        Cursor cursor_breeder_hatchery_records = myDb.getAllDataFromHatcheryRecords();
        cursor_breeder_hatchery_records.moveToFirst();

        if(cursor_breeder_hatchery_records.getCount() != 0){

            do {
                String deleted_at = cursor_breeder_hatchery_records.getString(9);

                if(inventory_id.equals(cursor_breeder_hatchery_records.getInt(1)) && deleted_at == null) {

                    Hatchery_Records hatcheryRecords = new Hatchery_Records(
                            cursor_breeder_hatchery_records.getInt(0),
                            cursor_breeder_hatchery_records.getInt(1),
                            cursor_breeder_hatchery_records.getString(2),
                            breeder_tag,
                            cursor_breeder_hatchery_records.getString(3),
                            cursor_breeder_hatchery_records.getInt(4),
                            cursor_breeder_hatchery_records.getInt(5),
                            cursor_breeder_hatchery_records.getInt(6),
                            cursor_breeder_hatchery_records.getInt(7),
                            cursor_breeder_hatchery_records.getString(8),
                            cursor_breeder_hatchery_records.getString(9)
                    );

                    Log.d("POULTRYDEBUGGER", "Hatchery ID: "+hatcheryRecords.getId());
                    arrayListHatcheryRecords.add(hatcheryRecords);
                }

            } while (cursor_breeder_hatchery_records.moveToNext());

            if(arrayListHatcheryRecords.isEmpty()) {
                Toast.makeText(this,"No data.", Toast.LENGTH_LONG).show();
            }

        }

    }

    private void API_getHatcheryRecords(){
        APIHelper.getHatcheryRecords("getHatcheryRecords/", new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response){

                Gson gson = new Gson();
                JSONHatchery jsonBreeder = gson.fromJson(rawJsonResponse, JSONHatchery.class);
                ArrayList<Hatchery_Records> arrayList_brooder = jsonBreeder.getData();

                for (int i = 0; i < arrayList_brooder.size(); i++) {
                    //check if generation to be inserted is already in the database
                    Cursor cursor = myDb.getAllDataFromBreederHatcheryWhereID(arrayList_brooder.get(i).getId());
                    cursor.moveToFirst();

                    if (cursor.getCount() == 0) {


                            boolean isInserted = myDb.insertHatcheryRecordsWithID(arrayList_brooder.get(i).getId(), arrayList_brooder.get(i).getBreeder_inv_id(),arrayList_brooder.get(i).getDate(), arrayList_brooder.get(i).getBatching_date(), arrayList_brooder.get(i).getEggs_set(), arrayList_brooder.get(i).getWeek_lay(), arrayList_brooder.get(i).getFertile(), arrayList_brooder.get(i).getHatched(), arrayList_brooder.get(i).getDate_hatched(), arrayList_brooder.get(i).getDeleted_at());
                            Toast.makeText(HatcheryRecords.this, "Hatchery Records Added", Toast.LENGTH_SHORT).show();
                    }

                }



            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonResponse, Object response){

                Toast.makeText(getApplicationContext(), "Failed to fetch Breeders from web database ", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable{
                return null;
            }
        });
    }

    private void API_addHatcheryRecords(RequestParams requestParams){
        APIHelper.addHatcheryRecords("addHatcheryRecords", requestParams, new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response){
                Toast.makeText(getApplicationContext(), "Successfully synced hatchery records to web", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonResponse, Object response){

                Toast.makeText(getApplicationContext(), "Failed to add hatchery record to web", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable{
                return null;
            }
        });
    }

    private void API_updateHatchery(){
        APIHelper.getHatcheryRecords("getHatcheryRecords/", new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response){

                Gson gson = new Gson();
                JSONHatchery jsonBrooderInventory = gson.fromJson(rawJsonResponse, JSONHatchery.class);
                ArrayList<Hatchery_Records> arrayListBrooderFeedingWeb = jsonBrooderInventory.getData();


                ArrayList<Hatchery_Records> arrayListBrooderFeedingLocal = new ArrayList<>();

                Cursor cursor_brooder_feeding = myDb.getAllDataFromHatcheryRecords();
                cursor_brooder_feeding.moveToFirst();
                if(cursor_brooder_feeding.getCount() != 0){
                    do {

                        Hatchery_Records hatcheryRecords = new Hatchery_Records(cursor_brooder_feeding.getInt(0), cursor_brooder_feeding.getInt(1), cursor_brooder_feeding.getString(2), breeder_tag, cursor_brooder_feeding.getString(3), cursor_brooder_feeding.getInt(4), cursor_brooder_feeding.getInt(5), cursor_brooder_feeding.getInt(6), cursor_brooder_feeding.getInt(7), cursor_brooder_feeding.getString(8), cursor_brooder_feeding.getString(9));
                        arrayListBrooderFeedingLocal.add(hatcheryRecords);

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

                    Cursor cursor = myDb.getAllDataFromBreederHatcheryWhereID(id_to_sync.get(i));
                    cursor.moveToFirst();
                    Integer id = cursor.getInt(0);
                    Integer breeder_inventory_id = cursor.getInt(1);
                    String date_eggs_set = cursor.getString(2);
                    String batching_date = cursor.getString(3);
                    Integer number_eggs_set = cursor.getInt(4);
                    Integer week_of_lay = cursor.getInt(5);
                    Integer number_fertile = cursor.getInt(6);
                    Integer number_hatched = cursor.getInt(7);
                    String date_hatched = cursor.getString(8);
                    String deleted_at = cursor.getString(9);




                    RequestParams requestParams = new RequestParams();
                    requestParams.add("id", id.toString());
                    requestParams.add("breeder_inventory_id", breeder_inventory_id.toString());
                    requestParams.add("date_eggs_set", date_eggs_set);
                    requestParams.add("batching_date", batching_date);
                    requestParams.add("number_eggs_set", number_eggs_set.toString());
                    requestParams.add("week_of_lay", week_of_lay.toString());
                    requestParams.add("number_fertile", number_fertile.toString());
                    requestParams.add("number_hatched", number_hatched.toString());
                    requestParams.add("date_hatched", date_hatched);
                    requestParams.add("deleted_at", deleted_at);



                    //Toast.makeText(BrooderFeedingRecordsActivity.this, id_to_sync.get(i).toString(), Toast.LENGTH_SHORT).show();

                    API_addHatcheryRecords(requestParams);

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
                = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public boolean onSupportNavigateUp() {
        Intent intent_brooders = new Intent(HatcheryRecords.this, CreateBreeders.class);
        startActivity(intent_brooders);
        return true;
    }

    @Override
    public void onBackPressed() {

        Intent intent_brooders = new Intent(HatcheryRecords.this, CreateBreeders.class);
        startActivity(intent_brooders);

    }
}
