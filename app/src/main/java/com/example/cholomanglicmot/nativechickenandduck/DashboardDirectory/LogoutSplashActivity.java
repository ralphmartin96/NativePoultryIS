package com.example.cholomanglicmot.nativechickenandduck.DashboardDirectory;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.example.cholomanglicmot.nativechickenandduck.APIHelperAsync;
import com.example.cholomanglicmot.nativechickenandduck.APIHelperSync;
import com.example.cholomanglicmot.nativechickenandduck.BreedersDirectory.Breeder_FeedingRecords;
import com.example.cholomanglicmot.nativechickenandduck.BreedersDirectory.Breeder_Inventory;
import com.example.cholomanglicmot.nativechickenandduck.BreedersDirectory.Breeder_PhenoMorphoView;
import com.example.cholomanglicmot.nativechickenandduck.BreedersDirectory.Breeders;
import com.example.cholomanglicmot.nativechickenandduck.BreedersDirectory.Egg_Production;
import com.example.cholomanglicmot.nativechickenandduck.BreedersDirectory.Egg_Quality;
import com.example.cholomanglicmot.nativechickenandduck.BreedersDirectory.Hatchery_Records;
import com.example.cholomanglicmot.nativechickenandduck.BreedersDirectory.JSONBreeder;
import com.example.cholomanglicmot.nativechickenandduck.BreedersDirectory.JSONBreederFeeding;
import com.example.cholomanglicmot.nativechickenandduck.BreedersDirectory.JSONBreederInventory;
import com.example.cholomanglicmot.nativechickenandduck.BreedersDirectory.JSONEggProduction;
import com.example.cholomanglicmot.nativechickenandduck.BreedersDirectory.JSONEggQuality;
import com.example.cholomanglicmot.nativechickenandduck.BreedersDirectory.JSONHatchery;
import com.example.cholomanglicmot.nativechickenandduck.BreedersDirectory.Mortality_Sales;
import com.example.cholomanglicmot.nativechickenandduck.BroodersDirectory.Brooder_FeedingRecords;
import com.example.cholomanglicmot.nativechickenandduck.BroodersDirectory.Brooder_GrowthRecords;
import com.example.cholomanglicmot.nativechickenandduck.BroodersDirectory.Brooder_Inventory;
import com.example.cholomanglicmot.nativechickenandduck.BroodersDirectory.Brooders;
import com.example.cholomanglicmot.nativechickenandduck.BroodersDirectory.JSONBrooder;
import com.example.cholomanglicmot.nativechickenandduck.BroodersDirectory.JSONBrooderFeeding;
import com.example.cholomanglicmot.nativechickenandduck.BroodersDirectory.JSONBrooderGrowth;
import com.example.cholomanglicmot.nativechickenandduck.BroodersDirectory.JSONBrooderInventory;
import com.example.cholomanglicmot.nativechickenandduck.BroodersDirectory.JSONMortalityAndSales;
import com.example.cholomanglicmot.nativechickenandduck.DatabaseHelper;
import com.example.cholomanglicmot.nativechickenandduck.FamilyDirectory.Family;
import com.example.cholomanglicmot.nativechickenandduck.FamilyDirectory.Family1;
import com.example.cholomanglicmot.nativechickenandduck.FamilyDirectory.JSONFamily;
import com.example.cholomanglicmot.nativechickenandduck.FamilyDirectory.JSONFamily1;
import com.example.cholomanglicmot.nativechickenandduck.GenerationsAndLinesDirectory.Generation;
import com.example.cholomanglicmot.nativechickenandduck.GenerationsAndLinesDirectory.JSONGeneration;
import com.example.cholomanglicmot.nativechickenandduck.GenerationsAndLinesDirectory.JSONLine;
import com.example.cholomanglicmot.nativechickenandduck.GenerationsAndLinesDirectory.Line;
import com.example.cholomanglicmot.nativechickenandduck.PensDirectory.JSONPen;
import com.example.cholomanglicmot.nativechickenandduck.PensDirectory.Pen;
import com.example.cholomanglicmot.nativechickenandduck.ProjectAdapter;
import com.example.cholomanglicmot.nativechickenandduck.R;
import com.example.cholomanglicmot.nativechickenandduck.ReplacementsDirectory.JSONPhenoMorphoValues;
import com.example.cholomanglicmot.nativechickenandduck.ReplacementsDirectory.JSONPhenoMorphos;
import com.example.cholomanglicmot.nativechickenandduck.ReplacementsDirectory.JSONReplacement;
import com.example.cholomanglicmot.nativechickenandduck.ReplacementsDirectory.JSONReplacementFeeding;
import com.example.cholomanglicmot.nativechickenandduck.ReplacementsDirectory.JSONReplacementGrowth;
import com.example.cholomanglicmot.nativechickenandduck.ReplacementsDirectory.JSONReplacementInventory;
import com.example.cholomanglicmot.nativechickenandduck.ReplacementsDirectory.Pheno_Morphos;
import com.example.cholomanglicmot.nativechickenandduck.ReplacementsDirectory.Replacement_FeedingRecords;
import com.example.cholomanglicmot.nativechickenandduck.ReplacementsDirectory.Replacement_GrowthRecords;
import com.example.cholomanglicmot.nativechickenandduck.ReplacementsDirectory.Replacement_Inventory;
import com.example.cholomanglicmot.nativechickenandduck.ReplacementsDirectory.Replacements;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class LogoutSplashActivity extends AppCompatActivity {

    public static final String DATABASE_NAME = "Project.db";

    DatabaseHelper myDb;
    String debugTag = "POULTRYDEBUGGER";
    String farm_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout_splash);

        myDb = new DatabaseHelper(this);

        new FetchStats().execute();

    }

    private class FetchStats extends AsyncTask<String, String, String> {
        JSONObject response;

        @Override
        protected String doInBackground(String... params) {

            updateWebData();
            return null;
        }

        protected void onPostExecute(String result) {
            runOnUiThread(new Runnable() {
                public void run() {

                    Intent intent_settings = new Intent(LogoutSplashActivity.this, LoginActivity.class);
                    startActivity(intent_settings);
                }
            });
        }
    }

    private void updateWebData() {

        farm_id = myDb.getFarmIDFromLocalDb().toString();

        try {
            API_updatePen(farm_id);
            API_updateGeneration(farm_id);
            API_updateLine();
        } catch (Exception e) {
            Log.d(debugTag, "Sync Error");
        }

        getApplicationContext().deleteDatabase(DATABASE_NAME);

    }


    //Pen, Generation, Line and Family
    private void API_updatePen(String farm_id) {

        APIHelperSync.getPen("getPen/" + farm_id, new BaseJsonHttpResponseHandler<Object>() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {

                Gson gson = new Gson();
                JSONPen jsonBrooderInventory = gson.fromJson(rawJsonResponse, JSONPen.class);
                ArrayList<Pen> arrayLisPenWeb = jsonBrooderInventory.getData();
                ArrayList<Pen> arrayLisPenLocal = new ArrayList<>();

                ArrayList<Integer> id_local = new ArrayList<>();
                ArrayList<Integer> id_web = new ArrayList<>();
                ArrayList<Integer> id_to_sync = new ArrayList<>();

                Cursor cursor_pen = myDb.getAllDataFromPen();
                cursor_pen.moveToFirst();

                if (cursor_pen.getCount() != 0) {
                    do {

                        Pen pen = new Pen(
                                cursor_pen.getInt(0), //id
                                cursor_pen.getString(2), //pen number
                                cursor_pen.getString(3), //pen type
                                cursor_pen.getInt(4), //pen inventory
                                cursor_pen.getInt(5), // pen capacity
                                cursor_pen.getInt(1), //farm id
                                cursor_pen.getInt(6) //is active
                        );
                        arrayLisPenLocal.add(pen);
                        id_local.add(pen.getId());

                    } while (cursor_pen.moveToNext());
                }

                for (Pen pen : arrayLisPenWeb) {
                    id_web.add(pen.getId());
                }


                for (int i = 0; i < id_local.size(); i++) {
                    if (!id_web.contains(id_local.get(i))) {
                        id_to_sync.add(id_local.get(i));
                    }
                }

                for (int i : id_to_sync) {

                    Log.d(debugTag, i + "");

                    Cursor cursor = myDb.getAllDataFromPenWhereID(i);
                    cursor.moveToFirst();


                    Integer id = cursor.getInt(0);
                    Integer farm_id = cursor.getInt(1);
                    String number = cursor.getString(2);
                    String type = cursor.getString(3);
                    Integer total_capacity = cursor.getInt(4);
                    Integer current_capacity = cursor.getInt(5);
                    Integer is_active = cursor.getInt(6);

                    RequestParams requestParams = new RequestParams();
                    requestParams.add("id", id.toString());
                    requestParams.add("farm_id", farm_id.toString());
                    requestParams.add("number", number);
                    requestParams.add("type", type);
                    requestParams.add("total_capacity", total_capacity.toString());
                    requestParams.add("current_capacity", current_capacity.toString());
                    requestParams.add("is_active", is_active.toString());

                    API_addPen(requestParams);
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonResponse, Object response) {
                Log.d(debugTag, "Failed to fetch pens from web");
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                return null;
            }
        });
    }
    private void API_addPen(RequestParams requestParams) {
        APIHelperSync.addPen("addPen", requestParams, new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
                Log.d(debugTag, "Successfully synced pen to web");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonResponse, Object response) {
                Log.d(debugTag, "Failed to sync Pen to web");
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                return null;
            }
        });
    }

    private void API_updateGeneration(String farm_id) {

        APIHelperSync.getGeneration("getGeneration/" + farm_id, new BaseJsonHttpResponseHandler<Object>() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {

                Gson gson = new Gson();
                JSONGeneration jsonGeneration = gson.fromJson(rawJsonResponse, JSONGeneration.class);

                ArrayList<Generation> arrayListGenerationWeb = jsonGeneration.getData();
                ArrayList<Generation> arrayListGenerationLocal = new ArrayList<>();

                ArrayList<Integer> id_local = new ArrayList<>();
                ArrayList<Integer> id_web = new ArrayList<>();
                ArrayList<Integer> id_to_sync = new ArrayList<>();

                Cursor cursor_generations = myDb.getAllDataFromGeneration();
                cursor_generations.moveToFirst();

                if (cursor_generations.getCount() != 0) {
                    do {
                        Generation generation = new Generation(
                                cursor_generations.getString(2),
                                cursor_generations.getInt(4),
                                cursor_generations.getInt(0),
                                cursor_generations.getInt(1),
                                cursor_generations.getInt(3),
                                cursor_generations.getString(5)
                        );
                        arrayListGenerationLocal.add(generation);
                        id_local.add(generation.getId());

                    } while (cursor_generations.moveToNext());
                }

                for (Generation g : arrayListGenerationWeb) id_web.add(g.getId());

                for (int i : id_local)
                    if (!id_web.contains(i))
                        id_to_sync.add(i);


                for (int i : id_to_sync) {

                    Log.d(debugTag, i + "");

                    Cursor cursor = myDb.getAllDataFromGenerationWhereID(i);
                    cursor.moveToFirst();

                    Integer id = cursor.getInt(0);
                    Integer farm_id = cursor.getInt(1);
                    String number = cursor.getString(2);
                    Integer numerical_generation = cursor.getInt(3);
                    Integer is_active = cursor.getInt(4);
                    String deleted_at = cursor.getString(5);


                    RequestParams requestParams = new RequestParams();
                    requestParams.add("id", id.toString());
                    requestParams.add("farm_id", farm_id.toString());
                    requestParams.add("number", number);
                    requestParams.add("numerical_generation", numerical_generation.toString());
                    requestParams.add("is_active", is_active.toString());
                    requestParams.add("deleted_at", deleted_at);

                    API_addGeneration(requestParams);

                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonResponse, Object response) {

                Log.d(debugTag, "Failed to get Generations from web database ");
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                return null;
            }
        });
    }

    private void API_addGeneration(RequestParams requestParams) {
        APIHelperSync.addGeneration("addGeneration", requestParams, new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
                Toast.makeText(getApplicationContext(), "Successfully synced generations to web", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonResponse, Object response) {
                Toast.makeText(getApplicationContext(), "Failed to sync generations to web", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                return null;
            }
        });
    }

    private void API_updateLine() {

        APIHelperSync.getLine("getLine/", new BaseJsonHttpResponseHandler<Object>() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {

                Gson gson = new Gson();
                JSONLine jsonLine = gson.fromJson(rawJsonResponse, JSONLine.class);

                ArrayList<Line> arrayListLineWeb = jsonLine.getData();
                ArrayList<Line> arrayListLineLocal = new ArrayList<>();

                ArrayList<Integer> id_local = new ArrayList<>();
                ArrayList<Integer> id_web = new ArrayList<>();
                ArrayList<Integer> id_to_sync = new ArrayList<>();

                Cursor cursor_lines = myDb.getAllDataFromLine();
                cursor_lines.moveToFirst();

                if (cursor_lines.getCount() != 0) {
                    do {
                        Line line = new Line(
                                cursor_lines.getInt(0),
                                cursor_lines.getString(1),
                                cursor_lines.getInt(3)
                        );
                        arrayListLineLocal.add(line);
                        id_local.add(line.getId());
                    } while (cursor_lines.moveToNext());
                }

                for (Line line : arrayListLineWeb)
                    id_web.add(line.getId());

                for (int i : id_local) {
                    if (!id_web.contains(i)) {
                        id_to_sync.add(i);
                    }
                }

                for (int i : id_to_sync) {

                    Cursor cursor = myDb.getAllDataFromLineWhereID(id_to_sync.get(i));
                    cursor.moveToFirst();

                    Integer id = cursor.getInt(0);
                    String number = cursor.getString(1);
                    Integer is_active = cursor.getInt(2);
                    Integer generation_id = cursor.getInt(3);
                    String deleted_at = cursor.getString(4);

                    Log.d(debugTag, i + " " + generation_id + " " + number);

                    RequestParams requestParams = new RequestParams();
                    requestParams.add("id", id.toString());
                    requestParams.add("number", number.toString());
                    requestParams.add("is_active", is_active.toString());
                    requestParams.add("generation_id", generation_id.toString());
                    requestParams.add("deleted_at", deleted_at);


                    API_addLine(requestParams);
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonResponse, Object response) {

                Log.d(debugTag, "Failed to fetch Lines from web database");
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                return null;
            }
        });
    }

    private void API_addLine(RequestParams requestParams) {
        APIHelperSync.addLine("addLine", requestParams, new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
                Toast.makeText(getApplicationContext(), "Successfully synced lines to web", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonResponse, Object response) {

                Toast.makeText(getApplicationContext(), "Failed to add Line to web", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                return null;
            }
        });
    }


    //Brooders


    //Breeders


    //Phenotypic Morphometric


    //Replacements


    //Mortality and Sales


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
