package com.example.elecshopping.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.elecshopping.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class AdminDiscountActivity extends AppCompatActivity {



    private ImageView closeTextBtn;
    private EditText inputdiscount;
    private ProgressDialog loadingBar;
    private Button Continue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_discount);

        closeTextBtn = (ImageView) findViewById(R.id.close);
        closeTextBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });

        Continue=(Button)findViewById(R.id.excon);
        inputdiscount=(EditText) findViewById(R.id.edittxtdiscountallproducts);



        loadingBar= new ProgressDialog(this);

        ////// had firebase ll login and signin
        Continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddingDiscount();
            }
        });

    }



    private void AddingDiscount() {
        String overdiscount= inputdiscount.getText().toString() ;

        if (TextUtils.isEmpty(overdiscount)){
            Toast.makeText(this, "Please write your discount", Toast.LENGTH_SHORT).show();
        }

        else{

            loadingBar.setTitle("Adding Discount");
            loadingBar.setMessage("please wait");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            adddiscount(overdiscount);
        }

    }

    private void adddiscount(final String overdiscount) {
        final DatabaseReference RootRef;
        RootRef= FirebaseDatabase.getInstance().getReference();
        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (!(snapshot.child("Admin").child(overdiscount).exists())) {
                    HashMap<String, Object> userdataMap= new HashMap<>();
                    userdataMap.put("overdiscount",overdiscount);


                    RootRef.child("Admin").child("Policies").child("overdiscount").updateChildren(userdataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()){
                                Toast.makeText(AdminDiscountActivity.this, "Congratulations...", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();

                                Intent intent = new Intent(AdminDiscountActivity.this,AdminHomeActivity.class);
                                startActivity(intent);
                            }

                            else {
                                loadingBar.dismiss();
                                Toast.makeText(AdminDiscountActivity.this, "Network Error: Please try again  ", Toast.LENGTH_SHORT).show();

                            }


                        }
                    });

                } else {
                    Toast.makeText(AdminDiscountActivity.this, "This already exists", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    Toast.makeText(AdminDiscountActivity.this, "Please try again", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AdminDiscountActivity.this, AdminHomeActivity.class);
                    startActivity(intent);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}