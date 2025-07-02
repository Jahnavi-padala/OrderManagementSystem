package com.jocata.orderdao;

import com.jocata.orderservice.entity.Payment;

public interface PaymentDao {
    Payment findByOrderId(Integer orderId);
    Payment update(Payment payment);
    Payment save(Payment payment);
}
