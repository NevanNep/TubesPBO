package com.scentify.backend.model;

import jakarta.persistence.*;

@Entity
public class review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    private String komentar;
    private int rating;
    private String userName;

    @ManyToOne
    @JoinColumn(name = "buyer_id")
    private Buyer buyer;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    // Getter dan Setter
    public Long getReviewId() { return reviewId; }
    public void setReviewId(Long reviewId) { this.reviewId = reviewId; }

    public String getKomentar() { return komentar; }
    public void setKomentar(String komentar) { this.komentar = komentar; }

    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public Buyer getBuyer() { return buyer; }
    public void setBuyer(Buyer buyer) { this.buyer = buyer; }

    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }
}
