package com.example.cholomanglicmot.nativechickenandduck.BreedersDirectory;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cholomanglicmot.nativechickenandduck.APIHelper;
import com.example.cholomanglicmot.nativechickenandduck.BroodersDirectory.JSONMortalityAndSales;
import com.example.cholomanglicmot.nativechickenandduck.DatabaseHelper;
import com.example.cholomanglicmot.nativechickenandduck.R;
import com.google.gson.Gson;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MortalityAndSalesRecords extends AppCompatActivity {
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
    ArrayList<Egg_Production> arrayListBrooderGrowthRecords = new ArrayList<>();
    ArrayList<Breeder_Inventory>arrayListBrooderInventory = new ArrayList<>();
    ArrayList<Mortality_Sales>arrayList_temp = new ArrayList<>();
    ImageButton create_egg_prod;
    TextView replacement_pheno_inv_id;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mortality_and_sales);
        final String breeder_tag;
        final Integer breeder_id;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                breeder_tag= null;
                breeder_id=null;
            } else {
                breeder_tag= extras.getString("Breeder Tag");
                breeder_id=extras.getInt("Breeder ID");
            }
        } else {
            breeder_tag= (String) savedInstanceState.getSerializable("Breeder Tag");
            breeder_id = (Integer) savedInstanceState.getSerializable("Breeder ID");
        }
        final Bundle args = new Bundle();
        args.putString("Breeder Tag",breeder_tag);
//        args.putInt("Breeder ID", breeder_id);
        replacement_pheno_inv_id = findViewById(R.id.replacement_pheno_inv_id);
        replacement_pheno_inv_id.setText("Mortality and Sales | " + breeder_tag);
        mToolbar = findViewById(R.id.nav_action);
        create_egg_prod = findViewById(R.id.open_dialog);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Mortality and Sales");
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        recyclerView = (RecyclerView)findViewById(R.id.recyclerViewReplacementViewPhenoMorphoRecords);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        create_egg_prod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateMortalityAndSalesDialog newFragment = new CreateMortalityAndSalesDialog();
                newFragment.setArguments(args);
                newFragment.show(getSupportFragmentManager(), "CreateMortalityAndSalesDialog");
            }
        });



        myDb = new DatabaseHelper(this);
        ///////////////////DATABASE
        if(isNetworkAvailable()){
            API_updateMortalityAndSales();
        }
        Cursor cursor = myDb.getAllDataFromMortandSalesRecords();
        cursor.moveToFirst();
        if(cursor.getCount() == 0){
            Toast.makeText(this, "No data in Mortality and Sales", Toast.LENGTH_SHORT).show();
        }else{
            do{
                Integer breeder_inv_id = cursor.getInt(2);
                if(breeder_inv_id == breeder_id){
                    Mortality_Sales mortalityAndSalesRecords = new Mortality_Sales(cursor.getInt(0), cursor.getString(1),breeder_tag, cursor.getInt(2), cursor.getInt(3), cursor.getInt(4), cursor.getString(5),cursor.getString(6), cursor.getFloat(7), cursor.getInt(8), cursor.getInt(9),cursor.getInt(10),cursor.getString(11),cursor.getString(12), cursor.getString(12));
                    arrayList_temp.add(mortalityAndSalesRecords);
                }


            }while (cursor.moveToNext());
        }


        //dapat may filter pa sa arraylist temp na dapat tung mortality and sales lang ng given breeder tag yung lalabas
        recycler_adapter = new RecyclerAdapter_Mortality_and_Sales(arrayList_temp);
        recyclerView.setAdapter(recycler_adapter);
        recycler_adapter.notifyDataSetChanged();



    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) this.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void API_addMortalityAndSales(RequestParams requestParams){
        APIHelper.addMortalityAndSales("addMortalityAndSales", requestParams, new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response){
                Toast.makeText(getApplicationContext(), "Successfully synced mortality and sales to web", Toast.LENGTH_SHORT).show();
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

    private void API_updateMortalityAndSales(){
        APIHelper.getMortalityAndSales("getMortalityAndSales/", new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response){

                Gson gson = new Gson();
                JSONMortalityAndSales jsonBrooderInventory = gson.fromJson(rawJsonResponse, JSONMortalityAndSales.class);
                ArrayList<Mortality_Sales> arrayListBrooderFeedingWeb = jsonBrooderInventory.getData();


                ArrayList<Mortality_Sales> arrayListBrooderFeedingLocal = new ArrayList<>();

                Cursor cursor_brooder_feeding = myDb.getAllDataFromMortandSalesRecords();
                cursor_brooder_feeding.moveToFirst();
                if(cursor_brooder_feeding.getCount() != 0){
                    do {

                        Mortality_Sales mortalityAndSalesRecords = new Mortality_Sales(cursor_brooder_feeding.getInt(0), cursor_brooder_feeding.getString(1),null, cursor_brooder_feeding.getInt(2), cursor_brooder_feeding.getInt(3), cursor_brooder_feeding.getInt(4), cursor_brooder_feeding.getString(5),cursor_brooder_feeding.getString(6), cursor_brooder_feeding.getFloat(7), cursor_brooder_feeding.getInt(8), cursor_brooder_feeding.getInt(9),cursor_brooder_feeding.getInt(10),cursor_brooder_feeding.getString(11),cursor_brooder_feeding.getString(12), cursor_brooder_feeding.getString(12));
                        arrayListBrooderFeedingLocal.add(mortalityAndSalesRecords);

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

                    Cursor cursor = myDb.getAllDataFromMortandSalesRecordsWithID(id_to_sync.get(i));
                    cursor.moveToFirst();
                    Integer id = cursor.getInt(0);
                    String date = cursor.getString(1);
                    Integer breeder_inventory_id = cursor.getInt(2);
                    Integer replacement_inventory_id = cursor.getInt(3);
                    Integer brooder_inventory_id = cursor.getInt(4);
                    String  type = cursor.getString(5);
                    String  category = cursor.getString(6);
                    Float price = cursor.getFloat(7);
                    Integer male = cursor.getInt(8);
                    Integer female = cursor.getInt(9);
                    Integer total = cursor.getInt(10);
                    String reason = cursor.getString(11);
                    String remarks = cursor.getString(12);



                    RequestParams requestParams = new RequestParams();
                    requestParams.add("id", id.toString());
                    requestParams.add("date", date);
                    requestParams.add("breeder_inventory_id", breeder_inventory_id.toString());
                    requestParams.add("replacement_inventory_id", replacement_inventory_id.toString());
                    requestParams.add("brooder_inventory_id", brooder_inventory_id.toString());
                    requestParams.add("type", type);
                    requestParams.add("category", category);
                    requestParams.add("price", price.toString());
                    requestParams.add("male", male.toString());
                    requestParams.add("female", female.toString());
                    requestParams.add("total", total.toString());
                    requestParams.add("reason", reason);
                    requestParams.add("remarks", remarks);




                    //Toast.makeText(BrooderFeedingRecordsActivity.this, id_to_sync.get(i).toString(), Toast.LENGTH_SHORT).show();

                    API_addMortalityAndSales(requestParams);

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
    @Override
    public boolean onSupportNavigateUp() {
        Intent intent_brooders = new Intent(MortalityAndSalesRecords.this, CreateBreeders.class);
        startActivity(intent_brooders);
        return true;
    }

    @Override
    public void onBackPressed() {


        Intent intent_brooders = new Intent(MortalityAndSalesRecords.this, CreateBreeders.class);
        startActivity(intent_brooders);

    }
}
