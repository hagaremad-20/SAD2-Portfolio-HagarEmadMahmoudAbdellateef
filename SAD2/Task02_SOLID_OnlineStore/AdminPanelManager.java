package com.mycompany.roky;
import java.util.List;
import java.util.Scanner;
public class AdminPanelManager {
    private Admin admin;
    private Scanner sc;
    private Store store;
    public AdminPanelManager(Admin admin, Scanner sc, Store store) {
        this.admin = admin;
        this.sc = sc;
        this.store = store;
    }
    public void showAdminPanel() {
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
            int choice = readIntSafely();
            switch (choice) {
                case 1: addProduct(); break;
                case 2: updateProduct(); break;
                case 3: removeProduct(); break;
                case 4: addCategory(); break;
                case 5: viewProducts(); break;
                case 6: adminPanel = false; break;
                default: System.out.println("Invalid choice!");
            }
        }
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
    private void addProduct() {
        System.out.print("Enter product ID: ");
        int id = readIntSafely();
        System.out.print("Enter product name: ");
        String name = sc.nextLine();
        System.out.print("Enter price: ");
        double price = Double.parseDouble(sc.nextLine());
        System.out.print("Enter quantity: ");
        int qty = readIntSafely();
        System.out.println("Choose category:");
        List<Category> cats = store.getCategories();
        for (int i = 0; i < cats.size(); i++)
            System.out.println((i+1) + ") " + cats.get(i).getName());
        System.out.print("Enter category number: ");
        int catChoice = readIntSafely();
        Category cat = cats.get(Math.max(0, Math.min(catChoice-1, cats.size()-1)));
        Product newProduct = new Product(id, name, price, qty, cat);
        admin.addProduct(newProduct, store);
    }
    private void updateProduct() {
        System.out.print("Enter product ID to update: ");
        int id = readIntSafely();
        System.out.print("Enter new name: ");
        String name = sc.nextLine();
        System.out.print("Enter new price: ");
        double price = Double.parseDouble(sc.nextLine());
        System.out.print("Enter new quantity: ");
        int qty = readIntSafely();
        System.out.println("Choose new category:");
        List<Category> cats = store.getCategories();
        for (int i = 0; i < cats.size(); i++)
            System.out.println((i+1) + ") " + cats.get(i).getName());
        System.out.print("Enter category number: ");
        int catChoice = readIntSafely();
        Category cat = cats.get(Math.max(0, Math.min(catChoice-1, cats.size()-1)));
        Product upd = new Product(id, name, price, qty, cat);
        admin.updateProduct(upd, store);
    }
    private void removeProduct() {
        System.out.print("Enter product ID to remove: ");
        int id = readIntSafely();
        admin.removeProduct(id, store);
    }
    private void addCategory() {
        System.out.print("Enter new category ID: ");
        int id = readIntSafely();
        System.out.print("Enter new category name: ");
        String name = sc.nextLine();
        System.out.print("Enter category description: ");
        String desc = sc.nextLine();
        Category cat = new Category(id, name, desc);
        admin.addCategory(cat, store);
    }
    private void viewProducts() {
        System.out.println("\n--- Products List ---");
        for (Product p : store.getProducts())
            System.out.println(p.getProductID() + " - " + p.getName() + " | $" + p.getPrice());
    }
}
