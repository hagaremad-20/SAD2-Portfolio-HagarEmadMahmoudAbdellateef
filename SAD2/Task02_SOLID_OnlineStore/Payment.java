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
    public int getPaymentID() { return paymentID; }
    public int getOrderID() { return orderID; }
    public double getAmount() { return amount; }
    public String getPaymentMethod() { return paymentMethod; }
    public boolean isPaid() { return isPaid; }
    public void setPaid(boolean paid) { isPaid = paid; }
    public void displayPayment() {
        System.out.println("Payment ID: " + paymentID +
                " | Order ID: " + orderID +
                " | Amount: $" + amount +
                " | Method: " + paymentMethod +
                " | Paid: " + isPaid);
    }
}
