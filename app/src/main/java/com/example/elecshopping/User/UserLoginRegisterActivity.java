package com.example.elecshopping.User;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.elecshopping.LoginActivity;
import com.example.elecshopping.R;
import com.example.elecshopping.registerActivity;

public class UserLoginRegisterActivity extends AppCompatActivity {



    private TextView login, register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login_register);

        login=(TextView) findViewById(R.id.addlog);
        register=(TextView) findViewById(R.id.addregister);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserLoginRegisterActivity.this, LoginActivity.class);
                startActivity(intent);

            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserLoginRegisterActivity.this, registerActivity.class);
                startActivity(intent);

            }
        });


    }
}