package com.example.pizzaapplication.app;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.example.pizzaapplication.test.Coupon;
import com.example.pizzaapplication.test.OrderObservable;
import com.example.pizzaapplication.test.PizzaMenuItem;


import java.util.ArrayList;
import java.util.List;


public class CertificateActivity extends ActionBarActivity {

    private List<Coupon> items;

    private OrderObservable orderObservable;

    private Coupon currentCoupon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_certificate);

        orderObservable = orderObservable.getInstance();

        //TODO set reward points
        ListView listView = (ListView) findViewById(R.id.certList);

        //TODO add in real coupons

        List<PizzaMenuItem> menuitems = new ArrayList<>();
        menuitems.add(new PizzaMenuItem("","pizza",10));
        menuitems.add(new PizzaMenuItem("meat everyone","meat pizza",8));
        menuitems.add(new PizzaMenuItem("","soda",3));
        menuitems.add(new PizzaMenuItem("Whats the difference?","coke",2));
        menuitems.add(new PizzaMenuItem("cheesy","cheese pizza",11));

        items = new ArrayList<>();
        items.add(new Coupon(menuitems.get(3),3));
        items.add(new Coupon(menuitems.get(4),2));
        items.add(new Coupon(menuitems.get(1),7));
        items.add(new Coupon(menuitems.get(2),4));
        items.add(new Coupon(menuitems.get(0),1));


        ArrayAdapter<Coupon> adapter= new ArrayAdapter<Coupon>(this, R.layout.simple_list_item_1 , items);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,long arg3) {
                view.setSelected(true);
                currentCoupon = items.get(position);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.certificate, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void cancel(View view) {
        finish();
    }

    public void redeemCertificate(View view) {
        //TODO null handling
        if (currentCoupon != null){
            orderObservable.addCoupon(currentCoupon);
        }
        finish();
    }
}
