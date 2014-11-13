package com.example.pizzaapplication.app;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class CustomerScreen extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_screen);
        //if customer is null set order history button/log out to inactive
        /*if (kiosk.getLoggedInUser() == NULL) {
        Button orderHistoryButton = (Button) findViewById(R.id.orderHistoryButton);
        orderHistoryButton.setEnabled(false);

        Button logoutButton = (Button) findViewById(R.id.logOutButton);
        logoutButton.setEnabled(false);

        TextView text = (TextView) findViewById(R.id.rewardPoints);
        text.setText(String.getValue(customer.getRewardsPoint());
        }
        */
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
    }

    public void orderHistory(View view) {
    }

    public void logout(View view) {
        //TO DO LOG OUT OF ACCOUNT
        //set customer to null
        finish();
    }
}
