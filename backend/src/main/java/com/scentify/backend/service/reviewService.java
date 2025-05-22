package com.scentify.backend.service;

import com.scentify.backend.model.review;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class reviewService {

    // Simulasi database
    private List<review> reviewList = new ArrayList<>();

    public List<review> getAllReviews() {
        return reviewList;
    }

    public review getReviewById(String id) {
        return reviewList.stream()
                .filter(review -> review.getReviewId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public review createReview(review review) {
        reviewList.add(review);
        return review;
    }

    public review updateReview(String id, review updated) {
        for (int i = 0; i < reviewList.size(); i++) {
            if (reviewList.get(i).getReviewId().equals(id)) {
                reviewList.set(i, updated);
                return updated;
            }
        }
        return null;
    }

    public boolean deleteReview(String id) {
        return reviewList.removeIf(review -> review.getReviewId().equals(id));
    }
}
