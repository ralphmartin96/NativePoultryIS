package com.example.cholomanglicmot.nativechickenandduck.ReplacementsDirectory;

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


public class CreateReplacements extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private Toolbar mToolbar;
    private ImageButton create_pen;
    private Button show_data_button;
    private Button delete_pen_table;
    private EditText replacement_search_bar;
    LinkedHashMap<String, List<String>> Project_category;
    List<String> Project_list;
    ExpandableListView Exp_list;
    ProjectAdapter adapter;
    DatabaseHelper myDb;

    ArrayList<String> list = new ArrayList<String>();

    RecyclerView recyclerView;
    RecyclerView.Adapter recycler_adapter;
    RecyclerView.LayoutManager layoutManager;

    ArrayList<Replacement_Pen> arrayList_replacement_pens = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_replacements);

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
                        Intent intent_main = new Intent(CreateReplacements.this, DashBoardActivity.class);
                        startActivity(intent_main);
                        break;
                    case "Pens":
                        Intent intent_pens = new Intent(CreateReplacements.this, CreatePen.class);
                        startActivity(intent_pens);
                        break;
                    case "Generations and Lines":
                        Intent intent_generations_and_lines = new Intent(CreateReplacements.this, CreateGenerationsAndLines.class);
                        startActivity(intent_generations_and_lines);
                        break;
                    case "Families":
                        Intent intent_families = new Intent(CreateReplacements.this, CreateFamilies.class);
                        startActivity(intent_families);
                        break;
                    case "Breeders":
                        Intent intent_breeders = new Intent(CreateReplacements.this, CreateBreeders.class);
                        startActivity(intent_breeders);
                        break;
                    case "Brooders":
                        Intent intent_brooders = new Intent(CreateReplacements.this, CreateBrooders.class);
                        startActivity(intent_brooders);
                        break;
                    case "Replacements":
                        finish();
                        startActivity(getIntent());
                        break;
                     case "Reports":
                        break;

                    case "Farm Settings":
                        Intent intent = new Intent(CreateReplacements.this, MainActivity.class);
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
        getSupportActionBar().setTitle("Create Replacements");

        local_getReplacement();

        recycler_adapter = new RecyclerAdapter_Replacement_Pen(arrayList_replacement_pens);
        recyclerView.setAdapter(recycler_adapter);
        recycler_adapter.notifyDataSetChanged();

    }

    private void local_getReplacement() {

        Cursor cursor_replacement_pen = myDb.getReplacementsFromPen();
        cursor_replacement_pen.moveToFirst();

        if (cursor_replacement_pen.getCount() == 0) {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        } else {

            cursor_replacement_pen.moveToFirst();

            do {
                Replacement_Pen replacement_pen = new Replacement_Pen(
                        cursor_replacement_pen.getString(2),
                        cursor_replacement_pen.getInt(5),
                        cursor_replacement_pen.getInt(4) - cursor_replacement_pen.getInt(5)
                );

                arrayList_replacement_pens.add(replacement_pen);

            } while (cursor_replacement_pen.moveToNext());

            Collections.sort(arrayList_replacement_pens, Replacement_Pen.penComparator);

        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void API_addReplacement(RequestParams requestParams){
        APIHelperAsync.addReplacementFamily("addReplacementFamily", requestParams, new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response){
                Toast.makeText(getApplicationContext(), "Successfully synced replacements to web", Toast.LENGTH_SHORT).show();
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

    private void API_updateReplacement(){

        APIHelperAsync.getReplacement("getReplacement/", new BaseJsonHttpResponseHandler<Object>() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response){

                Gson gson = new Gson();
                JSONReplacement jsonBrooderInventory = gson.fromJson(rawJsonResponse, JSONReplacement.class);
                ArrayList<Replacements> arrayListBrooderInventoryWeb = jsonBrooderInventory.getData();

                ArrayList<Replacements> arrayListBrooderInventoryLocal = new ArrayList<>();

                Cursor cursor_brooder_inventory = myDb.getAllDataFromReplacements();
                cursor_brooder_inventory.moveToFirst();
                if(cursor_brooder_inventory.getCount() != 0){
                    do {

                        Replacements brooder_inventory = new Replacements(cursor_brooder_inventory.getInt(0),cursor_brooder_inventory.getInt(1), cursor_brooder_inventory.getString(2), cursor_brooder_inventory.getString(3));
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

                    Cursor cursor = myDb.getAllDataFromReplacementsWhereID(id_to_sync.get(i));
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

                    API_addReplacement(requestParams);



                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonResponse, Object response){

               // Toast.makeText(getApplicationContext(), "Failed to fetch Brooders Inventory from web database ", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable{
                return null;
            }
        });
    }

    private void API_getReplacement(){
        APIHelperAsync.getReplacement("getReplacement/", new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response){

                Gson gson = new Gson();
                JSONReplacement jsonReplacement = gson.fromJson(rawJsonResponse, JSONReplacement.class);
                ArrayList <Replacements> arrayList_brooder = jsonReplacement.getData();

                for (int i = 0; i < arrayList_brooder.size(); i++) {
                    //check if generation to be inserted is already in the database
                    Cursor cursor = myDb.getAllDataFromReplacementsWhereID(arrayList_brooder.get(i).getId());
                    cursor.moveToFirst();

                    if (cursor.getCount() == 0) {

                        boolean isInserted = myDb.insertDataReplacementWithID(arrayList_brooder.get(i).getId(), arrayList_brooder.get(i).getReplacement_family_number(), arrayList_brooder.get(i).getReplacement_date_added(), arrayList_brooder.get(i).getReplacement_deleted_at());
                        Toast.makeText(CreateReplacements.this, "Replacements Added", Toast.LENGTH_SHORT).show();
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
