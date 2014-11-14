package com.example.pizzaapplication.app;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import android.widget.ListView;
import com.example.pizzaapplication.test.DailySpecial;
import com.example.pizzaapplication.test.PizzaMenuItem;

import java.util.ArrayList;
import java.util.List;


public class DailySpecialActivity extends ActionBarActivity {

    List<DailySpecial> dailySpecialList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_special);

        ListView listView = (ListView) findViewById(R.id.specialList);

        //TODO add in real specials

        List<PizzaMenuItem> menuitems = new ArrayList<>();
        menuitems.add(new PizzaMenuItem("","pizza",10));
        menuitems.add(new PizzaMenuItem("meat everyone","meat pizza",8));
        menuitems.add(new PizzaMenuItem("","soda",3));
        menuitems.add(new PizzaMenuItem("Whats the difference?","coke",2));
        menuitems.add(new PizzaMenuItem("cheesy","cheese pizza",11));

        dailySpecialList = new ArrayList<>();
        dailySpecialList.add(new DailySpecial(10, menuitems));

        menuitems = new ArrayList<>();
        menuitems.add(new PizzaMenuItem("","pizza",10));
        menuitems.add(new PizzaMenuItem("cheesy","cheese pizza",11));

        dailySpecialList.add(new DailySpecial(7, menuitems));

        menuitems = new ArrayList<>();
        menuitems.add(new PizzaMenuItem("meat everyone","meat pizza",8));
        menuitems.add(new PizzaMenuItem("","soda",3));
        menuitems.add(new PizzaMenuItem("cheesy","cheese pizza",11));

        dailySpecialList.add(new DailySpecial(5, menuitems));


        ArrayAdapter<DailySpecial> adapter= new ArrayAdapter<DailySpecial>(this, R.layout.simple_list_item_1 , dailySpecialList);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,long arg3) {
                view.setSelected(true);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.daily_special, menu);
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

    public void orderSpecial(View view) {
        //TODO order special and add order items to list to order
        finish();
    }

    public void cancel(View view) {
        finish();
    }
}
