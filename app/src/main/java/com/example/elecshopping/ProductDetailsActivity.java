package com.example.elecshopping;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.elecshopping.Model.Products;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;


public class ProductDetailsActivity extends AppCompatActivity {


    private ImageView closeTextBtn;
    private Button addToCartButton;
    private ImageView productImage;
    private ElegantNumberButton numberButton;
    private TextView productPrice, productDescription,productName , deliverytime, deliveryfee, paymentmethod, productqnt ;
    private String productID = "", state = "Normal";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

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

       addToCartButton = (Button) findViewById(R.id.pd_add_to_cart_button);
        numberButton = (ElegantNumberButton) findViewById(R.id.number_btn);
        productImage = (ImageView) findViewById(R.id.product_image_details);
        productPrice = (TextView) findViewById(R.id.product_price);
        productDescription = (TextView) findViewById(R.id.product_description);
        productqnt = (TextView) findViewById(R.id.product_qnt);
        deliveryfee = (TextView) findViewById(R.id.delivery_fee);
       deliverytime = (TextView) findViewById(R.id.delivery_time);
        paymentmethod = (TextView) findViewById(R.id.payment_method);


        productID=getIntent().getStringExtra("pid");


        getProductDetails(productID);

        addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addingToCartList();

                if (state.equals("AdminOrders Placed") || state.equals("AdminOrders Shipped")){
                    Toast.makeText(ProductDetailsActivity.this, "you can purchase more products once your order is purchased or confirmed.", Toast.LENGTH_LONG).show();
                }
                else{
                    addingToCartList();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        checkOrderState();
    }

    private void addingToCartList() {
        String saveCurrentTime,saveCurrentDate;
        Calendar calForDate =  Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calForDate.getTime());


      final DatabaseReference cartListRef= FirebaseDatabase.getInstance().getReference().child("Cart List");

            final HashMap<String, Object> cartMap = new HashMap<>();
            cartMap.put("pid",productID);
            cartMap.put("pname",productName.getText().toString());
            cartMap.put("pprice",productPrice.getText().toString());
            cartMap.put("pdate",saveCurrentDate);
            cartMap.put("ptime",saveCurrentTime);
            cartMap.put("pquantity",numberButton.getNumber());
        cartMap.put("ppayment_method",paymentmethod.getText().toString());
        cartMap.put("pdelivery_fee",deliveryfee.getText().toString());
        cartMap.put("pdelivery_time",deliverytime.getText().toString());
            cartMap.put("discount","");

            cartListRef.child("User View").child(Prevelent.currentonlineusers.getEmail())
                    .child("Products").child(productID).updateChildren(cartMap)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                cartListRef.child("Admin View").child(Prevelent.currentonlineusers.getEmail()).child("Products")
                                        .child(productID).updateChildren(cartMap)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(ProductDetailsActivity.this, "Added To Cart List", Toast.LENGTH_SHORT).show();
                                                    Intent intent = new Intent(ProductDetailsActivity.this, HomeActivity.class);
                                                    startActivity(intent);
                                                }
                                            }
                                        });

                            }


                        }
                    });


        }






    private void getProductDetails(String productID) {
        final DatabaseReference productsRef= FirebaseDatabase.getInstance().getReference().child("Products");
        productsRef.child(productID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()){
                    Products products = dataSnapshot.getValue(Products.class);

                    productName.setText(products.getPname());
                    productqnt.setText(products.getPqnt());
                   deliveryfee.setText(products.getPdelivfee());
                   deliverytime.setText(products.getPtime());
                   paymentmethod.setText(products.getPpaymentmethod());
                    productPrice.setText(products.getPprice());
                    productDescription.setText(products.getPdescription());
                    Picasso.get().load(products.getPimage()).into(productImage);



                }

            }

            @Override
            public void onCancelled( DatabaseError databaseError) {


            }
        });
    }
    private void checkOrderState(){
        final DatabaseReference ordersRef= FirebaseDatabase.getInstance().getReference().child("AdminOrders").child(Prevelent.currentonlineusers.getEmail());
               ordersRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange( DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()){


                           String shippingState = (String) dataSnapshot.child("state").getValue();

                                   if (shippingState != null) {
                                       if (shippingState.equals("shipped")) {

                                           state = "Order Shipped";


                                       } else if (shippingState.equals("not shipped")) {

                                           state = "Order Placed";


                                       }

                                   }

                        }

                   }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

    }

}
