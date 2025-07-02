package com.jocata.orderdao;

import com.jocata.orderservice.entity.Order;

public interface OrderDao {
    Order save(Order order);
    Order findById(Integer id);
    Order update(Order order);
}
