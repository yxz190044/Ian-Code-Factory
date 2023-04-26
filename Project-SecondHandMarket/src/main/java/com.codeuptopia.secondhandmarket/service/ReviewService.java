
package com.codeuptopia.secondhandmarket.service;

import com.codeuptopia.secondhandmarket.dao.ReviewDao;
import com.codeuptopia.secondhandmarket.model.Item;
import com.codeuptopia.secondhandmarket.model.User;
import com.codeuptopia.secondhandmarket.model.Review;
import java.util.List;

public class ReviewService {
    private ReviewDao reviewDao;
    
    public ReviewService(ReviewDao reviewDao) {
        this.reviewDao = reviewDao;
    }
    
    public Review createReview(User reviewer, User reviewedUser, Item item, int rating, String comment) {
        Review review = new Review(reviewer, reviewedUser, item, rating, comment);
        reviewDao.save(review);
        return review;
    }
    
    public List<Review> getReviewsForUser(User user) {
        return reviewDao.findByReviewedUser(user);
    }
    
    public double getAverageRatingForUser(User user) {
        return reviewDao.getAverageRatingForReviewedUser(user);
    }
    
    public void updateReview(Review review, int rating, String comment) {
        review.setRating(rating);
        review.setComment(comment);
        reviewDao.update(review);
    }
    
    public void deleteReview(Review review) {
        reviewDao.delete(review);
    }
}
