package com.jocata.service;

import com.jocata.shippingservice.form.ShippingForm;

public interface ShippingService {
    ShippingForm createShipping(ShippingForm shipmentForm);
    ShippingForm getShipping(String shipmentId);
}
