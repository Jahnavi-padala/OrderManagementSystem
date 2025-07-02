package com.jocata.TransactionDao;

import com.jocata.transactionservice.entity.Invoice;

public interface InvoiceDao {
    Invoice save(Invoice invoice);

}
