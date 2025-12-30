package com.mycompany.roky;
import java.util.ArrayList;
public class Order {
    private static int counter = 1;
    private int orderID;
    private int customerID;
    private ArrayList<OrderItem> orderItems;
    private double totalAmount;
    public static class OrderItem {
        private Product product;
        private int quantity;
        public OrderItem(Product product, int quantity) {
            this.product = product;
            this.quantity = quantity;
        }
        public Product getProduct() { return product; }
        public int getQuantity() { return quantity; }
    }
    public Order(int customerID, ArrayList<OrderItem> items, double totalAmount) {
        this.orderID = counter++;
        this.customerID = customerID;
        this.orderItems = new ArrayList<>(items);
        this.totalAmount = totalAmount;
    }
    public int getOrderID() { return orderID; }
    public double getTotalAmount() { return totalAmount; }
    public void displayOrder() {
        System.out.println("\nItems:");
        for (OrderItem item : orderItems) {
            Product p = item.getProduct();
            int q = item.getQuantity();
            System.out.println("- " + p.getName() + " x " + q + " = $" + (p.getPrice() * q));
        }
        System.out.println("\nTotal Amount: $" + totalAmount);
    }
}
