package com.example.elecshopping.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.gbksoft.countrycodepickerlib.CountryCodePicker;
import com.google.firebase.auth.FirebaseUser;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.elecshopping.R;
import com.example.elecshopping.registerActivity;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class AdminExchangeActivity extends AppCompatActivity {


    private ImageView closeTextBtn;
    private EditText inputexchange;
    private ProgressDialog loadingBar;
    private Button Continue;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_exchange);


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
        inputexchange=(EditText) findViewById(R.id.edittxtexchangepolicy);



        loadingBar= new ProgressDialog(this);

        ////// had firebase ll login and signin
        Continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddingExchange();
            }
        });

    }



    private void AddingExchange() {
        String exchange= inputexchange.getText().toString() ;

        if (TextUtils.isEmpty(exchange)){
            Toast.makeText(this, "Please write your exchange policy", Toast.LENGTH_SHORT).show();
        }

        else{

            loadingBar.setTitle("Adding Exchange Policy");
            loadingBar.setMessage("please wait");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            addexchange(exchange);
        }

    }

    private void addexchange(final String exchange) {
        final DatabaseReference RootRef;
        RootRef= FirebaseDatabase.getInstance().getReference();
        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (!(snapshot.child("Admin").child(exchange).exists())) {
                    HashMap<String, Object> userdataMap= new HashMap<>();
                    userdataMap.put("Exchange_policy",exchange);


                    RootRef.child("Admin").child("Policies").child("Exchange_policy").updateChildren(userdataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()){
                                Toast.makeText(AdminExchangeActivity.this, "Congratulations...", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();

                                Intent intent = new Intent(AdminExchangeActivity.this,AdminHomeActivity.class);
                                startActivity(intent);
                            }

                            else {
                                loadingBar.dismiss();
                                Toast.makeText(AdminExchangeActivity.this, "Network Error: Please try again  ", Toast.LENGTH_SHORT).show();

                            }


                        }
                    });

                } else {
                    Toast.makeText(AdminExchangeActivity.this, "This already exists", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    Toast.makeText(AdminExchangeActivity.this, "Please try again", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AdminExchangeActivity.this, AdminHomeActivity.class);
                    startActivity(intent);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}