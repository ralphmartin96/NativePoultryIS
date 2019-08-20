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
public class ContactsFragment extends Fragment {
    private Button contacts_submit_button, show_data_button;
    private EditText profile_phone, profile_email;
    DatabaseHelper myDb;


    public ContactsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contacts, container, false);
        myDb = new DatabaseHelper(getContext());
        contacts_submit_button = view.findViewById(R.id.contacts_submit_button);
        profile_phone = view.findViewById(R.id.profile_phone);
        profile_email = view.findViewById(R.id.profile_email);

        Cursor cursor = myDb.getAllData();
        cursor.moveToFirst();
        if (cursor.getCount() != 0){
            profile_phone.setText(cursor.getString(6));
            profile_email.setText(cursor.getString(7));
        }


        contacts_submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor res = myDb.getAllData();
                if(res.getCount() == 0) {
                    boolean isInserted = myDb.insertDataContacts("QUEBAI", profile_phone.getText().toString(),
                            profile_email.getText().toString());
                    if(isInserted == true){
                        Toast.makeText(getActivity(),"Data inserted to database", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getActivity(),"Data not inserted to database", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    boolean isUpdate = myDb.updateDataContacts("QUEBAI",profile_phone.getText().toString(),
                            profile_email.getText().toString());
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

