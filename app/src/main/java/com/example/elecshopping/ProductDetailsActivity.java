package com.example.elecshopping;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
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


public class ProductDetailsActivity extends AppCompatActivity {


    private ImageView closeTextBtn;
    private Button addToCartButton;
    private ImageView productImage;
    private ElegantNumberButton numberButton;
    private TextView productPrice, productDescription,productName , productDeliverytime, productDeliveryfee, productPaymentmethod, productQuantity , productBrand ;
    private String productID = "", state = "Normal";

   private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private DatabaseReference mUserDatabase = FirebaseDatabase.getInstance().getReference();
    FirebaseUser currentUser = mAuth.getCurrentUser();

    private Context mContext;
    private Activity mActivity;
    private RelativeLayout mRelativeLayout;
    private PopupWindow mPopupWindow;



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
        productPrice = (TextView) findViewById(R.id.product_price_details);
        productDescription = (TextView) findViewById(R.id.product_description_details);
        productName = (TextView) findViewById(R.id.product_name_details);
        productBrand = (TextView) findViewById(R.id.product_brand_details);
        productDeliveryfee = (TextView) findViewById(R.id.delivery_fee_details);
        productDeliverytime = (TextView) findViewById(R.id.delivery_time_details);
        productQuantity = (TextView) findViewById(R.id.product_qnt_details);
        productPaymentmethod= (TextView) findViewById(R.id.payment_method_details);

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

       // checkOrderState();
    }

    private void addingToCartList() {
        String saveCurrentTime,saveCurrentDate;
        Calendar calForDate =  Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calForDate.getTime());


        final DatabaseReference cartListRef= FirebaseDatabase.getInstance().getReference().child("Cart List");

        final HashMap<String,Object> cartMap = new HashMap<>();
        cartMap.put("pid",productID);
        cartMap.put("pname",productName.getText().toString());
        cartMap.put("price",productPrice.getText().toString());
        cartMap.put("payment_method",productPaymentmethod.getText().toString());
        cartMap.put("delivery_time",productDeliverytime.getText().toString());
        cartMap.put("delivery_fee",productDeliveryfee.getText().toString());
        cartMap.put("brand",productBrand.getText().toString());
        cartMap.put("pquantity",productQuantity.getText().toString());
        cartMap.put("date",saveCurrentDate);
        cartMap.put("time",saveCurrentTime);
        cartMap.put("quantity",numberButton.getNumber());
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


///////////////////////////////////////////delete user view

    }






    private void getProductDetails(String productID) {
        final DatabaseReference productsRef= FirebaseDatabase.getInstance().getReference().child("Products");
        productsRef.child(productID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()){
                    Products products = dataSnapshot.getValue(Products.class);

                    productName.setText(products.getPname());
                    productPaymentmethod.setText(products.getPayment_method());
                    productDeliveryfee.setText(products.getDelivery_fee());
                    productDeliverytime.setText(products.getDelivery_time());
                    productBrand.setText(products.getBrand());
                    productQuantity.setText(products.getPquantity());
                    productPrice.setText(products.getPrice());
                    productDescription.setText(products.getDescription());
                    Picasso.get().load(products.getImage()).into(productImage);



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
