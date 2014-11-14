package com.example.pizzaapplication.test;

//TODO DELETE
public class Coupon {
    private PizzaMenuItem menuItem;
    private int rewardPoints;

    public Coupon(PizzaMenuItem menuItem, int rewardPoints) {
        this.menuItem = menuItem;
        this.rewardPoints = rewardPoints;
    }

    @Override
    public String toString() {
        return menuItem.getName() + "-" + rewardPoints +" points";
    }
}
