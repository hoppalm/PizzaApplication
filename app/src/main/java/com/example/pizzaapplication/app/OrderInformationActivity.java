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
import android.widget.RadioGroup;
import android.widget.TextView;
import edu.colostate.cs414.d.pizza.api.order.OrderType;


public class OrderInformationActivity extends Activity {

    private OrderObservable orderObservable;
    private EditText name;
    private EditText address;
    private TextView price;
    private OrderType orderType = OrderType.DELIVERY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_information);
        orderObservable = orderObservable.getInstance();
        name = (EditText)findViewById(R.id.editText);
        address = (EditText)findViewById(R.id.editText2);
        price =  (TextView) findViewById(R.id.textView5);
        price.setText(String.format("$%.2f", orderObservable.getPrice()));

        RadioGroup rg = (RadioGroup) findViewById(R.id.radioGroup);


        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.pickUpButton:
                        // do operations specific to this selection
                        orderType = OrderType.PICKUP;
                        break;

                    case R.id.deliveryButton:
                        // do operations specific to this selection
                        orderType = OrderType.DELIVERY;
                        break;

                    case R.id.dineInButton:
                        // do operations specific to this selection
                        orderType = OrderType.EATIN;
                        break;
                }
            }
        });
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
        String addressString = address.getText().toString();
        String nameString = name.getText().toString();

        if (nameString == null || nameString.isEmpty() || nameString.equals("Name")){
            printError("Name not entered", "Must enter in Name");
            return;
        }
        orderObservable.getOrder().setCustomerName(nameString);
        if (orderType == OrderType.DELIVERY) {
            if (addressString == null || addressString.isEmpty() || addressString.equals("Delivery Address")){
                printError("Address not entered", "Must enter in address for delivery orders");
                return;
            }
            orderObservable.getOrder().setCustomerAddress(addressString);
        }
        orderObservable.getOrder().setType(orderType);

        Intent intent = new Intent(this, CashPaymentActivity_.class);
        startActivity(intent);
    }

    public void cardPayment(View view) {
        String addressString = address.getText().toString();
        String nameString = name.getText().toString();

        if (nameString == null || nameString.isEmpty() || nameString.equals("Name")){
            printError("Name not entered", "Must enter in Name");
            return;
        }
        orderObservable.getOrder().setCustomerName(nameString);
        if (orderType == OrderType.DELIVERY) {
            if (addressString == null || addressString.isEmpty() || addressString.equals("Delivery Address")){
                printError("Address not entered", "Must enter in address for delivery orders");
                return;
            }
            orderObservable.getOrder().setCustomerAddress(addressString);
        }
        orderObservable.getOrder().setType(orderType);


        Intent intent = new Intent(this, CardPaymentActivity_.class);
        startActivity(intent);
    }

    public void checkPayment(View view) {
        String addressString = address.getText().toString();
        String nameString = name.getText().toString();

        if (nameString == null || nameString.isEmpty() || nameString.equals("Name")){
            printError("Name not entered", "Must enter in Name");
            return;
        }
        orderObservable.getOrder().setCustomerName(nameString);
        if (orderType == OrderType.DELIVERY) {
            if (addressString == null || addressString.isEmpty() || addressString.equals("Delivery Address")){
                printError("Address not entered", "Must enter in address for delivery orders");
                return;
            }
            orderObservable.getOrder().setCustomerAddress(addressString);
        }
        orderObservable.getOrder().setType(orderType);

        Intent intent = new Intent(this, CheckPaymentActivity_.class);
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
}
