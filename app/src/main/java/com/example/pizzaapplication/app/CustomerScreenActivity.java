package com.example.pizzaapplication.app;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import edu.colostate.cs414.d.pizza.Kiosk;
import edu.colostate.cs414.d.pizza.api.menu.PizzaMenuItem;
import org.androidannotations.annotations.*;

import java.util.ArrayList;
import java.util.List;


@EActivity(R.layout.activity_customer_screen)
public class CustomerScreenActivity extends ActionBarActivity {

    @ViewById(R.id.rewardPoints)
    protected TextView textView;

    private Kiosk kiosk;


    public CustomerScreenActivity() {
        kiosk = Kiosk.getInstance();
    }

    @AfterViews
    protected void init() {
        textView = (TextView) findViewById(R.id.rewardPoints);
        if (MainActivity.currentUser == null) {
            Button orderHistoryButton = (Button) findViewById(R.id.orderHistoryButton);
            orderHistoryButton.setEnabled(false);

            Button logoutButton = (Button) findViewById(R.id.logOutButton);
            logoutButton.setEnabled(false);
        }
        else{
            fetchCustomer();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.customer_screen, menu);
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

    public void placeOrder(View view) {
        Intent intent = new Intent(this, OrderActivity_.class);
        startActivity(intent);
    }

    public void orderHistory(View view) {
        Intent intent = new Intent(this, OrderHistoryActivity_.class);
        startActivity(intent);
    }

    public void logout(View view) {
        MainActivity.currentUser = null;
        finish();
    }

    @Background
    public void fetchCustomer() {
        MainActivity.currentUser = kiosk.getLoggedInUser();
        setRewardPoints();
    }

    @UiThread
    public void setRewardPoints() {
        textView.setText(String.valueOf(MainActivity.currentUser.getRewardPoints()));
    }
}
