package com.example.cholomanglicmot.nativechickenandduck.BreedersDirectory;

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

public class ViewBreederEggQualRecordDialog extends DialogFragment {

    DatabaseHelper myDb;
    TextView deleted_at,date_collected, week, egg_weight, egg_color, egg_shape, length, width, albumen_height, albumen_weight, yolk_weight,yolk_color, shell_weight, shell_thickness_top,shell_thickness_middle,shell_thickness_bottom, textView;

    Button update, save;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_view_egg_qual, container, false);
        final Integer breeder_inventory_idID = getArguments().getInt("Breeder Inventory ID");
        final Integer hatchery_id = getArguments().getInt("Breeder Hatchery ID");
        final String breeder_tag = getArguments().getString("Breeder Tag");



        myDb = new DatabaseHelper(getContext());

        textView=view.findViewById(R.id.textView);

        date_collected = view.findViewById(R.id.date_collected);
        week = view.findViewById(R.id.week);
        egg_weight = view.findViewById(R.id.egg_weight); //galing sa brooder table
        egg_color = view.findViewById(R.id.egg_color);//galing sa brooder table
        egg_shape = view.findViewById(R.id.egg_shape);//galing sa brooder table
        length = view.findViewById(R.id.length);//galing sa brooder table
        width = view.findViewById(R.id.width);//galing sa brooder t
        albumen_height = view.findViewById(R.id.albumen_height);//galing sa brooder t
        albumen_weight = view.findViewById(R.id.albumen_weight);
        yolk_weight = view.findViewById(R.id.yolk_weight);
        yolk_color = view.findViewById(R.id.yolk_color); //galing sa brooder table
        shell_weight = view.findViewById(R.id.shell_weight);//galing sa brooder table
        shell_thickness_top = view.findViewById(R.id.shell_thickness_top);//galing sa brooder table
        shell_thickness_middle = view.findViewById(R.id.shell_thickness_middle);//galing sa brooder table
        shell_thickness_bottom = view.findViewById(R.id.shell_thickness_bottom);//galing sa brooder t

        save = view.findViewById(R.id.save);

/*    public static final String TABLE_EGG_QUALITY = "egg_quality";
    public static final String EGG_QUALITY_COL_0 = "ID";
    public static final String EGG_QUALITY_COL_1  = "EGG_QUALITY_BREEDER_INVENTORY_ID";
    public static final String EGG_QUALITY_COL_2  = "EGG_QUALITY_DATE";
    public static final String EGG_QUALITY_COL_3  = "EGG_QUALITY_WEEK";
    public static final String EGG_QUALITY_COL_4  = "EGG_QUALITY_WEIGHT";
    public static final String EGG_QUALITY_COL_5  = "EGG_QUALITY_COLOR";

    public static final String EGG_QUALITY_COL_6  = "EGG_QUALITY_SHAPE";

    public static final String EGG_QUALITY_COL_7  = "EGG_QUALITY_LENGTH";

    public static final String EGG_QUALITY_COL_8  = "EGG_QUALITY_WIDTH";

    public static final String EGG_QUALITY_COL_9 = "EGG_QUALITY_ALBUMENT_HEIGHT";
    public static final String EGG_QUALITY_COL_10  = "EGG_QUALITY_ALBUMENT_WEIGHT";

    public static final String EGG_QUALITY_COL_11  = "EGG_QUALITY_YOLK_WEIGHT";

    public static final String EGG_QUALITY_COL_12  = "EGG_QUALITY_YOLK_COLOR";

    public static final String EGG_QUALITY_COL_13  = "EGG_QUALITY_SHELL_WEIGHT";

    public static final String EGG_QUALITY_COL_14  = "EGG_QUALITY_SHELL_THICKNESS_TOP";
    public static final String EGG_QUALITY_COL_15  = "EGG_QUALITY_SHELL_THICKNESS_MIDDLE";
    public static final String EGG_QUALITY_COL_16  = "EGG_QUALITY_SHELL_THICKNESS_BOTTOM";
    public static final String EGG_QUALITY_COL_17  = "EGG_QUALITY_DELETED_AT";
*/
        textView.setText(breeder_tag);
        Cursor cursor = myDb.getAllDataFromBreederEggQualWhereID(hatchery_id);
        cursor.moveToFirst();
        if(cursor.getCount() != 0){
            Integer week_ = cursor.getInt(3);
            Float weight_ = cursor.getFloat(4);
            Float length_ = cursor.getFloat(7);
            Float width_ = cursor.getFloat(8);
            Float al_height_ = cursor.getFloat(9);
            Float al_weight_ = cursor.getFloat(10);
            Float yolk_weight_ = cursor.getFloat(11);
            String yolk_color_ = cursor.getString(12);
            Float shell_weight_ = cursor.getFloat(13);
            Float thick_top_ = cursor.getFloat(14);
            Float thick_mid_ = cursor.getFloat(15);
            Float thick_bot_ = cursor.getFloat(16);


////////////YUNG AGE IN WEEKS DAPAT ATA CINOCOMPUTE?????????
            date_collected.setText(cursor.getString(2));
            week.setText(week_.toString());
            egg_color.setText(cursor.getString(5));
            egg_shape.setText(cursor.getString(6));
            egg_weight.setText(weight_.toString());
            length.setText(length_.toString());
            width.setText(width_.toString());

            albumen_height.setText(al_height_.toString());
            albumen_weight.setText(al_weight_.toString());
            yolk_weight.setText(yolk_weight_.toString());

            yolk_color.setText(yolk_color_);
            shell_weight.setText(shell_weight_.toString());
            shell_thickness_top.setText(thick_top_.toString());
            shell_thickness_middle.setText(thick_mid_.toString());
            shell_thickness_bottom.setText(thick_bot_.toString());






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