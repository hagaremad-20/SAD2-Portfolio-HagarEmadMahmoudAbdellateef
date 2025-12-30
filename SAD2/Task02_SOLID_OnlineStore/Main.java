package com.mycompany.roky;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        StoreInitializer.initializeStore();
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
        System.out.print("\nAre you admin? (yes/no): ");
        String adminAnswer = sc.nextLine();
        if (adminAnswer.equalsIgnoreCase("yes")) {
            Admin admin = new Admin(101, "Admin1", "admin@gmail.com", "01012345678", "Cairo", "admin123");
            AdminPanelManager adminPanel = new AdminPanelManager(admin, sc, Store.getInstance());
            adminPanel.showAdminPanel();
        }
Store store = Store.getInstance();   
PaymentMethod paymentProcessor = new CreditCardPaymentProcessor(); 
CustomerShoppingManager shopping =
        new CustomerShoppingManager(customer, sc, store, paymentProcessor);
shopping.startShopping();
        sc.close();
    }
}
