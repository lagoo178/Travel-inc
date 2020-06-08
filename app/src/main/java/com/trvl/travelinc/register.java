package com.trvl.travelinc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;
import java.util.Map;

public class    register extends AppCompatActivity {

    FirebaseAuth mauth;
    EditText inputNama, inputUsername,inputEmail, inputPassword, inputConfirm ;
    Button btnSignUp, SignIn;
    ProgressDialog progressDialog;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Get Firebase auth instance
        mauth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("users");

        btnSignUp = (Button) findViewById(R.id.signin);
        SignIn = (Button) findViewById(R.id.sign_in);
        inputNama = (EditText) findViewById(R.id.rnama);
        inputUsername = (EditText) findViewById(R.id.runame);
        inputEmail = (EditText) findViewById(R.id.remail);
        inputPassword = (EditText) findViewById(R.id.repass);
        inputConfirm = (EditText) findViewById(R.id.recopass);
        progressDialog = new ProgressDialog(register.this);
    }

    //-----REGISTER BUTTON IS PRESSED---
    public void buttonIsClicked(View view){

        if(view.getId()==R.id.signup){

            String displayname=inputUsername.getText().toString().trim();
            String email=inputEmail.getText().toString().trim();
            String password=inputPassword.getText().toString().trim();

            //----CHECKING THE EMPTINESS OF THE EDITTEXT-----
            if(displayname.equals("")){
                Toast.makeText(register.this, "Please Fill the name", Toast.LENGTH_SHORT).show();
                return;
            }

            if(email.equals("")){
                Toast.makeText(register.this, "Please Fill the email", Toast.LENGTH_SHORT).show();
                return ;
            }

            if(password.length()<6){
                Toast.makeText(register.this, "Password is too short", Toast.LENGTH_SHORT).show();
                return;
            }

            progressDialog.setTitle("Registering User");
            progressDialog.setMessage("Please wait while we are creating your account... ");
            progressDialog.setCancelable(false);
            progressDialog.setProgress(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();
            register_user(displayname,email,password);
        }
    }

    //-----REGISTERING THE NEW USER------
    private void register_user(final String displayname, String email, String password) {

        mauth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this,new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                //------IF USER IS SUCCESSFULLY REGISTERED-----
                if(task.isSuccessful()){

                    FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
                    final String uid=current_user.getUid();
                    String token_id = FirebaseInstanceId.getInstance().getToken();
                    Map userMap=new HashMap();
                    userMap.put("device_token",token_id);
                    userMap.put("name",displayname);
                    userMap.put("status","Hello World");
                    userMap.put("image","default");
                    userMap.put("thumb_image","default");
                    userMap.put("online","true");

                    mDatabase.child(uid).setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task1) {
                            if(task1.isSuccessful()){

                                progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(), "New User is created", Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(register.this,home.class);

                                //----REMOVING THE LOGIN ACTIVITY FROM THE QUEUE----
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();



                            }
                            else{

                                Toast.makeText(register.this, "YOUR NAME IS NOT REGISTERED... MAKE NEW ACCOUNT-- ", Toast.LENGTH_SHORT).show();

                            }

                        }
                    });


                }
                //---ERROR IN ACCOUNT CREATING OF NEW USER---
                else{
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "ERROR REGISTERING USER....", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
