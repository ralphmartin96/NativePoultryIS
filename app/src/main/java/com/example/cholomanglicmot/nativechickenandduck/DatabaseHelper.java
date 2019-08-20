package com.example.cholomanglicmot.nativechickenandduck;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by ProgrammingKnowledge on 4/3/2015.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper db;

    public static final String DATABASE_NAME = "Project.db";




    public static final String TABLE_PROFILE = "profile_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "BREED";
    public static final String COL_3 = "REGION";
    public static final String COL_4 = "PROVINCE";
    public static final String COL_5 = "TOWN";
    public static final String COL_6 = "BARANGAY";
    public static final String COL_7 = "PHONE";
    public static final String COL_8 = "EMAIL";

    public static final String TABLE_ROLES = "roles";
    public static final String ROLE_COL_1 = "ID";
    public static final String ROLE_COL_2 = "ROLE";

    public static final String TABLE_ROLE_USERS = "role_users";
    public static final String ROLE_USER_COL_1 = "USER_ID";
    public static final String ROLE_USER_COL_2 = "ROLE_ID";

    public static final String TABLE_FARMS = "farms";
    public static final String FARM_COL_1 = "ID";
    public static final String FARM_COL_2 = "NAME";
    public static final String FARM_COL_3 = "CODE";
    public static final String FARM_COL_4 = "ADDRESS";
    public static final String FARM_COL_5 = "BATCHING_WEEK";
    public static final String FARM_COL_6 = "BREEDABLE_ID";

    public static final String TABLE_USERS = "users";
    public static final String USER_COL_1 = "ID";
    public static final String USER_COL_2 = "NAME";
    public static final String USER_COL_3 = "EMAIL";
    public static final String USER_COL_4 = "PICTURE";
    public static final String USER_COL_5 = "LAST_ACTIVE";
    public static final String USER_COL_6 = "FARM_ID";
    public static final String USER_COL_7 = "ROLE_ID";
    public static final String USER_COL_8 = "REMEMBER_TOKEN";
    public static final String USER_COL_9 = "DELETED_AT";


    public static final String TABLE_PEN = "pen_table";
    public static final String PEN_COL_0 = "ID";
    public static final String PEN_COL_1 = "farm_id";
    public static final String PEN_COL_2 = "PEN_NUMBER";
    public static final String PEN_COL_3 = "PEN_TYPE";
    public static final String PEN_COL_4 = "PEN_TOTAL_CAPACITY";
    public static final String PEN_COL_5 = "PEN_CURRENT_CAPACITY";
    public static final String PEN_COL_6 = "PEN_IS_ACTIVE";


    public static final String TABLE_GENERATION = "generation_table";
    public static final String GENERATION_COL_0 = "ID";
    public static final String GENERATION_COL_1 = "farm_id";
    public static final String GENERATION_COL_2 = "GENERATION_NUMBER";
    public static final String GENERATION_COL_3 = "numerical_generation";
    public static final String GENERATION_COL_4 = "is_active";
    public static final String GENERATION_COL_5 = "deleted_at";



    public static final String TABLE_LINE = "line_table";
    public static final String LINE_COL_0 = "ID";
    public static final String LINE_COL_1 = "LINE_NUMBER";
    public static final String LINE_COL_2 = "is_active";
    public static final String LINE_COL_3 = "LINE_GENERATION";
    public static final String LINE_COL_4 = "deleted_at";


    public static final String TABLE_FAMILY = "family_table";
    public static final String FAMILY_COL_0 = "ID";
    public static final String FAMILY_COL_1 = "FAMILY_NUMBER";
    public static final String FAMILY_COL_2 = "is_active";
    public static final String FAMILY_COL_3 = "FAMILY_LINE";
    public static final String FAMILY_COL_4 = "deleted_at";






    public static final String TABLE_BROODER = "brooder_table";
    public static final String BROODER_COL_0 = "ID";
    public static final String BROODER_COL_1 = "BROODER_FAMILY";
    public static final String BROODER_COL_2 = "BROODER_DATE_ADDED";
    public static final String BROODER_COL_3 = "BROODER_DELETED_AT";

    public static final String TABLE_BROODER_INVENTORIES = "brooder_inventory_table";
    public static final String BROODER_INV_COL_0 = "ID";
    public static final String BROODER_INV_COL_1 = "BROODER_INV_BROODER_ID";
    public static final String BROODER_INV_COL_2 = "BROODER_INV_PEN_NUMBER"; //REFERENCES SA PEN
    public static final String BROODER_INV_COL_3 = "BROODER_INV_BROODER_TAG";
    public static final String BROODER_INV_COL_4 = "BROODER_INV_BATCHING_DATE";
    public static final String BROODER_INV_COL_5 = "BROODER_INV_NUMBER_MALE";
    public static final String BROODER_INV_COL_6 = "BROODER_INV_NUMBER_FEMALE";
    public static final String BROODER_INV_COL_7 = "BROODER_INV_TOTAL";
    public static final String BROODER_INV_COL_8 = "BROODER_INV_LAST_UPDATE";
    public static final String BROODER_INV_COL_9 = "BROODER_INV_DELETED_AT";

    public static final String TABLE_BROODER_FEEDING_RECORDS = "brooder_feeding_records";
    public static final String BROODER_FEEDING_COL_0 = "ID";
    public static final String BROODER_FEEDING_COL_1 = "BROODER_FEEDING_INVENTORY_ID"; //REFERENCE SA BROODER INV ID
    public static final String BROODER_FEEDING_COL_2 = "BROODER_FEEDING_DATE_COLLECTED";
    public static final String BROODER_FEEDING_COL_3 = "BROODER_FEEDING_AMOUNT_OFFERED";
    public static final String BROODER_FEEDING_COL_4 = "BROODER_FEEDING_AMOUNT_REFUSED";
    public static final String BROODER_FEEDING_COL_5 = "BROODER_FEEDING_REMARKS";
    public static final String BROODER_FEEDING_COL_6 = "BROODER_FEEDING_DELETED_AT";

    public static final String TABLE_BROODER_GROWTH_RECORDS = "brooder_growth_records";
    public static final String BROODER_GROWTH_COL_0 = "ID";
    public static final String BROODER_GROWTH_COL_1 = "BROODER_GROWTH_INVENTORY_ID"; //REFEREC=BCE SA BROODER INV ID
    public static final String BROODER_GROWTH_COL_2 = "BROODER_GROWTH_COLLECTION_DAY";
    public static final String BROODER_GROWTH_COL_3 = "BROODER_GROWTH_DATE_COLLECTED";
    public static final String BROODER_GROWTH_COL_4 = "BROODER_GROWTH_MALE_QUANTITY";
    public static final String BROODER_GROWTH_COL_5 = "BROODER_GROWTH_MALE_WEIGHT";
    public static final String BROODER_GROWTH_COL_6 = "BROODER_GROWTH_FEMALE_QUANTITY";
    public static final String BROODER_GROWTH_COL_7 = "BROODER_GROWTH_FEMALE_WEIGHT";
    public static final String BROODER_GROWTH_COL_8 = "BROODER_GROWTH_TOTAL_QUANTITY";
    public static final String BROODER_GROWTH_COL_9 = "BROODER_GROWTH_TOTAL_WEIGHT";
    public static final String BROODER_GROWTH_COL_10 = "BROODER_GROWTH_DELETED_AT";



    public static final String TABLE_REPLACEMENT = "replacement_table";
    public static final String REPLACEMENT_COL_0 = "ID";
    public static final String REPLACEMENT_COL_1 = "REPLACEMENT_FAMILY";
    public static final String REPLACEMENT_COL_2 = "REPLACEMENT_DATE_ADDED";
    public static final String REPLACEMENT_COL_3 = "REPLACEMENT_DELETED_AT";

    public static final String TABLE_REPLACEMENT_INVENTORIES = "replacement_inventory_table";
    public static final String REPLACEMENT_INV_COL_0 = "ID";
    public static final String REPLACEMENT_INV_COL_1 = "REPLACEMENT_INV_REPLACEMENT_ID";
    public static final String REPLACEMENT_INV_COL_2 = "REPLACEMENT_INV_PEN_NUMBER"; //REFERENCES SA PEN
    public static final String REPLACEMENT_INV_COL_3 = "REPLACEMENT_INV_REPLACEMENT_TAG";
    public static final String REPLACEMENT_INV_COL_4 = "REPLACEMENT_INV_BATCHING_DATE";
    public static final String REPLACEMENT_INV_COL_5 = "REPLACEMENT_INV_NUMBER_MALE";
    public static final String REPLACEMENT_INV_COL_6 = "REPLACEMENT_INV_NUMBER_FEMALE";
    public static final String REPLACEMENT_INV_COL_7 = "REPLACEMENT_INV_TOTAL";
    public static final String REPLACEMENT_INV_COL_8 = "REPLACEMENT_INV_LAST_UPDATE";
    public static final String REPLACEMENT_INV_COL_9 = "REPLACEMENT_INV_DELETED_AT";


    public static final String TABLE_REPLACEMENT_FEEDING_RECORDS = "replacement_feeding_records";
    public static final String REPLACEMENT_FEEDING_COL_0 = "ID";
    public static final String REPLACEMENT_FEEDING_COL_1 = "REPLACEMENT_FEEDING_INVENTORY_ID"; //REFERENCE SA REPLACEMENT INV ID
    public static final String REPLACEMENT_FEEDING_COL_2 = "REPLACEMENT_FEEDING_DATE_COLLECTED";
    public static final String REPLACEMENT_FEEDING_COL_3 = "REPLACEMENT_FEEDING_AMOUNT_OFFERED";
    public static final String REPLACEMENT_FEEDING_COL_4 = "REPLACEMENT_FEEDING_AMOUNT_REFUSED";
    public static final String REPLACEMENT_FEEDING_COL_5 = "REPLACEMENT_FEEDING_REMARKS";
    public static final String REPLACEMENT_FEEDING_COL_6 = "REPLACEMENT_FEEDING_DELETED_AT";

    public static final String TABLE_REPLACEMENT_GROWTH_RECORDS = "replacement_growth_records";
    public static final String REPLACEMENT_GROWTH_COL_0 = "ID";
    public static final String REPLACEMENT_GROWTH_COL_1 = "REPLACEMENT_GROWTH_INVENTORY_ID"; //REFEREC=BCE SA REPLACEMENT INV ID
    public static final String REPLACEMENT_GROWTH_COL_2 = "REPLACEMENT_GROWTH_COLLECTION_DAY";
    public static final String REPLACEMENT_GROWTH_COL_3 = "REPLACEMENT_GROWTH_DATE_COLLECTED";
    public static final String REPLACEMENT_GROWTH_COL_4 = "REPLACEMENT_GROWTH_MALE_QUANTITY";
    public static final String REPLACEMENT_GROWTH_COL_5 = "REPLACEMENT_GROWTH_MALE_WEIGHT";
    public static final String REPLACEMENT_GROWTH_COL_6 = "REPLACEMENT_GROWTH_FEMALE_QUANTITY";
    public static final String REPLACEMENT_GROWTH_COL_7 = "REPLACEMENT_GROWTH_FEMALE_WEIGHT";
    public static final String REPLACEMENT_GROWTH_COL_8 = "REPLACEMENT_GROWTH_TOTAL_QUANTITY";
    public static final String REPLACEMENT_GROWTH_COL_9 = "REPLACEMENT_GROWTH_TOTAL_WEIGHT";
    public static final String REPLACEMENT_GROWTH_COL_10 = "REPLACEMENT_GROWTH_DELETED_AT";

    public static final String TABLE_REPLACEMENT_PHENOTYPIC_AND_MORPHOMETRIC_RECORDS = "replacement_pheno_morpho_records";
    public static final String REPLACEMENT_PHENO_MORPHO_COL_0 = "ID";
    public static final String REPLACEMENT_PHENO_MORPHO_COL_1 = "REPLACEMENT_PHENO_MORPHO_INVENTORY_ID";
    public static final String REPLACEMENT_PHENO_MORPHO_COL_2 = "REPLACEMENT_PHENO_MORPHO_COLLECTED";
    public static final String REPLACEMENT_PHENO_MORPHO_COL_3 = "REPLACEMENT_PHENO_MORPHO_SEX";
    public static final String REPLACEMENT_PHENO_MORPHO_COL_4 = "REPLACEMENT_PHENO_MORPHO_TAG_OR_REGISTRY";
    public static final String REPLACEMENT_PHENO_MORPHO_COL_5 = "REPLACEMENT_PHENO_RECORD";
    public static final String REPLACEMENT_PHENO_MORPHO_COL_6 = "REPLACEMENT_MORPHO_RECORD";


    public static final String TABLE_BREEDER = "breeder_table";
    public static final String BREEDER_COL_0 = "ID";
    public static final String BREEDER_COL_1 = "BREEDER_FAMILY";
    public static final String BREEDER_COL_2 = "BREEDER_FEMALE_FAMILY";
    public static final String BREEDER_COL_3 = "BREEDER_DATE_ADDED";
    public static final String BREEDER_COL_4 = "BREEDER_DELETED_AT";

    public static final String TABLE_BREEDER_INVENTORIES = "breeder_inventory_table";
    public static final String BREEDER_INV_COL_0 = "ID";
    public static final String BREEDER_INV_COL_1 = "BREEDER_INV_BREEDER_ID";
    public static final String BREEDER_INV_COL_2 = "BREEDER_INV_PEN_NUMBER";
    public static final String BREEDER_INV_COL_3 = "BREEDER_INV_BREEDER_TAG";
    public static final String BREEDER_INV_COL_4 = "BREEDER_INV_BATCHING_DATE";
    public static final String BREEDER_INV_COL_5 = "BREEDER_INV_NUMBER_MALE";
    public static final String BREEDER_INV_COL_6 = "BREEDER_INV_NUMBER_FEMALE";
    public static final String BREEDER_INV_COL_7 = "BREEDER_INV_TOTAL";
    public static final String BREEDER_INV_COL_8 = "BREEDER_INV_LAST_UPDATE";
    public static final String BREEDER_INV_COL_9 = "BREEDER_INV_DELETED_AT";
    public static final String BREEDER_INV_COL_10 = "BREEDER_INV_CODE";
    public static final String BREEDER_INV_COL_11 = "BREEDER_INV_MALE_WINGBAND";
    public static final String BREEDER_INV_COL_12 = "BREEDER_INV_FEMALE_WINGBAND";


    public static final String TABLE_BREEDER_FEEDING_RECORDS = "breeder_feeding_records";
    public static final String BREEDER_FEEDING_COL_0 = "ID";
    public static final String BREEDER_FEEDING_COL_1 = "BREEDER_FEEDING_INVENTORY_ID"; //REFERENCE SA BROODER INV ID
    public static final String BREEDER_FEEDING_COL_2 = "BREEDER_FEEDING_DATE_COLLECTED";
    public static final String BREEDER_FEEDING_COL_3 = "BREEDER_FEEDING_AMOUNT_OFFERED";
    public static final String BREEDER_FEEDING_COL_4 = "BREEDER_FEEDING_AMOUNT_REFUSED";
    public static final String BREEDER_FEEDING_COL_5 = "BREEDER_FEEDING_REMARKS";
    public static final String BREEDER_FEEDING_COL_6 = "BREEDER_FEEDING_DELETED_AT";

    public static final String TABLE_BREEDER_GROWTH_RECORDS = "breeder_growth_records";
    public static final String BREEDER_GROWTH_COL_0 = "ID";
    public static final String BREEDER_GROWTH_COL_1 = "BREEDER_GROWTH_INVENTORY_ID"; //REFEREC=BCE SA BROODER INV ID
    public static final String BREEDER_GROWTH_COL_2 = "BREEDER_GROWTH_COLLECTION_DAY";
    public static final String BREEDER_GROWTH_COL_3 = "BREEDER_GROWTH_DATE_COLLECTED";
    public static final String BREEDER_GROWTH_COL_4 = "BREEDER_GROWTH_MALE_QUANTITY";
    public static final String BREEDER_GROWTH_COL_5 = "BREEDER_GROWTH_MALE_WEIGHT";
    public static final String BREEDER_GROWTH_COL_6 = "BREEDER_GROWTH_FEMALE_QUANTITY";
    public static final String BREEDER_GROWTH_COL_7 = "BREEDER_GROWTH_FEMALE_WEIGHT";
    public static final String BREEDER_GROWTH_COL_8 = "BREEDER_GROWTH_TOTAL_QUANTITY";
    public static final String BREEDER_GROWTH_COL_9 = "BREEDER_GROWTH_TOTAL_WEIGHT";
    public static final String BREEDER_GROWTH_COL_10 = "BREEDER_GROWTH_DELETED_AT";

    public static final String TABLE_BREEDER_PHENOTYPIC_AND_MORPHOMETRIC_RECORDS = "breeder_pheno_morpho_records";
    public static final String BREEDER_PHENO_MORPHO_COL_0 = "ID";
    public static final String BREEDER_PHENO_MORPHO_COL_1  = "BREEDER_PHENO_MORPHO_INVENTORY_ID";
    public static final String BREEDER_PHENO_MORPHO_COL_2  = "BREEDER_PHENO_MORPHO_COLLECTED";
    public static final String BREEDER_PHENO_MORPHO_COL_3  = "BREEDER_PHENO_MORPHO_SEX";
    public static final String BREEDER_PHENO_MORPHO_COL_4  = "BREEDER_PHENO_MORPHO_TAG_OR_REGISTRY";
    public static final String BREEDER_PHENO_MORPHO_COL_5  = "BREEDER_PHENO_RECORD";
    public static final String BREEDER_PHENO_MORPHO_COL_6  = "BREEDER_MORPHO_RECORD";

    public static final String TABLE_EGG_PRODUCTION = "egg_production";
    public static final String EGG_PRODUCTION_COL_0 = "ID";
    public static final String EGG_PRODUCTION_COL_1  = "EGG_PRODUCTION_BREEDER_INVENTORY_ID";
    public static final String EGG_PRODUCTION_COL_2  = "EGG_PRODUCTION_DATE";
    public static final String EGG_PRODUCTION_COL_3  = "EGG_PRODUCTION_EGGS_INTACT";
    public static final String EGG_PRODUCTION_COL_4  = "EGG_PRODUCTION_EGG_WEIGHT";
    public static final String EGG_PRODUCTION_COL_5  = "EGG_PRODUCTION_TOTAL_BROKEN";
    public static final String EGG_PRODUCTION_COL_6  = "EGG_PRODUCTION_TOTAL_REJECTS";
    public static final String EGG_PRODUCTION_COL_7 = "EGG_PRODUCTION_REMARKS";
    public static final String EGG_PRODUCTION_COL_8  = "EGG_PRODUCTION_DELETED_AT";
    public static final String EGG_PRODUCTION_COL_9  = "EGG_PRODUCTION_FEMALE_INVENTORY";

    public static final String TABLE_EGG_QUALITY = "egg_quality";
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


    public static final String TABLE_HATCHERY_RECORD = "hatchery_record";
    public static final String HATCHERY_COL_0 = "ID";
    public static final String HATCHERY_COL_1  = "HATCHERY_BREEDER_INVENTORY_ID";
    public static final String HATCHERY_COL_2  = "HATCHERY_DATE";
    public static final String HATCHERY_COL_3  = "HATCHERY__BATCHING_DATE";
    public static final String HATCHERY_COL_4  = "HATCHERY_EGGS_SET";
    public static final String HATCHERY_COL_5  = "HATCHERY_WEEK_LAY";
    public static final String HATCHERY_COL_6  = "HATCHERY_FERTILE";
    public static final String HATCHERY_COL_7  = "HATCHERY_HATCHED";
    public static final String HATCHERY_COL_8 = "HATCHERY_DATE_HATCHED";
    public static final String HATCHERY_COL_9  = "HATCHERY_DELETED_AT";

    public static final String TABLE_MORTALITY_AND_SALES = "mortality_and_sales";
    public static final String MORT_SALES_COL_0 = "ID";
    public static final String MORT_SALES_COL_1  = "MORT_AND_SALES_DATE";
    public static final String MORT_SALES_COL_2  = "MORT_AND_SALES_BREEDER_INV_ID";
    public static final String MORT_SALES_COL_3  = "MORT_AND_SALES_REPLACEMENT_INV_ID";
    public static final String MORT_SALES_COL_4  = "MORT_AND_SALES_BROODER_INV_ID";
    public static final String MORT_SALES_COL_5  = "MORT_AND_SALES_TYPE";
    public static final String MORT_SALES_COL_6  = "MORT_AND_SALES_CATEGORY";
    public static final String MORT_SALES_COL_7  = "MORT_AND_SALES_PRICE";
    public static final String MORT_SALES_COL_8 = "MORT_AND_SALES_MALE";
    public static final String MORT_SALES_COL_9  = "MORT_AND_SALES_FEMALE";
    public static final String MORT_SALES_COL_10  = "MORT_AND_SALES_TOTAL";
    public static final String MORT_SALES_COL_11  = "MORT_AND_SALES_REASON";
    public static final String MORT_SALES_COL_12  = "MORT_AND_SALES_REMARKS";
    public static final String MORT_SALES_COL_13  = "MORT_AND_SALES_DELETED_AT";



    public static final String TABLE_PHENO_MORPHOS = "pheno_morphos";
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



    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 95 );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        db.execSQL("create table " + TABLE_PROFILE +" (ID TEXT PRIMARY KEY,BREED TEXT,REGION TEXT,PROVINCE TEXT,TOWN TEXT,BARANGAY TEXT,PHONE TEXT,EMAIL TEXT)");

        db.execSQL("create table " + TABLE_PEN +" (ID INTEGER PRIMARY KEY, farm_id INTEGER, PEN_NUMBER TEXT, PEN_TYPE TEXT,PEN_TOTAL_CAPACITY INTEGER,PEN_CURRENT_CAPACITY INTEGER, PEN_IS_ACTIVE INTEGER)");
        db.execSQL("create table " + TABLE_GENERATION +" (ID INTEGER PRIMARY KEY, farm_id INTEGER, GENERATION_NUMBER TEXT, numerical_generation INTEGER ,is_active INTEGER, deleted_at TEXT, FOREIGN KEY (farm_id) REFERENCES TABLE_FARMS(ID))");


        db.execSQL("create table " + TABLE_LINE +" (ID INTEGER PRIMARY KEY,LINE_NUMBER TEXT, is_active INTEGER, LINE_GENERATION INTEGER, deleted_at TEXT, FOREIGN KEY (LINE_GENERATION) REFERENCES TABLE_GENERATION(ID))");

        db.execSQL("create table " + TABLE_FAMILY +" (ID INTEGER PRIMARY KEY, FAMILY_NUMBER TEXT, is_active INTEGER, FAMILY_LINE INTEGER, deleted_at TEXT, FOREIGN KEY (FAMILY_LINE) REFERENCES TABLE_LINE(ID))");




        db.execSQL("create table " + TABLE_BROODER +" (ID INTEGER PRIMARY KEY, BROODER_FAMILY INTEGER, BROODER_DATE_ADDED TEXT, BROODER_DELETED_AT TEXT, FOREIGN KEY (BROODER_FAMILY) REFERENCES TABLE_FAMILY(ID))");
        db.execSQL("create table " + TABLE_BROODER_INVENTORIES +" (ID INTEGER PRIMARY KEY,BROODER_INV_BROODER_ID INTEGER, BROODER_INV_PEN_NUMBER INTEGER, BROODER_INV_BROODER_TAG TEXT, BROODER_INV_BATCHING_DATE TEXT, BROODER_INV_NUMBER_MALE INTEGER, BROODER_INV_NUMBER_FEMALE INTEGER, BROODER_INV_TOTAL INTEGER, BROODER_INV_LAST_UPDATE TEXT, BROODER_INV_DELETED_AT TEXT, FOREIGN KEY (BROODER_INV_BROODER_ID) REFERENCES TABLE_BROODER(ID))");
        db.execSQL("create table " + TABLE_BROODER_FEEDING_RECORDS +" (ID INTEGER PRIMARY KEY AUTOINCREMENT, BROODER_FEEDING_INVENTORY_ID INTEGER, BROODER_FEEDING_DATE_COLLECTED TEXT, BROODER_FEEDING_AMOUNT_OFFERED TEXT, BROODER_FEEDING_AMOUNT_REFUSED INTEGER, BROODER_FEEDING_REMARKS TEXT, BROODER_FEEDING_DELETED_AT TEXT, FOREIGN KEY (BROODER_FEEDING_INVENTORY_ID) REFERENCES TABLE_BROODER_INVENTORIES(ID))");
        db.execSQL("create table " + TABLE_BROODER_GROWTH_RECORDS +" (ID INTEGER PRIMARY KEY, BROODER_GROWTH_INVENTORY_ID INTEGER, BROODER_GROWTH_COLLECTION_DAY TEXT, BROODER_GROWTH_DATE_COLLECTED TEXT, BROODER_GROWTH_MALE_QUANTITY INTEGER, BROODER_GROWTH_MALE_WEIGHT INTEGER, BROODER_GROWTH_FEMALE_QUANTITY INTEGER, BROODER_GROWTH_FEMALE_WEIGHT INTEGER,BROODER_GROWTH_TOTAL_QUANTITY INTEGER, BROODER_GROWTH_TOTAL_WEIGHT INTEGER,BROODER_GROWTH_DELETED_AT TEXT, FOREIGN KEY (BROODER_GROWTH_INVENTORY_ID) REFERENCES TABLE_BROODER_INVENTORIES(ID))");

        db.execSQL("create table " + TABLE_REPLACEMENT +" (ID INTEGER PRIMARY KEY, REPLACEMENT_FAMILY INTEGER, REPLACEMENT_DATE_ADDED TEXT, REPLACEMENT_DELETED_AT TEXT, FOREIGN KEY (REPLACEMENT_FAMILY) REFERENCES TABLE_FAMILY(ID))");
        db.execSQL("create table " + TABLE_REPLACEMENT_INVENTORIES +" (ID INTEGER PRIMARY KEY,REPLACEMENT_INV_REPLACEMENT_ID INTEGER, REPLACEMENT_INV_PEN_NUMBER INTEGER, REPLACEMENT_INV_REPLACEMENT_TAG TEXT, REPLACEMENT_INV_BATCHING_DATE TEXT, REPLACEMENT_INV_NUMBER_MALE INTEGER, REPLACEMENT_INV_NUMBER_FEMALE INTEGER, REPLACEMENT_INV_TOTAL INTEGER, REPLACEMENT_INV_LAST_UPDATE TEXT, REPLACEMENT_INV_DELETED_AT TEXT, FOREIGN KEY(REPLACEMENT_INV_REPLACEMENT_ID) REFERENCES TABLE_REPLACEMENT(ID))");
        db.execSQL("create table " + TABLE_REPLACEMENT_FEEDING_RECORDS +" (ID INTEGER PRIMARY KEY, REPLACEMENT_FEEDING_INVENTORY_ID INTEGER , REPLACEMENT_FEEDING_DATE_COLLECTED TEXT, REPLACEMENT_FEEDING_AMOUNT_OFFERED TEXT, REPLACEMENT_FEEDING_AMOUNT_REFUSED INTEGER, REPLACEMENT_FEEDING_REMARKS TEXT, REPLACEMENT_FEEDING_DELETED_AT TEXT, FOREIGN KEY (REPLACEMENT_FEEDING_INVENTORY_ID) REFERENCES TABLE_REPLACEMENT_INVENTORIES(ID))");
        db.execSQL("create table " + TABLE_REPLACEMENT_GROWTH_RECORDS +" (ID INTEGER PRIMARY KEY, REPLACEMENT_GROWTH_INVENTORY_ID INTEGER, REPLACEMENT_GROWTH_COLLECTION_DAY TEXT, REPLACEMENT_GROWTH_DATE_COLLECTED TEXT, REPLACEMENT_GROWTH_MALE_QUANTITY INTEGER, REPLACEMENT_GROWTH_MALE_WEIGHT INTEGER, REPLACEMENT_GROWTH_FEMALE_QUANTITY INTEGER, REPLACEMENT_GROWTH_FEMALE_WEIGHT INTEGER,REPLACEMENT_GROWTH_TOTAL_QUANTITY INTEGER, REPLACEMENT_GROWTH_TOTAL_WEIGHT INTEGER,REPLACEMENT_GROWTH_DELETED_AT TEXT, FOREIGN KEY (REPLACEMENT_GROWTH_INVENTORY_ID) REFERENCES TABLE_REPLACEMENT_INVENTORIES(ID))");


        db.execSQL("create table " + TABLE_BREEDER +" (ID INTEGER PRIMARY KEY, BREEDER_FAMILY INTEGER, BREEDER_FEMALE_FAMILY INTEGER, BREEDER_DATE_ADDED TEXT, BREEDER_DELETED_AT TEXT, FOREIGN KEY(BREEDER_FAMILY) REFERENCES TABLE_FAMILY(ID), FOREIGN KEY(BREEDER_FEMALE_FAMILY) REFERENCES TABLE_FAMILY(ID))");
        db.execSQL("create table " + TABLE_BREEDER_INVENTORIES +" (ID INTEGER PRIMARY KEY,BREEDER_INV_BREEDER_ID INTEGER, BREEDER_INV_PEN_NUMBER INTEGER, BREEDER_INV_BREEDER_TAG TEXT, BREEDER_INV_BATCHING_DATE TEXT, BREEDER_INV_NUMBER_MALE INTEGER, BREEDER_INV_NUMBER_FEMALE INTEGER, BREEDER_INV_TOTAL INTEGER, BREEDER_INV_LAST_UPDATE TEXT, BREEDER_INV_DELETED_AT TEXT,BREEDER_INV_CODE TEXT, BREEDER_INV_MALE_WINGBAND TEXT, BREEDER_INV_FEMALE_WINGBAND TEXT, FOREIGN KEY (BREEDER_INV_BREEDER_ID) REFERENCES TABLE_BREEDER(ID))");
        db.execSQL("create table " + TABLE_BREEDER_FEEDING_RECORDS +" (ID INTEGER PRIMARY KEY, BREEDER_FEEDING_INVENTORY_ID INTEGER, BREEDER_FEEDING_DATE_COLLECTED TEXT, BREEDER_FEEDING_AMOUNT_OFFERED TEXT, BREEDER_FEEDING_AMOUNT_REFUSED INTEGER, BREEDER_FEEDING_REMARKS TEXT, BREEDER_FEEDING_DELETED_AT TEXT, FOREIGN KEY (BREEDER_FEEDING_INVENTORY_ID) REFERENCES TABLE_BREEDER_INVENTORIES(ID) )");
        db.execSQL("create table " + TABLE_BREEDER_GROWTH_RECORDS +" (ID INTEGER PRIMARY KEY, BREEDER_GROWTH_INVENTORY_ID INTEGER, BREEDER_GROWTH_COLLECTION_DAY TEXT, BREEDER_GROWTH_DATE_COLLECTED TEXT, BREEDER_GROWTH_MALE_QUANTITY INTEGER, BREEDER_GROWTH_MALE_WEIGHT INTEGER, BREEDER_GROWTH_FEMALE_QUANTITY INTEGER, BREEDER_GROWTH_FEMALE_WEIGHT INTEGER,BREEDER_GROWTH_TOTAL_QUANTITY INTEGER, BREEDER_GROWTH_TOTAL_WEIGHT INTEGER,BREEDER_GROWTH_DELETED_AT TEXT, FOREIGN KEY (BREEDER_GROWTH_INVENTORY_ID) REFERENCES TABLE_BREEDER_INVENTORIES(ID))");
        db.execSQL("create table " + TABLE_EGG_PRODUCTION +" (ID INTEGER PRIMARY KEY, EGG_PRODUCTION_BREEDER_INVENTORY_ID INTEGER, EGG_PRODUCTION_DATE TEXT, EGG_PRODUCTION_EGGS_INTACT INTEGER, EGG_PRODUCTION_EGG_WEIGHT INTEGER, EGG_PRODUCTION_TOTAL_BROKEN INTEGER, EGG_PRODUCTION_TOTAL_REJECTS INTEGER, EGG_PRODUCTION_REMARKS TEXT, EGG_PRODUCTION_DELETED_AT TEXT, EGG_PRODUCTION_FEMALE_INVENTORY INTEGER, FOREIGN KEY (EGG_PRODUCTION_BREEDER_INVENTORY_ID) REFERENCES TABLE_BREEDER_INVENTORIES(ID))");
        db.execSQL("create table " + TABLE_HATCHERY_RECORD +" (ID INTEGER PRIMARY KEY, HATCHERY_BREEDER_INVENTORY_ID INTEGER, HATCHERY_DATE TEXT, HATCHERY__BATCHING_DATE TEXT, HATCHERY_EGGS_SET INTEGER, HATCHERY_WEEK_LAY INTEGER, HATCHERY_FERTILE INTEGER, HATCHERY_HATCHED INTEGER, HATCHERY_DATE_HATCHED TEXT, HATCHERY_DELETED_AT TEXT, FOREIGN KEY (HATCHERY_BREEDER_INVENTORY_ID) REFERENCES TABLE_BREEDER_INVENTORIES(ID))");
        db.execSQL("create table " + TABLE_EGG_QUALITY +" (ID INTEGER PRIMARY KEY, EGG_QUALITY_BREEDER_INVENTORY_ID INTEGER, EGG_QUALITY_DATE TEXT, EGG_QUALITY_WEEK TEXT ,EGG_QUALITY_WEIGHT INTEGER, EGG_QUALITY_COLOR TEXT, EGG_QUALITY_SHAPE TEXT, EGG_QUALITY_LENGTH INTEGER, EGG_QUALITY_WIDTH INTEGER, EGG_QUALITY_ALBUMENT_HEIGHT INTEGER, EGG_QUALITY_ALBUMENT_WEIGHT INTEGER, EGG_QUALITY_YOLK_WEIGHT INTEGER, EGG_QUALITY_YOLK_COLOR TEXT, EGG_QUALITY_SHELL_WEIGHT INTEGER,EGG_QUALITY_SHELL_THICKNESS_TOP INTEGER,EGG_QUALITY_SHELL_THICKNESS_MIDDLE INTEGER,EGG_QUALITY_SHELL_THICKNESS_BOTTOM INTEGER, EGG_QUALITY_DELETED_AT TEXT, FOREIGN KEY (EGG_QUALITY_BREEDER_INVENTORY_ID) REFERENCES TABLE_BREEDER_INVENTORIES(ID))");
        db.execSQL("create table " + TABLE_MORTALITY_AND_SALES +" (ID INTEGER PRIMARY KEY, MORT_AND_SALES_DATE TEXT, MORT_AND_SALES_BREEDER_INV_ID INTEGER, MORT_AND_SALES_REPLACEMENT_INV_ID INTEGER, MORT_AND_SALES_BROODER_INV_ID INTEGER, MORT_AND_SALES_TYPE TEXT, MORT_AND_SALES_CATEGORY TEXT, MORT_AND_SALES_PRICE INTEGER, MORT_AND_SALES_MALE INTEGER,MORT_AND_SALES_FEMALE INTEGER,  MORT_AND_SALES_TOTAL INTEGER, MORT_AND_SALES_REASON TEXT,MORT_AND_SALES_REMARKS TEXT, MORT_AND_SALES_DELETED_AT TEXT, FOREIGN KEY (MORT_AND_SALES_BREEDER_INV_ID) REFERENCES TABLE_BREEDER_INVENTORIES(ID), FOREIGN KEY (MORT_AND_SALES_REPLACEMENT_INV_ID) REFERENCES TABLE_REPLACEMENT_INVENTORIES(ID), FOREIGN KEY (MORT_AND_SALES_BROODER_INV_ID) REFERENCES TABLE_BROODER_INVENTORIES(ID))");

        db.execSQL("create table "+ TABLE_PHENO_MORPHO_VALUES + "(id INTEGER PRIMARY KEY, gender TEXT, tag TEXT , phenotypic TEXT, morphometric TEXT, date_collected TEXT, deleted_at TEXT)");

        db.execSQL("create table "+ TABLE_PHENO_MORPHOS + "(id INTEGER PRIMARY KEY, replacement_inventory INTEGER, breeder_inventory INTEGER REFERENCES TABLE_BREEDER_INVENTORIES(ID), values_id INTEGER REFERENCES TABLE_PHENO_MORPHO_VALUES(id), deleted_at TEXT, FOREIGN KEY (replacement_inventory) REFERENCES TABLE_REPLACEMENT_INVENTORIES(ID))");


        db.execSQL("create table "+ TABLE_FARMS + "(ID INTEGER PRIMARY KEY, NAME TEXT, CODE TEXT, ADDRESS TEXT, BATCHING_WEEK INTEGER, BREEDABLE_ID INTEGER)");


        db.execSQL("create table "+ TABLE_ROLES + "(ID INTEGER PRIMARY KEY, ROLE TEXT )");

        db.execSQL("create table "+ TABLE_ROLE_USERS + "(USER_ID INTEGER, ROLE_ID INTEGER, FOREIGN KEY (ROLE_ID) REFERENCES TABLE_ROLES(ID))");
        db.execSQL("create table "+ TABLE_USERS + "(ID INTEGER PRIMARY KEY, NAME TEXT, EMAIL TEXT, PICTURE TEXT, LAST_ACTIVE TEXT, FARM_ID INTEGER, ROLE_ID INTEGER, REMEMBER_TOKEN TEXT, DELETED_AT TEXT, FOREIGN KEY(FARM_ID) REFERENCES TABLE_FARMS(ID))");



    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_PROFILE);

        db.execSQL("DROP TABLE IF EXISTS "+TABLE_PEN);

        db.execSQL("DROP TABLE IF EXISTS "+TABLE_GENERATION);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_LINE);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_FAMILY);

        db.execSQL("DROP TABLE IF EXISTS "+TABLE_BREEDER);

        db.execSQL("DROP TABLE IF EXISTS "+TABLE_BROODER);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_BROODER_INVENTORIES);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_BROODER_FEEDING_RECORDS);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_BROODER_GROWTH_RECORDS);

        db.execSQL("DROP TABLE IF EXISTS "+TABLE_REPLACEMENT);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_REPLACEMENT_INVENTORIES);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_REPLACEMENT_FEEDING_RECORDS);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_REPLACEMENT_GROWTH_RECORDS);

        db.execSQL("DROP TABLE IF EXISTS "+TABLE_BREEDER);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_BREEDER_INVENTORIES);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_BREEDER_FEEDING_RECORDS);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_BREEDER_GROWTH_RECORDS);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_EGG_PRODUCTION);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_EGG_QUALITY);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_HATCHERY_RECORD);


        db.execSQL("DROP TABLE IF EXISTS "+TABLE_MORTALITY_AND_SALES);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_PHENO_MORPHOS);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_PHENO_MORPHO_VALUES);

        db.execSQL("DROP TABLE IF EXISTS "+TABLE_FARMS);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_ROLES);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_ROLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_USERS);



        onCreate(db);
    }

//----------------

    public void deleteTables(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+TABLE_PROFILE);

        db.execSQL("DELETE FROM "+TABLE_PEN);

        db.execSQL("DELETE FROM "+TABLE_GENERATION);
        db.execSQL("DELETE FROM "+TABLE_LINE);
        db.execSQL("DELETE FROM "+TABLE_FAMILY);

        db.execSQL("DELETE FROM "+TABLE_BREEDER);

        db.execSQL("DELETE FROM "+TABLE_BROODER);
        db.execSQL("DELETE FROM "+TABLE_BROODER_INVENTORIES);
        db.execSQL("DELETE FROM "+TABLE_BROODER_FEEDING_RECORDS);
        db.execSQL("DELETE FROM "+TABLE_BROODER_GROWTH_RECORDS);

        db.execSQL("DELETE FROM "+TABLE_REPLACEMENT);
        db.execSQL("DELETE FROM "+TABLE_REPLACEMENT_INVENTORIES);
        db.execSQL("DELETE FROM "+TABLE_REPLACEMENT_FEEDING_RECORDS);
        db.execSQL("DELETE FROM "+TABLE_REPLACEMENT_GROWTH_RECORDS);

        db.execSQL("DELETE FROM "+TABLE_BREEDER);
        db.execSQL("DELETE FROM "+TABLE_BREEDER_INVENTORIES);
        db.execSQL("DELETE FROM "+TABLE_BREEDER_FEEDING_RECORDS);
        db.execSQL("DELETE FROM "+TABLE_BREEDER_GROWTH_RECORDS);
        db.execSQL("DELETE FROM "+TABLE_EGG_PRODUCTION);
        db.execSQL("DELETE FROM "+TABLE_EGG_QUALITY);
        db.execSQL("DELETE FROM "+TABLE_HATCHERY_RECORD);


        db.execSQL("DELETE FROM "+TABLE_MORTALITY_AND_SALES);
        db.execSQL("DELETE FROM "+TABLE_PHENO_MORPHOS);
        db.execSQL("DELETE FROM "+TABLE_PHENO_MORPHO_VALUES);

        db.execSQL("DELETE FROM "+TABLE_FARMS);
        db.execSQL("DELETE FROM "+TABLE_ROLES);
        db.execSQL("DELETE FROM "+TABLE_ROLE_USERS);
        db.execSQL("DELETE FROM "+TABLE_USERS);

    }
    public boolean insertDataUser(String name, String email, String picture, String last_active, Integer farm_id, Integer role_id, String remember_token, String deleted_at){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(USER_COL_2, name);
        contentValues.put(USER_COL_3, email);
        contentValues.put(USER_COL_4, picture);
        contentValues.put(USER_COL_5, last_active);
        contentValues.put(USER_COL_6, farm_id);
        contentValues.put(USER_COL_7, role_id);
        contentValues.put(USER_COL_8, remember_token);
        contentValues.put(USER_COL_9, deleted_at);
        long result = db.insert(TABLE_USERS,null,contentValues);
        if (result == -1)
            return  false;
        else
            return true;

    }
    public boolean insertDataFarm(Integer farm_id, String name, String code, String address, Integer batching_week, Integer breedable_id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(FARM_COL_1, farm_id);
        contentValues.put(FARM_COL_2, name);
        contentValues.put(FARM_COL_3, code);
        contentValues.put(FARM_COL_4, address);
        contentValues.put(FARM_COL_5, batching_week);
        contentValues.put(FARM_COL_6, breedable_id);
        long result = db.insert(TABLE_FARMS,null,contentValues);
        if (result == -1)
            return  false;
        else
            return true;

    }
    public boolean insertDataPen(Integer farm_id, String pen_number, String pen_type, Integer pen_total_capacity, Integer pen_current_capacity, Integer is_active){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PEN_COL_1, farm_id);
        contentValues.put(PEN_COL_2, pen_number);
        contentValues.put(PEN_COL_3, pen_type);
        contentValues.put(PEN_COL_4, pen_total_capacity);
        contentValues.put(PEN_COL_5, pen_current_capacity);
        contentValues.put(PEN_COL_6, is_active);
        long result = db.insert(TABLE_PEN,null,contentValues);
        if (result == -1)
            return  false;
        else
            return true;

    }
    public boolean insertDataPenWithID(Integer id,Integer farm_id, String pen_number, String pen_type, Integer pen_total_capacity, Integer pen_current_capacity, Integer is_active){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PEN_COL_0, id);
        contentValues.put(PEN_COL_1, farm_id);
        contentValues.put(PEN_COL_2, pen_number);
        contentValues.put(PEN_COL_3, pen_type);
        contentValues.put(PEN_COL_4, pen_total_capacity);
        contentValues.put(PEN_COL_5, pen_current_capacity);
        contentValues.put(PEN_COL_6, is_active);
        long result = db.insert(TABLE_PEN,null,contentValues);
        if (result == -1)
            return  false;
        else
            return true;

    }


//--------------------


    public boolean insertDataGeneration(Integer farm_id, String generation_number, Integer numerical_gen, Integer is_active){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(GENERATION_COL_1, farm_id);
        contentValues.put(GENERATION_COL_2, generation_number);
        contentValues.put(GENERATION_COL_3, numerical_gen);
        contentValues.put(GENERATION_COL_4, is_active);



        long result = db.insert(TABLE_GENERATION,null,contentValues);

        if (result == -1)
            return false;
        else
            return true;

    }
    public boolean insertDataGenerationWithID(Integer id,Integer farm_id, String generation_number, Integer numerical_gen, Integer is_active){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(GENERATION_COL_0, id);
        contentValues.put(GENERATION_COL_1, farm_id);
        contentValues.put(GENERATION_COL_2, generation_number);
        contentValues.put(GENERATION_COL_3, numerical_gen);
        contentValues.put(GENERATION_COL_4, is_active);



        long result = db.insert(TABLE_GENERATION,null,contentValues);

        if (result == -1)
            return false;
        else
            return true;

    }

//--------------------


    public boolean insertDataLine(String line_number, Integer is_active, Integer line_generation_number ){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(LINE_COL_1, line_number);
        contentValues.put(LINE_COL_2, is_active);
        contentValues.put(LINE_COL_3, line_generation_number);


        long result = db.insert(TABLE_LINE,null,contentValues);

        if (result == -1)
            return  false;
        else
            return true;

    }
    public boolean insertDataLineWithID(Integer id, String line_number, Integer is_active, Integer line_generation_number ){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(LINE_COL_0, id);
        contentValues.put(LINE_COL_1, line_number);
        contentValues.put(LINE_COL_2, is_active);
        contentValues.put(LINE_COL_3, line_generation_number);


        long result = db.insert(TABLE_LINE,null,contentValues);

        if (result == -1)
            return  false;
        else
            return true;

    }


    /*/*    boolean isInserted = myDb.insertDataFamily(family,1,line_id);

     */
/*    public static final String TABLE_FAMILY = "family_table";
    public static final String FAMILY_COL_0 = "ID";
    public static final String FAMILY_COL_1 = "FAMILY_NUMBER";
    public static final String FAMILY_COL_2 = "is_active";
    public static final String FAMILY_COL_3 = "FAMILY_LINE";
    public static final String FAMILY_COL_4 = "deleted_at";
*/
    public boolean insertDataFamily(String family_number, Integer is_active, Integer family_line_number ){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(FAMILY_COL_1, family_number);
        contentValues.put(FAMILY_COL_2, is_active);
        contentValues.put(FAMILY_COL_3, family_line_number);

        long result = db.insert(TABLE_FAMILY,null,contentValues);

        if (result == -1)
            return  false;
        else
            return true;

    }
    public boolean insertDataFamilyWithID(Integer id, String family_number, Integer is_active, Integer family_line_number, String deleted_at ){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(FAMILY_COL_0, id);
        contentValues.put(FAMILY_COL_1, family_number);
        contentValues.put(FAMILY_COL_2, is_active);
        contentValues.put(FAMILY_COL_3, family_line_number);
        contentValues.put(FAMILY_COL_4, deleted_at);
        long result = db.insert(TABLE_FAMILY,null,contentValues);

        if (result == -1)
            return  false;
        else
            return true;

    }


    public boolean insertDataBrooder(Integer brooder_family_number, String brooder_date_added, String brooder_deleted_at ){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(BROODER_COL_1, brooder_family_number);
        contentValues.put(BROODER_COL_2, brooder_date_added);
        contentValues.put(BROODER_COL_3, brooder_deleted_at);

        long result = db.insert(TABLE_BROODER,null,contentValues);

        if (result == -1)
            return  false;
        else
            return true;

    }
    public boolean insertDataBrooderWithID(Integer id,Integer brooder_family_number, String brooder_date_added, String brooder_deleted_at ){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(BROODER_COL_0, id);
        contentValues.put(BROODER_COL_1, brooder_family_number);
        contentValues.put(BROODER_COL_2, brooder_date_added);
        contentValues.put(BROODER_COL_3, brooder_deleted_at);

        long result = db.insert(TABLE_BROODER,null,contentValues);

        if (result == -1)
            return  false;
        else
            return true;

    }
    public boolean insertDataBreederWithID(Integer id,Integer brooder_family_number, Integer brooder_female_family_number,String brooder_date_added, String brooder_deleted_at ){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(BREEDER_COL_0, id);
        contentValues.put(BREEDER_COL_1, brooder_family_number);
        contentValues.put(BREEDER_COL_2, brooder_female_family_number);
        contentValues.put(BREEDER_COL_3, brooder_date_added);
        contentValues.put(BREEDER_COL_4, brooder_deleted_at);

        long result = db.insert(TABLE_BREEDER,null,contentValues);

        if (result == -1)
            return  false;
        else
            return true;

    }
    public boolean insertDataReplacement(Integer replacement_family_number, String replacement_date_added, String replacment_deleted_at ){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        //contentValues.put(BROODER_COL_0, brooder_id);
        contentValues.put(REPLACEMENT_COL_1, replacement_family_number);
        contentValues.put(REPLACEMENT_COL_2, replacement_date_added);
        contentValues.put(REPLACEMENT_COL_3, replacment_deleted_at);




        long result = db.insert(TABLE_REPLACEMENT,null,contentValues);

        if (result == -1)
            return  false;
        else
            return true;

    }
    public boolean insertDataReplacementWithID(Integer id, Integer replacement_family_number, String replacement_date_added, String replacment_deleted_at ){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(BROODER_COL_0, id);
        contentValues.put(REPLACEMENT_COL_1, replacement_family_number);
        contentValues.put(REPLACEMENT_COL_2, replacement_date_added);
        contentValues.put(REPLACEMENT_COL_3, replacment_deleted_at);




        long result = db.insert(TABLE_REPLACEMENT,null,contentValues);

        if (result == -1)
            return  false;
        else
            return true;

    }
    public boolean insertDataBreeder(Integer brooder_family_number, Integer brooder_female_family_id, String brooder_date_added, String brooder_deleted_at ){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(BREEDER_COL_1, brooder_family_number);
        contentValues.put(BREEDER_COL_2, brooder_female_family_id);
        contentValues.put(BREEDER_COL_3, brooder_date_added);
        contentValues.put(BREEDER_COL_4, brooder_deleted_at);

        long result = db.insert(TABLE_BREEDER,null,contentValues);

        if (result == -1)
            return  false;
        else
            return true;

    }

    public boolean insertDataBrooderInventory(Integer brooder_inv_brooder_id, Integer brooder_inv_pen_number, String brooder_inv_brooder_tag, String brooder_inv_batching_date, Integer brooder_inv_number_male, Integer brooder_inv_number_female, Integer brooder_inv_total, String brooder_inv_last_update, String brooder_inv_deleted_at){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(BROODER_INV_COL_1, brooder_inv_brooder_id);
        contentValues.put(BROODER_INV_COL_2, brooder_inv_pen_number);
        contentValues.put(BROODER_INV_COL_3, brooder_inv_brooder_tag);
        contentValues.put(BROODER_INV_COL_4, brooder_inv_batching_date);
        contentValues.put(BROODER_INV_COL_5, brooder_inv_number_male);
        contentValues.put(BROODER_INV_COL_6, brooder_inv_number_female);
        contentValues.put(BROODER_INV_COL_7, brooder_inv_total);
        contentValues.put(BROODER_INV_COL_8, brooder_inv_last_update);
        contentValues.put(BROODER_INV_COL_9, brooder_inv_deleted_at);

        long result = db.insert(TABLE_BROODER_INVENTORIES,null,contentValues);

        if (result == -1)
            return  false;
        else
            return true;

    }

    public boolean insertDataBrooderInventoryWithID(Integer id, Integer brooder_inv_brooder_id, Integer brooder_inv_pen_number, String brooder_inv_brooder_tag, String brooder_inv_batching_date, Integer brooder_inv_number_male, Integer brooder_inv_number_female, Integer brooder_inv_total, String brooder_inv_last_update, String brooder_inv_deleted_at){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(BROODER_INV_COL_0, id);
        contentValues.put(BROODER_INV_COL_1, brooder_inv_brooder_id);
        contentValues.put(BROODER_INV_COL_2, brooder_inv_pen_number);
        contentValues.put(BROODER_INV_COL_3, brooder_inv_brooder_tag);
        contentValues.put(BROODER_INV_COL_4, brooder_inv_batching_date);
        contentValues.put(BROODER_INV_COL_5, brooder_inv_number_male);
        contentValues.put(BROODER_INV_COL_6, brooder_inv_number_female);
        contentValues.put(BROODER_INV_COL_7, brooder_inv_total);
        contentValues.put(BROODER_INV_COL_8, brooder_inv_last_update);
        contentValues.put(BROODER_INV_COL_9, brooder_inv_deleted_at);

        long result = db.insert(TABLE_BROODER_INVENTORIES,null,contentValues);

        if (result == -1)
            return  false;
        else
            return true;

    }

    public boolean insertDataBreederInventory(Integer brooder_inv_brooder_id, Integer brooder_inv_pen_number, String brooder_inv_brooder_tag, String brooder_inv_batching_date, Integer brooder_inv_number_male, Integer brooder_inv_number_female, Integer brooder_inv_total, String brooder_inv_last_update, String brooder_inv_deleted_at,String breeder_code, String male_wingband, String female_wingband){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(BREEDER_INV_COL_1, brooder_inv_brooder_id);
        contentValues.put(BREEDER_INV_COL_2, brooder_inv_pen_number);
        contentValues.put(BREEDER_INV_COL_3, brooder_inv_brooder_tag);
        contentValues.put(BREEDER_INV_COL_4, brooder_inv_batching_date);
        contentValues.put(BREEDER_INV_COL_5, brooder_inv_number_male);
        contentValues.put(BREEDER_INV_COL_6, brooder_inv_number_female);
        contentValues.put(BREEDER_INV_COL_7, brooder_inv_total);
        contentValues.put(BREEDER_INV_COL_8, brooder_inv_last_update);
        contentValues.put(BREEDER_INV_COL_9, brooder_inv_deleted_at);

        contentValues.put(BREEDER_INV_COL_10, breeder_code);
        contentValues.put(BREEDER_INV_COL_11, male_wingband);
        contentValues.put(BREEDER_INV_COL_12, female_wingband);

        long result = db.insert(TABLE_BREEDER_INVENTORIES,null,contentValues);

        if (result == -1)
            return  false;
        else
            return true;

    }
    public boolean insertDataBreederInventoryWithID(Integer id, Integer brooder_inv_brooder_id, Integer brooder_inv_pen_number, String brooder_inv_brooder_tag, String brooder_inv_batching_date, Integer brooder_inv_number_male, Integer brooder_inv_number_female, Integer brooder_inv_total, String brooder_inv_last_update, String brooder_inv_deleted_at, String breeder_code, String male_wingband, String female_wingband){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(BREEDER_INV_COL_0, id);
        contentValues.put(BREEDER_INV_COL_1, brooder_inv_brooder_id);
        contentValues.put(BREEDER_INV_COL_2, brooder_inv_pen_number);
        contentValues.put(BREEDER_INV_COL_3, brooder_inv_brooder_tag);
        contentValues.put(BREEDER_INV_COL_4, brooder_inv_batching_date);
        contentValues.put(BREEDER_INV_COL_5, brooder_inv_number_male);
        contentValues.put(BREEDER_INV_COL_6, brooder_inv_number_female);
        contentValues.put(BREEDER_INV_COL_7, brooder_inv_total);
        contentValues.put(BREEDER_INV_COL_8, brooder_inv_last_update);
        contentValues.put(BREEDER_INV_COL_9, brooder_inv_deleted_at);
        contentValues.put(BREEDER_INV_COL_10, breeder_code);
        contentValues.put(BREEDER_INV_COL_11, male_wingband);
        contentValues.put(BREEDER_INV_COL_12, female_wingband);


        long result = db.insert(TABLE_BREEDER_INVENTORIES,null,contentValues);

        if (result == -1)
            return  false;
        else
            return true;

    }
    public boolean insertDataReplacementInventory(Integer brooder_inv_brooder_id, Integer brooder_inv_pen_number, String brooder_inv_brooder_tag, String brooder_inv_batching_date, Integer brooder_inv_number_male, Integer brooder_inv_number_female, Integer brooder_inv_total, String brooder_inv_last_update, String brooder_inv_deleted_at){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(REPLACEMENT_INV_COL_1, brooder_inv_brooder_id);
        contentValues.put(REPLACEMENT_INV_COL_2, brooder_inv_pen_number);
        contentValues.put(REPLACEMENT_INV_COL_3, brooder_inv_brooder_tag);
        contentValues.put(REPLACEMENT_INV_COL_4, brooder_inv_batching_date);
        contentValues.put(REPLACEMENT_INV_COL_5, brooder_inv_number_male);
        contentValues.put(REPLACEMENT_INV_COL_6, brooder_inv_number_female);
        contentValues.put(REPLACEMENT_INV_COL_7, brooder_inv_total);
        contentValues.put(REPLACEMENT_INV_COL_8, brooder_inv_last_update);
        contentValues.put(REPLACEMENT_INV_COL_9, brooder_inv_deleted_at);

        long result = db.insert(TABLE_REPLACEMENT_INVENTORIES,null,contentValues);

        if (result == -1)
            return  false;
        else
            return true;

    }


    public boolean insertDataReplacementInventoryWithID(Integer id,Integer brooder_inv_brooder_id, Integer brooder_inv_pen_number, String brooder_inv_brooder_tag, String brooder_inv_batching_date, Integer brooder_inv_number_male, Integer brooder_inv_number_female, Integer brooder_inv_total, String brooder_inv_last_update, String brooder_inv_deleted_at){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(REPLACEMENT_INV_COL_0, id);
        contentValues.put(REPLACEMENT_INV_COL_1, brooder_inv_brooder_id);
        contentValues.put(REPLACEMENT_INV_COL_2, brooder_inv_pen_number);
        contentValues.put(REPLACEMENT_INV_COL_3, brooder_inv_brooder_tag);
        contentValues.put(REPLACEMENT_INV_COL_4, brooder_inv_batching_date);
        contentValues.put(REPLACEMENT_INV_COL_5, brooder_inv_number_male);
        contentValues.put(REPLACEMENT_INV_COL_6, brooder_inv_number_female);
        contentValues.put(REPLACEMENT_INV_COL_7, brooder_inv_total);
        contentValues.put(REPLACEMENT_INV_COL_8, brooder_inv_last_update);
        contentValues.put(REPLACEMENT_INV_COL_9, brooder_inv_deleted_at);

        long result = db.insert(TABLE_REPLACEMENT_INVENTORIES,null,contentValues);

        if (result == -1)
            return  false;
        else
            return true;

    }


    public boolean insertDataBrooderFeedingRecords(Integer brooder_feed_brooder_id, String brooder_feed_date_collected, Float brooder_feed_offered, Float brooder_feed_refused, String brooder_feed_remarks, String brooder_feed_deleted_at){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(BROODER_FEEDING_COL_1, brooder_feed_brooder_id);
        contentValues.put(BROODER_FEEDING_COL_2, brooder_feed_date_collected);
        contentValues.put(BROODER_FEEDING_COL_3, brooder_feed_offered);
        contentValues.put(BROODER_FEEDING_COL_4, brooder_feed_refused);
        contentValues.put(BROODER_FEEDING_COL_5, brooder_feed_remarks);
        contentValues.put(BROODER_FEEDING_COL_6, brooder_feed_deleted_at);


        long result = db.insert(TABLE_BROODER_FEEDING_RECORDS,null,contentValues);

        if (result == -1)
            return  false;
        else
            return true;

    }
    public boolean insertDataBrooderFeedingRecordsWithID(Integer id, Integer brooder_feed_brooder_id, String brooder_feed_date_collected, Float brooder_feed_offered, Float brooder_feed_refused, String brooder_feed_remarks, String brooder_feed_deleted_at){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(BROODER_FEEDING_COL_0, id);
        contentValues.put(BROODER_FEEDING_COL_1, brooder_feed_brooder_id);
        contentValues.put(BROODER_FEEDING_COL_2, brooder_feed_date_collected);
        contentValues.put(BROODER_FEEDING_COL_3, brooder_feed_offered);
        contentValues.put(BROODER_FEEDING_COL_4, brooder_feed_refused);
        contentValues.put(BROODER_FEEDING_COL_5, brooder_feed_remarks);
        contentValues.put(BROODER_FEEDING_COL_6, brooder_feed_deleted_at);


        long result = db.insert(TABLE_BROODER_FEEDING_RECORDS,null,contentValues);

        if (result == -1)
            return  false;
        else
            return true;

    }

    public boolean insertDataBreederFeedingRecords(Integer brooder_feed_brooder_id, String brooder_feed_date_collected, Float brooder_feed_offered, Float brooder_feed_refused, String brooder_feed_remarks, String brooder_feed_deleted_at){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(BREEDER_FEEDING_COL_1, brooder_feed_brooder_id);
        contentValues.put(BREEDER_FEEDING_COL_2, brooder_feed_date_collected);
        contentValues.put(BREEDER_FEEDING_COL_3, brooder_feed_offered);
        contentValues.put(BREEDER_FEEDING_COL_4, brooder_feed_refused);
        contentValues.put(BREEDER_FEEDING_COL_5, brooder_feed_remarks);
        contentValues.put(BREEDER_FEEDING_COL_6, brooder_feed_deleted_at);


        long result = db.insert(TABLE_BREEDER_FEEDING_RECORDS,null,contentValues);

        if (result == -1)
            return  false;
        else
            return true;

    }
    public boolean insertDataBreederFeedingRecordsWithID(Integer id, Integer brooder_feed_brooder_id, String brooder_feed_date_collected, Float brooder_feed_offered, Float brooder_feed_refused, String brooder_feed_remarks, String brooder_feed_deleted_at){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(BREEDER_FEEDING_COL_0, id);
        contentValues.put(BREEDER_FEEDING_COL_1, brooder_feed_brooder_id);
        contentValues.put(BREEDER_FEEDING_COL_2, brooder_feed_date_collected);
        contentValues.put(BREEDER_FEEDING_COL_3, brooder_feed_offered);
        contentValues.put(BREEDER_FEEDING_COL_4, brooder_feed_refused);
        contentValues.put(BREEDER_FEEDING_COL_5, brooder_feed_remarks);
        contentValues.put(BREEDER_FEEDING_COL_6, brooder_feed_deleted_at);


        long result = db.insert(TABLE_BREEDER_FEEDING_RECORDS,null,contentValues);

        if (result == -1)
            return  false;
        else
            return true;

    }

    public boolean insertDataReplacementFeedingRecords(Integer brooder_feed_brooder_id, String brooder_feed_date_collected, Float brooder_feed_offered, Float brooder_feed_refused, String brooder_feed_remarks, String brooder_feed_deleted_at){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(REPLACEMENT_FEEDING_COL_1, brooder_feed_brooder_id);
        contentValues.put(REPLACEMENT_FEEDING_COL_2, brooder_feed_date_collected);
        contentValues.put(REPLACEMENT_FEEDING_COL_3, brooder_feed_offered);
        contentValues.put(REPLACEMENT_FEEDING_COL_4, brooder_feed_refused);
        contentValues.put(REPLACEMENT_FEEDING_COL_5, brooder_feed_remarks);
        contentValues.put(REPLACEMENT_FEEDING_COL_6, brooder_feed_deleted_at);


        long result = db.insert(TABLE_REPLACEMENT_FEEDING_RECORDS,null,contentValues);

        if (result == -1)
            return  false;
        else
            return true;

    }
    public boolean insertDataReplacementFeedingRecordsWithID(Integer id,Integer brooder_feed_brooder_id, String brooder_feed_date_collected, Float brooder_feed_offered, Float brooder_feed_refused, String brooder_feed_remarks, String brooder_feed_deleted_at){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(REPLACEMENT_FEEDING_COL_0, id);
        contentValues.put(REPLACEMENT_FEEDING_COL_1, brooder_feed_brooder_id);
        contentValues.put(REPLACEMENT_FEEDING_COL_2, brooder_feed_date_collected);
        contentValues.put(REPLACEMENT_FEEDING_COL_3, brooder_feed_offered);
        contentValues.put(REPLACEMENT_FEEDING_COL_4, brooder_feed_refused);
        contentValues.put(REPLACEMENT_FEEDING_COL_5, brooder_feed_remarks);
        contentValues.put(REPLACEMENT_FEEDING_COL_6, brooder_feed_deleted_at);


        long result = db.insert(TABLE_REPLACEMENT_FEEDING_RECORDS,null,contentValues);

        if (result == -1)
            return  false;
        else
            return true;

    }
    public boolean insertDataBrooderGrowthRecords(Integer brooder_growth_brooder_id, Integer brooder_growth_collection_day, String brooder_growth_date_collected, Integer brooder_growth_male_quantity, Float brooder_growth_male_weight, Integer brooder_growth_female_quantity, Float brooder_growth_female_weight, Integer brooder_growth_total_quantity, Float brooder_growth_total_weight, String brooder_growth_deleted_at){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(BROODER_GROWTH_COL_1, brooder_growth_brooder_id);
        contentValues.put(BROODER_GROWTH_COL_2, brooder_growth_collection_day);
        contentValues.put(BROODER_GROWTH_COL_3, brooder_growth_date_collected);
        contentValues.put(BROODER_GROWTH_COL_4, brooder_growth_male_quantity);
        contentValues.put(BROODER_GROWTH_COL_5, brooder_growth_male_weight);
        contentValues.put(BROODER_GROWTH_COL_6, brooder_growth_female_quantity);
        contentValues.put(BROODER_GROWTH_COL_7, brooder_growth_female_weight);
        contentValues.put(BROODER_GROWTH_COL_8, brooder_growth_total_quantity);
        contentValues.put(BROODER_GROWTH_COL_9, brooder_growth_total_weight);
        contentValues.put(BROODER_GROWTH_COL_10, brooder_growth_deleted_at);


        long result = db.insert(TABLE_BROODER_GROWTH_RECORDS,null,contentValues);

        if (result == -1)
            return  false;
        else
            return true;

    }

    public boolean insertDataBrooderGrowthRecordsWithID(Integer id,Integer brooder_growth_brooder_id, Integer brooder_growth_collection_day, String brooder_growth_date_collected, Integer brooder_growth_male_quantity, Float brooder_growth_male_weight, Integer brooder_growth_female_quantity, Float brooder_growth_female_weight, Integer brooder_growth_total_quantity, Float brooder_growth_total_weight, String brooder_growth_deleted_at){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(BROODER_GROWTH_COL_0, id);
        contentValues.put(BROODER_GROWTH_COL_1, brooder_growth_brooder_id);
        contentValues.put(BROODER_GROWTH_COL_2, brooder_growth_collection_day);
        contentValues.put(BROODER_GROWTH_COL_3, brooder_growth_date_collected);
        contentValues.put(BROODER_GROWTH_COL_4, brooder_growth_male_quantity);
        contentValues.put(BROODER_GROWTH_COL_5, brooder_growth_male_weight);
        contentValues.put(BROODER_GROWTH_COL_6, brooder_growth_female_quantity);
        contentValues.put(BROODER_GROWTH_COL_7, brooder_growth_female_weight);
        contentValues.put(BROODER_GROWTH_COL_8, brooder_growth_total_quantity);
        contentValues.put(BROODER_GROWTH_COL_9, brooder_growth_total_weight);
        contentValues.put(BROODER_GROWTH_COL_10, brooder_growth_deleted_at);


        long result = db.insert(TABLE_BROODER_GROWTH_RECORDS,null,contentValues);

        if (result == -1)
            return  false;
        else
            return true;

    }






    public boolean insertDataReplacementGrowthRecords(Integer brooder_growth_brooder_id, Integer brooder_growth_collection_day, String brooder_growth_date_collected, Integer brooder_growth_male_quantity, Float brooder_growth_male_weight, Integer brooder_growth_female_quantity, Float brooder_growth_female_weight, Integer brooder_growth_total_quantity, Float brooder_growth_total_weight, String brooder_growth_deleted_at){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(REPLACEMENT_GROWTH_COL_1, brooder_growth_brooder_id);
        contentValues.put(REPLACEMENT_GROWTH_COL_2, brooder_growth_collection_day);
        contentValues.put(REPLACEMENT_GROWTH_COL_3, brooder_growth_date_collected);
        contentValues.put(REPLACEMENT_GROWTH_COL_4, brooder_growth_male_quantity);
        contentValues.put(REPLACEMENT_GROWTH_COL_5, brooder_growth_male_weight);
        contentValues.put(REPLACEMENT_GROWTH_COL_6, brooder_growth_female_quantity);
        contentValues.put(REPLACEMENT_GROWTH_COL_7, brooder_growth_female_weight);
        contentValues.put(REPLACEMENT_GROWTH_COL_8, brooder_growth_total_quantity);
        contentValues.put(REPLACEMENT_GROWTH_COL_9, brooder_growth_total_weight);
        contentValues.put(REPLACEMENT_GROWTH_COL_10, brooder_growth_deleted_at);


        long result = db.insert(TABLE_REPLACEMENT_GROWTH_RECORDS,null,contentValues);

        if (result == -1)
            return  false;
        else
            return true;

    }
/*    db.execSQL("create table " + TABLE_REPLACEMENT_PHENOTYPIC_AND_MORPHOMETRIC_RECORDS +" (ID INTEGER PRIMARY KEY, REPLACEMENT_PHENO_MORPHO_INVENTORY_ID INTEGER REFERENCES TABLE_REPLACEMENT_INVENTORIES(ID),  REPLACEMENT_PHENO_MORPHO_COLLECTED TEXT,  REPLACEMENT_PHENO_MORPHO_SEX TEXT, REPLACEMENT_PHENO_MORPHO_TAG_OR_REGISTRY TEXT,REPLACEMENT_PHENO_RECORD TEXT, REPLACEMENT_MORPHO_RECORD TEXT)");
 */

    public boolean insertDataReplacementPhenoMorphoRecords(Integer inv_id,  String date, String sex, String tag, String pheno, String morpho){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(REPLACEMENT_PHENO_MORPHO_COL_1, inv_id);
        contentValues.put(REPLACEMENT_PHENO_MORPHO_COL_2, date);
        contentValues.put(REPLACEMENT_PHENO_MORPHO_COL_3, sex);
        contentValues.put(REPLACEMENT_PHENO_MORPHO_COL_4, tag);
        contentValues.put(REPLACEMENT_PHENO_MORPHO_COL_5, pheno);
        contentValues.put(REPLACEMENT_PHENO_MORPHO_COL_6, morpho);



        long result = db.insert(TABLE_REPLACEMENT_PHENOTYPIC_AND_MORPHOMETRIC_RECORDS,null,contentValues);

        if (result == -1)
            return  false;
        else
            return true;

    }

    public boolean insertDataBreederPhenoMorphoRecords(Integer inv_id,  String date, String sex, String tag, String pheno, String morpho){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(BREEDER_PHENO_MORPHO_COL_1, inv_id);
        contentValues.put(BREEDER_PHENO_MORPHO_COL_2, date);
        contentValues.put(BREEDER_PHENO_MORPHO_COL_3, sex);
        contentValues.put(BREEDER_PHENO_MORPHO_COL_4, tag);
        contentValues.put(BREEDER_PHENO_MORPHO_COL_5, pheno);
        contentValues.put(BREEDER_PHENO_MORPHO_COL_6, morpho);



        long result = db.insert(TABLE_BREEDER_PHENOTYPIC_AND_MORPHOMETRIC_RECORDS,null,contentValues);

        if (result == -1)
            return  false;
        else
            return true;

    }

public boolean insertEggQualityRecords(Integer breeder_inv_id, String date, Integer week,Float weight, String color, String shape, Float length, Float width, Float al_height, Float al_weight, Float yolk_weight, String yolk_color, Float shell_weight, Float shell_thick_top, Float shell_thick_middle, Float shell_thick_bottom){
    SQLiteDatabase db = this.getWritableDatabase();

    ContentValues contentValues = new ContentValues();

    contentValues.put(EGG_QUALITY_COL_1, breeder_inv_id);
    contentValues.put(EGG_QUALITY_COL_2, date);
    contentValues.put(EGG_QUALITY_COL_3, week);
    contentValues.put(EGG_QUALITY_COL_4, weight);
    contentValues.put(EGG_QUALITY_COL_5, color);
    contentValues.put(EGG_QUALITY_COL_6, shape);
    contentValues.put(EGG_QUALITY_COL_7, length);
    contentValues.put(EGG_QUALITY_COL_8, width);
    contentValues.put(EGG_QUALITY_COL_9, al_height);
    contentValues.put(EGG_QUALITY_COL_10, al_weight);
    contentValues.put(EGG_QUALITY_COL_11, yolk_weight);
    contentValues.put(EGG_QUALITY_COL_12, yolk_color);
    contentValues.put(EGG_QUALITY_COL_13, shell_weight);
    contentValues.put(EGG_QUALITY_COL_14, shell_thick_top);
    contentValues.put(EGG_QUALITY_COL_15, shell_thick_middle);
    contentValues.put(EGG_QUALITY_COL_16, shell_thick_bottom);



    long result = db.insert(TABLE_EGG_QUALITY,null,contentValues);

    if (result == -1)
        return  false;
    else
        return true;

}
    public boolean insertEggQualityRecordsWithID(Integer id,Integer breeder_inv_id, String date, Integer week,Float weight, String color, String shape, Float length, Float width, Float al_height, Float al_weight, Float yolk_weight, String yolk_color, Float shell_weight, Float shell_thick_top, Float shell_thick_middle, Float shell_thick_bottom){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(EGG_QUALITY_COL_0, id);
        contentValues.put(EGG_QUALITY_COL_1, breeder_inv_id);
        contentValues.put(EGG_QUALITY_COL_2, date);
        contentValues.put(EGG_QUALITY_COL_3, week);
        contentValues.put(EGG_QUALITY_COL_4, weight);
        contentValues.put(EGG_QUALITY_COL_5, color);
        contentValues.put(EGG_QUALITY_COL_6, shape);
        contentValues.put(EGG_QUALITY_COL_7, length);
        contentValues.put(EGG_QUALITY_COL_8, width);
        contentValues.put(EGG_QUALITY_COL_9, al_height);
        contentValues.put(EGG_QUALITY_COL_10, al_weight);
        contentValues.put(EGG_QUALITY_COL_11, yolk_weight);
        contentValues.put(EGG_QUALITY_COL_12, yolk_color);
        contentValues.put(EGG_QUALITY_COL_13, shell_weight);
        contentValues.put(EGG_QUALITY_COL_14, shell_thick_top);
        contentValues.put(EGG_QUALITY_COL_15, shell_thick_middle);
        contentValues.put(EGG_QUALITY_COL_16, shell_thick_bottom);



        long result = db.insert(TABLE_EGG_QUALITY,null,contentValues);

        if (result == -1)
            return  false;
        else
            return true;

    }
    public boolean insertEggProductionRecords(Integer breeder_inv_id, String date, Integer intact, Float weight, Integer broken, Integer rejects, String remarks, String deleted_at, String female_inventory){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(EGG_PRODUCTION_COL_1, breeder_inv_id);
        contentValues.put(EGG_PRODUCTION_COL_2, date);
        contentValues.put(EGG_PRODUCTION_COL_3, intact);
        contentValues.put(EGG_PRODUCTION_COL_4, weight);
        contentValues.put(EGG_PRODUCTION_COL_5, broken);
        contentValues.put(EGG_PRODUCTION_COL_6, rejects);
        contentValues.put(EGG_PRODUCTION_COL_7, remarks);
        contentValues.put(EGG_PRODUCTION_COL_8, deleted_at);
        contentValues.put(EGG_PRODUCTION_COL_9, female_inventory);


        long result = db.insert(TABLE_EGG_PRODUCTION,null,contentValues);

        if (result == -1)
            return  false;
        else
            return true;

    }
    public boolean insertEggProductionRecordsWithID(Integer id,Integer breeder_inv_id, String date, Integer intact, Float weight, Integer broken, Integer rejects, String remarks, String deleted_at,String female_inventory){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(EGG_PRODUCTION_COL_0, id);
        contentValues.put(EGG_PRODUCTION_COL_1, breeder_inv_id);
        contentValues.put(EGG_PRODUCTION_COL_2, date);
        contentValues.put(EGG_PRODUCTION_COL_3, intact);
        contentValues.put(EGG_PRODUCTION_COL_4, weight);
        contentValues.put(EGG_PRODUCTION_COL_5, broken);
        contentValues.put(EGG_PRODUCTION_COL_6, rejects);
        contentValues.put(EGG_PRODUCTION_COL_7, remarks);
        contentValues.put(EGG_PRODUCTION_COL_8, deleted_at);
        contentValues.put(EGG_PRODUCTION_COL_9, female_inventory);

        long result = db.insert(TABLE_EGG_PRODUCTION,null,contentValues);

        if (result == -1)
            return  false;
        else
            return true;

    }
    public boolean insertHatcheryRecords(Integer breeder_inv_id, String date, String batch_date, Integer set, Integer week, Integer fertile, Integer hatched,String date_hatched, String deleted_at){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(HATCHERY_COL_1, breeder_inv_id);
        contentValues.put(HATCHERY_COL_2, date);
        contentValues.put(HATCHERY_COL_3, batch_date);
        contentValues.put(HATCHERY_COL_4, set);
        contentValues.put(HATCHERY_COL_5, week);
        contentValues.put(HATCHERY_COL_6, fertile);
        contentValues.put(HATCHERY_COL_7, hatched);
        contentValues.put(HATCHERY_COL_8, date_hatched);
        contentValues.put(HATCHERY_COL_9, deleted_at);


        long result = db.insert(TABLE_HATCHERY_RECORD,null,contentValues);

        if (result == -1)
            return  false;
        else
            return true;

    }
    public boolean insertHatcheryRecordsWithID(Integer id,Integer breeder_inv_id, String date, String batch_date, Integer set, Integer week, Integer fertile, Integer hatched,String date_hatched, String deleted_at){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(HATCHERY_COL_0, id);
        contentValues.put(HATCHERY_COL_1, breeder_inv_id);
        contentValues.put(HATCHERY_COL_2, date);
        contentValues.put(HATCHERY_COL_3, batch_date);
        contentValues.put(HATCHERY_COL_4, set);
        contentValues.put(HATCHERY_COL_5, week);
        contentValues.put(HATCHERY_COL_6, fertile);
        contentValues.put(HATCHERY_COL_7, hatched);
        contentValues.put(HATCHERY_COL_8, date_hatched);
        contentValues.put(HATCHERY_COL_9, deleted_at);


        long result = db.insert(TABLE_HATCHERY_RECORD,null,contentValues);

        if (result == -1)
            return  false;
        else
            return true;

    }
    public boolean insertDataMortalityAndSales(String date, Integer breeder_id, Integer replacement_id, Integer brooder_id, String type, String category, Float price, Integer male_quantity, Integer female_quantity, Integer total, String reason, String remarks, String deleted_at){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(MORT_SALES_COL_1, date);
        contentValues.put(MORT_SALES_COL_2, breeder_id);
        contentValues.put(MORT_SALES_COL_3, replacement_id);
        contentValues.put(MORT_SALES_COL_4, brooder_id);
        contentValues.put(MORT_SALES_COL_5, type);
        contentValues.put(MORT_SALES_COL_6, category);
        contentValues.put(MORT_SALES_COL_7, price);
        contentValues.put(MORT_SALES_COL_8, male_quantity);
        contentValues.put(MORT_SALES_COL_9, female_quantity);
        contentValues.put(MORT_SALES_COL_10, total);
        contentValues.put(MORT_SALES_COL_11, reason);
        contentValues.put(MORT_SALES_COL_12, remarks);
        contentValues.put(MORT_SALES_COL_13, deleted_at);


        long result = db.insert(TABLE_MORTALITY_AND_SALES,null,contentValues);

        if (result == -1)
            return  false;
        else
            return true;

    }
    public boolean insertDataMortalityAndSalesWithID(Integer id, String date, Integer breeder_id, Integer replacement_id, Integer brooder_id, String type, String category, Float price, Integer male_quantity, Integer female_quantity, Integer total, String reason, String remarks, String deleted_at){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(MORT_SALES_COL_0, id);
        contentValues.put(MORT_SALES_COL_1, date);
        contentValues.put(MORT_SALES_COL_2, breeder_id);
        contentValues.put(MORT_SALES_COL_3, replacement_id);
        contentValues.put(MORT_SALES_COL_4, brooder_id);
        contentValues.put(MORT_SALES_COL_5, type);
        contentValues.put(MORT_SALES_COL_6, category);
        contentValues.put(MORT_SALES_COL_7, price);
        contentValues.put(MORT_SALES_COL_8, male_quantity);
        contentValues.put(MORT_SALES_COL_9, female_quantity);
        contentValues.put(MORT_SALES_COL_10, total);
        contentValues.put(MORT_SALES_COL_11, reason);
        contentValues.put(MORT_SALES_COL_12, remarks);
        contentValues.put(MORT_SALES_COL_13, deleted_at);


        long result = db.insert(TABLE_MORTALITY_AND_SALES,null,contentValues);

        if (result == -1)
            return  false;
        else
            return true;

    }

    public boolean insertDataAddress(String region, String province, String town, String barangay){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        //contentValues.put(COL_1, id);
        //contentValues.put(COL_2, breed);
        contentValues.put(COL_3, region);
        contentValues.put(COL_4, province);
        contentValues.put(COL_5, town);
        contentValues.put(COL_6, barangay);
        long result = db.insert(TABLE_PROFILE,null,contentValues);

        if (result == -1)
            return  false;
        else
            return true;
    }
    public boolean insertDataContacts(String id, String phone, String email){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, id);
        contentValues.put(COL_7, phone);
        contentValues.put(COL_8, email);
        long result = db.insert(TABLE_PROFILE,null,contentValues);

        if (result == -1)
            return  false;
        else
            return true;

    }

    public boolean insertPhenoMorphoRecords(String gender, String tag, String phenotypic, String morphometric, String date_collected, String  deleted_at){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PHENO_MORPHO_VALUES_COL_1, gender);
        contentValues.put(PHENO_MORPHO_VALUES_COL_2, tag);
        contentValues.put(PHENO_MORPHO_VALUES_COL_3, phenotypic);
        contentValues.put(PHENO_MORPHO_VALUES_COL_4, morphometric);
        contentValues.put(PHENO_MORPHO_VALUES_COL_5, date_collected);
        contentValues.put(PHENO_MORPHO_VALUES_COL_6, deleted_at);
        long result = db.insert(TABLE_PHENO_MORPHO_VALUES,null,contentValues);

        if (result == -1)
            return  false;
        else
            return true;
    }
    public boolean insertPhenoMorphoRecordsWithID(Integer id,String gender, String tag, String phenotypic, String morphometric, String date_collected, String  deleted_at){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PHENO_MORPHO_VALUES_COL_0, id);
        contentValues.put(PHENO_MORPHO_VALUES_COL_1, gender);
        contentValues.put(PHENO_MORPHO_VALUES_COL_2, tag);
        contentValues.put(PHENO_MORPHO_VALUES_COL_3, phenotypic);
        contentValues.put(PHENO_MORPHO_VALUES_COL_4, morphometric);
        contentValues.put(PHENO_MORPHO_VALUES_COL_5, date_collected);
        contentValues.put(PHENO_MORPHO_VALUES_COL_6, deleted_at);
        long result = db.insert(TABLE_PHENO_MORPHO_VALUES,null,contentValues);

        if (result == -1)
            return  false;
        else
            return true;
    }

    public boolean insertPhenoMorphos(Integer replacement_inv, Integer breeder_inv, Integer values_id, String deleted_at){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PHENO_MORPHOS_COL_1, replacement_inv);
        contentValues.put(PHENO_MORPHOS_COL_2, breeder_inv);
        contentValues.put(PHENO_MORPHOS_COL_3, values_id);
        contentValues.put(PHENO_MORPHOS_COL_4, deleted_at);
        long result = db.insert(TABLE_PHENO_MORPHOS,null,contentValues);

        if (result == -1)
            return  false;
        else
            return true;
    }
    public boolean insertPhenoMorphosWithID(Integer id, Integer replacement_inv, Integer breeder_inv, Integer values_id, String deleted_at){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PHENO_MORPHOS_COL_0, id);
        contentValues.put(PHENO_MORPHOS_COL_1, replacement_inv);
        contentValues.put(PHENO_MORPHOS_COL_2, breeder_inv);
        contentValues.put(PHENO_MORPHOS_COL_3, values_id);
        contentValues.put(PHENO_MORPHOS_COL_4, deleted_at);
        long result = db.insert(TABLE_PHENO_MORPHOS,null,contentValues);

        if (result == -1)
            return  false;
        else
            return true;
    }
    public void deleteAll()
    {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("delete from "+ TABLE_PEN);

        db.close();
    }

    public Cursor getAllDataFromGeneration(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " +TABLE_GENERATION, null);
        return res;
    }
    public Cursor getAllDataFromGenerationWhereID(Integer id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " +TABLE_GENERATION+ " where ID is ?", new String[]{id.toString()});
        return res;
    }
    public Cursor getAllDataFromLine(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " +TABLE_LINE, null);

        return res;
    }
    public Cursor getAllDataFromLineWhereID(Integer id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " +TABLE_LINE+ " where ID is ?", new String[]{id.toString()});

        return res;
    }
    public Cursor getAllDataFromFamily(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " +TABLE_FAMILY, null);

        return res;
    }


    public Cursor getAllDataFromFarms(Integer id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " +TABLE_FARMS+ " where ID is ?", new String[]{id.toString()});

        return res;
    }

    public Cursor getAllDataFromFamilyWhereID(Integer id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " +TABLE_FAMILY+ " where ID is ?", new String[]{id.toString()});

        return res;
    }

    public Cursor getDataFromGenerationWhereGenNumber(String number){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " +TABLE_GENERATION+ " where GENERATION_NUMBER is ?", new String[]{number});

        return res;
    }
    public Cursor getDataFromGenerationWhereID(Integer id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " +TABLE_GENERATION+ " where ID is ?", new String[]{id.toString()});

        return res;
    }
    public Cursor getDataFromLineWhereLineNumber(String number){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " +TABLE_LINE+ " where LINE_NUMBER is ?", new String[]{number});

        return res;
    }
    public Cursor getDataFromLineWhereID(Integer id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " +TABLE_LINE+ " where ID is ?", new String[]{id.toString()});

        return res;
    }


    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " +TABLE_PROFILE, null);
        return res;
    }



    public Cursor getAllDataFromPen(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " +TABLE_PEN, null);
        return res;
    }
/*
    public static final String TABLE_PEN = "pen_table";
    public static final String PEN_COL_0 = "ID";
    public static final String PEN_COL_1 = "farm_id";
    public static final String PEN_COL_2 = "PEN_NUMBER";
    public static final String PEN_COL_3 = "PEN_TYPE";
    public static final String PEN_COL_4 = "PEN_TOTAL_CAPACITY";
    public static final String PEN_COL_5 = "PEN_CURRENT_CAPACITY";
    public static final String PEN_COL_6 = "PEN_IS_ACTIVE";
*/
    public Cursor getAllDataFromPenWhere(String pen_number){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " +TABLE_PEN+ " where PEN_NUMBER is ?", new String[]{pen_number});
        return res;
    }
    public Cursor getAllDataFromPenWhereID(Integer id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " +TABLE_PEN+ " where ID like ?", new String[]{id.toString()});
        return res;
    }














    public Cursor getAllDataFromBrooders(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " +TABLE_BROODER,null);
        return res;
    }
    public Cursor getIDFromBroodersWhere(Integer family){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " +TABLE_BROODER+ " where BROODER_FAMILY is ?", new String[]{family.toString()},null);
        return res;
    }
    public Cursor getBroodersFromPen(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " +TABLE_PEN+ " where PEN_TYPE LIKE '%Brooder%'", null);
        return res;
    }

    public Cursor getAllDataFromBroodersWhereID(Integer id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " +TABLE_BROODER+ " where ID is ?", new String[]{id.toString()});

        return res;
    }


    public Cursor getAllDataFromBrooderInventory(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " +TABLE_BROODER_INVENTORIES, null);

        return res;
    }
    public Cursor getAllDataFromBrooderInventoryWhereID(Integer id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " +TABLE_BROODER_INVENTORIES+ " where ID is ?", new String[]{id.toString()});

        return res;
    }
    public Cursor getDataFromBrooderInventoryWherePenAndID(String tag, Integer pen){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " +TABLE_BROODER_INVENTORIES + " where BROODER_INV_BROODER_TAG is ? and BROODER_INV_PEN_NUMBER is ?", new String[] {tag, pen.toString()});

        return res;
    }
    public Cursor getDataFromBrooderInventoryWherePen(Integer pen){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " +TABLE_BROODER_INVENTORIES + " where BROODER_INV_PEN_NUMBER is ?", new String[] {pen.toString()});

        return res;
    }
    public Cursor getDataFromBrooderInventoryWhereTag(String tag){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " +TABLE_BROODER_INVENTORIES + " where BROODER_INV_BROODER_TAG is ?", new String[]{tag});

        return res;
    }
    public Cursor getAllDataFromBrooderFeedingRecords(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " +TABLE_BROODER_FEEDING_RECORDS, null);

        return res;
    }
    public Cursor getDataFromBrooderFeedingRecordsWhereFeedingID(Integer id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " +TABLE_BROODER_FEEDING_RECORDS+ " where ID is ?", new String[]{id.toString()});

        return res;
    }

    public Cursor getAllDataFromBrooderFeedingRecordsWhereFeedingID(Integer id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select BROODER_FEEDING_AMOUNT_OFFERED,BROODER_FEEDING_AMOUNT_REFUSED,BROODER_FEEDING_REMARKS,BROODER_FEEDING_DATE_COLLECTED,ID,BROODER_FEEDING_INVENTORY_ID from " +TABLE_BROODER_FEEDING_RECORDS+ " where ID is ?",new String[]{id.toString()});

        return res;
    }

    /*  requestParams.add("broodergrower_inventory_id", key.toString());
                            requestParams.add("date_collected", brooder_feeding_date_collected.getText().toString());
                            requestParams.add("amount_offered", valueOffered.toString());
                            requestParams.add("amount_refused", valueRefused.toString());
                            requestParams.add("remarks", brooder_feeding_record_remarks.getText().toString());
                            requestParams.add("deleted_at", null);
*/

    /*    public static final String BROODER_FEEDING_COL_0 = "ID";
    public static final String BROODER_FEEDING_COL_1 = "BROODER_FEEDING_INVENTORY_ID"; //REFERENCE SA BROODER INV ID
    public static final String BROODER_FEEDING_COL_2 = "BROODER_FEEDING_DATE_COLLECTED";
    public static final String BROODER_FEEDING_COL_3 = "BROODER_FEEDING_AMOUNT_OFFERED";
    public static final String BROODER_FEEDING_COL_4 = "BROODER_FEEDING_AMOUNT_REFUSED";
    public static final String BROODER_FEEDING_COL_5 = "BROODER_FEEDING_REMARKS";
    public static final String BROODER_FEEDING_COL_6 = "BROODER_FEEDING_DELETED_AT";*/
    public Cursor getIDFromBrooderFeedingRecordsWhere(Integer inventory_id, String date, Float offered, Float refused){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " +TABLE_BROODER_FEEDING_RECORDS+ " where BROODER_FEEDING_INVENTORY_ID is ? and BROODER_FEEDING_DATE_COLLECTED like ? and BROODER_FEEDING_AMOUNT_OFFERED like ? and BROODER_FEEDING_AMOUNT_REFUSED like ? ",new String[]{inventory_id.toString(), date, offered.toString(), refused.toString()});/* inventory_id, String date, Float offered, Float refused, String remarks, String deleted_at){*/

        return res;
    }
    public Cursor getAllDataFromBrooderGrowthRecordsWhereGrowthID(Integer id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " +TABLE_BROODER_GROWTH_RECORDS+ " where ID is ?",new String[]{id.toString()});

        return res;
    }
    public Cursor getAllDataFromBrooderGrowthRecords(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " +TABLE_BROODER_GROWTH_RECORDS, null);

        return res;
    }
    public Cursor getTagFromBrooderInventory(Integer inventory_id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select BROODER_INV_BROODER_TAG from " +TABLE_BROODER_INVENTORIES+ " where ID is ?",new String[]{inventory_id.toString()});

        return res;
    }

    public Integer getFamIDFromBrooders(Integer id){
        Integer fam_id=0;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select BROODER_FAMILY from " +TABLE_BROODER+ " where ID is ?",new String[]{id.toString()});
        res.moveToFirst();
        if(res.getCount() != 0){
            fam_id = res.getInt(0);
        }
        return fam_id;
    }
    public Integer getFamIDFromReplacements(Integer id){
        Integer fam_id = null;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select REPLACEMENT_FAMILY from " +TABLE_REPLACEMENT+ " where ID is ?",new String[]{id.toString()});
        res.moveToFirst();
        if(res.getCount() != 0){
            fam_id = res.getInt(0);
        }
        return fam_id;
    }
    public Integer getFamIDFromBreeders(Integer id){
        Integer fam_id = null;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select BREEDER_FAMILY from " +TABLE_BREEDER+ " where ID is ?",new String[]{id.toString()});
        res.moveToFirst();
        if(res.getCount() != 0){
            fam_id = res.getInt(0);
        }
        return fam_id;
    }






















    public Cursor getIDFromReplacementsWhere(Integer family){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " +TABLE_REPLACEMENT+ " where REPLACEMENT_FAMILY is ?", new String[]{family.toString()},null);
        return res;
    }

    public Cursor getReplacementsFromPen(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " +TABLE_PEN+ " where PEN_TYPE LIKE '%Grower%'", null);
        return res;
    }
    public Cursor getAllDataFromReplacements(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " +TABLE_REPLACEMENT, null);

        return res;
    }

    public Cursor getAllDataFromReplacementsWhereID(Integer id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " +TABLE_REPLACEMENT+ " where ID is ?", new String[]{id.toString()});

        return res;
    }

    public Cursor getDataFromReplacementInventoryWherePenAndID(String tag, Integer pen){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " +TABLE_REPLACEMENT_INVENTORIES + " where REPLACEMENT_INV_REPLACEMENT_TAG is ? and REPLACEMENT_INV_PEN_NUMBER is ?", new String[] {tag, pen.toString()});

        return res;
    }
    public Cursor getDataFromReplacementInventoryWherePen( Integer pen){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " +TABLE_REPLACEMENT_INVENTORIES + " where REPLACEMENT_INV_PEN_NUMBER is ?", new String[] { pen.toString()});

        return res;
    }
    public Cursor getAllDataFromReplacementInventoryWhereID(Integer id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " +TABLE_REPLACEMENT_INVENTORIES+ " where ID is ?", new String[]{id.toString()});

        return res;
    }
    public Cursor getAllDataFromReplacementGrowthRecordsWhereGrowthID(Integer id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " +TABLE_REPLACEMENT_GROWTH_RECORDS+ " where ID is ?",new String[]{id.toString()});

        return res;
    }
    public Cursor getAllDataFromReplacementInventory(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " +TABLE_REPLACEMENT_INVENTORIES, null);

        return res;
    }
    public Cursor getDataFromReplacementInventoryWhereTag(String tag){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " +TABLE_REPLACEMENT_INVENTORIES + " where REPLACEMENT_INV_REPLACEMENT_TAG is ?", new String[]{tag});

        return res;
    }
    public Cursor getAllDataFromReplacementFeedingRecords(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " +TABLE_REPLACEMENT_FEEDING_RECORDS, null);

        return res;
    }

    public Cursor getAllDataFromReplacementPhenoMorphoRecords(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " +TABLE_REPLACEMENT_PHENOTYPIC_AND_MORPHOMETRIC_RECORDS, null);

        return res;
    }

    public Cursor getAllDataFromReplacementGrowthRecords(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " +TABLE_REPLACEMENT_GROWTH_RECORDS, null);

        return res;
    }
    public Cursor getAllDataFromPhenoMorphoRecords(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " +TABLE_PHENO_MORPHO_VALUES, null);

        return res;
    }
    public Cursor getAllDataFromPhenoMorphoRecordsWithID(Integer id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " +TABLE_PHENO_MORPHO_VALUES+ " where id is ?", new String[]{id.toString()});

        return res;
    }
    public Cursor getDataFromPhenoMorphoValuesWhere(String sex, String tag, String pheno, String morphos, String date){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " +TABLE_PHENO_MORPHO_VALUES+ " where gender is ? and tag is ? and phenotypic is ? and morphometric is ? and date_collected is ?", new String[]{sex, tag, pheno, morphos, date});

        return res;
    }
    public Cursor getDataFromReplacementPhenoMorphosWhere(Integer inv_id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select values_id from " +TABLE_PHENO_MORPHOS+" where replacement_inventory is ?", new String[]{inv_id.toString()});

        return res;
    }
    public Cursor getDataFromReplacementPhenoMorphosWhereID(Integer id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select values_id from " +TABLE_PHENO_MORPHOS+" where id is ?", new String[]{id.toString()});

        return res;
    }
    public Cursor getIDFromReplacementInventoyWhereTag(String tag){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select ID from " +TABLE_REPLACEMENT_INVENTORIES+" where REPLACEMENT_INV_REPLACEMENT_TAG is ?", new String[]{tag});

        return res;
    }
    public Cursor getDataFromPhenoMorphoValuesWhereValuesID(Integer id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " +TABLE_PHENO_MORPHO_VALUES+" where id is ?", new String[]{id.toString()});

        return res;
    }
    public Cursor getAllDataFromReplacementFeedingRecordsWhereFeedingID(Integer id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select REPLACEMENT_FEEDING_AMOUNT_OFFERED,REPLACEMENT_FEEDING_AMOUNT_REFUSED,REPLACEMENT_FEEDING_REMARKS,REPLACEMENT_FEEDING_DATE_COLLECTED,ID,REPLACEMENT_FEEDING_INVENTORY_ID from " +TABLE_REPLACEMENT_FEEDING_RECORDS+ " where ID is ?",new String[]{id.toString()});

        return res;
    }


    public Cursor getAllDataFromPhenoMorphos(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " +TABLE_PHENO_MORPHOS, null);

        return res;
    }
    public Cursor getAllDataFromPhenoMorphosWhereID(Integer id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " +TABLE_PHENO_MORPHOS+ " where id is ?", new String[]{id.toString()});

        return res;
    }



    public Cursor getAllDataFromPhenoMorphoRecordsWhereID(Integer id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " +TABLE_PHENO_MORPHO_VALUES+ " where id is ?", new String[]{id.toString()});

        return res;
    }










//functions for dashboard


    public Integer getAllMaleFromBreeders(Integer farm_id){
        SQLiteDatabase db = this.getWritableDatabase();
        Integer male_count=0;
        ArrayList<Integer> pens = new ArrayList<>();


        Cursor res0 = db.rawQuery("SELECT ID FROM " +TABLE_PEN+ " WHERE farm_id is ?", new String[]{farm_id.toString()});
        res0.moveToFirst();
        if(res0.getCount() != 0){
            do{
                Integer a = res0.getInt(0);
                pens.add(a);
            }while(res0.moveToNext());
        }


        Cursor res = db.rawQuery("SELECT BREEDER_INV_NUMBER_MALE, BREEDER_INV_PEN_NUMBER FROM " +TABLE_BREEDER_INVENTORIES, null);
        res.moveToFirst();

        if(res.getCount() != 0 ){
            do{

                Integer pen_id = res.getInt(1);
                if(pens.contains(pen_id)){
                    male_count = male_count + res.getInt(0);
                }

            }while (res.moveToNext());
        }

        return male_count;
    }
    public Integer getAllFemaleFromBreeders(Integer farm_id){
        SQLiteDatabase db = this.getWritableDatabase();
        Integer male_count=0;

        ArrayList<Integer> pens = new ArrayList<>();


        Cursor res0 = db.rawQuery("SELECT ID FROM " +TABLE_PEN+ " WHERE farm_id is ?", new String[]{farm_id.toString()});
        res0.moveToFirst();
        if(res0.getCount() != 0){
            do{
                Integer a = res0.getInt(0);
                pens.add(a);
            }while(res0.moveToNext());
        }

        Cursor res = db.rawQuery("SELECT BREEDER_INV_NUMBER_FEMALE,BREEDER_INV_PEN_NUMBER FROM " +TABLE_BREEDER_INVENTORIES, null);
        res.moveToFirst();
        if(res.getCount() != 0 ){
            do{

                Integer pen_id = res.getInt(1);
                if(pens.contains(pen_id)){
                    male_count = male_count + res.getInt(0);
                }

            }while (res.moveToNext());
        }
        return male_count;
    }

    public Integer getAllBreederMaleFromMort(Integer farm_id){
        SQLiteDatabase db = this.getWritableDatabase();
        Integer male_count=0;

        ArrayList<Integer> pens = new ArrayList<>();
        ArrayList<Integer> breeders = new ArrayList<>();


        Cursor res0 = db.rawQuery("SELECT ID FROM " +TABLE_PEN+ " WHERE farm_id is ?", new String[]{farm_id.toString()});
        res0.moveToFirst();
        if(res0.getCount() != 0){
            do{
                Integer a = res0.getInt(0);
                pens.add(a);
            }while(res0.moveToNext());
        }

        Cursor res1 = db.rawQuery("SELECT ID FROM " +TABLE_BREEDER_INVENTORIES, null);
        res1.moveToFirst();
        if(res1.getCount() != 0){
            do{

                Integer a = res1.getInt(0);
                if(pens.contains(a)){
                    breeders.add(a);
                }

            }while (res1.moveToNext());
        }


        String type = "breeder";
        String category = "died";
        Cursor res = db.rawQuery("SELECT MORT_AND_SALES_MALE, ID FROM " +TABLE_MORTALITY_AND_SALES+ " WHERE MORT_AND_SALES_CATEGORY LIKE ? AND MORT_AND_SALES_TYPE LIKE ?", new String[]{category, type});
        res.moveToFirst();
        if(res.getCount() != 0){
            do{
                Integer a= res.getInt(1);
                if(breeders.contains(a)){
                    male_count = male_count + res.getInt(0);
                }

            }while (res.moveToNext());
        }

        return male_count;
    }

    public Integer getAllBreedeFemaleFromMort(Integer farm_id){
        SQLiteDatabase db = this.getWritableDatabase();
        Integer male_count=0;

        ArrayList<Integer> pens = new ArrayList<>();
        ArrayList<Integer> breeders = new ArrayList<>();


        Cursor res0 = db.rawQuery("SELECT ID FROM " +TABLE_PEN+ " WHERE farm_id is ?", new String[]{farm_id.toString()});
        res0.moveToFirst();
        if(res0.getCount() != 0){
            do{
                Integer a = res0.getInt(0);
                pens.add(a);
            }while(res0.moveToNext());
        }

        Cursor res1 = db.rawQuery("SELECT ID FROM " +TABLE_BREEDER_INVENTORIES, null);
        res1.moveToFirst();
        if(res1.getCount() != 0){
            do{

                Integer a = res1.getInt(0);
                if(pens.contains(a)){
                    breeders.add(a);
                }

            }while (res1.moveToNext());
        }


        String type = "breeder";
        String category = "died";
        Cursor res = db.rawQuery("SELECT MORT_AND_SALES_FEMALE, ID FROM " +TABLE_MORTALITY_AND_SALES+ " WHERE MORT_AND_SALES_CATEGORY LIKE ? AND MORT_AND_SALES_TYPE LIKE ?", new String[]{category, type});
        res.moveToFirst();
        if(res.getCount() != 0){
            do{
                Integer a= res.getInt(1);
                if(breeders.contains(a)){
                    male_count = male_count + res.getInt(0);
                }

            }while (res.moveToNext());
        }

        return male_count;
    }


    public Integer getAllBreederMaleFromSales(Integer farm_id){

        SQLiteDatabase db = this.getWritableDatabase();
        Integer male_count=0;

        ArrayList<Integer> pens = new ArrayList<>();
        ArrayList<Integer> breeders = new ArrayList<>();


        Cursor res0 = db.rawQuery("SELECT ID FROM " +TABLE_PEN+ " WHERE farm_id is ?", new String[]{farm_id.toString()});
        res0.moveToFirst();
        if(res0.getCount() != 0){
            do{
                Integer a = res0.getInt(0);
                pens.add(a);
            }while(res0.moveToNext());
        }

        Cursor res1 = db.rawQuery("SELECT ID FROM " +TABLE_BREEDER_INVENTORIES, null);
        res1.moveToFirst();
        if(res1.getCount() != 0){
            do{

                Integer a = res1.getInt(0);
                if(pens.contains(a)){
                    breeders.add(a);
                }

            }while (res1.moveToNext());
        }


        String type = "breeder";
        String category = "sold";
        Cursor res = db.rawQuery("SELECT MORT_AND_SALES_MALE, ID FROM " +TABLE_MORTALITY_AND_SALES+ " WHERE MORT_AND_SALES_CATEGORY LIKE ? AND MORT_AND_SALES_TYPE LIKE ?", new String[]{category, type});
        res.moveToFirst();
        if(res.getCount() != 0){
            do{
                Integer a= res.getInt(1);
                if(breeders.contains(a)){
                    male_count = male_count + res.getInt(0);
                }

            }while (res.moveToNext());
        }

        return male_count;


    }

    public Integer getAllBreedeFemaleFromSales(Integer farm_id){
        SQLiteDatabase db = this.getWritableDatabase();
        Integer male_count=0;

        ArrayList<Integer> pens = new ArrayList<>();
        ArrayList<Integer> breeders = new ArrayList<>();


        Cursor res0 = db.rawQuery("SELECT ID FROM " +TABLE_PEN+ " WHERE farm_id is ?", new String[]{farm_id.toString()});
        res0.moveToFirst();
        if(res0.getCount() != 0){
            do{
                Integer a = res0.getInt(0);
                pens.add(a);
            }while(res0.moveToNext());
        }

        Cursor res1 = db.rawQuery("SELECT ID FROM " +TABLE_BREEDER_INVENTORIES, null);
        res1.moveToFirst();
        if(res1.getCount() != 0){
            do{

                Integer a = res1.getInt(0);
                if(pens.contains(a)){
                    breeders.add(a);
                }

            }while (res1.moveToNext());
        }


        String type = "breeder";
        String category = "sold";
        Cursor res = db.rawQuery("SELECT MORT_AND_SALES_FEMALE, ID FROM " +TABLE_MORTALITY_AND_SALES+ " WHERE MORT_AND_SALES_CATEGORY LIKE ? AND MORT_AND_SALES_TYPE LIKE ?", new String[]{category, type});
        res.moveToFirst();
        if(res.getCount() != 0){
            do{
                Integer a= res.getInt(1);
                if(breeders.contains(a)){
                    male_count = male_count + res.getInt(0);
                }

            }while (res.moveToNext());
        }

        return male_count;


    }


    public Integer getAllEggSales(Integer farm_id){
        SQLiteDatabase db = this.getWritableDatabase();
        Integer male_count=0;

        ArrayList<Integer> pens = new ArrayList<>();
        ArrayList<Integer> breeders = new ArrayList<>();


        Cursor res0 = db.rawQuery("SELECT ID FROM " +TABLE_PEN+ " WHERE farm_id is ?", new String[]{farm_id.toString()});
        res0.moveToFirst();
        if(res0.getCount() != 0){
            do{
                Integer a = res0.getInt(0);
                pens.add(a);
            }while(res0.moveToNext());
        }

        Cursor breeder_cursor = db.rawQuery("SELECT ID FROM " +TABLE_BREEDER_INVENTORIES, null);
        breeder_cursor.moveToFirst();
        if(breeder_cursor.getCount() != 0){
            do{

                Integer b = breeder_cursor.getInt(0);
                if(pens.contains(b)){
                    breeders.add(b);
                }

            }while (breeder_cursor.moveToNext());
        }



        String type = "egg";

        Cursor res = db.rawQuery("SELECT MORT_AND_SALES_TOTAL, MORT_AND_SALES_BREEDER_INV_ID FROM " +TABLE_MORTALITY_AND_SALES+ " WHERE MORT_AND_SALES_TYPE LIKE ?", new String[]{ type});
        res.moveToFirst();
        if(res.getCount() != 0){
            do{
                Integer a= res.getInt(1);
                if(breeders.contains(a)){
                    male_count = male_count + res.getInt(0);
                }

            }while (res.moveToNext());
        }

        return male_count;


    }


    public Integer getBreederFeedingOffered(){
        SQLiteDatabase db = this.getWritableDatabase();
        Integer male_count=0;


        Cursor res = db.rawQuery("SELECT BREEDER_FEEDING_AMOUNT_OFFERED FROM " +TABLE_BREEDER_FEEDING_RECORDS,null);
        res.moveToFirst();
        if(res.getCount() != 0){
            do{
                male_count = male_count + res.getInt(0);
            }while (res.moveToNext());
        }

        return male_count;
    }


    public Integer getBreederFeedingRefused(){
        SQLiteDatabase db = this.getWritableDatabase();
        Integer male_count=0;


        Cursor res = db.rawQuery("SELECT BREEDER_FEEDING_AMOUNT_REFUSED FROM " +TABLE_BREEDER_FEEDING_RECORDS,null);
        res.moveToFirst();
        if(res.getCount() != 0){
            do{
                male_count = male_count + res.getInt(0);
            }while (res.moveToNext());
        }

        return male_count;
    }


    public Integer getTotalIntact(Integer farm_id){
        SQLiteDatabase db = this.getWritableDatabase();
        Integer male_count=0;

        ArrayList<Integer> pens = new ArrayList<>();
        ArrayList<Integer> breeders = new ArrayList<>();


        Cursor res0 = db.rawQuery("SELECT ID FROM " +TABLE_PEN+ " WHERE farm_id is ?", new String[]{farm_id.toString()});
        res0.moveToFirst();
        if(res0.getCount() != 0){
            do{
                Integer a = res0.getInt(0);
                pens.add(a);
            }while(res0.moveToNext());
        }

        Cursor res1 = db.rawQuery("SELECT ID FROM " +TABLE_BREEDER_INVENTORIES, null);
        res1.moveToFirst();
        if(res1.getCount() != 0){
            do{

                Integer a = res1.getInt(0);
                if(pens.contains(a)){
                    breeders.add(a);
                }

            }while (res1.moveToNext());
        }




        Cursor res = db.rawQuery("SELECT EGG_PRODUCTION_EGGS_INTACT, EGG_PRODUCTION_BREEDER_INVENTORY_ID FROM " +TABLE_EGG_PRODUCTION, null);
        res.moveToFirst();
        if(res.getCount() != 0){
            do{
                Integer a= res.getInt(1);
                if(breeders.contains(a)){
                    male_count = male_count + res.getInt(0);
                }

            }while (res.moveToNext());
        }

        return male_count;

    }


    public Integer getTotalWeight(Integer farm_id){
        SQLiteDatabase db = this.getWritableDatabase();
        Integer male_count=0;

        ArrayList<Integer> pens = new ArrayList<>();
        ArrayList<Integer> breeders = new ArrayList<>();


        Cursor res0 = db.rawQuery("SELECT ID FROM " +TABLE_PEN+ " WHERE farm_id is ?", new String[]{farm_id.toString()});
        res0.moveToFirst();
        if(res0.getCount() != 0){
            do{
                Integer a = res0.getInt(0);
                pens.add(a);
            }while(res0.moveToNext());
        }

        Cursor res1 = db.rawQuery("SELECT ID FROM " +TABLE_BREEDER_INVENTORIES, null);
        res1.moveToFirst();
        if(res1.getCount() != 0){
            do{

                Integer a = res1.getInt(0);
                if(pens.contains(a)){
                    breeders.add(a);
                }

            }while (res1.moveToNext());
        }




        Cursor res = db.rawQuery("SELECT EGG_PRODUCTION_EGG_WEIGHT, EGG_PRODUCTION_BREEDER_INVENTORY_ID FROM " +TABLE_EGG_PRODUCTION, null);
        res.moveToFirst();
        if(res.getCount() != 0){
            do{
                Integer a= res.getInt(1);
                if(breeders.contains(a)){
                    male_count = male_count + res.getInt(0);
                }

            }while (res.moveToNext());
        }

        return male_count;

    }



    public Integer getTotalBroken(Integer farm_id){
        SQLiteDatabase db = this.getWritableDatabase();
        Integer male_count=0;

        ArrayList<Integer> pens = new ArrayList<>();
        ArrayList<Integer> breeders = new ArrayList<>();


        Cursor res0 = db.rawQuery("SELECT ID FROM " +TABLE_PEN+ " WHERE farm_id is ?", new String[]{farm_id.toString()});
        res0.moveToFirst();
        if(res0.getCount() != 0){
            do{
                Integer a = res0.getInt(0);
                pens.add(a);
            }while(res0.moveToNext());
        }

        Cursor res1 = db.rawQuery("SELECT ID FROM " +TABLE_BREEDER_INVENTORIES, null);
        res1.moveToFirst();
        if(res1.getCount() != 0){
            do{

                Integer a = res1.getInt(0);
                if(pens.contains(a)){
                    breeders.add(a);
                }

            }while (res1.moveToNext());
        }




        Cursor res = db.rawQuery("SELECT EGG_PRODUCTION_TOTAL_BROKEN, EGG_PRODUCTION_BREEDER_INVENTORY_ID FROM " +TABLE_EGG_PRODUCTION, null);
        res.moveToFirst();
        if(res.getCount() != 0){
            do{
                Integer a= res.getInt(1);
                if(breeders.contains(a)){
                    male_count = male_count + res.getInt(0);
                }

            }while (res.moveToNext());
        }

        return male_count;

    }




    public Integer getTotalRejects(Integer farm_id){
        SQLiteDatabase db = this.getWritableDatabase();
        Integer male_count=0;

        ArrayList<Integer> pens = new ArrayList<>();
        ArrayList<Integer> breeders = new ArrayList<>();


        Cursor res0 = db.rawQuery("SELECT ID FROM " +TABLE_PEN+ " WHERE farm_id is ?", new String[]{farm_id.toString()});
        res0.moveToFirst();
        if(res0.getCount() != 0){
            do{
                Integer a = res0.getInt(0);
                pens.add(a);
            }while(res0.moveToNext());
        }

        Cursor res1 = db.rawQuery("SELECT ID FROM " +TABLE_BREEDER_INVENTORIES, null);
        res1.moveToFirst();
        if(res1.getCount() != 0){
            do{

                Integer a = res1.getInt(0);
                if(pens.contains(a)){
                    breeders.add(a);
                }

            }while (res1.moveToNext());
        }




        Cursor res = db.rawQuery("SELECT EGG_PRODUCTION_TOTAL_REJECTS, EGG_PRODUCTION_BREEDER_INVENTORY_ID FROM " +TABLE_EGG_PRODUCTION, null);
        res.moveToFirst();
        if(res.getCount() != 0){
            do{
                Integer a= res.getInt(1);
                if(breeders.contains(a)){
                    male_count = male_count + res.getInt(0);
                }

            }while (res.moveToNext());
        }

        return male_count;

    }
    public Float getHenDayEggProduction(Integer farm_id){
        SQLiteDatabase db = this.getWritableDatabase();
        DatabaseHelper myDb;



        Integer male_count=0;
        Float hen_day = 0f;

        ArrayList<Integer> pens = new ArrayList<>();
        ArrayList<Integer> breeders = new ArrayList<>();


        Cursor res0 = db.rawQuery("SELECT ID FROM " +TABLE_PEN+ " WHERE farm_id is ?", new String[]{farm_id.toString()});
        res0.moveToFirst();
        if(res0.getCount() != 0){
            do{
                Integer a = res0.getInt(0);
                pens.add(a);
            }while(res0.moveToNext());
        }

        Cursor res1 = db.rawQuery("SELECT ID FROM " +TABLE_BREEDER_INVENTORIES, null);
        res1.moveToFirst();
        if(res1.getCount() != 0){
            do{

                Integer a = res1.getInt(0);
                if(pens.contains(a)){
                    breeders.add(a);
                }

            }while (res1.moveToNext());
        }



        DateFormat dateFormat = new SimpleDateFormat("MM");
        Date date = new Date();
        String month = dateFormat.format(date);

        Calendar cal = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();

        int month2 = cal.get(Calendar.MONTH);
        cal2.add(month2, -1);

        Integer month3 = cal2.get(Calendar.MONTH);


        DateFormat dateFormat2 = new SimpleDateFormat("YYYY");
        Date date2 = new Date();
        String year = dateFormat2.format(date2);

        String monthFinal = String.format("%02d" , month3);

        Cursor res = db.rawQuery("SELECT EGG_PRODUCTION_EGGS_INTACT, EGG_PRODUCTION_BREEDER_INVENTORY_ID FROM " +TABLE_EGG_PRODUCTION+ " WHERE strftime('%m', EGG_PRODUCTION_DATE) LIKE ? AND strftime('%Y', EGG_PRODUCTION_DATE) = ?", new String[]{monthFinal,year });
        res.moveToFirst();
        if(res.getCount() != 0){
            do{
                Integer a= res.getInt(1);
                if(breeders.contains(a)){
                    male_count = male_count + res.getInt(0);
                }

            }while (res.moveToNext());
        }


        ////

        Float female_count=0f;


        Cursor res4 = db.rawQuery("SELECT BREEDER_INV_NUMBER_FEMALE,BREEDER_INV_PEN_NUMBER FROM " +TABLE_BREEDER_INVENTORIES+ " WHERE strftime('%m', BREEDER_INV_LAST_UPDATE) LIKE ? AND strftime('%Y', BREEDER_INV_LAST_UPDATE) = ?", new String[]{monthFinal,year });

        res4.moveToFirst();
        if(res4.getCount() != 0 ){
            do{

                Integer pen_id = res4.getInt(1);
                if(pens.contains(pen_id)){
                    female_count = female_count + res4.getInt(0);
                }

            }while (res4.moveToNext());
        }

        hen_day = (male_count/female_count)*100;
        return hen_day;

    }

    public Float getFertilityPercentage(Integer farm_id){
        SQLiteDatabase db = this.getWritableDatabase();
        Integer male_count=0;
        Float eggs_set=0f;
        Float eggs_fertile=0f;
        Float fertility=0f;

        ArrayList<Integer> pens = new ArrayList<>();
        ArrayList<Integer> breeders = new ArrayList<>();


        Cursor res0 = db.rawQuery("SELECT ID FROM " +TABLE_PEN+ " WHERE farm_id is ?", new String[]{farm_id.toString()});
        res0.moveToFirst();
        if(res0.getCount() != 0){
            do{
                Integer a = res0.getInt(0);
                pens.add(a);
            }while(res0.moveToNext());
        }

        Cursor res1 = db.rawQuery("SELECT ID FROM " +TABLE_BREEDER_INVENTORIES, null);
        res1.moveToFirst();
        if(res1.getCount() != 0){
            do{

                Integer a = res1.getInt(0);
                if(pens.contains(a)){
                    breeders.add(a);
                }

            }while (res1.moveToNext());
        }




        Cursor res = db.rawQuery("SELECT HATCHERY_EGGS_SET, HATCHERY_FERTILE, HATCHERY_BREEDER_INVENTORY_ID FROM " +TABLE_HATCHERY_RECORD, null);
        res.moveToFirst();
        if(res.getCount() != 0){
            do{
                Integer a= res.getInt(2);
                if(breeders.contains(a)){
                     eggs_set = eggs_set + res.getInt(0);
                     eggs_fertile = eggs_fertile + res.getInt(1);
                }

            }while (res.moveToNext());
        }

        fertility = (eggs_fertile/eggs_set)*100;
        return fertility;
/*        SQLiteDatabase db = this.getWritableDatabase();
        Float eggs_set=0f;
        Float eggs_fertile=0f;
        Float fertility=0f;


        Cursor res = db.rawQuery("SELECT HATCHERY_EGGS_SET, HATCHERY_FERTILE FROM " +TABLE_HATCHERY_RECORD,null);
        res.moveToFirst();
        if(res.getCount() != 0){
            do{
                eggs_set = eggs_set + res.getInt(0);
                eggs_fertile = eggs_fertile + res.getInt(1);
            }while (res.moveToNext());
        }

        fertility = (eggs_fertile/eggs_set)*100;
        return fertility;*/
    }

    public Float getHatchabilityPercentage(Integer farm_id){
        SQLiteDatabase db = this.getWritableDatabase();
        Integer male_count=0;
        Float eggs_set=0f;
        Float eggs_fertile=0f;
        Float fertility=0f;

        ArrayList<Integer> pens = new ArrayList<>();
        ArrayList<Integer> breeders = new ArrayList<>();


        Cursor res0 = db.rawQuery("SELECT ID FROM " +TABLE_PEN+ " WHERE farm_id is ?", new String[]{farm_id.toString()});
        res0.moveToFirst();
        if(res0.getCount() != 0){
            do{
                Integer a = res0.getInt(0);
                pens.add(a);
            }while(res0.moveToNext());
        }

        Cursor res1 = db.rawQuery("SELECT ID FROM " +TABLE_BREEDER_INVENTORIES, null);
        res1.moveToFirst();
        if(res1.getCount() != 0){
            do{

                Integer a = res1.getInt(0);
                if(pens.contains(a)){
                    breeders.add(a);
                }

            }while (res1.moveToNext());
        }




        Cursor res = db.rawQuery("SELECT HATCHERY_EGGS_SET, HATCHERY_HATCHED, HATCHERY_BREEDER_INVENTORY_ID FROM " +TABLE_HATCHERY_RECORD, null);
        res.moveToFirst();
        if(res.getCount() != 0){
            do{
                Integer a= res.getInt(2);
                if(breeders.contains(a)){
                     eggs_set = eggs_set + res.getInt(0);
                     eggs_fertile = eggs_fertile + res.getInt(1);
                }

            }while (res.moveToNext());
        }

        fertility = (eggs_fertile/eggs_set)*100;
        return fertility;

    }



////////////////////////////////replacement
public Integer getAllMaleFromReplacements(Integer farm_id){
    SQLiteDatabase db = this.getWritableDatabase();
    Integer male_count=0;

    ArrayList<Integer> pens = new ArrayList<>();

    Cursor res0 = db.rawQuery("SELECT ID FROM " +TABLE_PEN+ " WHERE farm_id is ?", new String[]{farm_id.toString()});
    res0.moveToFirst();
    if(res0.getCount() != 0){
        do{
            Integer a = res0.getInt(0);
            pens.add(a);
        }while(res0.moveToNext());
    }
    Cursor res = db.rawQuery("SELECT REPLACEMENT_INV_NUMBER_MALE, REPLACEMENT_INV_PEN_NUMBER FROM " +TABLE_REPLACEMENT_INVENTORIES, null);
    res.moveToFirst();
    if(res.getCount() != 0){
        do{
            Integer pen_id = res.getInt(1);
            if(pens.contains(pen_id)){
                male_count = male_count + res.getInt(0);
            }
        }while (res.moveToNext());
    }

    return male_count;
}
    public Integer getAllFemaleFromReplacements(Integer farm_id){
        SQLiteDatabase db = this.getWritableDatabase();
        Integer male_count=0;
        ArrayList<Integer> pens = new ArrayList<>();

        Cursor res0 = db.rawQuery("SELECT ID FROM " +TABLE_PEN+ " WHERE farm_id is ?", new String[]{farm_id.toString()});
        res0.moveToFirst();
        if(res0.getCount() != 0){
            do{
                Integer a = res0.getInt(0);
                pens.add(a);
            }while(res0.moveToNext());
        }
        Cursor res = db.rawQuery("SELECT REPLACEMENT_INV_NUMBER_FEMALE, REPLACEMENT_INV_PEN_NUMBER FROM " +TABLE_REPLACEMENT_INVENTORIES, null);
        res.moveToFirst();
        if(res.getCount() != 0){
            do{
                Integer pen_id = res.getInt(1);
                if(pens.contains(pen_id)){
                    male_count = male_count + res.getInt(0);
                }
            }while (res.moveToNext());
        }
        return male_count;
    }


    public Integer getAllReplacementMaleFromMort(Integer farm_id){
        SQLiteDatabase db = this.getWritableDatabase();
        Integer male_count=0;

        ArrayList<Integer> pens = new ArrayList<>();
        ArrayList<Integer> breeders = new ArrayList<>();


        Cursor res0 = db.rawQuery("SELECT ID FROM " +TABLE_PEN+ " WHERE farm_id is ?", new String[]{farm_id.toString()});
        res0.moveToFirst();
        if(res0.getCount() != 0){
            do{
                Integer a = res0.getInt(0);
                pens.add(a);
            }while(res0.moveToNext());
        }

        Cursor res1 = db.rawQuery("SELECT ID FROM " +TABLE_REPLACEMENT_INVENTORIES, null);
        res1.moveToFirst();
        if(res1.getCount() != 0){
            do{

                Integer a = res1.getInt(0);
                if(pens.contains(a)){
                    breeders.add(a);
                }

            }while (res1.moveToNext());
        }


        String type = "replacement";
        String category = "died";
        Cursor res = db.rawQuery("SELECT MORT_AND_SALES_MALE, ID FROM " +TABLE_MORTALITY_AND_SALES+ " WHERE MORT_AND_SALES_CATEGORY LIKE ? AND MORT_AND_SALES_TYPE LIKE ?", new String[]{category, type});
        res.moveToFirst();
        if(res.getCount() != 0){
            do{
                Integer a= res.getInt(1);
                if(breeders.contains(a)){
                    male_count = male_count + res.getInt(0);
                }

            }while (res.moveToNext());
        }

        return male_count;
    }

    public Integer getAllReplacementFemaleFromMort(Integer farm_id){
        SQLiteDatabase db = this.getWritableDatabase();
        Integer male_count=0;

        ArrayList<Integer> pens = new ArrayList<>();
        ArrayList<Integer> breeders = new ArrayList<>();


        Cursor res0 = db.rawQuery("SELECT ID FROM " +TABLE_PEN+ " WHERE farm_id is ?", new String[]{farm_id.toString()});
        res0.moveToFirst();
        if(res0.getCount() != 0){
            do{
                Integer a = res0.getInt(0);
                pens.add(a);
            }while(res0.moveToNext());
        }

        Cursor res1 = db.rawQuery("SELECT ID FROM " +TABLE_REPLACEMENT_INVENTORIES, null);
        res1.moveToFirst();
        if(res1.getCount() != 0){
            do{

                Integer a = res1.getInt(0);
                if(pens.contains(a)){
                    breeders.add(a);
                }

            }while (res1.moveToNext());
        }


        String type = "replacement";
        String category = "died";
        Cursor res = db.rawQuery("SELECT MORT_AND_SALES_FEMALE, ID FROM " +TABLE_MORTALITY_AND_SALES+ " WHERE MORT_AND_SALES_CATEGORY LIKE ? AND MORT_AND_SALES_TYPE LIKE ?", new String[]{category, type});
        res.moveToFirst();
        if(res.getCount() != 0){
            do{
                Integer a= res.getInt(1);
                if(breeders.contains(a)){
                    male_count = male_count + res.getInt(0);
                }

            }while (res.moveToNext());
        }

        return male_count;

    }


    public Integer getAllReplacementMaleFromSales(Integer farm_id){
        SQLiteDatabase db = this.getWritableDatabase();
        Integer male_count=0;

        ArrayList<Integer> pens = new ArrayList<>();
        ArrayList<Integer> breeders = new ArrayList<>();


        Cursor res0 = db.rawQuery("SELECT ID FROM " +TABLE_PEN+ " WHERE farm_id is ?", new String[]{farm_id.toString()});
        res0.moveToFirst();
        if(res0.getCount() != 0){
            do{
                Integer a = res0.getInt(0);
                pens.add(a);
            }while(res0.moveToNext());
        }

        Cursor res1 = db.rawQuery("SELECT ID FROM " +TABLE_REPLACEMENT_INVENTORIES, null);
        res1.moveToFirst();
        if(res1.getCount() != 0){
            do{

                Integer a = res1.getInt(0);
                if(pens.contains(a)){
                    breeders.add(a);
                }

            }while (res1.moveToNext());
        }


        String type = "replacement";
        String category = "sold";
        Cursor res = db.rawQuery("SELECT MORT_AND_SALES_MALE, ID FROM " +TABLE_MORTALITY_AND_SALES+ " WHERE MORT_AND_SALES_CATEGORY LIKE ? AND MORT_AND_SALES_TYPE LIKE ?", new String[]{category, type});
        res.moveToFirst();
        if(res.getCount() != 0){
            do{
                Integer a= res.getInt(1);
                if(breeders.contains(a)){
                    male_count = male_count + res.getInt(0);
                }

            }while (res.moveToNext());
        }

        return male_count;

    }

    public Integer getAllReplacementFemaleFromSales(Integer farm_id){
        SQLiteDatabase db = this.getWritableDatabase();
        Integer male_count=0;

        ArrayList<Integer> pens = new ArrayList<>();
        ArrayList<Integer> breeders = new ArrayList<>();


        Cursor res0 = db.rawQuery("SELECT ID FROM " +TABLE_PEN+ " WHERE farm_id is ?", new String[]{farm_id.toString()});
        res0.moveToFirst();
        if(res0.getCount() != 0){
            do{
                Integer a = res0.getInt(0);
                pens.add(a);
            }while(res0.moveToNext());
        }

        Cursor res1 = db.rawQuery("SELECT ID FROM " +TABLE_REPLACEMENT_INVENTORIES, null);
        res1.moveToFirst();
        if(res1.getCount() != 0){
            do{

                Integer a = res1.getInt(0);
                if(pens.contains(a)){
                    breeders.add(a);
                }

            }while (res1.moveToNext());
        }


        String type = "replacement";
        String category = "sold";
        Cursor res = db.rawQuery("SELECT MORT_AND_SALES_FEMALE, ID FROM " +TABLE_MORTALITY_AND_SALES+ " WHERE MORT_AND_SALES_CATEGORY LIKE ? AND MORT_AND_SALES_TYPE LIKE ?", new String[]{category, type});
        res.moveToFirst();
        if(res.getCount() != 0){
            do{
                Integer a= res.getInt(1);
                if(breeders.contains(a)){
                    male_count = male_count + res.getInt(0);
                }

            }while (res.moveToNext());
        }

        return male_count;


    }

    public Integer getReplacementFeedingOffered(){
        SQLiteDatabase db = this.getWritableDatabase();
        Integer male_count=0;


        Cursor res = db.rawQuery("SELECT REPLACEMENT_FEEDING_AMOUNT_OFFERED FROM " +TABLE_REPLACEMENT_FEEDING_RECORDS,null);
        res.moveToFirst();
        if(res.getCount() != 0){
            do{
                male_count = male_count + res.getInt(0);
            }while (res.moveToNext());
        }

        return male_count;
    }


    public Integer getReplacementFeedingRefused(){
        SQLiteDatabase db = this.getWritableDatabase();
        Integer male_count=0;


        Cursor res = db.rawQuery("SELECT REPLACEMENT_FEEDING_AMOUNT_REFUSED FROM " +TABLE_REPLACEMENT_FEEDING_RECORDS,null);
        res.moveToFirst();
        if(res.getCount() != 0){
            do{
                male_count = male_count + res.getInt(0);
            }while (res.moveToNext());
        }

        return male_count;
    }


////////////////brooders

    /*public Integer getAllMaleFromReplacements(Integer farm_id){
    SQLiteDatabase db = this.getWritableDatabase();
    Integer male_count=0;

    ArrayList<Integer> pens = new ArrayList<>();

    Cursor res0 = db.rawQuery("SELECT ID FROM " +TABLE_PEN+ " WHERE farm_id is ?", new String[]{farm_id.toString()});
    res0.moveToFirst();
    if(res0.getCount() != 0){
        do{
            Integer a = res0.getInt(0);
            pens.add(a);
        }while(res0.moveToNext());
    }
    Cursor res = db.rawQuery("SELECT REPLACEMENT_INV_NUMBER_MALE, REPLACEMENT_INV_PEN_NUMBER FROM " +TABLE_REPLACEMENT_INVENTORIES, null);
    res.moveToFirst();
    if(res.getCount() != 0){
        do{
            Integer pen_id = res.getInt(1);
            if(pens.contains(pen_id)){
                male_count = male_count + res.getInt(0);
            }
        }while (res.moveToNext());
    }

    return male_count;
}*/
public Integer getAllMaleFromBrooders(Integer farm_id){
    SQLiteDatabase db = this.getWritableDatabase();
    Integer male_count=0;
    ArrayList<Integer> pens = new ArrayList<>();

    Cursor res0 = db.rawQuery("SELECT ID FROM " +TABLE_PEN+ " WHERE farm_id is ?", new String[]{farm_id.toString()});
    res0.moveToFirst();
    if(res0.getCount() != 0){
        do{
            Integer a = res0.getInt(0);
            pens.add(a);
        }while(res0.moveToNext());
    }
    Cursor res = db.rawQuery("SELECT BROODER_INV_NUMBER_MALE, BROODER_INV_PEN_NUMBER FROM " +TABLE_BROODER_INVENTORIES, null);
    res.moveToFirst();
    if(res.getCount() != 0){
        do{
            Integer pen_id = res.getInt(1);
            if(pens.contains(pen_id)){
                male_count = male_count + res.getInt(0);
            }
        }while (res.moveToNext());
    }
    return male_count;
}
    public Integer getAllFemaleFromBrooders(Integer farm_id){
        SQLiteDatabase db = this.getWritableDatabase();
        Integer male_count=0;
        ArrayList<Integer> pens = new ArrayList<>();

        Cursor res0 = db.rawQuery("SELECT ID FROM " +TABLE_PEN+ " WHERE farm_id is ?", new String[]{farm_id.toString()});
        res0.moveToFirst();
        if(res0.getCount() != 0){
            do{
                Integer a = res0.getInt(0);
                pens.add(a);
            }while(res0.moveToNext());
        }
        Cursor res = db.rawQuery("SELECT BROODER_INV_NUMBER_FEMALE, BROODER_INV_PEN_NUMBER FROM " +TABLE_BROODER_INVENTORIES, null);
        res.moveToFirst();
        if(res.getCount() != 0){
            do{
                Integer pen_id = res.getInt(1);
                if(pens.contains(pen_id)){
                    male_count = male_count + res.getInt(0);
                }
            }while (res.moveToNext());
        }
        return male_count;
    }

    public Integer getAllBrooderMaleFromMort(Integer farm_id){
        SQLiteDatabase db = this.getWritableDatabase();
        Integer male_count=0;

        ArrayList<Integer> pens = new ArrayList<>();
        ArrayList<Integer> breeders = new ArrayList<>();


        Cursor res0 = db.rawQuery("SELECT ID FROM " +TABLE_PEN+ " WHERE farm_id is ?", new String[]{farm_id.toString()});
        res0.moveToFirst();
        if(res0.getCount() != 0){
            do{
                Integer a = res0.getInt(0);
                pens.add(a);
            }while(res0.moveToNext());
        }

        Cursor res1 = db.rawQuery("SELECT ID FROM " +TABLE_BREEDER_INVENTORIES, null);
        res1.moveToFirst();
        if(res1.getCount() != 0){
            do{

                Integer a = res1.getInt(0);
                if(pens.contains(a)){
                    breeders.add(a);
                }

            }while (res1.moveToNext());
        }


        String type = "brooder";
        String category = "died";
        Cursor res = db.rawQuery("SELECT MORT_AND_SALES_MALE, ID FROM " +TABLE_MORTALITY_AND_SALES+ " WHERE MORT_AND_SALES_CATEGORY LIKE ? AND MORT_AND_SALES_TYPE LIKE ?", new String[]{category, type});
        res.moveToFirst();
        if(res.getCount() != 0){
            do{
                Integer a= res.getInt(1);
                if(breeders.contains(a)){
                    male_count = male_count + res.getInt(0);
                }

            }while (res.moveToNext());
        }

        return male_count;
    }

    public Integer getAllBrooderFemaleFromMort(Integer farm_id){
        SQLiteDatabase db = this.getWritableDatabase();
        Integer male_count=0;

        ArrayList<Integer> pens = new ArrayList<>();
        ArrayList<Integer> breeders = new ArrayList<>();


        Cursor res0 = db.rawQuery("SELECT ID FROM " +TABLE_PEN+ " WHERE farm_id is ?", new String[]{farm_id.toString()});
        res0.moveToFirst();
        if(res0.getCount() != 0){
            do{
                Integer a = res0.getInt(0);
                pens.add(a);
            }while(res0.moveToNext());
        }

        Cursor res1 = db.rawQuery("SELECT ID FROM " +TABLE_BREEDER_INVENTORIES, null);
        res1.moveToFirst();
        if(res1.getCount() != 0){
            do{

                Integer a = res1.getInt(0);
                if(pens.contains(a)){
                    breeders.add(a);
                }

            }while (res1.moveToNext());
        }


        String type = "brooder";
        String category = "died";
        Cursor res = db.rawQuery("SELECT MORT_AND_SALES_FEMALE, ID FROM " +TABLE_MORTALITY_AND_SALES+ " WHERE MORT_AND_SALES_CATEGORY LIKE ? AND MORT_AND_SALES_TYPE LIKE ?", new String[]{category, type});
        res.moveToFirst();
        if(res.getCount() != 0){
            do{
                Integer a= res.getInt(1);
                if(breeders.contains(a)){
                    male_count = male_count + res.getInt(0);
                }

            }while (res.moveToNext());
        }

        return male_count;
    }


    public Integer getAllBrooderMaleFromSales(Integer farm_id){
        SQLiteDatabase db = this.getWritableDatabase();
        Integer male_count=0;

        ArrayList<Integer> pens = new ArrayList<>();
        ArrayList<Integer> breeders = new ArrayList<>();


        Cursor res0 = db.rawQuery("SELECT ID FROM " +TABLE_PEN+ " WHERE farm_id is ?", new String[]{farm_id.toString()});
        res0.moveToFirst();
        if(res0.getCount() != 0){
            do{
                Integer a = res0.getInt(0);
                pens.add(a);
            }while(res0.moveToNext());
        }

        Cursor res1 = db.rawQuery("SELECT ID FROM " +TABLE_BREEDER_INVENTORIES, null);
        res1.moveToFirst();
        if(res1.getCount() != 0){
            do{

                Integer a = res1.getInt(0);
                if(pens.contains(a)){
                    breeders.add(a);
                }

            }while (res1.moveToNext());
        }


        String type = "brooder";
        String category = "sold";
        Cursor res = db.rawQuery("SELECT MORT_AND_SALES_MALE, ID FROM " +TABLE_MORTALITY_AND_SALES+ " WHERE MORT_AND_SALES_CATEGORY LIKE ? AND MORT_AND_SALES_TYPE LIKE ?", new String[]{category, type});
        res.moveToFirst();
        if(res.getCount() != 0){
            do{
                Integer a= res.getInt(1);
                if(breeders.contains(a)){
                    male_count = male_count + res.getInt(0);
                }

            }while (res.moveToNext());
        }

        return male_count;

    }

    public Integer getAllBrooderFemaleFromSales(Integer farm_id){
        SQLiteDatabase db = this.getWritableDatabase();
        Integer male_count=0;

        ArrayList<Integer> pens = new ArrayList<>();
        ArrayList<Integer> breeders = new ArrayList<>();


        Cursor res0 = db.rawQuery("SELECT ID FROM " +TABLE_PEN+ " WHERE farm_id is ?", new String[]{farm_id.toString()});
        res0.moveToFirst();
        if(res0.getCount() != 0){
            do{
                Integer a = res0.getInt(0);
                pens.add(a);
            }while(res0.moveToNext());
        }

        Cursor res1 = db.rawQuery("SELECT ID FROM " +TABLE_BREEDER_INVENTORIES, null);
        res1.moveToFirst();
        if(res1.getCount() != 0){
            do{

                Integer a = res1.getInt(0);
                if(pens.contains(a)){
                    breeders.add(a);
                }

            }while (res1.moveToNext());
        }


        String type = "brooder";
        String category = "sold";
        Cursor res = db.rawQuery("SELECT MORT_AND_SALES_FEMALE, ID FROM " +TABLE_MORTALITY_AND_SALES+ " WHERE MORT_AND_SALES_CATEGORY LIKE ? AND MORT_AND_SALES_TYPE LIKE ?", new String[]{category, type});
        res.moveToFirst();
        if(res.getCount() != 0){
            do{
                Integer a= res.getInt(1);
                if(breeders.contains(a)){
                    male_count = male_count + res.getInt(0);
                }

            }while (res.moveToNext());
        }

        return male_count;
    }

    public Integer getBrooderFeedingOffered(){
        SQLiteDatabase db = this.getWritableDatabase();
        Integer male_count=0;


        Cursor res = db.rawQuery("SELECT BROODER_FEEDING_AMOUNT_OFFERED FROM " +TABLE_BROODER_FEEDING_RECORDS,null);
        res.moveToFirst();
        if(res.getCount() != 0){
            do{
                male_count = male_count + res.getInt(0);
            }while (res.moveToNext());
        }

        return male_count;
    }


    public Integer getBrooderFeedingRefused(){
        SQLiteDatabase db = this.getWritableDatabase();
        Integer male_count=0;


        Cursor res = db.rawQuery("SELECT BROODER_FEEDING_AMOUNT_REFUSED FROM " +TABLE_BROODER_FEEDING_RECORDS,null);
        res.moveToFirst();
        if(res.getCount() != 0){
            do{
                male_count = male_count + res.getInt(0);
            }while (res.moveToNext());
        }

        return male_count;
    }



















    public Cursor getAllDataFromBreedersWhereID(Integer id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " +TABLE_BREEDER+ " where ID is ?", new String[]{id.toString()});

        return res;
    }

    public Cursor getIDFromBreedersWhere(Integer family){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " +TABLE_BREEDER+ " where BREEDER_FAMILY is ?", new String[]{family.toString()},null);
        return res;
    }

    public Cursor getBreedersFromPen(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " +TABLE_PEN+ " where PEN_TYPE LIKE '%Layer%'", null);
        return res;
    }
    public Cursor getAllDataFromBreeders(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " +TABLE_BREEDER, null);

        return res;
    }
    public Cursor getDataFromBreederInvWhereTag(String tag){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " +TABLE_BREEDER_INVENTORIES+ " where BREEDER_INV_BREEDER_TAG like ?", new String[]{tag}, null);

        return res;
    }
    public Cursor getBreederPhenoRecordwhereID(Integer id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " +TABLE_BREEDER_PHENOTYPIC_AND_MORPHOMETRIC_RECORDS+ " where BREEDER_PHENO_MORPHO_INVENTORY_ID like ?", new String[] {id.toString()}, null);

        return res;
    }
    public Cursor getAllDataFromBreederInventory(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " +TABLE_BREEDER_INVENTORIES, null);

        return res;
    }
    public Cursor getAllDataFromBreederInventoryWhereID(Integer id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " +TABLE_BREEDER_INVENTORIES+ " where ID is ?", new String[]{id.toString()});

        return res;
    }
    public Cursor getAllDataFromBreederFeedingRecords(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " +TABLE_BREEDER_FEEDING_RECORDS, null);

        return res;
    }
    public Cursor getAllDataFromBreederPhenoMorphoRecords(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " +TABLE_BREEDER_PHENOTYPIC_AND_MORPHOMETRIC_RECORDS, null);

        return res;
    }

    public Cursor getAllDataFromBreederGrowthRecords(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " +TABLE_BREEDER_GROWTH_RECORDS, null);

        return res;
    }
    public Cursor getAllDataFromPhenoMorphoRecordsBreeder(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " +TABLE_BREEDER_PHENOTYPIC_AND_MORPHOMETRIC_RECORDS, null);

        return res;
    }

    public Cursor getAllDataFromEggProduction(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " +TABLE_EGG_PRODUCTION, null);

        return res;
    }
    public Cursor getAllDataFromEggProductionWhereID(Integer id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " +TABLE_EGG_PRODUCTION+ " where id is ?", new String[]{id.toString()});

        return res;
    }
    public Cursor getAllDataFromEggQuality(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " +TABLE_EGG_QUALITY, null);

        return res;
    }

    public Cursor getAllDataFromHatcheryRecords(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " +TABLE_HATCHERY_RECORD, null);

        return res;
    }

    public Cursor getAllDataFromMortandSalesRecords(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " +TABLE_MORTALITY_AND_SALES, null);

        return res;
    }
    public Cursor getAllDataFromMortandSalesRecordsWithID(Integer id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " +TABLE_MORTALITY_AND_SALES+ " where id is ? ", new String[]{id.toString()});

        return res;
    }
    public Cursor getIDFromBreederInventoyWhereTag(String tag){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select ID from " +TABLE_BREEDER_INVENTORIES+" where BREEDER_INV_BREEDER_TAG is ?", new String[]{tag});

        return res;
    }
    public Cursor getDataFromBreederPhenoMorphosWhere(Integer inv_id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select values_id from " +TABLE_PHENO_MORPHOS+" where breeder_inventory is ?", new String[]{inv_id.toString()});

        return res;
    }
    public Cursor getAllDataFromBreederFeedingRecordsWhereFeedingID(Integer id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select BREEDER_FEEDING_AMOUNT_OFFERED,BREEDER_FEEDING_AMOUNT_REFUSED,BREEDER_FEEDING_REMARKS,BREEDER_FEEDING_DATE_COLLECTED from " +TABLE_BREEDER_FEEDING_RECORDS+ " where ID is ?",new String[]{id.toString()});

        return res;
    }

    public Cursor getAllDataFromBreederEggProdWhereID(Integer id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " +TABLE_EGG_PRODUCTION+ " where ID is ?",new String[]{id.toString()});

        return res;
    }

    public Cursor getAllDataFromBreederHatcheryWhereID(Integer id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " +TABLE_HATCHERY_RECORD+ " where ID is ?",new String[]{id.toString()});

        return res;
    }
    public Cursor getAllDataFromBreederEggQualWhereID(Integer id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " +TABLE_EGG_QUALITY+ " where ID is ?",new String[]{id.toString()});

        return res;
    }
    public Cursor getAllDataFromBreederMortSalesWhereID(Integer id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " +TABLE_MORTALITY_AND_SALES+ " where ID is ?",new String[]{id.toString()});

        return res;
    }

    public Cursor getAllDataFromUsers(Integer farm_id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " +TABLE_USERS+ " where farm_id is ?",new String[]{farm_id.toString()});

        return res;
    }



    public Cursor getFarmIDFromUsers(String email){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select FARM_ID from " +TABLE_USERS+ " where EMAIL is ?",new String[]{email});

        return res;
    }










    public Cursor familyChecker(String family, String selected_line, String selected_generation){

        SQLiteDatabase db = this.getReadableDatabase();
        Integer generation = null;
        Integer line = null;
        Integer family_id =null;

        //get generation id based on selected generation
        Cursor cursor_gen = db.rawQuery("SELECT ID FROM "+TABLE_GENERATION+ " WHERE GENERATION_NUMBER LIKE ?", new String[] {selected_generation}, null);
        cursor_gen.moveToFirst();
        if(cursor_gen.getCount() != 0){
            generation = cursor_gen.getInt(0);

        }
        db.close();

        SQLiteDatabase db2 = this.getReadableDatabase();
        Cursor cursor = db2.rawQuery("SELECT ID FROM "+TABLE_LINE+ " WHERE LINE_GENERATION LIKE ? AND LINE_NUMBER LIKE ?",new String[]{generation.toString(),selected_line}, null);
        cursor.moveToFirst();
        if(cursor.getCount() != 0){
            line = cursor.getInt(0);
        }
        db2.close();

        SQLiteDatabase db3 = this.getReadableDatabase();
        Cursor cursor1 = db3.rawQuery("SELECT ID FROM "+TABLE_FAMILY+ " WHERE FAMILY_LINE LIKE ? AND FAMILY_NUMBER LIKE ? ",new String[]{line.toString(), family},null);
        cursor1.moveToFirst();
        if(cursor1.getCount() != 0){
            family_id = cursor1.getInt(0);
        }

        db3.close();

        SQLiteDatabase db4 = this.getReadableDatabase();
        Cursor res = db4.rawQuery("select * from " +TABLE_BROODER + " where BROODER_FAMILY like ? ",new String[]{family_id.toString()}, null);

        return res;

    }
    public Cursor replacementFamilyChecker(String family, String selected_line, String selected_generation){

        SQLiteDatabase db = this.getReadableDatabase();
        Integer generation = null;
        Integer line = null;
        Integer family_id =null;

        //get generation id based on selected generation
        Cursor cursor_gen = db.rawQuery("SELECT ID FROM "+TABLE_GENERATION+ " WHERE GENERATION_NUMBER LIKE ?", new String[] {selected_generation}, null);
        cursor_gen.moveToFirst();
        if(cursor_gen.getCount() != 0){
            generation = cursor_gen.getInt(0);

        }
        db.close();

        SQLiteDatabase db2 = this.getReadableDatabase();
        Cursor cursor = db2.rawQuery("SELECT ID FROM "+TABLE_LINE+ " WHERE LINE_GENERATION LIKE ? AND LINE_NUMBER LIKE ?",new String[]{generation.toString(),selected_line}, null);
        cursor.moveToFirst();
        if(cursor.getCount() != 0){
            line = cursor.getInt(0);
        }
        db2.close();

        SQLiteDatabase db3 = this.getReadableDatabase();
        Cursor cursor1 = db3.rawQuery("SELECT ID FROM "+TABLE_FAMILY+ " WHERE FAMILY_LINE LIKE ? AND FAMILY_NUMBER LIKE ? ",new String[]{line.toString(), family},null);
        cursor1.moveToFirst();
        if(cursor1.getCount() != 0){
            family_id = cursor1.getInt(0);
        }

        db3.close();

        SQLiteDatabase db4 = this.getReadableDatabase();
        Cursor res = db4.rawQuery("select * from " +TABLE_REPLACEMENT + " where REPLACEMENT_FAMILY like ? ",new String[]{family_id.toString()}, null);

        return res;

    }
    public Cursor breederFamilyChecker(String family, String selected_line, String selected_generation){

        SQLiteDatabase db = this.getReadableDatabase();
        Integer generation = null;
        Integer line = null;
        Integer family_id =null;

        //get generation id based on selected generation
        Cursor cursor_gen = db.rawQuery("SELECT ID FROM "+TABLE_GENERATION+ " WHERE GENERATION_NUMBER LIKE ?", new String[] {selected_generation}, null);
        cursor_gen.moveToFirst();
        if(cursor_gen.getCount() != 0){
            generation = cursor_gen.getInt(0);

        }
        db.close();

        SQLiteDatabase db2 = this.getReadableDatabase();
        Cursor cursor = db2.rawQuery("SELECT ID FROM "+TABLE_LINE+ " WHERE LINE_GENERATION LIKE ? AND LINE_NUMBER LIKE ?",new String[]{generation.toString(),selected_line}, null);
        cursor.moveToFirst();
        if(cursor.getCount() != 0){
            line = cursor.getInt(0);
        }
        db2.close();

        SQLiteDatabase db3 = this.getReadableDatabase();
        Cursor cursor1 = db3.rawQuery("SELECT ID FROM "+TABLE_FAMILY+ " WHERE FAMILY_LINE LIKE ? AND FAMILY_NUMBER LIKE ? ",new String[]{line.toString(), family},null);
        cursor1.moveToFirst();
        if(cursor1.getCount() != 0){
            family_id = cursor1.getInt(0);
        }

        db3.close();

        SQLiteDatabase db4 = this.getReadableDatabase();
        Cursor res = db4.rawQuery("select * from " +TABLE_BREEDER + " where BREEDER_FAMILY like ? ",new String[]{family_id.toString()}, null);

        return res;

    }
    public String getFamLineGen(Integer family_id){
        SQLiteDatabase db = this.getReadableDatabase();
        StringBuffer buffer = new StringBuffer();
        List<String> famLineGen = new ArrayList<>();
        Integer line_id=null;
        Integer gen_id=null;

        try{
            Cursor fam = db.rawQuery("select FAMILY_LINE, FAMILY_NUMBER from " +TABLE_FAMILY + " where ID is ? ",new String[]{family_id.toString()}, null);
            fam.moveToFirst();
            if(fam.getCount() != 0){
                line_id = fam.getInt(0);
                buffer.append(fam.getString(1) + " ");
            }

            Cursor line = db.rawQuery("select LINE_GENERATION, LINE_NUMBER from " +TABLE_LINE + " where ID is ? ",new String[]{line_id.toString()}, null);
            line.moveToFirst();
            if(line.getCount() != 0){
                gen_id = line.getInt(0);
                buffer.append(line.getString(1)+ " ");
            }

            Cursor gen = db.rawQuery("select GENERATION_NUMBER from " +TABLE_GENERATION + " where ID is ? ",new String[]{gen_id.toString()}, null);
            gen.moveToFirst();
            if(gen.getCount() != 0){
                buffer.append(gen.getString(0));
            }

            return buffer.toString();
        }catch (Exception e){
            return null;
        }

    }
    public Cursor getFamilyID(String family, String selected_line, String selected_generation){

        List<String> families = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        Integer generation = null;
        Integer line = null;
        Integer family_id =null;

        //get generation id based on selected generation
        Cursor cursor_gen = db.rawQuery("SELECT ID FROM "+TABLE_GENERATION+ " WHERE GENERATION_NUMBER LIKE ?", new String[] {selected_generation}, null);
        cursor_gen.moveToFirst();
        if(cursor_gen.getCount() != 0){
            generation = cursor_gen.getInt(0);

        }
        db.close();

        SQLiteDatabase db2 = this.getReadableDatabase();
        Cursor cursor = db2.rawQuery("SELECT ID FROM "+TABLE_LINE+ " WHERE LINE_GENERATION LIKE ? AND LINE_NUMBER LIKE ?",new String[]{generation.toString(),selected_line}, null);
        cursor.moveToFirst();
        if(cursor.getCount() != 0){
            line = cursor.getInt(0);
        }
        db2.close();

        SQLiteDatabase db3 = this.getReadableDatabase();
        Cursor cursor1 = db3.rawQuery("SELECT ID FROM "+TABLE_FAMILY+ " WHERE FAMILY_LINE LIKE ? AND FAMILY_NUMBER LIKE ? ",new String[]{line.toString(), family},null);
        cursor1.moveToFirst();

        db3.close();


        return cursor1;
    }




















    public List<String> getAllDataFromGenerationasList(){
        List<String> generations = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_GENERATION;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Integer is_active = cursor.getInt(4);
                if(is_active == 1){
                    generations.add(cursor.getString(2));
                }

            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();

        // returning lables
        return generations;
    }
    public List<String> getAllDataFromLineasList(String selected_generation){

        List<String> lines = new ArrayList<String>();
        Integer generation = null;

        SQLiteDatabase db = this.getReadableDatabase();

       // String getGenNumberQuery = "SELECT * FROM " +TABLE_GENERATION+ " WHERE GENERATION_NUMBER LIKE '%0003%'";// +selected_generation;
        Cursor cursor_gen = db.rawQuery("SELECT ID FROM "+TABLE_GENERATION+ " WHERE GENERATION_NUMBER LIKE ?", new String[] {selected_generation}, null);
        cursor_gen.moveToFirst();
        if(cursor_gen.getCount() != 0){
            generation = cursor_gen.getInt(0);

        }
        db.close();
        // Select All Query
        SQLiteDatabase db2 = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_LINE + " WHERE LINE_GENERATION like " + generation;
        Cursor cursor = db2.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        // looping through all rows and adding to list
        if (cursor.getCount()!= 0) {
            do {
                Integer is_active = cursor.getInt(2);
                if(is_active == 1){
                    lines.add(cursor.getString(1));
                }

            } while (cursor.moveToNext());
        }else{
        }
        db2.close();
        // closing connection
        cursor.close();

        return lines;
    }
    public List<String> getAllDataFromFamilyasList(String selected_line, String selected_generation){
        List<String> families = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        Integer generation = null;
        Integer line = null;

        //get generation id based on selected generation
        Cursor cursor_gen = db.rawQuery("SELECT ID FROM "+TABLE_GENERATION+ " WHERE GENERATION_NUMBER IS ?", new String[] {selected_generation}, null);
        cursor_gen.moveToFirst();
        if(cursor_gen.getCount() != 0){
            generation = cursor_gen.getInt(0);

        }
        db.close();

        SQLiteDatabase db2 = this.getReadableDatabase();
        Cursor cursor = db2.rawQuery("SELECT ID FROM "+TABLE_LINE+ " WHERE LINE_GENERATION IS ? AND LINE_NUMBER IS ?",new String[]{generation.toString(),selected_line}, null);
        cursor.moveToFirst();
        if(cursor.getCount() != 0){
          line = cursor.getInt(0);
        }
        db2.close();

        SQLiteDatabase db3 = this.getReadableDatabase();
        Cursor cursor1 = db3.rawQuery("SELECT * FROM "+TABLE_FAMILY+ " WHERE FAMILY_LINE IS ?",new String[]{line.toString()},null);
        cursor1.moveToFirst();
        if(cursor1.getCount() != 0){
            do {
                Integer is_active = cursor1.getInt(2);
                if(is_active == 1){
                    families.add(cursor1.getString(1));
                }

            } while (cursor1.moveToNext());
        }

        db3.close();


        cursor1.close();


        // returning lables
        return families;
    }
    public List<String> getAllDataFromBroodersasList(String selected_family, String selected_line, String selected_generation){
        List<String> brooders = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        Integer generation = null;
        Integer line = null;
        Integer familiy = null;
        Integer brooder = 0;

        //get generation id based on selected generation
        Cursor cursor_gen = db.rawQuery("SELECT ID FROM "+TABLE_GENERATION+ " WHERE GENERATION_NUMBER IS ?", new String[] {selected_generation}, null);
        cursor_gen.moveToFirst();
        if(cursor_gen.getCount() != 0){
            generation = cursor_gen.getInt(0);

        }
        db.close();

        SQLiteDatabase db2 = this.getReadableDatabase();
        Cursor cursor = db2.rawQuery("SELECT ID FROM "+TABLE_LINE+ " WHERE LINE_GENERATION IS ? AND LINE_NUMBER IS ?",new String[]{generation.toString(),selected_line}, null);
        cursor.moveToFirst();
        if(cursor.getCount() != 0){
            line = cursor.getInt(0);
        }
        db2.close();

        SQLiteDatabase db3 = this.getReadableDatabase();
        Cursor cursor1 = db3.rawQuery("SELECT ID FROM "+TABLE_FAMILY+ " WHERE FAMILY_LINE IS ? AND FAMILY_NUMBER IS ?",new String[]{line.toString(),selected_family}, null);
        cursor1.moveToFirst();
        if(cursor1.getCount() != 0){
            familiy = cursor1.getInt(0);
        }
        db3.close();


        SQLiteDatabase db4 = this.getReadableDatabase();
        Cursor cursor2 = db4.rawQuery("SELECT ID FROM "+TABLE_BROODER+ " WHERE BROODER_FAMILY IS ?",new String[]{familiy.toString()},null);
        cursor2.moveToFirst();
        if(cursor2.getCount() != 0){
            brooder = cursor2.getInt(0);
        }

        db4.close();
        String pen_number=null;
        SQLiteDatabase db5 = this.getReadableDatabase();
        Cursor cursor3 = db5.rawQuery("SELECT BROODER_INV_BROODER_TAG,BROODER_INV_PEN_NUMBER, BROODER_INV_BATCHING_DATE FROM " +TABLE_BROODER_INVENTORIES+ " WHERE BROODER_INV_BROODER_ID IS ?", new String[]{brooder.toString()});
        cursor3.moveToFirst();





        if(cursor3.getCount() != 0){
            do{
                Integer pen_id=cursor3.getInt(1);
                Cursor cursor4 = db5.rawQuery("SELECT PEN_NUMBER FROM " + TABLE_PEN+ " WHERE ID IS ?", new String[]{pen_id.toString()});
                cursor4.moveToFirst();
                if(cursor4.getCount() != 0){
                    pen_number = cursor4.getString(0);
                }
                brooders.add("Brooder: "+cursor3.getString(0)+" | "+"Pen: "+pen_number+" | "+"Batch: " +cursor3.getString(2));
            }while(cursor3.moveToNext());

        }

        cursor3.close();
        db5.close();


        // returning lables
        return brooders;
    }
    public List<String> getAllDataFromMaleReplacementasList(String selected_family, String selected_line, String selected_generation) {
        List<String> replacements = new ArrayList<String>();
        Integer zero = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        Integer generation = null;
        Integer line = null;
        Integer familiy = null;
        Integer replacement = 0;

        //get generation id based on selected generation
        Cursor cursor_gen = db.rawQuery("SELECT ID FROM " + TABLE_GENERATION + " WHERE GENERATION_NUMBER IS ?", new String[]{selected_generation}, null);
        cursor_gen.moveToFirst();
        if (cursor_gen.getCount() != 0) {
            generation = cursor_gen.getInt(0);

        }
        db.close();

        SQLiteDatabase db2 = this.getReadableDatabase();
        Cursor cursor = db2.rawQuery("SELECT ID FROM " + TABLE_LINE + " WHERE LINE_GENERATION IS ? AND LINE_NUMBER IS ?", new String[]{generation.toString(), selected_line}, null);
        cursor.moveToFirst();
        if (cursor.getCount() != 0) {
            line = cursor.getInt(0);
        }
        db2.close();

        SQLiteDatabase db3 = this.getReadableDatabase();
        Cursor cursor1 = db3.rawQuery("SELECT ID FROM " + TABLE_FAMILY + " WHERE FAMILY_LINE IS ? AND FAMILY_NUMBER IS ?", new String[]{line.toString(), selected_family}, null);
        cursor1.moveToFirst();
        if (cursor1.getCount() != 0) {
            familiy = cursor1.getInt(0);
        }
        db3.close();


        SQLiteDatabase db4 = this.getReadableDatabase();
        Cursor cursor2 = db4.rawQuery("SELECT ID FROM " + TABLE_REPLACEMENT + " WHERE REPLACEMENT_FAMILY IS ?", new String[]{familiy.toString()}, null);
        cursor2.moveToFirst();
        if (cursor2.getCount() != 0) {
            replacement = cursor2.getInt(0);
        }

        db4.close();

        SQLiteDatabase db5 = this.getReadableDatabase();
        Cursor cursor3 = db5.rawQuery("SELECT REPLACEMENT_INV_REPLACEMENT_TAG,REPLACEMENT_INV_NUMBER_MALE, REPLACEMENT_INV_NUMBER_FEMALE FROM " + TABLE_REPLACEMENT_INVENTORIES + " WHERE REPLACEMENT_INV_REPLACEMENT_ID IS ? AND NOT REPLACEMENT_INV_NUMBER_MALE IS ?", new String[]{replacement.toString(), zero.toString()});
        cursor3.moveToFirst();
        if (cursor3.getCount() != 0) {
            do {
                replacements.add("Tag: " + cursor3.getString(0) + " | " + "Male: " + cursor3.getInt(1) + " | " + "Female: " + cursor3.getInt(2));
            } while (cursor3.moveToNext());

        }

        cursor3.close();
        db5.close();


        // returning lables
        return replacements;
    }
    public List<String> getAllDataFromFemaleReplacementasList(String selected_family, String selected_line, String selected_generation) {
        List<String> replacements = new ArrayList<String>();
        Integer zero = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        Integer generation = null;
        Integer line = null;
        Integer familiy = null;
        Integer replacement = 0;

        //get generation id based on selected generation
        Cursor cursor_gen = db.rawQuery("SELECT ID FROM " + TABLE_GENERATION + " WHERE GENERATION_NUMBER IS ?", new String[]{selected_generation}, null);
        cursor_gen.moveToFirst();
        if (cursor_gen.getCount() != 0) {
            generation = cursor_gen.getInt(0);

        }
        db.close();

        SQLiteDatabase db2 = this.getReadableDatabase();
        Cursor cursor = db2.rawQuery("SELECT ID FROM " + TABLE_LINE + " WHERE LINE_GENERATION IS ? AND LINE_NUMBER IS ?", new String[]{generation.toString(), selected_line}, null);
        cursor.moveToFirst();
        if (cursor.getCount() != 0) {
            line = cursor.getInt(0);
        }
        db2.close();

        SQLiteDatabase db3 = this.getReadableDatabase();
        Cursor cursor1 = db3.rawQuery("SELECT ID FROM " + TABLE_FAMILY + " WHERE FAMILY_LINE IS ? AND FAMILY_NUMBER IS ?", new String[]{line.toString(), selected_family}, null);
        cursor1.moveToFirst();
        if (cursor1.getCount() != 0) {
            familiy = cursor1.getInt(0);
        }
        db3.close();


        SQLiteDatabase db4 = this.getReadableDatabase();
        Cursor cursor2 = db4.rawQuery("SELECT ID FROM " + TABLE_REPLACEMENT + " WHERE REPLACEMENT_FAMILY IS ?", new String[]{familiy.toString()}, null);
        cursor2.moveToFirst();
        if (cursor2.getCount() != 0) {
            replacement = cursor2.getInt(0);
        }

        db4.close();

        SQLiteDatabase db5 = this.getReadableDatabase();
        Cursor cursor3 = db5.rawQuery("SELECT REPLACEMENT_INV_REPLACEMENT_TAG,REPLACEMENT_INV_NUMBER_MALE, REPLACEMENT_INV_NUMBER_FEMALE FROM " + TABLE_REPLACEMENT_INVENTORIES + " WHERE REPLACEMENT_INV_REPLACEMENT_ID IS ? AND NOT REPLACEMENT_INV_NUMBER_FEMALE IS ?", new String[]{replacement.toString(), zero.toString()});
        cursor3.moveToFirst();
        if (cursor3.getCount() != 0) {
            do {
                replacements.add("Tag: " + cursor3.getString(0) + " | " + "Male: " + cursor3.getInt(1) + " | " + "Female: " + cursor3.getInt(2));
            } while (cursor3.moveToNext());

        }

        cursor3.close();
        db5.close();


        // returning lables
        return replacements;
    }
    public List<String> getAllDataFromPenasList(){
        Integer zero = 0;
        List<String> pen = new ArrayList<String>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from pen_table where PEN_TYPE like '%Layer%' AND PEN_CURRENT_CAPACITY IS ?",new String[]{zero.toString()});

        if (cursor.moveToFirst()) {
            do {
                pen.add(cursor.getString(2));
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();


        return pen;
    }
    public List<String> getAllDataFromBrooderPenasList(){

        List<String> generations = new ArrayList<String>();

        String selectQuery = ("select * from pen_table where PEN_TYPE LIKE '%Brooder%'");

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                generations.add(cursor.getString(2));
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();

        // returning lables
        return generations;
    }











/*

    public static final String TABLE_PEN = "pen_table";
    public static final String PEN_COL_0 = "ID";
    public static final String PEN_COL_1 = "farm_id";
    public static final String PEN_COL_2 = "PEN_NUMBER";
    public static final String PEN_COL_3 = "PEN_TYPE";
    public static final String PEN_COL_4 = "PEN_TOTAL_CAPACITY";
    public static final String PEN_COL_5 = "PEN_CURRENT_CAPACITY";
    public static final String PEN_COL_6 = "PEN_IS_ACTIVE";
*/


    public boolean updatePen(String number, String type, Integer total, Integer current){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PEN_COL_2, number);
        contentValues.put(PEN_COL_3, type);
        contentValues.put(PEN_COL_4, total);
        contentValues.put(PEN_COL_5, current);
        long result = db.update(TABLE_PEN,contentValues,"PEN_NUMBER = ?", new String[]{number});
        if (result == -1)
            return  false;
        else
            return true;


    }

    public boolean updateFarm(Integer id, String name, String address, Integer week){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(FARM_COL_2, name);
        contentValues.put(FARM_COL_4, address);
        contentValues.put(FARM_COL_5, week);

        long result = db.update(TABLE_FARMS,contentValues,"ID = ?", new String[]{id.toString()});
        if (result == -1)
            return  false;
        else
            return true;


    }

    public boolean updateDataAddress(String id, String breed, String region, String province, String town, String barangay){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, id);
        contentValues.put(COL_2, breed);
        contentValues.put(COL_3, region);
        contentValues.put(COL_4, province);
        contentValues.put(COL_5, town);
        contentValues.put(COL_6, barangay);
        db.update(TABLE_PROFILE, contentValues, "ID = ?", new String[]{ id });
        return true;
    }

    public boolean updateDataContacts(String id, String phone, String email){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, id);
        contentValues.put(COL_7, phone);
        contentValues.put(COL_8, email);
        db.update(TABLE_PROFILE, contentValues, "ID = ?", new String[]{ id });
        return true;
    }


    public boolean updateDataPen(Integer pen_id, String pen_number, Integer pen_capacity){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PEN_COL_2, pen_number );
        contentValues.put(PEN_COL_4, pen_capacity);

        db.update(TABLE_PEN, contentValues, "ID = ?", new String[]{ pen_id.toString() });
        return true;
    }
    public boolean updateDataPen1(Integer pen_id, String pen_number, Integer pen_capacity){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PEN_COL_2, pen_number );
        contentValues.put(PEN_COL_5, pen_capacity);

        db.update(TABLE_PEN, contentValues, "ID = ?", new String[]{ pen_id.toString() });
        return true;
    }

    public boolean updateMaleFemaleCount(String tag, Integer male_count, Integer female_count){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(BROODER_INV_COL_3, tag);
        contentValues.put(BROODER_INV_COL_5, male_count);
        contentValues.put(BROODER_INV_COL_6, female_count);
        db.update(TABLE_BROODER_INVENTORIES, contentValues, "BROODER_INV_BROODER_TAG = ?", new String[]{ tag });
        return true;
    }

    public boolean updateMaleFemaleReplacementCount2(String tag, Integer male_count, Integer female_count, Integer total_count){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(REPLACEMENT_INV_COL_3, tag);
        contentValues.put(REPLACEMENT_INV_COL_5, male_count);
        contentValues.put(REPLACEMENT_INV_COL_6, female_count);
        contentValues.put(REPLACEMENT_INV_COL_7, total_count);
        db.update(TABLE_REPLACEMENT_INVENTORIES, contentValues, "REPLACEMENT_INV_REPLACEMENT_TAG = ?", new String[]{ tag });
        return true;
    }
    public boolean updateMaleFemaleReplacementCount(String tag, Integer male_count, Integer female_count){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(REPLACEMENT_INV_COL_3, tag);
        contentValues.put(REPLACEMENT_INV_COL_5, male_count);
        contentValues.put(REPLACEMENT_INV_COL_6, female_count);
        db.update(TABLE_REPLACEMENT_INVENTORIES, contentValues, "REPLACEMENT_INV_REPLACEMENT_TAG = ?", new String[]{ tag });
        return true;
    }

    public boolean updateMaleFemaleBreederCount(String tag, Integer male_count, Integer female_count, Integer total_count){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(BREEDER_INV_COL_3, tag);
        contentValues.put(BREEDER_INV_COL_5, male_count);
        contentValues.put(BREEDER_INV_COL_6, female_count);
        contentValues.put(BREEDER_INV_COL_7, total_count);
        db.update(TABLE_BREEDER_INVENTORIES, contentValues, "BREEDER_INV_BREEDER_TAG = ?", new String[]{ tag });
        return true;
    }

    /*
    public static final String TABLE_PHENO_MORPHOS = "pheno_morphos";
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
    public boolean updatePhenoMorphoValues(Integer id){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(PHENO_MORPHO_VALUES_COL_6, getDate());

        long result =db.update(TABLE_PHENO_MORPHO_VALUES, contentValues, "id = ?", new String[]{ id.toString() });
        long result2 = db.update(TABLE_PHENO_MORPHOS, contentValues, "values_id = ?", new String[]{id.toString()});

        if(result==-1 || result2 ==-1){
            return false;
        }else{
            return true;
        }

    }

    public boolean updateMaleFemaleBrooderCount(String tag, Integer male_count, Integer female_count, Integer total_count){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(BROODER_INV_COL_3, tag);
        contentValues.put(BROODER_INV_COL_5, male_count);
        contentValues.put(BROODER_INV_COL_6, female_count);
        contentValues.put(BROODER_INV_COL_7, total_count);
        db.update(TABLE_BROODER_INVENTORIES, contentValues, "BROODER_INV_BROODER_TAG = ?", new String[]{ tag });
        return true;
    }
    public boolean updateBrooderInventory(String tag, Integer total){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(BROODER_INV_COL_3, tag);
        contentValues.put(BROODER_INV_COL_7, total);
        db.update(TABLE_BROODER_INVENTORIES, contentValues, "BROODER_INV_BROODER_TAG = ?", new String[]{ tag });
        return true;
    }
    public boolean deleteFeedingRecord(Integer id){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(BROODER_FEEDING_COL_6, getDate());

        db.update(TABLE_BROODER_FEEDING_RECORDS, contentValues, "ID = ?", new String[]{ id.toString() });
        return true;
    }
    public boolean deleteFeedingRecordReplacement(Integer id){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(REPLACEMENT_FEEDING_COL_6, getDate());

        db.update(TABLE_REPLACEMENT_FEEDING_RECORDS, contentValues, "ID = ?", new String[]{ id.toString() });
        return true;
    }
    public boolean deleteFeedingRecordBreeder(Integer id){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(BREEDER_FEEDING_COL_6, getDate());

        db.update(TABLE_BREEDER_FEEDING_RECORDS, contentValues, "ID = ?", new String[]{ id.toString() });
        return true;
    }
    public boolean deleteGrowthRecord(Integer id){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(BROODER_GROWTH_COL_10, getDate());

        db.update(TABLE_BROODER_GROWTH_RECORDS, contentValues, "ID = ?", new String[]{ id.toString() });
        return true;
    }
    public boolean deleteGrowthRecordReplacement(Integer id){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(REPLACEMENT_GROWTH_COL_10, getDate());

        db.update(TABLE_REPLACEMENT_GROWTH_RECORDS, contentValues, "ID = ?", new String[]{ id.toString() });
        return true;
    }
    public boolean deleteEggProduction(Integer id){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(EGG_PRODUCTION_COL_8, getDate());

        db.update(TABLE_EGG_PRODUCTION, contentValues, "ID = ?", new String[]{ id.toString() });
        return true;
    }
    public boolean deleteHatchery(Integer id){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(HATCHERY_COL_9, getDate());

        db.update(TABLE_HATCHERY_RECORD, contentValues, "ID = ?", new String[]{ id.toString() });
        return true;
    }
    public boolean deleteEggQuality(Integer id){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(EGG_QUALITY_COL_17, getDate());

        db.update(TABLE_EGG_QUALITY, contentValues, "ID = ?", new String[]{ id.toString() });
        return true;
    }
    public boolean cullBrooderInventory(String tag, String date){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(BROODER_INV_COL_3, tag);
        contentValues.put(BROODER_INV_COL_9, date);
        db.update(TABLE_BROODER_INVENTORIES, contentValues, "BROODER_INV_BROODER_TAG = ?", new String[]{ tag });
        return true;
    }

    public boolean cullReplacementInventory(String tag, String date){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(REPLACEMENT_INV_COL_3, tag);
        contentValues.put(REPLACEMENT_INV_COL_9, date);
        db.update(TABLE_REPLACEMENT_INVENTORIES, contentValues, "REPLACEMENT_INV_REPLACEMENT_TAG = ?", new String[]{ tag });
        return true;
    }
    public boolean cullBreederInventory(String tag, String date){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(BREEDER_INV_COL_3, tag);
        contentValues.put(BREEDER_INV_COL_9, date);
        db.update(TABLE_BREEDER_INVENTORIES, contentValues, "BREEDER_INV_BREEDER_TAG = ?", new String[]{ tag });
        return true;
    }
    public boolean cullGeneration(Integer generation_id){
        String date = getDate();
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        ContentValues contentValuesLine = new ContentValues();
        ContentValues contentValuesFamily = new ContentValues();

        ContentValues contentValuesBreeder = new ContentValues();
        ContentValues contentValuesBreederInventory = new ContentValues();
        ContentValues contentValuesBreederInventoryPen = new ContentValues();

        ContentValues contentValuesReplacement = new ContentValues();
        ContentValues contentValuesReplacementInventory = new ContentValues();
        ContentValues contentValuesReplacementInventoryPen = new ContentValues();

        ContentValues contentValuesBrooder = new ContentValues();
        ContentValues contentValuesBrooderInventory = new ContentValues();
        ContentValues contentValuesBrooderInventoryPen = new ContentValues();

        Integer zero = 0;
        contentValues.put(GENERATION_COL_4, zero);
        contentValuesLine.put(LINE_COL_2, zero);
        contentValuesFamily.put(FAMILY_COL_2, zero);

        contentValuesBreeder.put(BREEDER_COL_4, date);
        contentValuesBreederInventory.put(BREEDER_INV_COL_9, date);
        contentValuesBreederInventoryPen.put(PEN_COL_5, zero);

        contentValuesReplacement.put(REPLACEMENT_COL_3, date);
        contentValuesReplacementInventory.put(REPLACEMENT_INV_COL_9, date);
        contentValuesReplacementInventoryPen.put(PEN_COL_5, zero);

        contentValuesBrooder.put(BROODER_COL_3, date);
        contentValuesBrooderInventory.put(BROODER_INV_COL_9, date);
        contentValuesBrooderInventoryPen.put(PEN_COL_5, zero);

        db.update(TABLE_GENERATION, contentValues, "ID is ?", new String[]{ generation_id.toString() });
        Cursor cursor_line = db.rawQuery("SELECT ID FROM "+TABLE_LINE+ " WHERE LINE_GENERATION IS ?", new String[] {generation_id.toString()}, null);
        cursor_line.moveToFirst();
        if(cursor_line.getCount() != 0){
            do{
                Integer line_id = cursor_line.getInt(0);
                db.update(TABLE_LINE, contentValues, "ID is ?", new String[]{ line_id.toString() });
                Cursor cursor_fam =  db.rawQuery("SELECT ID FROM "+TABLE_FAMILY+ " WHERE FAMILY_LINE IS ?", new String[] {line_id.toString()}, null);
                cursor_fam.moveToFirst();
                if(cursor_fam.getCount() != 0){
                    do{
                        Integer fam_id = cursor_fam.getInt(0);
                        db.update(TABLE_FAMILY, contentValuesFamily,"ID is ?", new String[]{ fam_id.toString() });

                        Cursor cursor_breeder =  db.rawQuery("SELECT ID FROM "+TABLE_BREEDER+ " WHERE BREEDER_FAMILY IS ?", new String[] {fam_id.toString()}, null);
                        cursor_breeder.moveToFirst();
                        if(cursor_breeder.getCount() != 0){
                            do{
                                Integer breeder_id = cursor_breeder.getInt(0);
                                db.update(TABLE_BREEDER, contentValuesBreeder, "ID is ?", new String[]{breeder_id.toString()});
                                Cursor cursor_breeder_inv = db.rawQuery("SELECT * FROM "+TABLE_BREEDER_INVENTORIES+ " WHERE BREEDER_INV_BREEDER_ID IS ?", new String[] {breeder_id.toString()}, null);
                                cursor_breeder_inv.moveToFirst();
                                if(cursor_breeder_inv.getCount() != 0){
                                    do{
                                        Integer breeder_inv_id = cursor_breeder_inv.getInt(0);
                                        Integer breeder_pen_id = cursor_breeder_inv.getInt(2);
                                        db.update(TABLE_BREEDER_INVENTORIES, contentValuesBreederInventory, "ID is ?", new String[]{breeder_inv_id.toString()});
                                        db.update(TABLE_PEN, contentValuesBreederInventoryPen, "ID is ?", new String[]{breeder_pen_id.toString()});

                                    }while (cursor_breeder_inv.moveToNext());
                                }
                            }while(cursor_breeder.moveToNext());
                        }

                        Cursor cursor_replacement =  db.rawQuery("SELECT ID FROM "+TABLE_REPLACEMENT+ " WHERE REPLACEMENT_FAMILY IS ?", new String[] {fam_id.toString()}, null);
                        cursor_replacement.moveToFirst();
                        if(cursor_replacement.getCount() != 0){
                            do{
                                Integer replacement_id = cursor_replacement.getInt(0);
                                db.update(TABLE_REPLACEMENT, contentValuesReplacement, "ID is ?", new String[]{replacement_id.toString()});
                                Cursor cursor_replacement_inv = db.rawQuery("SELECT * FROM "+TABLE_REPLACEMENT_INVENTORIES+ " WHERE REPLACEMENT_INV_REPLACEMENT_ID IS ?", new String[] {replacement_id.toString()}, null);
                                cursor_replacement_inv.moveToFirst();
                                if(cursor_replacement_inv.getCount() != 0){
                                    do{
                                        Integer replacement_inv_id = cursor_replacement_inv.getInt(0);
                                        Integer replacement_pen_id = cursor_replacement_inv.getInt(2);
                                        db.update(TABLE_REPLACEMENT_INVENTORIES, contentValuesReplacementInventory, "ID is ?", new String[]{replacement_inv_id.toString()});
                                        db.update(TABLE_PEN, contentValuesReplacementInventoryPen, "ID is ?", new String[]{replacement_pen_id.toString()});

                                    }while (cursor_replacement_inv.moveToNext());
                                }
                            }while(cursor_replacement.moveToNext());
                        }
                        Cursor cursor_brooder =  db.rawQuery("SELECT ID FROM "+TABLE_BROODER+ " WHERE BROODER_FAMILY IS ?", new String[] {fam_id.toString()}, null);
                        cursor_brooder.moveToFirst();
                        if(cursor_brooder.getCount() != 0){
                            do{
                                Integer brooder_id = cursor_brooder.getInt(0);
                                db.update(TABLE_BROODER, contentValuesBrooder, "ID is ?", new String[]{brooder_id.toString()});
                                Cursor cursor_brooder_inv = db.rawQuery("SELECT * FROM "+TABLE_BROODER_INVENTORIES+ " WHERE BROODER_INV_BROODER_ID IS ?", new String[] {brooder_id.toString()}, null);
                                cursor_brooder_inv.moveToFirst();
                                if(cursor_brooder_inv.getCount() != 0){
                                    do{
                                        Integer brooder_inv_id = cursor_brooder_inv.getInt(0);
                                        Integer brooder_pen_id = cursor_brooder_inv.getInt(2);
                                        db.update(TABLE_BROODER_INVENTORIES, contentValuesBrooderInventory, "ID is ?", new String[]{brooder_inv_id.toString()});
                                        db.update(TABLE_PEN, contentValuesBrooderInventoryPen, "ID is ?", new String[]{brooder_pen_id.toString()});

                                    }while (cursor_brooder_inv.moveToNext());
                                }
                            }while(cursor_brooder.moveToNext());
                        }
                    }while(cursor_fam.moveToNext());
                }
            }while(cursor_line.moveToNext());


        }
        return true;
    }
    private String getDate() {



        Date currentTime = Calendar.getInstance().getTime();
        // currentTime = currentTime.toString();
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        //System.out.println(formatter.format(date));
        return formatter.format(date);
    }
    public boolean cullLine(Integer line_id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        Integer zero = 0;
        contentValues.put(LINE_COL_2, zero);

        long result = db.update(TABLE_LINE, contentValues, "ID is ?", new String[]{ line_id.toString() });
        if (result == -1)
            return  false;
        else
            return true;
    }
    public boolean cullFamily(Integer family_id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        Integer zero = 0;
        contentValues.put(FAMILY_COL_2, zero);

        long result = db.update(TABLE_FAMILY, contentValues, "ID is ?", new String[]{ family_id.toString() });
        if (result == -1)
            return  false;
        else
            return true;
    }
    public boolean updateReplacementInventoryMaleCountAndTotal(String tag, Integer male_count, Integer total){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(REPLACEMENT_INV_COL_3, tag);
        contentValues.put(REPLACEMENT_INV_COL_5, male_count);
        contentValues.put(REPLACEMENT_INV_COL_7, total);
        db.update(TABLE_REPLACEMENT_INVENTORIES, contentValues, "REPLACEMENT_INV_REPLACEMENT_TAG = ?", new String[]{ tag });
        return true;
    }
    public boolean updateReplacementInventoryFemaleCountAndTotal(String tag, Integer female_count, Integer total){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(REPLACEMENT_INV_COL_3, tag);
        contentValues.put(REPLACEMENT_INV_COL_6, female_count);
        contentValues.put(REPLACEMENT_INV_COL_7, total);
        db.update(TABLE_REPLACEMENT_INVENTORIES, contentValues, "REPLACEMENT_INV_REPLACEMENT_TAG = ?", new String[]{ tag });
        return true;
    }

    public boolean updateHatcheryRecord(Integer hatchery_id, String date_set, String batching_date, Integer quantity, Integer week_lay,Integer fertile, Integer no_hatched, String date_hatched){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(HATCHERY_COL_2, date_set);
        contentValues.put(HATCHERY_COL_3, batching_date);
        contentValues.put(HATCHERY_COL_4, quantity);
        contentValues.put(HATCHERY_COL_5, week_lay);
        contentValues.put(HATCHERY_COL_6, fertile);
        contentValues.put(HATCHERY_COL_7, no_hatched);
        contentValues.put(HATCHERY_COL_8, date_hatched);
        db.update(TABLE_HATCHERY_RECORD, contentValues, "ID = ?", new String[]{ hatchery_id.toString() });
        return true;
    }



}
