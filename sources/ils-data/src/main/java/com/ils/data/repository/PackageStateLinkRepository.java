package com.ils.data.repository;

import com.ils.data.exception.DataException;
import com.ils.data.model.IlsPackage;
import com.ils.data.model.PackageStateLink;

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
public class PackageStateLinkRepository extends AbstractRepository<PackageStateLink>{

    @Autowired
    private JpaTransactionManager transactionManager;

    public JpaTransactionManager getTransactionManager() {
        return transactionManager;
    }

    public void setTransactionManager(JpaTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    public List<PackageStateLink> findByPackage(IlsPackage ilsPackage) throws DataException {
        String queryString = "select link from PackageStateLink link where link.ilsPackage = ?1";
        return findList(queryString, ilsPackage, PackageStateLink.class);
    }

    public List<PackageStateLink> findByState(State state) throws DataException {
        String queryString = "select link from PackageStateLink link where link.state = ?1";
        return findList(queryString, state, PackageStateLink.class);
    }
}
