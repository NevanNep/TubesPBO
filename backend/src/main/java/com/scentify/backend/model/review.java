package com.scentify.backend.model;

import java.util.Date;

public class review {

    private String reviewId;
    private Buyer buyer;
    private Product product;
    private int rating;
    private String comment;
    private Date createdAt;
    private String user;
    private String productName;
    private String userName;    

    public review() {
    }

    public review(String reviewId, Buyer buyer, Product product, int rating, String comment, Date createdAt) {
        this.reviewId = reviewId;
        this.buyer = buyer;
        this.product = product;
        this.rating = rating;
        this.comment = comment;
        this.createdAt = createdAt;  
    }

    public String getReviewId() {
        return reviewId;
    }

    public void setReviewId(String reviewId) {
        this.reviewId = reviewId;
    }

    public Buyer getBuyer() {
        return buyer;
    }

    public void setBuyer(Buyer buyer) {
        this.buyer = buyer;
    }

    public Product getProduct() {
        return this.product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getProductName() {
        return productName;
    }

    public String getUsername() {
        return userName;
    }


    public String getReviewInfo() {
    return "Review ID: " + reviewId + "\n" +
           "Rating: " + rating + "\n" +
           "Review: " + comment + "\n" +
           "Product: " + (product != null ? product.toString() : "N/A") + "\n" +
           "User: " + (user != null ? user.toString() : "N/A");
    }

}