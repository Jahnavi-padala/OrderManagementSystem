package com.jocata.service.impl;

import com.jocata.orderservice.form.OrderDataForm;
import com.jocata.orderservice.form.OrderItemForm;
import com.jocata.orderservice.form.OrderStatusForm;
import com.jocata.ratingdao.RatingDao;
import com.jocata.ratingreviewservice.entity.RatingReview;
import com.jocata.ratingreviewservice.form.RatingReviewForm;
import com.jocata.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

@Service
public class RatingServiceImpl implements RatingService {
    @Autowired
    private RatingDao reviewDao;

    @Autowired
    private RestTemplate restTemplate;
    private static final String ORDER_SERVICE_URL = "http://localhost:8084/orderservice/api/orders";


    private boolean orderItemExists(String orderId, String productId) {
        String url = ORDER_SERVICE_URL + "/getOrderById/" + orderId;
        OrderDataForm orderData = restTemplate.getForObject(url, OrderDataForm.class);

        if (orderData == null || orderData.getOrderItems() == null) {
            throw new RuntimeException("Order not found: " + orderId);
        }

        for (OrderItemForm item : orderData.getOrderItems()) {
            if (productId.equals(item.getProductId())) {
                return true;
            }
        }
        return false;
    }


    private String getLatestOrderStatus(String orderId) {
        String url = ORDER_SERVICE_URL + "/getOrderById/" + orderId;
        OrderDataForm response = restTemplate.getForObject(url, OrderDataForm.class);
        if (response != null && response.getOrderStatuses() != null && !response.getOrderStatuses().isEmpty()) {
            int lastIndex = response.getOrderStatuses().size() - 1;
            return response.getOrderStatuses().get(lastIndex).getStatus();
        }
        throw new RuntimeException("Order status not found for Order ID: " + orderId);
    }



    @Override
    public RatingReviewForm addReview(RatingReviewForm reviewForm) {
        String orderId = reviewForm.getOrderId();
        String productId = reviewForm.getProductId();


        if (!orderItemExists(orderId, productId)) {
            throw new RuntimeException("Order Item not found for orderId: " + orderId + ", productId: " + productId);
        }


        String latestStatus = getLatestOrderStatus(orderId);
        if (!"DELIVERED".equalsIgnoreCase(latestStatus)) {
            throw new RuntimeException(
                    "Review can only be added if order status is DELIVERED. Current status: " + latestStatus
            );
        }

        RatingReview review = new RatingReview();
        review.setOrderId(Integer.parseInt(orderId));
        review.setProductId(Integer.parseInt(productId));
        review.setRating(Integer.parseInt(reviewForm.getRating()));
        review.setReviewText(reviewForm.getReviewText());
        review.setCreatedAt(LocalDateTime.now());

        RatingReview saved = reviewDao.save(review);

        reviewForm.setId(saved.getId().toString());
        reviewForm.setCreatedAt(saved.getCreatedAt().toString());
        return reviewForm;
    }

    @Override
    public RatingReviewForm getReview(String reviewId) {
        RatingReview review = reviewDao.findById(Integer.parseInt(reviewId));
        if (review == null) {
            throw new RuntimeException("Review not found for ID: " + reviewId);
        }
        RatingReviewForm form = new RatingReviewForm();
        form.setId(review.getId().toString());
        form.setOrderId(review.getOrderId().toString());
        form.setProductId(review.getProductId().toString());
        form.setRating(review.getRating().toString());
        form.setReviewText(review.getReviewText());
        form.setCreatedAt(review.getCreatedAt().toString());
        return form;
    }

}

