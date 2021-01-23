package com.example.elecshopping;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.elecshopping.Admin.AdminMaintainProductsActivity;
import com.example.elecshopping.Admin.AdminloginActivity;
import com.example.elecshopping.Model.Categories;
import com.example.elecshopping.Model.Products;
import com.example.elecshopping.ViewHolder.CategoryViewHolder;
import com.example.elecshopping.ViewHolder.ProductViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.snapshot.Index;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.util.ArrayList;

import io.paperdb.Paper;

public class HomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{


///

    private DatabaseReference ProductsRef;
    private RecyclerView recyclerView;
    private String type="";
    RecyclerView.LayoutManager layoutManager;
    ProgressDialog progressDialog;

   // this for hide addperson in home activity
    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;
    private ImageView person , language;



    @NonNull
    @Override
    public LayoutInflater getLayoutInflater() {
        return super.getLayoutInflater();
    }

    /// this for addpesron
    private Context mContext;
    private Activity mActivity;
    private RelativeLayout mRelativeLayout;
    private PopupWindow mPopupWindow;

    /// this for slide show
    private int[] mImages = new int[]{
            R.drawable.sli1,
            R.drawable.sli2,
            R.drawable.sli3,
            R.drawable.sli4,
    };

    //// hay lt3ref navigation
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        progressDialog = new ProgressDialog(HomeActivity.this);


        person = (ImageView) findViewById(R.id.person);
        language = (ImageView) findViewById(R.id.language);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        if ( currentUser!=null){
            person.setVisibility(View.GONE);
        }
        ///// hay ll navigatiom
        bottomNavigationView = findViewById (R.id.nav_view);
        setListeners ();


        /// this for put categories in home
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            type = getIntent().getExtras().get("Admin").toString();
        }

        ProductsRef = FirebaseDatabase.getInstance().getReference().child("Products");
        Paper.init(this);
        recyclerView = findViewById(R.id.recycler_menu);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        /// this for slideshow
        CarouselView carouselView = findViewById(R.id.carouselView);
        carouselView.setPageCount(mImages.length);
        carouselView.setImageListener(new ImageListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {
                imageView.setImageResource(mImages[position]);

            }
        });



/// this to switch language
        language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence options[] = new CharSequence[]
                            {

                                    "Arabic",
                                    "English"
                            };

                    AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
                    builder.setTitle("Language Options:");

                    builder.setItems(options, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int i) {
                            if (i==0){
                             //   Intent intent = new Intent(HomeActivity.this,ProductDetailsActivity.class);
                              //  intent.putExtra("pid",model.getPid());
                                //startActivity(intent);
                            }

                            if (i==1){
                                //Intent intent = new Intent(HomeActivity.this,ProductDetailsActivity.class);
                                //  intent.putExtra("pid",model.getPid());
                                //startActivity(intent);
                                }

                            }


                    });
                    builder.show();
                }

    });



        ///// this for login and register add person in discover app

        // Get the application context
        mContext = getApplicationContext();

        // Get the activity
        mActivity = HomeActivity.this;

        mRelativeLayout = (RelativeLayout) findViewById(R.id.r1);
        person.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Initialize a new instance of LayoutInflater service
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
                // Inflate the custom layout/view
                View customView = inflater.inflate(R.layout.activity_user_login_register, null);
                // Initialize a new instance of popup window
                mPopupWindow = new PopupWindow(
                        customView,
                        LinearLayoutCompat.LayoutParams.WRAP_CONTENT,
                        LinearLayoutCompat.LayoutParams.WRAP_CONTENT
                );

                // Set an elevation value for popup window
                // Call requires API level 21
                if (Build.VERSION.SDK_INT >= 21) {
                    mPopupWindow.setElevation(5.0f);
                }
                TextView addlog=(TextView)customView.findViewById(R.id.addlog);
                addlog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent myIntent = new Intent(HomeActivity.this,
                                LoginActivity.class);
                        startActivity(myIntent);
                        person.setVisibility(View.GONE);


                    }
                });

                TextView addregister=(TextView)customView.findViewById(R.id.addregister);
                addregister.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent myIntent = new Intent(HomeActivity.this,
                                registerActivity.class);
                        startActivity(myIntent);
                        person.setVisibility(View.GONE);

                    }
                });
                // Get a reference for the custom view close button
                ImageButton closeButton = (ImageButton) customView.findViewById(R.id.ib_close);
                closeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Dismiss the popup window
                        mPopupWindow.dismiss();
                    }
                });
                // show the popup window at the center location
                mPopupWindow.showAtLocation(mRelativeLayout, Gravity.CENTER, 0, 0);

            }

        });



    }



    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<Products> options=
                new FirebaseRecyclerOptions.Builder<Products>()
                        .setQuery(ProductsRef, Products.class).build();
        FirebaseRecyclerAdapter adapter=
                new FirebaseRecyclerAdapter<Products, ProductViewHolder>(options) {
                    @Override
                    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.product_items_layout, parent, false);
                        return new ProductViewHolder(view);

                    }
                    @Override
                    protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull final Products model) {

                        holder.txtProductName.setText(model.getPname());
                        holder.txtProductDesc.setText(model.getDescription());
                        holder.txtProductPrice.setText("Price = " +model.getPrice() +"$");
                        Picasso.get().load(model.getImage()).into(holder.imageView);

                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                if (type.equals("Admin")){
                                    Intent intent = new Intent(HomeActivity.this, AdminMaintainProductsActivity.class);
                                    intent.putExtra("pid", model.getPid());
                                    startActivity(intent);

                                }
                                else {

                                    Intent intent = new Intent(HomeActivity.this, ProductDetailsActivity.class);
                                    intent.putExtra("pid", model.getPid());
                                    startActivity(intent);
                                }
                            }
                        });


                    }


                };

        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }


    //// hay 3shan el navigation teshte8el
    private void setListeners() {
        bottomNavigationView.setOnNavigationItemSelectedListener (this);
    }

    public boolean onNavigationItemSelected(@NonNull MenuItem item) {


        //  If item is Checked make it unchecked
        if (item.isChecked ())
            item.setChecked (false);
        switch (item.getItemId ()) {
            case R.id.navigation_home:
                item.setChecked (true);
                break;

            case R.id.navigation_cart:

                mAuth = FirebaseAuth.getInstance();
                currentUser = mAuth.getCurrentUser();
                if ( currentUser!=null){

                    Intent intentcart = new Intent(HomeActivity.this, CartActivity.class);
                    startActivity(intentcart);
                    item.setChecked (true);
                }
                else
                    Toast.makeText(this, "You must first login or register ", Toast.LENGTH_SHORT).show();
                    item.setChecked (true);


                break;

            case R.id.navigation_index:
                Intent intent = new Intent(HomeActivity.this, IndexActivity.class);
                startActivity(intent);
                item.setChecked (true);
                break;

            case R.id.navigation_search:
                Intent intentsearch = new Intent(HomeActivity.this, SearchProductActivity.class);
                startActivity(intentsearch);
                item.setChecked (true);



                return true;

        }
        return false;
    }


}