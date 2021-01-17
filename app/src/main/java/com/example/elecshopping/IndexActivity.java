package com.example.elecshopping;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;

import com.example.elecshopping.User.UserDeliveryFeeActivity;
import com.example.elecshopping.User.UserDeliveryTimeActivity;
import com.example.elecshopping.User.UserExchangeActivity;
import com.example.elecshopping.User.UserPaymentMethodActivity;
import com.example.elecshopping.User.UserReturnsActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class IndexActivity extends AppCompatActivity {


    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);


        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.lvExp);

        // preparing list data
        prepareListData();

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);
    }

    /*
     * Preparing the list data
     */
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("Categories");
        listDataHeader.add("Offers");
        listDataHeader.add("Payment Method");
        listDataHeader.add("Delivery Fee");
        listDataHeader.add("Delivery Time");
        listDataHeader.add("Returns Policy");
        listDataHeader.add("Exchange Policy");


        // Adding child data
        final List<String> Categories = new ArrayList<String>();
        Categories.add("Computers");
        Categories.add("Laptops");
        Categories.add("Headphones");
        Categories.add("Cameras");
        Categories.add("TVs");
        Categories.add("Mobiles");
        Categories.add("Tablets");
        Categories.add("Other Electronics");

        final List<String> Payment = new ArrayList<String>();
        Payment.add("click here to Read the payment method");

        final List<String> fee = new ArrayList<String>();
        fee.add("click here to Read the delivery fee");

        final List<String> time = new ArrayList<String>();
        time.add("click here to Read the delivery time");

        final List<String> returns = new ArrayList<String>();
        returns.add("click here to Read the returns policy");

        final List<String> exchange = new ArrayList<String>();
        exchange.add("click here to Read the exchange policy");



        listDataChild.put(listDataHeader.get(0), Categories);// Header, Child data
        listDataChild.put(listDataHeader.get(2), Payment);
        listDataChild.put(listDataHeader.get(3), fee);
        listDataChild.put(listDataHeader.get(4), time);
        listDataChild.put(listDataHeader.get(5), returns);
        listDataChild.put(listDataHeader.get(6), exchange);

        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {


                final String selected = (String) listAdapter.getChild(
                        groupPosition, childPosition);

                // Switch case to open selected child element activity on child element selection.

                Intent intent;
                switch(selected){
                    case "click here to Read the payment method":
                        intent = new Intent(IndexActivity.this, UserPaymentMethodActivity.class);
                        startActivity(intent);
                        break;

                    case "click here to Read the delivery fee":
                    intent = new Intent(IndexActivity.this, UserDeliveryFeeActivity.class);
                    startActivity(intent);
                    break;

                    case "click here to Read the delivery time":
                        intent = new Intent(IndexActivity.this, UserDeliveryTimeActivity.class);
                        startActivity(intent);
                        break;

                    case "click here to Read the returns policy":
                        intent = new Intent(IndexActivity.this, UserReturnsActivity.class);
                        startActivity(intent);
                        break;

                    case "click here to Read the exchange policy":
                        intent = new Intent(IndexActivity.this, UserExchangeActivity.class);
                        startActivity(intent);
                        break;

                }

                return true;
            }
        });
    }





}
