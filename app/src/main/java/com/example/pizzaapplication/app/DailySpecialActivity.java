package com.example.pizzaapplication.app;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import android.widget.ListView;
import com.example.pizzaapplication.test.OrderObservable;
import edu.colostate.cs414.d.pizza.Kiosk;
import edu.colostate.cs414.d.pizza.api.menu.DailySpecial;
import org.androidannotations.annotations.*;

import java.util.List;

@EActivity(R.layout.activity_daily_special)
public class DailySpecialActivity extends ActionBarActivity {

    private Kiosk kiosk;

    private OrderObservable orderObservable;

    private List<DailySpecial> dailySpecialList;

    private DailySpecial currentDailySpecial;

    @ViewById(R.id.specialList)
    protected ListView listView;

    public DailySpecialActivity() {
        kiosk = Kiosk.getInstance();
        orderObservable = OrderObservable.getInstance();
    }

    @AfterViews
    protected void init() {
        // fetchSpecials will request the menu in the background, and then call
        // setSpecials() with the returned items (in the UI thread)
        fetchSpecials();

        // could potentially use @ItemSelect here
        // see: https://github.com/excilys/androidannotations/wiki/AdapterViewEvents
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,long arg3) {
                view.setSelected(true);
                currentDailySpecial = dailySpecialList.get(position);
                System.out.println(currentDailySpecial);
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
        //TODO check if special is null
        if (currentDailySpecial != null){
            orderObservable.addDailySpecial(currentDailySpecial);
        }
        finish();
    }

    public void cancel(View view) {
        finish();
    }

    @Background
    public void fetchSpecials() {
        List<DailySpecial> items = kiosk.viewDailySpecials();
        setSpecials(items);
    }

    @UiThread
    public void setSpecials(List<DailySpecial> items) {
        this.dailySpecialList = items;

        ArrayAdapter<DailySpecial> adapter = new ArrayAdapter<>(this, R.layout.simple_list_item_1 , items);
        listView.setAdapter(adapter);
    }
}
