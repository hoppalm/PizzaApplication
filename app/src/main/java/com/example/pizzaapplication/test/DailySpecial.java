package com.example.pizzaapplication.test;

import java.util.LinkedList;
import java.util.List;

//TODO DELETE
public class DailySpecial {
    private double price;
    private final List<PizzaMenuItem> items;

    public DailySpecial(double price, List<PizzaMenuItem> items) {
        this.price = price;
        this.items = items;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<PizzaMenuItem> getItems() {
        return items;
    }

    @Override
    public String toString() {
        String output = "";
        output += "[";
        for (PizzaMenuItem menuItem : this.items){
            output += menuItem.getName() + ", ";
        }
        output += "] $" + String.valueOf(price);
        return output;
    }

}
