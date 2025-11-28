package com.example.kafkaspringmongo.model;

public class OrderEvent {
    private String id;
    private String item;
    private int quantity;
    private double price;
    private String action; // CREATE, UPDATE, DELETE

    public OrderEvent() {}

    public OrderEvent(String id, String item, int quantity, double price, String action) {
        this.id = id;
        this.item = item;
        this.quantity = quantity;
        this.price = price;
        this.action = action;
    }

    // getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getItem() { return item; }
    public void setItem(String item) { this.item = item; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public String getAction() { return action; }
    public void setAction(String action) { this.action = action; }
}
