package com.example.pizzaapplication.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

//ADD REFERENCE TO ORDER, COUPONS, DAILYSPECIALS
public class OrderObservable extends Observable {
    public static OrderObservable instance;

    private Order order;
    private List<DailySpecial> dailySpecials;

    private OrderObservable() {
        order = new Order();
        dailySpecials = new ArrayList<>();
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
    //testing purposes
    public void Update(){
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
}
