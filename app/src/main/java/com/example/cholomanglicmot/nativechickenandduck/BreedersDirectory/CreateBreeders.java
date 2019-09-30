package com.example.cholomanglicmot.nativechickenandduck.BreedersDirectory;

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
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cholomanglicmot.nativechickenandduck.APIHelperAsync;
import com.example.cholomanglicmot.nativechickenandduck.BroodersDirectory.CreateBrooders;
import com.example.cholomanglicmot.nativechickenandduck.DashboardDirectory.DashBoardActivity;
import com.example.cholomanglicmot.nativechickenandduck.DashboardDirectory.LogOutDialog;
import com.example.cholomanglicmot.nativechickenandduck.DataProvider;
import com.example.cholomanglicmot.nativechickenandduck.DatabaseHelper;
import com.example.cholomanglicmot.nativechickenandduck.FamilyDirectory.CreateFamilies;
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
import java.util.LinkedHashMap;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import de.hdodenhof.circleimageview.CircleImageView;

//import com.squareup.picasso.Picasso;

public class CreateBreeders extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private Toolbar mToolbar;
    private FloatingActionButton create_pen;
    Integer farm_id;
    String farm_code=null;

    LinkedHashMap<String, List<String>> Project_category;
    List<String> Project_list;
    ExpandableListView Exp_list;
    ProjectAdapter adapter;
    DatabaseHelper myDb;

    RecyclerView recyclerView;
    RecyclerView.Adapter recycler_adapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Breeder_Inventory> arrayListBreederInventory = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_breeders);


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
        create_pen = findViewById(R.id.open_dialog);


        myDb = new DatabaseHelper(this);

        recyclerView = (RecyclerView)findViewById(R.id.recyclerView1);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        create_pen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CreateBreederDialog newFragment = new CreateBreederDialog();
                newFragment.show(getSupportFragmentManager(), "CreateBreederDialog");

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
                    create_pen.show();

                } else if (dy > 0) {
                    create_pen.hide();
                }
            }
        });

        Exp_list.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                String string2 = Project_list.get(groupPosition);

                switch(string2){
                    case "Dashboard":
                        Intent intent_main = new Intent(CreateBreeders.this, DashBoardActivity.class);
                        startActivity(intent_main);
                        break;
                    case "Pens":
                        Intent intent_pens = new Intent(CreateBreeders.this, CreatePen.class);
                        startActivity(intent_pens);
                        break;
                    case "Generations and Lines":
                        Intent intent_generations_and_lines = new Intent(CreateBreeders.this, CreateGenerationsAndLines.class);
                        startActivity(intent_generations_and_lines);
                        break;
                    case "Families":
                        Intent intent_families = new Intent(CreateBreeders.this, CreateFamilies.class);
                        startActivity(intent_families);
                        break;
                    case "Breeders":
                        finish();
                        startActivity(getIntent());
                        break;
                    case "Brooders":
                        Intent intent_brooders = new Intent(CreateBreeders.this, CreateBrooders.class);
                        startActivity(intent_brooders);
                        break;
                    case "Replacements":
                        Intent intent_replacements = new Intent(CreateBreeders.this, CreateReplacements.class);
                        startActivity(intent_replacements);
                        break;

                    case "Reports":
                        break;

                    case "Farm Settings":
                        Intent intent = new Intent(CreateBreeders.this, MainActivity.class);
                        startActivity(intent);
                        break;
                    case "Log Out":

                        if (isNetworkAvailable()) {
                            LogOutDialog dialogFragment = new LogOutDialog();

                            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

                            dialogFragment.show(ft, "dialog");
                        } else {
                            Toast.makeText(getApplicationContext(), "Logout failed. Check your internet", Toast.LENGTH_SHORT).show();
                        }
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
        getSupportActionBar().setTitle("Create Breeders");


        local_getBreederInventory();

        recycler_adapter = new RecyclerAdapter_Breeder(arrayListBreederInventory);
        recyclerView.setAdapter(recycler_adapter);
        recycler_adapter.notifyDataSetChanged();
    }

    private void local_getBreederInventory(){

        Cursor cursorBreederInv = myDb.getAllDataFromBreederInventory();
        cursorBreederInv.moveToFirst();

        if(cursorBreederInv.getCount()==0){
            Toast.makeText(this, "No breeder inventory data", Toast.LENGTH_SHORT).show();
        }else{
            do{

                if(cursorBreederInv.getString(12) == null) {

                    Breeder_Inventory breeder_inventory = new Breeder_Inventory(
                            cursorBreederInv.getInt(0),
                            cursorBreederInv.getInt(1),
                            cursorBreederInv.getInt(2),
                            cursorBreederInv.getString(3),
                            cursorBreederInv.getString(4),
                            cursorBreederInv.getInt(5),
                            cursorBreederInv.getInt(6),
                            cursorBreederInv.getInt(7),
                            cursorBreederInv.getString(8),
                            cursorBreederInv.getString(9),
                            cursorBreederInv.getString(10),
                            cursorBreederInv.getString(11),
                            cursorBreederInv.getString(12)
                    );

                    arrayListBreederInventory.add(breeder_inventory);

                }

            }while (cursorBreederInv.moveToNext());

        }

    }

    private void API_addBreederInventory(RequestParams requestParams){
        APIHelperAsync.addBreederInventory("addBreederInventory", requestParams, new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response){
                Toast.makeText(getApplicationContext(), "Successfully synced breeder inventory to web", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonResponse, Object response){

                Toast.makeText(getApplicationContext(), "Failed to sync breeder inventory to web", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable{
                return null;
            }
        });
    }

    private void API_updateBreederInventory(){

        APIHelperAsync.getBreederInventory("getBreederInventory/", new BaseJsonHttpResponseHandler<Object>() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response){

                Gson gson = new Gson();
                try{
                    JSONBreederInventory jsonBrooderInventory = gson.fromJson(rawJsonResponse, JSONBreederInventory.class);
                    ArrayList<Breeder_Inventory> arrayListBrooderInventoryWeb = jsonBrooderInventory.getData();

                    ArrayList<Breeder_Inventory> arrayListBrooderInventoryLocal = new ArrayList<>();

                    Cursor cursor_brooder_inventory = myDb.getAllDataFromBreederInventory();
                    cursor_brooder_inventory.moveToFirst();
                    if(cursor_brooder_inventory.getCount() != 0){
                        do {

                            Breeder_Inventory brooder_inventory = new Breeder_Inventory(cursor_brooder_inventory.getInt(0),cursor_brooder_inventory.getInt(1), cursor_brooder_inventory.getInt(2), cursor_brooder_inventory.getString(3),cursor_brooder_inventory.getString(4), cursor_brooder_inventory.getInt(5), cursor_brooder_inventory.getInt(6),cursor_brooder_inventory.getInt(7), cursor_brooder_inventory.getString(8), cursor_brooder_inventory.getString(9),cursor_brooder_inventory.getString(10), cursor_brooder_inventory.getString(11), cursor_brooder_inventory.getString(12));
                            arrayListBrooderInventoryLocal.add(brooder_inventory);
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

                        Cursor cursor = myDb.getAllDataFromBreederInventoryWhereID(id_to_sync.get(i));
                        cursor.moveToFirst();
                        Integer id = cursor.getInt(0);
                        Integer broodergrower_id = cursor.getInt(1);
                        Integer pen_id = cursor.getInt(2);
                        String broodergrower_tag = cursor.getString(3);
                        String batching_date = cursor.getString(4);
                        Integer number_male = cursor.getInt(5);
                        Integer number_female = cursor.getInt(6);
                        Integer total = cursor.getInt(7);
                        String  last_update = cursor.getString(8);
                        String deleted_at = cursor.getString(9);

                        RequestParams requestParams = new RequestParams();
                        requestParams.add("id", id.toString());
                        requestParams.add("breeder_id", broodergrower_id.toString());
                        requestParams.add("pen_id", pen_id.toString());
                        requestParams.add("breeder_tag", broodergrower_tag);
                        requestParams.add("batching_date", batching_date);
                        requestParams.add("number_male", number_male.toString());
                        requestParams.add("number_female", number_female.toString());
                        requestParams.add("total", total.toString());
                        requestParams.add("last_update", last_update);
                        requestParams.add("deleted_at", deleted_at);

                        //Toast.makeText(CreateBreeders.this, id_to_sync.get(i).toString(), Toast.LENGTH_SHORT).show();

                        API_addBreederInventory(requestParams);



                    }

                }catch (Exception e){}

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonResponse, Object response){

                Toast.makeText(getApplicationContext(), "Failed to fetch Breeders Inventory from web database ", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable{
                return null;
            }
        });
    }

    private void API_addBreeder(RequestParams requestParams){
        APIHelperAsync.addBreederFamily("addBreederFamily", requestParams, new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response){
                Toast.makeText(getApplicationContext(), "Successfully synced breeder to web", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonResponse, Object response){

                Toast.makeText(getApplicationContext(), "Failed to sync breeder to web", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable{
                return null;
            }
        });
    }

    private void API_updateBreeder(){

        APIHelperAsync.getBreeder("getBreeder/", new BaseJsonHttpResponseHandler<Object>() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response){

                Gson gson = new Gson();
                try{
                    JSONBreeder jsonBrooderInventory = gson.fromJson(rawJsonResponse, JSONBreeder.class);
                    ArrayList<Breeders> arrayListBrooderInventoryWeb = jsonBrooderInventory.getData();

                    ArrayList<Breeders> arrayListBrooderInventoryLocal = new ArrayList<>();

                    Cursor cursor_brooder_inventory = myDb.getAllDataFromBreeders();
                    cursor_brooder_inventory.moveToFirst();
                    if(cursor_brooder_inventory.getCount() != 0){
                        do {

                            Breeders brooder_inventory = new Breeders(cursor_brooder_inventory.getInt(0),cursor_brooder_inventory.getInt(1),cursor_brooder_inventory.getInt(2), cursor_brooder_inventory.getString(3), cursor_brooder_inventory.getString(4));
                            arrayListBrooderInventoryLocal.add(brooder_inventory);
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

                    Integer female_family_id;
                    for(int i=0;i<id_to_sync.size();i++){

                        Cursor cursor = myDb.getAllDataFromBreedersWhereID(id_to_sync.get(i));
                        cursor.moveToFirst();
                        Integer id = cursor.getInt(0);
                        Integer family_id = cursor.getInt(1);
                        female_family_id = cursor.getInt(2);
                        if(female_family_id == 0){
                            female_family_id = null;
                        }
                        String date_added = cursor.getString(3);
                        String deleted_at = cursor.getString(4);


                        RequestParams requestParams = new RequestParams();
                        requestParams.add("id", id.toString());
                        requestParams.add("family_id", family_id.toString());
                        requestParams.add("female_family_id", female_family_id.toString());
                        requestParams.add("date_added", date_added);
                        requestParams.add("deleted_at", deleted_at);

                        //Toast.makeText(BrooderInventoryActivity.this, id_to_sync.get(i).toString(), Toast.LENGTH_SHORT).show();

                        API_addBreeder(requestParams);



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

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
