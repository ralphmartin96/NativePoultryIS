package com.example.cholomanglicmot.nativechickenandduck.BroodersDirectory;

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
import com.example.cholomanglicmot.nativechickenandduck.BreedersDirectory.Breeder_Inventory;
import com.example.cholomanglicmot.nativechickenandduck.BreedersDirectory.Egg_Production;
import com.example.cholomanglicmot.nativechickenandduck.BreedersDirectory.Mortality_Sales;
import com.example.cholomanglicmot.nativechickenandduck.BreedersDirectory.RecyclerAdapter_Mortality_and_Sales;
import com.example.cholomanglicmot.nativechickenandduck.DatabaseHelper;
import com.example.cholomanglicmot.nativechickenandduck.R;
import com.google.gson.Gson;
import com.loopj.android.http.BaseJsonHttpResponseHandler;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MortalityAndSalesRecordsBrooder extends AppCompatActivity {
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
    ArrayList<Mortality_Sales> arrayList_MortalitySales = new ArrayList<>();
    ImageButton create_egg_prod;
    TextView replacement_pheno_inv_id;
    Integer brooder_pen;
    Integer brooder_id;
    String brooder_pen_number, brooder_tag;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mortality_and_sales);

        final Integer brooder_id;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                brooder_tag = null;
                brooder_id = null;
                brooder_pen = null;
            } else {
                brooder_tag = extras.getString("Brooder Tag");
                brooder_id = extras.getInt("Brooder ID");
                brooder_pen = extras.getInt("Brooder Pen ID");
            }
        } else {
            brooder_tag = (String) savedInstanceState.getSerializable("Brooder Tag");
            brooder_id = (Integer) savedInstanceState.getSerializable("Brooder ID");
            brooder_pen = (Integer) savedInstanceState.getSerializable("Brooder Pen ID");
        }
        final Bundle args = new Bundle();
        args.putString("Brooder Tag", brooder_tag);
        args.putInt("Brooder ID", brooder_id);
        args.putInt("Brooder Pen ID", brooder_pen);
        replacement_pheno_inv_id = findViewById(R.id.replacement_pheno_inv_id);
        replacement_pheno_inv_id.setText("Mortality and Sales | " + brooder_tag);
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
                CreateMortalityAndSalesBrooderDialog newFragment = new CreateMortalityAndSalesBrooderDialog();
                newFragment.setArguments(args);
                newFragment.show(getSupportFragmentManager(), "CreateMortalityAndSalesBrooderDialog");
            }
        });



        myDb = new DatabaseHelper(this);

        Integer brooder_inventory_id;

        brooder_inventory_id = myDb.getIDFromBrooderInventoryWhereTag(brooder_tag);

        Cursor cursor = myDb.getAllDataForBrooderMortalitySalesWhereInvId(brooder_inventory_id);
        cursor.moveToFirst();

        if(cursor.getCount() == 0){
            Toast.makeText(this, "No data in Mortality and Sales", Toast.LENGTH_SHORT).show();
        }else{

            do{
                Mortality_Sales mortalityAndSalesRecords = new Mortality_Sales(
                        cursor.getInt(0),
                        cursor.getString(1),
                        brooder_tag,
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

            }while (cursor.moveToNext());
        }

        recycler_adapter = new RecyclerAdapter_Mortality_and_Sales(arrayList_MortalitySales);
        recyclerView.setAdapter(recycler_adapter);
        recycler_adapter.notifyDataSetChanged();

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void API_getMortalityAndSales(){
        APIHelper.getMortalityAndSales("getMortalityAndSales/", new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response){

                Gson gson = new Gson();
                JSONMortalityAndSales jsonBrooder = gson.fromJson(rawJsonResponse, JSONMortalityAndSales.class);
                ArrayList<Mortality_Sales> arrayList_brooder = jsonBrooder.getData();

                for (int i = 0; i < arrayList_brooder.size(); i++) {
                    //check if generation to be inserted is already in the database
                    Cursor cursor = myDb.getAllDataFromMortandSalesRecordsWithID(arrayList_brooder.get(i).getId());
                    cursor.moveToFirst();

                    if (cursor.getCount() == 0) {


//                        boolean isInserted = myDb.insertDataMortalityAndSalesWithID(arrayList_brooder.get(i).getId(), arrayList_brooder.get(i).getDate(), arrayList_brooder.get(i).getbrooder_id(), arrayList_brooder.get(i).getReplaement_id(), arrayList_brooder.get(i).getBrooder_id(), arrayList_brooder.get(i).getType(), arrayList_brooder.get(i).getCategory(), arrayList_brooder.get(i).getPrice(), arrayList_brooder.get(i).getMale_count(), arrayList_brooder.get(i).getFemale_count(), arrayList_brooder.get(i).getTotal(), arrayList_brooder.get(i).getReason(), arrayList_brooder.get(i).getRemarks(), arrayList_brooder.get(i).getDeleted_at());
                        Toast.makeText(MortalityAndSalesRecordsBrooder.this, "Mortality and Sales Added", Toast.LENGTH_SHORT).show();
                    }

                }



            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonResponse, Object response){

                //Toast.makeText(getApplicationContext(), "Failed to fetch Mortality and Sales from web ", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable{
                return null;
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        Intent intent_brooders = new Intent(MortalityAndSalesRecordsBrooder.this, BrooderInventoryActivity.class);
        intent_brooders.putExtra("Brooder Pen",brooder_pen_number);
        intent_brooders.putExtra("Brooder Tag", brooder_tag);
        intent_brooders.putExtra("Brooder ID", brooder_id);
        startActivity(intent_brooders);
        return true;
    }

    @Override
    public void onBackPressed() {


        Intent intent_brooders = new Intent(MortalityAndSalesRecordsBrooder.this, BrooderInventoryActivity.class);
        intent_brooders.putExtra("Brooder Pen",brooder_pen_number);
        intent_brooders.putExtra("Brooder Tag", brooder_tag);
        intent_brooders.putExtra("Brooder ID", brooder_id);
        startActivity(intent_brooders);

    }
}
