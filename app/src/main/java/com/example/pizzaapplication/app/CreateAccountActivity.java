package com.example.pizzaapplication.app;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import edu.colostate.cs414.d.pizza.Kiosk;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;

//@EActivity(R.layout.activity_create_account)
public class CreateAccountActivity extends ActionBarActivity {

    private EditText userName;
    private EditText password;
    private Kiosk kiosk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        userName = (EditText)findViewById(R.id.userName);
        password = (EditText)findViewById(R.id.password);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.create_account, menu);
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

    public void save(View view){
        //TO DO CREATE ACCOUNT IF YOU CAN
        String userNameString = userName.getText().toString();
        String passwordString = password.getText().toString();
        //authenticate error if it already exists
        finish();
    }

    public void cancel(View view){
        finish();
    }
/*
    @Background
    public void createAccount(String userName, String password) {
        try {
            kiosk.registerUser(userName, password);
            finishCreateAccount();
        }
        catch (edu.colostate.cs414.d.pizza.client.WebServiceException We){
            startAlertDialog();
        }
    }

    @UiThread
    public void finishCreateAccount() {
        finish();
    }

    @UiThread
    public void startAlertDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Credentials already exist")
                .setMessage("Retry?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
*/
}
