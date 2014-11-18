package com.example.pizzaapplication.app;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import edu.colostate.cs414.d.pizza.Kiosk;
import edu.colostate.cs414.d.pizza.api.menu.PizzaMenuItem;
import edu.colostate.cs414.d.pizza.api.user.User;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;

import java.util.List;

@EActivity(R.layout.activity_main)
public class MainActivity extends ActionBarActivity {

    public static User currentUser = null;
    private EditText userName;
    private EditText password;
    private Kiosk kiosk;

    public MainActivity() {
        kiosk = kiosk.getInstance();
        kiosk.initialize("http://10.0.2.2:8080/");
    }

    @AfterViews
    protected void init() {
        userName = (EditText)findViewById(R.id.userName);
        password = (EditText)findViewById(R.id.password);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    public void login(View view) {
        String userNameString = userName.getText().toString();
        String passwordString = password.getText().toString();
        logIn(userNameString,passwordString);
    }

    public void nonUser(View view) {
        currentUser = null;
        Intent intent = new Intent(this, CustomerScreenActivity.class);
        startActivity(intent);
    }

    public void createAccount(View view) {
        Intent intent = new Intent(this, CreateAccountActivity_.class);
        startActivity(intent);
    }

    @Background
    public void logIn(String userName, String password) {
        System.out.println("USER/PASS: " + userName + " " + password);
        try {
            currentUser = kiosk.authenticateUser(userName, password);
            kiosk.loginUser(currentUser);
            System.out.println(currentUser.getRewardPoints() + " " + currentUser.getUserName() + " " + currentUser.getPassword());
            startCustomerActivity();
        }
        catch (edu.colostate.cs414.d.pizza.client.WebServiceException We){
            currentUser = null;
            startAlertDialog();
        }
    }

    @UiThread
    public void startCustomerActivity() {
        Intent intent = new Intent(this, CustomerScreenActivity.class);
        startActivity(intent);
    }

    @UiThread
    public void startAlertDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Invalid credentials")
                .setMessage("Continue as a non user?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        startCustomerActivity();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
