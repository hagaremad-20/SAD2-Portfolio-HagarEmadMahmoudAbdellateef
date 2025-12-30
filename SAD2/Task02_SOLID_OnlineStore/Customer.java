package com.mycompany.roky;
public class Customer extends User {
    private int customerID;
    private Cart cart;
    public Customer(int customerID, int userID, String name, String email, String phone, String address, String password) {
        super(userID, name, email, phone, address, password);
        this.customerID = customerID;
        this.cart = new Cart(customerID);
    }
    public Cart getCart() { return cart; }
    public void addToCart(Product product, int quantity) {
        if (product == null || quantity <= 0) {
            System.out.println("Invalid product or quantity.");
            return;
        }
        if (product.getStockQuantity() < quantity) {
            System.out.println("Not enough stock for " + product.getName());
            return;
        }
        cart.addItem(product, quantity);
    }
    public void removeFromCart(Product product) {
        if (cart == null) {
            System.out.println("Cart is empty.");
            return;
        }
        cart.removeItem(product);
    }
    public Order placeOrder() {
        if (cart == null) {
            System.out.println("Cart is empty");
            return null;
        }
        Order order = cart.checkout();
        if (order == null) {
            System.out.println("cart empty");
            return null;
        }
        return order;
    }
}
