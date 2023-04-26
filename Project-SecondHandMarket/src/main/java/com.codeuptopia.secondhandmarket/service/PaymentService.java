package com.codeuptopia.secondhandmarket.service;

import com.codeuptopia.secondhandmarket.dao.PaymentDao;
import com.codeuptopia.secondhandmarket.model.Payment;
import com.codeuptopia.secondhandmarket.model.User;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;

import java.util.Date;

public class PaymentService {
    private PaymentDao paymentDao;
    
    public PaymentService(PaymentDao paymentDao) {
        this.paymentDao = paymentDao;
    }
    
    public Payment createPayment(double amount, User payer, User payee, String paymentMethodId) {
        Payment payment = new Payment(amount, new Date(), payer, payee, paymentMethodId);
        paymentDao.save(payment);
        return payment;
    }
    
    public Payment getPaymentById(int id) {
        return paymentDao.findById(id);
    }
    
    public void processPayment(Payment payment) throws StripeException {
        Stripe.apiKey = "YOUR_STRIPE_SECRET_KEY";
        
        PaymentIntent paymentIntent = PaymentIntent.create(
                                                           new PaymentIntent.CreateParams()
                                                           .setAmount((long) (payment.getAmount() * 100))
                                                           .setCurrency("usd")
                                                           .setPaymentMethod(payment.getPaymentMethodId())
                                                           .setConfirmationMethod(PaymentIntent.ConfirmationMethod.MANUAL)
                                                           .setConfirm(true)
                                                           );
        
        payment.setPaymentIntent(paymentIntent);
        payment.setTransactionId(paymentIntent.getId());
        
        // Save the updated payment in the database using the PaymentDao
        paymentDao.update(payment);
    }
    
    // Other methods for validating, handling errors, and updating payment status
}
