package com.ils.core.boot;

import com.ils.core.exception.IlsCoreException;
import com.ils.core.user.IUserManager;
import com.ils.data.exception.DataException;
import com.ils.data.model.IlsUser;
import com.ils.data.model.Role;
import static com.ils.data.model.Role.*;
import static com.ils.data.model.Role.Name.*;
import com.ils.data.repository.RoleRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is responsible for initializing
 * tha application by setting up all necessary
 * stuff before application get ready
 *
 * Created by mara on 10/19/15.
 */
@Service("userManager")
public class SystemInitializer {

    private static final Logger logger = Logger.getLogger(SystemInitializer.class);

    private static final String ROOT_USERNAME = "admin";

    @Autowired
    private IUserManager userManager;

    @Autowired
    private RoleRepository roleRepository;

    @PostConstruct
    public void init() throws IlsCoreException {

        // creating all roles of users
        // if necessary
        createRoles();

        // creating the default root user
        // note that the credentials of this user
        // is overridden the first time the user
        // sign in.
        createRootUser();
    }

    private void createRoles() throws IlsCoreException {
        try {
            createRole(ROLE_ADMIN);
            createRole(ROLE_AGENT);
            createRole(ROLE_USER);
            createRole(ROLE_ANONYMOUS);

        } catch (DataException e) {
            String errorMsg = "An error occurred while creating system roles";
            logger.error(errorMsg);
            throw new IlsCoreException(errorMsg, e);
        }
    }

    private void createRootUser() throws IlsCoreException {
        try {
            IlsUser admin = userManager.findByUsername(ROOT_USERNAME);
            if (admin == null) {
                admin = new IlsUser();
                List<Role> autorities = new ArrayList<Role>();
                autorities.add(roleRepository.findByName(ROLE_ADMIN));
                admin.setAuthorities(autorities);
                admin.setUsername(ROOT_USERNAME);
                admin.setPassword(ROOT_USERNAME);
                admin.setPhone("0000000000");
                userManager.save(admin);
            }
        } catch (DataException e) {
            String errorMsg = "An error occurred while creating system admin user";
            logger.error(errorMsg);
            throw new IlsCoreException(errorMsg, e);
        }
    }

    private void createRole(Name name) throws DataException {
        Role role = roleRepository.findByName(name);
        if (role == null) {
            // the role doesn't exist yet
            roleRepository.save(new Role(name));
        }
    }

    @Qualifier("dbUserManager")
    public IUserManager getUserManager() {
        return userManager;
    }

    public void setUserManager(IUserManager userManager) {
        this.userManager = userManager;
    }
}
