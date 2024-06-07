package com.example.payment.controller;

import com.example.payment.dtos.PaymentRequestDto;
import com.example.payment.service.PaymentServices;
import com.stripe.exception.StripeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentsController {
    //injecting PaymentServices
    PaymentServices paymentService;
    PaymentsController(PaymentServices paymentService) {
        this.paymentService = paymentService;
    }
    //now let's create a body for the information we will get from the client
    @PostMapping("/payments")
    public ResponseEntity<String> createPayment(@RequestBody PaymentRequestDto paymentRequestDto) throws StripeException {
        String paymentLink = paymentService.makePayment(paymentRequestDto.getOrderId(), paymentRequestDto.getPaymentAmount());
        return new ResponseEntity<>(paymentLink, HttpStatus.OK);
    }
}
