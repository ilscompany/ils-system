package com.ils.data.repository;

import com.ils.data.exception.DataException;
import com.ils.data.model.BulkPackage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by mara on 7/12/15.
 */
@Repository
@Transactional(readOnly = true)
public class BulkPackageRepository extends AbstractRepository<BulkPackage>{

    @Autowired
    private JpaTransactionManager transactionManager;

    public JpaTransactionManager getTransactionManager() {
        return transactionManager;
    }

    public void setTransactionManager(JpaTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    public BulkPackage findByIdentifier(String identifier) throws DataException {
        String queryString = "select bulk from BulkPackage bulk where bulk.identifier = ?1";
        return findSingle(queryString, identifier, BulkPackage.class);
    }
}
