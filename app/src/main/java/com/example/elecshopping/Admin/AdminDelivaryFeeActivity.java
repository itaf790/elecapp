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

public class AdminDelivaryFeeActivity extends AppCompatActivity {

    private ImageView closeTextBtn;
    private EditText inputdeliveryfee;
    private ProgressDialog loadingBar;
    private Button Continue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_delivary_fee);

        closeTextBtn = (ImageView) findViewById(R.id.close);
        closeTextBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });

        Continue=(Button)findViewById(R.id.recon);
        inputdeliveryfee=(EditText) findViewById(R.id.edittxtdeliveryfee);



        loadingBar= new ProgressDialog(this);

        ////// had firebase ll login and signin
        Continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Addingfee();
            }
        });

    }



    private void Addingfee() {
        String fee= inputdeliveryfee.getText().toString() ;

        if (TextUtils.isEmpty(fee)){
            Toast.makeText(this, "Please write your delivery fee", Toast.LENGTH_SHORT).show();
        }

        else{

            loadingBar.setTitle("Adding Delivery Fee");
            loadingBar.setMessage("please wait");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            addfee(fee);
        }

    }

    private void addfee(final String fee) {
        final DatabaseReference RootRef;
        RootRef= FirebaseDatabase.getInstance().getReference();
        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (!(snapshot.child("Admin").child(fee).exists())) {
                    HashMap<String, Object> userdataMap= new HashMap<>();
                    userdataMap.put("Delivery_fee",fee);
                    RootRef.child("Admin").child("Policies").child("Delivery_fee").updateChildren(userdataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()){
                                Toast.makeText(AdminDelivaryFeeActivity.this, "Congratulations...", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();

                                Intent intent = new Intent(AdminDelivaryFeeActivity.this,AdminHomeActivity.class);
                                startActivity(intent);
                            }

                            else {
                                loadingBar.dismiss();
                                Toast.makeText(AdminDelivaryFeeActivity.this, "Network Error: Please try again  ", Toast.LENGTH_SHORT).show();

                            }


                        }
                    });

                } else {
                    Toast.makeText(AdminDelivaryFeeActivity.this, "This already exists", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    Toast.makeText(AdminDelivaryFeeActivity.this, "Please try again", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AdminDelivaryFeeActivity.this, AdminHomeActivity.class);
                    startActivity(intent);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}