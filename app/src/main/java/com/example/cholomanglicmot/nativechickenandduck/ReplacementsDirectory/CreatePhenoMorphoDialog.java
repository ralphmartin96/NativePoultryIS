package com.example.cholomanglicmot.nativechickenandduck.ReplacementsDirectory;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cholomanglicmot.nativechickenandduck.DatabaseHelper;
import com.example.cholomanglicmot.nativechickenandduck.R;
import com.example.cholomanglicmot.nativechickenandduck.SpinnerAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CreatePhenoMorphoDialog extends DialogFragment{
    private Spinner spinner_plumage_color,spinner_plumage_pattern;
    private SpinnerAdapter spinnerAdapter,spinnerAdapter2;
    private String[] plumage_color = {"White", "Orange", "Black", "Brown", "Red", "Yellow"};
    private String[] plumage_pattern = {"Plain", "Wild Type", "Molted", "Barred", "Laced"};
    private int[] plumage_pattern_images = {R.drawable.plumage_pattern_plain,R.drawable.plumage_pattern_wildtype,R.drawable.plumage_pattern_mottled, R.drawable.plumage_pattern_barred,R.drawable.plumage_pattern_laced };
    private int[] plumage_color_images = {R.drawable.plumage_white,R.drawable.plumage_orange,R.drawable.plumage_black,R.drawable.plumage_brown,R.drawable.plumage_red,R.drawable.plumage_yellow};

    private Spinner spinner_hackle_color,spinner_hackle_pattern,spinner_body_carriage;
    private SpinnerAdapter spinnerAdapter3,spinnerAdapter4, spinnerAdapter5;
    private String[] hackle_color = {"Yellow", "Brown", "Black", "Orange", "Red"};
    private String[] hackle_pattern = {"Plain", "Barred", "Laced"};
    private String[] body_carriage = {"Upright", "Slight Upright"};
    private int[] hackle_color_images = {R.drawable.hackle_yellow,R.drawable.hackle_brown,R.drawable.hackle_black, R.drawable.hackle_orange,R.drawable.hackle_red };
    private int[] hackle_pattern_images = {R.drawable.plumage_pattern_plain,R.drawable.plumage_pattern_barred,R.drawable.plumage_pattern_laced};
    private int[] body_carriage_images = {R.drawable.body_carriage_upright, R.drawable.body_carriage__slightly_upright};

    private Spinner spinner_comb_type,spinner_comb_color;
    private SpinnerAdapter spinnerAdapter6,spinnerAdapter7;
    private String[] comb_type = {"Single", "Pea", "Rose"};
    private String[] comb_color = {"Red", "Pink", "Black"};
    private int[] comb_type_images = {R.drawable.comb_single,R.drawable.comb_pea,R.drawable.comb_rose};
    private int[] comb_color_images = {R.drawable.comb_red,R.drawable.comb_pink,R.drawable.comb_black};

    private Spinner spinner_earlobe_color,spinner_iris_color, spinner_beak_color;
    private SpinnerAdapter spinnerAdapter8,spinnerAdapter9, spinnerAdapter10;
    private String[] earlobe_color = {"White", "Red-White", "Red"};
    private String[] iris_color = {"Red", "Brown", "Orange", "Yellow"};
    private String[] beak_color = {"White", "Black","Brown", "Yellow"};
    private int[] earlobe_color_images = {R.drawable.earlobe_red,R.drawable.earlobe_redwhite,R.drawable.earlobe_white};
    private int[] iris_color_images = {R.drawable.iris_red,R.drawable.iris_brown,R.drawable.iris_orange, R.drawable.iris_yellow};
    private int[] beak_color_images = {R.drawable.beak_white,R.drawable.beak_black,R.drawable.beak_brown,R.drawable.beak_yellow};

    private Spinner spinner_shank_color,spinner_skin_color;
    private SpinnerAdapter spinnerAdapter11,spinnerAdapter12;
    private String[] shank_color = {"White", "Green", "Black","Grey","Yellow"};
    private String[] skin_color = {"White","Yellow"};
    private int[] shank_color_images = {R.drawable.shank_white,R.drawable.shank_green,R.drawable.shank_black, R.drawable.shank_grey, R.drawable.shank_yellow};
    private int[] skin_color_images = {R.drawable.skin_white,R.drawable.skin_yellow};


    private Spinner spinner_plumage_color_duck;
    List<String> lines = new ArrayList<String>() {{
        add("Black");
        add("Brown with Black");
        add("Black with Brown");
        add("Brown");
    }};

    private Spinner spinner_plumage_pattern_duck;
    List<String> plumage_patter_duck = new ArrayList<String>() {{
        add("Dusky");
        add("Runner");
        add("Mallard");
        add("Runner/Mallard");
        add("Plain Brown");
    }};


    private Spinner spinner_body_carriage_duck;
    List<String> body_carriage_duck = new ArrayList<String>() {{
        add("Horizontal");
        add("Slight upright");
        add("Upright");

    }};

    private Spinner spinner_shank_color_duck;
    List<String> shank_color_duck = new ArrayList<String>() {{
        add("Black");
        add("Brown");
        add("Dark Brown");
        add("Dark Orange");
        add("Orange with Black");

    }};


    private Spinner spinner_neck_feather_markings;
    List<String> neck_feather_markings = new ArrayList<String>() {{
        add("Plain");
        add("With Bib (Small)");
        add("With Bib (Medium)");
        add("With Bib (Large)");

    }};


    private Spinner spinner_wing_feather_color;
    List<String> wing_feather_color = new ArrayList<String>() {{
        add("Black with Brown");
        add("Black with White");
        add("Brown with White");
        add("Brown");

    }};

    private Spinner spinner_tail_feather_color;
    List<String> tail_feather_color = new ArrayList<String>() {{
        add("Black");
        add("Brown");
        add("Brown with White");

    }};

    private Spinner spinner_bill_color;
    List<String> bill_color = new ArrayList<String>() {{
        add("Green");
        add("Black");
        add("Black with Grey");

    }};

    private Spinner spinner_bill_shape;
    List<String> bill_shape = new ArrayList<String>() {{
        add("Uniform");
        add("Saddle");

    }};


    private Spinner spinner_bean_color;
    List<String> bean_color = new ArrayList<String>() {{
        add("Black");
        add("Grey");

    }};

    private Spinner spinner_presence_of_crest;
    List<String> crest = new ArrayList<String>() {{
        add("Yes");
        add("No");

    }};

    private Spinner spinner_eye_color;
    List<String> eye_color = new ArrayList<String>() {{
        add("Black");
        add("Brown");

    }};



    private RadioGroup sex;
    private RadioButton male, female;
    private Button mActionOk;
    private Calendar calendar;
    private EditText pheno_date, pheno_tag;
    private TextView replacement_tag;

    private String  replacement_pen;
    private String replacement_inv_tag, pheno_record;
    Switch replacement_system_type;
    LinearLayout chicken, duck;
    boolean isDuck;

    DatabaseHelper myDb;

    ArrayList<Replacement_PhenoMorphoRecords> arrayListPhenoMorpho = new ArrayList<>();
    ArrayList<Replacement_PhenoMorphoRecords>arrayList_temp = new ArrayList<>();
    ArrayList<Replacement_Inventory>arrayListReplacementInventory = new ArrayList<>();
    ArrayList<Replacement_Inventory>arrayListReplacementInventory1 = new ArrayList<>();



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_create_pheno_morpho_dialog, container, false);

        replacement_pen = getArguments().getString("Replacement Pen");
        replacement_inv_tag = getArguments().getString("Replacement Tag");
        final StringBuffer pheno_records = new StringBuffer();
        spinner_plumage_color = (Spinner) view.findViewById(R.id.spinner_plumage_color);
        spinner_plumage_pattern = view.findViewById(R.id.spinner_plumage_pattern);
        spinnerAdapter = new SpinnerAdapter(getContext(),plumage_color, plumage_color_images);
        spinnerAdapter2 = new SpinnerAdapter(getContext(),plumage_pattern, plumage_pattern_images);
        spinner_plumage_pattern.setAdapter(spinnerAdapter2);
        spinner_plumage_color.setAdapter(spinnerAdapter);

        spinner_hackle_color = view.findViewById(R.id.spinner_hackle_color);
        spinner_hackle_pattern = view.findViewById(R.id.spinner_hackle_pattern);
        spinner_body_carriage = view.findViewById(R.id.spinner_body_carriage);
        spinnerAdapter3 = new SpinnerAdapter(getContext(),hackle_color, hackle_color_images);
        spinnerAdapter4 = new SpinnerAdapter(getContext(),hackle_pattern, hackle_pattern_images);
        spinnerAdapter5 = new SpinnerAdapter(getContext(), body_carriage, body_carriage_images);
        spinner_hackle_color.setAdapter(spinnerAdapter3);
        spinner_hackle_pattern.setAdapter(spinnerAdapter4);
        spinner_body_carriage.setAdapter(spinnerAdapter5);

        spinner_comb_type = view.findViewById(R.id.spinner_comb_type);
        spinner_comb_color = view.findViewById(R.id.spinner_comb_color);
        spinnerAdapter6 = new SpinnerAdapter(getContext(),comb_type, comb_type_images);
        spinnerAdapter7 = new SpinnerAdapter(getContext(),comb_color, comb_color_images);
        spinner_comb_type.setAdapter(spinnerAdapter6);
        spinner_comb_color.setAdapter(spinnerAdapter7);

        spinner_earlobe_color = view.findViewById(R.id.spinner_earlobe_color);
        spinner_iris_color = view.findViewById(R.id.spinner_iris_color);
        spinner_beak_color = view.findViewById(R.id.spinner_beak_color);
        spinnerAdapter8 = new SpinnerAdapter(getContext(),earlobe_color, earlobe_color_images);
        spinnerAdapter9 = new SpinnerAdapter(getContext(),iris_color, iris_color_images);
        spinnerAdapter10 = new SpinnerAdapter(getContext(),beak_color, beak_color_images);
        spinner_earlobe_color.setAdapter(spinnerAdapter8);
        spinner_iris_color.setAdapter(spinnerAdapter9);
        spinner_beak_color.setAdapter(spinnerAdapter10);

        spinner_shank_color = view.findViewById(R.id.spinner_shank_color);
        spinner_skin_color = view.findViewById(R.id.spinner_skin_color);
        spinnerAdapter11 = new SpinnerAdapter(getContext(),shank_color, shank_color_images);
        spinnerAdapter12 = new SpinnerAdapter(getContext(),skin_color, skin_color_images);

        spinner_shank_color.setAdapter(spinnerAdapter11);
        spinner_skin_color.setAdapter(spinnerAdapter12);


        //duck
        spinner_plumage_color_duck = view.findViewById(R.id.spinner_plumage_color_duck);
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, lines);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_plumage_color_duck.setAdapter(dataAdapter2);


        spinner_plumage_pattern_duck= view.findViewById(R.id.spinner_plumage_pattern_duck);
        ArrayAdapter<String> dataAdapter3 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, plumage_patter_duck);
        dataAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_plumage_pattern_duck.setAdapter(dataAdapter3);


        spinner_body_carriage_duck= view.findViewById(R.id.spinner_body_carriage_duck);
        ArrayAdapter<String> dataAdapter4 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, body_carriage_duck);
        dataAdapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_body_carriage_duck.setAdapter(dataAdapter4);

        spinner_shank_color_duck= view.findViewById(R.id.spinner_shank_color_duck);
        ArrayAdapter<String> dataAdapter5 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, shank_color_duck);
        dataAdapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_shank_color_duck.setAdapter(dataAdapter5);


        spinner_neck_feather_markings= view.findViewById(R.id.spinner_neck_feather_markings);
        ArrayAdapter<String> dataAdapter6 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, neck_feather_markings);
        dataAdapter6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_neck_feather_markings.setAdapter(dataAdapter6);



        spinner_wing_feather_color= view.findViewById(R.id.spinner_wing_feather_color);
        ArrayAdapter<String> dataAdapter7 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, wing_feather_color);
        dataAdapter7.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_wing_feather_color.setAdapter(dataAdapter7);


        spinner_tail_feather_color= view.findViewById(R.id.spinner_tail_feather_color);
        ArrayAdapter<String> dataAdapter8 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, tail_feather_color);
        dataAdapter8.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_tail_feather_color.setAdapter(dataAdapter8);

        spinner_bill_color= view.findViewById(R.id.spinner_bill_color);
        ArrayAdapter<String> dataAdapter9 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, bill_color);
        dataAdapter9.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_bill_color.setAdapter(dataAdapter9);


        spinner_bill_shape= view.findViewById(R.id.spinner_bill_shape);
        ArrayAdapter<String> dataAdapter10 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, bill_shape);
        dataAdapter10.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_bill_shape.setAdapter(dataAdapter10);


        spinner_bean_color= view.findViewById(R.id.spinner_bean_color);
        ArrayAdapter<String> dataAdapter11 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, bean_color);
        dataAdapter11.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_bean_color.setAdapter(dataAdapter11);



        spinner_presence_of_crest= view.findViewById(R.id.spinner_presence_of_crest);
        ArrayAdapter<String> dataAdapter12 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, crest);
        dataAdapter12.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_presence_of_crest.setAdapter(dataAdapter12);


        spinner_eye_color= view.findViewById(R.id.spinner_eye_color);
        ArrayAdapter<String> dataAdapter13 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, eye_color);
        dataAdapter13.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_eye_color.setAdapter(dataAdapter13);




        mActionOk = view.findViewById(R.id.action_ok);

        sex = view.findViewById(R.id.radio_group_gender);
        male = view.findViewById(R.id.radioButton);
        female = view.findViewById(R.id.radioButton2);
        replacement_system_type = view.findViewById(R.id.replacement_system_type);
        chicken = view.findViewById(R.id.chicken);
        duck = view.findViewById(R.id.duck);

        pheno_date = view.findViewById(R.id.pheno_date);
        pheno_tag = view.findViewById(R.id.pheno_tag);

        replacement_tag = view.findViewById(R.id.replacement_tag);
        replacement_tag.setText(replacement_inv_tag);


        myDb = new DatabaseHelper(getContext());


        pheno_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog mDatePicker = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int selectedYear, int selectedMonth, int selectedDay) {
                        selectedMonth++;
                        pheno_date.setText(selectedYear + "-" + selectedMonth + "-" + selectedDay);
                        calendar.set(selectedYear,selectedMonth,selectedDay);
                    }
                }, year, month, day);
                mDatePicker.show();

            }
        });

        replacement_system_type.setChecked(false);
        replacement_system_type.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {

                if (isChecked) {
                    duck.setVisibility(View.VISIBLE);
                    chicken.setVisibility(View.GONE);
                    isDuck = true;

                } else {
                    duck.setVisibility(View.GONE);
                    chicken.setVisibility(View.VISIBLE);
                    isDuck = false;
                }

            }
        });
        final int male_ = 1000;
        final int female_ = 1001;

        male.setId(male_);
        female.setId(female_);

        mActionOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                StringBuffer pheno = new StringBuffer();
                if(!pheno_date.getText().toString().isEmpty() && !pheno_tag.getText().toString().isEmpty()){

                    int selectedDay = sex.getCheckedRadioButtonId();
                    String gender = new String();
                    // if(!())
                    switch (selectedDay) {
                        case male_:
                            // the first RadioButton is checked.
                            gender = "Male";

                            break;
                        //other checks for the other RadioButtons ids from the RadioGroup
                        case female_:
                            // the first RadioButton is checked.
                            gender = "Female";
                            break;
                        //other checks for the other RadioButtons ids from the RadioGroup

                        case -1:
                            gender =  null;
                            // no RadioButton is checked inthe Radiogroup
                            break;
                    }


                    ///concatinating all pheno data to a string
                    if(!isDuck){
                        pheno.append("["+'"'+spinner_plumage_color.getSelectedItem().toString()+'"'+", ");
                        pheno.append('"'+spinner_plumage_pattern.getSelectedItem().toString()+'"'+", ");
                        pheno.append('"'+spinner_hackle_color.getSelectedItem().toString()+'"'+", ");
                        pheno.append('"'+spinner_hackle_pattern.getSelectedItem().toString()+'"'+", ");
                        pheno.append('"'+spinner_body_carriage.getSelectedItem().toString()+'"'+", ");
                        pheno.append('"'+spinner_comb_type.getSelectedItem().toString()+'"'+", ");
                        pheno.append('"'+spinner_comb_color.getSelectedItem().toString()+'"'+", ");
                        pheno.append('"'+spinner_earlobe_color.getSelectedItem().toString()+'"'+", ");
                        pheno.append('"'+spinner_iris_color.getSelectedItem().toString()+'"'+", ");
                        pheno.append('"'+spinner_beak_color.getSelectedItem().toString()+'"'+", ");
                        pheno.append('"'+spinner_shank_color.getSelectedItem().toString()+'"'+", ");
                        pheno.append('"'+spinner_skin_color.getSelectedItem().toString()+'"'+"]");

                        final Bundle args = new Bundle();

                        args.putString("Phenotypic Records", pheno.toString());
                        args.putString("Phenotypic Sex", gender);
                        args.putString("Phenotypic Tag", pheno_tag.getText().toString());
                        args.putString("Phenotypic Date", pheno_date.getText().toString());
                        args.putString("Replacement Pen", replacement_pen);
                        args.putString("Replacement Tag", replacement_inv_tag);



                        FragmentActivity activity = (FragmentActivity)(getContext());
                        FragmentManager fm = activity.getSupportFragmentManager();
                        CreateMorphoDialog alertDialog = new CreateMorphoDialog();
                        alertDialog.setArguments(args);
                        alertDialog.show(fm, "CreateMorphoDialog");
                        getDialog().dismiss();
                    }else{

                        pheno.append("["+'"'+spinner_plumage_color_duck.getSelectedItem().toString()+'"'+", ");
                        pheno.append('"'+spinner_plumage_pattern_duck.getSelectedItem().toString()+'"'+", ");
                        pheno.append('"'+spinner_body_carriage_duck.getSelectedItem().toString()+'"'+", ");
                        pheno.append('"'+spinner_shank_color_duck.getSelectedItem().toString()+'"'+", ");
                        pheno.append('"'+spinner_neck_feather_markings.getSelectedItem().toString()+'"'+", ");
                        pheno.append('"'+spinner_wing_feather_color.getSelectedItem().toString()+'"'+", ");
                        pheno.append('"'+spinner_tail_feather_color.getSelectedItem().toString()+'"'+", ");
                        pheno.append('"'+spinner_bill_color.getSelectedItem().toString()+'"'+", ");
                        pheno.append('"'+spinner_bill_shape.getSelectedItem().toString()+'"'+", ");
                        pheno.append('"'+spinner_bean_color.getSelectedItem().toString()+'"'+", ");
                        pheno.append('"'+spinner_presence_of_crest.getSelectedItem().toString()+'"'+", ");
                        pheno.append('"'+spinner_eye_color.getSelectedItem().toString()+'"'+"]");


                        final Bundle args = new Bundle();

                        args.putString("Phenotypic Records", pheno.toString());
                        args.putString("Phenotypic Sex", gender);
                        args.putString("Phenotypic Tag", pheno_tag.getText().toString());
                        args.putString("Phenotypic Date", pheno_date.getText().toString());
                        args.putString("Replacement Pen", replacement_pen);
                        args.putString("Replacement Tag", replacement_inv_tag);



                        FragmentActivity activity = (FragmentActivity)(getContext());
                        FragmentManager fm = activity.getSupportFragmentManager();
                        CreateMorphoDialog alertDialog = new CreateMorphoDialog();
                        alertDialog.setArguments(args);
                        alertDialog.show(fm, "CreateMorphoDialog");
                        getDialog().dismiss();
                    }


                }else{
                    Toast.makeText(getContext(), "Please fill any empty fields", Toast.LENGTH_SHORT).show();
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
}

