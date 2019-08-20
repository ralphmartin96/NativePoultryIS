package com.example.cholomanglicmot.nativechickenandduck.ReplacementsDirectory;

import android.app.AlertDialog;
import android.content.Context;
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
import com.example.cholomanglicmot.nativechickenandduck.BreedersDirectory.Breeder_PhenoMorphoView;
import com.example.cholomanglicmot.nativechickenandduck.DatabaseHelper;
import com.example.cholomanglicmot.nativechickenandduck.R;
import com.google.gson.Gson;
import com.loopj.android.http.BaseJsonHttpResponseHandler;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class ReplacementPhenoMorphoViewActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private TextView replacement_pheno_inv_id;

    DatabaseHelper myDb;
    RecyclerView recyclerView;
    RecyclerView.Adapter recycler_adapter;
    RecyclerView.LayoutManager layoutManager;

    ArrayList<Replacement_Inventory> arrayListReplacementInventory = new ArrayList<>();
    ArrayList<Replacement_Inventory> arrayListReplacementInventory1 = new ArrayList<>();
    ArrayList<Replacements> arrayListReplacements = new ArrayList<>();
    ArrayList<Replacement_PhenoMorphoView>arrayList_temp = new ArrayList<>();
    ArrayList<Replacement_PhenoMorphoView>arrayListPhenoMorpho = new ArrayList<>();
    ArrayList<Integer>arrayListValuesID = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_replacement_pheno_morpho_view);
StringBuffer buffer = new StringBuffer();
        final String replacement_inv_tag, replacement_pen;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                replacement_inv_tag = null;
                replacement_pen = null;
            } else {
                replacement_inv_tag = extras.getString("Replacement Tag");
                replacement_pen = extras.getString("Replacement Pen");

            }
        } else {
            replacement_inv_tag = (String) savedInstanceState.getSerializable("Replacement Tag");
            replacement_pen = (String) savedInstanceState.getSerializable("Replacement Pen");
        }




        final Bundle args = new Bundle();
        args.putString("Replacement Pen", replacement_inv_tag);
        replacement_pheno_inv_id = findViewById(R.id.replacement_pheno_inv_id);
        replacement_pheno_inv_id.setText("Phenotypic & Morphometric | " + replacement_inv_tag);
        mToolbar = findViewById(R.id.nav_action);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Replacement Phenotypic & Morpometric Records");
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        myDb = new DatabaseHelper(this);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewReplacementViewPhenoMorphoRecords);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        if(isNetworkAvailable()){
            API_getPhenoMorphoValues();
            API_getPhenoMorphos();
        }

        //////////////////////////DATABASE

        ////inventory
/*        Cursor cursor_inventory = myDb.getAllDataFromReplacementInventory();
        cursor_inventory.moveToFirst();

        if (cursor_inventory.getCount() == 0) {

        } else {
            do {
                Replacement_Inventory replacement_inventory = new Replacement_Inventory(cursor_inventory.getInt(0), cursor_inventory.getInt(1), null,null,null,cursor_inventory.getString(2), cursor_inventory.getString(3), cursor_inventory.getString(4), cursor_inventory.getInt(5), cursor_inventory.getInt(6), cursor_inventory.getInt(7), null, cursor_inventory.getString(8), cursor_inventory.getString(9));
                arrayListReplacementInventory.add(replacement_inventory);
            } while (cursor_inventory.moveToNext());
        }
*//*getAlLDataFromPhenoMorphoRecords*/

        Cursor cursor_inv = myDb.getIDFromReplacementInventoyWhereTag(replacement_inv_tag);
        cursor_inv.moveToFirst();

        Cursor cursor_view = myDb.getDataFromReplacementPhenoMorphosWhere(cursor_inv.getInt(0));
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

            Replacement_PhenoMorphoView replacement_phenoMorphoView = new Replacement_PhenoMorphoView(cursor_values.getInt(0), cursor_values.getString(1), cursor_values.getString(2),cursor_values.getString(3),cursor_values.getString(4),cursor_values.getString(5),cursor_values.getString(6) );
            arrayListPhenoMorpho.add(replacement_phenoMorphoView);
        }
        ArrayList<Replacement_PhenoMorphoView> arrayList = new ArrayList<>();
        for(int i=0;i<arrayListPhenoMorpho.size();i++){
            if(arrayListPhenoMorpho.get(i).getDeleted_at() == null){
                arrayList.add(arrayListPhenoMorpho.get(i));
            }
        }

        recycler_adapter = new RecyclerAdapter_Replacement_PhenoMorphoView(arrayList);
        recyclerView.setAdapter(recycler_adapter);
        recycler_adapter.notifyDataSetChanged();














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
                        Toast.makeText(ReplacementPhenoMorphoViewActivity.this, "Pheno and Morphos Added", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(ReplacementPhenoMorphoViewActivity.this, "Pheno and Morphos Values Added", Toast.LENGTH_SHORT).show();
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
}
