package com.ils.data.repository;

import com.ils.data.exception.DataException;
import com.ils.data.model.IlsUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public class UserRepository extends AbstractRepository<IlsUser>{

    @Autowired
    private JpaTransactionManager transactionManager;

    public JpaTransactionManager getTransactionManager() {
        return transactionManager;
    }

    public void setTransactionManager(JpaTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    public IlsUser findByUsername(String username) throws DataException {
        String queryString = "select user from IlsUser user where user.username = ?1";
        return findSingle(queryString, username, IlsUser.class);
    }
    
    public List<IlsUser> findByFirstName(String firstName) throws DataException {
        String queryString = "select user from IlsUser user where user.firstname = ?1";
        return findList(queryString, firstName, IlsUser.class);
    }

    public List<IlsUser> findByLastName(String lastName) throws DataException {
        String queryString = "select user from IlsUser user where user.lastname = ?1";
        return findList(queryString, lastName, IlsUser.class);
    }

    public IlsUser findByPhoneNumber(String phone) throws DataException {
        String queryString = "select user from IlsUser user where user.phone = ?1";
        return findSingle(queryString, phone, IlsUser.class);
    }

    public IlsUser findByEmail(String email) throws DataException {
        String queryString = "select user from IlsUser user where user.email = ?1";
        return findSingle(queryString, email, IlsUser.class);
    }

    public List<IlsUser> findAll() throws DataException {
        String queryString = "select user from IlsUser user";
        return findAll(queryString, IlsUser.class);
    }

}
