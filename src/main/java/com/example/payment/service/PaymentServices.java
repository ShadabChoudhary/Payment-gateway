package com.example.payment.service;

import com.stripe.exception.StripeException;

public interface PaymentServices {
    String makePayment(String orderId,  Long amount) throws StripeException;
}
