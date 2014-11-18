package com.example.pizzaapplication.app;

import edu.colostate.cs414.d.pizza.api.menu.Coupon;
import edu.colostate.cs414.d.pizza.api.menu.DailySpecial;
import edu.colostate.cs414.d.pizza.api.menu.PizzaMenuItem;
import edu.colostate.cs414.d.pizza.api.order.Order;
import edu.colostate.cs414.d.pizza.api.order.OrderItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class OrderObservable extends Observable {
    public static OrderObservable instance;

    private Order order;
    private List<DailySpecial> dailySpecials;
    private List<Coupon> coupons;
    private double price;

    private OrderObservable() {
        order = new Order();
        dailySpecials = new ArrayList<>();
        coupons = new ArrayList<>();
    }

    public static OrderObservable getInstance() {
        if (instance == null) {
            instance = new OrderObservable();
        }
        return instance;
    }

    public void addOrderItem(OrderItem orderItem){
        order.getItems().add(orderItem);
        triggerObservers();
    }

    public void addDailySpecial(DailySpecial dailySpecial){
        dailySpecials.add(dailySpecial);
        for(PizzaMenuItem menuItem : dailySpecial.getItems()){
            order.getItems().add(new OrderItem(menuItem,1));
        }
        triggerObservers();
    }

    public void addCoupon(Coupon coupon){
        coupons.add(coupon);
        addOrderItem(new OrderItem(coupon.getMenuItem(), 1));
    }

    private void triggerObservers() {
        setChanged();
        notifyObservers();
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public List<DailySpecial> getDailySpecials() {
        return dailySpecials;
    }

    public void setDailySpecials(List<DailySpecial> dailySpecials) {
        this.dailySpecials = dailySpecials;
    }

    public List<Coupon> getCoupons() {
        return coupons;
    }

    public void setCoupons(List<Coupon> coupons) {
        this.coupons = coupons;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
