package com.example.elecshopping;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.elecshopping.Admin.AdminloginActivity;
import com.example.elecshopping.Model.Cart;
import com.example.elecshopping.Model.Policies;
import com.example.elecshopping.Model.Products;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class ExchangeAndReturnsActivity extends AppCompatActivity {


    private TextView txtname;
    private ImageView closeTextBtn;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private DatabaseReference mUserDatabase = FirebaseDatabase.getInstance().getReference();
    private String polID = "";
    FirebaseUser currentUser = mAuth.getCurrentUser();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchange_and_returns);


        closeTextBtn = (ImageView) findViewById(R.id.close);
        closeTextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });




        txtname = (TextView) findViewById(R.id.exrename);



        polID=getIntent().getStringExtra("pid");


        getProductDetails(polID);

        final DatabaseReference productsRef= FirebaseDatabase.getInstance().getReference().child("Cart List");
        productsRef.child("User View").child(currentUser.getUid()).child("Products").child(polID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()){
                    Products policies = dataSnapshot.getValue(Products.class);
                    txtname.setText(policies.getBrand());

                }

            }

            @Override
            public void onCancelled( DatabaseError databaseError) {
                throw databaseError.toException();

            }
        });


    }

    private void getProductDetails(String polID) {
    }
}