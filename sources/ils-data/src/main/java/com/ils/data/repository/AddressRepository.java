package com.ils.data.repository;

import com.ils.data.model.Address;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by mara on 7/12/15.
 */
@Repository
@Transactional(readOnly = true)
public class AddressRepository extends AbstractRepository<Address>{

    @Autowired
    private JpaTransactionManager transactionManager;

    public JpaTransactionManager getTransactionManager() {
        return transactionManager;
    }

    public void setTransactionManager(JpaTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

}
