package com.example.cholomanglicmot.nativechickenandduck.ReplacementsDirectory;

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
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cholomanglicmot.nativechickenandduck.APIHelperAsync;
import com.example.cholomanglicmot.nativechickenandduck.BreedersDirectory.Mortality_Sales;
import com.example.cholomanglicmot.nativechickenandduck.BreedersDirectory.RecyclerAdapter_Mortality_and_Sales;
import com.example.cholomanglicmot.nativechickenandduck.BroodersDirectory.JSONMortalityAndSales;
import com.example.cholomanglicmot.nativechickenandduck.DatabaseHelper;
import com.example.cholomanglicmot.nativechickenandduck.R;
import com.google.gson.Gson;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MortalityAndSalesRecordsReplacement extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private Toolbar mToolbar;


    DatabaseHelper myDb;
    RecyclerView recyclerView;
    RecyclerView.Adapter recycler_adapter;
    RecyclerView.LayoutManager layoutManager;

    ArrayList<Mortality_Sales> arrayList_MortalitySales = new ArrayList<>();
    ImageButton create_egg_prod;
    TextView replacement_pheno_inv_id;
    Integer brooder_pen;
    Integer breeder_id;
    Integer breeder_inventory_id;
    String brooder_pen_number, breeder_tag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mortality_and_sales);

        final Integer breeder_id;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                breeder_tag= null;
                breeder_id=null;
                brooder_pen = null;
            } else {
                breeder_tag= extras.getString("Brooder Tag");
                breeder_id=extras.getInt("Brooder ID");
                brooder_pen = extras.getInt("Brooder Pen ID");
            }
        } else {
            breeder_tag= (String) savedInstanceState.getSerializable("Brooder Tag");
            breeder_id = (Integer) savedInstanceState.getSerializable("Brooder ID");
            brooder_pen = (Integer) savedInstanceState.getSerializable("Brooder Pen ID");
        }
        final Bundle args = new Bundle();
        args.putString("Brooder Tag",breeder_tag);
        args.putInt("Brooder ID", breeder_id);
        args.putInt("Brooder Pen ID", brooder_pen);
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
                CreateMortalityAndSalesReplacementDialog newFragment = new CreateMortalityAndSalesReplacementDialog();
                newFragment.setArguments(args);
                newFragment.show(getSupportFragmentManager(), "CreateMortalityAndSalesReplacementDialog");
            }
        });



        myDb = new DatabaseHelper(this);

        Log.d("POULTRYDEBUGGER", "" + breeder_tag);


        breeder_inventory_id = myDb.getIDFromReplacementInventoryWhereTag(breeder_tag);


        local_getMortalityAndSales();

        recycler_adapter = new RecyclerAdapter_Mortality_and_Sales(arrayList_MortalitySales);
        recyclerView.setAdapter(recycler_adapter);
        recycler_adapter.notifyDataSetChanged();
    }

    private void local_getMortalityAndSales() {
        Cursor cursor = myDb.getAllDataForReplacementMortalitySalesWhereInvId(breeder_inventory_id);
        cursor.moveToFirst();

        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No data in Mortality and Sales", Toast.LENGTH_SHORT).show();
        } else {
            do {
                Mortality_Sales mortalityAndSalesRecords = new Mortality_Sales(
                        cursor.getInt(0),
                        cursor.getString(1),
                        breeder_tag,
                        cursor.getInt(2),
                        cursor.getInt(3),
                        cursor.getInt(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getFloat(7),
                        cursor.getInt(8),
                        cursor.getInt(9),
                        cursor.getInt(10),
                        cursor.getString(11),
                        cursor.getString(12),
                        cursor.getString(12)
                );
                arrayList_MortalitySales.add(mortalityAndSalesRecords);

            } while (cursor.moveToNext());
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) this.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void API_addMortalityAndSales(RequestParams requestParams){
        APIHelperAsync.addMortalityAndSales("addMortalityAndSales", requestParams, new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response){
                Toast.makeText(getApplicationContext(), "Successfully synced mortality and sales to web", Toast.LENGTH_SHORT).show();
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

    private void API_updateMortalityAndSales(){
        APIHelperAsync.getMortalityAndSales("getMortalityAndSales/", new BaseJsonHttpResponseHandler<Object>() {
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

            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable{
                return null;
            }
        });
    }
    @Override
    public boolean onSupportNavigateUp() {
        Intent intent_brooders = new Intent(MortalityAndSalesRecordsReplacement.this, ReplacementInventoryActivity.class);
        intent_brooders.putExtra("Replacement Pen",brooder_pen_number);

        intent_brooders.putExtra("Replacement Tag",breeder_tag);
        intent_brooders.putExtra("Replacement ID",breeder_id);
        startActivity(intent_brooders);
        return true;
    }

    @Override
    public void onBackPressed() {


        Intent intent_brooders = new Intent(MortalityAndSalesRecordsReplacement.this, ReplacementInventoryActivity.class);
        intent_brooders.putExtra("Replacement Pen",brooder_pen_number);
        intent_brooders.putExtra("Replacement Tag",breeder_tag);
        intent_brooders.putExtra("Replacement ID",breeder_id);
        startActivity(intent_brooders);

    }
}
