package com.example.elecshopping.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class AdminMaintainProductsActivity extends AppCompatActivity {


    private Button applyChangesBtn,deleteBtn;
    private EditText price,description,name , deliverytime, deliveryfee, paymentmethod, brand;
    private ImageView imageView;
    private String productID="";
    private DatabaseReference productRef;
    private ImageView closeTextBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_maintain_products);



        closeTextBtn = (ImageView) findViewById(R.id.close);
        closeTextBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });
        applyChangesBtn= findViewById(R.id.apply_changes_btn_mantain);
        price= findViewById(R.id.maintan_product_price);
        description= findViewById(R.id.maintan_product_description);
        name= findViewById(R.id.maintan_product_name);
        brand= findViewById(R.id.maintan_product_brand);
        deliveryfee= findViewById(R.id.maintan_deliveryfee);
        deliverytime= findViewById(R.id.maintan_deliverytime);
        paymentmethod= findViewById(R.id.maintan_paymentmethod);
        imageView= findViewById(R.id.product_image_mantain);
        deleteBtn= findViewById(R.id.delete_pdt_btn);

        productID=getIntent().getStringExtra("pid");
        productRef= FirebaseDatabase.getInstance().getReference().child("Products")
                .child(productID);

        displaySpecificProductInfo();

        applyChangesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                applyChanges();
            }
        });
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteThisProduct();
            }
        });

    }

    private void deleteThisProduct() {
        productRef.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(AdminMaintainProductsActivity.this,"Product deleted successfully",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AdminMaintainProductsActivity.this, AdminHomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void applyChanges() {
        String pName =name.getText().toString();
        String pPrice =price.getText().toString();
        String pDeliverytime =deliverytime.getText().toString();
        String pDeliveryfee =deliveryfee.getText().toString();
        String pPaymentmethod =paymentmethod.getText().toString();
        String pDesc =description.getText().toString();
        String pBrand =brand.getText().toString();


        if(pName.equals("")){
            Toast.makeText(this,"Enter Product Name",Toast.LENGTH_LONG).show();

        }
        if(pDeliveryfee.equals("")){
            Toast.makeText(this,"Enter Product delivery fee",Toast.LENGTH_LONG).show();

        }
        if(pDeliverytime.equals("")){
            Toast.makeText(this,"Enter Product Delivery time",Toast.LENGTH_LONG).show();

        }
        if(pPaymentmethod.equals("")){
            Toast.makeText(this,"Enter Product Payement method",Toast.LENGTH_LONG).show();

        }
        if(pBrand.equals("")){
            Toast.makeText(this,"Enter Product Brand",Toast.LENGTH_LONG).show();

        }

        if(pPrice.equals("")){
            Toast.makeText(this,"Enter Product Price",Toast.LENGTH_LONG).show();

        }
        if(pDesc.equals("")){
            Toast.makeText(this,"Enter Product Description",Toast.LENGTH_LONG).show();

        }
        else {
            final HashMap<String,Object> prodMap=new HashMap<>();
            prodMap.put("pid",productID);
            prodMap.put("pname",pName);
            prodMap.put("price",pPrice);
            prodMap.put("description",pDesc);
            prodMap.put("payment_method",pPaymentmethod);
            prodMap.put("delivery_fee",pDeliveryfee);
            prodMap.put("delivery_time",pDeliverytime);
            prodMap.put("brand",pBrand);
            productRef.updateChildren(prodMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(AdminMaintainProductsActivity.this,"Changes applied successfully",Toast.LENGTH_LONG).show();
                        Intent intent =new Intent(AdminMaintainProductsActivity.this,AdminHomeActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            });
        }




    }

    private void displaySpecificProductInfo() {
        productRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String pName= dataSnapshot.child("pname").getValue().toString();
                    String pPrice= dataSnapshot.child("price").getValue().toString();
                    String pDescription= dataSnapshot.child("description").getValue().toString();
                    String pPaymentmethod= dataSnapshot.child("payment_method").getValue().toString();
                    String pDeliverytime= dataSnapshot.child("delivery_time").getValue().toString();
                    String pDeliveryfee= dataSnapshot.child("delivery_fee").getValue().toString();
                    String pBrand= dataSnapshot.child("brand").getValue().toString();
                    String pImage= dataSnapshot.child("image").getValue().toString();

                    name.setText(pName);
                    brand.setText(pBrand);
                    paymentmethod.setText(pPaymentmethod);
                    deliverytime.setText(pDeliverytime);
                    deliveryfee.setText(pDeliveryfee);
                    price.setText(pPrice);
                    description.setText(pDescription);
                    Picasso.get().load(pImage).into(imageView);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }


        });
    }
}
