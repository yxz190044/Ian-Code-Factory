package com.codeuptopia.secondhandmarket.dao;

import com.codeuptopia.secondhandmarket.model.Review;
import com.codeuptopia.secondhandmarket.database.DatabaseConnection;

public class ReviewDaoImpl implements ReviewDao {
    private DatabaseConnection databaseConnection;
    
    public ReviewDaoImpl(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }
    
    public Review getReviewById(int id) {
        // TODO: Implement getReviewById method
        return null;
    }
    
    public Review createReview(Review review) {
        // TODO: Implement createReview method
        return null;
    }
    
    public void updateReview(Review review) {
        // TODO: Implement updateReview method
    }
    
    public void deleteReview(int id) {
        // TODO: Implement deleteReview method
    }
}
