package com.example.pizzaapplication.app;

import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.*;
import edu.colostate.cs414.d.pizza.Kiosk;
import edu.colostate.cs414.d.pizza.api.order.Order;
import edu.colostate.cs414.d.pizza.api.order.OrderItem;
import org.androidannotations.annotations.*;

import java.util.List;


@EActivity(R.layout.activity_order_history)
public class OrderHistoryActivity extends ActionBarActivity {

    private Kiosk kiosk;

    @ViewById(R.id.menuList)
    protected TableLayout tableLayout;

    @ViewById(R.id.rewardPoints)
    protected TextView textView;

    private List<Order> items;

    public OrderHistoryActivity() {
        kiosk = Kiosk.getInstance();
    }

    @AfterViews
    protected void init() {
        // fetchMenu will request the menu in the background, and then call
        // setMenu() with the returned items (in the UI thread)
        fetchOrders();

        tableLayout = (TableLayout) findViewById(R.id.orderHistoryTable);

        textView = (TextView) findViewById(R.id.rewardPoints);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.order_history, menu);
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

    public TableRow getTableRow(OrderItem orderItem, int orderID){
        TableRow tr = new TableRow(this);
        tr.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.FILL_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));
        //add orderID
        TextView labelOrderID = new TextView(this);
        labelOrderID.setText(String.valueOf(orderID));
        labelOrderID.setTextColor(Color.BLACK);
        labelOrderID.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.FILL_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));
        tr.addView(labelOrderID);

        //add menuItem
        TextView labelMenuItem = new TextView(this);
        labelMenuItem.setText(orderItem.getItem().getName());
        labelMenuItem.setTextColor(Color.BLACK);
        labelMenuItem.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.FILL_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));
        tr.addView(labelMenuItem);

        //add qty
        TextView labelQty = new TextView(this);
        labelQty.setText(String.valueOf(orderItem.getQuantity()));
        labelQty.setTextColor(Color.BLACK);
        labelQty.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.FILL_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));
        tr.addView(labelQty);

        return tr;
    }

    @Background
    public void fetchOrders() {
        List<Order> orders = kiosk.getUserOrders();
        int rewardPoints = kiosk.getLoggedInUser().getRewardPoints();
        setTable(orders, rewardPoints);

    }

    @UiThread
    public void setTable(List<Order> table, int rewardPoints) {
        textView.setText(String.valueOf(rewardPoints));
        for(Order order : table){
            for(OrderItem orderItem : order.getItems()){
                tableLayout.addView(getTableRow(orderItem, order.getId()), new TableLayout.LayoutParams(
                        TableRow.LayoutParams.FILL_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT));
            }
        }
    }



}
