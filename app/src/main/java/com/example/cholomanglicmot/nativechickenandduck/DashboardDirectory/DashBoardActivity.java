package com.example.cholomanglicmot.nativechickenandduck.DashboardDirectory;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cholomanglicmot.nativechickenandduck.APIHelper;
import com.example.cholomanglicmot.nativechickenandduck.BreedersDirectory.Breeder_FeedingRecords;
import com.example.cholomanglicmot.nativechickenandduck.BreedersDirectory.Breeder_Inventory;
import com.example.cholomanglicmot.nativechickenandduck.BreedersDirectory.Breeder_PhenoMorphoView;
import com.example.cholomanglicmot.nativechickenandduck.BreedersDirectory.Breeders;
import com.example.cholomanglicmot.nativechickenandduck.BreedersDirectory.CreateBreeders;
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
import com.example.cholomanglicmot.nativechickenandduck.BroodersDirectory.CreateBrooders;
import com.example.cholomanglicmot.nativechickenandduck.BroodersDirectory.JSONBrooder;
import com.example.cholomanglicmot.nativechickenandduck.BroodersDirectory.JSONBrooderFeeding;
import com.example.cholomanglicmot.nativechickenandduck.BroodersDirectory.JSONBrooderGrowth;
import com.example.cholomanglicmot.nativechickenandduck.BroodersDirectory.JSONBrooderInventory;
import com.example.cholomanglicmot.nativechickenandduck.BroodersDirectory.JSONMortalityAndSales;
import com.example.cholomanglicmot.nativechickenandduck.DataProvider;
import com.example.cholomanglicmot.nativechickenandduck.DatabaseHelper;
import com.example.cholomanglicmot.nativechickenandduck.FamilyDirectory.CreateFamilies;
import com.example.cholomanglicmot.nativechickenandduck.FamilyDirectory.Family;
import com.example.cholomanglicmot.nativechickenandduck.FamilyDirectory.Family1;
import com.example.cholomanglicmot.nativechickenandduck.FamilyDirectory.JSONFamily;
import com.example.cholomanglicmot.nativechickenandduck.FamilyDirectory.JSONFamily1;
import com.example.cholomanglicmot.nativechickenandduck.FarmSettingsDirectory.MainActivity;
import com.example.cholomanglicmot.nativechickenandduck.GenerationsAndLinesDirectory.CreateGenerationsAndLines;
import com.example.cholomanglicmot.nativechickenandduck.GenerationsAndLinesDirectory.Generation;
import com.example.cholomanglicmot.nativechickenandduck.GenerationsAndLinesDirectory.JSONGeneration;
import com.example.cholomanglicmot.nativechickenandduck.GenerationsAndLinesDirectory.JSONLine;
import com.example.cholomanglicmot.nativechickenandduck.GenerationsAndLinesDirectory.Line;
import com.example.cholomanglicmot.nativechickenandduck.PensDirectory.CreatePen;
import com.example.cholomanglicmot.nativechickenandduck.PensDirectory.JSONPen;
import com.example.cholomanglicmot.nativechickenandduck.PensDirectory.Pen;
import com.example.cholomanglicmot.nativechickenandduck.ProjectAdapter;
import com.example.cholomanglicmot.nativechickenandduck.R;
import com.example.cholomanglicmot.nativechickenandduck.ReplacementsDirectory.CreateReplacements;
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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import de.hdodenhof.circleimageview.CircleImageView;


public class DashBoardActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private Toolbar mToolbar;

    TextView male_count_breeder;
    TextView female_count_breeder;
    TextView intact;
    TextView broken;
    TextView rejected;
    TextView hen_day;
    TextView male_count_replacement;
    TextView female_count_replacement;
    TextView male_count_brooder;
    TextView female_count_brooder;
    TextView last_checked;

    RecyclerView.LayoutManager layoutManager;
    LinkedHashMap<String, List<String>> Project_category;
    List<String> Project_list;
    ExpandableListView Exp_list;
    ProjectAdapter adapter;
    Integer farm_id_local;
    DatabaseHelper myDb;
    String name;
    String email;
    GoogleSignInClient mGoogleSignInClient;

    final String debugTag = "POULTRYDEBUGGER";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);

        Exp_list = findViewById(R.id.exp_list);
        Project_category = DataProvider.getInfo();
        Project_list =  new ArrayList<String>(Project_category.keySet());
        adapter = new ProjectAdapter(this, Project_category, Project_list);
        Exp_list.setAdapter(adapter);
        myDb = new DatabaseHelper(this);


        FirebaseAuth mAuth;

        mAuth = FirebaseAuth.getInstance();

        FirebaseUser user = mAuth.getCurrentUser();

        name = user.getDisplayName();

        String email = user.getEmail();

        Uri photo = user.getPhotoUrl();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View hView =  navigationView.getHeaderView(0);
        TextView nav_user = (TextView)hView.findViewById(R.id.textView8);
        TextView nav_email = (TextView)hView.findViewById(R.id.textView9);
        CircleImageView circleImageView = hView.findViewById(R.id.display_photo);
        nav_user.setText(name);
        Picasso.get().load(photo).into(circleImageView);
        nav_email.setText(email);


        /////////////////////////////////////
        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("536452586282-rd7d10idfckmaoi6brrc65645blii924.apps.googleusercontent.com")
                .requestEmail()
                .build();
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        ///////////////////////////////////


        boolean isNetworkAvailable = isNetworkAvailable();

        if(isNetworkAvailable){

            //if internet is available, load data from web database

            API_getFarmID(email);
//
//            API_getPhenoMorphoValues();
//            API_getPhenoMorphos();
//            API_getMortalityAndSales();
//
//            API_getReplacementFeeding();
//            API_getReplacementGrowth();
        }

        Cursor cursor = myDb.getFarmIDFromUsers(email);
        cursor.moveToFirst();

        if(cursor.getCount() != 0){
            farm_id_local = cursor.getInt(0);
        }

        if(farm_id_local == null){
            farm_id_local = 0;
        }

        {
            male_count_breeder = findViewById(R.id.male_count_breeder);
            female_count_breeder = findViewById(R.id.female_count_breeder);
            male_count_breeder.setText(myDb.getAllMaleFromBreeders(farm_id_local).toString());
            female_count_breeder.setText(myDb.getAllFemaleFromBreeders(farm_id_local).toString());

            intact = findViewById(R.id.intact);
            broken = findViewById(R.id.broken);
            rejected = findViewById(R.id.rejected);
            hen_day = findViewById(R.id.hen_day);
            intact.setText(myDb.getTotalIntact(farm_id_local).toString());
            broken.setText(myDb.getTotalBroken(farm_id_local).toString());
            rejected.setText(myDb.getTotalRejects(farm_id_local).toString());
            hen_day.setText(myDb.getHenDayEggProduction(farm_id_local).toString() + " %");


            DateFormat dateFormat = new SimpleDateFormat("MM");
            Date date = new Date();
            String month = dateFormat.format(date);

            Calendar cal = Calendar.getInstance();
            Calendar cal2 = Calendar.getInstance();

            int month2 = cal.get(Calendar.MONTH);
            cal2.add(month2, -1);

            Integer month3 = cal2.get(Calendar.MONTH);

            DateFormat dateFormat2 = new SimpleDateFormat("YYYY");
            Date date2 = new Date();
            String year = dateFormat2.format(date2);

            String monthFinal = String.format("%02d", month3);
            last_checked = findViewById(R.id.last_checked);
            last_checked.setText("Last checked: " + monthFinal + "/" + year);

            male_count_replacement = findViewById(R.id.male_count_replacement);
            female_count_replacement = findViewById(R.id.female_count_replacement);
            male_count_replacement.setText(myDb.getAllMaleFromReplacements(farm_id_local).toString());
            female_count_replacement.setText(myDb.getAllFemaleFromReplacements(farm_id_local).toString());

            male_count_brooder = findViewById(R.id.male_count_brooder);
            female_count_brooder = findViewById(R.id.female_count_brooder);
            male_count_brooder.setText(myDb.getAllMaleFromBrooders(farm_id_local).toString());
            female_count_brooder.setText(myDb.getAllFemaleFromBrooders(farm_id_local).toString());

        }

        layoutManager = new LinearLayoutManager(this);

        Exp_list.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                String string2 = Project_list.get(groupPosition);

                switch(string2){
                    case "Dashboard":
                        finish();
                        startActivity(getIntent());
                        break;

                    case "Pens":
                        Intent intent_create_pens = new Intent(DashBoardActivity.this, CreatePen.class);
                        startActivity(intent_create_pens);
                        break;

                    case "Generations and Lines":
                        Intent intent_main = new Intent(DashBoardActivity.this, CreateGenerationsAndLines.class);
                        startActivity(intent_main);
                        break;

                    case "Families":
                        Intent intent_families = new Intent(DashBoardActivity.this, CreateFamilies.class);
                        startActivity(intent_families);
                        break;

                    case "Breeders":
                        Intent intent_breeders = new Intent(DashBoardActivity.this, CreateBreeders.class);
                        startActivity(intent_breeders);
                        break;

                    case "Brooders":
                        Intent intent_brooders = new Intent(DashBoardActivity.this, CreateBrooders.class);
                        startActivity(intent_brooders);
                        break;

                    case "Replacements":
                        Intent intent_replacements = new Intent(DashBoardActivity.this, CreateReplacements.class);
                        startActivity(intent_replacements);
                        break;

                    case "Reports":
                        break;

                    case "Farm Settings":
                        Intent intent_settings = new Intent(DashBoardActivity.this, MainActivity.class);
                        startActivity(intent_settings);
                        break;

                    case "Log Out":
                        LogOutDialog dialogFragment = new LogOutDialog();

                        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

                        dialogFragment.show(ft, "dialog");
                }
                return false;
            }
        });

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.closed);
        mToolbar = (Toolbar)findViewById(R.id.nav_action);
        setSupportActionBar(mToolbar);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Dashboard");

    }

    //    -----------------------------------------------

    private void API_getFarmID(String email){
        APIHelper.getFarmID("getFarmID/"+email, new BaseJsonHttpResponseHandler<Object>() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response){

                String farm_id;

                farm_id = rawJsonResponse;
                farm_id = farm_id.replaceAll("\\[", "").replaceAll("\\]","");

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
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonResponse, Object response){
                Toast.makeText(getApplicationContext(), "Failed in getting farm ID ", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable{
                return null;
            }
        });
    }

    private void API_getFarmInfo(String farm_id){
        APIHelper.getFarmInfo("getFarmInfo/"+farm_id, new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response){


                myDb = new DatabaseHelper(getApplicationContext());
                rawJsonResponse = rawJsonResponse.replaceAll("\\[", "").replaceAll("\\]","");
                Gson gson = new Gson();

                try{
                    FarmInfo farmInfo = gson.fromJson(rawJsonResponse, FarmInfo.class);

                    Cursor cursor = myDb.getAllDataFromFarms(Integer.parseInt(farm_id));
                    cursor.moveToFirst();
                    if(cursor.getCount() != 0){
                        if(cursor.getInt(0) != farmInfo.getId()){
                            boolean isInserted = myDb.insertDataFarm(farmInfo.getId(), farmInfo.getName(), farmInfo.getCode(), farmInfo.getAddress(), farmInfo.getBatching_week(), farmInfo.getBreedable_id());
                        }else{
                            Toast.makeText(DashBoardActivity.this, "Farm Info already exists in your account", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        boolean isInserted = myDb.insertDataFarm(farmInfo.getId(), farmInfo.getName(), farmInfo.getCode(), farmInfo.getAddress(), farmInfo.getBatching_week(), farmInfo.getBreedable_id());
                        if(!isInserted){
                            Toast.makeText(DashBoardActivity.this, "Farm Info already exists in your account", Toast.LENGTH_SHORT).show();
                        }
                    }

                }catch (Exception e){}
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonResponse, Object response){

                Toast.makeText(getApplicationContext(), "Failed getting farm info ", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable{
                return null;
            }
        });
    }

    private void API_getPen(String farm_id){
        APIHelper.getPen("getPen/"+farm_id, new BaseJsonHttpResponseHandler<Object>() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response){

                Gson gson = new Gson();
                ArrayList<Pen> arrayList_pen = new ArrayList<>();
                ArrayList<Integer> arrayList_brooder_pen_id = new ArrayList<>();

                try{

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

                            if(isInserted)
                                if (pen.getPen_type().equals("brooder"))
                                    arrayList_brooder_pen_id.add(pen.getId());

                        }
                    }

                    if (!arrayList_brooder_pen_id.isEmpty())
                        API_getBrooderInventory(arrayList_brooder_pen_id);

                }catch (Exception e){
                    Log.d(debugTag, "Exception in Pen API caught");
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonResponse, Object response){
                Toast.makeText(getApplicationContext(), "Failed to fetch Pens from web database", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable{
                return null;
            }
        });
    }

    private void API_getGeneration(String farm_id){
        APIHelper.getGeneration("getGeneration/"+farm_id, new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {

                Gson gson = new Gson();
                try{
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

                }catch (Exception e){
                    Log.d(debugTag, "Exception in Generations API caught");
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonResponse, Object response){

                Toast.makeText(getApplicationContext(), "Failed to fetch Generations from web database", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable{
                return null;
            }
        });
    }

    private void API_getLine(String generation_id){

        APIHelper.getLine("getLine/"+generation_id, new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response){

                Gson gson = new Gson();

                try{

                    JSONLine jsonLine = gson.fromJson(rawJsonResponse, JSONLine.class);
                    ArrayList<Line> arrayList_line = jsonLine.getData();
                    ArrayList<Integer> arrayList_line_id = new ArrayList<>();

                    for(Line line : arrayList_line){
                        Cursor cursor = myDb.getAllDataFromLineWhereID(line.getId());

                        if(cursor.getCount() == 0){

                            boolean isInserted = myDb.insertDataLineWithID(
                                    line.getId(),
                                    line.getLine_number(),
                                    1,
                                    Integer.parseInt(generation_id)
                            );

                            if(isInserted) arrayList_line_id.add(line.getId());
                        }
                    }

                    if (!arrayList_line_id.isEmpty())
                        API_getFamily(arrayList_line_id);

                }catch (Exception e){
                    Log.d(debugTag, "Exception in Lines API caught");
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonResponse, Object response){

                Toast.makeText(getApplicationContext(), "Failed to fetch Lines from web database", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable{
                return null;
            }
        });
    }

    private void API_getFamily(ArrayList<Integer> arrayList_line_id){
        APIHelper.getFamily("getFamily/", new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response){

                Gson gson = new Gson();

                Cursor cursor_line = myDb.getAllDataFromLine();
                cursor_line.moveToFirst();

                try{
                    JSONFamily1 jsonFamily1 = gson.fromJson(rawJsonResponse, JSONFamily1.class);
                    ArrayList<Family1> arrayList_family1 = jsonFamily1.getData();
                    ArrayList<Integer> arrayList_family1_id = new ArrayList<>();

                    for (Family1 family : arrayList_family1) {

                        if(arrayList_line_id.contains(family.getLine_id())) {

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


                                if(isInserted){
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

                }catch (Exception e){
                    Log.d(debugTag, "Exception in Family API caught");
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonResponse, Object response){

                Toast.makeText(getApplicationContext(), "Failed to fetch Families from web database ", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable{
                return null;
            }
        });
    }

    private void API_getFamilyForDisplay(String farm_id){
        APIHelper.getFamily("getFamilyForDisplay/"+farm_id, new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response){

                Gson gson = new Gson();

                try{
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

                }catch (Exception e){
                    Log.d(debugTag, "Exception in FamilyForDisplay API caught");
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonResponse, Object response){

                Toast.makeText(getApplicationContext(), "Failed to fetch Family For Display from web database ", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable{
                return null;
            }
        });
    }



    private void API_getBrooder(ArrayList<Integer> arrayList_family_id){
        APIHelper.getBrooder("getBrooder/", new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response){

                Gson gson = new Gson();
                try{
                    JSONBrooder jsonBrooder = gson.fromJson(rawJsonResponse, JSONBrooder.class);
                    ArrayList<Brooders> arrayList_brooder = jsonBrooder.getData();

                    for(Brooders brooder: arrayList_brooder){

                        if(arrayList_family_id.contains(brooder.getBrooder_family_number())){

                            Cursor cursor = myDb.getAllDataFromBroodersWhereID(brooder.getId());
                            cursor.moveToFirst();

                            if(cursor.getCount() == 0){

                                boolean isInserted = myDb.insertDataBrooderWithID(
                                        brooder.getId(),
                                        brooder.getBrooder_family_number(),
                                        brooder.getBrooder_date_added(),
                                        brooder.getBrooder_deleted_at()
                                );

                            }

                        }

                    }

                }catch (Exception e){}

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonResponse, Object response){
                Toast.makeText(getApplicationContext(), "Failed to fetch Brooders Inventory from web database ", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable{
                return null;
            }
        });
    }

    private void API_getBrooderInventory(ArrayList<Integer> arrayList_pen_id){
        APIHelper.getBrooderInventory("getBrooderInventory/", new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response){

                Gson gson = new Gson();
                try{
                    JSONBrooderInventory jsonBrooderInventory = gson.fromJson(rawJsonResponse, JSONBrooderInventory.class);
                    ArrayList<Brooder_Inventory> arrayList_brooderInventory = jsonBrooderInventory.getData();
                    ArrayList<Integer> arrayList_brooderInventory_id = new ArrayList<>();

                    for(Brooder_Inventory brooder_inventory : arrayList_brooderInventory){

                        if(arrayList_pen_id.contains(brooder_inventory.getBrooder_inv_pen())){
                            Cursor cursor = myDb.getAllDataFromBrooderInventoryWhereID(brooder_inventory.getId());
                            cursor.moveToFirst();

                            if(cursor.getCount() == 0){

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

                                if(isInserted)
                                    arrayList_brooderInventory_id.add(brooder_inventory.getId());

                            }

                        }

                    }

                    if (!arrayList_brooderInventory_id.isEmpty()) {
                        API_getBrooderFeeding(arrayList_brooderInventory_id);
                        API_getBrooderGrowth(arrayList_brooderInventory_id);
                    }

                }catch (Exception e){}

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonResponse, Object response){
                Toast.makeText(getApplicationContext(), "Failed to fetch Brooders Inventory from web database ", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable{
                return null;
            }
        });
    }

    private void API_getBrooderFeeding(ArrayList<Integer> arrayList_brooderInventory_id){
        APIHelper.getBrooderFeeding("getBrooderFeeding/", new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response){

                Gson gson = new Gson();
                try {
                    JSONBrooderFeeding jsonBrooderFeeding = gson.fromJson(rawJsonResponse, JSONBrooderFeeding.class);
                    ArrayList<Brooder_FeedingRecords> arrayList_brooderFeeding = jsonBrooderFeeding.getData();

                    for (Brooder_FeedingRecords feedingRecords : arrayList_brooderFeeding) {

                        Cursor cursor = myDb.getAllDataFromBrooderFeedingRecordsWhereFeedingID(feedingRecords.getId());
                        cursor.moveToFirst();

                        if(arrayList_brooderInventory_id.contains(feedingRecords.getBrooder_feeding_inventory_id())) {

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

                }catch (Exception e){

                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonResponse, Object response){

                Toast.makeText(getApplicationContext(), "Failed to fetch Brooders Inventory from web database ", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable{
                return null;
            }
        });
    }

    private void API_getBrooderGrowth(ArrayList<Integer> arrayList_brooderInventory_id){
        APIHelper.getBrooderGrowth("getBrooderGrowth/", new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response){

                Gson gson = new Gson();
                try{
                    JSONBrooderGrowth jsonBrooderGrowth = gson.fromJson(rawJsonResponse, JSONBrooderGrowth.class);
                    ArrayList<Brooder_GrowthRecords> arrayList_brooderGrowthRecords = jsonBrooderGrowth.getData();


                    for(Brooder_GrowthRecords brooder_growthRecords : arrayList_brooderGrowthRecords) {

                        Cursor cursor = myDb.getAllDataFromBrooderGrowthRecordsWhereGrowthID(brooder_growthRecords.getId());
                        cursor.moveToFirst();

                        if(arrayList_brooderInventory_id.contains(brooder_growthRecords.getBrooder_growth_inventory_id())) {

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

                }catch (Exception e){}

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonResponse, Object response){

                Toast.makeText(getApplicationContext(), "Failed to fetch Brooders Growth Records from web database ", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable{
                return null;
            }
        });
    }



    private void API_getBreeder(ArrayList<Integer> arrayList_family_id){
        APIHelper.getBreeder("getBreeder/", new BaseJsonHttpResponseHandler<Object>() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response){

                Gson gson = new Gson();
                try{


                    JSONBreeder jsonBreeder = gson.fromJson(rawJsonResponse, JSONBreeder.class);
                    ArrayList<Breeders> arrayList_breeder = jsonBreeder.getData();
                    ArrayList<Integer> arrayList_breeder_id = new ArrayList<>();

                    for (Breeders breeder : arrayList_breeder) {

                        if(arrayList_family_id.contains(breeder.getFamily_number())) {

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

                }catch (Exception e){
                    Log.d("JSON", e.toString());
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

    private void API_getBreederInventory(ArrayList<Integer> arrayList_breeder_id){
        APIHelper.getBreederInventory("getBreederInventory/", new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response){

                Gson gson = new Gson();

                try{
                    JSONBreederInventory jsonBreederInventory = gson.fromJson(rawJsonResponse, JSONBreederInventory.class);
                    ArrayList<Breeder_Inventory> arrayList_breederInventory = jsonBreederInventory.getData();
                    ArrayList<Integer> arrayList_breederInventory_id = new ArrayList<>();

                    for (Breeder_Inventory breeder_inventory : arrayList_breederInventory) {

                        if(arrayList_breeder_id.contains(breeder_inventory.getBreeder_inv_breeder_id())) {

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

                                if(isInserted)
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
                    }

                }catch (Exception e){}


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonResponse, Object response){

                Toast.makeText(getApplicationContext(), "Failed to fetch Breeder Inventory from web database ", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable{
                return null;
            }
        });
    }

    private void API_getBreederFeeding(ArrayList<Integer> arrayList_breederInventory_id){

        //TODO: UPDATE hardcoded API url
        APIHelper.getBreederFeeding("getBreederFeeding?page=3", new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response){

                Gson gson = new Gson();
                try{
                    JSONBreederFeeding jsonBreederFeeding = gson.fromJson(rawJsonResponse, JSONBreederFeeding.class);
                    ArrayList<Breeder_FeedingRecords> arrayList_breederFeeding = jsonBreederFeeding.getData();

                    for (Breeder_FeedingRecords feedingRecords : arrayList_breederFeeding) {

                        if(arrayList_breederInventory_id.contains(feedingRecords.getBreeder_feeding_inventory_id())) {

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

                }catch (Exception e){

                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonResponse, Object response){
                Toast.makeText(getApplicationContext(), "Failed to fetch Breeder Feeding from web database ", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable{
                return null;
            }
        });
    }

    private void API_getEggProduction(ArrayList<Integer> arrayList_breederInventory_id){
        APIHelper.getEggProduction("getEggProduction/", new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response){

                Gson gson = new Gson();
                try{
                    JSONEggProduction jsonBreeder = gson.fromJson(rawJsonResponse, JSONEggProduction.class);
                    ArrayList<Egg_Production> arrayList_eggProduction = jsonBreeder.getData();

                    for (Egg_Production egg_production : arrayList_eggProduction) {

                        if(arrayList_breederInventory_id.contains(egg_production.getEgg_breeder_inv_id())) {

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

                }catch (Exception e){}
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

    private void API_getHatcheryRecords(ArrayList<Integer> arrayList_breederInventory_id){
        APIHelper.getHatcheryRecords("getHatcheryRecords/", new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response){

                Gson gson = new Gson();
                try {
                    JSONHatchery jsonBreeder = gson.fromJson(rawJsonResponse, JSONHatchery.class);
                    ArrayList<Hatchery_Records> arrayList_hatchery_records = jsonBreeder.getData();

                    for (Hatchery_Records hatchery_records : arrayList_hatchery_records) {

                        if(arrayList_breederInventory_id.contains(hatchery_records.getBreeder_inv_id())) {

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

                }catch (Exception e){}

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

    private void API_getEggQuality(ArrayList<Integer> arrayList_breederInventory_id){
        APIHelper.getEggQuality("getEggQuality/", new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response){

                Gson gson = new Gson();
                try{
                    JSONEggQuality jsonBreeder = gson.fromJson(rawJsonResponse, JSONEggQuality.class);
                    ArrayList<Egg_Quality> arrayList_eggQuality = jsonBreeder.getData();


                    for (Egg_Quality egg_quality : arrayList_eggQuality) {

                        if(arrayList_breederInventory_id.contains(egg_quality.getEgg_breeder_inv_id())) {

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

                }catch (Exception e){}

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


    private void API_getPhenoMorphos(ArrayList<Integer> arrayList_inventory_id) {
        APIHelper.getPhenoMorphos("getPhenoMorphos/", new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response){

                Gson gson = new Gson();
                try{
                    JSONPhenoMorphos jsonReplacement = gson.fromJson(rawJsonResponse, JSONPhenoMorphos.class);
                    ArrayList<Pheno_Morphos> arrayList_phenoMorphos = jsonReplacement.getData();

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
                                    if (pheno_morphos.getBreeder_inventory() != null)
                                        Log.d(debugTag, "Breeder PM: " + pheno_morphos.getId() + " " + pheno_morphos.getBreeder_inventory());
                                    else
                                        Log.d(debugTag, "Replacement PM: " + pheno_morphos.getId() + " " + pheno_morphos.getReplacement_inventory());
                                }
                            }

                        }

                    }
                }catch (Exception e){}

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
                try{
                    JSONPhenoMorphoValues jsonReplacement = gson.fromJson(rawJsonResponse, JSONPhenoMorphoValues.class);
                    ArrayList <Breeder_PhenoMorphoView> arrayList_brooder = jsonReplacement.getData();

                    for (int i = 0; i < arrayList_brooder.size(); i++) {
                        //check if generation to be inserted is already in the database
                        Cursor cursor = myDb.getAllDataFromPhenoMorphoRecordsWithID(arrayList_brooder.get(i).getId());
                        cursor.moveToFirst();

                        if (cursor.getCount() == 0) {


                            boolean isInserted = myDb.insertPhenoMorphoRecordsWithID(arrayList_brooder.get(i).getId(), arrayList_brooder.get(i).getGender(), arrayList_brooder.get(i).getTag(), arrayList_brooder.get(i).getPheno_record(), arrayList_brooder.get(i).getMorpho_record(), arrayList_brooder.get(i).getDate(), arrayList_brooder.get(i).getDeleted_at());
                            // Toast.makeText(ReplacementPhenoMorphoViewActivity.this, "Pheno and Morphos Values Added", Toast.LENGTH_SHORT).show();
                        }

                    }
                }catch (Exception e){}




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


    private void API_getReplacement(ArrayList<Integer> arrayList_family_id) {
        APIHelper.getReplacement("getReplacement/", new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response){

                Gson gson = new Gson();
                try{
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
                }catch (Exception e){}


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonResponse, Object response){

                Toast.makeText(getApplicationContext(), "Failed to fetch Brooders from web database ", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable{
                return null;
            }
        });
    }

    private void API_getReplacementInventory(ArrayList<Integer> arrayList_replacement_id) {
        APIHelper.getReplacementInventory("getReplacementInventory/", new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response){

                Gson gson = new Gson();
                try{
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

                    API_getReplacementFeeding(arrayList_replacementInventory_id);
                    API_getPhenoMorphos(arrayList_replacementInventory_id);
                }catch (Exception e){}


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonResponse, Object response){
                Toast.makeText(getApplicationContext(), "Failed to fetch Replacement Inventory from web database ", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable{
                return null;
            }
        });
    }

    private void API_getReplacementFeeding(ArrayList<Integer> replacementInventory_id) {
        APIHelper.getReplacementFeeding("getReplacementFeeding/", new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response){


                Gson gson = new Gson();
                try{
                    JSONReplacementFeeding jsonBrooderInventory = gson.fromJson(rawJsonResponse, JSONReplacementFeeding.class);
                    ArrayList<Replacement_FeedingRecords> arrayList_replacementFeeding = jsonBrooderInventory.getData();


                    for (Replacement_FeedingRecords replacement_feedingRecords : arrayList_replacementFeeding) {

                        if (replacementInventory_id.contains(replacement_feedingRecords.getReplacement_feeding_inventory_id())) {

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

                                if (isInserted)
                                    Log.d(debugTag, "REP FEED ID: " + replacement_feedingRecords.getId());

                            }

                        }

                    }
                }catch (Exception e){}



            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonResponse, Object response){

                Toast.makeText(getApplicationContext(), "Failed to fetch Brooders Inventory from web database ", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable{
                return null;
            }
        });
    }

    private void API_getReplacementGrowth() {
        APIHelper.getReplacementGrowth("getReplacementGrowth/", new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response){


                Gson gson = new Gson();
                try{
                    JSONReplacementGrowth jsonBrooderInventory = gson.fromJson(rawJsonResponse, JSONReplacementGrowth.class);
                    ArrayList<Replacement_GrowthRecords> arrayList_brooderInventory = jsonBrooderInventory.getData();


                    for (int i = 0; i < arrayList_brooderInventory.size(); i++) {
                        //check if generation to be inserted is already in the database
                        Cursor cursor = myDb.getAllDataFromReplacementGrowthRecordsWhereGrowthID(arrayList_brooderInventory.get(i).getId());
                        cursor.moveToFirst();

                        if (cursor.getCount() == 0) {


                            boolean isInserted = myDb.insertDataBrooderGrowthRecordsWithID(arrayList_brooderInventory.get(i).getId(), arrayList_brooderInventory.get(i).getReplacement_growth_inventory_id(), arrayList_brooderInventory.get(i).getReplacement_growth_collection_day(), arrayList_brooderInventory.get(i).getReplacement_growth_date_collected(), arrayList_brooderInventory.get(i).getReplacement_growth_male_quantity(), arrayList_brooderInventory.get(i).getReplacement_growth_male_weight(), arrayList_brooderInventory.get(i).getReplacement_growth_female_quantity(), arrayList_brooderInventory.get(i).getReplacement_growth_female_weight(), arrayList_brooderInventory.get(i).getReplacement_growth_total_quantity(), arrayList_brooderInventory.get(i).getReplacement_growth_total_weight(), arrayList_brooderInventory.get(i).getReplacement_growth_deleted_at());
                            //Toast.makeText(BrooderInventoryActivity.this, "oyoyooyoy", Toast.LENGTH_SHORT).show();
                        }

                    }

                }catch (Exception e){}


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonResponse, Object response){

                Toast.makeText(getApplicationContext(), "Failed to fetch Brooders Inventory from web database ", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable{
                return null;
            }
        });
    }


    private void API_getMortalityAndSales() {
        APIHelper.getMortalityAndSales("getMortalityAndSales/", new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response){

                Gson gson = new Gson();
                try{
                    JSONMortalityAndSales jsonBrooder = gson.fromJson(rawJsonResponse, JSONMortalityAndSales.class);
                    ArrayList<Mortality_Sales> arrayList_brooder = jsonBrooder.getData();

                    for (int i = 0; i < arrayList_brooder.size(); i++) {
                        //check if generation to be inserted is already in the database
                        Cursor cursor = myDb.getAllDataFromMortandSalesRecordsWithID(arrayList_brooder.get(i).getId());
                        cursor.moveToFirst();

                        if (cursor.getCount() == 0) {


                            boolean isInserted = myDb.insertDataMortalityAndSalesWithID(arrayList_brooder.get(i).getId(), arrayList_brooder.get(i).getDate(), arrayList_brooder.get(i).getBreeder_id(), arrayList_brooder.get(i).getReplaement_id(), arrayList_brooder.get(i).getBrooder_id(), arrayList_brooder.get(i).getType(), arrayList_brooder.get(i).getCategory(), arrayList_brooder.get(i).getPrice(), arrayList_brooder.get(i).getMale_count(), arrayList_brooder.get(i).getFemale_count(), arrayList_brooder.get(i).getTotal(), arrayList_brooder.get(i).getReason(), arrayList_brooder.get(i).getRemarks(), arrayList_brooder.get(i).getDeleted_at());

                        }

                    }
                }catch (Exception e){}

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonResponse, Object response){

                Toast.makeText(getApplicationContext(), "Failed to fetch Mortality and Sales from web ", Toast.LENGTH_SHORT).show();
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
    public boolean onOptionsItemSelected(MenuItem item) {

        if(mToggle.onOptionsItemSelected(item)){
            return true;

        }
        return super.onOptionsItemSelected(item);
    }

    public void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    @Override
    public void onBackPressed() {


        finish();
        startActivity(getIntent());

    }
}
