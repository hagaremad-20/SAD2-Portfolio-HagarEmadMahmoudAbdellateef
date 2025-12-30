package com.mycompany.roky;
public class CreditCardPaymentProcessor implements PaymentMethod {
    private static int paymentCounter = 9000;
    @Override
    public Payment createPaymentForOrder(Order order) {
        return new Payment(++paymentCounter, order.getOrderID(), order.getTotalAmount(), "Credit Card");
    }
    @Override
    public boolean processPayment(Payment payment) {
        payment.setPaid(true);
        System.out.println("Payment processed for Order ID: " + payment.getOrderID());
        return true;
    }
}
