package com.example.elecshopping.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.elecshopping.CartActivity;
import com.example.elecshopping.Model.Cart;
import com.example.elecshopping.R;
import com.example.elecshopping.ViewHolder.CartViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class AdminUserProductsActivity extends AppCompatActivity {

    private RecyclerView productsList;
    RecyclerView.LayoutManager layoutManager;
    private String userID = "";
    private TextView txtTotalAmount  ;
    private DatabaseReference cartListRef;
    private ImageView closeTextBtn;
    private int overTotalAmount = 0 , overtotal=0;

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private DatabaseReference mUserDatabase = FirebaseDatabase.getInstance().getReference();
    FirebaseUser currentUser = mAuth.getCurrentUser();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_user_products);

        userID =  getIntent().getStringExtra("uid");
        productsList = findViewById(R.id.products_list);
        productsList.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        productsList.setLayoutManager(layoutManager);

        txtTotalAmount = (TextView) findViewById(R.id.order);



        cartListRef= FirebaseDatabase.getInstance().getReference()
                .child("Cart List").child("Admin View").child(userID).child("Products");

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Cart> options =
                new  FirebaseRecyclerOptions.Builder<Cart>()
                        .setQuery(cartListRef,Cart.class).build();

        FirebaseRecyclerAdapter<Cart, CartViewHolder> adapter = new FirebaseRecyclerAdapter<Cart, CartViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CartViewHolder cartViewHolder, int position, @NonNull Cart model) {
                cartViewHolder.txtProductQuantity.setText(" Product Quantity = " + model.getNumberquantity());
                cartViewHolder.txtProductPrice.setText("Product Price = $" + model.getPrice() + " $");
                cartViewHolder.txtProductName.setText(" Product Name: " + model.getPname());
                cartViewHolder.txtProductBrand.setText("Brand :  " + model.getBrand());
                cartViewHolder.txtProductTime.setText("Time: "+ model.getTime());
                cartViewHolder.txtProductDate.setText("Date:  "+ model.getDate());
                cartViewHolder.txtProductshipped.setText("Shipped Price =  $ "+ model.getDelivery_fee());




                int oneTypeTotalPrice = (Integer.valueOf(model.getPrice())) * Integer.valueOf(model.getNumberquantity());
                int oneTypeTotalShipped = (Integer.valueOf(model.getDelivery_fee())) ;
                overtotal = oneTypeTotalPrice + oneTypeTotalShipped;
                overTotalAmount = overTotalAmount + oneTypeTotalPrice +oneTypeTotalShipped;

                cartViewHolder.txtProducttotalprice.setText("Total Price =  $"+ oneTypeTotalPrice);
                cartViewHolder.txttotalamount.setText("Total Amount = $ "+ overtotal);


                final DatabaseReference cartListRef= FirebaseDatabase.getInstance().getReference().child("Cart List");

                final HashMap<String,Object> cartMap = new HashMap<>();
                cartMap.put("totalAmount", txtTotalAmount.getText().toString());
                cartListRef.child("User View").child(currentUser.getUid())
                        .child("totalAmount").updateChildren(cartMap)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    cartListRef.child("Admin View").child(currentUser.getUid()).child("totalAmount")
                                            .updateChildren(cartMap)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {

                                                        Toast.makeText(AdminUserProductsActivity.this, "Total Price = $ "+ overTotalAmount, Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });

                                }


                            }
                        });



            }

            @NonNull
            @Override
            public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_items_layout,parent,false);
                CartViewHolder holder = new CartViewHolder(view);
                return holder;

            }
        };

        productsList.setAdapter(adapter);
        adapter.startListening();
    }
}
