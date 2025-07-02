package com.jocata.service;

import com.jocata.orderservice.entity.Order;
import com.jocata.orderservice.form.OrderDataForm;
import com.jocata.orderservice.form.PaymentForm;

public interface OrderService {
    OrderDataForm placeOrder(OrderDataForm orderDataForm);
    OrderDataForm getOrderById(String orderId);
    OrderDataForm makePayment(String orderId, PaymentForm paymentForm);
    OrderDataForm mapOrderToDataForm(Order order);
    String trackOrder(String orderId);
    void updateOrderStatus(String orderId, String newStatus);
}
