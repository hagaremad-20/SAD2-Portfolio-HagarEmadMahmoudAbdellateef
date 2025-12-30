package com.mycompany.roky;

public class Payment {
   
    private int paymentID;
    private int orderID;
    private double amount;
    private String paymentMethod; 
    private boolean isPaid;

    public Payment(int paymentID, int orderID, double amount, String paymentMethod) {
        this.paymentID = paymentID;
        this.orderID = orderID;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.isPaid = false;
    }
    
    public void processPayment() {
        this.isPaid = true;
        System.out.println(" Payment successful for Order ID: " + orderID);
    }

    public void displayPayment() {
        System.out.println("Payment ID: " + paymentID +
            " | Order ID: " + orderID +
            " | Amount: $" + amount +
            " | Method: " + paymentMethod +""
           );
    }

}