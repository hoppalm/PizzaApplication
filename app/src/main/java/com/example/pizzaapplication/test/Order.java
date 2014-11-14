package com.example.pizzaapplication.test;

import java.util.ArrayList;
import java.util.List;

//TODO DELETE
public class Order {

    private List<OrderItem> items;

    public Order() {
        items = new ArrayList<>();
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }
}
