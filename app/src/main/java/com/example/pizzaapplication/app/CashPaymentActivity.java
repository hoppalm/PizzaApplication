package com.example.pizzaapplication.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
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
    private EditText totalGiven;

    public CashPaymentActivity() {
        kiosk = Kiosk.getInstance();
        orderObservable = OrderObservable.getInstance();
    }

    @AfterViews
    protected void init() {
        price =  (TextView) findViewById(R.id.textView2);
        price.setText(String.format("$%.2f", orderObservable.getPrice()));

        totalGiven = (EditText)findViewById(R.id.cashTotalGiven);
        totalGiven.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(2)});
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
        if(totalGiven.getText().toString().isEmpty())
        {
            printError("Payment not entered.","Must enter a payment amount for Total Given.");
            return;
        }

        float total = Float.valueOf(totalGiven.getText().toString());
        if(total < orderObservable.getPrice())
        {
            printError("Insufficient Payment", "Payment must be at least Total Due.");
            return;
        }
        printMessage("Payment Accepted.", "Your change is $"+String.format("%.2f", (total-orderObservable.getPrice())));
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
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public void printError(String errorName, String error){
        new AlertDialog.Builder(this)
                .setTitle(errorName)
                .setMessage(error)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    public void printMessage(String errorName, String error){
        new AlertDialog.Builder(this)
                .setTitle(errorName)
                .setMessage(error)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_info)
                .setCancelable(false)
                .show();
    }
}
