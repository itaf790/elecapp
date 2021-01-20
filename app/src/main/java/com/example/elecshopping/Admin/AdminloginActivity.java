package com.example.elecshopping.Admin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.elecshopping.ForgetPaswwordActivity;
import com.example.elecshopping.HomeActivity;
import com.example.elecshopping.LoginActivity;
import com.example.elecshopping.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class AdminloginActivity extends AppCompatActivity {

    private ImageView closeTextBtn;
    private EditText AdminEmail,AdminPassword;
    private Button login;
    private FirebaseAuth auth;//Used for firebase authentication
    private ProgressDialog loadingBar;
    private String parentDbName="Admin";
    private TextView forgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminlogin);
        closeTextBtn = (ImageView) findViewById(R.id.close);
        closeTextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        loadingBar = new ProgressDialog(this);
        loadingBar.setMessage("Loading.....");
        loadingBar.setCancelable(true);
        loadingBar.setCanceledOnTouchOutside(false);

        auth = FirebaseAuth.getInstance();

        AdminEmail = (EditText) findViewById(R.id.emailadmin);
        AdminPassword = (EditText) findViewById(R.id.passwordadmin);
        login = (Button) findViewById(R.id.loginadmin);
        forgotPassword = (TextView) findViewById(R.id.forget);

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetPasswordUser();
            }
        });




        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminloginActivity.this, ForgetPaswwordActivity.class);
                startActivity(intent);

            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = AdminEmail.getText().toString().trim();
                String pwd = AdminPassword.getText().toString();

                if(TextUtils.isEmpty(email))
                {
                    Toast.makeText(AdminloginActivity.this,"Please enter email ",Toast.LENGTH_SHORT).show();
                }
                if(TextUtils.isEmpty(pwd))
                {
                    Toast.makeText(AdminloginActivity.this,"Please enter password",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    //When both email and password are available log in to the account
                    //Show the progress on Progress Dialog
                    loadingBar.setTitle("Sign In");
                    loadingBar.setMessage("Please wait");
                    auth.signInWithEmailAndPassword(email,pwd)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful())//If account login successful print message and send user to main Activity
                                    {
                                        sendToHomeActivity();
                                        Toast.makeText(AdminloginActivity.this," welcome admin, you are logged in Succeessfully",Toast.LENGTH_SHORT).show();
                                        loadingBar.dismiss();
                                    }

                                    else//Print the error message incase of failure
                                    {
                                        String msg = task.getException().toString();
                                        loadingBar.dismiss();
                                        Toast.makeText(AdminloginActivity.this, "Wrong, Please try again", Toast.LENGTH_SHORT).show();
                                    }
                                }

                            });

                }
            }


            private void sendToHomeActivity() {
                //This is to send  to MainActivity
                Intent  HomeIntent = new Intent(AdminloginActivity.this, AdminHomeActivity.class);
                startActivity(HomeIntent);
            }


        });}

    private void resetPasswordUser() {
        String email =AdminEmail.getText().toString().trim();
        if(TextUtils.isEmpty(email))
        {
            Toast.makeText(AdminloginActivity.this,"Please enter your email ",Toast.LENGTH_SHORT).show();
        }
        else
        {
            FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(AdminloginActivity.this, "Reset Email sent", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }


}