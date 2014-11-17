package com.example.pizzaapplication.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class OrderInformationActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_information);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.order_information, menu);
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

    public void cashPayment(View view) {
        Intent intent = new Intent(this, CashPaymentActivity.class);
        startActivity(intent);
    }

    public void cardPayment(View view) {
        Intent intent = new Intent(this, CardPaymentActivity.class);
        startActivity(intent);
    }

    public void checkPayment(View view) {
        Intent intent = new Intent(this, CheckPaymentActivity.class);
        startActivity(intent);
    }
}
