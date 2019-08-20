package com.example.cholomanglicmot.nativechickenandduck.GenerationsAndLinesDirectory;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.internal.NavigationMenu;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cholomanglicmot.nativechickenandduck.APIHelper;
import com.example.cholomanglicmot.nativechickenandduck.BreedersDirectory.CreateBreeders;
import com.example.cholomanglicmot.nativechickenandduck.BroodersDirectory.CreateBrooders;
import com.example.cholomanglicmot.nativechickenandduck.DashboardDirectory.DashBoardActivity;
import com.example.cholomanglicmot.nativechickenandduck.DashboardDirectory.LogOutDialog;
import com.example.cholomanglicmot.nativechickenandduck.DataProvider;
import com.example.cholomanglicmot.nativechickenandduck.DatabaseHelper;
import com.example.cholomanglicmot.nativechickenandduck.FamilyDirectory.CreateFamilies;
import com.example.cholomanglicmot.nativechickenandduck.FarmSettingsDirectory.MainActivity;
import com.example.cholomanglicmot.nativechickenandduck.PensDirectory.CreatePen;
import com.example.cholomanglicmot.nativechickenandduck.ProjectAdapter;
import com.example.cholomanglicmot.nativechickenandduck.R;
import com.example.cholomanglicmot.nativechickenandduck.ReplacementsDirectory.CreateReplacements;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import cz.msebera.android.httpclient.Header;
import de.hdodenhof.circleimageview.CircleImageView;
import io.github.yavski.fabspeeddial.FabSpeedDial;

//import com.squareup.picasso.Picasso;

public class CreateGenerationsAndLines extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private Toolbar mToolbar;
    //private FabSpeedDial create_generation;
    private Button show_data_button;
    String farm_id;

    TextView sample;
    LinkedHashMap<String, List<String>> Project_category;
    List<String> Project_list;
    ExpandableListView Exp_list;
    ProjectAdapter adapter;
    DatabaseHelper myDb;

    RecyclerView recyclerView;
    RecyclerView.Adapter recycler_adapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Generation> arrayList = new ArrayList<>();
    ArrayList<Generation> arrayList_gen = new ArrayList<>();

    Map<String, ArrayList<String>> line_dictionary = new HashMap<String, ArrayList<String>>();
    ArrayList<String> list = new ArrayList<String>();
    Context context;
    String generation_number = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generations_and_lines);

        ////////////
        FirebaseAuth mAuth;

        mAuth = FirebaseAuth.getInstance();

        FirebaseUser user = mAuth.getCurrentUser();

        String name = user.getDisplayName();

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
        ///////////////////
        Exp_list = findViewById(R.id.exp_list);
        Project_category = DataProvider.getInfo();
        Project_list =  new ArrayList<String>(Project_category.keySet());
        adapter = new ProjectAdapter(this, Project_category, Project_list);
        Exp_list.setAdapter(adapter);

        FabSpeedDial fabSpeedDial = findViewById(R.id.fabSpeedDial);
        sample = findViewById(R.id.sample);

        myDb = new DatabaseHelper(this);

        recyclerView = (RecyclerView)findViewById(R.id.recyclerView1);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);



        fabSpeedDial.setMenuListener(new FabSpeedDial.MenuListener() {
            @Override
            public boolean onPrepareMenu(NavigationMenu navigationMenu) {
                return true;
            }

            @Override
            public boolean onMenuItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.create_generation_btn:

                        CreateGenerationDialog newFragment = new CreateGenerationDialog();
                        newFragment.show(getSupportFragmentManager(), "CreateGenerationDialog");
                        return true;


                    case R.id.create_line_btn:

                        CreateLineDialog newFragment1 = new CreateLineDialog();
                        newFragment1.show(getSupportFragmentManager(), "CreateFamilyDialog");
                        return true;


                }
                return true;


            }

            @Override
            public void onMenuClosed() {

            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                if (dy < 0) {
                    fabSpeedDial.show();

                } else if (dy > 0) {
                    fabSpeedDial.hide();
                }
            }
        });


        Exp_list.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                String string2 = Project_list.get(groupPosition);

                switch(string2){
                    case "Dashboard":
                        Intent intent_main = new Intent(CreateGenerationsAndLines.this, DashBoardActivity.class);
                        startActivity(intent_main);
                        break;

                    case "Pens":
                        Intent intent_create_pens = new Intent(CreateGenerationsAndLines.this, CreatePen.class);
                        startActivity(intent_create_pens);
                        break;
                    case "Generations and Lines":
                        finish();
                        startActivity(getIntent());
                        break;
                    case "Families":
                        Intent intent_families = new Intent(CreateGenerationsAndLines.this, CreateFamilies.class);
                        startActivity(intent_families);
                        break;
                    case "Breeders":
                        Intent intent_breeders = new Intent(CreateGenerationsAndLines.this, CreateBreeders.class);
                        startActivity(intent_breeders);
                        break;
                    case "Brooders":
                        Intent intent_brooders = new Intent(CreateGenerationsAndLines.this, CreateBrooders.class);
                        startActivity(intent_brooders);
                        break;
                    case "Replacements":
                        Intent intent_replacements = new Intent(CreateGenerationsAndLines.this, CreateReplacements.class);
                        startActivity(intent_replacements);
                        break;

                    case "Reports":
                        break;

                    case "Farm Settings":
                        Intent intent = new Intent(CreateGenerationsAndLines.this, MainActivity.class);
                        startActivity(intent);
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
        getSupportActionBar().setTitle("Generations and Lines");









        if(isNetworkAvailable()){
            //if internet is available, load data from web database



            //HARDCODED KASI WALA KA PANG DATABASE NA NANDUN EMAIL MO
            API_getFarmID(email);
            API_updateGeneration(farm_id);
            API_updateLine();





        }


            Cursor cursor = myDb.getAllDataFromGeneration();
            Cursor line_cursor = myDb.getAllDataFromLine();

//-----DATABASE
            if (cursor.getCount() == 0) {
                //show message
                Toast.makeText(this, "No generations", Toast.LENGTH_SHORT).show();
                return;
            } else {

                cursor.moveToFirst();
                do {
                            Integer farm_id_checker = cursor.getInt(0);
                            //Integer farm_id_from_db = Integer.parseInt(farm_id);
                           // if(farm_id_checker == farm_id_from_db){
                                Generation generation = new Generation(cursor.getString(2), cursor.getInt(4), cursor.getInt(0), cursor.getInt(1), cursor.getInt(3), cursor.getString(5));

                                arrayList.add(generation);
                          // }






                } while (cursor.moveToNext());
                cursor.close();

                line_cursor.moveToFirst();
                if (line_cursor.getCount() == 0) {
                    //show message
                     Toast.makeText(this,"No lines", Toast.LENGTH_SHORT).show();

                } else {

                    do {
                        Cursor cursor1 = myDb.getDataFromGenerationWhereID(line_cursor.getInt(3));
                        cursor1.moveToFirst();
                        if (cursor1.getCount() != 0) {
                            generation_number = cursor1.getString(2);
                        }
                        if (line_dictionary.containsKey(generation_number)) {
                            list = line_dictionary.get(generation_number);
                            list.add(line_cursor.getString(1));
                        } else {
                            ArrayList<String> list1 = new ArrayList<String>();
                            list1.add(line_cursor.getString(1));
                            line_dictionary.put(generation_number, list1);
                        }


                    } while (line_cursor.moveToNext());
                }


                recycler_adapter = new RecyclerAdapter_Generation(arrayList, line_dictionary);
                recyclerView.setAdapter(recycler_adapter);
                recycler_adapter.notifyDataSetChanged(); //does not work, kailangan may way ka para maupdate yung adapter mo



        }


    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    private void API_getGeneration(String farm_id){
        APIHelper.getGeneration("getGeneration/"+farm_id, new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {

                Gson gson = new Gson();
                JSONGeneration jsonGeneration = gson.fromJson(rawJsonResponse, JSONGeneration.class);
                arrayList_gen = jsonGeneration.getData();

                for (int i = 0; i < arrayList_gen.size(); i++) {
                    //check if generation to be inserted is already in the database
                    Cursor cursor = myDb.getDataFromGenerationWhereID(arrayList_gen.get(i).getId());
                    cursor.moveToFirst();

                    if (cursor.getCount() == 0) {
                        API_getLine(arrayList_gen.get(i).getId().toString());
                        //edit insertDataGeneration function, dapat kasama yung primary key "id" kapag nilalagay sa database
                        boolean isInserted = myDb.insertDataGenerationWithID(arrayList_gen.get(i).getId(), arrayList_gen.get(i).getFarm_id(), arrayList_gen.get(i).getGeneration_number(), arrayList_gen.get(i).getNumerical_generation(), arrayList_gen.get(i).getGeneration_status());
                    }

                }



            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonResponse, Object response){

                Toast.makeText(getApplicationContext(), "Failed ", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable{
                return null;
            }
        });
    }
    private void API_getLine(String generation_id){
       // for(int i = 0; i<arrayList_gen.size();i++){
            APIHelper.getLine("getLine/"+generation_id, new BaseJsonHttpResponseHandler<Object>() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response){


                    Gson gson = new Gson();
                    JSONLine jsonLine = gson.fromJson(rawJsonResponse, JSONLine.class);
                    ArrayList<Line> arrayList = jsonLine.getData();
                    for(int i=0;i<arrayList.size();i++){
                        Cursor cursor = myDb.getAllDataFromLineWhereID(arrayList.get(i).getId());
                        if(cursor.getCount() == 0){
                            //dapat insert mo kasama yung primary key "id"
                            //edit mo yung existing insertDataLine function tapos dapat pati primary key iniinsert mo kapag galing sa web yung data
                            boolean isInserted = myDb.insertDataLineWithID(arrayList.get(i).getId(),arrayList.get(i).getLine_number(),1,Integer.parseInt(generation_id));

                        }
                    }


                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonResponse, Object response){

                    Toast.makeText(getApplicationContext(), "Failed Lines ", Toast.LENGTH_SHORT).show();
                }

                @Override
                protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable{
                    return null;
                }
            });
        }


    private void API_getFarmID(String email){
        APIHelper.getFarmID("getFarmID/"+email, new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response){


                farm_id = rawJsonResponse;

                farm_id = farm_id.replaceAll("\\[", "").replaceAll("\\]","");

                API_getGeneration(farm_id);
              //  Toast.makeText(CreateGenerationsAndLines.this, "Generation and Lines loaded from database", Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonResponse, Object response){

                Toast.makeText(getApplicationContext(), "Failed ", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable{
                return null;
            }
        });
    }
    private void API_addGeneration(RequestParams requestParams){
        APIHelper.addGeneration("addGeneration", requestParams, new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response){
                Toast.makeText(getApplicationContext(), "Successfully synced generations to web", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonResponse, Object response){

              //  Toast.makeText(context, "Failed to add Generation to web", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable{
                return null;
            }
        });
    }
    private void API_updateGeneration(String farm_id){

        APIHelper.getGeneration("getGeneration/"+farm_id, new BaseJsonHttpResponseHandler<Object>() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response){

                Gson gson = new Gson();
                JSONGeneration jsonBrooderInventory = gson.fromJson(rawJsonResponse, JSONGeneration.class);
                ArrayList<Generation> arrayListBrooderInventoryWeb = jsonBrooderInventory.getData();

                ArrayList<Generation> arrayListBrooderInventoryLocal = new ArrayList<>();

                Cursor cursor_brooder_inventory = myDb.getAllDataFromGeneration();
                cursor_brooder_inventory.moveToFirst();
                if(cursor_brooder_inventory.getCount() != 0){
                    do {

                        Generation generation = new Generation(cursor_brooder_inventory.getString(2), cursor_brooder_inventory.getInt(4), cursor_brooder_inventory.getInt(0), cursor_brooder_inventory.getInt(1), cursor_brooder_inventory.getInt(3), cursor_brooder_inventory.getString(5));
                        arrayListBrooderInventoryLocal.add(generation);
                    } while (cursor_brooder_inventory.moveToNext());
                }




                //arrayListBrooderInventoryLocal contains all data from local database
                //arrayListBrooderInventoryWeb   contains all data from web database

                //put the ID of each brooder inventory to another arraylist
                ArrayList<Integer> id_local = new ArrayList<>();
                ArrayList<Integer> id_web = new ArrayList<>();
                ArrayList<Integer> id_to_sync = new ArrayList<>();

                for(int i=0;i<arrayListBrooderInventoryLocal.size();i++){
                    id_local.add(arrayListBrooderInventoryLocal.get(i).getId());
                }
                for(int i=0;i<arrayListBrooderInventoryWeb.size();i++){
                    id_web.add(arrayListBrooderInventoryWeb.get(i).getId());
                }


                for (int i=0;i<id_local.size();i++){
                    if(!id_web.contains(id_local.get(i))){ //if id_web does not contain the current value of i, add it the an arraylist
                        id_to_sync.add(id_local.get(i));
                    }
                }


                for(int i=0;i<id_to_sync.size();i++){

                    Cursor cursor = myDb.getAllDataFromGenerationWhereID(id_to_sync.get(i));
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
                //Toast.makeText(CreateGenerationsAndLines.this, id_to_sync.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonResponse, Object response){

                Toast.makeText(getApplicationContext(), "Failed to sync from web database ", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable{
                return null;
            }
        });
    }
    private void API_addLine(RequestParams requestParams){
        APIHelper.addLine("addLine", requestParams, new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response){
                Toast.makeText(getApplicationContext(), "Successfully synced lines to web", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonResponse, Object response){

                Toast.makeText(getApplicationContext(), "Failed to add Line to web", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable{
                return null;
            }
        });
    }
    private void API_updateLine(){

        APIHelper.getLine("getLine/", new BaseJsonHttpResponseHandler<Object>() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response){

                Gson gson = new Gson();
                JSONLine jsonBrooderInventory = gson.fromJson(rawJsonResponse, JSONLine.class);
                ArrayList<Line> arrayListBrooderInventoryWeb = jsonBrooderInventory.getData();

                ArrayList<Line> arrayListBrooderInventoryLocal = new ArrayList<>();

                Cursor cursor_brooder_inventory = myDb.getAllDataFromLine();
                cursor_brooder_inventory.moveToFirst();
                if(cursor_brooder_inventory.getCount() != 0){
                    do {

                        Line line = new Line(cursor_brooder_inventory.getInt(0), cursor_brooder_inventory.getString(1),  cursor_brooder_inventory.getInt(3));
                        arrayListBrooderInventoryLocal.add(line);
                    } while (cursor_brooder_inventory.moveToNext());
                }




                //arrayListBrooderInventoryLocal contains all data from local database
                //arrayListBrooderInventoryWeb   contains all data from web database

                //put the ID of each brooder inventory to another arraylist
                ArrayList<Integer> id_local = new ArrayList<>();
                ArrayList<Integer> id_web = new ArrayList<>();
                ArrayList<Integer> id_to_sync = new ArrayList<>();

                for(int i=0;i<arrayListBrooderInventoryLocal.size();i++){
                    id_local.add(arrayListBrooderInventoryLocal.get(i).getId());
                }
                for(int i=0;i<arrayListBrooderInventoryWeb.size();i++){
                    id_web.add(arrayListBrooderInventoryWeb.get(i).getId());
                }


                for (int i=0;i<id_local.size();i++){
                    if(!id_web.contains(id_local.get(i))){ //if id_web does not contain the current value of i, add it the an arraylist
                        id_to_sync.add(id_local.get(i));
                    }
                }


                for(int i=0;i<id_to_sync.size();i++){

                    Cursor cursor = myDb.getAllDataFromLineWhereID(id_to_sync.get(i));
                    cursor.moveToFirst();
                    Integer id = cursor.getInt(0);
                    String number = cursor.getString(1);
                    Integer is_active = cursor.getInt(2);
                    Integer generation_id = cursor.getInt(3);
                    String deleted_at = cursor.getString(4);



                    RequestParams requestParams = new RequestParams();
                    requestParams.add("id", id.toString());
                    requestParams.add("number", number.toString());
                    requestParams.add("is_active", is_active.toString());
                    requestParams.add("generation_id", generation_id.toString());
                    requestParams.add("deleted_at", deleted_at);


                    //Toast.makeText(CreateBreeders.this, id_to_sync.get(i).toString(), Toast.LENGTH_SHORT).show();

                    API_addLine(requestParams);



                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonResponse, Object response){

                //Toast.makeText(getApplicationContext(), "Failed to fetch Breeders Inventory from web database ", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable{
                return null;
            }
        });
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
