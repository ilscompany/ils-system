package com.ils.core.user;

import com.ils.core.exception.IlsCoreException;
import com.ils.data.model.IlsUser;

import java.util.List;

/**
 * Created by mara on 10/11/15.
 */
public interface IUserManager {

    void delete(IlsUser user) throws IlsCoreException;

    IlsUser save(IlsUser user) throws IlsCoreException;

    IlsUser findByUsername(String username) throws IlsCoreException;

    IlsUser findByPhoneNumber(String phone) throws IlsCoreException;

    IlsUser findByEmail(String email) throws IlsCoreException;

    List<IlsUser> findByFirstName(String firstName) throws IlsCoreException;

    List<IlsUser> findByLastName(String lastName) throws IlsCoreException;

    List<IlsUser> findAll() throws IlsCoreException;

}
