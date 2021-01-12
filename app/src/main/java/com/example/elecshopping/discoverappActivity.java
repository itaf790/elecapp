package com.example.elecshopping;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

public class discoverappActivity extends AppCompatActivity {


    private int[] mImages = new int[]{
            R.drawable.apple,
            R.drawable.elec,
            R.drawable.back,
            R.drawable.backm,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discoverapp);

        CarouselView carouselView = findViewById (R.id.carouselView);
        carouselView.setPageCount (mImages.length);
        carouselView.setImageListener (new ImageListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {
                imageView.setImageResource (mImages[position]);

            }
        });

    }
}