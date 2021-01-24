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

    private ImageView closeTextBtn;
    private Button next;
    private EditText writereason;
    private RadioButton radioexchange , radioreturns;
    private RadioGroup radioGroup;
   private RadioButton radioButton;
    private ImageView productImage;
    private NumberPicker nexre ;
    private TextView txtMsg1 , txtre, txtselect , txtradio ,select ;
    private TextView productPrice,productName , productBrand , numberquantity;
    private String productID = "";
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private DatabaseReference mUserDatabase = FirebaseDatabase.getInstance().getReference();
    FirebaseUser currentUser = mAuth.getCurrentUser();
    private String[] pickerVals;




    private String radio = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchange_and_returns);


        closeTextBtn = (ImageView) findViewById(R.id.close);
        closeTextBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
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


        productID = getIntent().getStringExtra("pid");


        productImage = (ImageView) findViewById(R.id.product_image_details);
        productPrice = (TextView) findViewById(R.id.product_price_details);

        productName = (TextView) findViewById(R.id.product_name_details);
        productBrand = (TextView) findViewById(R.id.product_brand_details);

        numberquantity = (TextView) findViewById(R.id.tv);


        productID=getIntent().getStringExtra("pid");



        getProductDetails(productID);
        addingToCartList();



    }


    @Override
    protected void onStart() {
        super.onStart();


    }

    private void addingToCartList() {

        if (currentUser != null) {
            String saveCurrentTime, saveCurrentDate;
            Calendar calForDate = Calendar.getInstance();
            SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
            saveCurrentDate = currentDate.format(calForDate.getTime());

            SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
            saveCurrentTime = currentTime.format(calForDate.getTime());


            final DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("Cart List");

            final HashMap<String, Object> cartMap = new HashMap<>();
            cartMap.put("pid", productID);
            cartMap.put("pname", productName.getText().toString());
            cartMap.put("price", productPrice.getText().toString());

            cartMap.put("brand", productBrand.getText().toString());
            // cartMap.put("pquantity",productQuantity.getText().toString());
            cartMap.put("date", saveCurrentDate);
            cartMap.put("time", saveCurrentTime);
            cartMap.put("numberquantity", numberquantity.getText().toString());
            cartMap.put("discount", "");


            cartListRef.child("User View").child(currentUser.getUid())
                    .child("Products").child(productID).updateChildren(cartMap)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                cartListRef.child("Admin View").child(currentUser.getUid()).child("Products")
                                        .child(productID).updateChildren(cartMap)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {

                                                    Toast.makeText(ExchangeAndReturnsActivity.this, "Added To Cart List", Toast.LENGTH_SHORT).show();
                                                    Intent intent = new Intent(ExchangeAndReturnsActivity.this, HomeActivity.class);
                                                    startActivity(intent);
                                                }
                                            }
                                        });

                            }


                        }
                    });

        }
        else
            Toast.makeText(this, "you must login ", Toast.LENGTH_SHORT).show();



    }






    private void getProductDetails(String productID) {
        final DatabaseReference productsRef= FirebaseDatabase.getInstance().getReference().child("Products");
        productsRef.child(productID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    Products products = dataSnapshot.getValue(Products.class);
                    Cart cart = dataSnapshot.getValue(Cart.class);


                    productName.setText(products.getPname());

                    productBrand.setText(products.getBrand());
//                    productQuantity.setText(products.getPquantity());
                    productPrice.setText(products.getPrice());

                    Picasso.get().load(products.getImage()).into(productImage);


                } }

            @Override
            public void onCancelled( DatabaseError databaseError) {


            }
        });
    }



}
