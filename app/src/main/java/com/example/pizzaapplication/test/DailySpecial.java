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

}
