package com.example.elecshopping;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.elecshopping.Model.Cart;
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

public class ChooseItemActivity extends AppCompatActivity {


    private ImageView closeTextBtn;
    private Button next;

    private NumberPicker np ;
    private ElegantNumberButton numberButton;
    private TextView productPrice , exorre;
    EditText writedown;
    private String productID = "", state = "Normal";
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private DatabaseReference mUserDatabase = FirebaseDatabase.getInstance().getReference();
    FirebaseUser currentUser = mAuth.getCurrentUser();
    int count = 0 ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_item);

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

        next = (Button) findViewById(R.id.next);

        productPrice = (TextView) findViewById(R.id.product_price_details);

        writedown = (EditText) findViewById(R.id.writereason);
        exorre = (TextView) findViewById(R.id.textnumb);
        np = (NumberPicker) findViewById(R.id.np);


        productID=getIntent().getStringExtra("pid");





        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addingToCartList();


                    addingToCartList();

            }
        });

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



            np  = new NumberPicker(this);
            final String[] arrayString= new String[]{"Exchange","Returns"};
            np.setDisplayedValues(arrayString);
            np.setWrapSelectorWheel(true);
            np.setMinValue(0);
            np.setMaxValue(arrayString.length-1);

            np.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPicker picker, int oldVal, final int newVal) {

                    String newvalue = String.valueOf(newVal);
                    exorre.setText(newvalue);


                    if (currentUser != null) {


                        np.setVisibility(View.VISIBLE);

                    } else

                        np.setVisibility(View.GONE);

                }});




            final DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("Cart List");

            final HashMap<String, Object> cartMap = new HashMap<>();
            cartMap.put("pid", productID);
            cartMap.put("reason", writedown.getText().toString());
                    cartMap.put("exorre",exorre.getText().toString());

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

                                                    Toast.makeText(ChooseItemActivity.this, "Added To Admin List", Toast.LENGTH_SHORT).show();
                                                    Intent intent = new Intent(ChooseItemActivity.this, HomeActivity.class);
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







}
