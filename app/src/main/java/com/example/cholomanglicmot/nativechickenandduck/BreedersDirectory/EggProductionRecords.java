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

public class EggProductionRecords extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private Toolbar mToolbar;

    private Button show_data_button;
    private Button delete_pen_table;


    DatabaseHelper myDb;
    RecyclerView recyclerView;
    RecyclerView.Adapter recycler_adapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Egg_Production> arrayListEggProductionRecords = new ArrayList<>();
    ArrayList<Breeder_Inventory>arrayListBrooderInventory = new ArrayList<>();
    ArrayList<Breeder_Inventory>arrayList_temp = new ArrayList<>();
    FloatingActionButton create_egg_prod;
    TextView replacement_pheno_inv_id;
    String breeder_tag;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_egg_production_records);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                breeder_tag= null;
            } else {
                breeder_tag= extras.getString("Breeder Tag");
            }
        } else {
            breeder_tag = (String) savedInstanceState.getSerializable("Breeder Tag");
        }
        final Bundle args = new Bundle();
        args.putString("Breeder Tag",breeder_tag);

        replacement_pheno_inv_id = findViewById(R.id.replacement_pheno_inv_id);
        create_egg_prod = findViewById(R.id.open_dialog);
        replacement_pheno_inv_id.setText("Egg Production | "+ breeder_tag);
        mToolbar = findViewById(R.id.nav_action);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Breeder Egg Productions");
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        create_egg_prod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateEggProductionDialog newFragment = new CreateEggProductionDialog();
                newFragment.setArguments(args);
                newFragment.show(getSupportFragmentManager(), "CreateEggProductionDialog");
            }
        });

        myDb = new DatabaseHelper(this);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerViewReplacementEggProductionRecords);
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

        local_getEggProduction();

        recycler_adapter = new RecyclerAdapter_Egg_Production(arrayListEggProductionRecords);
        recyclerView.setAdapter(recycler_adapter);
        recycler_adapter.notifyDataSetChanged();

    }

    private void local_getEggProduction(){
        Cursor cur = myDb.getDataFromBreederInvWhereTag(breeder_tag);
        cur.moveToFirst();
        Integer inventory_id = cur.getInt(0);

        Cursor cursor_brooder_feeding_records = myDb.getAllDataFromEggProduction();
        cursor_brooder_feeding_records.moveToFirst();

        if(cursor_brooder_feeding_records.getCount() != 0){

            do {
                Float total_weight = cursor_brooder_feeding_records.getFloat(4);
                Integer total_intact = cursor_brooder_feeding_records.getInt(3);
                Float average_weight = total_weight/total_intact;
                Integer breeder_inv_id1 = cursor_brooder_feeding_records.getInt(1);
                String deleted_at = cursor_brooder_feeding_records.getString(8);

                if(breeder_inv_id1.equals(inventory_id) && deleted_at == null) {

                    Egg_Production egg_production = new Egg_Production(
                            cursor_brooder_feeding_records.getInt(0),
                            cursor_brooder_feeding_records.getInt(1),
                            cursor_brooder_feeding_records.getString(2),
                            breeder_tag,
                            cursor_brooder_feeding_records.getInt(3),
                            cursor_brooder_feeding_records.getFloat(4),
                            average_weight,
                            cursor_brooder_feeding_records.getInt(5),
                            cursor_brooder_feeding_records.getInt(6),
                            cursor_brooder_feeding_records.getString(7),
                            cursor_brooder_feeding_records.getString(8),
                            cursor_brooder_feeding_records.getString(9)
                    );

                    arrayListEggProductionRecords.add(egg_production);
                }
            }while (cursor_brooder_feeding_records.moveToNext());

            if(arrayListEggProductionRecords.isEmpty())
                Toast.makeText(this,"No data.", Toast.LENGTH_LONG).show();

        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void API_getEggProduction(){
        APIHelper.getEggProduction("getEggProduction/", new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response){

                Gson gson = new Gson();
                try{
                    JSONEggProduction jsonBreeder = gson.fromJson(rawJsonResponse, JSONEggProduction.class);
                    ArrayList<Egg_Production> arrayList_brooder = jsonBreeder.getData();

                    for (int i = 0; i < arrayList_brooder.size(); i++) {
                        //check if generation to be inserted is already in the database
                        Cursor cursor = myDb.getAllDataFromEggProductionWhereID(arrayList_brooder.get(i).getId());
                        cursor.moveToFirst();

                        if (cursor.getCount() == 0) {
                            /* public boolean insertEggProductionRecordsWithID(Integer id,Integer breeder_inv_id, String date, Integer intact, Float weight, Integer broken, Integer rejects, String remarks, String deleted_at){
                             */
                            boolean isInserted = myDb.insertEggProductionRecordsWithID(arrayList_brooder.get(i).getId(), arrayList_brooder.get(i).getEgg_breeder_inv_id(),arrayList_brooder.get(i).getDate(), arrayList_brooder.get(i).getIntact(), arrayList_brooder.get(i).getWeight(), arrayList_brooder.get(i).getBroken(), arrayList_brooder.get(i).getRejects(), arrayList_brooder.get(i).getRemarks(), arrayList_brooder.get(i).getDeleted_at(),arrayList_brooder.get(i).getFemale_inventory());
                            Toast.makeText(EggProductionRecords.this, "Egg Production Added", Toast.LENGTH_SHORT).show();
                        }

                    }
                }catch (Exception e){}




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

    private void API_addEggProduction(RequestParams requestParams){
        APIHelper.addEggProduction("addEggProduction", requestParams, new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response){
                Toast.makeText(getApplicationContext(), "Successfully synced egg production record to web", Toast.LENGTH_SHORT).show();
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

    private void API_updateEggProduction(){
        APIHelper.getEggProduction("getEggProduction/", new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response){

                Gson gson = new Gson();
                try{
                    JSONEggProduction jsonBrooderInventory = gson.fromJson(rawJsonResponse, JSONEggProduction.class);
                    ArrayList<Egg_Production> arrayListBrooderFeedingWeb = jsonBrooderInventory.getData();


                    ArrayList<Egg_Production> arrayListBrooderFeedingLocal = new ArrayList<>();

                    Cursor cursor_brooder_feeding = myDb.getAllDataFromEggProduction();
                    cursor_brooder_feeding.moveToFirst();
                    if(cursor_brooder_feeding.getCount() != 0){
                        do {
                            Float total_weight = cursor_brooder_feeding.getFloat(4);
                            Integer total_intact = cursor_brooder_feeding.getInt(3);
                            Float average_weight = total_weight/total_intact;
                            Integer breeder_inv_id1 = cursor_brooder_feeding.getInt(1);
                            String deleted_at = cursor_brooder_feeding.getString(8);
                            Egg_Production egg_production = new Egg_Production(cursor_brooder_feeding.getInt(0), cursor_brooder_feeding.getInt(1), cursor_brooder_feeding.getString(2), breeder_tag, cursor_brooder_feeding.getInt(3), cursor_brooder_feeding.getFloat(4), average_weight, cursor_brooder_feeding.getInt(5), cursor_brooder_feeding.getInt(6), cursor_brooder_feeding.getString(7), cursor_brooder_feeding.getString(8), cursor_brooder_feeding.getString(9));
                            arrayListBrooderFeedingLocal.add(egg_production);

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

                        Cursor cursor = myDb.getAllDataFromEggProductionWhereID(id_to_sync.get(i));
                        cursor.moveToFirst();
                        Integer id = cursor.getInt(0);
                        Integer breeder_inventory_id = cursor.getInt(1);
                        String date_collected = cursor.getString(2);
                        Integer total_eggs_intact = cursor.getInt(3);
                        Float total_egg_weight = cursor.getFloat(4);
                        Integer total_broken = cursor.getInt(5);
                        Integer total_rejects = cursor.getInt(6);
                        String remarks = cursor.getString(7);
                        String deleted_at = cursor.getString(8);




                        RequestParams requestParams = new RequestParams();
                        requestParams.add("id", id.toString());
                        requestParams.add("breeder_inventory_id", breeder_inventory_id.toString());
                        requestParams.add("date_collected", date_collected);
                        requestParams.add("total_eggs_intact", total_eggs_intact.toString());
                        requestParams.add("total_egg_weight", total_egg_weight.toString());
                        requestParams.add("total_broken", total_broken.toString());
                        requestParams.add("total_rejects", total_rejects.toString());
                        requestParams.add("remarks", remarks);
                        requestParams.add("deleted_at", deleted_at);


                        //Toast.makeText(BrooderFeedingRecordsActivity.this, id_to_sync.get(i).toString(), Toast.LENGTH_SHORT).show();

                        API_addEggProduction(requestParams);

                    }
                }catch (Exception e){}


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

    @Override
    public boolean onSupportNavigateUp() {
        Intent intent_brooders = new Intent(EggProductionRecords.this, CreateBreeders.class);
        startActivity(intent_brooders);
        return true;
    }

    @Override
    public void onBackPressed() {

        Intent intent_brooders = new Intent(EggProductionRecords.this, CreateBreeders.class);
        startActivity(intent_brooders);

    }
}
