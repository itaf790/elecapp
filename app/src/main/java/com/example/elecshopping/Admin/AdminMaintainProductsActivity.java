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
                AlertDialog.Builder builder
                        = new AlertDialog
                        .Builder(AdminMaintainProductsActivity.this);

                // Set the message show for the Alert time
                builder.setMessage("Are you sure to delete this product?");

                // Set Alert Title
                builder.setTitle("Delete product !");

                // Set Cancelable false
                // for when the user clicks on the outside
                // the Dialog Box then it will remain show
                builder.setCancelable(false);

                // Set the positive button with yes name
                // OnClickListener method is use of
                // DialogInterface interface.

                builder
                        .setPositiveButton(
                                "Yes",
                                new DialogInterface
                                        .OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which)
                                    {

                                        // When the user click yes button
                                        // then app will close
                                        finish();
                                    }
                                });

                // Set the Negative button with No name
                // OnClickListener method is use
                // of DialogInterface interface.
                builder
                        .setNegativeButton(
                                "No",
                                new DialogInterface
                                        .OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which)
                                    {

                                        // If user click no
                                        // then dialog box is canceled.
                                        dialog.cancel();
                                    }
                                });

                // Create the Alert dialog
                AlertDialog alertDialog = builder.create();

                // Show the Alert Dialog box
                alertDialog.show();
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
        if(pName.equals("")){
            Toast.makeText(this,"Enter Product Name",Toast.LENGTH_LONG).show();

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
                    String pImage= dataSnapshot.child("image").getValue().toString();

                    name.setText(pName);
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