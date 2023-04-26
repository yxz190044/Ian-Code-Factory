
package com.codeuptopia.secondhandmarket.controller;

import com.google.inject.Inject;
import com.codeuptopia.secondhandmarket.model.Payment;
import com.codeuptopia.secondhandmarket.service.PaymentService;
import io.javalin.http.Context;

public class PaymentController {
    
    private final PaymentService paymentService;
    
    @Inject
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }
    
    public void createPayment(Context ctx) {
        Payment payment = ctx.bodyAsClass(Payment.class);
        String paymentUrl = paymentService.createPayment(payment);
        if (paymentUrl != null) {
            ctx.json(paymentUrl);
        } else {
            ctx.status(400);
        }
    }
    
    public void confirmPayment(Context ctx) {
        String paymentId = ctx.pathParam("id");
        boolean confirmed = paymentService.confirmPayment(paymentId);
        if (confirmed) {
            ctx.status(204);
        } else {
            ctx.status(400);
        }
    }
    
    public void getPayment(Context context) {
        String paymentId = context.pathParam("paymentId");
        Payment payment = paymentService.getPayment(paymentId);
        context.json(payment);
    }
    
    public void updatePayment(Context context) {
        String paymentId = context.pathParam("paymentId");
        Payment payment = context.bodyAsClass(Payment.class);
        paymentService.updatePayment(paymentId, payment);
        context.status(204);
    }
    
    public void deletePayment(Context context) {
        String paymentId = context.pathParam("paymentId");
        paymentService.deletePayment(paymentId);
        context.status(204);
    }
    
}

