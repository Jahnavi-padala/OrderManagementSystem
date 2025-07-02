package com.jocata.service;

import com.jocata.ratingreviewservice.form.RatingReviewForm;

public interface RatingService {
    RatingReviewForm getReview(String reviewId);
    RatingReviewForm addReview(RatingReviewForm reviewForm);
}
