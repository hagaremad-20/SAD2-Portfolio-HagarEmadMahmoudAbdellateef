package com.mycompany.roky;

import java.util.ArrayList;
import java.util.Scanner;

public class Cart {

    private int customerID;
   
        
    //  Inner class 
         private class CartItem {
        Product product;
        int quantity;
        CartItem(Product product, int quantity) {
            this.product = product;
            this.quantity = quantity;
        }
    }

    private ArrayList<CartItem> items;

    public Cart(int customerID) {
        this.customerID = customerID;
        this.items = new ArrayList<>();
    }
    
    //additem
    public void addItem(Product product, int quantity) {
    items.add(new CartItem(product, quantity));
    System.out.println(product.getName() + " added to cart.");
}
 
    //Total
    public double calculateTotal() {
        double total = 0;
        for (CartItem item : items) {
            total += item.product.getPrice() * item.quantity;
        }
        return total;
    }

    //  order
    public Order checkout() {
        if (items.isEmpty()) {
            System.out.println("Cart is empty. Cannot checkout.");
            return null;
        }
        ArrayList<Order.OrderItem> orderItems = new ArrayList<>();
        for (CartItem item : items) {
            orderItems.add(new Order.OrderItem(item.product, item.quantity));
        }
        double total = calculateTotal();
        return new Order(customerID, orderItems, total);
    }

     public void removeItem(Product product) {
        if (product == null) return;

        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).product.equals(product)) {
                items.remove(i);
                System.out.println(product.getName() + " removed from cart.");
                return;
            }
        }}
    public void viewCart() {
    System.out.println("\nYour Cart:");
    for (CartItem item : items) {
        System.out.println("- " + item.product.getName() + " x " + item.quantity);
    }
}





}