package com.example.pizzaapplication.test;

//TODO DELETE
public class OrderItem {
    private PizzaMenuItem item;
    private int quantity;

    public OrderItem(PizzaMenuItem item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }

    public PizzaMenuItem getItem() {
        return item;
    }

    public void setItem(PizzaMenuItem item) {
        this.item = item;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return item.getName() + " qty:" + String.valueOf(quantity);
    }
}
