package com.example.cholomanglicmot.nativechickenandduck.FamilyDirectory;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
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
import com.example.cholomanglicmot.nativechickenandduck.BreedersDirectory.CreateBreeders;
import com.example.cholomanglicmot.nativechickenandduck.BroodersDirectory.CreateBrooders;
import com.example.cholomanglicmot.nativechickenandduck.DashboardDirectory.DashBoardActivity;
import com.example.cholomanglicmot.nativechickenandduck.DashboardDirectory.LogOutDialog;
import com.example.cholomanglicmot.nativechickenandduck.DataProvider;
import com.example.cholomanglicmot.nativechickenandduck.DatabaseHelper;
import com.example.cholomanglicmot.nativechickenandduck.FarmSettingsDirectory.MainActivity;
import com.example.cholomanglicmot.nativechickenandduck.GenerationsAndLinesDirectory.CreateGenerationsAndLines;
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


public class CreateFamilies extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private Toolbar mToolbar;
    private FloatingActionButton create_family;
    String farm_id;
    ArrayList<Family> arrayList_family;
    ArrayList<Family1> arrayList_family1;
    //private Button create_family;


    LinkedHashMap<String, List<String>> Project_category;
    List<String> Project_list;
    ExpandableListView Exp_list;
    ProjectAdapter adapter;
    DatabaseHelper myDb;

    RecyclerView recyclerView;
    RecyclerView.Adapter recycler_adapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Family> arrayList = new ArrayList<>();
    String[] lines;
    Map<String, ArrayList<String>> line_dictionary = new HashMap<String, ArrayList<String>>();
    ArrayList<String> list = new ArrayList<String>();
    StringBuffer buffer = new StringBuffer();
    String line_number = null;
    String generation_number = null;
    Integer generation_id;
    boolean addGeneration=false;
    String validation_farm_id_string=null;
    Integer farm_id_INT;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_families);


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
        create_family = findViewById(R.id.open_dialog);


        //delete_pen_table = findViewById(R.id.delete_pen_table);
        myDb = new DatabaseHelper(this);

        recyclerView = (RecyclerView)findViewById(R.id.recyclerView1);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        create_family.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CreateFamilyDialog newFragment = new CreateFamilyDialog();
                newFragment.show(getSupportFragmentManager(), "CreateFamilyDialog");

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
                    create_family.show();

                } else if (dy > 0) {
                    create_family.hide();
                }
            }
        });


        Exp_list.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                String string2 = Project_list.get(groupPosition);

                switch(string2){
                    case "Dashboard":
                        Intent intent_main = new Intent(CreateFamilies.this, DashBoardActivity.class);
                        startActivity(intent_main);
                        break;

                    case "Pens":
                        Intent intent_create_pens = new Intent(CreateFamilies.this, CreatePen.class);
                        startActivity(intent_create_pens);
                        break;
                    case "Generations and Lines":
                        Intent intent_create_generation_and_lines = new Intent(CreateFamilies.this, CreateGenerationsAndLines.class);
                        startActivity(intent_create_generation_and_lines);
                        break;
                    case "Families":
                        finish();
                        startActivity(getIntent());
                        break;
                    case "Breeders":
                        Intent intent_breeders = new Intent(CreateFamilies.this, CreateBreeders.class);
                        startActivity(intent_breeders);
                        break;
                    case "Brooders":
                        Intent intent_brooders = new Intent(CreateFamilies.this, CreateBrooders.class);
                        startActivity(intent_brooders);
                        break;
                    case "Replacements":
                        Intent intent_replacements = new Intent(CreateFamilies.this, CreateReplacements.class);
                        startActivity(intent_replacements);
                        break;

                    case "Reports":
                        break;

                    case "Farm Settings":
                        Intent intent_settings = new Intent(CreateFamilies.this, MainActivity.class);
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
        getSupportActionBar().setTitle("Families");


        boolean isNetworkAvailable = isNetworkAvailable();
        if(isNetworkAvailable){
            //if internet is available, load data from web database

            API_getFarmID(email);
//            API_updateFamily();

        }

        Cursor cursor_farm_id = myDb.getFarmIDFromUsers(email);
        cursor_farm_id.moveToFirst();

        farm_id_INT = cursor_farm_id.getInt(0);

            Cursor cursor = myDb.getAllDataFromFamily();
            cursor.moveToFirst();

//-----DATABASE
            if(cursor.getCount() == 0){
                //show message
                Toast.makeText(this,"No data", Toast.LENGTH_SHORT).show();

            }else{

                do {

                    Integer validation_farm_id=0;
                    Integer line_id = cursor.getInt(3);
                    Cursor cursor1 = myDb.getDataFromLineWhereID(line_id);


                    cursor1.moveToFirst();

                    if(cursor1.getCount() != 0){

                        do{
                            line_number = cursor1.getString(1);
                            generation_id = cursor1.getInt(3);

                            Cursor cursor2 = myDb.getDataFromGenerationWhereID(generation_id);
                            cursor2.moveToFirst();
//ANG GAWIN MO NA LANG DITO, GAMITIN MO YUNG FARM_ID
                            if(cursor2.getCount() != 0){
                                validation_farm_id = cursor2.getInt(1);
                                //validation_farm_id_string = validation_farm_id.toString();
                                generation_number = cursor2.getString(2);
                                //addGeneration = true;
                            }

                            Integer is_active = cursor.getInt(2);
                            String family_number = cursor.getString(1);
                            //String validation_farm_id_string = validation_farm_id.toString();

                            Log.d("LINE&GEN", line_number+" "+generation_number);


                            if(is_active == 1 && line_number != null && generation_number != null/*&& validation_farm_id ==farm_id_INT*/ /*&& validation_farm_id_string.equals(farm_id)*/){
                                Family family = new Family(family_number, line_number, generation_number);
                                arrayList.add(family);

                            }

                        }while(cursor1.moveToNext());

                    }

                }while (cursor.moveToNext());

                recycler_adapter = new RecyclerAdapter_Family(arrayList);
                recyclerView.setAdapter(recycler_adapter);
                recycler_adapter.notifyDataSetChanged();

        }

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void API_getFarmID(String email){
        APIHelper.getFarmID("getFarmID/"+email, new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response){


                farm_id = rawJsonResponse;

                farm_id = farm_id.replaceAll("\\[", "").replaceAll("\\]","");

                API_getFamilyForDisplay(farm_id);

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

    private void API_getFamilyForDisplay(String farm_id){
        APIHelper.getFamilyForDisplay("getFamilyForDisplay/"+farm_id, new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response){

                Gson gson = new Gson();
                JSONFamily jsonFamily = gson.fromJson(rawJsonResponse, JSONFamily.class);
                arrayList_family = jsonFamily.getData();

                API_getFamily();

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonResponse, Object response){

                Toast.makeText(getApplicationContext(), "Failed to fetch Pens from web database ", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable{
                return null;
            }
        });
    }
    private void API_getFamily(){
        APIHelper.getFamily("getFamily/", new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response){
                Gson gson = new Gson();
                JSONFamily1 jsonFamily1 = gson.fromJson(rawJsonResponse, JSONFamily1.class);
                arrayList_family1 = jsonFamily1.getData();

                for (int i = 0; i < arrayList_family1.size(); i++) {
                    //check if generation to be inserted is already in the database
                    Cursor cursor = myDb.getAllDataFromFamilyWhereID(arrayList_family1.get(i).getId());
                    cursor.moveToFirst();

                    if (cursor.getCount() == 0) {
                        // API_getLine(arrayList_pen.get(i).getId().toString());
                        //edit insertDataGeneration function, dapat kasama yung primary key "id" kapag nilalagay sa database
                        boolean isInserted = myDb.insertDataFamilyWithID(arrayList_family1.get(i).getId(), arrayList_family1.get(i).getNumber(), arrayList_family1.get(i).getIs_active(), arrayList_family1.get(i).getLine_id(), arrayList_family1.get(i).getDeleted_at());
                    }

                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonResponse, Object response){

                Toast.makeText(getApplicationContext(), "Failed to fetch families from web database", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable{
                return null;
            }
        });
    }



    private void API_addFamily(RequestParams requestParams){
        APIHelper.addFamily("addFamily", requestParams, new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response){
                Toast.makeText(getApplicationContext(), "Successfully synced families to web", Toast.LENGTH_SHORT).show();
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
    private void API_updateFamily(){

        APIHelper.getFamily("getFamily/", new BaseJsonHttpResponseHandler<Object>() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response){

                Gson gson = new Gson();
                JSONFamily1 jsonBrooderInventory = gson.fromJson(rawJsonResponse, JSONFamily1.class);
                ArrayList<Family1> arrayListBrooderInventoryWeb = jsonBrooderInventory.getData();

                ArrayList<Family1> arrayListBrooderInventoryLocal = new ArrayList<>();

                Cursor cursor_brooder_inventory = myDb.getAllDataFromFamily();
                cursor_brooder_inventory.moveToFirst();
                if(cursor_brooder_inventory.getCount() != 0){
                    do {

                        Family1 family = new Family1(cursor_brooder_inventory.getInt(0), cursor_brooder_inventory.getString(1),  cursor_brooder_inventory.getInt(2), cursor_brooder_inventory.getInt(3), cursor_brooder_inventory.getString(4));
                        arrayListBrooderInventoryLocal.add(family);
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

                    Cursor cursor = myDb.getAllDataFromFamilyWhereID(id_to_sync.get(i));
                    cursor.moveToFirst();
                    Integer id = cursor.getInt(0);
                    String number = cursor.getString(1);
                    Integer is_active = cursor.getInt(2);
                    Integer line_id = cursor.getInt(3);
                    String deleted_at = cursor.getString(4);



                    RequestParams requestParams = new RequestParams();
                    requestParams.add("id", id.toString());
                    requestParams.add("number", number);
                    requestParams.add("is_active", is_active.toString());
                    requestParams.add("line_id", line_id.toString());
                    requestParams.add("deleted_at", deleted_at);


                    //Toast.makeText(CreateBreeders.this, id_to_sync.get(i).toString(), Toast.LENGTH_SHORT).show();

                    API_addFamily(requestParams);



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