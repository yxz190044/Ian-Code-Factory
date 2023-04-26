package com.codeuptopia.secondhandmarket.dao;

import com.codeuptopia.secondhandmarket.model.Review;

public interface ReviewDao {
    Review getReviewById(int id);
    Review createReview(Review review);
    void updateReview(Review review);
    void deleteReview(int id);
}
