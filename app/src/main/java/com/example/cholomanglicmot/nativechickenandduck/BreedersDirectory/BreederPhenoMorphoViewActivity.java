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

import com.example.cholomanglicmot.nativechickenandduck.DatabaseHelper;
import com.example.cholomanglicmot.nativechickenandduck.R;

import java.util.ArrayList;

public class BreederPhenoMorphoViewActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private TextView replacement_pheno_inv_id;

    DatabaseHelper myDb;
    RecyclerView recyclerView;
    RecyclerView.Adapter recycler_adapter;
    RecyclerView.LayoutManager layoutManager;
    String breeder_inv_tag;
    Integer replacement_pen;
    Integer breeder_inventory_id;

    ArrayList<Breeder_PhenoMorphoView> arrayListPhenoMorpho = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_replacement_pheno_morpho_view);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                breeder_inv_tag = null;
                replacement_pen = null;
            } else {
                breeder_inv_tag = extras.getString("Replacement Tag");
                replacement_pen = extras.getInt("Replacement Pen");

            }
        } else {
            breeder_inv_tag = (String) savedInstanceState.getSerializable("Replacement Tag");
            replacement_pen = (Integer) savedInstanceState.getSerializable("Replacement Pen");
        }

        final Bundle args = new Bundle();
        args.putString("Breeder Pen", breeder_inv_tag);
        replacement_pheno_inv_id = findViewById(R.id.replacement_pheno_inv_id);
        replacement_pheno_inv_id.setText("Phenotypic & Morphometric | " + breeder_inv_tag);
        mToolbar = findViewById(R.id.nav_action);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Breeder Phenotypic & Morpometric Records");
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        myDb = new DatabaseHelper(this);
        recyclerView = findViewById(R.id.recyclerViewReplacementViewPhenoMorphoRecords);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        local_getBreederPhenoMorpho();

        recycler_adapter = new RecyclerAdapter_Breeder_PhenoMorphoView(arrayListPhenoMorpho);
        recyclerView.setAdapter(recycler_adapter);
        recycler_adapter.notifyDataSetChanged();

    }

    private void local_getBreederPhenoMorpho() {
        Cursor cursor_inv = myDb.getIDFromBreederInventoyWhereTag(breeder_inv_tag);
        cursor_inv.moveToFirst();

        breeder_inventory_id = cursor_inv.getInt(0);

        Cursor cursor_view = myDb.getDataFromBreederPhenoMorphosWhere(breeder_inventory_id);
        cursor_view.moveToFirst();

        if (cursor_view.getCount() == 0) {
            Toast.makeText(this, "No records yet", Toast.LENGTH_SHORT).show();
        } else {
            do {
                int values_id = cursor_view.getInt(0);

                Cursor cursor_values = myDb.getDataFromPhenoMorphoValuesWhereValuesID(values_id);
                cursor_values.moveToFirst();

                Breeder_PhenoMorphoView breeder_phenoMorphoView = new Breeder_PhenoMorphoView(
                        cursor_values.getInt(0),
                        cursor_values.getString(1),
                        cursor_values.getString(2),
                        cursor_values.getString(3),
                        cursor_values.getString(4),
                        cursor_values.getString(5),
                        cursor_values.getString(6)
                );

                if (breeder_phenoMorphoView.getDeleted_at() == null)
                    arrayListPhenoMorpho.add(breeder_phenoMorphoView);

            } while (cursor_view.moveToNext());
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
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
        intent_replacement_pheno_morpho_records.putExtra("Breeder Tag", breeder_inv_tag);
        startActivity(intent_replacement_pheno_morpho_records);
        return true;
    }

    @Override
    public void onBackPressed() {


        Intent intent_replacement_pheno_morpho_records = new Intent(BreederPhenoMorphoViewActivity.this, BreederPhenoMorphoRecordsActivity.class);
        intent_replacement_pheno_morpho_records.putExtra("Replacement Pen",replacement_pen);
        intent_replacement_pheno_morpho_records.putExtra("Breeder Tag", breeder_inv_tag);
        startActivity(intent_replacement_pheno_morpho_records);

    }
}
