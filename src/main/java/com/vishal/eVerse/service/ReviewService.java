package com.vishal.eVerse.service;

import com.vishal.eVerse.models.evStations.Review;
import com.vishal.eVerse.repository.ReviewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewsRepository reviewsRepository;

    public ResponseEntity<?> addReview(Review review){
        return ResponseEntity.ok(reviewsRepository.save(review));
    }

    public ResponseEntity<List<Review>> getAllReviewsByUserId(Integer userId){
        return ResponseEntity.ok(reviewsRepository.findAllReviewsByUserId(userId));
    }

    public ResponseEntity<List<Review>> getAllReviewsByStationId(Integer stationId){
        return ResponseEntity.ok(reviewsRepository.findAllReviewsByStationId(stationId));
    }

    public ResponseEntity<?> updateReview(Review review){
        return ResponseEntity.ok(reviewsRepository.save(review));
    }

    public ResponseEntity<?> deleteReview(Integer id){
        reviewsRepository.deleteById(id);
        return ResponseEntity.ok("Review deleted successfully");
    }
}
