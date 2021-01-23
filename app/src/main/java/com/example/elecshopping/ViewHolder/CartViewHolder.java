package com.example.elecshopping.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.elecshopping.Interface.ItemClickListner;
import com.example.elecshopping.R;


public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtProductName, txtProductPrice, txtProductQuantity, txtProductTime , txtProductDate , txtProductBrand , txtProductshipped, txtProducttotalprice, txttotalamount;
    private ItemClickListner itemClickListener;

    public CartViewHolder(@NonNull View itemView) {
        super(itemView);

        txtProductName = itemView.findViewById(R.id.cart_product_name);
        txtProductPrice = itemView.findViewById(R.id.cart_product_price);
        txtProductQuantity = itemView.findViewById(R.id.cart_product_quantity);
        txtProductTime = itemView.findViewById(R.id.cart_product_time);
        txtProductDate = itemView.findViewById(R.id.cart_product_date);
        txtProductBrand = itemView.findViewById(R.id.cart_product_brand);
        txtProducttotalprice = itemView.findViewById(R.id.cart_total_price);
        txtProductshipped = itemView.findViewById(R.id.cart_shipped_price);
        txttotalamount = itemView.findViewById(R.id.cart_total_amount);


    }

    @Override
    public void onClick(View view) {

        itemClickListener.onClick(view, getAdapterPosition(),false);


    }

    public void setItemClickListener(ItemClickListner itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
}
