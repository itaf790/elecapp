package com.example.elecshopping;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class registerActivity extends AppCompatActivity {

    private ImageView closeTextBtn;
    private FirebaseAuth mAuth; // used for firebase authentecation
    private EditText mName,mLastname,mPhone,mAddress ,mEmail, mPassword;

    private Button mRegister;
    private ProgressDialog mRegProgress;
    //Database Reference
    private DatabaseReference mUserDetails = FirebaseDatabase.getInstance().getReference();
    private ProgressDialog loadingBar;//Used to show the progress of the registration process


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        mRegProgress = new ProgressDialog(this);
        //User Details
        mName =( EditText) findViewById(R.id.name);
        mLastname = (EditText) findViewById(R.id.lastname);
        mPhone = (EditText) findViewById(R.id.phone);
        mAddress = (EditText) findViewById(R.id.address);
        mEmail = (EditText) findViewById(R.id.emailsign);
        mPassword = (EditText) findViewById(R.id.passwordsign);
        mRegister = (Button) findViewById(R.id.btncreate);
        loadingBar = new ProgressDialog(this);


        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = mName.getText().toString().trim();
                String lastname = mLastname.getText().toString().trim();
                String phone = mPhone.getText().toString().trim();
                String address =mAddress.getText().toString().trim();
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();


                if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(name) && !TextUtils.isEmpty(lastname) && !TextUtils.isEmpty(phone) && !TextUtils.isEmpty(address)){

                    mRegProgress.setTitle("Creating Account");
                    mRegProgress.setMessage("Please Wait! We are Processing");
                    mRegProgress.setCanceledOnTouchOutside(false);
                    mRegProgress.show();


                    createAccount(name,lastname,phone,address,email,password);

                }
                else{

                    Toast.makeText(registerActivity.this,"Please fill all field",Toast.LENGTH_LONG).show();

                }

            }
        });



        closeTextBtn = (ImageView) findViewById(R.id.close);
        closeTextBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });


    }

    private void createAccount(final String name, final String lastname, final String phone, final String address, final String email, final String password) {

        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(registerActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){

                            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                            String uid = currentUser.getUid();

                            mUserDetails.child("Users").child(uid).child("Email");

                            HashMap<String,String> userDetails = new HashMap<>();
                            userDetails.put("Name",name);
                            userDetails.put("Last_name",lastname);
                            userDetails.put("Phone_number",phone);
                            userDetails.put("Address",address);
                            userDetails.put("Email",email);
                            userDetails.put("Password",password);

                            mUserDetails.child("Users").child(uid).setValue(userDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    mRegProgress.dismiss();
                                    Toast.makeText(registerActivity.this,"Congratulations, your account has been created",Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(registerActivity.this, HomeActivity.class);
                                    startActivity(intent);

                                }
                            });


                        }
                        else {

                            mRegProgress.hide();
                            Toast.makeText(registerActivity.this,"Creating Account Failed",Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }



}
