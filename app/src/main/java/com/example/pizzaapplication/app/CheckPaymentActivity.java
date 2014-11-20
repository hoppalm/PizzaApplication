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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import edu.colostate.cs414.d.pizza.Kiosk;
import edu.colostate.cs414.d.pizza.api.menu.PizzaMenuItem;
import edu.colostate.cs414.d.pizza.api.order.OrderStatus;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;

import java.util.List;

@EActivity(R.layout.activity_check_payment)
public class CheckPaymentActivity extends Activity {

    private Kiosk kiosk;
    private OrderObservable orderObservable;
    private TextView price;
    private EditText checkTotalGiven;
    private EditText checkName;
    private EditText checkNumber;
    private EditText checkAccountNumber;
    private EditText checkRouting;

    public CheckPaymentActivity() {
        kiosk = Kiosk.getInstance();
        orderObservable = OrderObservable.getInstance();
    }

    @AfterViews
    protected void init() {
        price =  (TextView) findViewById(R.id.textView7);
        price.setText(String.format("$%.2f", orderObservable.getPrice()));

        checkName = (EditText) findViewById(R.id.editText);
        checkAccountNumber = (EditText) findViewById(R.id.editText2);
        checkRouting = (EditText) findViewById(R.id.editText3);
        checkNumber = (EditText) findViewById(R.id.editText4);
        checkTotalGiven = (EditText) findViewById(R.id.editText5);
        checkTotalGiven.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(2)});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.check_payment, menu);
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

    public boolean isNumber(String number) {
        for(int i = 0; i<number.length(); i++ ){
            if(!Character.isDigit(number.charAt(i))){
                return false;
            }
        }
        return true;
    }

    public void submitPayment(View view) {

        if(checkName.getText().toString().isEmpty()) {
            printError("Invalid name.", "Name must be entered in from check");
            return;
        }

        if(checkAccountNumber.getText().toString().isEmpty()){
            printError("Invalid account number.", "Check Account number must be entered in from check");
            return;
        }

        if(!isNumber(checkAccountNumber.getText().toString()) || checkAccountNumber.getText().toString().length() < 4 || checkAccountNumber.getText().toString().length() > 17){
            printError("Invalid account number.", "Check Account number must be all numbers and a length of from 4 to 17 digits");
            return;
        }

        if(checkRouting.getText().toString().isEmpty()){
            printError("Invalid routing number.", "Check Routing Number must be entered in from check");
            return;
        }

        if(!isNumber(checkRouting.getText().toString()) || checkRouting.getText().toString().length() != 9){
            printError("Invalid routing number.", "Check routing number from check must be all numbers and a length of 9 digits");
            return;
        }

        if(checkNumber.getText().toString().isEmpty()){
            printError("Invalid check number.","Check Number must be entered in from check");
            return;
        }

        if(!isNumber(checkNumber.getText().toString())){
            printError("Invalid check number.", "Check Number must be numeric");
            return;
        }

        float totalGiven = 0.0f;
        try{
            totalGiven = Float.parseFloat(checkTotalGiven.getText().toString());
        }
        catch (NumberFormatException e)  {
            printError("Insufficient payment.", "Must enter a payment amount.");
            return;
        }
        if(totalGiven < orderObservable.getPrice()){
            printError("Insufficient payment.", "Check total not enough for the total");
            return;
        }
        printMessage("Payment Accepted.", "Your change is $"+String.format("%.2f", (totalGiven-orderObservable.getPrice())));
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
