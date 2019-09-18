package com.example.cholomanglicmot.nativechickenandduck.PensDirectory;

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
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
import com.example.cholomanglicmot.nativechickenandduck.GenerationsAndLinesDirectory.CreateGenerationsAndLines;
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
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import de.hdodenhof.circleimageview.CircleImageView;


public class CreatePen extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private Toolbar mToolbar;
    private FloatingActionButton create_pen;
    private Button show_data_button;
    private Button delete_pen_table;
    String farm_id;
    Integer farm_id2=0;
    ArrayList<Pen> arrayList_pen;
    ArrayList<Pen> arrayList_brooder_pen;
    ArrayList<Pen> arrayList_grower_pen;
    ArrayList<Pen> arrayList_layer_pen;

    LinkedHashMap<String, List<String>> Project_category;
    List<String> Project_list;
    ExpandableListView Exp_list;
    ProjectAdapter adapter;
    DatabaseHelper myDb;

    RecyclerView recyclerView;
    RecyclerView.Adapter recycler_adapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Pen> arrayList = new ArrayList<>();
    String farmID;
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_pen);

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


        Exp_list = findViewById(R.id.exp_list);
        Project_category = DataProvider.getInfo();
        Project_list =  new ArrayList<String>(Project_category.keySet());
        adapter = new ProjectAdapter(this, Project_category, Project_list);
        Exp_list.setAdapter(adapter);
        create_pen = findViewById(R.id.open_dialog);

        myDb = new DatabaseHelper(this);

        recyclerView = (RecyclerView)findViewById(R.id.recyclerView1);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);


        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                if (dy < 0) {
                    create_pen.show();

                } else if (dy > 0) {
                    create_pen.hide();
                }
            }
        });


        create_pen.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                CreatePenDialog newFragment = new CreatePenDialog();
                newFragment.show(getSupportFragmentManager(), "CreatePenDialog");

            }
        });


        Exp_list.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                String string2 = Project_list.get(groupPosition);

                switch(string2){
                    case "Dashboard":
                        Intent intent_main = new Intent(CreatePen.this, DashBoardActivity.class);
                        startActivity(intent_main);
                        break;
                    case "Pens":
                        finish();
                        startActivity(getIntent());
                        break;
                    case "Generations and Lines":
                        Intent intent_generations_and_lines = new Intent(CreatePen.this, CreateGenerationsAndLines.class);
                        startActivity(intent_generations_and_lines);
                        break;
                    case "Families":
                        Intent intent_families = new Intent(CreatePen.this, CreateFamilies.class);
                        startActivity(intent_families);
                        break;
                    case "Breeders":
                        Intent intent_breeders = new Intent(CreatePen.this, CreateBreeders.class);
                        startActivity(intent_breeders);
                        break;
                    case "Brooders":
                        Intent intent_brooders = new Intent(CreatePen.this, CreateBrooders.class);
                        startActivity(intent_brooders);
                        break;
                    case "Replacements":
                        Intent intent_replacements = new Intent(CreatePen.this, CreateReplacements.class);
                        startActivity(intent_replacements);
                        break;
                      case "Reports":
                        break;

                    case "Farm Settings":
                        Intent intent = new Intent(CreatePen.this, MainActivity.class);
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
        getSupportActionBar().setTitle("Create Pens");

        Cursor cursor_farm_id = myDb.getFarmIDFromUsers(email);
        cursor_farm_id.moveToFirst();

        if(cursor_farm_id.getCount() != 0){
            farm_id2 = cursor_farm_id.getInt(0);
        }

        farm_id = farm_id2.toString();

        local_getPen();

        recycler_adapter = new RecyclerAdapter_Pen(arrayList);
        recyclerView.setAdapter(recycler_adapter);
        recycler_adapter.notifyDataSetChanged();

    }

    private void local_getPen(){

        arrayList = new ArrayList<Pen>();

        Cursor cursor = myDb.getAllDataFromPen();
        cursor.moveToFirst();

        if(cursor.getCount() == 0)
            Toast.makeText(this,"No data", Toast.LENGTH_SHORT).show();
        else {
            do {
                String type = "";

                switch(cursor.getString(3).toUpperCase()){
                    case "BROODER":
                        type = "Brooder";
                        break;
                    case "GROWER":
                        type = "Grower";
                        break;
                    case "LAYER":
                        type = "Layer";
                        break;
                }

                Pen pen = new Pen(
                        cursor.getInt(0),
                        cursor.getString(2),
                        type, //cursor.getString(3),
                        cursor.getInt(4),
                        cursor.getInt(5),
                        cursor.getInt(1),
                        cursor.getInt(6)
                );

                arrayList.add(pen);
            }while (cursor.moveToNext());
        }

        Collections.sort(arrayList, Pen.penComparator);

//        recycler_adapter = new RecyclerAdapter_Pen(arrayList);
//        recyclerView.setAdapter(recycler_adapter);
//        recycler_adapter.notifyDataSetChanged();
    }

    private void API_addPen(RequestParams requestParams){
        APIHelper.addPen("addPen", requestParams, new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response){
                Toast.makeText(getApplicationContext(), "Successfully synced pen to web", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonResponse, Object response){

                Toast.makeText(getApplicationContext(), "Failed to sync Pen to web", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable{
                return null;
            }
        });
    }

    //TODO: remove API's for web fetching
    private void API_updatePen(String farm_id){

        APIHelper.getPen("getPen/"+farm_id, new BaseJsonHttpResponseHandler<Object>() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response){

                Gson gson = new Gson();
                JSONPen jsonBrooderInventory = gson.fromJson(rawJsonResponse, JSONPen.class);
                ArrayList<Pen> arrayListBrooderInventoryWeb = jsonBrooderInventory.getData();

                ArrayList<Pen> arrayListBrooderInventoryLocal = new ArrayList<>();

                Cursor cursor_brooder_inventory = myDb.getAllDataFromPen();
                cursor_brooder_inventory.moveToFirst();

                if(cursor_brooder_inventory.getCount() != 0){
                    do {
                        Pen pen = new Pen(
                                cursor_brooder_inventory.getInt(0), //id
                                cursor_brooder_inventory.getString(2), //pen number
                                cursor_brooder_inventory.getString(3), //pen type
                                cursor_brooder_inventory.getInt(4), //pen inventory
                                cursor_brooder_inventory.getInt(5), // pen capacity
                                cursor_brooder_inventory.getInt(1), //farm id
                                cursor_brooder_inventory.getInt(6) //is active
                        );
                        arrayListBrooderInventoryLocal.add(pen);
                    } while (cursor_brooder_inventory.moveToNext());
                }

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

                    Cursor cursor = myDb.getAllDataFromPenWhereID(id_to_sync.get(i));
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
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonResponse, Object response){

                //Toast.makeText(getApplicationContext(), "Failed to sync pens from web database ", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable{
                return null;
            }
        });
    }

    private void TEST_updatePen(String farm_id){

        APIHelper.getPen("getPen/"+farm_id, new BaseJsonHttpResponseHandler<Object>() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response){

                Gson gson = new Gson();
                JSONPen jsonBrooderInventory = gson.fromJson(rawJsonResponse, JSONPen.class);
                ArrayList<Pen> arrayListBrooderInventoryLocal = arrayList_pen;
                ArrayList<Pen> arrayListBrooderInventoryWeb = jsonBrooderInventory.getData();

                ArrayList<Integer> id_local = new ArrayList<>();
                ArrayList<Integer> id_web = new ArrayList<>();
                ArrayList<Integer> id_to_sync = new ArrayList<>();

                for(int i=0;i<arrayList_pen.size();i++){
                    id_local.add(arrayList_pen.get(i).getId());
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

                    Cursor cursor = myDb.getAllDataFromPenWhereID(id_to_sync.get(i));
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
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonResponse, Object response){

                Toast.makeText(getApplicationContext(), "Failed to connect to web database", Toast.LENGTH_SHORT).show();
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
                JSONPen jsonPen = gson.fromJson(rawJsonResponse, JSONPen.class);
                arrayList_pen = jsonPen.getData();

                for (int i = 0; i < arrayList_pen.size(); i++) {
                    //check if generation to be inserted is already in the database
                    Cursor cursor1 = myDb.getAllDataFromPenWhereID(arrayList_pen.get(i).getId());
                    cursor1.moveToFirst();

                    Pen pen = new Pen(
                            arrayList_pen.get(i).getId(),
                            arrayList_pen.get(i).getPen_number(),
                            arrayList_pen.get(i).getPen_type(),
                            arrayList_pen.get(i).getPen_inventory(),
                            arrayList_pen.get(i).getPen_capacity(),
                            arrayList_pen.get(i).getFarm_id(),
                            arrayList_pen.get(i).getIs_active()
                    );
                    arrayList.add(pen);
                }

//                recycler_adapter = new RecyclerAdapter_Pen(arrayList);
//                recyclerView.setAdapter(recycler_adapter);
//                recycler_adapter.notifyDataSetChanged();

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
