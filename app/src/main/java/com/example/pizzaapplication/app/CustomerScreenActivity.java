package com.example.pizzaapplication.app;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class CustomerScreenActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_screen);
        if (MainActivity.currentUser == null) {
            Button orderHistoryButton = (Button) findViewById(R.id.orderHistoryButton);
            orderHistoryButton.setEnabled(false);

            Button logoutButton = (Button) findViewById(R.id.logOutButton);
            logoutButton.setEnabled(false);
        }
        else {
            TextView text = (TextView) findViewById(R.id.rewardPoints);
            text.setText(String.valueOf(MainActivity.currentUser.getRewardPoints()));
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
        Intent intent = new Intent(this, OrderActivity.class);
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
}
