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
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cholomanglicmot.nativechickenandduck.APIHelper;
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
    private Button show_data_button;
    private Button delete_pen_table;
    Integer farm_id;
    Integer fam_id=0;
    String farm_code=null;

    LinkedHashMap<String, List<String>> Project_category;
    List<String> Project_list;
    ExpandableListView Exp_list;
    ProjectAdapter adapter;
    DatabaseHelper myDb;

    RecyclerView recyclerView;
    RecyclerView.Adapter recycler_adapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Breeders> arrayListBreeders = new ArrayList<>();
    ArrayList<Breeder_Inventory> arrayListBreederInventory = new ArrayList<>();
    ArrayList<Breeder_Inventory> arrayListBreederInventory2 = new ArrayList<>();


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

        Cursor cursor_farm_id = myDb.getFarmIDFromUsers(email);
        cursor_farm_id.moveToFirst();
        if(cursor_farm_id.getCount() != 0){
            farm_id = cursor_farm_id.getInt(0);
        }



      /*  Exp_list.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                //  Toast.makeText(getBaseContext(),Project_category.get(Project_list.get(groupPosition)).get(childPosition) + " from category" + Project_list.get(groupPosition) + "is selected", Toast.LENGTH_SHORT).show();

                String string = Project_category.get(Project_list.get(groupPosition)).get(childPosition);
                switch (string){
                    case "Generation":
                        Intent intent_breeder_generation = new Intent(CreateBreeders.this, BreederGeneration.class);
                        startActivity(intent_breeder_generation);
                        break;
                    case "Family Records":
                        Intent intent_breeder_family_records = new Intent(CreateBreeders.this, BreederFamilyRecords.class);
                        startActivity(intent_breeder_family_records);
                        break;
                    case "Daily Records":
                        Intent intent_breeder_daily_records = new Intent(CreateBreeders.this, BreederDailyRecords.class);
                        startActivity(intent_breeder_daily_records);
                        break;
                    case "Hatchery Records":
                        Intent intent_breeder_hatchery_records = new Intent(CreateBreeders.this, BreederHatcheryRecords.class);
                        startActivity(intent_breeder_hatchery_records);
                        break;
                    case "Egg Quality Records":
                        Intent intent_breeder_egg_quality_records = new Intent(CreateBreeders.this, BreederEggQualityRecords.class);
                        startActivity(intent_breeder_egg_quality_records);
                        break;
                    case "Add Replacement Stocks":
                        Intent intent_replacement_individual_record_add = new Intent(CreateBreeders.this, ReplacementIndividualRecordAdd.class);
                        startActivity(intent_replacement_individual_record_add);
                        break;
                    case "Phenotypic and Morphometric":
                        Intent intent_replacement_phenotypic_morphometric = new Intent(CreateBreeders.this, ReplacementPhenotypicMorphometric.class);
                        startActivity(intent_replacement_phenotypic_morphometric);
                        break;
                    case "Feeding Record":
                        Intent intent_replacement_feeding_record = new Intent(CreateBreeders.this, ReplacementFeedingRecord.class);
                        startActivity(intent_replacement_feeding_record);
                        break;
                    case "Growth Performance":
                        Intent intent_brooder_growth_performance = new Intent(CreateBreeders.this, BroodersGrowthPerformance.class);
                        startActivity(intent_brooder_growth_performance);
                        break;
                    case "Feeding Records":
                        Intent intent_brooder_feeding_records = new Intent(CreateBreeders.this, BrooderFeedingRecords.class);
                        startActivity(intent_brooder_feeding_records);
                        break;
                }
                return false;
            }


        });
*/
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
        getSupportActionBar().setTitle("Create Breeders");





        ///////////////////////-----DATABASE

        if (isNetworkAvailable()) {
            //if internet is available, load data from web database


            //HARDCODED KASI WALA KA PANG DATABASE NA NANDUN EMAIL MO
            ;
            API_updateBreederInventory();
            API_updateBreeder();
            API_getBreeder();
            API_getBreederInventory();


        }

        Cursor cursor_code = myDb.getAllDataFromFarms(farm_id);
        cursor_code.moveToFirst();
        if(cursor_code.getCount() != 0){
            farm_code = cursor_code.getString(2);
        }
        Cursor cursorBreederInv = myDb.getAllDataFromBreederInventory();
        cursorBreederInv.moveToFirst();
        if(cursorBreederInv.getCount()==0){
            Toast.makeText(this, "No breeder inventory data", Toast.LENGTH_SHORT).show();

        }else{
            //for getting breeders
            do{
                String breeder_tag = cursorBreederInv.getString(3);
                String is_deleted = cursorBreederInv.getString(12);

                if(breeder_tag.contains(farm_code) && is_deleted == null){
                    Breeder_Inventory breeder_inventory = new Breeder_Inventory(cursorBreederInv.getInt(0), cursorBreederInv.getInt(1), cursorBreederInv.getInt(2), cursorBreederInv.getString(3), cursorBreederInv.getString(4), cursorBreederInv.getInt(5), cursorBreederInv.getInt(6), cursorBreederInv.getInt(7),cursorBreederInv.getString(8), cursorBreederInv.getString(9),cursorBreederInv.getString(10),cursorBreederInv.getString(11),cursorBreederInv.getString(12));

                    arrayListBreederInventory2.add(breeder_inventory);
                }
            }while (cursorBreederInv.moveToNext());



            ////////////START
       /*     Cursor cursor = myDb.getAllDataFromBreeders();
            cursor.moveToFirst();

            if(cursor.getCount() == 0){
                //show message

            }else{

                do {

                    Breeders breeders = new Breeders(cursor.getInt(0), cursor.getInt(1), cursor.getInt(2), cursor.getString(3), cursor.getString(4));
                    arrayListBreeders.add(breeders);
                }while (cursor.moveToNext());

                do{
                    for(int i=0;i<arrayListBreeders.size();i++){

                        //getFamLineGen
                        //boolean isExists = "iPhone".indexOf("i");
                        if(arrayListBreeders.get(i).getId() == cursorBreederInv.getInt(1) && arrayListBreeders.get(i).getDeleted_at() == null){
                        //kunin yung breeder inventory tag tapos para madd sa arraylist, dapat it contains yung given na farm code.
                            Integer brooder_id = arrayListBreeders.get(i).getId();
                            Cursor cursor_fam_id = myDb.getAllDataFromBreedersWhereID(brooder_id);
                            cursor_fam_id.moveToFirst();
                            if(cursor_fam_id.getCount() != 0){
                                fam_id = cursor_fam_id.getInt(1);
                            }
                            String checker = myDb.getFamLineGen(fam_id);
                            if(checker != null){
                                Breeder_Inventory breeder_inventory = new Breeder_Inventory(cursorBreederInv.getInt(0), cursorBreederInv.getInt(1), cursorBreederInv.getInt(2), cursorBreederInv.getString(3), cursorBreederInv.getString(4), cursorBreederInv.getInt(5), cursorBreederInv.getInt(6), cursorBreederInv.getInt(7),cursorBreederInv.getString(8), cursorBreederInv.getString(9),cursorBreederInv.getString(10),cursorBreederInv.getString(11),cursorBreederInv.getString(12));

                                arrayListBreederInventory2.add(breeder_inventory);
                            }

                            //String deleted_at = cursorBreederInv.getString(9);


                        }
                    }

                }while(cursorBreederInv.moveToNext());
            }*/
            ///////// END

        }


        recycler_adapter = new RecyclerAdapter_Breeder(arrayListBreederInventory2);
        recyclerView.setAdapter(recycler_adapter);
        recycler_adapter.notifyDataSetChanged();
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
    private void API_getBreeder(){
        APIHelper.getBreeder("getBreeder/", new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response){

                Gson gson = new Gson();
                try{
                    JSONBreeder jsonBreeder = gson.fromJson(rawJsonResponse, JSONBreeder.class);
                    ArrayList<Breeders> arrayList_brooder = jsonBreeder.getData();

                    for (int i = 0; i < arrayList_brooder.size(); i++) {
                        //check if generation to be inserted is already in the database
                        Cursor cursor = myDb.getAllDataFromBreedersWhereID(arrayList_brooder.get(i).getId());
                        cursor.moveToFirst();

                        if (cursor.getCount() == 0) {

                            boolean isInserted = myDb.insertDataBreederWithID(arrayList_brooder.get(i).getId(), arrayList_brooder.get(i).getFamily_number(),arrayList_brooder.get(i).getFemale_family_number(), arrayList_brooder.get(i).getDate_added(), arrayList_brooder.get(i).getDeleted_at());
                            Toast.makeText(CreateBreeders.this, "Breeders Added", Toast.LENGTH_SHORT).show();
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

    private void API_getBreederInventory(){
        APIHelper.getBreederInventory("getBreederInventory/", new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response){

                Gson gson = new Gson();
                try{
                    JSONBreederInventory jsonBreederInventory = gson.fromJson(rawJsonResponse, JSONBreederInventory.class);
                    ArrayList<Breeder_Inventory> arrayList_brooderInventory = jsonBreederInventory.getData();

                    for (int i = 0; i < arrayList_brooderInventory.size(); i++) {
                        //check if generation to be inserted is already in the database
                        Cursor cursor = myDb.getAllDataFromBreederInventoryWhereID(arrayList_brooderInventory.get(i).getId());
                        cursor.moveToFirst();

                        if (cursor.getCount() == 0) {


                            boolean isInserted = myDb.insertDataBreederInventoryWithID(arrayList_brooderInventory.get(i).getId(), arrayList_brooderInventory.get(i).getBrooder_inv_brooder_id(), arrayList_brooderInventory.get(i).getBrooder_inv_pen(), arrayList_brooderInventory.get(i).getBrooder_inv_brooder_tag(),arrayList_brooderInventory.get(i).getBrooder_inv_batching_date(),arrayList_brooderInventory.get(i).getBrooder_male_quantity(),arrayList_brooderInventory.get(i).getBrooder_female_quantity(),arrayList_brooderInventory.get(i).getBrooder_total_quantity(), arrayList_brooderInventory.get(i).getBrooder_inv_last_update(), arrayList_brooderInventory.get(i).getBrooder_inv_deleted_at(), arrayList_brooderInventory.get(i).getBreeder_code(), arrayList_brooderInventory.get(i).getMale_wingband(), arrayList_brooderInventory.get(i).getFemale_wingband());

                        }

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
    private void API_addBreederInventory(RequestParams requestParams){
        APIHelper.addBreederInventory("addBreederInventory", requestParams, new BaseJsonHttpResponseHandler<Object>() {
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

        APIHelper.getBreederInventory("getBreederInventory/", new BaseJsonHttpResponseHandler<Object>() {

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
        APIHelper.addBreederFamily("addBreederFamily", requestParams, new BaseJsonHttpResponseHandler<Object>() {
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

        APIHelper.getBreeder("getBreeder/", new BaseJsonHttpResponseHandler<Object>() {

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
}
