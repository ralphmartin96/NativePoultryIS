package com.example.cholomanglicmot.nativechickenandduck.DashboardDirectory;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ExpandableListView;
import android.widget.Toast;

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

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class LoginSplashActivity extends AppCompatActivity {

    GoogleSignInClient mGoogleSignInClient;


    LinkedHashMap<String, List<String>> Project_category;
    List<String> Project_list;
    ExpandableListView Exp_list;
    ProjectAdapter adapter;

    DatabaseHelper myDb;
    String name;
    String email;
    String debugTag = "POULTRYDEBUGGER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        myDb = new DatabaseHelper(this);

        FirebaseAuth mAuth;

        mAuth = FirebaseAuth.getInstance();

        FirebaseUser user = mAuth.getCurrentUser();

        name = user.getDisplayName();

        email = user.getEmail();

        ///////////////////////////////////
        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("536452586282-rd7d10idfckmaoi6brrc65645blii924.apps.googleusercontent.com")
                .requestEmail()
                .build();
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        /////////////////////////////////

        new FetchStats().execute();


        Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "Loading web data", Toast.LENGTH_LONG).show();

    }

    private class FetchStats extends AsyncTask<String, String, String> {
        JSONObject response;

        @Override
        protected String doInBackground(String... params) {

            try {
                if (isNetworkAvailable())
                    API_getFarmID(email);
            } catch (Exception e) {
                Log.d(debugTag, "Data fetching error caught.");
            }
            return null;
        }

        protected void onPostExecute(String result) {
            runOnUiThread(new Runnable() {
                public void run() {

                    Intent i = new Intent(LoginSplashActivity.this, DashBoardActivity.class);
                    startActivity(i);

                    finish();

                }
            });
        }
    }

    //Farm and Users
    private void API_getFarmID(String email) {
        APIHelperSync.getFarmID("getFarmID/" + email, new BaseJsonHttpResponseHandler<Object>() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {

                String farm_id;

                farm_id = rawJsonResponse;
                farm_id = farm_id.replaceAll("\\[", "").replaceAll("\\]", "");

                API_getFarmInfo(farm_id);
                API_getPen(farm_id);
                API_getGeneration(farm_id);
                API_getFamilyForDisplay(farm_id);

                myDb = new DatabaseHelper(getApplicationContext());
                boolean isInsertedUser = myDb.insertDataUser(
                        name, email,
                        null, null,
                        Integer.parseInt(farm_id),
                        null, null, null
                );

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonResponse, Object response) {
                Toast.makeText(getApplicationContext(), "Failed in getting farm ID ", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                return null;
            }
        });
    }

    private void API_getFarmInfo(String farm_id) {
        APIHelperSync.getFarmInfo("getFarmInfo/" + farm_id, new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {


                myDb = new DatabaseHelper(getApplicationContext());
                rawJsonResponse = rawJsonResponse.replaceAll("\\[", "").replaceAll("\\]", "");
                Gson gson = new Gson();

                try {
                    FarmInfo farmInfo = gson.fromJson(rawJsonResponse, FarmInfo.class);

                    Cursor cursor = myDb.getAllDataFromFarms(Integer.parseInt(farm_id));
                    cursor.moveToFirst();
                    if (cursor.getCount() != 0) {
                        if (cursor.getInt(0) != farmInfo.getId()) {
                            boolean isInserted = myDb.insertDataFarm(farmInfo.getId(), farmInfo.getName(), farmInfo.getCode(), farmInfo.getAddress(), farmInfo.getBatching_week(), farmInfo.getBreedable_id());
                        } else {
                            Toast.makeText(LoginSplashActivity.this, "Farm Info already exists in your account", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        boolean isInserted = myDb.insertDataFarm(farmInfo.getId(), farmInfo.getName(), farmInfo.getCode(), farmInfo.getAddress(), farmInfo.getBatching_week(), farmInfo.getBreedable_id());
                        if (!isInserted) {
                            Toast.makeText(LoginSplashActivity.this, "Farm Info already exists in your account", Toast.LENGTH_SHORT).show();
                        }
                    }

                } catch (Exception e) {
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonResponse, Object response) {

                Toast.makeText(getApplicationContext(), "Failed getting farm info ", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                return null;
            }
        });
    }


    //Pen, Generation, Line and Family
    private void API_getPen(String farm_id) {
        APIHelperSync.getPen("getPen/" + farm_id, new BaseJsonHttpResponseHandler<Object>() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {

                Gson gson = new Gson();
                ArrayList<Pen> arrayList_pen = new ArrayList<>();
                ArrayList<Integer> arrayList_brooder_pen_id = new ArrayList<>();

                try {

                    JSONPen jsonPen = gson.fromJson(rawJsonResponse, JSONPen.class);
                    arrayList_pen = jsonPen.getData();

                    for (Pen pen : arrayList_pen) {

                        Cursor cursor1 = myDb.getAllDataFromPenWhereID(pen.getId());
                        cursor1.moveToFirst();

                        if (cursor1.getCount() == 0) {

                            boolean isInserted = myDb.insertDataPenWithID(
                                    pen.getId(),
                                    pen.getFarm_id(),
                                    pen.getPen_number(),
                                    pen.getPen_type(),
                                    pen.getPen_inventory(),
                                    pen.getPen_capacity(),
                                    pen.getIs_active()
                            );

                            if (isInserted)
                                if (pen.getPen_type().equals("brooder"))
                                    arrayList_brooder_pen_id.add(pen.getId());

                        }
                    }

                    if (!arrayList_brooder_pen_id.isEmpty())
                        API_getBrooderInventory(arrayList_brooder_pen_id);

                } catch (Exception e) {
                    Log.d(debugTag, "Exception in Pen API caught");
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonResponse, Object response) {
                Toast.makeText(getApplicationContext(), "Failed to fetch Pens from web database", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                return null;
            }
        });
    }

    private void API_getGeneration(String farm_id) {
        APIHelperSync.getGeneration("getGeneration/" + farm_id, new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {

                Gson gson = new Gson();
                try {
                    JSONGeneration jsonGeneration = gson.fromJson(rawJsonResponse, JSONGeneration.class);
                    ArrayList<Generation> arrayList_gen = jsonGeneration.getData();

                    for (Generation generation : arrayList_gen) {

                        Cursor cursor = myDb.getDataFromGenerationWhereID(generation.getId());
                        cursor.moveToFirst();

                        if (cursor.getCount() == 0) {
                            API_getLine(generation.getId().toString());

                            boolean isInserted = myDb.insertDataGenerationWithID(
                                    generation.getId(),
                                    generation.getFarm_id(),
                                    generation.getGeneration_number(),
                                    generation.getNumerical_generation(),
                                    generation.getGeneration_status()
                            );

//                            if(isInserted) Log.d(debugTag, "Inserted generation "+generation.getId());
                        }

                    }

                } catch (Exception e) {
                    Log.d(debugTag, "Exception in Generations API caught");
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonResponse, Object response) {

                Toast.makeText(getApplicationContext(), "Failed to fetch Generations from web database", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                return null;
            }
        });
    }

    private void API_getLine(String generation_id) {

        APIHelperSync.getLine("getLine/" + generation_id, new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {

                Gson gson = new Gson();

                try {

                    JSONLine jsonLine = gson.fromJson(rawJsonResponse, JSONLine.class);
                    ArrayList<Line> arrayList_line = jsonLine.getData();
                    ArrayList<Integer> arrayList_line_id = new ArrayList<>();

                    for (Line line : arrayList_line) {
                        Cursor cursor = myDb.getAllDataFromLineWhereID(line.getId());

                        if (cursor.getCount() == 0) {

                            boolean isInserted = myDb.insertDataLineWithID(
                                    line.getId(),
                                    line.getLine_number(),
                                    1,
                                    Integer.parseInt(generation_id)
                            );

                            if (isInserted) arrayList_line_id.add(line.getId());
                        }
                    }

                    if (!arrayList_line_id.isEmpty())
                        API_getFamily(arrayList_line_id);

                } catch (Exception e) {
                    Log.d(debugTag, "Exception in Lines API caught");
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonResponse, Object response) {

                Toast.makeText(getApplicationContext(), "Failed to fetch Lines from web database", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                return null;
            }
        });
    }

    private void API_getFamily(ArrayList<Integer> arrayList_line_id) {
        APIHelperSync.getFamily("getFamily/", new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {

                Gson gson = new Gson();

                Cursor cursor_line = myDb.getAllDataFromLine();
                cursor_line.moveToFirst();

                try {
                    JSONFamily1 jsonFamily1 = gson.fromJson(rawJsonResponse, JSONFamily1.class);
                    ArrayList<Family1> arrayList_family1 = jsonFamily1.getData();
                    ArrayList<Integer> arrayList_family1_id = new ArrayList<>();

                    for (Family1 family : arrayList_family1) {

                        if (arrayList_line_id.contains(family.getLine_id())) {

                            DatabaseHelper myDb = new DatabaseHelper(getApplicationContext());
                            Cursor cursor = myDb.getAllDataFromFamilyWhereID(family.getId());
                            cursor.moveToFirst();

                            if (cursor.getCount() == 0) {
                                boolean isInserted = myDb.insertDataFamilyWithID(
                                        family.getId(),
                                        family.getNumber(),
                                        family.getIs_active(),
                                        family.getLine_id(),
                                        family.getDeleted_at()
                                );


                                if (isInserted) {
                                    arrayList_family1_id.add(family.getId());
                                }

                            }

                        }

                    }

                    if (!arrayList_family1_id.isEmpty()) {
                        API_getBrooder(arrayList_family1_id);
                        API_getBreeder(arrayList_family1_id);
                        API_getReplacement(arrayList_family1_id);
                    }

                } catch (Exception e) {
                    Log.d(debugTag, "Exception in Family API caught");
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonResponse, Object response) {

                Toast.makeText(getApplicationContext(), "Failed to fetch Families from web database ", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                return null;
            }
        });
    }

    private void API_getFamilyForDisplay(String farm_id) {
        APIHelperSync.getFamily("getFamilyForDisplay/" + farm_id, new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {

                Gson gson = new Gson();

                try {
                    JSONFamily jsonFamily = gson.fromJson(rawJsonResponse, JSONFamily.class);
                    ArrayList<Family> arrayList_family = jsonFamily.getData();

                    for (Family family : arrayList_family) {


                        DatabaseHelper myDb = new DatabaseHelper(getApplicationContext());

                        Cursor cursor = myDb.getAllDataFromFamilyForDisplay(
                                family.getLine_number(),
                                family.getFamily_number(),
                                family.getGeneration_number()
                        );

                        cursor.moveToFirst();

                        if (cursor.getCount() == 0) {
                            boolean isInserted = myDb.insertDataFamilyDisplay(
                                    family.getLine_number(),
                                    family.getFamily_number(),
                                    1,
                                    family.getGeneration_number()
                            );
                        }

                    }

                } catch (Exception e) {
                    Log.d(debugTag, "Exception in FamilyForDisplay API caught");
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonResponse, Object response) {

                Toast.makeText(getApplicationContext(), "Failed to fetch Family For Display from web database ", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                return null;
            }
        });
    }


    //Brooders
    private void API_getBrooder(ArrayList<Integer> arrayList_family_id) {
        APIHelperSync.getBrooder("getBrooder/", new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {

                Gson gson = new Gson();
                try {
                    JSONBrooder jsonBrooder = gson.fromJson(rawJsonResponse, JSONBrooder.class);
                    ArrayList<Brooders> arrayList_brooder = jsonBrooder.getData();

                    for (Brooders brooder : arrayList_brooder) {

                        if (arrayList_family_id.contains(brooder.getBrooder_family_number())) {

                            Cursor cursor = myDb.getAllDataFromBroodersWhereID(brooder.getId());
                            cursor.moveToFirst();

                            if (cursor.getCount() == 0) {

                                boolean isInserted = myDb.insertDataBrooderWithID(
                                        brooder.getId(),
                                        brooder.getBrooder_family_number(),
                                        brooder.getBrooder_date_added(),
                                        brooder.getBrooder_deleted_at()
                                );

                            }

                        }

                    }

                } catch (Exception e) {
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonResponse, Object response) {
                Toast.makeText(getApplicationContext(), "Failed to fetch Brooders Inventory from web database ", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                return null;
            }
        });
    }

    private void API_getBrooderInventory(ArrayList<Integer> arrayList_pen_id) {
        APIHelperSync.getBrooderInventory("getBrooderInventory/", new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {

                Gson gson = new Gson();
                try {
                    JSONBrooderInventory jsonBrooderInventory = gson.fromJson(rawJsonResponse, JSONBrooderInventory.class);
                    ArrayList<Brooder_Inventory> arrayList_brooderInventory = jsonBrooderInventory.getData();
                    ArrayList<Integer> arrayList_brooderInventory_id = new ArrayList<>();

                    for (Brooder_Inventory brooder_inventory : arrayList_brooderInventory) {

                        if (arrayList_pen_id.contains(brooder_inventory.getBrooder_inv_pen())) {
                            Cursor cursor = myDb.getAllDataFromBrooderInventoryWhereID(brooder_inventory.getId());
                            cursor.moveToFirst();

                            if (cursor.getCount() == 0) {

                                boolean isInserted = myDb.insertDataBrooderInventoryWithID(
                                        brooder_inventory.getId(),
                                        brooder_inventory.getBrooder_inv_brooder_id(),
                                        brooder_inventory.getBrooder_inv_pen(),
                                        brooder_inventory.getBrooder_inv_brooder_tag(),
                                        brooder_inventory.getBrooder_inv_batching_date(),
                                        brooder_inventory.getBrooder_male_quantity(),
                                        brooder_inventory.getBrooder_female_quantity(),
                                        brooder_inventory.getBrooder_total_quantity(),
                                        brooder_inventory.getBrooder_inv_last_update(),
                                        brooder_inventory.getBrooder_inv_deleted_at()
                                );

                                if (isInserted)
                                    arrayList_brooderInventory_id.add(brooder_inventory.getId());

                            }

                        }

                    }

                    if (!arrayList_brooderInventory_id.isEmpty()) {
                        API_getBrooderFeeding(arrayList_brooderInventory_id);
                        API_getBrooderGrowth(arrayList_brooderInventory_id);
                        API_getMortalityAndSales(arrayList_brooderInventory_id);
                    }

                } catch (Exception e) {
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonResponse, Object response) {
                Toast.makeText(getApplicationContext(), "Failed to fetch Brooders Inventory from web database ", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                return null;
            }
        });
    }

    private void API_getBrooderFeeding(ArrayList<Integer> arrayList_brooderInventory_id) {
        APIHelperSync.getBrooderFeeding("getBrooderFeeding/", new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {

                Gson gson = new Gson();
                try {
                    JSONBrooderFeeding jsonBrooderFeeding = gson.fromJson(rawJsonResponse, JSONBrooderFeeding.class);
                    ArrayList<Brooder_FeedingRecords> arrayList_brooderFeeding = jsonBrooderFeeding.getData();

                    for (Brooder_FeedingRecords feedingRecords : arrayList_brooderFeeding) {

                        Cursor cursor = myDb.getAllDataFromBrooderFeedingRecordsWhereFeedingID(feedingRecords.getId());
                        cursor.moveToFirst();

                        if (arrayList_brooderInventory_id.contains(feedingRecords.getBrooder_feeding_inventory_id())) {

                            if (cursor.getCount() == 0) {

                                boolean isInserted = myDb.insertDataBrooderFeedingRecordsWithID(
                                        feedingRecords.getId(),
                                        feedingRecords.getBrooder_feeding_inventory_id(),
                                        feedingRecords.getBrooder_feeding_date_collected(),
                                        feedingRecords.getBrooder_feeding_offered(),
                                        feedingRecords.getBrooder_feeding_refused(),
                                        feedingRecords.getBrooder_feeding_remarks(),
                                        feedingRecords.getBrooder_feeding_deleted_at()
                                );

                            }

                        }

                        cursor.close();

                    }

                } catch (Exception e) {

                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonResponse, Object response) {

                Toast.makeText(getApplicationContext(), "Failed to fetch Brooders Inventory from web database ", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                return null;
            }
        });
    }

    private void API_getBrooderGrowth(ArrayList<Integer> arrayList_brooderInventory_id) {
        APIHelperSync.getBrooderGrowth("getBrooderGrowth/", new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {

                Gson gson = new Gson();
                try {
                    JSONBrooderGrowth jsonBrooderGrowth = gson.fromJson(rawJsonResponse, JSONBrooderGrowth.class);
                    ArrayList<Brooder_GrowthRecords> arrayList_brooderGrowthRecords = jsonBrooderGrowth.getData();


                    for (Brooder_GrowthRecords brooder_growthRecords : arrayList_brooderGrowthRecords) {

                        Cursor cursor = myDb.getAllDataFromBrooderGrowthRecordsWhereGrowthID(brooder_growthRecords.getId());
                        cursor.moveToFirst();

                        if (arrayList_brooderInventory_id.contains(brooder_growthRecords.getBrooder_growth_inventory_id())) {

                            if (cursor.getCount() == 0) {
                                boolean isInserted = myDb.insertDataBrooderGrowthRecordsWithID(
                                        brooder_growthRecords.getId(),
                                        brooder_growthRecords.getBrooder_growth_inventory_id(),
                                        brooder_growthRecords.getBrooder_growth_collection_day(),
                                        brooder_growthRecords.getBrooder_growth_date_collected(),
                                        brooder_growthRecords.getBrooder_growth_male_quantity(),
                                        brooder_growthRecords.getBrooder_growth_male_weight(),
                                        brooder_growthRecords.getBrooder_growth_female_quantity(),
                                        brooder_growthRecords.getBrooder_growth_female_weight(),
                                        brooder_growthRecords.getBrooder_growth_total_quantity(),
                                        brooder_growthRecords.getBrooder_growth_total_weight(),
                                        brooder_growthRecords.getBrooder_growth_deleted_at()
                                );

                            }

                        }

                    }

                } catch (Exception e) {
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonResponse, Object response) {

                Toast.makeText(getApplicationContext(), "Failed to fetch Brooders Growth Records from web database ", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                return null;
            }
        });
    }


    //Breeders
    private void API_getBreeder(ArrayList<Integer> arrayList_family_id) {
        APIHelperSync.getBreeder("getBreeder/", new BaseJsonHttpResponseHandler<Object>() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {

                Gson gson = new Gson();
                try {


                    JSONBreeder jsonBreeder = gson.fromJson(rawJsonResponse, JSONBreeder.class);
                    ArrayList<Breeders> arrayList_breeder = jsonBreeder.getData();
                    ArrayList<Integer> arrayList_breeder_id = new ArrayList<>();

                    for (Breeders breeder : arrayList_breeder) {

                        if (arrayList_family_id.contains(breeder.getFamily_number())) {

                            Cursor cursor = myDb.getAllDataFromBreedersWhereID(breeder.getId());
                            cursor.moveToFirst();

                            if (cursor.getCount() == 0) {
                                boolean isInserted = myDb.insertDataBreederWithID(
                                        breeder.getId(),
                                        breeder.getFamily_number(),
                                        breeder.getFemale_family_number(),
                                        breeder.getDate_added(),
                                        breeder.getDeleted_at()
                                );

                                if (isInserted)
                                    arrayList_breeder_id.add(breeder.getId());


                            }
                        }
                    }

                    if (!arrayList_breeder_id.isEmpty())
                        API_getBreederInventory(arrayList_breeder_id);

                } catch (Exception e) {
                    Log.d("JSON", e.toString());
                }


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonResponse, Object response) {

                Toast.makeText(getApplicationContext(), "Failed to fetch Breeders from web database ", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                return null;
            }
        });
    }

    private void API_getBreederInventory(ArrayList<Integer> arrayList_breeder_id) {
        APIHelperSync.getBreederInventory("getBreederInventory/", new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {

                Gson gson = new Gson();

                try {
                    JSONBreederInventory jsonBreederInventory = gson.fromJson(rawJsonResponse, JSONBreederInventory.class);
                    ArrayList<Breeder_Inventory> arrayList_breederInventory = jsonBreederInventory.getData();
                    ArrayList<Integer> arrayList_breederInventory_id = new ArrayList<>();

                    for (Breeder_Inventory breeder_inventory : arrayList_breederInventory) {

                        if (arrayList_breeder_id.contains(breeder_inventory.getBreeder_inv_breeder_id())) {

                            Cursor cursor = myDb.getAllDataFromBreederInventoryWhereID(breeder_inventory.getId());
                            cursor.moveToFirst();

                            if (cursor.getCount() == 0) {

                                boolean isInserted = myDb.insertDataBreederInventoryWithID(
                                        breeder_inventory.getId(),
                                        breeder_inventory.getBreeder_inv_breeder_id(),
                                        breeder_inventory.getBreeder_inv_pen(),
                                        breeder_inventory.getBreeder_inv_breeder_tag(),
                                        breeder_inventory.getBreeder_inv_batching_date(),
                                        breeder_inventory.getBreeder_male_quantity(),
                                        breeder_inventory.getBreeder_female_quantity(),
                                        breeder_inventory.getBreeder_total_quantity(),
                                        breeder_inventory.getBreeder_inv_last_update(),
                                        breeder_inventory.getBreeder_inv_deleted_at(),
                                        breeder_inventory.getBreeder_code(),
                                        breeder_inventory.getMale_wingband(),
                                        breeder_inventory.getFemale_wingband()
                                );

                                if (isInserted)
                                    arrayList_breederInventory_id.add(breeder_inventory.getId());
                            }
                        }
                    }

                    if (!arrayList_breederInventory_id.isEmpty()) {
                        API_getBreederFeeding(arrayList_breederInventory_id);
                        API_getEggProduction(arrayList_breederInventory_id);
                        API_getHatcheryRecords(arrayList_breederInventory_id);
                        API_getEggQuality(arrayList_breederInventory_id);
                        API_getPhenoMorphos(arrayList_breederInventory_id);
                        API_getMortalityAndSales(arrayList_breederInventory_id);
                    }

                } catch (Exception e) {
                }


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonResponse, Object response) {

                Toast.makeText(getApplicationContext(), "Failed to fetch Breeder Inventory from web database ", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                return null;
            }
        });
    }

    private void API_getBreederFeeding(ArrayList<Integer> arrayList_breederInventory_id) {

        //TODO: UPDATE hardcoded API url
        APIHelperSync.getBreederFeeding("getBreederFeeding?page=3", new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {

                Gson gson = new Gson();
                try {
                    JSONBreederFeeding jsonBreederFeeding = gson.fromJson(rawJsonResponse, JSONBreederFeeding.class);
                    ArrayList<Breeder_FeedingRecords> arrayList_breederFeeding = jsonBreederFeeding.getData();

                    for (Breeder_FeedingRecords feedingRecords : arrayList_breederFeeding) {

                        if (arrayList_breederInventory_id.contains(feedingRecords.getBreeder_feeding_inventory_id())) {

                            Cursor cursor = myDb.getAllDataFromBreederFeedingRecordsWhereFeedingID(feedingRecords.getId());
                            cursor.moveToFirst();

                            if (cursor.getCount() == 0) {

                                boolean isInserted = myDb.insertDataBreederFeedingRecordsWithID(
                                        feedingRecords.getId(),
                                        feedingRecords.getBreeder_feeding_inventory_id(),
                                        feedingRecords.getBreeder_feeding_date_collected(),
                                        feedingRecords.getBreeder_feeding_offered(),
                                        feedingRecords.getBreeder_feeding_refused(),
                                        feedingRecords.getBreeder_feeding_remarks(),
                                        feedingRecords.getBreeder_feeding_deleted_at()
                                );

                            }

                        }
                    }

                } catch (Exception e) {

                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonResponse, Object response) {
                Toast.makeText(getApplicationContext(), "Failed to fetch Breeder Feeding from web database ", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                return null;
            }
        });
    }

    private void API_getEggProduction(ArrayList<Integer> arrayList_breederInventory_id) {
        APIHelperSync.getEggProduction("getEggProduction/", new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {

                Gson gson = new Gson();
                try {
                    JSONEggProduction jsonBreeder = gson.fromJson(rawJsonResponse, JSONEggProduction.class);
                    ArrayList<Egg_Production> arrayList_eggProduction = jsonBreeder.getData();

                    for (Egg_Production egg_production : arrayList_eggProduction) {

                        if (arrayList_breederInventory_id.contains(egg_production.getEgg_breeder_inv_id())) {

                            Cursor cursor = myDb.getAllDataFromEggProductionWhereID(egg_production.getId());
                            cursor.moveToFirst();

                            if (cursor.getCount() == 0) {
                                boolean isInserted = myDb.insertEggProductionRecordsWithID(
                                        egg_production.getId(),
                                        egg_production.getEgg_breeder_inv_id(),
                                        egg_production.getDate(),
                                        egg_production.getIntact(),
                                        egg_production.getWeight(),
                                        egg_production.getBroken(),
                                        egg_production.getRejects(),
                                        egg_production.getRemarks(),
                                        egg_production.getDeleted_at(),
                                        egg_production.getFemale_inventory()
                                );

//                                if(isInserted)
//                                    Log.d(debugTag, "EP ID: " + egg_production.getId());
                            }

                        }

                    }

                } catch (Exception e) {
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonResponse, Object response) {

                Toast.makeText(getApplicationContext(), "Failed to fetch Egg Production from web database ", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                return null;
            }
        });
    }

    private void API_getHatcheryRecords(ArrayList<Integer> arrayList_breederInventory_id) {
        APIHelperSync.getHatcheryRecords("getHatcheryRecords/", new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {

                Gson gson = new Gson();
                try {
                    JSONHatchery jsonBreeder = gson.fromJson(rawJsonResponse, JSONHatchery.class);
                    ArrayList<Hatchery_Records> arrayList_hatchery_records = jsonBreeder.getData();

                    for (Hatchery_Records hatchery_records : arrayList_hatchery_records) {

                        if (arrayList_breederInventory_id.contains(hatchery_records.getBreeder_inv_id())) {

                            Cursor cursor = myDb.getAllDataFromBreederHatcheryWhereID(hatchery_records.getId());
                            cursor.moveToFirst();

                            if (cursor.getCount() == 0) {

                                boolean isInserted = myDb.insertHatcheryRecordsWithID(
                                        hatchery_records.getId(),
                                        hatchery_records.getBreeder_inv_id(),
                                        hatchery_records.getDate(),
                                        hatchery_records.getBatching_date(),
                                        hatchery_records.getEggs_set(),
                                        hatchery_records.getWeek_lay(),
                                        hatchery_records.getFertile(),
                                        hatchery_records.getHatched(),
                                        hatchery_records.getDate_hatched(),
                                        hatchery_records.getDeleted_at()
                                );

//                                if(isInserted)
//                                    Log.d(debugTag, "Hatchery ID: "+hatchery_records.getId());

                            }
                        }

                    }

                } catch (Exception e) {
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonResponse, Object response) {

                Toast.makeText(getApplicationContext(), "Failed to fetch Hatchery Records from web database ", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                return null;
            }
        });
    }

    private void API_getEggQuality(ArrayList<Integer> arrayList_breederInventory_id) {
        APIHelperSync.getEggQuality("getEggQuality/", new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {

                Gson gson = new Gson();
                try {
                    JSONEggQuality jsonBreeder = gson.fromJson(rawJsonResponse, JSONEggQuality.class);
                    ArrayList<Egg_Quality> arrayList_eggQuality = jsonBreeder.getData();


                    for (Egg_Quality egg_quality : arrayList_eggQuality) {

                        if (arrayList_breederInventory_id.contains(egg_quality.getEgg_breeder_inv_id())) {

                            Cursor cursor = myDb.getAllDataFromBreederEggQualWhereID(egg_quality.getId());
                            cursor.moveToFirst();

                            if (cursor.getCount() == 0) {

                                boolean isInserted = myDb.insertEggQualityRecordsWithID(
                                        egg_quality.getId(),
                                        egg_quality.getEgg_breeder_inv_id(),
                                        egg_quality.getDate(),
                                        egg_quality.getWeek(),
                                        egg_quality.getWeight(),
                                        egg_quality.getColor(),
                                        egg_quality.getShape(),
                                        egg_quality.getLength(),
                                        egg_quality.getWidth(),
                                        egg_quality.getAlbument_height(),
                                        egg_quality.getAlbument_weight(),
                                        egg_quality.getYolk_weight(),
                                        egg_quality.getYolk_color(),
                                        egg_quality.getShell_weight(),
                                        egg_quality.getShell_thickness_top(),
                                        egg_quality.getShell_thickness_middle(),
                                        egg_quality.getShell_thickness_bottom()
                                );

//                                if(isInserted)
//                                    Log.d(debugTag, "EQ id: "+ egg_quality.getId());
                            }

                        }

                    }

                } catch (Exception e) {
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonResponse, Object response) {

                Toast.makeText(getApplicationContext(), "Failed to fetch Egg Quality from web database ", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                return null;
            }
        });
    }


    //Phenotypic Morphometric
    private void API_getPhenoMorphos(ArrayList<Integer> arrayList_inventory_id) {
        APIHelperSync.getPhenoMorphos("getPhenoMorphos/", new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {

                Gson gson = new Gson();
                try {
                    JSONPhenoMorphos jsonReplacement = gson.fromJson(rawJsonResponse, JSONPhenoMorphos.class);
                    ArrayList<Pheno_Morphos> arrayList_phenoMorphos = jsonReplacement.getData();
                    ArrayList<Integer> arrayList_phenoMorphos_values_id = new ArrayList<>();

                    for (Pheno_Morphos pheno_morphos : arrayList_phenoMorphos) {

                        Integer inventory_id;

                        inventory_id = pheno_morphos.getBreeder_inventory() != null ?
                                pheno_morphos.getBreeder_inventory() : pheno_morphos.getReplacement_inventory();

                        if (arrayList_inventory_id.contains(inventory_id)) {

                            Cursor cursor = myDb.getDataFromReplacementPhenoMorphosWhereID(pheno_morphos.getId());
                            cursor.moveToFirst();

                            if (cursor.getCount() == 0) {

                                boolean isInserted = myDb.insertPhenoMorphosWithID(
                                        pheno_morphos.getId(),
                                        pheno_morphos.getReplacement_inventory(),
                                        pheno_morphos.getBreeder_inventory(),
                                        pheno_morphos.getValues_id(),
                                        pheno_morphos.getDeleted_at()
                                );

                                if (isInserted) {
//                                    if (pheno_morphos.getBreeder_inventory() != null)
//                                        Log.d(debugTag, "Breeder PM: " + pheno_morphos.getId() + " " + pheno_morphos.getBreeder_inventory());
//                                    else
//                                        Log.d(debugTag, "Replacement PM: " + pheno_morphos.getId() + " " + pheno_morphos.getReplacement_inventory());

                                    arrayList_phenoMorphos_values_id.add(pheno_morphos.getValues_id());
                                }
                            }

                        }

                    }

                    if (!arrayList_phenoMorphos_values_id.isEmpty())
                        API_getPhenoMorphoValues(arrayList_phenoMorphos_values_id);

//                    Log.d(debugTag, "Values: "+ arrayList_phenoMorphos_values_id.size() + " " + arrayList_phenoMorphos_values_id);
                } catch (Exception e) {
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonResponse, Object response) {

                Toast.makeText(getApplicationContext(), "Failed to fetch pheno and morphos web database ", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                return null;
            }
        });
    }

    private void API_getPhenoMorphoValues(ArrayList<Integer> arrayList_values_Id) {
        APIHelperSync.getPhenoMorphoValues("getPhenoMorphoValues/", new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {

                Gson gson = new Gson();
                try {
                    JSONPhenoMorphoValues jsonPhenoMorpho = gson.fromJson(rawJsonResponse, JSONPhenoMorphoValues.class);
                    ArrayList<Breeder_PhenoMorphoView> arrayList_breeder_phenoMorphoView = jsonPhenoMorpho.getData();

                    for (Breeder_PhenoMorphoView phenoMorphoValues : arrayList_breeder_phenoMorphoView) {

                        if (arrayList_values_Id.contains(phenoMorphoValues.getId())) {

                            Cursor cursor = myDb.getAllDataFromPhenoMorphoRecordsWithID(phenoMorphoValues.getId());
                            cursor.moveToFirst();

                            if (cursor.getCount() == 0) {
                                boolean isInserted = myDb.insertPhenoMorphoRecordsWithID(
                                        phenoMorphoValues.getId(),
                                        phenoMorphoValues.getGender(),
                                        phenoMorphoValues.getTag(),
                                        phenoMorphoValues.getPheno_record(),
                                        phenoMorphoValues.getMorpho_record(),
                                        phenoMorphoValues.getDate(),
                                        phenoMorphoValues.getDeleted_at()
                                );

                            }

                        }

                    }
                } catch (Exception e) {
                }


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonResponse, Object response) {

                Toast.makeText(getApplicationContext(), "Failed to fetch pheno and morphos values web database ", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                return null;
            }
        });
    }


    //Replacements
    private void API_getReplacement(ArrayList<Integer> arrayList_family_id) {
        APIHelperSync.getReplacement("getReplacement/", new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {

                Gson gson = new Gson();
                try {
                    JSONReplacement jsonReplacement = gson.fromJson(rawJsonResponse, JSONReplacement.class);
                    ArrayList<Replacements> arrayList_replacement = jsonReplacement.getData();
                    ArrayList<Integer> arrayList_replacement_id = new ArrayList<>();

                    for (Replacements replacements : arrayList_replacement) {

                        if (arrayList_family_id.contains(replacements.getReplacement_family_number())) {

                            Cursor cursor = myDb.getAllDataFromReplacementsWhereID(replacements.getId());
                            cursor.moveToFirst();

                            if (cursor.getCount() == 0) {

                                boolean isInserted = myDb.insertDataReplacementWithID(
                                        replacements.getId(),
                                        replacements.getReplacement_family_number(),
                                        replacements.getReplacement_date_added(),
                                        replacements.getReplacement_deleted_at()
                                );

                                if (isInserted)
                                    arrayList_replacement_id.add(replacements.getId());
//                                    Log.d(debugTag, "REPLACEMENT ID: " + replacements.getId() +" "+ replacements.getReplacement_family_number());
                            }
                        }

                        if (!arrayList_replacement_id.isEmpty())
                            API_getReplacementInventory(arrayList_replacement_id);

                    }
                } catch (Exception e) {
                }


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonResponse, Object response) {

                Toast.makeText(getApplicationContext(), "Failed to fetch Brooders from web database ", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                return null;
            }
        });
    }

    private void API_getReplacementInventory(ArrayList<Integer> arrayList_replacement_id) {
        APIHelperSync.getReplacementInventory("getReplacementInventory/", new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {

                Gson gson = new Gson();
                try {
                    JSONReplacementInventory jsonReplacementInventory = gson.fromJson(rawJsonResponse, JSONReplacementInventory.class);
                    ArrayList<Replacement_Inventory> arrayList_replacementInventory = jsonReplacementInventory.getData();
                    ArrayList<Integer> arrayList_replacementInventory_id = new ArrayList<>();

                    for (Replacement_Inventory replacement_inventory : arrayList_replacementInventory) {

                        if (arrayList_replacement_id.contains(replacement_inventory.getReplacement_inv_replacement_id())) {

                            Cursor cursor = myDb.getAllDataFromBrooderInventoryWhereID(replacement_inventory.getId());
                            cursor.moveToFirst();

                            if (cursor.getCount() == 0) {

                                try {
                                    boolean isInserted = myDb.insertDataReplacementInventoryWithID(
                                            replacement_inventory.getId(),
                                            replacement_inventory.getReplacement_inv_replacement_id(),
                                            replacement_inventory.getReplacement_inv_pen(),
                                            replacement_inventory.getReplacement_inv_replacement_tag(),
                                            replacement_inventory.getReplacement_inv_batching_date(),
                                            replacement_inventory.getReplacement_male_quantity(),
                                            replacement_inventory.getReplacement_female_quantity(),
                                            replacement_inventory.getReplacement_total_quantity(),
                                            replacement_inventory.getReplacement_inv_last_update(),
                                            replacement_inventory.getReplacement_inv_deleted_at()
                                    );

                                    if (isInserted)
                                        arrayList_replacementInventory_id.add(replacement_inventory.getId());

                                } catch (Exception e) {
                                }

                            }

                        }


                    }

                    if (!arrayList_replacementInventory_id.isEmpty()) {
                        API_getReplacementFeeding(arrayList_replacementInventory_id);
                        API_getReplacementGrowth(arrayList_replacementInventory_id);
                        API_getPhenoMorphos(arrayList_replacementInventory_id);
                        API_getMortalityAndSales(arrayList_replacementInventory_id);
                    }

                } catch (Exception e) {
                }


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonResponse, Object response) {
                Toast.makeText(getApplicationContext(), "Failed to fetch Replacement Inventory from web database ", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                return null;
            }
        });
    }

    private void API_getReplacementFeeding(ArrayList<Integer> arrayList_replacementInventory_id) {
        APIHelperSync.getReplacementFeeding("getReplacementFeeding/", new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {


                Gson gson = new Gson();
                try {
                    JSONReplacementFeeding jsonBrooderInventory = gson.fromJson(rawJsonResponse, JSONReplacementFeeding.class);
                    ArrayList<Replacement_FeedingRecords> arrayList_replacementFeeding = jsonBrooderInventory.getData();


                    for (Replacement_FeedingRecords replacement_feedingRecords : arrayList_replacementFeeding) {

                        if (arrayList_replacementInventory_id.contains(replacement_feedingRecords.getReplacement_feeding_inventory_id())) {

                            Cursor cursor = myDb.getAllDataFromReplacementFeedingRecordsWhereFeedingID(replacement_feedingRecords.getId());
                            cursor.moveToFirst();

                            if (cursor.getCount() == 0) {

                                boolean isInserted = myDb.insertDataReplacementFeedingRecordsWithID(
                                        replacement_feedingRecords.getId(),
                                        replacement_feedingRecords.getReplacement_feeding_inventory_id(),
                                        replacement_feedingRecords.getReplacement_feeding_date_collected(),
                                        replacement_feedingRecords.getReplacement_feeding_offered(),
                                        replacement_feedingRecords.getReplacement_feeding_refused(),
                                        replacement_feedingRecords.getReplacement_feeding_remarks(),
                                        replacement_feedingRecords.getReplacement_feeding_deleted_at()
                                );

//                                if (isInserted)
//                                    Log.d(debugTag, "REP FEED ID: " + replacement_feedingRecords.getId());

                            }

                        }

                    }
                } catch (Exception e) {
                }


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonResponse, Object response) {

                Toast.makeText(getApplicationContext(), "Failed to fetch Brooders Inventory from web database ", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                return null;
            }
        });
    }

    private void API_getReplacementGrowth(ArrayList<Integer> arrayList_replacementInventory_id) {
        APIHelperSync.getReplacementGrowth("getReplacementGrowth/", new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {


                Gson gson = new Gson();
                try {
                    JSONReplacementGrowth jsonBrooderInventory = gson.fromJson(rawJsonResponse, JSONReplacementGrowth.class);
                    ArrayList<Replacement_GrowthRecords> arrayList_replacementGrowth = jsonBrooderInventory.getData();

                    for (Replacement_GrowthRecords replacement_growthRecords : arrayList_replacementGrowth) {

                        if (arrayList_replacementInventory_id.contains(replacement_growthRecords.getReplacement_growth_inventory_id())) {

                            Cursor cursor = myDb.getAllDataFromReplacementGrowthRecordsWhereGrowthID(replacement_growthRecords.getId());
                            cursor.moveToFirst();

                            if (cursor.getCount() == 0) {

                                boolean isInserted = myDb.insertDataReplacementGrowthRecords(
                                        replacement_growthRecords.getId(),
                                        replacement_growthRecords.getReplacement_growth_inventory_id(),
                                        replacement_growthRecords.getReplacement_growth_collection_day(),
                                        replacement_growthRecords.getReplacement_growth_date_collected(),
                                        replacement_growthRecords.getReplacement_growth_male_quantity(),
                                        replacement_growthRecords.getReplacement_growth_male_weight(),
                                        replacement_growthRecords.getReplacement_growth_female_quantity(),
                                        replacement_growthRecords.getReplacement_growth_female_weight(),
                                        replacement_growthRecords.getReplacement_growth_total_quantity(),
                                        replacement_growthRecords.getReplacement_growth_total_weight(),
                                        replacement_growthRecords.getReplacement_growth_deleted_at()
                                );

//                                if (isInserted)
//                                    Log.d(debugTag, "REPL GROWTH: " + replacement_growthRecords.getId());

                            }

                        }

                    }

                } catch (Exception e) {
                }


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonResponse, Object response) {

                Toast.makeText(getApplicationContext(), "Failed to fetch Replacement Growth Records from web database ", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                return null;
            }
        });
    }


    //Mortality and Sales
    private void API_getMortalityAndSales(ArrayList<Integer> arrayList_inventory_id) {

        APIHelperSync.getMortalityAndSales("getMortalityAndSales/", new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {

                Gson gson = new Gson();
                try {
                    JSONMortalityAndSales jsonBrooder = gson.fromJson(rawJsonResponse, JSONMortalityAndSales.class);
                    ArrayList<Mortality_Sales> arrayList_mortalitySales = jsonBrooder.getData();

                    for (Mortality_Sales mortality_sales : arrayList_mortalitySales) {

                        Integer inventory_id = 0;

                        if (mortality_sales.getBreeder_id() != null)
                            inventory_id = mortality_sales.getBreeder_id();
                        else if (mortality_sales.getReplaement_id() != null)
                            inventory_id = mortality_sales.getReplaement_id();
                        else
                            inventory_id = mortality_sales.getBrooder_id();

                        if (arrayList_inventory_id.contains(inventory_id)) {

                            Cursor cursor = myDb.getAllDataFromMortandSalesRecordsWithID(mortality_sales.getId());
                            cursor.moveToFirst();

                            if (cursor.getCount() == 0) {

                                boolean isInserted = myDb.insertDataMortalityAndSalesWithID(
                                        mortality_sales.getId(),
                                        mortality_sales.getDate(),
                                        mortality_sales.getBreeder_id(),
                                        mortality_sales.getReplaement_id(),
                                        mortality_sales.getBrooder_id(),
                                        mortality_sales.getType(),
                                        mortality_sales.getCategory(),
                                        mortality_sales.getPrice(),
                                        mortality_sales.getMale_count(),
                                        mortality_sales.getFemale_count(),
                                        mortality_sales.getTotal(),
                                        mortality_sales.getReason(),
                                        mortality_sales.getRemarks(),
                                        mortality_sales.getDeleted_at()
                                );

//                                if(isInserted)
//                                    Log.d(debugTag, "INV ID: " + inventory_id + " " + mortality_sales.getId());

                            }
                        }


                    }
                } catch (Exception e) {
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonResponse, Object response) {

                Toast.makeText(getApplicationContext(), "Failed to fetch Mortality and Sales from web ", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
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
}
