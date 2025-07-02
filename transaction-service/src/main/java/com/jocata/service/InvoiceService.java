package com.jocata.service;

import com.jocata.transactionservice.form.InvoiceForm;

public interface InvoiceService {
    InvoiceForm generateInvoice(InvoiceForm input);
}
