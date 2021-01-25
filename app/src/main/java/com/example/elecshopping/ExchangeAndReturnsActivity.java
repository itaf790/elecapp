package com.example.elecshopping;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.elecshopping.Admin.AdminMaintainProductsActivity;
import com.example.elecshopping.Admin.AdminloginActivity;
import com.example.elecshopping.Model.Cart;
import com.example.elecshopping.Model.Policies;
import com.example.elecshopping.Model.Products;
import com.example.elecshopping.ViewHolder.CartViewHolder;
import com.example.elecshopping.ViewHolder.ProductViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
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
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import io.paperdb.Paper;

public class ExchangeAndReturnsActivity extends AppCompatActivity {


    private ImageView closeTextBtn;

    private DatabaseReference ProductsRef;
    private String productID = "";
    private RecyclerView recyclerView;
    private TextView txtMsg1;
    private String type="";
    RecyclerView.LayoutManager layoutManager;
    ProgressDialog progressDialog;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private DatabaseReference mUserDatabase = FirebaseDatabase.getInstance().getReference();
    FirebaseUser currentUser = mAuth.getCurrentUser();

    private ImageView productImage;



    @NonNull
    @Override
    public LayoutInflater getLayoutInflater() {
        return super.getLayoutInflater();
    }

    /// this for addpesron
    private Context mContext;
    private Activity mActivity;
    private RelativeLayout mRelativeLayout;
    private PopupWindow mPopupWindow;

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
        progressDialog = new ProgressDialog(ExchangeAndReturnsActivity.this);


        ///// hay ll navigatiom


        /// this for put categories in home
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            type = getIntent().getExtras().get("Admin").toString();
        }

        ProductsRef = FirebaseDatabase.getInstance().getReference().child("Products");
        Paper.init(this);
        recyclerView = findViewById(R.id.recycler_menu);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


    }


    @Override
    protected void onStart() {
        super.onStart();
        checkOrderState();

        final DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("Cart List");
        FirebaseRecyclerOptions<Cart> options =
                new FirebaseRecyclerOptions.Builder<Cart>()
                        .setQuery(cartListRef.child("User View")
                                .child(currentUser.getUid())
                                .child("Products"),Cart.class).build();



        FirebaseRecyclerAdapter<Cart, CartViewHolder> adapter =
                new FirebaseRecyclerAdapter<Cart, CartViewHolder>(options) {
                    @SuppressLint("SetTextI18n")
                    @Override
                    protected void onBindViewHolder(@NonNull CartViewHolder cartViewHolder, int position, @NonNull final Cart model) {
                        try {

                            cartViewHolder.txtProductQuantity.setText(" Product Quantity = " + model.getNumberquantity());
                            cartViewHolder.txtProductPrice.setText("Product Price = $" + model.getPrice());
                            cartViewHolder.txtProductName.setText(" Product Name: " + model.getPname());
                            cartViewHolder.txtProductBrand.setText("Brand :  " + model.getBrand());
                            cartViewHolder.txtProductDate.setText("Date:  "+ model.getDate());


                            cartViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                        Intent intent = new Intent(ExchangeAndReturnsActivity.this, ChooseItemActivity.class);
                                        intent.putExtra("pid", model.getPid());
                                        startActivity(intent);


                                }
                            });





                        } catch(NumberFormatException e){
                            return;
                        }






                    }

                    @NonNull
                    @Override
                    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ex_re_layout,parent,false);
                        CartViewHolder holder = new CartViewHolder(view);
                        return holder;
                    }
                };

        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }




    private void checkOrderState(){
        DatabaseReference ordersRef;
        ordersRef= FirebaseDatabase.getInstance().getReference().child("Orders")
                .child(currentUser.getUid());

        ordersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    String shippingState = (String) dataSnapshot.child("state").getValue();
                    String userName = (String) dataSnapshot.child("name").getValue();

                    if (shippingState != null){

                              recyclerView.setVisibility(View.GONE);
                              txtMsg1.setVisibility(View.VISIBLE);
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }






}
