package com.example.payment.service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentLink;
import com.stripe.model.Price;
import com.stripe.param.PaymentLinkCreateParams;
import com.stripe.param.PriceCreateParams;
import org.springframework.stereotype.Service;

@Service
public class StripePaymentImp implements PaymentServices{
    @Override
    public String makePayment(String orderId, Long amount) throws StripeException {
        Stripe.apiKey = "Use secret Key Provided by Stripe";

        PriceCreateParams params =
                PriceCreateParams.builder()
                        .setCurrency("INR")
                        .setUnitAmount(amount)
                        //don't want this part for now
//                        .setRecurring(
//                                PriceCreateParams.Recurring.builder()
//                                        .setInterval(PriceCreateParams.Recurring.Interval.MONTH)
//                                        .build()
//                        )
                        .setProductData(
                                PriceCreateParams.ProductData.builder().setName("Gold Plan").build()
                        )
                        .build();
        //Now this will hit the stripe account, and it will create a unique id for this payment
        //So that one can use this id to create a payment
        Price price = Price.create(params);

        PaymentLinkCreateParams paymentParams =
                PaymentLinkCreateParams.builder()
                        .addLineItem(
                                PaymentLinkCreateParams.LineItem.builder()
                                        .setPrice(price.getId())//its has in build getId()
                                        .setQuantity(1L)//No. of time's we want to collect the amount
                                        .build()
                        )
                        //After payment done we can redirect to successful payment page
                        .setAfterCompletion(
                                PaymentLinkCreateParams.AfterCompletion.builder()
                                        .setType(PaymentLinkCreateParams.AfterCompletion.Type.REDIRECT)
                                        .setRedirect(
                                                PaymentLinkCreateParams.AfterCompletion.Redirect.builder()
                                                        .setUrl("https://www.amazon.in/")
                                                        .build()
                                        )
                                        .build()
                        )
                        .build();

        PaymentLink paymentLink = PaymentLink.create(paymentParams);
        return paymentLink.getUrl();//in build method getUrl()
    }
}
