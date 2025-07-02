package com.jocata.TransactionDao.Impl;

import com.jocata.TransactionDao.InvoiceDao;
import com.jocata.transactionservice.entity.Invoice;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class InvoiceDaoImpl implements InvoiceDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Invoice save(Invoice invoice) {
        entityManager.persist(invoice);
        return invoice;
    }


}
