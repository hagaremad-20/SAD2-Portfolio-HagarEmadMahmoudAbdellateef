package com.mycompany.roky;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
public class Cart {
    private int customerID;
    private static class CartItem {
        Product product;
        int quantity;
        CartItem(Product product, int quantity) {
            this.product = product;
            this.quantity = quantity;
        }
    }
    private final List<CartItem> items;
    public Cart(int customerID) {
        this.customerID = customerID;
        this.items = new ArrayList<>();
    }
    public void addItem(Product product, int quantity) {
        if (product == null || quantity <= 0) return;
        for (CartItem it : items) {
            if (it.product.getProductID() == product.getProductID()) {
                it.quantity += quantity;
                System.out.println(product.getName() + " quantity updated in cart.");
                return;
            }
        }
        items.add(new CartItem(product, quantity));
        System.out.println(product.getName() + " added to cart.");
    }
    public double calculateTotal() {
        double total = 0;
        for (CartItem item : items) {
            total += item.product.getPrice() * item.quantity;
        }
        return total;
    }
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
            if (items.get(i).product.getProductID() == product.getProductID()) {
                items.remove(i);
                System.out.println(product.getName() + " removed from cart.");
                return;
            }
        }
        System.out.println("Product not found in cart.");
    }
    public void viewCart() {
        System.out.println("\nYour Cart:");
        if (items.isEmpty()) {
            System.out.println("- (empty)");
            return;
        }
        for (CartItem item : items) {
            System.out.println("- " + item.product.getName() + " x " + item.quantity);
        }
        System.out.println("Cart Total: $" + calculateTotal());
    }
    public boolean isEmpty() {
        return items.isEmpty();
    }
}
