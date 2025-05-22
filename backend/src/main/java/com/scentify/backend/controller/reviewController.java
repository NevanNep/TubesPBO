package com.scentify.backend.controller;

import com.scentify.backend.model.review;
import com.scentify.backend.service.reviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class reviewController {

    @Autowired
    private reviewService reviewService;

    // Get all reviews
    @GetMapping
    public ResponseEntity<List<review>> getAllReviews() {
        return ResponseEntity.ok(reviewService.getAllReviews());
    }

    // Get review by ID
    @GetMapping("/{id}")
    public ResponseEntity<review> getReviewById(@PathVariable String id) {
        review review = reviewService.getReviewById(id);
        if (review != null) {
            return ResponseEntity.ok(review);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Create new review
    @PostMapping
    public ResponseEntity<review> createReview(@RequestBody review review) {
        return ResponseEntity.ok(reviewService.createReview(review));
    }

    // Update review
    @PutMapping("/{id}")
    public ResponseEntity<review> updateReview(@PathVariable String id, @RequestBody review updatedReview) {
        review review = reviewService.updateReview(id, updatedReview);
        if (review != null) {
            return ResponseEntity.ok(review);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete review
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable String id) {
        if (reviewService.deleteReview(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
