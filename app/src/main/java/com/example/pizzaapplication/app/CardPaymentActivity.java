package com.example.pizzaapplication.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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

import java.util.Calendar;

@EActivity(R.layout.activity_card_payment)
public class CardPaymentActivity extends Activity {

    private Kiosk kiosk;
    private OrderObservable orderObservable;
    private TextView price;
    private EditText cardHolder;
    private EditText cardNumber;
    private EditText billingAddress;
    private EditText city;
    private EditText zipCode;
    private EditText securityCode;
    private EditText expirationDate;

    public CardPaymentActivity() {
        kiosk = Kiosk.getInstance();
        orderObservable = OrderObservable.getInstance();
    }

    @AfterViews
    protected void init() {
        price =  (TextView) findViewById(R.id.textView9);
        price.setText(String.format("$%.2f", orderObservable.getPrice()));

        cardHolder = (EditText)findViewById(R.id.cardHolder);
        cardNumber = (EditText)findViewById(R.id.cardNumber);
        billingAddress = (EditText)findViewById(R.id.billingAddress);
        city = (EditText)findViewById(R.id.city);
        zipCode = (EditText)findViewById(R.id.zipCode);
        securityCode = (EditText)findViewById(R.id.securityCode);
        expirationDate = (EditText)findViewById(R.id.expirationDate);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.card_payment, menu);
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
        if(cardHolder.getText().toString().isEmpty()){
            printError("Invalid card holder.", "The card holder name must be entered.");
            return;
        }

        if(cardNumber.getText().toString().isEmpty()){
            printError("Invalid card number.", "You must enter a valid card number.");
            return;
        }

        if(!isNumber(cardNumber.getText().toString()) || cardNumber.getText().toString().length() != 16){
            printError("Invalid card number.", "Card number must be exactly 16 digits.");
            return;
        }

        if(securityCode.getText().toString().isEmpty()){
            printError("Invalid security code.", "You must enter a 3-4 digit security code.");
            return;
        }

        if(!isNumber(securityCode.getText().toString()) || securityCode.getText().toString().length() < 3 || securityCode.getText().toString().length() > 4){
            printError("Invalid security code.", "Credit Card security number must be a length of 3 or 4 digits depending on the type of card you have");
            return;
        }

        String expirationDateString = expirationDate.getText().toString();
        if(expirationDateString.length() != 5 || expirationDateString.indexOf('/') != 2) {
            printError("Invalid Expiration Date", "Please enter a valid expiration date in MM/YY format.");
            return;
        }

        try {
            int monthExpiration = Integer.parseInt(expirationDateString.substring(0, expirationDateString.indexOf('/')));
            int yearExpiration = Integer.parseInt(expirationDateString.substring(expirationDateString.indexOf('/') + 1)) + 2000;

            if(monthExpiration < 1 || monthExpiration > 12)
            {
                printError("Invalid month.", "The expiration month must be between 01 and 12.");
                return;
            }

            if (yearExpiration < Calendar.getInstance().get(Calendar.YEAR) ||
                    (yearExpiration == Calendar.getInstance().get(Calendar.YEAR) && monthExpiration <= Calendar.getInstance().get(Calendar.MONTH)+1)) {
                printError("Expired Card", "The expiration date for this card has already passed.");
                return;
            }
        }
        catch (NumberFormatException e) {
            printError("Invalid Expiration Date", "Please enter a valid expiration date in MM/YY format.");
            return;
        }

        if(billingAddress.getText().toString().isEmpty()){
            printError("Invalid address.", "Address must be entered in for billing purposes");
            return;
        }

        if(city.getText().toString().isEmpty()){
            printError("Invalid city.", "City must be entered in for billing purposes");
            return;
        }

        if(zipCode.getText().toString().isEmpty()){
            printError("Invalid zip code.", "Zip code must be entered in for billing purposes");
            return;
        }

        if(zipCode.getText().toString().length() < 5){
            printError("Invalid zip code.", "Zip code must be at least 5 digits.");
            return;
        }
        printMessage("Payment accepted.", "Thank you for your payment, your order was placed.");
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
