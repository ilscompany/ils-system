package com.ils.data.repository;

import com.ils.data.exception.DataException;
import com.ils.data.model.Role;
import static com.ils.data.model.Role.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by mara on 10/19/15.
 */
@Repository
@Transactional(readOnly = true)
public class RoleRepository extends AbstractRepository<Role>{

    @Autowired
    private JpaTransactionManager transactionManager;

    public JpaTransactionManager getTransactionManager() {
        return transactionManager;
    }

    public void setTransactionManager(JpaTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    public Role findByName(Name name) throws DataException {
        String queryString = "select role from Role role where role.name = ?1";
        return findSingle(queryString, name, Role.class);
    }
}
