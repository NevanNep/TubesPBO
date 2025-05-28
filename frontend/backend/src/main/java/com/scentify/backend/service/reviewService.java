package com.scentify.backend.service;

import com.scentify.backend.model.review;
import com.scentify.backend.repository.reviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class reviewService {

    private final reviewRepository reviewRepository;

    @Autowired
    public reviewService(reviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public List<review> getAllReviews() {
        return reviewRepository.findAll();
    }

    public Optional<review> getReviewById(Long id) {
        return reviewRepository.findById(id);
    }

    public review createReview(review review) {
        return reviewRepository.save(review);
    }

    public Optional<review> updateReview(Long id, review updated) {
        return reviewRepository.findById(id).map(existing -> {
            existing.setKomentar(updated.getKomentar());
            existing.setRating(updated.getRating());
            return reviewRepository.save(existing);
        });
    }

    public boolean deleteReview(Long id) {
        if (reviewRepository.existsById(id)) {
            reviewRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
