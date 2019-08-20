package com.example.cholomanglicmot.nativechickenandduck.ReplacementsDirectory;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.cholomanglicmot.nativechickenandduck.DatabaseHelper;
import com.example.cholomanglicmot.nativechickenandduck.R;

public class ViewMorePhenoMorphoDialog extends DialogFragment {

    DatabaseHelper myDb;
    TextView textView,gender, pheno, morpho, date_added;
    Button  save;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_view_replacement_pheno_morph_more, container, false);
        final Integer pheno_morpho_id = getArguments().getInt("PhenoMorpho ID");

        myDb = new DatabaseHelper(getContext());



        textView = view.findViewById(R.id.tag);
        gender = view.findViewById(R.id.gender);
        pheno = view.findViewById(R.id.pheno);
        morpho = view.findViewById(R.id.morpho);
        date_added = view.findViewById(R.id.date_added);


        save = view.findViewById(R.id.save);



        Cursor cursor = myDb.getAllDataFromPhenoMorphoRecordsWhereID(pheno_morpho_id);
        cursor.moveToFirst();
        if(cursor.getCount() != 0){
            String tag = cursor.getString(2);
            String gender1 = cursor.getString(1);
            String date_added1 = cursor.getString(5);
            String pheno1 = cursor.getString(3);
            String morpho1 = cursor.getString(4);

            textView.setText(tag);
            gender.setText(gender1);
            date_added.setText(date_added1);
            pheno.setText(pheno1);
            morpho.setText(morpho1);


        }else{
            textView.setText("alaws tropa");
        }


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();


            }
        });


        return view;
    }
}