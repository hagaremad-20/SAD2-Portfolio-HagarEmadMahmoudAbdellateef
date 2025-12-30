package com.mycompany.roky;
public interface PaymentMethod {
    Payment createPaymentForOrder(Order order);
    boolean processPayment(Payment payment);
}
