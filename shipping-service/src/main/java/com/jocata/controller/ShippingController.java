package com.jocata.controller;

import com.jocata.service.ShippingService;
import com.jocata.shippingservice.form.ShippingForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class ShippingController {

    @Autowired
    private ShippingService shippingService;
    @PostMapping("/createShipping")
    public ShippingForm createShipping(@RequestBody ShippingForm shippingForm) {
        return shippingService.createShipping(shippingForm);
    }

    @GetMapping("/get/{id}")
    public ShippingForm getShipping(@PathVariable String id) {
        return shippingService.getShipping(id);
    }
}
