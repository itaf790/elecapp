package com.example.elecshopping.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.elecshopping.Interface.ItemClickListner;
import com.example.elecshopping.R;


public class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


    public TextView txtCategoryName;
    public ImageView imageView;
    public ItemClickListner listner;

    public CategoryViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView= (ImageView) itemView.findViewById(R.id.category_image);
        txtCategoryName= (TextView) itemView.findViewById(R.id.category_name);


    }


    public void setItemClickListner(ItemClickListner listner){

        this.listner=listner;

    }
    @Override
    public void onClick(View view) {
        listner.onClick(view, getAdapterPosition(),false);

    }

}
