package com.codeuptopia.secondhandmarket.dao;

import com.codeuptopia.secondhandmarket.model.Payment;
import com.codeuptopia.secondhandmarket.database.DatabaseConnection;

public class PaymentDaoImpl implements PaymentDao {
    private DatabaseConnection databaseConnection;
    
    public PaymentDaoImpl(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }
    
    public Payment getPaymentById(int id) {
        // TODO: Implement getPaymentById method
        return null;
    }
    
    public Payment createPayment(Payment payment) {
        // TODO: Implement createPayment method
        return null;
    }
    
    public void updatePayment(Payment payment) {
        // TODO: Implement updatePayment method
    }
    
    public void deletePayment(int id) {
        // TODO: Implement deletePayment method
    }
}
