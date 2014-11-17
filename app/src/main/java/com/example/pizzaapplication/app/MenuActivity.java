package com.example.pizzaapplication.app;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import com.example.pizzaapplication.test.OrderObservable;
import edu.colostate.cs414.d.pizza.Kiosk;
import edu.colostate.cs414.d.pizza.api.menu.PizzaMenuItem;
import edu.colostate.cs414.d.pizza.api.order.OrderItem;

import java.util.ArrayList;
import java.util.List;

public class MenuActivity extends ActionBarActivity {

    private List<PizzaMenuItem> items;

    private OrderObservable orderObservable;

    private PizzaMenuItem currentMenuItem;
    private Kiosk kiosk;

    private int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        ListView listView = (ListView) findViewById(R.id.menuList);

        orderObservable = orderObservable.getInstance();

        //TODO add in real menu items
        kiosk = kiosk.getInstance();
        kiosk.viewMenu();
        items =  kiosk.viewMenu();
        /*items.add(new PizzaMenuItem("pizza",10,""));
        items.add(new PizzaMenuItem("meat pizza",8,"meat everyone"));
        items.add(new PizzaMenuItem("soda",3,""));
        items.add(new PizzaMenuItem("coke",2,"Whats the difference?"));
        items.add(new PizzaMenuItem("cheese pizza",11,"cheesy"));*/

        Spinner spinner = (Spinner) findViewById(R.id.spinner);


        final List<String> quantities = new ArrayList<String>();
        quantities.add("1");
        quantities.add("2");
        quantities.add("3");
        quantities.add("4");
        quantities.add("5");

        ArrayAdapter<String> dataAdapter= new ArrayAdapter<String>(this, R.layout.simple_spinner_item , quantities);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(dataAdapter);

        ArrayAdapter<PizzaMenuItem> adapter= new ArrayAdapter<>(this, R.layout.simple_list_item_1 , items);

        listView.setAdapter(adapter);

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
        //TODO nothing selected
        if(currentMenuItem != null)
            orderObservable.addOrderItem(new OrderItem(currentMenuItem,quantity));
        finish();
    }
}
