package com.example.cholomanglicmot.nativechickenandduck.GenerationsAndLinesDirectory;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.cholomanglicmot.nativechickenandduck.APIHelper;
import com.example.cholomanglicmot.nativechickenandduck.DatabaseHelper;
import com.example.cholomanglicmot.nativechickenandduck.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.Header;

public class CreateGenerationDialog extends DialogFragment {

    private static final String TAG = "CreateGenerationDialog";
    private EditText mInput_generation_number,mInput_generation_status;
    private RadioGroup radioGroup;
    private RadioButton radioButton,radioButton2,radioButton3;
    public RecyclerView recyclerView;
    RecyclerView.Adapter recycler_adapter;

    private EditText mInput_pen_capacity;
    private Button mActionOk;
    DatabaseHelper myDb;
    String generation_number;
    Context context;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_create_generation, container, false);
        mActionOk = view.findViewById(R.id.action_ok);
        mInput_generation_number = view.findViewById(R.id.generation_no);
        context = getActivity().getApplicationContext();

        myDb = new DatabaseHelper(getContext());

        mActionOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(!mInput_generation_number.getText().toString().isEmpty()){
                    generation_number = String.format("%04d", Integer.parseInt(mInput_generation_number.getText().toString()));

                    FirebaseAuth mAuth;

                    mAuth = FirebaseAuth.getInstance();

                    FirebaseUser user = mAuth.getCurrentUser();

                    String name = user.getDisplayName();

                    String email = user.getEmail();

                    Uri photo = user.getPhotoUrl();

                    Integer farm_id =0;
                    Cursor cursor_farm_id = myDb.getFarmIDFromUsers(email);
                    cursor_farm_id.moveToFirst();
                    if(cursor_farm_id.getCount() != 0){
                        farm_id = cursor_farm_id.getInt(0);
                    }

                    Integer is_active = 1;
                    boolean isInserted = myDb.insertDataGeneration(
                            farm_id,
                            generation_number,
                            Integer.parseInt(mInput_generation_number.getText().toString()),
                            is_active
                    );

                    if(isInserted == true){
                        Toast.makeText(getActivity(),"Generation added to database", Toast.LENGTH_SHORT).show();
                        Intent intent_generation = new Intent(getActivity(), CreateGenerationsAndLines.class);
                        startActivity(intent_generation);

                        getDialog().dismiss();
                    }else{
                       // Toast.makeText(getActivity(),"Generation not added to database", Toast.LENGTH_SHORT).show();
                    }
                    getDialog().dismiss();
                }else{
                    Toast.makeText(getActivity(), "Please fill any empty fields", Toast.LENGTH_SHORT).show();
                }



            }

        });

        return view;
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}