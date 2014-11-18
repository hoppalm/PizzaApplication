package com.example.pizzaapplication.app;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import edu.colostate.cs414.d.pizza.Kiosk;
import edu.colostate.cs414.d.pizza.api.menu.PizzaMenuItem;
import edu.colostate.cs414.d.pizza.api.order.OrderItem;
import org.androidannotations.annotations.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

@EActivity(R.layout.activity_order)
public class OrderActivity extends ActionBarActivity implements Observer {

    private Kiosk kiosk;
    private OrderObservable orderObservable;

    //@ViewById(R.id.orderRewardPoints)
    protected TextView rewardPointsText;

    //@ViewById(R.id.orderRewardPoints)
    private TextView priceText;

    private ArrayAdapter<OrderItem> adapter;
    private ListView listView;
    private int removePosition = -1;

    public OrderActivity() {
        kiosk = Kiosk.getInstance();
        orderObservable = OrderObservable.getInstance();
        orderObservable.clearOrder();

        /*if ( MainActivity.currentUser != null) {
            orderObservable.setRewardPoints(MainActivity.currentUser.getRewardPoints());
        }
        else{
            orderObservable.setRewardPoints(0);
        }*/
    }

    @AfterViews
    protected void init() {
        rewardPointsText = (TextView) findViewById(R.id.orderRewardPoints);
        priceText =  (TextView) findViewById(R.id.totalDue);
        updateView();
        orderObservable.addObserver(this);
    }

    public void updateView(){
        fetchPrice();

        removePosition = -1;

        //rewardPointsText.setText(orderObservable.getRewardPoints());
        List<OrderItem> currentItems = new ArrayList<>();
        currentItems.addAll(orderObservable.getOrder().getItems());
        currentItems.addAll(orderObservable.getCouponItems());
        listView = (ListView) findViewById(R.id.orderItems);
        adapter= new ArrayAdapter<OrderItem>(this, R.layout.simple_list_item_1 , currentItems);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long arg3) {
                view.setSelected(true);
                removePosition = position;

            }
        });
    }


    @Override
    public void update(Observable observable, Object data) {
        System.out.println("DEBUG: It was updated!");
        updateView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.order, menu);
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

    public void addMenuItem(View view) {
        // `MenuActivity_` instead of `MenuActivity`
        // AndroidAnnotations generates the `_` for us, but we have to call it
        // instead of the "real" class manually
        Intent intent = new Intent(this, MenuActivity_.class);
        startActivity(intent);
    }

    public void addDailySpecial(View view) {
        Intent intent = new Intent(this, DailySpecialActivity_.class);
        startActivity(intent);
    }

    public void redeemCertificate(View view) {
        Intent intent = new Intent(this, CertificateActivity_.class);
        startActivity(intent);
    }

    public void removeButton(View view) {
        if(removePosition >= 0) {
            orderObservable.removeItem(removePosition);
            updateView();
        }
    }

    public void placeOrder(View view) {
        if(orderObservable.getOrder().getItems().size() == 0){
            new AlertDialog.Builder(this)
                    .setTitle("Order Is Empty")
                    .setMessage("Must order at least one item")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
        else {
            Intent intent = new Intent(this, OrderInformationActivity.class);
            startActivity(intent);
        }
    }

    @Background
    public void fetchPrice() {
        double price = kiosk.calculateSubtotal(orderObservable.getOrder(), orderObservable.getDailySpecials());
        orderObservable.setPrice(price);
        setPrice();
    }

    @UiThread
    public void setPrice() {
        priceText.setText(String.format("$%.2f", orderObservable.getPrice()));
    }
}
