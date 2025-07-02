package com.jocata.controller;

import com.jocata.service.InvoiceService;
import com.jocata.transactionservice.form.InvoiceForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class InvoiceController {
    @Autowired
    private InvoiceService invoiceService;

    @PostMapping("/generateInvoice")
    public ResponseEntity<InvoiceForm> generateInvoice(@RequestBody InvoiceForm invoiceForm) {
        InvoiceForm generated = invoiceService.generateInvoice(invoiceForm);
        return ResponseEntity.ok(generated);
    }
}
