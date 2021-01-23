package com.example.elecshopping;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.elecshopping.Admin.AdminHomeActivity;
import com.example.elecshopping.Admin.AdminloginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import io.paperdb.Paper;

public class LoginActivity extends AppCompatActivity {

    private ImageView closeTextBtn , person;
    private EditText Email,Password;
    private Button login;
    private TextView AdminLink, NotAdminLink,forgotPassword;
    private FirebaseUser currentUser;//used to store current user of account
    private FirebaseAuth mAuth;//Used for firebase authentication
    private ProgressDialog loadingBar;
    private String parentDbName="Users";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        closeTextBtn = (ImageView) findViewById(R.id.close);
        closeTextBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });


        Email = (EditText) findViewById(R.id.email);
        Password = (EditText) findViewById(R.id.pass);
        login = (Button) findViewById(R.id.login);
        forgotPassword = (TextView) findViewById(R.id.forget);
        AdminLink= (TextView)findViewById(R.id.admin);
        mAuth = FirebaseAuth.getInstance();
        loadingBar = new ProgressDialog(this);
        currentUser = mAuth.getCurrentUser();
        Paper.init(this);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AllowUserToLogin();

            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetPasswordUser();
            }
        });




        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ForgetPaswwordActivity.class);
                startActivity(intent);

            }
        });

        AdminLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                parentDbName ="Admins";
                Intent intent = new Intent(LoginActivity.this, AdminloginActivity.class);
                startActivity(intent);
            }
        });




    }


    private void resetPasswordUser() {
        String email =Email.getText().toString().trim();
        if(TextUtils.isEmpty(email))
        {
            Toast.makeText(LoginActivity.this,"Please enter your email ",Toast.LENGTH_SHORT).show();
        }
        else
        {
            FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(LoginActivity.this, "Reset Email sent", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }


    private void AllowUserToLogin() {
        String email = Email.getText().toString().trim();
        String pwd = Password.getText().toString();
        if(TextUtils.isEmpty(email))
        {
            Toast.makeText(LoginActivity.this,"Please enter email",Toast.LENGTH_SHORT).show();
        }
        if(TextUtils.isEmpty(pwd))
        {
            Toast.makeText(LoginActivity.this,"Please enter password",Toast.LENGTH_SHORT).show();
        }
        else
        {
            //When both email and password are available log in to the account
            //Show the progress on Progress Dialog
            loadingBar.setTitle("Sign In");
            loadingBar.setMessage("Please wait");
            mAuth.signInWithEmailAndPassword(email,pwd)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())//If account login successful print message and send user to main Activity
                            {
                                sendToMainActivity();
                                Toast.makeText(LoginActivity.this,"Welcome, Enjoy Shopping ",Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                            }
                            else//Print the error message incase of failure
                            {
                                String msg = task.getException().toString();
                                Toast.makeText(LoginActivity.this,"Entered Email or Password is wrong",Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                            }
                        }
                    });
        }
    }

    protected void onStart() {
        //Check if user has already signed in if yes send to mainActivity
        //This to avoid signing in everytime you open the app.
        super.onStart();
        if(currentUser!=null)
        {
            sendToMainActivity();
        }
    }

    private void sendToMainActivity() {
        //This is to send user to MainActivity
        Intent  MainIntent = new Intent(LoginActivity.this,HomeActivity.class);
        startActivity(MainIntent);

    }
}
