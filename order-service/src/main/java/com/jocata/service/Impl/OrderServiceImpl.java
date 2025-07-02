package com.jocata.service.Impl;

import com.jocata.orderdao.OrderDao;
import com.jocata.orderdao.OrderItemDao;
import com.jocata.orderdao.OrderStatusDao;
import com.jocata.orderdao.PaymentDao;
import com.jocata.orderservice.entity.Order;
import com.jocata.orderservice.entity.OrderItem;
import com.jocata.orderservice.entity.OrderStatus;
import com.jocata.orderservice.entity.Payment;
import com.jocata.orderservice.form.*;
import com.jocata.productservice.form.ProductDataForm;
import com.jocata.service.OrderService;

import com.jocata.userservice.form.UserAddressForm;
import com.jocata.userservice.form.UserDataForm;
import com.jocata.userservice.form.UserForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDao orderDao;
    @Autowired private OrderItemDao orderItemDao;
    @Autowired private OrderStatusDao orderStatusDao;
    @Autowired private PaymentDao paymentDao;
    @Autowired
    private RestTemplate restTemplate;

    private static final String USER_SERVICE_URL = "http://localhost:8082/userservice/api/v1";
    private static final String PRODUCT_SERVICE_URL = "http://localhost:8083/productservice/api/products";

    private boolean userExists(Integer userId) {
        String url = USER_SERVICE_URL + "/getUserById/" + userId;
        try {
            ResponseEntity<Object> response = restTemplate.getForEntity(url, Object.class);
            return response.getStatusCode().is2xxSuccessful() && response.getBody() != null;
        } catch (HttpClientErrorException.NotFound ex) {
            return false;
        } catch (Exception ex) {
            return false;
        }
    }
    private UserDataForm getUserById(Integer userId) {
        String url = USER_SERVICE_URL + "/getUserById/" + userId;
        try {
            return restTemplate.getForObject(url, UserDataForm.class);
        } catch (HttpClientErrorException.NotFound ex) {
            return null;
        } catch (Exception ex) {
            return null;
        }
    }

    private boolean productExists(Integer productId) {
        String url = PRODUCT_SERVICE_URL + "/getProductById/" + productId;
        try {
            ResponseEntity<Object> response = restTemplate.getForEntity(url, Object.class);
            return response.getStatusCode().is2xxSuccessful() && response.getBody() != null;
        } catch (HttpClientErrorException.NotFound ex) {
            return false;
        } catch (Exception ex) {
            return false;
        }
    }

    private ProductDataForm getProductById(Integer productId) {
        String url = PRODUCT_SERVICE_URL + "/getProductById/" + productId;
        try {
            return restTemplate.getForObject(url, ProductDataForm.class);
        } catch (HttpClientErrorException.NotFound ex) {
            return null;
        } catch (Exception ex) {
            return null;
        }
    }

    private boolean reduceProductQuantity(Integer productId, Integer quantity) {
        String url = PRODUCT_SERVICE_URL + "/" + productId + "/reduceQuantity";

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("quantity", quantity);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<Void> response = restTemplate.exchange(url, HttpMethod.PUT, entity, Void.class);
            return response.getStatusCode().is2xxSuccessful();
        } catch (Exception ex) {
            return false;
        }
    }
    @Override
    public OrderDataForm placeOrder(OrderDataForm orderDataForm) {
        OrderForm of = orderDataForm.getOrder();
        Integer userId = Integer.valueOf(of.getUserId());
        if (!userExists(userId)) {
            throw new RuntimeException("User ID not found: " + userId);
        }

        UserDataForm userDataForm = getUserById(Integer.valueOf(userId.toString()));
        List<UserAddressForm> addresses = userDataForm.getAddresses();
        if (addresses == null || addresses.isEmpty()) {
            throw new RuntimeException("User has no address to use as shipping address.");
        }

        UserAddressForm primaryAddress = addresses.get(0);
        String shippingAddress = primaryAddress.getAddressLine1() + ", " +
                primaryAddress.getAddressLine2() + ", " +
                primaryAddress.getCity() + ", " +
                primaryAddress.getPostalCode() + ", " +
                primaryAddress.getCountry();


        BigDecimal totalAmount = BigDecimal.ZERO;

        for (OrderItemForm item : orderDataForm.getOrderItems()) {
            Integer productId = Integer.valueOf(item.getProductId());
            if (!productExists(productId)) {
                throw new RuntimeException("Product ID not found: " + productId);
            }

            ProductDataForm product = getProductById(productId);
            Integer availableQty = Integer.valueOf(product.getProduct().getQuantity());
            Integer requestedQty = Integer.valueOf(item.getQuantity());

            if (availableQty < requestedQty) {
                throw new RuntimeException("Insufficient quantity for product ID: " + productId);
            }

            BigDecimal price = new BigDecimal(product.getProduct().getPrice());
            totalAmount = totalAmount.add(price.multiply(BigDecimal.valueOf(requestedQty)));
        }

        Order order = new Order();
        order.setUserId(userId);
        order.setOrderDate(LocalDateTime.now());
        order.setTotalAmount(totalAmount);
        order.setStatus("PLACED");
        order.setShippingAddress(shippingAddress);
        order.setCreatedAt(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());
        orderDao.save(order);

        for (OrderItemForm itemForm : orderDataForm.getOrderItems()) {
            Integer productId = Integer.valueOf(itemForm.getProductId());
            Integer qtyToOrder = Integer.valueOf(itemForm.getQuantity());

            OrderItem item = new OrderItem();
            item.setOrder(order);
            item.setProductId(productId);
            item.setQuantity(qtyToOrder);
            ProductDataForm product = getProductById(productId);
            BigDecimal price = new BigDecimal(product.getProduct().getPrice());
            item.setPrice(price);
            orderItemDao.save(item);

        }


        PaymentForm pf = orderDataForm.getPayment();
        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setPaymentMethod(pf.getPaymentMethod());
        payment.setAmount(totalAmount);
        payment.setStatus("PENDING");
        payment.setPaymentDate(LocalDateTime.now());
        paymentDao.save(payment);


        for (OrderStatusForm statusForm : orderDataForm.getOrderStatuses()) {
            OrderStatus status = new OrderStatus();
            status.setOrder(order);
            status.setStatus(statusForm.getStatus());
            status.setTimestamp(LocalDateTime.now());
            orderStatusDao.save(status);
        }

        return mapOrderToDataForm(order);
    }


    @Override
    public OrderDataForm getOrderById(String orderId) {
        Order order = orderDao.findById(Integer.valueOf(orderId));
        if (order == null) throw new RuntimeException("Order not found");
        return mapOrderToDataForm(order);
    }


    @Override
    public OrderDataForm makePayment(String orderId, PaymentForm paymentForm) {
        Order order = orderDao.findById(Integer.valueOf(orderId));
        if (order == null) throw new RuntimeException("Order not found");

        Payment payment = paymentDao.findByOrderId(order.getId());

        payment.setStatus(paymentForm.getStatus());
        payment.setPaymentDate(LocalDateTime.now());
        payment.setPaymentMethod(paymentForm.getPaymentMethod());
        paymentDao.update(payment);


        if ("PAID".equalsIgnoreCase(paymentForm.getStatus())) {
            for (OrderItem item : orderItemDao.findByOrderId(order.getId())) {
                reduceProductQuantity(item.getProductId(), item.getQuantity());
            }
            order.setStatus("PAID");

            OrderStatus shippedStatus = new OrderStatus();
            shippedStatus.setOrder(order);
            shippedStatus.setStatus("SHIPPED");
            shippedStatus.setTimestamp(LocalDateTime.now());
            orderStatusDao.save(shippedStatus);
        } else if ("CANCELLED".equalsIgnoreCase(paymentForm.getStatus())) {
            order.setStatus("CANCELLED");

            OrderStatus cancelledStatus = new OrderStatus();
            cancelledStatus.setOrder(order);
            cancelledStatus.setStatus("CANCELLED");
            cancelledStatus.setTimestamp(LocalDateTime.now());
            orderStatusDao.save(cancelledStatus);

        } else {
            order.setStatus("PAYMENT_" + paymentForm.getStatus().toUpperCase());

            OrderStatus status = new OrderStatus();
            status.setOrder(order);
            status.setStatus(order.getStatus());
            status.setTimestamp(LocalDateTime.now());
            orderStatusDao.save(status);
        }

        orderDao.update(order);





        return mapOrderToDataForm(order);
    }


    public OrderDataForm mapOrderToDataForm(Order order) {
        OrderForm of = new OrderForm();
        of.setId(order.getId().toString());
        of.setUserId(order.getUserId().toString());
        of.setOrderDate(order.getOrderDate().toString());
        of.setTotalAmount(order.getTotalAmount().toString());
        of.setStatus(order.getStatus());
        of.setShippingAddress(order.getShippingAddress());
        of.setCreatedAt(order.getCreatedAt().toString());
        of.setUpdatedAt(order.getUpdatedAt() != null ? order.getUpdatedAt().toString() : null);

        List<OrderItemForm> itemForms = new ArrayList<>();
        for (OrderItem item : orderItemDao.findByOrderId(order.getId())) {
            OrderItemForm itemForm = new OrderItemForm();
            itemForm.setId(item.getId().toString());
            itemForm.setOrderId(order.getId().toString());
            itemForm.setProductId(item.getProductId().toString());
            itemForm.setQuantity(item.getQuantity().toString());
            itemForm.setPrice(item.getPrice().toString());
            itemForms.add(itemForm);
        }

        Payment payment = paymentDao.findByOrderId(order.getId());
        PaymentForm pf = new PaymentForm();
        pf.setId(payment.getId().toString());
        pf.setOrderId(order.getId().toString());
        pf.setPaymentMethod(payment.getPaymentMethod());
        pf.setAmount(payment.getAmount().toString());
        pf.setStatus(payment.getStatus());
        pf.setPaymentDate(payment.getPaymentDate().toString());

        List<OrderStatusForm> statusForms = new ArrayList<>();
        for (OrderStatus status : orderStatusDao.findByOrderId(order.getId())) {
            OrderStatusForm sf = new OrderStatusForm();
            sf.setId(status.getId().toString());
            sf.setOrderId(order.getId().toString());
            sf.setStatus(status.getStatus());
            sf.setTimestamp(status.getTimestamp().toString());
            statusForms.add(sf);
        }

        OrderDataForm response = new OrderDataForm();
        response.setOrder(of);
        response.setOrderItems(itemForms);
        response.setPayment(pf);
        response.setOrderStatuses(statusForms);

        return response;
    }
    @Override
    public String trackOrder(String orderId) {
        Order order = orderDao.findById(Integer.valueOf(orderId));
        if (order == null) {
            throw new RuntimeException("Order ID " + orderId + " does not exist");
        }
        return "Current status of Order ID " + orderId + " is: " + order.getStatus();
    }
    @Override
    public void updateOrderStatus(String orderId, String newStatus) {
        Order order = orderDao.findById(Integer.parseInt(orderId));
        if (order == null) {
            throw new RuntimeException("Order not found with ID: " + orderId);
        }

        // Update orders table
        order.setStatus(newStatus);
        order.setUpdatedAt(LocalDateTime.now());
        orderDao.update(order);

        // Insert new record into order_status table
        OrderStatus status = new OrderStatus();
        status.setOrder(order);
        status.setStatus(newStatus);
        status.setTimestamp(LocalDateTime.now());
        orderStatusDao.save(status);
    }



}
