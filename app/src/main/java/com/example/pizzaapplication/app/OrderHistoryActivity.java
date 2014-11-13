package com.example.pizzaapplication.app;

import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;


public class OrderHistoryActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);


        TableLayout tableLayout = (TableLayout) findViewById(R.id.orderHistoryTable);
        //TODO: Loop through the orderItem and build a table
        tableLayout.addView(getTableRow(1,"pizza",2), new TableLayout.LayoutParams(
                TableRow.LayoutParams.FILL_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));
        tableLayout.addView(getTableRow(10,"breadsticks",1), new TableLayout.LayoutParams(
                TableRow.LayoutParams.FILL_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));
        tableLayout.addView(getTableRow(11,"cinnisticks",5), new TableLayout.LayoutParams(
                TableRow.LayoutParams.FILL_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));
        tableLayout.addView(getTableRow(14,"soda",7), new TableLayout.LayoutParams(
                TableRow.LayoutParams.FILL_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));
        tableLayout.addView(getTableRow(15,"pizza",2), new TableLayout.LayoutParams(
                TableRow.LayoutParams.FILL_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));

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

    public TableRow getTableRow(int orderID, String menuItem, int qty){
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
        labelMenuItem.setText(menuItem);
        labelMenuItem.setTextColor(Color.BLACK);
        labelMenuItem.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.FILL_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));
        tr.addView(labelMenuItem);

        //add qty
        TextView labelQty = new TextView(this);
        labelQty.setText(String.valueOf(qty));
        labelQty.setTextColor(Color.BLACK);
        labelQty.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.FILL_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));
        tr.addView(labelQty);

        return tr;
    }



}
