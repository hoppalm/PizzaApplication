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
import edu.colostate.cs414.d.pizza.api.order.OrderItem;

import java.util.Observable;
import java.util.Observer;


public class OrderActivity extends ActionBarActivity implements Observer {

    public OrderObservable orderObservable;
    private ArrayAdapter<OrderItem> adapter;
    private ListView listView;
    private int removePosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        orderObservable = orderObservable.getInstance();
        makeOrderItemTable();
        orderObservable.addObserver(this);
    }

    public void makeOrderItemTable(){
        removePosition = -1;
        listView = (ListView) findViewById(R.id.orderItems);
        adapter= new ArrayAdapter<OrderItem>(this, R.layout.simple_list_item_1 , orderObservable.getOrder().getItems());

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
        makeOrderItemTable();
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
        if(removePosition >= 0)
            orderObservable.getOrder().getItems().remove(removePosition);
        makeOrderItemTable();
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
}
