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
import com.example.cholomanglicmot.nativechickenandduck.DatabaseHelper;
import com.example.cholomanglicmot.nativechickenandduck.R;
import com.google.gson.Gson;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class EggQualityRecords extends AppCompatActivity {
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
    ArrayList<Egg_Quality> arrayListEggQualityRecords = new ArrayList<>();
    ArrayList<Breeder_Inventory>arrayList_temp = new ArrayList<>();
    ImageButton create_egg_prod;
    TextView replacement_pheno_inv_id;
    String breeder_tag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_egg_quality_records);

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
        replacement_pheno_inv_id.setText("Egg Quality Records | "+ breeder_tag);
        mToolbar = findViewById(R.id.nav_action);
        create_egg_prod = findViewById(R.id.open_dialog);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Breeder Egg Quality");
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        create_egg_prod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateEggQualityDialog newFragment = new CreateEggQualityDialog();
                newFragment.setArguments(args);
                newFragment.show(getSupportFragmentManager(), "CreateEggQualityDialog");
            }
        });

        myDb = new DatabaseHelper(this);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerViewEggQualityRecords);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        local_getEggQuality();

        recycler_adapter = new RecyclerAdapter_Egg_Quality(arrayListEggQualityRecords);
        recyclerView.setAdapter(recycler_adapter);
        recycler_adapter.notifyDataSetChanged();

    }

    private void local_getEggQuality() {

        Cursor cur = myDb.getDataFromBreederInvWhereTag(breeder_tag);
        cur.moveToFirst();
        Integer inventory_id = cur.getInt(0);

        Cursor cursor_egg_quality = myDb.getAllDataFromEggQuality();
        cursor_egg_quality.moveToFirst();

        if (cursor_egg_quality.getCount() != 0) {

            do {
                String deleted_at = cursor_egg_quality.getString(17);

                if (inventory_id.equals(cursor_egg_quality.getInt(1)) && deleted_at == null) {
                    Egg_Quality egg_quality = new Egg_Quality(
                            cursor_egg_quality.getInt(0),
                            breeder_tag,
                            cursor_egg_quality.getInt(1),
                            cursor_egg_quality.getString(2),
                            cursor_egg_quality.getInt(3),
                            cursor_egg_quality.getFloat(4),
                            cursor_egg_quality.getString(5),
                            cursor_egg_quality.getString(6),
                            cursor_egg_quality.getFloat(7),
                            cursor_egg_quality.getFloat(8),
                            cursor_egg_quality.getFloat(9),
                            cursor_egg_quality.getFloat(10),
                            cursor_egg_quality.getFloat(11),
                            cursor_egg_quality.getString(12),
                            cursor_egg_quality.getFloat(13),
                            cursor_egg_quality.getFloat(14),
                            cursor_egg_quality.getFloat(15),
                            cursor_egg_quality.getFloat(16)
                    );

                    arrayListEggQualityRecords.add(egg_quality);
                }
            } while (cursor_egg_quality.moveToNext());

            if (arrayListEggQualityRecords.isEmpty())
                Toast.makeText(this, "No data.", Toast.LENGTH_LONG).show();

        }

    }

    private void API_addEggQuality(RequestParams requestParams){
        APIHelper.addEggQuality("addEggQuality", requestParams, new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response){
                Toast.makeText(getApplicationContext(), "Successfully synced egg quality to web", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonResponse, Object response){

               // Toast.makeText(getActivity(), "Failed to add egg quality to web", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable{
                return null;
            }
        });
    }

    private void API_updateEggQuality(){
        APIHelper.getEggQuality("getEggQuality/", new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response){

                Gson gson = new Gson();
                JSONEggQuality jsonBrooderInventory = gson.fromJson(rawJsonResponse, JSONEggQuality.class);
                ArrayList<Egg_Quality> arrayListBrooderFeedingWeb = jsonBrooderInventory.getData();


                ArrayList<Egg_Quality> arrayListBrooderFeedingLocal = new ArrayList<>();

                Cursor cursor_brooder_feeding = myDb.getAllDataFromEggQuality();
                cursor_brooder_feeding.moveToFirst();
                if(cursor_brooder_feeding.getCount() != 0){
                    do {

                        Egg_Quality egg_quality = new Egg_Quality(cursor_brooder_feeding.getInt(0), null, cursor_brooder_feeding.getInt(1), cursor_brooder_feeding.getString(2), cursor_brooder_feeding.getInt(3), cursor_brooder_feeding.getFloat(4), cursor_brooder_feeding.getString(5), cursor_brooder_feeding.getString(6), cursor_brooder_feeding.getFloat(7), cursor_brooder_feeding.getFloat(8), cursor_brooder_feeding.getFloat(9), cursor_brooder_feeding.getFloat(10), cursor_brooder_feeding.getFloat(11), cursor_brooder_feeding.getString(12), cursor_brooder_feeding.getFloat(13), cursor_brooder_feeding.getFloat(14), cursor_brooder_feeding.getFloat(15), cursor_brooder_feeding.getFloat(16));
                        arrayListBrooderFeedingLocal.add(egg_quality);

                    } while (cursor_brooder_feeding.moveToNext());
                }

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

                    Cursor cursor = myDb.getAllDataFromBreederEggQualWhereID(id_to_sync.get(i));
                    cursor.moveToFirst();
                    Integer id = cursor.getInt(0);
                    Integer breeder_inventory_id = cursor.getInt(1);
                    String date_collected = cursor.getString(2);
                    Integer egg_quality_at = cursor.getInt(3);
                    Float weight = cursor.getFloat(4);
                    String  color = cursor.getString(5);
                    String  shape = cursor.getString(6);
                    Float length = cursor.getFloat(7);
                    Float width = cursor.getFloat(8);
                    Float albumen_height = cursor.getFloat(9);
                    Float albumen_weight = cursor.getFloat(10);
                    Float yolk_weight = cursor.getFloat(11);
                    String  yolk_color = cursor.getString(12);
                    Float shell_weight = cursor.getFloat(13);
                    Float thickness_top = cursor.getFloat(14);
                    Float thickness_mid = cursor.getFloat(14);
                    Float thickness_bot = cursor.getFloat(16);
                    String deleted_at = cursor.getString(17);



                    RequestParams requestParams = new RequestParams();
                    requestParams.add("id", id.toString());
                    requestParams.add("breeder_inventory_id", breeder_inventory_id.toString());
                    requestParams.add("date_collected", date_collected);
                    requestParams.add("egg_quality_at", egg_quality_at.toString());
                    requestParams.add("weight", weight.toString());
                    requestParams.add("color", color);
                    requestParams.add("shape", shape);
                    requestParams.add("length", length.toString());
                    requestParams.add("width", width.toString());
                    requestParams.add("albumen_height", albumen_height.toString());
                    requestParams.add("albumen_weight", albumen_weight.toString());
                    requestParams.add("yolk_weight", yolk_weight.toString());
                    requestParams.add("yolk_color", yolk_color);
                    requestParams.add("shell_weight", shell_weight.toString());
                    requestParams.add("thickness_top", thickness_top.toString());
                    requestParams.add("thickness_mid", thickness_mid.toString());
                    requestParams.add("thickness_bot", thickness_bot.toString());
                    requestParams.add("deleted_at", deleted_at);




                    //Toast.makeText(BrooderFeedingRecordsActivity.this, id_to_sync.get(i).toString(), Toast.LENGTH_SHORT).show();

                    API_addEggQuality(requestParams);

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

    private void API_getEggQuality(){
        APIHelper.getEggQuality("getEggQuality/", new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response){

                Gson gson = new Gson();
                JSONEggQuality jsonBreeder = gson.fromJson(rawJsonResponse, JSONEggQuality.class);
                ArrayList<Egg_Quality> arrayList_brooder = jsonBreeder.getData();

                for (int i = 0; i < arrayList_brooder.size(); i++) {
                    //check if generation to be inserted is already in the database
                    Cursor cursor = myDb.getAllDataFromBreederEggQualWhereID(arrayList_brooder.get(i).getId());
                    cursor.moveToFirst();

                    if (cursor.getCount() == 0) {



                            boolean isInserted = myDb.insertEggQualityRecordsWithID(arrayList_brooder.get(i).getId(), arrayList_brooder.get(i).getEgg_breeder_inv_id(),arrayList_brooder.get(i).getDate(), arrayList_brooder.get(i).getWeek(), arrayList_brooder.get(i).getWeight(), arrayList_brooder.get(i).getColor(), arrayList_brooder.get(i).getShape(), arrayList_brooder.get(i).getLength(), arrayList_brooder.get(i).getWidth(), arrayList_brooder.get(i).getAlbument_height(), arrayList_brooder.get(i).getAlbument_weight(), arrayList_brooder.get(i).getYolk_weight(), arrayList_brooder.get(i).getYolk_color(), arrayList_brooder.get(i).getShell_weight(), arrayList_brooder.get(i).getShell_thickness_top(), arrayList_brooder.get(i).getShell_thickness_middle(), arrayList_brooder.get(i).getShell_thickness_bottom());
                        Toast.makeText(EggQualityRecords.this, "Egg Qualities Added", Toast.LENGTH_SHORT).show();
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

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public boolean onSupportNavigateUp() {
        Intent intent_brooders = new Intent(EggQualityRecords.this, CreateBreeders.class);
        startActivity(intent_brooders);
        return true;
    }

    @Override
    public void onBackPressed() {


        Intent intent_brooders = new Intent(EggQualityRecords.this, CreateBreeders.class);
        startActivity(intent_brooders);

    }
}
