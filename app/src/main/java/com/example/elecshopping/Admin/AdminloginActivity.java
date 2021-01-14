package com.example.elecshopping.Admin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = AdminEmail.getText().toString();
                final String password = AdminPassword.getText().toString();
                try {

                    if (password.length()> 0 && email.length()> 0){
                        loadingBar.show();
                        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(AdminloginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (!task.isSuccessful()){
                                    Toast.makeText(AdminloginActivity.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
                                    Log.v("error", task.getResult().toString());
                                } else{
                                    Toast.makeText(AdminloginActivity.this,"You logged in successfully",Toast.LENGTH_LONG).show();
                                    loadingBar.dismiss();
                                    Intent intent = new Intent(AdminloginActivity.this,AdminHomeActivity.class);
//                                    Users users = new Users(email,password);
//                                    Prevalent.currentOnLineUsers = users;
                                    startActivity(intent);

                                }

                                loadingBar.dismiss();
                                Toast.makeText(AdminloginActivity.this, "Password is incorrect", Toast.LENGTH_SHORT).show();

                            }
                        });
                    } else{
                        Toast.makeText(AdminloginActivity.this,"Account with this "+ email+" email do not exists",Toast.LENGTH_LONG).show();
                    }



                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

    }
}

