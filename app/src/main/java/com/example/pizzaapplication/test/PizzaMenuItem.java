package com.example.pizzaapplication.test;


    //TODO delete later, JUST FOR TESTING PURPOSE!!!!!!
    public class PizzaMenuItem {
        private String name;
        private int price;
        private String description;

        public PizzaMenuItem(String description, String name, int price) {
            this.description = description;
            this.name = name;
            this.price = price;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        @Override
        public String toString() {
            return name + " $" + price;
        }
    }

