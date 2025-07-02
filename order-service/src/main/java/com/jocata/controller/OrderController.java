package com.jocata.controller;

import com.jocata.orderservice.form.OrderDataForm;
import com.jocata.orderservice.form.PaymentForm;
import com.jocata.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/placeOrder")
    public ResponseEntity<OrderDataForm> placeOrder(@RequestBody OrderDataForm orderDataForm) {
        OrderDataForm response = orderService.placeOrder(orderDataForm);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/getOrderById/{orderId}")
    public ResponseEntity<OrderDataForm> getOrderById(@PathVariable String orderId) {
        OrderDataForm response = orderService.getOrderById(orderId);
        return ResponseEntity.ok(response);
    }


    @PostMapping("/pay/{orderId}")
    public ResponseEntity<OrderDataForm> makePayment(@PathVariable String orderId, @RequestBody PaymentForm paymentForm) {
        OrderDataForm response = orderService.makePayment(orderId, paymentForm);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/trackOrder/{id}")
    public String trackOrder(@PathVariable("id") String orderId) {
        return orderService.trackOrder(orderId);
    }

    @PutMapping("/updateOrderStatus/{orderId}/{newStatus}")
    public ResponseEntity<String> updateOrderStatus(
            @PathVariable String orderId,
            @PathVariable String newStatus) {
        orderService.updateOrderStatus(orderId, newStatus);
        return ResponseEntity.ok("Order status updated to: " + newStatus);
    }

}
