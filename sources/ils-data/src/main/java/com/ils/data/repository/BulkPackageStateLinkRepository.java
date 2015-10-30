package com.ils.data.repository;

import com.ils.data.exception.DataException;
import com.ils.data.model.BulkPackage;
import com.ils.data.model.BulkPackageStateLink;

import com.ils.data.model.State;
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
public class BulkPackageStateLinkRepository extends AbstractRepository<BulkPackageStateLink>{

    @Autowired
    private JpaTransactionManager transactionManager;

    public JpaTransactionManager getTransactionManager() {
        return transactionManager;
    }

    public void setTransactionManager(JpaTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    public List<BulkPackageStateLink> findByBulk(BulkPackage bulkPackage) throws DataException {
        String queryString = "select link from BulkPackageStateLink link where link.bulkPackage = ?1";
        return findList(queryString, bulkPackage, BulkPackageStateLink.class);
    }

    public List<BulkPackageStateLink> findByState(State state) throws DataException {
        String queryString = "select link from BulkPackageStateLink link where link.state = ?1";
        return findList(queryString, state, BulkPackageStateLink.class);
    }
}
