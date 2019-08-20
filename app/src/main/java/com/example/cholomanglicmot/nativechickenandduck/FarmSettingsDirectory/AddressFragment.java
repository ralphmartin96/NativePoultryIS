package com.example.cholomanglicmot.nativechickenandduck.FarmSettingsDirectory;


import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cholomanglicmot.nativechickenandduck.DatabaseHelper;
import com.example.cholomanglicmot.nativechickenandduck.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddressFragment extends Fragment {
    private Button address_submit_button;
    private EditText profile_region,profile_province,profile_town,profile_barangay;
    DatabaseHelper myDb;


    public AddressFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_address, container, false);
        myDb = new DatabaseHelper(getContext());
        address_submit_button = (Button) view.findViewById(R.id.address_submit_button);
        profile_region = view.findViewById(R.id.profile_region);
        profile_province = view.findViewById(R.id.profile_province);
        profile_town = view.findViewById(R.id.profile_town);
        profile_barangay = view.findViewById(R.id.profile_barangay);


        Cursor cursor = myDb.getAllData();
        cursor.moveToFirst();
        if (cursor.getCount() != 0){
            profile_region.setText(cursor.getString(2));
            profile_province.setText(cursor.getString(3));
            profile_town.setText(cursor.getString(4));
            profile_barangay.setText(cursor.getString(5));
        }

        address_submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor res = myDb.getAllData();
                if(res.getCount() == 0){//check ang database kung meron na laman
                    Toast.makeText(getActivity(),"EMPTY", Toast.LENGTH_SHORT).show();
                    boolean isInserted = myDb.insertDataAddress(profile_region.getText().toString(),
                            profile_province.getText().toString(),profile_town.getText().toString(),
                            profile_barangay.getText().toString());
                    if(isInserted == true){
                        Toast.makeText(getActivity(),"Data inserted to database", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getActivity(),"Data not inserted to database", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    boolean isUpdate = myDb.updateDataAddress("QUEBAI","BP",profile_region.getText().toString(),
                            profile_province.getText().toString(),profile_town.getText().toString(),
                            profile_barangay.getText().toString());
                    if(isUpdate == true){
                        Toast.makeText(getActivity(),"Database updated", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getActivity(),"Database not updated", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
        return view;
    }

}
