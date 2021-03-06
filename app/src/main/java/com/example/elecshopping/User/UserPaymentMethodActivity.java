package com.example.elecshopping.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.elecshopping.Model.Policies;
import com.example.elecshopping.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserPaymentMethodActivity extends AppCompatActivity {

    private TextView txtpayment;
    private ImageView closeTextBtn;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private DatabaseReference mUserDatabase = FirebaseDatabase.getInstance().getReference();
    private String polID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_payment_method);

        closeTextBtn = (ImageView) findViewById(R.id.close);
        closeTextBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });
        polID = getIntent().getStringExtra("polid");

        txtpayment = (TextView) findViewById(R.id.texttxtpaymentmethod);

        polID=getIntent().getStringExtra("pid");


        getProductDetails(polID);

        final DatabaseReference productsRef= FirebaseDatabase.getInstance().getReference().child("Admin");
        productsRef.child("Policies").child("Payment_methods").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()){
                    Policies policies = dataSnapshot.getValue(Policies.class);
                    Log.d("mmmmmmmmm","lllllllllllllllllllll");
                    txtpayment.setText(policies.getPayment_methods());

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