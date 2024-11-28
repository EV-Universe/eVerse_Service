package com.vishal.eVerse.controllers;

import com.vishal.eVerse.models.reviews.Review;
import com.vishal.eVerse.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/reviews")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping("/add")
    public ResponseEntity<?> addReview(
        @RequestBody
        Review review
    ){
        return reviewService.addReview(review);
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateReview(
        @RequestBody
        Review review
    ){
        return reviewService.updateReview(review);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getAllReviewsByUserId(
        @PathVariable
        Integer userId
    ){
        return reviewService.getAllReviewsByUserId(userId);
    }

    @GetMapping("/station/{stationId}")
    public ResponseEntity<?> getAllReviewsByStationId(
        @PathVariable
        Integer stationId
    ){
        return reviewService.getAllReviewsByStationId(stationId);
    }

}
