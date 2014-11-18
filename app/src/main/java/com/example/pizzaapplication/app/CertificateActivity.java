package com.example.pizzaapplication.app;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.example.pizzaapplication.test.OrderObservable;
import edu.colostate.cs414.d.pizza.Kiosk;
import edu.colostate.cs414.d.pizza.api.menu.Coupon;
import edu.colostate.cs414.d.pizza.api.menu.PizzaMenuItem;
import org.androidannotations.annotations.*;


import java.util.ArrayList;
import java.util.List;

@EActivity(R.layout.activity_certificate)
public class CertificateActivity extends ActionBarActivity {

    private Kiosk kiosk;
    private OrderObservable orderObservable;

    @ViewById(R.id.certList)
    protected ListView listView;

    private List<Coupon> items;
    private Coupon currentCoupon;

    public CertificateActivity() {
        kiosk = Kiosk.getInstance();
        orderObservable = OrderObservable.getInstance();
    }

    @AfterViews
    protected void init() {
        // fetchCoupons will request the menu in the background, and then call
        // setCoupons() with the returned items (in the UI thread)
        fetchCoupons();

        //TODO set reward points

        // could potentially use @ItemSelect here
        // see: https://github.com/excilys/androidannotations/wiki/AdapterViewEvents
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,long arg3) {
                view.setSelected(true);
                currentCoupon = items.get(position);
                System.out.println(currentCoupon);
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

    @Background
    public void fetchCoupons() {
        List<Coupon> items = kiosk.viewCoupons();
        setCoupons(items);
    }

    @UiThread
    public void setCoupons(List<Coupon> items) {
        this.items = items;

        ArrayAdapter<Coupon> adapter = new ArrayAdapter<>(this, R.layout.simple_list_item_1 , items);
        listView.setAdapter(adapter);
    }

}
