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

import com.example.cholomanglicmot.nativechickenandduck.APIHelperAsync;
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


public class CreateGenerationsAndLines extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private Toolbar mToolbar;
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
        getSupportActionBar().setTitle("Generations and Lines");

        if(isNetworkAvailable()){

            API_getFarmID(email);
//            API_updateGeneration(farm_id);
//            API_updateLine();
        }

        local_getGenerations();

        recycler_adapter = new RecyclerAdapter_Generation(arrayList, line_dictionary);
        recyclerView.setAdapter(recycler_adapter);
        recycler_adapter.notifyDataSetChanged();

    }

    private void local_getGenerations() {

        Cursor cursor_generation = myDb.getAllDataFromGeneration();

        if (cursor_generation.getCount() == 0){
            Toast.makeText(this, "No generations", Toast.LENGTH_SHORT).show();
            return;
        }else{

            cursor_generation.moveToFirst();

            do {
                Generation generation = new Generation(
                        cursor_generation.getString(2),
                        cursor_generation.getInt(4),
                        cursor_generation.getInt(0),
                        cursor_generation.getInt(1),
                        cursor_generation.getInt(3),
                        cursor_generation.getString(5)
                );
                arrayList.add(generation);

            } while (cursor_generation.moveToNext());

            cursor_generation.close();

            local_getLines();
        }
    }

    private void local_getLines(){
        Cursor cursor_line = myDb.getAllDataFromLine();
        cursor_line.moveToFirst();

            if (cursor_line.getCount() == 0) {
                Toast.makeText(this,"No lines", Toast.LENGTH_SHORT).show();
            } else {

                do {
                    Cursor cursor1 = myDb.getDataFromGenerationWhereID(cursor_line.getInt(3));
                    cursor1.moveToFirst();
                    if (cursor1.getCount() != 0) {
                        generation_number = cursor1.getString(2);
                    }
                    if (line_dictionary.containsKey(generation_number)) {
                        list = line_dictionary.get(generation_number);
                        list.add(cursor_line.getString(1));
                    } else {
                        ArrayList<String> list1 = new ArrayList<String>();
                        list1.add(cursor_line.getString(1));
                        line_dictionary.put(generation_number, list1);
                    }
                } while (cursor_line.moveToNext());
            }

    }


    private void API_getFarmID(String email){
        APIHelperAsync.getFarmID("getFarmID/" + email, new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response){
                farm_id = rawJsonResponse;
                farm_id = farm_id.replaceAll("\\[", "").replaceAll("\\]","");
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
