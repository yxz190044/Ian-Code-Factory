package com.codeuptopia.secondhandmarket.controller;

import com.google.inject.Inject;
import com.codeuptopia.secondhandmarket.model.Review;
import com.codeuptopia.secondhandmarket.service.ReviewService;
import io.javalin.http.Context;

import java.util.List;

public class ReviewController {
    
    private final ReviewService reviewService;
    
    @Inject
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }
    
    public void getReview(Context ctx) {
        String reviewId = ctx.pathParam("id");
        Review review = reviewService.getReviewById(reviewId);
        if (review != null) {
            ctx.json(review);
        } else {
            ctx.status(404);
        }
    }
    
    public void getAllReviews(Context ctx) {
        List<Review> reviews = reviewService.getAllReviews();
        ctx.json(reviews);
    }
    
    public void addReview(Context ctx) {
        Review review = ctx.bodyAsClass(Review.class);
        reviewService.addReview(review);
        ctx.status(201);
    }
    
    public void updateReview(Context ctx) {
        String reviewId = ctx.pathParam("id");
        Review review = ctx.bodyAsClass(Review.class);
        review.setId(reviewId);
        reviewService.updateReview(review);
        ctx.status(204);
    }
    
    public void deleteReview(Context ctx) {
        String reviewId = ctx.pathParam("id");
        reviewService.deleteReview(reviewId);
        ctx.status(204);
    }
    
}
