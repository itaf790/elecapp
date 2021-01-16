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

public class AdminAddCategoryActivity extends AppCompatActivity {

    private String cname;
    private Button addNewCategoryButton;
    private ImageView inputCategoryImage;
    private EditText inputCategoryName;
    private static final int GalleryPick = 1;
    private Uri imageUri;
    private String productRandomKey,downLoadImageUrl;
    private StorageReference categoryImagesRef;
    private DatabaseReference categoryRef;
    private ProgressDialog loadingBar;
    private ImageView closeTextBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_category);


        closeTextBtn = (ImageView) findViewById(R.id.close);
        closeTextBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });


        categoryImagesRef = FirebaseStorage.getInstance().getReference().child("Categories Images");
        categoryRef = FirebaseDatabase.getInstance().getReference().child("Categories");

        addNewCategoryButton = (Button) findViewById(R.id.add_new_category);
        inputCategoryImage = (ImageView) findViewById(R.id.select_category_image);
        inputCategoryName = (EditText) findViewById(R.id.category_name);
        loadingBar = new ProgressDialog(this);


        inputCategoryImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });

        addNewCategoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                validateCategoryData();

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

            imageUri = data.getData();
            inputCategoryImage.setImageURI(imageUri);
        }
    }

    private void validateCategoryData() {

        cname = inputCategoryName.getText().toString();

        if (imageUri == null) {

            Toast.makeText(this, "Category image is mandatory", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(cname)) {

            Toast.makeText(this, "Please write category name", Toast.LENGTH_SHORT).show();


        }

        else{
            storeProductInformation();
        }
    }

    private void storeProductInformation() {

        loadingBar.setTitle("Add New Category");
        loadingBar.setMessage("Dear Admin , Please wait while we are adding the new category.");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();



        final StorageReference filePath = categoryImagesRef.child(imageUri.getLastPathSegment() + productRandomKey + ".jpg");
        final UploadTask uploadTask = filePath.putFile(imageUri);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String message = e.toString();
                Toast.makeText(AdminAddCategoryActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(AdminAddCategoryActivity.this, "Category Image uploaded Successfully", Toast.LENGTH_SHORT).show();

                Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()){
                            throw task.getException();

                        }

                        downLoadImageUrl = filePath.getDownloadUrl().toString();
                        return  filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {

                        if (task.isSuccessful()){
                            downLoadImageUrl = task.getResult().toString();
                            Toast.makeText(AdminAddCategoryActivity.this, "got the Category image Url Successfully", Toast.LENGTH_SHORT).show();

                            saveProductInfoToDatabase();
                        }

                    }
                });
            }
        });


    }

    private void saveProductInfoToDatabase() {

        HashMap<String, Object> productMap  = new HashMap<>();
        productMap.put("cid" , productRandomKey);
        productMap.put("image" , downLoadImageUrl);
        productMap.put("cname" , cname);

        categoryRef.child(cname).updateChildren(productMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Intent intent = new Intent(AdminAddCategoryActivity.this, AdminHomeActivity.class);
                            startActivity(intent);
                            loadingBar.dismiss();
                            Toast.makeText(AdminAddCategoryActivity.this, "Category is added successfully", Toast.LENGTH_SHORT).show();
                        }

                        else{
                            loadingBar.dismiss();
                            String message = task.getException().toString();
                            Toast.makeText(AdminAddCategoryActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

}