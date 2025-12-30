package com.mycompany.roky;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
public class CustomerShoppingManager {
    private Customer customer;
    private Scanner sc;
    private Store store;
    private PaymentMethod paymentProcessor;
    public CustomerShoppingManager(Customer customer, Scanner sc, Store store, PaymentMethod paymentProcessor) {
        this.customer = customer;
        this.sc = sc;
        this.store = store;
        this.paymentProcessor = paymentProcessor;
    }
    private int readIntSafely() {
        while (true) {
            try {
                int n = Integer.parseInt(sc.nextLine());
                return n;
            } catch (NumberFormatException ex) {
                System.out.print("Please enter a valid number: ");
            }
        }
    }
    public void startShopping() {
        boolean shopping = true;
        while (shopping) {
            System.out.println("\nAvailable Categories:");
            List<Category> cats = store.getCategories();
            for (int i = 0; i < cats.size(); i++)
                System.out.println((i+1) + ") " + cats.get(i).getName());
            System.out.print("Choose a category number: ");
            int catChoice = readIntSafely();
            Optional<Category> maybeCat = store.findCategoryByIndex(catChoice - 1);
            if (maybeCat.isEmpty()) {
                System.out.println("Invalid category choice.");
                continue;
            }
            Category selectedCat = maybeCat.get();
            System.out.println("\nProducts in this category:");
            for (Product p : store.getProducts()) {
                if (p.getCategory().getCategoryID() == selectedCat.getCategoryID())
                    System.out.println(p.getProductID() + ") " + p.getName() + " - $" + p.getPrice() + " (stock: " + p.getStockQuantity() + ")");
            }
            System.out.print("Enter product ID to add to cart (or 0 to skip): ");
            int pid = readIntSafely();
            if (pid == 0) {
            } else {
                Optional<Product> selected = store.findProductById(pid);
                if (selected.isPresent() && selected.get().getCategory().getCategoryID() == selectedCat.getCategoryID()) {
                    System.out.print("Enter quantity: ");
                    int qty = readIntSafely();
                    customer.addToCart(selected.get(), qty);
                } else {
                    System.out.println("Invalid product ID for this category!");
                }
            }
            System.out.print("Add more products? (yes/no): ");
            if (sc.nextLine().equalsIgnoreCase("no")) shopping = false;
        }
        customer.getCart().viewCart();
        System.out.print("\nDo you want to remove a product from the cart? (yes/no): ");
        if (sc.nextLine().equalsIgnoreCase("yes")) {
            System.out.print("Enter product ID to remove: ");
            int idToRemove = readIntSafely();
            store.findProductById(idToRemove).ifPresentOrElse(
                    product -> customer.removeFromCart(product),
                    () -> System.out.println("Product not found.")
            );
        }
        Order order = customer.placeOrder();
        if (order != null) {
            System.out.println("\nOrder Summary:");
            order.displayOrder();
            Payment payment = paymentProcessor.createPaymentForOrder(order);
            System.out.println("\nProcessing payment...");
            boolean success = paymentProcessor.processPayment(payment);
            payment.displayPayment();
            if (success) {
                System.out.println("\nYour order has been placed successfully!");
            } else {
                System.out.println("\nPayment failed. Please try again.");
            }
        }
    }
}
