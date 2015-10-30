package com.ils.data.repository;

import com.ils.data.exception.DataException;
import com.ils.data.model.BulkPackage;
import com.ils.data.model.IlsPackage;

import com.ils.data.model.IlsUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by mara on 7/12/15.
 */
@Repository
@Transactional(readOnly = true)
public class PackageRepository extends AbstractRepository<IlsPackage>{

    @Autowired
    private JpaTransactionManager transactionManager;

    public JpaTransactionManager getTransactionManager() {
        return transactionManager;
    }

    public void setTransactionManager(JpaTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    public IlsPackage findByIdentifier(String identifier) throws DataException {
        String queryString = "select package from IlsPackage package where package.identifier = ?1";
        return findSingle(queryString, identifier, IlsPackage.class);
    }

    public List<IlsPackage> findBySender(IlsUser sender) throws DataException {
        String queryString = "select package from IlsPackage package where package.sender = ?1";
        return findList(queryString, sender, IlsPackage.class);
    }

    public List<IlsPackage> findByRecipient(IlsUser recipient) throws DataException {
        String queryString = "select package from IlsPackage package where package.recipient = ?1";
        return findList(queryString, recipient, IlsPackage.class);
    }

    public List<IlsPackage> findByBulk(BulkPackage bulkPackage) throws DataException {
        String queryString = "select package from IlsPackage package where package.bulkPackage = ?1";
        return findList(queryString, bulkPackage, IlsPackage.class);
    }
}
