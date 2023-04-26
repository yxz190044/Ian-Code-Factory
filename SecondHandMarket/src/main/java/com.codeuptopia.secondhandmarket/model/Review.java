
package com.codeuptopia.secondhandmarket.model;

public class Review {
    private int id;
    private int userId;
    private int itemId;
    private String comment;
    private int rating;
    
    public Review() {
    }
    
    public Review(int userId, int itemId, String comment, int rating) {
        this.userId = userId;
        this.itemId = itemId;
        this.comment = comment;
        this.rating = rating;
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getUserId() {
        return userId;
    }
    
    public void setUserId(int userId) {
        this.userId = userId;
    }
    
    public int getItemId() {
        return itemId;
    }
    
    public void setItemId(int itemId) {
        this.itemId = itemId;
    }
    
    public String getComment() {
        return comment;
    }
    
    public void setComment(String comment) {
        this.comment = comment;
    }
    
    public int getRating() {
        return rating;
    }
    
    public void setRating(int rating) {
        this.rating = rating;
    }
}
