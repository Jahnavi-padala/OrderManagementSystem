package com.jocata.ratingdao;

import com.jocata.ratingreviewservice.entity.RatingReview;

public interface RatingDao {
    RatingReview save(RatingReview review);
    RatingReview findById(Integer id);
}
