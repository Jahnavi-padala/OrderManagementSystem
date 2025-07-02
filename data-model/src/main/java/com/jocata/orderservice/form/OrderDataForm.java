package com.jocata.orderservice.form;

import java.util.List;

public class OrderDataForm {
    private OrderForm order;
    private List<OrderItemForm> orderItems;
    private PaymentForm payment;
    private List<OrderStatusForm> orderStatuses;

    public OrderForm getOrder() {
        return order;
    }

    public void setOrder(OrderForm order) {
        this.order = order;
    }

    public List<OrderItemForm> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItemForm> orderItems) {
        this.orderItems = orderItems;
    }

    public PaymentForm getPayment() {
        return payment;
    }

    public void setPayment(PaymentForm payment) {
        this.payment = payment;
    }

    public List<OrderStatusForm> getOrderStatuses() {
        return orderStatuses;
    }

    public void setOrderStatuses(List<OrderStatusForm> orderStatuses) {
        this.orderStatuses = orderStatuses;
    }
}
