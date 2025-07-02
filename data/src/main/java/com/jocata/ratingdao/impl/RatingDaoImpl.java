package com.jocata.ratingdao.impl;

import com.jocata.ratingdao.RatingDao;
import com.jocata.ratingreviewservice.entity.RatingReview;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class RatingDaoImpl implements RatingDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public RatingReview save(RatingReview review) {
        entityManager.persist(review);
        return review;
    }

    @Override
    public RatingReview findById(Integer id) {
        return entityManager.find(RatingReview.class, id);
    }

}
