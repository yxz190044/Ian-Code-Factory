package com.codeuptopia.secondhandmarket.model;

import com.stripe.model.PaymentIntent;

import java.util.Date;

public class Payment {
    private int id;
    private double amount;
    private Date date;
    private User payer;
    private User payee;
    private String paymentMethodId;
    private PaymentIntent paymentIntent;
    private String transactionId;
    
    public Payment(double amount, Date date, User payer, User payee, String paymentMethodId) {
        this.amount = amount;
        this.date = date;
        this.payer = payer;
        this.payee = payee;
        this.paymentMethodId = paymentMethodId;
    }
    
    public int getId() {
        return id;
    }
    
    public double getAmount() {
        return amount;
    }
    
    public void setAmount(double amount) {
        this.amount = amount;
    }
    
    public Date getDate() {
        return date;
    }
    
    public void setDate(Date date) {
        this.date = date;
    }
    
    public User getPayer() {
        return payer;
    }
    
    public void setPayer(User payer) {
        this.payer = payer;
    }
    
    public User getPayee() {
        return payee;
    }
    
    public void setPayee(User payee) {
        this.payee = payee;
    }
    
    public String getPaymentMethodId() {
        return paymentMethodId;
    }
    
    public void setPaymentMethodId(String paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }
    
    public PaymentIntent getPaymentIntent() {
        return paymentIntent;
    }
    
    public void setPaymentIntent(PaymentIntent paymentIntent) {
        this.paymentIntent = paymentIntent;
    }
    
    public String getTransactionId() {
        return transactionId;
    }
    
    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }
}
