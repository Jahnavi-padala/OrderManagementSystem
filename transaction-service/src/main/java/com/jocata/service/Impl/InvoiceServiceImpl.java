package com.jocata.service.Impl;

import com.jocata.TransactionDao.InvoiceDao;
import com.jocata.orderservice.form.OrderDataForm;
import com.jocata.orderservice.form.OrderItemForm;

import com.jocata.service.InvoiceService;
import com.jocata.transactionservice.entity.Invoice;
import com.jocata.transactionservice.form.InvoiceForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    @Autowired
    private InvoiceDao invoiceDao;
    @Autowired
    private RestTemplate restTemplate;
    private static final String ORDER_SERVICE_URL = "http://localhost:8084/orderservice/api/orders";
    @Override
    public InvoiceForm generateInvoice(InvoiceForm input) {
        String orderId = input.getOrderId();

        OrderDataForm orderData = restTemplate.getForObject(
                ORDER_SERVICE_URL + "/getOrderById/" + orderId,
                OrderDataForm.class
        );

        if (orderData == null || !"PAID".equalsIgnoreCase(orderData.getOrder().getStatus())) {
            throw new RuntimeException("Order is not paid or has been cancelled.");
        }

        StringBuilder productDetails = new StringBuilder();
        for (OrderItemForm item : orderData.getOrderItems()) {
            productDetails.append("ProductID: ")
                    .append(item.getProductId())
                    .append(", Qty: ")
                    .append(item.getQuantity())
                    .append(", Price: ")
                    .append(item.getPrice())
                    .append(" | ");
        }

        String shippingAddress = orderData.getOrder().getShippingAddress();
        String userName = "User-" + orderData.getOrder().getUserId();
        String orderStatus     = orderData.getOrder().getStatus();

        Invoice invoice = new Invoice();
        invoice.setOrderId(Integer.parseInt(orderId));
        invoice.setAmount(new BigDecimal(orderData.getOrder().getTotalAmount()));
        invoice.setGeneratedDate(LocalDateTime.now());
        invoice.setStatus(orderStatus);
        invoice.setInvoiceNumber("INV-" + System.currentTimeMillis());

        invoiceDao.save(invoice);

        input.setId(invoice.getId().toString());
        input.setInvoiceNumber(invoice.getInvoiceNumber());
        input.setUserName(userName);
        input.setShippingAddress(shippingAddress);
        input.setProductDetails(productDetails.toString());
        input.setTotalAmount(orderData.getOrder().getTotalAmount());
        input.setStatus(orderStatus);
        input.setGeneratedDate(invoice.getGeneratedDate().toString());

        return input;
    }
}
