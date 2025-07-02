package com.jocata.service.Impl;

import com.jocata.orderservice.form.OrderDataForm;
import com.jocata.service.ShippingService;
import com.jocata.shippingdao.ShippingDao;
import com.jocata.shippingservice.entity.Shipping;
import com.jocata.shippingservice.form.ShippingForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.time.LocalDateTime;

@Service
public class ShippingServiceImpl implements ShippingService {
    @Autowired
    private ShippingDao shipmentDao;

    @Autowired
    private RestTemplate restTemplate;


        private static final String ORDER_SERVICE_URL = "http://localhost:8084/orderservice/api/orders";
    private String getOrderStatusFromOrderService(String orderId) {
        String url = ORDER_SERVICE_URL + "/getOrderById/" + orderId;
        OrderDataForm response = restTemplate.getForObject(url, OrderDataForm.class);
        if (response != null && response.getOrderStatuses() != null && !response.getOrderStatuses().isEmpty()) {
            int lastIndex = response.getOrderStatuses().size() - 1;
            return response.getOrderStatuses().get(lastIndex).getStatus();
        }
        throw new RuntimeException("Order statuses not found for Order ID: " + orderId);
    }

    private void markOrderAsDelivered(String orderId) {
        String url = ORDER_SERVICE_URL + "/updateOrderStatus/" + orderId + "/DELIVERED";
        restTemplate.put(url, null);
    }


    @Override
    public ShippingForm createShipping(ShippingForm shipmentForm) {

        String orderId = shipmentForm.getOrderId();
        String orderStatus = getOrderStatusFromOrderService(orderId);

        if ("SHIPPED".equalsIgnoreCase(orderStatus)) {
            markOrderAsDelivered(orderId);
            orderStatus = "DELIVERED";
        }

        Shipping shipment = new Shipping();
        shipment.setOrderId(Integer.parseInt(orderId));
        shipment.setTrackingNumber(shipmentForm.getTrackingNumber());
        shipment.setTrackingStatus(orderStatus);  // Save the final status
        shipment.setUpdateTime(LocalDateTime.now());

        Shipping savedShipment = shipmentDao.save(shipment);

        shipmentForm.setId(savedShipment.getId().toString());
        shipmentForm.setTrackingStatus(savedShipment.getTrackingStatus());
        shipmentForm.setUpdateTime(savedShipment.getUpdateTime().toString());

        return shipmentForm;
    }



    @Override
        public ShippingForm getShipping(String shipmentId) {
            Shipping shipment = shipmentDao.findById(Integer.parseInt(shipmentId));
            if (shipment == null) {
                throw new RuntimeException("Shipment not found with ID: " + shipmentId);
            }
            ShippingForm form = new ShippingForm();
            form.setId(shipment.getId().toString());
            form.setOrderId(shipment.getOrderId().toString());
            form.setTrackingNumber(shipment.getTrackingNumber());
            form.setTrackingStatus(shipment.getTrackingStatus());
            form.setUpdateTime(shipment.getUpdateTime().toString());
            return form;
        }


    }


