package com.example.cholomanglicmot.nativechickenandduck.FarmSettingsDirectory;


import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.cholomanglicmot.nativechickenandduck.DatabaseHelper;
import com.example.cholomanglicmot.nativechickenandduck.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


/**
 * A simple {@link Fragment} subclass.
 */
public class AccountFragment extends Fragment {
    private Button edit_button;
    private TextView farm_code, farm_name, farm_address, batching_week;

    DatabaseHelper myDb;
    String farm_code1, farm_name1, farm_address1;
    Integer batching_week1;
    Context context;
    Integer farm_id=0;

    public AccountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        myDb = new DatabaseHelper(getContext());
        context = getActivity();
        edit_button = (Button) view.findViewById(R.id.edit_button);
        farm_code = view.findViewById(R.id.farm_code);
        farm_name = view.findViewById(R.id.farm_name);
        farm_address = view.findViewById(R.id.farm_address);
        batching_week = view.findViewById(R.id.batching_week);

        ///GET BATCHING WEEK FROM DATABASE
        FirebaseAuth mAuth;

        mAuth = FirebaseAuth.getInstance();

        FirebaseUser user = mAuth.getCurrentUser();

        String name = user.getDisplayName();

        String email = user.getEmail();

        Uri photo = user.getPhotoUrl();

        Cursor cursor_farm_id = myDb.getFarmIDFromUsers(email);
        cursor_farm_id.moveToFirst();
        if(cursor_farm_id.getCount() != 0){
            farm_id = cursor_farm_id.getInt(0);
        }

        Cursor cursor = myDb.getAllDataFromFarms(farm_id);
        cursor.moveToFirst();
        if(cursor.getCount() != 0){
            farm_name1 = cursor.getString(1);
            farm_code1 = cursor.getString(2);
            farm_address1 = cursor.getString(3);
            batching_week1 = cursor.getInt(4);
        }

        farm_code.setText(farm_code1);
        farm_name.setText(farm_name1);
        farm_address.setText(farm_address1);
        batching_week.setText("Every "+batching_week1+" week/s");

        edit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentActivity activity = (FragmentActivity)(context);
                FragmentManager fm = activity.getSupportFragmentManager();
                EditFarmDialog alertDialog = new EditFarmDialog();
               //alertDialog.setArguments(args);
                alertDialog.show(fm, "EditFarmDialog");

            }
        });
        return view;
    }


}
