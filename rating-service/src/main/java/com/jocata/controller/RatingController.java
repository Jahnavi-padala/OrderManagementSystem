package com.jocata.controller;

import com.jocata.ratingreviewservice.form.RatingReviewForm;
import com.jocata.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class RatingController {
    @Autowired
    private RatingService ratingReviewService;

    @PostMapping("/addReview")
    public ResponseEntity<RatingReviewForm> addReview(@RequestBody RatingReviewForm reviewForm) {
        RatingReviewForm savedReview = ratingReviewService.addReview(reviewForm);
        return ResponseEntity.ok(savedReview);
    }


    @GetMapping("/getReview/{id}")
    public ResponseEntity<RatingReviewForm> getReview(@PathVariable("id") String id) {
        RatingReviewForm review = ratingReviewService.getReview(id);
        return ResponseEntity.ok(review);
    }
}
