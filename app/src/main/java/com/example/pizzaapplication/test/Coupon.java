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

    public PizzaMenuItem getMenuItem() {
        return menuItem;
    }

    public void setMenuItem(PizzaMenuItem menuItem) {
        this.menuItem = menuItem;
    }

    public int getRewardPoints() {
        return rewardPoints;
    }

    public void setRewardPoints(int rewardPoints) {
        this.rewardPoints = rewardPoints;
    }
}
