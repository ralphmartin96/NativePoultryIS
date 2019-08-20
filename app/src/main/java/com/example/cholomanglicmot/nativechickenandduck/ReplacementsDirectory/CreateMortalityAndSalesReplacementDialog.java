package com.example.cholomanglicmot.nativechickenandduck.ReplacementsDirectory;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;

import com.example.cholomanglicmot.nativechickenandduck.BreedersDirectory.Breeder_Inventory;
import com.example.cholomanglicmot.nativechickenandduck.DatabaseHelper;
import com.example.cholomanglicmot.nativechickenandduck.R;

import java.util.ArrayList;
import java.util.Calendar;

public class CreateMortalityAndSalesReplacementDialog extends DialogFragment{
    private EditText date_eggs_set,    number_eggs_set,            fertile,    eggs_hatched,            date_eggs_hatched;
    Switch brooder_switch_other_day;
    LinearLayout yes;
    Spinner outside_generation_spinner,
    outside_line_spinner,
            outside_family_spinner,
    pen;
    private BottomNavigationView mMainNav;
    private Button mActionOk;
    DatabaseHelper myDb;
    Calendar calendar,calendar2;
    ArrayList<Breeder_Inventory> arrayListBrooderInventory = new ArrayList<>();
    ArrayList<Breeder_Inventory>arrayList_temp = new ArrayList<>();

    MortalityFragmentReplacement mortFragment;

    SalesFragmentReplacement salesFragment;
    final Bundle args = new Bundle();



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_create_mortality_and_sales_records2, container, false);
        myDb = new DatabaseHelper(getContext());
        final Integer breeder_id = getArguments().getInt("Brooder ID");
        final String breeder_tag = getArguments().getString("Brooder Tag");
        final Integer brooder_pen = getArguments().getInt("Brooder Pen ID");
        args.putInt("Brooder ID", breeder_id);
        args.putString("Brooder Tag", breeder_tag);
        args.putInt("Brooder Pen ID", brooder_pen);



        mortFragment = new MortalityFragmentReplacement();
        salesFragment = new SalesFragmentReplacement();



        date_eggs_set= view.findViewById(R.id.date_eggs_set);
        number_eggs_set= view.findViewById(R.id.number_eggs_set);
        fertile= view.findViewById(R.id.fertile);
        eggs_hatched= view.findViewById(R.id.eggs_hatched);
        brooder_switch_other_day= view.findViewById(R.id.brooder_switch_other_day);
        yes= view.findViewById(R.id.yes);
        outside_generation_spinner= view.findViewById(R.id.outside_generation_spinner);
        outside_line_spinner= view.findViewById(R.id.outside_line_spinner);
        outside_family_spinner= view.findViewById(R.id.outside_family_spinner);
        date_eggs_hatched= view.findViewById(R.id.date_eggs_hatched);
        mMainNav = view.findViewById(R.id.bottom_nav);
        pen= view.findViewById(R.id.pen);

        setFragment(mortFragment);
        mMainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_mort:
                        setFragment(mortFragment);
                        return true;

                    case R.id.nav_sales:
                        setFragment(salesFragment);
                        return true;



                    default:
                        return false;
                }

            }
        });

        return view;
    }

    public void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    private void setFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        fragment.setArguments(args);
        fragmentTransaction.replace(R.id.mort_frame, fragment);
        fragmentTransaction.commit();
    }

}