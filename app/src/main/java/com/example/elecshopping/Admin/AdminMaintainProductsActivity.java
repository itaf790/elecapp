package com.example.elecshopping.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.elecshopping.Model.Cart;
import com.example.elecshopping.Model.Policies;
import com.example.elecshopping.Model.Products;
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
    private EditText price,description,name , deliverytime , deliveryfee, paymentmethod, discount, quantity, brand ;

    private TextView productPrice, productDescription,productName , productDeliverytime, productDeliveryfee, productPaymentmethod, productQuantity , productBrand , numberquantity;

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
        paymentmethod= findViewById(R.id.maintan_paymentmethod);
        deliveryfee= findViewById(R.id.maintan_deliveryfee);
        deliverytime= findViewById(R.id.maintan_deliverytime);
       // discount= findViewById(R.id.maintan_product_discount);
        brand= findViewById(R.id.maintan_product_brand);
        quantity= findViewById(R.id.maintan_product_qnt);
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
        String pDesc =description.getText().toString();
        String pPayment =paymentmethod.getText().toString();
       // String pDiscount =discount.getText().toString();
        String pDetime =deliverytime.getText().toString();
        String pDefee =deliveryfee.getText().toString();
        String pBrand =brand.getText().toString();
        String pQuantity =quantity.getText().toString();

        if(pName.equals("")){
            Toast.makeText(this,"Enter Product Name",Toast.LENGTH_LONG).show();
        }

        if(pPrice.equals("")){
            Toast.makeText(this,"Enter Product Price",Toast.LENGTH_LONG).show();
        }
        if(pDesc.equals("")){
            Toast.makeText(this,"Enter Product Description",Toast.LENGTH_LONG).show();
        }
        if(pDefee.equals("")){
            Toast.makeText(this,"Enter Product Deliver fee",Toast.LENGTH_LONG).show();
        }
        if(pDetime.equals("")){
            Toast.makeText(this,"Enter Product Delivery time",Toast.LENGTH_LONG).show();
        }
        if(pBrand.equals("")){
            Toast.makeText(this,"Enter Product Brand",Toast.LENGTH_LONG).show();
        }
      //  if(pDiscount.equals("")){ Toast.makeText(this,"Enter Product Discount",Toast.LENGTH_LONG).show(); }
        if(pPayment.equals("")){
            Toast.makeText(this,"Enter Product payment method ",Toast.LENGTH_LONG).show();
        }
        if(pQuantity.equals("")){
            Toast.makeText(this,"Enter Product quantity ",Toast.LENGTH_LONG).show();
        }

        else {
            final HashMap<String,Object> prodMap=new HashMap<>();
            prodMap.put("pid",productID);
            prodMap.put("pname",pName);
            prodMap.put("pquantity",pQuantity);
            prodMap.put("delivery_fee",pDefee);
            prodMap.put("delivery_time",pDetime);
            prodMap.put("payment_method",pPayment);
            prodMap.put("brand",pBrand);
           // prodMap.put("discount",pDiscount);
            prodMap.put("price",pPrice);
            prodMap.put("description",pDesc);
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
                    String pQuantity= dataSnapshot.child("pquantity").getValue().toString();
                    String pPayment= dataSnapshot.child("payment_method").getValue().toString();
                    String pDefee= dataSnapshot.child("delivery_fee").getValue().toString();
                    String pDetime= dataSnapshot.child("delivery_time").getValue().toString();
                   // String pDiscount= dataSnapshot.child("discount").getValue().toString();
                    String pBrand= dataSnapshot.child("brand").getValue().toString();
                    String pPrice= dataSnapshot.child("price").getValue().toString();
                    String pDescription= dataSnapshot.child("description").getValue().toString();
                    String pImage= dataSnapshot.child("image").getValue().toString();

                    name.setText(pName);
                    quantity.setText(pQuantity);
                    paymentmethod.setText(pPayment);
                    deliveryfee.setText(pDefee);
                    deliverytime.setText(pDetime);
                  //  discount.setText(pDiscount);
                    brand.setText(pBrand);
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