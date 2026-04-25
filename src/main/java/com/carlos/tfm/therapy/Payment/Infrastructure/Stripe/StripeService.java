package com.carlos.tfm.therapy.Payment.Infrastructure.Stripe;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class StripeService {

    public String createPaymentIntent() {
        return "pi_" + UUID.randomUUID();
    }

    public boolean confirmPayment(String externalReference) {
        return true;
    }
}