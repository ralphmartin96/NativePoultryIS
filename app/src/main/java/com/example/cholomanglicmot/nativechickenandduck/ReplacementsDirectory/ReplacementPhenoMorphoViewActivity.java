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

import com.example.cholomanglicmot.nativechickenandduck.DatabaseHelper;
import com.example.cholomanglicmot.nativechickenandduck.R;

import java.util.ArrayList;

public class ReplacementPhenoMorphoViewActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private TextView replacement_pheno_inv_id;
    String replacement_inv_tag, replacement_pen;
    Integer replacement_inv_id;

    DatabaseHelper myDb;
    RecyclerView recyclerView;
    RecyclerView.Adapter recycler_adapter;
    RecyclerView.LayoutManager layoutManager;

    ArrayList<Replacement_Inventory> arrayListReplacementInventory = new ArrayList<>();
    ArrayList<Replacement_PhenoMorphoView> arrayListPhenoMorpho = new ArrayList<>();

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

        local_getReplacementPhenoMorphos();

        recycler_adapter = new RecyclerAdapter_Replacement_PhenoMorphoView(arrayListPhenoMorpho);
        recyclerView.setAdapter(recycler_adapter);
        recycler_adapter.notifyDataSetChanged();

    }

    private void local_getReplacementPhenoMorphos() {

        Cursor cursor_inv = myDb.getIDFromReplacementInventoyWhereTag(replacement_inv_tag);
        cursor_inv.moveToFirst();

        replacement_inv_id = cursor_inv.getInt(0);

        Cursor cursor_view = myDb.getDataFromReplacementPhenoMorphosWhere(replacement_inv_id);
        cursor_view.moveToFirst();

        if (cursor_view.getCount() == 0) {
            Toast.makeText(this, "No records yet", Toast.LENGTH_SHORT).show();
        } else {

            do {
                int values_id = cursor_view.getInt(0);

                Cursor cursor_values = myDb.getDataFromPhenoMorphoValuesWhereValuesID(values_id);
                cursor_values.moveToFirst();

                Replacement_PhenoMorphoView replacement_phenoMorphoView = new Replacement_PhenoMorphoView(
                        cursor_values.getInt(0),
                        cursor_values.getString(1),
                        cursor_values.getString(2),
                        cursor_values.getString(3),
                        cursor_values.getString(4),
                        cursor_values.getString(5),
                        cursor_values.getString(6)
                );

                if (replacement_phenoMorphoView.getDeleted_at() == null)
                    arrayListPhenoMorpho.add(replacement_phenoMorphoView);

            } while (cursor_view.moveToNext());
        }


    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}
