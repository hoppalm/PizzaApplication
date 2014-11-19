package com.example.pizzaapplication.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import edu.colostate.cs414.d.pizza.Kiosk;
import edu.colostate.cs414.d.pizza.api.order.OrderStatus;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;

@EActivity(R.layout.activity_cash_payment)
public class CashPaymentActivity extends Activity {

    private Kiosk kiosk;
    private OrderObservable orderObservable;
    private TextView price;

    public CashPaymentActivity() {
        kiosk = Kiosk.getInstance();
        orderObservable = OrderObservable.getInstance();
    }

    @AfterViews
    protected void init() {
        price =  (TextView) findViewById(R.id.textView2);
        price.setText(String.format("$%.2f", orderObservable.getPrice()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.cash_payment, menu);
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

    public void submitPayment(View view) {
        //TODO ERROR CHECKING OF FIELDS HERE
        placeOrder();
    }

    @Background
    public void placeOrder() {
        orderObservable.getOrder().setStatus(OrderStatus.PENDING);
        orderObservable.getOrder().getItems().addAll(orderObservable.getCouponItems());
        if (MainActivity.currentUser != null){
            MainActivity.currentUser.setRewardPoints(orderObservable.getRewardPoints()+1);
            kiosk.getLoggedInUser().setRewardPoints(orderObservable.getRewardPoints()+1);
            kiosk.updateRewardPoints(orderObservable.getRewardPoints()+1);
        }
        kiosk.placeOrder(orderObservable.getOrder());
        startCustomerActivity();
    }

    @UiThread
    public void startCustomerActivity() {
        Intent intent = new Intent(this, CustomerScreenActivity_.class);
        startActivity(intent);
        finish();
    }
}
