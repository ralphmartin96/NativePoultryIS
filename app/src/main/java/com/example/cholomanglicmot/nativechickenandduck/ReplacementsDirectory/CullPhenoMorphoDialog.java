package com.example.cholomanglicmot.nativechickenandduck.ReplacementsDirectory;

import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cholomanglicmot.nativechickenandduck.DatabaseHelper;
import com.example.cholomanglicmot.nativechickenandduck.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CullPhenoMorphoDialog extends DialogFragment {

    DatabaseHelper myDb;
    TextView textView, tag, gender, pheno, morpho,date;
    EditText edit_male_count, edit_female_count;
    Button no, yes;
    List<String> famLineGen = new ArrayList<>();
    Context context;
    Integer  pen_capacity, total;
    String pen_number;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_cull_pheno_morpho, container, false);
/*    public static final String TABLE_PHENO_MORPHOS = "pheno_morphos";
    public static final String PHENO_MORPHOS_COL_0 = "id";
    public static final String PHENO_MORPHOS_COL_1   = "replacement_inventory";
    public static final String PHENO_MORPHOS_COL_2   = "breeder_inventory";
    public static final String PHENO_MORPHOS_COL_3   = "values_id";
    public static final String PHENO_MORPHOS_COL_4   = "deleted_at";


    public static final String TABLE_PHENO_MORPHO_VALUES = "pheno_morpho_values";
    public static final String PHENO_MORPHO_VALUES_COL_0 = "id";
    public static final String PHENO_MORPHO_VALUES_COL_1   = "gender";
    public static final String PHENO_MORPHO_VALUES_COL_2   = "tag";
    public static final String PHENO_MORPHO_VALUES_COL_3   = "phenotypic";
    public static final String PHENO_MORPHO_VALUES_COL_4   = "morphometric";
    public static final String PHENO_MORPHO_VALUES_COL_5   = "date_collected";
    public static final String PHENO_MORPHO_VALUES_COL_6   = "deleted_at";
*/

        final Integer phenoID = getArguments().getInt("PhenoMorpho ID");
        final String phenoTag = getArguments().getString("PhenoMorpho Tag");
        final String phenoGender = getArguments().getString("PhenoMorpho Gender");
        final String phenoP = getArguments().getString("PhenoMorpho Pheno");
        final String phenoM = getArguments().getString("PhenoMorpho Morpho");
        final String phenoDate = getArguments().getString("PhenoMorpho Date");



        context = getActivity();

        myDb = new DatabaseHelper(getContext());


        textView = view.findViewById(R.id.textView);
        tag = view.findViewById(R.id.tag);
        gender = view.findViewById(R.id.gender);
        pheno = view.findViewById(R.id.pheno);
        morpho = view.findViewById(R.id.morpho);
        date = view.findViewById(R.id.date);

        no = view.findViewById(R.id.no);
        yes = view.findViewById(R.id.yes);

        textView.setText("Delete Record");
        tag.setText("\nRegistry/Tag: "+phenoTag);
        gender.setText("Gender: "+phenoGender);
        pheno.setText("Pheno: "+phenoP);
        morpho.setText("Morpho: "+phenoM);
        date.setText("Date Collected "+phenoDate);



        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                getDialog().dismiss();

            }
        });


        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //update  is_active column of selected inventory to 0 or false
                boolean isCulled = myDb.updatePhenoMorphoValues(phenoID);
                if(isCulled){
                    Toast.makeText(getActivity(), "Succesfully deleted record", Toast.LENGTH_SHORT).show();
                    /*Intent intent_line = new Intent(getActivity(), ReplacementPhenoMorphoViewActivity.class);
                    startActivity(intent_line);*/
                    getDialog().dismiss();
                }


            }
        });


        return view;
    }
    private String getDate() {

        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());

        return formatter.format(date);
    }
}