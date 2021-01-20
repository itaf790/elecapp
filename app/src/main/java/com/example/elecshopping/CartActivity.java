package com.example.elecshopping;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.elecshopping.Model.Cart;
import com.example.elecshopping.Model.Prevelent;
import com.example.elecshopping.Model.Users;
import com.example.elecshopping.ViewHolder.CartViewHolder;
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

import io.paperdb.Paper;

public class CartActivity extends AppCompatActivity {


    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private Button nextProcessBtn;
    private TextView txtTotalPrice, txtMsg1 ,txtTotalShipped ,txtTotalAmount  ;
    private int overTotalAmount = 0;
    private ImageView closeTextBtn;
    private ProgressDialog loadingBar;

    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    final String uid = currentUser.getUid();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        closeTextBtn = (ImageView) findViewById(R.id.close);
        closeTextBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });
        loadingBar= new ProgressDialog(this);
        Paper.init(this);

        recyclerView = findViewById(R.id.cart_list);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        nextProcessBtn = (Button) findViewById(R.id.cart_next);
        txtTotalPrice = (TextView) findViewById(R.id.total_price);
        txtTotalShipped = (TextView) findViewById(R.id.shipped_price);
        txtTotalAmount = (TextView) findViewById(R.id.total_amount);
        txtMsg1 = (TextView) findViewById(R.id.msg1);



        nextProcessBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    txtTotalAmount.setText("Total Amount = " + String.valueOf(overTotalAmount));

                } catch (NumberFormatException e){
                    return;
                }
//                txtTotalAmount.setText("Total Price = R" + String.valueOf(overTotalPrice));
                Intent intent = new Intent(CartActivity.this,PaymentActivity.class);
                intent.putExtra("Total Amount",String.valueOf(overTotalAmount));
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();
        overTotalAmount=0;
    }

    @Override
    protected void onStart() {
        super.onStart();
        checkOrderState();

        String UserPasswordKey= Paper.book().read(Prevelent.UserPasswordKey);

        String UserEmailKey= Paper.book().read(Prevelent.UserEmailKey);

        if (UserEmailKey!="" && UserPasswordKey!= ""){
            if (!TextUtils.isEmpty(UserEmailKey) && !TextUtils.isEmpty(UserPasswordKey)){
                AllowAccess(UserEmailKey,UserPasswordKey);

                loadingBar.setTitle("Already Loggen in");
                loadingBar.setMessage("please wait...");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();
            }

        }

        final DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("Cart List");
        FirebaseRecyclerOptions<Cart> options =
                new FirebaseRecyclerOptions.Builder<Cart>()
                        .setQuery(cartListRef.child("User View")
                                .child(uid)
                                .child("Products"),Cart.class).build();

        FirebaseRecyclerAdapter<Cart, CartViewHolder> adapter =
                new FirebaseRecyclerAdapter<Cart, CartViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull CartViewHolder cartViewHolder, int position, @NonNull final Cart model) {
                        try {

                            cartViewHolder.txtProductQuantity.setText("Quantity = " + model.getQuantity());
                            cartViewHolder.txtProductPrice.setText("Price = " + model.getPrice() + " $");
                            cartViewHolder.txtProductName.setText("Name: " + model.getPname());
                            cartViewHolder.txtProductBrand.setText("Brand " + model.getBrand());
                            cartViewHolder.txtProductTime.setText("Time: " + model.getTime());
                            cartViewHolder.txtProductDate.setText("Date: " + model.getDate());



                            int oneTypeTotalPrice = (Integer.valueOf(model.getPrice())) * Integer.valueOf(model.getQuantity());
                            int oneTypeTotalShipped = (Integer.valueOf(model.getDeliveryfee())) ;
                            overTotalAmount = oneTypeTotalShipped + oneTypeTotalPrice;
                        } catch(NumberFormatException e){
                            return;
                        }


                        cartViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                CharSequence options[] = new CharSequence[]
                                        {

                                                "Edit Quantity",
                                                "Delete from Cart"
                                        };

                                AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this);
                                builder.setTitle("Cart Options:");

                                builder.setItems(options, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int i) {
                                        if (i==0){
                                            Intent intent = new Intent(CartActivity.this,ProductDetailsActivity.class);
                                            intent.putExtra("pid",model.getPid());
                                            startActivity(intent);
                                        }

                                        if (i==1){
                                            cartListRef.child("User View")
                                                    .child(uid)
                                                    .child("Products")
                                                    .child(model.getPid()).removeValue()
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {

                                                            if (task.isSuccessful()){
                                                                Toast.makeText(CartActivity.this, "Item deleted successfully.", Toast.LENGTH_SHORT).show();
                                                                Intent intent = new Intent(CartActivity.this,HomeActivity.class);
                                                                startActivity(intent);
                                                            }

                                                        }
                                                    });
                                        }

                                    }
                                });
                                builder.show();
                            }
                        });





                    }

                    @NonNull
                    @Override
                    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_items_layout,parent,false);
                        CartViewHolder holder = new CartViewHolder(view);
                        return holder;
                    }
                };

        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    private void checkOrderState(){
        DatabaseReference ordersRef;
        Log.d("mmmmmm","mmmmmmmmmmmmmmmmmmmmmmmmmm");
        ordersRef= FirebaseDatabase.getInstance().getReference().child("AdminOrders")
                .child(uid);

        ordersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    String shippingState = (String) dataSnapshot.child("state").getValue();
                    String userName = (String) dataSnapshot.child("name").getValue();

                    if (shippingState != null){
                        if (shippingState.equals("shipped")){

                            txtTotalAmount.setText("Dear " + userName + "\n order is shipped successfully.");
                            recyclerView.setVisibility(View.GONE);
                            txtMsg1.setVisibility(View.VISIBLE);
                            txtMsg1.setText("Congratulations, your final order has been Shipped successfully.Soon you will receive your order by your door step ");
                            nextProcessBtn.setVisibility(View.GONE);
                            Toast.makeText(CartActivity.this, "You can purchase more products once you receive your first order", Toast.LENGTH_SHORT).show();

                        } else if (shippingState.equals("not shipped")){
                            txtTotalAmount.setText("Shipping State = not shipped");
                            recyclerView.setVisibility(View.GONE);
                            txtMsg1.setVisibility(View.VISIBLE);
                            nextProcessBtn.setVisibility(View.GONE);
                            Toast.makeText(CartActivity.this, "You can purchase more products once you receive your first order", Toast.LENGTH_SHORT).show();


                        }}

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }



    private void AllowAccess( final String email,  final String password) {
        final DatabaseReference RootRef;
        RootRef= FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.child("Users").child(email).exists()){
                    Users usersData= snapshot.child("Users").child(email).getValue(Users.class);
                    if (usersData.getEmail().equals(email))
                    {
                        if (usersData.getPassword().equals(password))
                        {
                            Toast.makeText(CartActivity.this, "Please wait, you are already looged in", Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();

                            Intent intent = new Intent(CartActivity.this, HomeActivity.class);
                            Prevelent.currentonlineusers=usersData;
                            startActivity(intent);
                        }

                        else {
                            loadingBar.dismiss();
                            Toast.makeText(CartActivity.this, "Password is incorrect", Toast.LENGTH_SHORT).show();
                        }


                    }

                }
                else {

                    Toast.makeText(CartActivity.this, "Account with this"+ email+"email do not exists", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}
