package com.example.cholomanglicmot.nativechickenandduck.DashboardDirectory;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.cholomanglicmot.nativechickenandduck.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
//import com.squareup.picasso.Picasso;


public class LoginActivity extends AppCompatActivity{

    private SignInButton sign;
    private Button sign_out_button;
    ImageView imageView1;
    GoogleSignInClient mGoogleSignInClient;
    private int RC_SIGN_IN=1;
    private static final int REQ_CODE = 9001;
    private String TAG = "MainActivity";
    FirebaseAuth mAuth;
    static final int GOOGLE_SIGN = 123;
    ProgressBar progressBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sign = findViewById(R.id.sign_in_button);
        sign.setSize(SignInButton.SIZE_STANDARD);
        //sign_out_button = findViewById(R.id.sign_out_button);
        progressBar = findViewById(R.id.progress_bar);
        mAuth = FirebaseAuth.getInstance();
        imageView1 = findViewById(R.id.imageView1);


        /////////////////////////////////////
        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestIdToken(getString(R.string.default_web_client_id))
                .requestIdToken("536452586282-rd7d10idfckmaoi6brrc65645blii924.apps.googleusercontent.com")
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        ///////////////////////////////////

        if(isSignedIn()){
            Intent intent_main = new Intent(LoginActivity.this, DashBoardActivity.class);
            startActivity(intent_main);
        }

        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignInGoogle();

            }
        });

        if(mAuth.getCurrentUser() != null){
            FirebaseUser user = mAuth.getCurrentUser();
            updateUI(user);
        }
    }

    private boolean isSignedIn() {
        return GoogleSignIn.getLastSignedInAccount(this) != null;
    }

    void SignInGoogle(){

        progressBar.setVisibility(View.VISIBLE);
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, GOOGLE_SIGN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GOOGLE_SIGN){

            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try{

                GoogleSignInAccount account = task.getResult(ApiException.class);
                if(account != null){
                    firebaseAuthWithGoogle(account);
                }
            }catch (ApiException e){
                Toast.makeText(this, "Unable to verify", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {


        Log.d("TAG", "firebaseAuthWithGoogle: "+account.getId());
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if(task.isSuccessful()){
                        progressBar.setVisibility(View.INVISIBLE);
//                        Toast.makeText(this, "Signed-in Success", Toast.LENGTH_SHORT).show();

                        FirebaseUser user = mAuth.getCurrentUser();
                        Intent intent_main = new Intent(LoginActivity.this, DashBoardActivity.class);
                        startActivity(intent_main);
                        updateUI(user);

                    }else{
                        progressBar.setVisibility(View.INVISIBLE);

                        Toast.makeText(this, "Sign in Failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void updateUI(FirebaseUser user) {
        if(user != null){
            String name = user.getDisplayName();

            String email = user.getEmail();

            Uri photo = user.getPhotoUrl();
            sign.setVisibility(View.INVISIBLE);

        }else{
            sign.setVisibility(View.VISIBLE);
            sign_out_button.setVisibility(View.INVISIBLE);
        }
    }

    void Logout(){
        FirebaseAuth.getInstance().signOut();
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this,
                        task -> {
                            updateUI(null);
                        });
    }


}
