package com.mycompany.roky;

import java.util.ArrayList;
import java.util.Scanner;

public class Roky {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // ===== Categories =====
        Category Makeup = new Category(1, "Makeup", "Beauty");
        Category Clothes = new Category(2, "Clothing", "Fashion");
        Category Accessories = new Category(3, "Accessories", "Jewelry");
        Store.categories.add(Makeup);
        Store.categories.add(Clothes);
        Store.categories.add(Accessories);

        // ===== Products =====
        Product p1 = new Product(1, "Lipstick", 19.99, 50, Makeup);
        Product p2 = new Product(2, "Dress", 49.99, 20, Clothes);
        Product p3 = new Product(3, "Gold Necklace", 29.50, 10, Accessories);
        Product p4 = new Product(4, "Eyeliner", 15.00, 30, Makeup);
        Product p5 = new Product(5, "Bracelet", 25.00, 15, Accessories);
        Store.products.add(p1);
        Store.products.add(p2);
        Store.products.add(p3);
        Store.products.add(p4);
        Store.products.add(p5);

        // ===== Login / Customer Info =====
        System.out.println("Welcome to Roky Shop");
        System.out.print("Enter your name: ");
        String name = sc.nextLine();
        System.out.print("Enter your email: ");
        String email = sc.nextLine();
        System.out.print("Enter your phone: ");
        String phone = sc.nextLine();
        System.out.print("Enter your address: ");
        String address = sc.nextLine();
        System.out.print("Enter a password: ");
        String password = sc.nextLine();

        Customer customer = new Customer(5001, 1001, name, email, phone, address, password);
        System.out.println("\nWelcome, " + customer.getName());

        // ===== Admin Login =====
        System.out.print("\nAre you admin? (yes/no): ");
        String isAdmin = sc.nextLine().toLowerCase();
        if (isAdmin.equals("yes")) {
            Admin admin = new Admin(101, "Admin1", "admin@gmail.com", "01012345678", "Cairo", "admin123");
            boolean adminPanel = true;

            while (adminPanel) {
                System.out.println("\n--- Admin Panel ---");
                System.out.println("1) Add Product");
                System.out.println("2) Update Product");
                System.out.println("3) Remove Product");
                System.out.println("4) Add Category");   
                System.out.println("5) View Products");
                System.out.println("6) Exit Admin");
                System.out.print("Choose option: ");
                int choice = sc.nextInt();
                sc.nextLine();

                switch (choice) {
                    case 1: // Add Product
                        System.out.print("Enter product ID: ");
                        int id = sc.nextInt();
                        sc.nextLine();
                        System.out.print("Enter product name: ");
                        String prodName = sc.nextLine();
                        System.out.print("Enter price: ");
                        double price = sc.nextDouble();
                        System.out.print("Enter quantity: ");
                        int qty = sc.nextInt();
                        sc.nextLine();

                        System.out.println("Choose category:");
                        for (int i = 0; i < Store.categories.size(); i++)
                            System.out.println((i + 1) + ") " + Store.categories.get(i).getName());
                        System.out.print("Enter category number: ");
                        int catChoice = sc.nextInt();
                        sc.nextLine();
                        Category cat = Store.categories.get(Math.max(0, Math.min(catChoice - 1, Store.categories.size() - 1)));

                        Product newProduct = new Product(id, prodName, price, qty, cat);
                        admin.addProduct(newProduct);
                        break;

                    case 2: // Update Product
                        System.out.print("Enter product ID to update: ");
                        int updId = sc.nextInt();
                        sc.nextLine();
                        System.out.print("Enter new name: ");
                        String updName = sc.nextLine();
                        System.out.print("Enter new price: ");
                        double updPrice = sc.nextDouble();
                        System.out.print("Enter new quantity: ");
                        int updQty = sc.nextInt();
                        sc.nextLine();

                        System.out.println("Choose new category:");
                        for (int i = 0; i < Store.categories.size(); i++)
                            System.out.println((i + 1) + ") " + Store.categories.get(i).getName());
                        System.out.print("Enter category number: ");
                        int updCatChoice = sc.nextInt();
                        sc.nextLine();
                        Category updCat = Store.categories.get(Math.max(0, Math.min(updCatChoice - 1, Store.categories.size() - 1)));

                        Product updProd = new Product(updId, updName, updPrice, updQty, updCat);
                        admin.updateProduct(updProd);
                        break;

                    case 3: // Remove Product
                        System.out.print("Enter product ID to remove: ");
                        int remId = sc.nextInt();
                        sc.nextLine();
                        admin.removeProduct(remId);
                        break;
                        
                    case 4: // Add Category 
                        System.out.print("Enter new category ID: ");
                        int catId = sc.nextInt();
                        sc.nextLine();
                        System.out.print("Enter new category name: ");
                        String catName = sc.nextLine();
                        System.out.print("Enter category description: ");
                        String catDesc = sc.nextLine();
                        Category newCat = new Category(catId, catName, catDesc);
                        admin.addCategory(newCat);
                        break;

                    case 5: // View Products
                        System.out.println("\n--- Products List ---");
                        for (Product p : Store.products)
                            System.out.println(p.getProductID() + " - " + p.getName() + " | $" + p.getPrice());
                        break;

                    case 6: // Exit Admin
                        adminPanel = false;
                        break;

                    default:
                        System.out.println("Invalid choice!");
                }
            }
        }

        // ===== Customer Shopping =====
        boolean shopping = true;
        while (shopping) {
            System.out.println("\nAvailable Categories:");
            for (int i = 0; i < Store.categories.size(); i++)
                System.out.println((i + 1) + ") " + Store.categories.get(i).getName());
            System.out.print("Choose a category number: ");
            int catChoice = sc.nextInt();
            sc.nextLine();

            System.out.println("\nProducts in this category:");
            for (Product p : Store.products) {
                if (p.getCategory() == Store.categories.get(catChoice - 1)) {
                    System.out.println(p.getProductID() + ") " + p.getName() + " - $" + p.getPrice());
                }
            }

            System.out.print("Enter product ID to add to cart: ");
            int pid = sc.nextInt();
            System.out.print("Enter quantity: ");
            int qty = sc.nextInt();
            sc.nextLine();

            
            Product selected = null;
            for (Product p : Store.products) {
                if (p.getProductID() == pid && p.getCategory() == Store.categories.get(catChoice - 1)) {
                    selected = p;
                    break;
                }
            }

            if (selected != null) {
                customer.addToCart(selected, qty);
            } else {
                System.out.println("Invalid product ID for this category!");
            }

            System.out.print("Add more products? (yes/no): ");
            String more = sc.nextLine().toLowerCase();
            if (more.equals("no")) shopping = false;


        // ===== Cart Summary =====
        System.out.println("\nYour Cart Items:");
        customer.getCart().viewCart();

        System.out.print("\nDo you want to remove a product from the cart? (yes/no): ");
        String removeChoice = sc.nextLine().toLowerCase();
        if (removeChoice.equals("yes")) {
            System.out.print("Enter product name to remove: ");
            String removeName = sc.nextLine();
            Product toRemove = null;
            for (Product p : Store.products)
                if (p.getName().equalsIgnoreCase(removeName)) toRemove = p;
            if (toRemove != null) customer.removeFromCart(toRemove);
            else System.out.println("Product not found in cart.");
        }

        // ===== Place Order =====
        Order order = customer.placeOrder();
        if (order == null) {
            System.out.println("Could not create order.");
            return;
        }
        System.out.println("\nOrder Summary:");
        order.displayOrder();

        // ===== Payment =====
        Payment payment = new Payment(9001, order.getOrderID(), order.getTotalAmount(), "Credit Card");
        System.out.println("\nProcessing payment...");
        payment.processPayment();
        payment.displayPayment();

        System.out.println("\nYour order has been placed successfully!");
        sc.close();
    }
    }
}