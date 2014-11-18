package com.example.pizzaapplication.app;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import edu.colostate.cs414.d.pizza.Kiosk;
import edu.colostate.cs414.d.pizza.api.menu.PizzaMenuItem;
import edu.colostate.cs414.d.pizza.api.order.OrderItem;
import org.androidannotations.annotations.*;

import java.util.ArrayList;
import java.util.List;

// EActivity is the base AndroidAnnotations class
// we can (optionally) pass the layout ID in to have it initialize it
// for us automatically
// note that we have to refer to this class as `MenuActivity_` instead of
// `MenuActivity` - the '_' is the generated class with all of the annotations
// filled in
@EActivity(R.layout.activity_menu)
public class MenuActivity extends ActionBarActivity {

    private Kiosk kiosk;
    private OrderObservable orderObservable;

    // this view will get filled in automatically for us by the time init()
    // is called
    @ViewById(R.id.spinner)
    protected Spinner spinner;

    @ViewById(R.id.menuList)
    protected ListView listView;

    private List<PizzaMenuItem> items;
    private PizzaMenuItem currentMenuItem;

    private int quantity = 1;

    public MenuActivity() {
        kiosk = Kiosk.getInstance();
        orderObservable = OrderObservable.getInstance();
    }

    // instead of onCreate()
    // this gets called after the annotation processor sets `spinner` and
    // `listView` for us
    @AfterViews
    protected void init() {
        // fetchMenu will request the menu in the background, and then call
        // setMenu() with the returned items (in the UI thread)
        fetchMenu();

        final List<String> quantities = new ArrayList<>();
        quantities.add("1");
        quantities.add("2");
        quantities.add("3");
        quantities.add("4");
        quantities.add("5");

        ArrayAdapter<String> dataAdapter= new ArrayAdapter<>(this, R.layout.simple_spinner_item , quantities);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        // could potentially use @ItemSelect here
        // see: https://github.com/excilys/androidannotations/wiki/AdapterViewEvents
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,long arg3) {
                view.setSelected(true);
                currentMenuItem = items.get(position);
                System.out.println(currentMenuItem);
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                quantity = Integer.parseInt(quantities.get(position));
                System.out.println("Quantity is: " + quantity);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                quantity = 1;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
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

    public void cancel(View view) {
        finish();
    }

    public void addItem(View view) {
        if(currentMenuItem != null) {
            orderObservable.addOrderItem(new OrderItem(currentMenuItem, quantity));
            finish();
        }
        else {
            new AlertDialog.Builder(this)
                    .setTitle("Item Not Selected")
                    .setMessage("Must select an item to add")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
    }

    @Background
    public void fetchMenu() {
        List<PizzaMenuItem> items = kiosk.viewMenu();
        setMenu(items);
    }

    @UiThread
    public void setMenu(List<PizzaMenuItem> items) {
        this.items = items;

        ArrayAdapter<PizzaMenuItem> adapter = new ArrayAdapter<>(this, R.layout.simple_list_item_1 , items);
        listView.setAdapter(adapter);
    }

}
