
package com.example.cholomanglicmot.nativechickenandduck.BroodersDirectory;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
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
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cholomanglicmot.nativechickenandduck.APIHelperAsync;
import com.example.cholomanglicmot.nativechickenandduck.BreedersDirectory.CreateBreeders;
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

public class CreateBrooders extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private Toolbar mToolbar;
    private ImageButton create_pen;
    private Button show_data_button;
    private Button delete_pen_table;
    private EditText brooder_search_bar;
    LinkedHashMap<String, List<String>> Project_category;
    List<String> Project_list;
    ExpandableListView Exp_list;
    ProjectAdapter adapter;
    DatabaseHelper myDb;
    ArrayList<Brooders> arrayList_brooder;
    //private String brooder_pen = getArguments().getString("brooder_pen");

    //Map<String, ArrayList<String>> brooder_inventory_dictionary = new HashMap<String, ArrayList<String>>();
    ArrayList<String> list = new ArrayList<String>();

    RecyclerView recyclerView;
    RecyclerView.Adapter recycler_adapter;
    RecyclerView.LayoutManager layoutManager;

    ArrayList<Brooders_Pen> arrayList = new ArrayList<>();
    ArrayList<Brooder_Inventory> arrayList_brooderInventory = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_brooders);


        ////////////
        FirebaseAuth mAuth;

        mAuth = FirebaseAuth.getInstance();

        FirebaseUser user = mAuth.getCurrentUser();

        String name = user.getDisplayName();

        String email = user.getEmail();

        Uri photo = user.getPhotoUrl();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View hView = navigationView.getHeaderView(0);
        TextView nav_user = (TextView) hView.findViewById(R.id.textView8);
        TextView nav_email = (TextView) hView.findViewById(R.id.textView9);
        CircleImageView circleImageView = hView.findViewById(R.id.display_photo);
        nav_user.setText(name);
        Picasso.get().load(photo).into(circleImageView);
        nav_email.setText(email);

        Exp_list = findViewById(R.id.exp_list);
        Project_category = DataProvider.getInfo();
        Project_list = new ArrayList<String>(Project_category.keySet());
        adapter = new ProjectAdapter(this, Project_category, Project_list);
        Exp_list.setAdapter(adapter);


        myDb = new DatabaseHelper(this);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView1);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);


        Exp_list.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                String string2 = Project_list.get(groupPosition);

                switch (string2) {
                    case "Dashboard":
                        Intent intent_main = new Intent(CreateBrooders.this, DashBoardActivity.class);
                        startActivity(intent_main);
                        break;
                    case "Pens":
                        Intent intent_pens = new Intent(CreateBrooders.this, CreatePen.class);
                        startActivity(intent_pens);
                        break;
                    case "Generations and Lines":
                        Intent intent_generations_and_lines = new Intent(CreateBrooders.this, CreateGenerationsAndLines.class);
                        startActivity(intent_generations_and_lines);
                        break;
                    case "Families":
                        Intent intent_families = new Intent(CreateBrooders.this, CreateFamilies.class);
                        startActivity(intent_families);
                        break;
                    case "Breeders":
                        Intent intent_breeders = new Intent(CreateBrooders.this, CreateBreeders.class);
                        startActivity(intent_breeders);
                        break;
                    case "Brooders":
                        finish();
                        startActivity(getIntent());
                        break;
                    case "Replacements":
                        Intent intent_replacements = new Intent(CreateBrooders.this, CreateReplacements.class);
                        startActivity(intent_replacements);
                        break;


                    case "Reports":
                        break;

                    case "Farm Settings":
                        Intent intent = new Intent(CreateBrooders.this, MainActivity.class);
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
        mToolbar = (Toolbar) findViewById(R.id.nav_action);
        setSupportActionBar(mToolbar);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Create Brooders");

        StringBuffer buffer = new StringBuffer();
        Cursor cursor_pen = myDb.getAllDataFromPen();
        cursor_pen.moveToFirst();

        local_getBrooder();

        recycler_adapter = new RecyclerAdapter_Brooder_Pen(arrayList);
        recyclerView.setAdapter(recycler_adapter);
        recycler_adapter.notifyDataSetChanged();

    }

    private void local_getBrooder(){
        Cursor cursor_brooder_pen = myDb.getBroodersFromPen();

        if (cursor_brooder_pen.getCount() == 0) {
            Toast.makeText(this, "No Brooder data", Toast.LENGTH_SHORT).show();
            return;
        } else {

            cursor_brooder_pen.moveToFirst();
            do {
                Brooders_Pen broodersPen = new Brooders_Pen(
                        cursor_brooder_pen.getString(2),
                        cursor_brooder_pen.getInt(5),
                        cursor_brooder_pen.getInt(4) - cursor_brooder_pen.getInt(5)
                );
                arrayList.add(broodersPen);
            } while (cursor_brooder_pen.moveToNext());

        }

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void API_getBrooder(){
        APIHelperAsync.getBrooder("getBrooder/", new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response){

                Gson gson = new Gson();
                JSONBrooder jsonBrooder = gson.fromJson(rawJsonResponse, JSONBrooder.class);
                arrayList_brooder = jsonBrooder.getData();

                for (int i = 0; i < arrayList_brooder.size(); i++) {
                    //check if generation to be inserted is already in the database
                    Cursor cursor = myDb.getAllDataFromBroodersWhereID(arrayList_brooder.get(i).getId());
                    cursor.moveToFirst();

                    if (cursor.getCount() == 0) {

                        boolean isInserted = myDb.insertDataBrooderWithID(arrayList_brooder.get(i).getId(), arrayList_brooder.get(i).getBrooder_family_number(), arrayList_brooder.get(i).getBrooder_date_added(), arrayList_brooder.get(i).getBrooder_deleted_at());
                        Toast.makeText(CreateBrooders.this, "Brooders Added", Toast.LENGTH_SHORT).show();
                    }

                }

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

    private void API_getBrooderInventory(){
        APIHelperAsync.getBrooderInventory("getBrooderInventory/", new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response){

                Gson gson = new Gson();
                JSONBrooderInventory jsonBrooderInventory = gson.fromJson(rawJsonResponse, JSONBrooderInventory.class);
                arrayList_brooderInventory = jsonBrooderInventory.getData();

                for (int i = 0; i < arrayList_brooderInventory.size(); i++) {
                    //check if generation to be inserted is already in the database
                    Cursor cursor = myDb.getAllDataFromBrooderInventoryWhereID(arrayList_brooderInventory.get(i).getId());
                    cursor.moveToFirst();

                    if (cursor.getCount() == 0) {


                        boolean isInserted = myDb.insertDataBrooderInventoryWithID(arrayList_brooderInventory.get(i).getId(), arrayList_brooderInventory.get(i).getBrooder_inv_brooder_id(), arrayList_brooderInventory.get(i).getBrooder_inv_pen(), arrayList_brooderInventory.get(i).getBrooder_inv_brooder_tag(),arrayList_brooderInventory.get(i).getBrooder_inv_batching_date(),arrayList_brooderInventory.get(i).getBrooder_male_quantity(),arrayList_brooderInventory.get(i).getBrooder_female_quantity(),arrayList_brooderInventory.get(i).getBrooder_total_quantity(), arrayList_brooderInventory.get(i).getBrooder_inv_last_update(), arrayList_brooderInventory.get(i).getBrooder_inv_deleted_at());

                    }

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

    private void API_updateBrooderInventory(){

        APIHelperAsync.getBrooderInventory("getBrooderInventory/", new BaseJsonHttpResponseHandler<Object>() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response){

                Gson gson = new Gson();
                JSONBrooderInventory jsonBrooderInventory = gson.fromJson(rawJsonResponse, JSONBrooderInventory.class);
                ArrayList<Brooder_Inventory> arrayListBrooderInventoryWeb = jsonBrooderInventory.getData();

                ArrayList<Brooder_Inventory> arrayListBrooderInventoryLocal = new ArrayList<>();

                Cursor cursor_brooder_inventory = myDb.getAllDataFromBrooderInventory();
                cursor_brooder_inventory.moveToFirst();
                if(cursor_brooder_inventory.getCount() != 0){
                    do {

                        Brooder_Inventory brooder_inventory = new Brooder_Inventory(cursor_brooder_inventory.getInt(0),cursor_brooder_inventory.getInt(1), cursor_brooder_inventory.getInt(2), cursor_brooder_inventory.getString(3),cursor_brooder_inventory.getString(4), cursor_brooder_inventory.getInt(5), cursor_brooder_inventory.getInt(6),cursor_brooder_inventory.getInt(7), cursor_brooder_inventory.getString(8), cursor_brooder_inventory.getString(9));
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

                    Cursor cursor = myDb.getAllDataFromBrooderInventoryWhereID(id_to_sync.get(i));
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
                    requestParams.add("broodergrower_id", broodergrower_id.toString());
                    requestParams.add("pen_id", pen_id.toString());
                    requestParams.add("broodergrower_tag", broodergrower_tag);
                    requestParams.add("batching_date", batching_date);
                    requestParams.add("number_male", number_male.toString());
                    requestParams.add("number_female", number_female.toString());
                    requestParams.add("total", total.toString());
                    requestParams.add("last_update", last_update);
                    requestParams.add("deleted_at", deleted_at);

                    //Toast.makeText(BrooderInventoryActivity.this, id_to_sync.get(i).toString(), Toast.LENGTH_SHORT).show();

                    API_addBrooderInventory(requestParams);



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

    private void API_addBrooderInventory(RequestParams requestParams){
        APIHelperAsync.addBrooderInventory("addBrooderInventory", requestParams, new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response){
                Toast.makeText(getApplicationContext(), "Successfully synced brooder inventory to web", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonResponse, Object response){


            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable{
                return null;
            }
        });
    }

    private void API_addBrooder(RequestParams requestParams){
        APIHelperAsync.addBrooderFamily("addBrooderFamily", requestParams, new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response){
                      Toast.makeText(getApplicationContext(), "Successfully synced brooders to web", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonResponse, Object response){

                //Toast.makeText(getContext(), "Failed to add to web", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable{
                return null;
            }
        });
    }

    private void API_updateBrooder(){

        APIHelperAsync.getBrooder("getBrooder/", new BaseJsonHttpResponseHandler<Object>() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response){

                Gson gson = new Gson();
                JSONBrooder jsonBrooderInventory = gson.fromJson(rawJsonResponse, JSONBrooder.class);
                ArrayList<Brooders> arrayListBrooderInventoryWeb = jsonBrooderInventory.getData();

                ArrayList<Brooders> arrayListBrooderInventoryLocal = new ArrayList<>();

                Cursor cursor_brooder_inventory = myDb.getAllDataFromBrooders();
                cursor_brooder_inventory.moveToFirst();
                if(cursor_brooder_inventory.getCount() != 0){
                    do {

                        Brooders brooder_inventory = new Brooders(cursor_brooder_inventory.getInt(0),cursor_brooder_inventory.getInt(1), cursor_brooder_inventory.getString(2), cursor_brooder_inventory.getString(3));
                        arrayListBrooderInventoryLocal.add(brooder_inventory);
                    } while (cursor_brooder_inventory.moveToNext());
                }

                cursor_brooder_inventory.close();


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

                    Cursor cursor = myDb.getAllDataFromBroodersWhereID(id_to_sync.get(i));
                    cursor.moveToFirst();
                    Integer id = cursor.getInt(0);
                    Integer family_id = cursor.getInt(1);
                    String date_added = cursor.getString(2);
                    String deleted_at = cursor.getString(3);


                    RequestParams requestParams = new RequestParams();
                    requestParams.add("id", id.toString());
                    requestParams.add("family_id", family_id.toString());
                    requestParams.add("date_added", date_added);
                    requestParams.add("deleted_at", deleted_at);

                    //Toast.makeText(BrooderInventoryActivity.this, id_to_sync.get(i).toString(), Toast.LENGTH_SHORT).show();

                    API_addBrooder(requestParams);



                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonResponse, Object response){

                //Toast.makeText(getApplicationContext(), "Failed to fetch Brooders Inventory from web database ", Toast.LENGTH_SHORT).show();
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


}
