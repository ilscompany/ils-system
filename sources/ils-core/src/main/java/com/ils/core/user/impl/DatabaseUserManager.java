package com.ils.core.user.impl;

import com.ils.core.exception.IlsCoreException;
import com.ils.core.user.IUserManager;
import com.ils.data.exception.DataException;
import com.ils.data.model.IlsUser;
import com.ils.data.repository.UserRepository;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by mara on 10/11/15.
 */
@Service("dbUserManager")
public class DatabaseUserManager implements IUserManager {

    private static final Logger logger = Logger.getLogger(DatabaseUserManager.class);

    @Autowired
    private UserRepository userRepository;

    public IlsUser save(IlsUser user) throws IlsCoreException {

        try {
            return getUserRepository().save(user);

        } catch (DataException e) {
            String errorMsg = "An error occurred while trying to save a user";
            logger.error(errorMsg, e);
            throw new IlsCoreException(errorMsg, e);
        }
    }

    public void delete(IlsUser user) throws IlsCoreException {

        try {
            getUserRepository().delete(user);

        } catch (DataException e) {
            String errorMsg = "An error occurred while trying to delete a user";
            logger.error(errorMsg, e);
            throw new IlsCoreException(errorMsg, e);
        }
    }

    public IlsUser findByEmail(String email) throws IlsCoreException {

        if (EmailValidator.getInstance(true).isValid(email)) {
            try {
                return getUserRepository().findByEmail(email);

            } catch (DataException e) {
                String errorMsg = "An error occurred while trying to find a user by email : " + email;
                logger.error(errorMsg, e);
                throw new IlsCoreException(errorMsg, e);
            }

        } else {
            return null;
        }
    }

    public IlsUser findByPhoneNumber(String phone) throws IlsCoreException {

        if (StringUtils.isNotBlank(phone)) {
            try {
                return getUserRepository().findByPhoneNumber(phone);

            } catch (DataException e) {
                String errorMsg = "An error occurred while trying to find a user by phone : " + phone;
                logger.error(errorMsg, e);
                throw new IlsCoreException(errorMsg, e);
            }

        } else {
            return null;
        }
    }

    public IlsUser findByUsername(String username) throws IlsCoreException {

        if (StringUtils.isNotBlank(username)) {
            try {
                return getUserRepository().findByUsername(username);

            } catch (DataException e) {
                String errorMsg = "An error occurred while trying to find a user by username : " + username;
                logger.error(errorMsg, e);
                throw new IlsCoreException(errorMsg, e);
            }

        }else {
            return null;
        }
    }

    public List<IlsUser> findByFirstName(String firstName) throws IlsCoreException {

        if (StringUtils.isNotBlank(firstName)) {

            try {
                return getUserRepository().findByFirstName(firstName);

            } catch (DataException e) {
                String errorMsg = "An error occurred while trying to find a user by firstName : " + firstName;
                logger.error(errorMsg, e);
                throw new IlsCoreException(errorMsg, e);
            }

        }else {
            return null;
        }

    }

    public List<IlsUser> findByLastName(String lastName) throws IlsCoreException {

        if (StringUtils.isNotBlank(lastName)) {

            try {
                return getUserRepository().findByLastName(lastName);

            } catch (DataException e) {
                String errorMsg = "An error occurred while trying to find a user by lastName : " + lastName;
                logger.error(errorMsg, e);
                throw new IlsCoreException(errorMsg, e);
            }

        }else {
            return null;
        }
    }

    public List<IlsUser> findAll() throws IlsCoreException {
        try {
            return getUserRepository().findAll();

        } catch (DataException e) {
            String errorMsg = "An error occurred while trying to find all users" ;
            logger.error(errorMsg, e);
            throw new IlsCoreException(errorMsg, e);
        }
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
