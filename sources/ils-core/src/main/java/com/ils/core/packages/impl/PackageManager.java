package com.ils.core.packages.impl;

import com.ils.core.packages.IPackageManager;
import com.ils.core.exception.IlsCoreException;
import com.ils.data.exception.DataException;
import com.ils.data.model.BulkPackage;
import com.ils.data.model.IlsPackage;
import com.ils.data.model.PackageStateLink;
import com.ils.data.model.IlsUser;
import com.ils.data.repository.PackageRepository;
import com.ils.data.repository.PackageStateLinkRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by mara on 10/11/15.
 */
@Service("packageManager")
public class PackageManager implements IPackageManager {

    private static final Logger logger = Logger.getLogger(PackageManager.class);

    @Autowired
    private PackageRepository packageRepository;

    @Autowired
    private PackageStateLinkRepository packageStateLinkRepository;

    public IlsPackage save(IlsPackage ilsPackage) throws IlsCoreException {

        if (logger.isDebugEnabled()) {
            logger.debug("Trying to save ilsPackage : " + ilsPackage);
        }
        try {
            return packageRepository.save(ilsPackage);
        } catch (DataException e) {
            String errorMsg = "An error occurred while trying to save ilspackage : " + ilsPackage;
            logger.error(errorMsg,e);
            throw new IlsCoreException(errorMsg,e);
        }
    }

    public void delete(IlsPackage ilsPackage) throws IlsCoreException {

        if (logger.isDebugEnabled()) {
            logger.debug("Trying to delete ilsPackage : " + ilsPackage);
        }
        try {
            packageRepository.delete(ilsPackage);
        } catch (DataException e) {
            String errorMsg = "An error occurred while trying to delete ilspackage : " + ilsPackage;
            logger.error(errorMsg,e);
            throw new IlsCoreException(errorMsg,e);
        }
    }

    public IlsPackage findByIdentifier(String identifier) throws IlsCoreException {

        if (logger.isDebugEnabled()) {
            logger.debug("Trying to find ilsPackage by identifier : " + identifier);
        }
        try {
            return packageRepository.findByIdentifier(identifier);
        } catch (DataException e) {
            String errorMsg = "An error occurred while trying to find ilsPackage by identifier : " + identifier;
            logger.error(errorMsg,e);
            throw new IlsCoreException(errorMsg,e);
        }
    }

    public List<IlsPackage> findByBulk(BulkPackage bulkPackage) throws IlsCoreException {

        if (logger.isDebugEnabled()) {
            logger.debug("Trying to find ilsPackage by bulkPackage : " + bulkPackage);
        }
        try {
            return packageRepository.findByBulk(bulkPackage);
        } catch (DataException e) {
            String errorMsg = "An error occurred while trying to find ilsPackage by bulkPackage : " + bulkPackage;
            logger.error(errorMsg,e);
            throw new IlsCoreException(errorMsg,e);
        }
    }

    public List<IlsPackage> findByRecipient(IlsUser recipient) throws IlsCoreException {

        if (logger.isDebugEnabled()) {
            logger.debug("Trying to find ilsPackage by recipient : " + recipient);
        }
        try {
            return packageRepository.findByRecipient(recipient);
        } catch (DataException e) {
            String errorMsg = "An error occurred while trying to find ilsPackage by recipient : " + recipient;
            logger.error(errorMsg,e);
            throw new IlsCoreException(errorMsg,e);
        }
    }

    public List<IlsPackage> findBySender(IlsUser sender) throws IlsCoreException {

        if (logger.isDebugEnabled()) {
            logger.debug("Trying to find ilsPackage by sender : " + sender);
        }
        try {
            return packageRepository.findBySender(sender);
        } catch (DataException e) {
            String errorMsg = "An error occurred while trying to find ilsPackage by sender : " + sender;
            logger.error(errorMsg,e);
            throw new IlsCoreException(errorMsg,e);
        }
    }

    public List<PackageStateLink> findStateLinks(IlsPackage ilsPackage) throws IlsCoreException {

        if (logger.isDebugEnabled()) {
            logger.debug("Trying to find ils state links by ilsPackage : " + ilsPackage);
        }
        try {
            return packageStateLinkRepository.findByPackage(ilsPackage);
        } catch (DataException e) {
            String errorMsg = "An error occurred while trying to find ils state links by ilspackage : " + ilsPackage;
            logger.error(errorMsg, e);
            throw new IlsCoreException(errorMsg,e);
        }
    }

}
