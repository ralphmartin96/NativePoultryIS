package com.example.cholomanglicmot.nativechickenandduck.BreedersDirectory;

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

import com.example.cholomanglicmot.nativechickenandduck.APIHelper;
import com.example.cholomanglicmot.nativechickenandduck.DatabaseHelper;
import com.example.cholomanglicmot.nativechickenandduck.R;
import com.example.cholomanglicmot.nativechickenandduck.ReplacementsDirectory.JSONPhenoMorphoValues;
import com.example.cholomanglicmot.nativechickenandduck.ReplacementsDirectory.JSONPhenoMorphos;
import com.example.cholomanglicmot.nativechickenandduck.ReplacementsDirectory.Pheno_Morphos;
import com.google.gson.Gson;
import com.loopj.android.http.BaseJsonHttpResponseHandler;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class BreederPhenoMorphoViewActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private TextView replacement_pheno_inv_id;

    DatabaseHelper myDb;
    RecyclerView recyclerView;
    RecyclerView.Adapter recycler_adapter;
    RecyclerView.LayoutManager layoutManager;
    String replacement_inv_tag;
    Integer replacement_pen;
    ArrayList<Breeder_Inventory> arrayListReplacementInventory = new ArrayList<>();
    ArrayList<Breeder_Inventory> arrayListReplacementInventory1 = new ArrayList<>();
    ArrayList<Breeders> arrayListReplacements = new ArrayList<>();
    ArrayList<Breeder_PhenoMorphoView>arrayList_temp = new ArrayList<>();
    ArrayList<Breeder_PhenoMorphoView>arrayListPhenoMorpho = new ArrayList<>();
    ArrayList<Integer>arrayListValuesID = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_replacement_pheno_morpho_view);
StringBuffer buffer = new StringBuffer();

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                replacement_inv_tag = null;
                replacement_pen = null;
            } else {
                replacement_inv_tag = extras.getString("Replacement Tag");
                replacement_pen = extras.getInt("Replacement Pen");

            }
        } else {
            replacement_inv_tag = (String) savedInstanceState.getSerializable("Replacement Tag");
            replacement_pen = (Integer) savedInstanceState.getSerializable("Replacement Pen");
        }




        final Bundle args = new Bundle();
        args.putString("Breeder Pen", replacement_inv_tag);
        replacement_pheno_inv_id = findViewById(R.id.replacement_pheno_inv_id);
        replacement_pheno_inv_id.setText("Phenotypic & Morphometric | " + replacement_inv_tag);
        mToolbar = findViewById(R.id.nav_action);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Breeder Phenotypic & Morpometric Records");
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        myDb = new DatabaseHelper(this);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewReplacementViewPhenoMorphoRecords);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);



        //////////////////////////DATABASE
        if(isNetworkAvailable()){
            API_getPhenoMorphos();
            API_getPhenoMorphoValues();
        }

        Cursor cursor_inv = myDb.getIDFromBreederInventoyWhereTag(replacement_inv_tag);
        cursor_inv.moveToFirst();

        Cursor cursor_view = myDb.getDataFromBreederPhenoMorphosWhere(cursor_inv.getInt(0));
        cursor_view.moveToFirst();

        if(cursor_view.getCount() == 0){
            Toast.makeText(this, "No records yet", Toast.LENGTH_SHORT).show();
        }else{
            do{
                arrayListValuesID.add(cursor_view.getInt(0));

            }while(cursor_view.moveToNext());
        }


        for(int i=0; i<arrayListValuesID.size();i++){
            Cursor cursor_values = myDb.getDataFromPhenoMorphoValuesWhereValuesID(arrayListValuesID.get(i));
            cursor_values.moveToFirst();

            Breeder_PhenoMorphoView breeder_phenoMorphoView = new Breeder_PhenoMorphoView(cursor_values.getInt(0), cursor_values.getString(1), cursor_values.getString(2),cursor_values.getString(3),cursor_values.getString(4),cursor_values.getString(5),cursor_values.getString(6) );
            arrayListPhenoMorpho.add(breeder_phenoMorphoView);
        }

        ArrayList<Breeder_PhenoMorphoView> arrayList = new ArrayList<>();
        for(int i=0;i<arrayListPhenoMorpho.size();i++){
            if(arrayListPhenoMorpho.get(i).getDeleted_at() == null){
                arrayList.add(arrayListPhenoMorpho.get(i));
            }
        }



        recycler_adapter = new RecyclerAdapter_Breeder_PhenoMorphoView(arrayList);
        recyclerView.setAdapter(recycler_adapter);
        recycler_adapter.notifyDataSetChanged();














    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    private void API_getPhenoMorphos(){
        APIHelper.getPhenoMorphos("getPhenoMorphos/", new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response){

                Gson gson = new Gson();
                JSONPhenoMorphos jsonReplacement = gson.fromJson(rawJsonResponse, JSONPhenoMorphos.class);
                ArrayList <Pheno_Morphos> arrayList_brooder = jsonReplacement.getData();

                for (int i = 0; i < arrayList_brooder.size(); i++) {
                    //check if generation to be inserted is already in the database
                    Cursor cursor = myDb.getDataFromReplacementPhenoMorphosWhereID(arrayList_brooder.get(i).getId());
                    cursor.moveToFirst();

                    if (cursor.getCount() == 0) {


                        boolean isInserted = myDb.insertPhenoMorphosWithID(arrayList_brooder.get(i).getId(), arrayList_brooder.get(i).getReplacement_inventory(), arrayList_brooder.get(i).getBreeder_inventory(), arrayList_brooder.get(i).getValues_id(), arrayList_brooder.get(i).getDeleted_at());
                        Toast.makeText(BreederPhenoMorphoViewActivity.this, "Pheno and Morphos Added", Toast.LENGTH_SHORT).show();
                    }

                }



            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonResponse, Object response){

                Toast.makeText(getApplicationContext(), "Failed to fetch pheno and morphos web database ", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable{
                return null;
            }
        });
    }
    private void API_getPhenoMorphoValues(){
        APIHelper.getPhenoMorphoValues("getPhenoMorphoValues/", new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response){

                Gson gson = new Gson();
                JSONPhenoMorphoValues jsonReplacement = gson.fromJson(rawJsonResponse, JSONPhenoMorphoValues.class);
                ArrayList <Breeder_PhenoMorphoView> arrayList_brooder = jsonReplacement.getData();

                for (int i = 0; i < arrayList_brooder.size(); i++) {
                    //check if generation to be inserted is already in the database
                    Cursor cursor = myDb.getAllDataFromPhenoMorphoRecordsWithID(arrayList_brooder.get(i).getId());
                    cursor.moveToFirst();

                    if (cursor.getCount() == 0) {


                        boolean isInserted = myDb.insertPhenoMorphoRecordsWithID(arrayList_brooder.get(i).getId(), arrayList_brooder.get(i).getGender(), arrayList_brooder.get(i).getTag(), arrayList_brooder.get(i).getPheno_record(), arrayList_brooder.get(i).getMorpho_record(), arrayList_brooder.get(i).getDate(), arrayList_brooder.get(i).getDeleted_at());
                        Toast.makeText(BreederPhenoMorphoViewActivity.this, "Pheno and Morphos Values Added", Toast.LENGTH_SHORT).show();
                    }

                }



            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonResponse, Object response){

                Toast.makeText(getApplicationContext(), "Failed to fetch pheno and morphos values web database ", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable{
                return null;
            }
        });
    }
    public void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
    @Override
    public boolean onSupportNavigateUp() {
        Intent intent_replacement_pheno_morpho_records = new Intent(BreederPhenoMorphoViewActivity.this, BreederPhenoMorphoRecordsActivity.class);
        intent_replacement_pheno_morpho_records.putExtra("Replacement Pen",replacement_pen);
        intent_replacement_pheno_morpho_records.putExtra("Breeder Tag",replacement_inv_tag);
        startActivity(intent_replacement_pheno_morpho_records);
        return true;
    }

    @Override
    public void onBackPressed() {


        Intent intent_replacement_pheno_morpho_records = new Intent(BreederPhenoMorphoViewActivity.this, BreederPhenoMorphoRecordsActivity.class);
        intent_replacement_pheno_morpho_records.putExtra("Replacement Pen",replacement_pen);
        intent_replacement_pheno_morpho_records.putExtra("Breeder Tag",replacement_inv_tag);
        startActivity(intent_replacement_pheno_morpho_records);

    }
}
