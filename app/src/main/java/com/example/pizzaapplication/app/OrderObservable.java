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
    private int rewardPoints;
    private List<OrderItem> couponItems;

    private OrderObservable() {
        order = new Order();
        dailySpecials = new ArrayList<>();
        coupons = new ArrayList<>();
        couponItems = new ArrayList<>();
    }

    public void clearOrder(){
        order = new Order();
        dailySpecials.clear();
        coupons.clear();
        couponItems.clear();
        price = 0;
        rewardPoints = 0;
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
        this.couponItems.add(new OrderItem(coupon.getMenuItem(), 1));
        triggerObservers();
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

    public int getRewardPoints() {
        return rewardPoints;
    }

    public void setRewardPoints(int rewardPoints) {
        this.rewardPoints = rewardPoints;
    }

    public List<OrderItem> getCouponItems() {
        return couponItems;
    }

    public void setCouponItems(List<OrderItem> couponItems) {
        this.couponItems = couponItems;
    }

    public void removeItem(int removePosition) {
        if(removePosition < this.order.getItems().size()){
            this.order.getItems().remove(removePosition);
        }
        else {
            removePosition = removePosition - this.order.getItems().size();
            this.couponItems.remove(removePosition);
            this.rewardPoints += this.coupons.get(removePosition).getRewardPoints();
            this.coupons.remove(removePosition);
        }
    }
}
