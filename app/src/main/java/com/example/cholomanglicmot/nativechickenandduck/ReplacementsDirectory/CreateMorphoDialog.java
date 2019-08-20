package com.example.cholomanglicmot.nativechickenandduck.ReplacementsDirectory;

import android.app.AlertDialog;
import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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

import com.example.cholomanglicmot.nativechickenandduck.APIHelper;
import com.example.cholomanglicmot.nativechickenandduck.DatabaseHelper;
import com.example.cholomanglicmot.nativechickenandduck.R;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class CreateMorphoDialog extends DialogFragment{
    private EditText morpho_height, morpho_weight, morpho_body_length,morpho_chest_circumference, morpho_wing_span, morpho_shank_length;
    private String  replacement_pen;
    private String replacement_inv_tag, pheno_record,pheno_sex,pheno_tag, pheno_date;
    TextView replacement_tag;
    private Button mActionOk;

    DatabaseHelper myDb;

    ArrayList<Replacement_PhenoMorphoRecords> arrayListPhenoMorpho = new ArrayList<>();
    ArrayList<Replacement_PhenoMorphoRecords>arrayList_temp = new ArrayList<>();
    ArrayList<Replacement_Inventory>arrayListReplacementInventory = new ArrayList<>();
    ArrayList<Replacement_Inventory>arrayListReplacementInventory1 = new ArrayList<>();
    Integer inv_id, id_0 ;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_create_morpho_dialog, container, false);
        pheno_record = getArguments().getString("Phenotypic Records");
        pheno_sex = getArguments().getString("Phenotypic Sex");
        pheno_tag = getArguments().getString("Phenotypic Tag");
        pheno_date = getArguments().getString("Phenotypic Date");
        replacement_pen = getArguments().getString("Replacement Pen");
        replacement_inv_tag = getArguments().getString("Replacement Tag");

        replacement_tag = view.findViewById(R.id.replacement_tag);
        replacement_tag.setText(replacement_inv_tag);




        mActionOk = view.findViewById(R.id.action_ok);

        morpho_height = view.findViewById(R.id.morpho_height);
        morpho_weight = view.findViewById(R.id.morpho_weight);
        morpho_body_length = view.findViewById(R.id.morpho_body_length);
        morpho_chest_circumference = view.findViewById(R.id.morpho_chest_circumference);
        morpho_wing_span = view.findViewById(R.id.morpho_wing_span);
        morpho_shank_length = view.findViewById(R.id.morpho_shank_length);


        myDb = new DatabaseHelper(getContext());




        mActionOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                StringBuffer morpho = new StringBuffer();
                StringBuffer buffer = new StringBuffer();
                if(!morpho_height.getText().toString().isEmpty() && !morpho_weight.getText().toString().isEmpty() && !morpho_body_length.getText().toString().isEmpty()&&!morpho_chest_circumference.getText().toString().isEmpty()&&!morpho_wing_span.getText().toString().isEmpty()&&!morpho_shank_length.getText().toString().isEmpty()){

                    morpho.append("["+morpho_height.getText().toString() +", ");
                    morpho.append(morpho_weight.getText().toString() +", ");
                    morpho.append(morpho_body_length.getText().toString() +", ");
                    morpho.append(morpho_chest_circumference.getText().toString() +", ");
                    morpho.append(morpho_wing_span.getText().toString() +", ");
                    morpho.append(morpho_shank_length.getText().toString()+"]");


                    ////DATABASE OPERATIONS
                    Cursor cursor_inventory = myDb.getAllDataFromReplacementInventory();
                    cursor_inventory.moveToFirst();

                    if (cursor_inventory.getCount() == 0) {

                    } else {
                        do {
                            Replacement_Inventory replacement_inventory = new Replacement_Inventory(cursor_inventory.getInt(0),cursor_inventory.getInt(1), cursor_inventory.getInt(2), cursor_inventory.getString(3),cursor_inventory.getString(4), cursor_inventory.getInt(5), cursor_inventory.getInt(6),cursor_inventory.getInt(7), cursor_inventory.getString(8), cursor_inventory.getString(9));
                            arrayListReplacementInventory.add(replacement_inventory);
                        } while (cursor_inventory.moveToNext());
                    }

                    Cursor cursor_pheno_morpho = myDb.getAllDataFromPhenoMorphoRecords();
                    cursor_pheno_morpho.moveToFirst();

                    if(cursor_pheno_morpho.getCount()==0){
                        Toast.makeText(getContext(),"No data from pheno and morpho records", Toast.LENGTH_SHORT).show();
                    }else{
                        do{
                            Replacement_PhenoMorphoRecords replacement_phenoMorphoRecords = new Replacement_PhenoMorphoRecords(cursor_pheno_morpho.getInt(0), cursor_pheno_morpho.getInt(1), cursor_pheno_morpho.getString(2), cursor_pheno_morpho.getString(3), cursor_pheno_morpho.getString(4), cursor_pheno_morpho.getString(5),  null, null);
                            arrayList_temp.add(replacement_phenoMorphoRecords);
                        }while (cursor_pheno_morpho.moveToNext());

                    }
                    //get inventory given the tag
                    for (int i = 0; i<arrayListReplacementInventory.size();i++){
                        if (arrayListReplacementInventory.get(i).getReplacement_inv_replacement_tag().equals(replacement_inv_tag)){
                            arrayListReplacementInventory1.add(arrayListReplacementInventory.get(i));

                        }
                    }


                    String morphos  = morpho.toString();

                    inv_id = arrayListReplacementInventory1.get(0).getId();


                    boolean isInserted2 = myDb.insertPhenoMorphoRecords(pheno_sex, pheno_tag, pheno_record, morphos, pheno_date, null);
                    if(isInserted2){
                        Cursor cursor = myDb.getDataFromPhenoMorphoValuesWhere(pheno_sex, pheno_tag,pheno_record,morphos,pheno_date);
                        cursor.moveToFirst();
                        id_0 = cursor.getInt(0);
                    }

                    if(isNetworkAvailable()) {


                        RequestParams requestParams = new RequestParams();
                        requestParams.add("gender", pheno_sex);
                        requestParams.add("tag", pheno_tag);
                        requestParams.add("phenotypic", pheno_record);
                        requestParams.add("morphometric", morphos);
                        requestParams.add("date_collected", pheno_date);
                        requestParams.add("deleted_at", null);


                        API_addPhenoMorphoValues(requestParams);

                    }

                       // buffer.append(cursor.getInt(0)+"\n");
                        boolean isInserted1 = myDb.insertPhenoMorphos(inv_id, null, id_0, null);
               /*         if(isNetworkAvailable()) {


                            RequestParams requestParams = new RequestParams();
                            requestParams.add("replacement_inventory_id", inv_id.toString());
                            requestParams.add("breeder_inventory_id", null);
                            requestParams.add("values_id", id_0.toString());
                            requestParams.add("deleted_at", null);


                            API_addPhenoMorphos(requestParams);

                        }
*/
                        if (isInserted1 != true){
                            Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                        }




                    if(isInserted2 == true){
                        Toast.makeText(getContext(), "Added to database", Toast.LENGTH_SHORT).show();
                        getDialog().dismiss();
                    }else{
                        Toast.makeText(getContext(), "Error adding to database", Toast.LENGTH_SHORT).show();

                    }

                    //showMessage("Pheno", buffer.toString());




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
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) this.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    private void API_addPhenoMorphos(RequestParams requestParams){
        APIHelper.addPhenoMorphos("addPhenoMorphos", requestParams, new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response){
            //    Toast.makeText(getContext(), "Successfully added to web", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonResponse, Object response){

                Toast.makeText(getContext(), "Failed to add to web", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable{
                return null;
            }
        });
    }
    private void API_addPhenoMorphoValues(RequestParams requestParams){
        APIHelper.addPhenoMorphoValues("addPhenoMorphoValues", requestParams, new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response){
//                Toast.makeText(getContext(), "Successfully added to web", Toast.LENGTH_SHORT).show();
                RequestParams requestParams = new RequestParams();
                requestParams.add("replacement_inventory_id", inv_id.toString());
                requestParams.add("breeder_inventory_id", null);
                requestParams.add("values_id", id_0.toString());
                requestParams.add("deleted_at", null);


                API_addPhenoMorphos(requestParams);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonResponse, Object response){

                Toast.makeText(getContext(), "Failed to add to web", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable{
                return null;
            }
        });
    }
}
