package com.example.elecshopping;

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

import com.example.elecshopping.Model.Policies;
import com.example.elecshopping.Model.Prevelent;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class RecivedActivity extends AppCompatActivity {


    private EditText nameEditText, phoneEditText, addressEditText, emailEditText;
    private Button confirmOrderBtn;
    private ImageView closeTextBtn;
    private TextView txttotalprice;
    private String totalAmount = "";

    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    final String uid = currentUser.getUid();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recived);




        confirmOrderBtn = (Button) findViewById(R.id.confirm_final_order);
        nameEditText = (EditText) findViewById(R.id.shipment_name);
        phoneEditText = (EditText) findViewById(R.id.shipment_phone_number);
        addressEditText = (EditText) findViewById(R.id.shipment_address);
        emailEditText = (EditText) findViewById(R.id.shipment_email);
        txttotalprice = (TextView) findViewById(R.id.txttotalprice);

        confirmOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Check();
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

        final DatabaseReference totalprice= FirebaseDatabase.getInstance().getReference().child("Cart List");
        totalprice.child("User View").child(currentUser.getUid()).child("totalAmount").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()){
                    Products product = dataSnapshot.getValue(Products.class);
                    txttotalprice.setText(product.getTotalAmount());

                }

            }

            @Override
            public void onCancelled( DatabaseError databaseError) {
                throw databaseError.toException();

            }
        });


    }

    private void Check() {
        if (TextUtils.isEmpty(nameEditText.getText().toString())){
            Toast.makeText(this, "Please provide your full name", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(phoneEditText.getText().toString())){
            Toast.makeText(this, "Please provide your phone number ", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(addressEditText.getText().toString())){
            Toast.makeText(this, "Please provide your address", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(emailEditText.getText().toString())){
            Toast.makeText(this, "Please provide your email ", Toast.LENGTH_SHORT).show();
        }

        else{
            ConfirmOrder();
        }

    }

    private void ConfirmOrder() {
        final String saveCurrentDate,saveCurrentTime;
        Calendar calForDate =  Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calForDate.getTime());



        final DatabaseReference ordersRef = FirebaseDatabase.getInstance().getReference().child("Orders")
                .child(currentUser.getUid());
        HashMap<String, Object> ordersMap = new HashMap<>();
        ordersMap.put("totalAmount",txttotalprice.getText().toString());
        ordersMap.put("name",nameEditText.getText().toString());
        ordersMap.put("phone",phoneEditText.getText().toString());
        ordersMap.put("address",addressEditText.getText().toString());
        ordersMap.put("email",emailEditText.getText().toString());
        ordersMap.put("date",saveCurrentDate);
        ordersMap.put("time",saveCurrentTime);
        ordersMap.put("state","not shipped");

        ordersRef.updateChildren(ordersMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful())
                {
                    FirebaseDatabase.getInstance().getReference("Cart List")
                            .child("User View")
                            .child(currentUser.getUid())
                            .removeValue()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(RecivedActivity.this, "your final order has been placed successfully", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(RecivedActivity.this,HomeActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            });
                }
            }
        });






    }
}
