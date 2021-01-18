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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.elecshopping.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class AdminAddProductsActivity extends AppCompatActivity {


    private String pcategoryName, pdescription, pprice, pname,psaveCurrentDate, psaveCurrentTime,pbrand , ppaymentmethod, pdeltime, pdelfee, pqnt;
    private Button addNewProductButton;
    private ImageView inputProductImage;
    private EditText inputProductName, inputProductDescription, inputProductPrice, inputProductqnt
            , inputProductbrand, inputProductpaymentmethod , inputProductdelfee, inputProductdeltime ;
    private static final int GalleryPick = 1;
    private Uri pimageUri;
    private String pproductRandomKey,pdownLoadImageUrl;
    private StorageReference productImagesRef;
    private DatabaseReference productsRef;
    private ProgressDialog loadingBar;
    private ImageView closeTextBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_products);
        closeTextBtn = (ImageView) findViewById(R.id.close);
        closeTextBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });

        productImagesRef = FirebaseStorage.getInstance().getReference().child("Product Images");
        productsRef = FirebaseDatabase.getInstance().getReference().child("Products");

        addNewProductButton = (Button) findViewById(R.id.adminadd_new_product);
        inputProductImage = (ImageView) findViewById(R.id.adminproduct_image);
        inputProductName = (EditText) findViewById(R.id.adminproduct_name);
        inputProductDescription = (EditText) findViewById(R.id.adminproduct_description);
        inputProductPrice = (EditText) findViewById(R.id.adminproduct_price);
        inputProductqnt= (EditText) findViewById(R.id.adminproduct_qnt);
        inputProductpaymentmethod= (EditText) findViewById(R.id.adminpaymentmethod);
        inputProductdelfee= (EditText) findViewById(R.id.admindeliveryfee);
        inputProductbrand= (EditText) findViewById(R.id.adminproduct_brand);
        inputProductdeltime= (EditText) findViewById(R.id.admindeliverytime);

        loadingBar = new ProgressDialog(this);


        inputProductImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });

        addNewProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                validateProductData();

            }
        });


    }

    private void openGallery() {

        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, GalleryPick);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode,  Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GalleryPick && resultCode == RESULT_OK && data != null) {

            pimageUri = data.getData();
            inputProductImage.setImageURI(pimageUri);
        }
    }

    private void validateProductData() {

        pdescription = inputProductDescription.getText().toString();
        pprice = inputProductPrice.getText().toString();
        pname = inputProductName.getText().toString();
        pbrand = inputProductbrand.getText().toString();
        pqnt = inputProductqnt.getText().toString();
        ppaymentmethod = inputProductpaymentmethod.getText().toString();
        pdelfee = inputProductdelfee.getText().toString();
        pdeltime = inputProductdeltime.getText().toString();

        if (pimageUri == null) {

            Toast.makeText(this, "Product image is mandatory", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(pdescription)) {

            Toast.makeText(this, "Please write product description", Toast.LENGTH_SHORT).show();


        } else if (TextUtils.isEmpty(pprice)) {

            Toast.makeText(this, "Please write product price", Toast.LENGTH_SHORT).show();


        } else if (TextUtils.isEmpty(pname)) {

            Toast.makeText(this, "Please write product name", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(pbrand)) {

            Toast.makeText(this, "Please write product brand", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(pdelfee)) {

            Toast.makeText(this, "Please write product delivery fee", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(pdeltime)) {

            Toast.makeText(this, "Please write product delivery time", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(ppaymentmethod)) {

            Toast.makeText(this, "Please write product payment method ", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(pqnt)) {

            Toast.makeText(this, "Please write product quantity", Toast.LENGTH_SHORT).show();
        }

        else{
            storeProductInformation();
        }
    }

    private void storeProductInformation() {

        loadingBar.setTitle("Add New Product");
        loadingBar.setMessage("Dear Admin , Please wait while we are adding the new product.");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        Calendar calender = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd,yyyy");
        psaveCurrentDate = currentDate.format(calender.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        psaveCurrentTime = currentTime.format(calender.getTime());

        pproductRandomKey = psaveCurrentDate + psaveCurrentTime;

        final StorageReference filePath = productImagesRef.child(pimageUri.getLastPathSegment() + pproductRandomKey + ".jpg");
        final UploadTask uploadTask = filePath.putFile(pimageUri);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String message = e.toString();
                Toast.makeText(AdminAddProductsActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(AdminAddProductsActivity.this, "Product Image uploaded Successfully", Toast.LENGTH_SHORT).show();

                Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()){
                            throw task.getException();

                        }

                        pdownLoadImageUrl = filePath.getDownloadUrl().toString();
                        return  filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {

                        if (task.isSuccessful()){
                            pdownLoadImageUrl = task.getResult().toString();
                            Toast.makeText(AdminAddProductsActivity.this, "got the Product image Url Successfully", Toast.LENGTH_SHORT).show();

                            saveProductInfoToDatabase();
                        }

                    }
                });
            }
        });


    }

    private void saveProductInfoToDatabase() {

        HashMap<String, Object> productMap  = new HashMap<>();
        productMap.put("pid" , pproductRandomKey);
        productMap.put("pdate" , psaveCurrentDate);
        productMap.put("ptime" , psaveCurrentTime);
        productMap.put("pdescription" , pdescription);
        productMap.put("pimage" , pdownLoadImageUrl);
        productMap.put("pcategory" , pcategoryName);
        productMap.put("pprice" , pprice);
        productMap.put("pname" , pname);
        productMap.put("pqnt" , pqnt);
        productMap.put("pbrand" , pbrand);
        productMap.put("ppayment_method" , ppaymentmethod);
        productMap.put("pdelivery_fee" , pdelfee);
        productMap.put("pdelovery_time" , pdeltime);

        productsRef.child(pname).updateChildren(productMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Intent intent = new Intent(AdminAddProductsActivity.this, AdminHomeActivity.class);
                            startActivity(intent);
                            loadingBar.dismiss();
                            Toast.makeText(AdminAddProductsActivity.this, "Product is added successfully", Toast.LENGTH_SHORT).show();
                        }

                        else{
                            loadingBar.dismiss();
                            String message = task.getException().toString();
                            Toast.makeText(AdminAddProductsActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

}