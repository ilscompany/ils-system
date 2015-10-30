package com.ils.core.packages.impl;

import com.ils.core.packages.IBulkPackageManager;
import com.ils.core.exception.IlsCoreException;
import com.ils.core.packages.IPackageManager;
import com.ils.data.exception.DataException;
import com.ils.data.model.*;
import com.ils.data.repository.BulkPackageRepository;
import com.ils.data.repository.BulkPackageStateLinkRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

/**
 * Created by mara on 10/11/15.
 */
@Service("bulkPackageManager")
public class BulkPackageManager implements IBulkPackageManager {

    private static final Logger logger = Logger.getLogger(BulkPackageManager.class);

    @Autowired
    private BulkPackageRepository bulkPackageRepository;

    @Autowired
    private BulkPackageStateLinkRepository bulkStateLinkRepository;

    @Autowired
    private IPackageManager packageManager;

    public BulkPackage save(BulkPackage bulkPackage) throws IlsCoreException {

        if (logger.isDebugEnabled()) {
            logger.debug("Trying to save bulk : " + bulkPackage);
        }
        try {
            return bulkPackageRepository.save(bulkPackage);
        } catch (DataException e) {
            String errorMsg = "An error occurred while trying to save bulk : " + bulkPackage;
            logger.error(errorMsg,e);
            throw new IlsCoreException(errorMsg,e);
        }
    }

    public void delete(BulkPackage bulkPackage) throws IlsCoreException {

        if (logger.isDebugEnabled()) {
            logger.debug("Trying to delete bulk : " + bulkPackage);
        }
        try {
            bulkPackageRepository.delete(bulkPackage);
        } catch (DataException e) {
            String errorMsg = "An error occurred while trying to delete bulk : " + bulkPackage;
            logger.error(errorMsg,e);
            throw new IlsCoreException(errorMsg,e);
        }
    }

    public BulkPackage findByIdentifier(String identifier) throws IlsCoreException {

        if (logger.isDebugEnabled()) {
            logger.debug("Trying to find bulk by identifier: " + identifier);
        }
        try {
            return bulkPackageRepository.findByIdentifier(identifier);
        } catch (DataException e) {
            String errorMsg = "An error occurred while trying to find bulk by identifier: " + identifier;
            logger.error(errorMsg,e);
            throw new IlsCoreException(errorMsg,e);
        }
    }

    public List<BulkPackageStateLink> findStateLinks(BulkPackage bulkPackage) throws IlsCoreException {

        if (logger.isDebugEnabled()) {
            logger.debug("Trying to find bulk state links by bulkPackage : " + bulkPackage);
        }
        try {
            return bulkStateLinkRepository.findByBulk(bulkPackage);
        } catch (DataException e) {
            String errorMsg = "An error occurred while trying to find bulk state links by bulkPackage : " + bulkPackage;
            logger.error(errorMsg, e);
            throw new IlsCoreException(errorMsg,e);
        }
    }

    public void updateChildState(BulkPackage bulkPackage, State state) throws IlsCoreException {

        if (bulkPackage == null){
            String errorMsg = "Unable to update child state because bulkPackage is null";
            logger.error(errorMsg);
            throw new IlsCoreException(errorMsg);
        }

        if (state == null){
            String errorMsg = "Unable to update child state because state is null";
            logger.error(errorMsg);
            throw new IlsCoreException(errorMsg);
        }

        List<IlsPackage> packages = packageManager.findByBulk(bulkPackage);
        if (packages != null && !packages.isEmpty()) {
            Timestamp now = Timestamp.from(Instant.now());
            for(IlsPackage pack : packages){
                PackageStateLink link = new PackageStateLink();
                link.setCreationDate(now);
                link.setIlsPackage(pack);
                link.setState(state);
                pack.addStateLink(link);
                packageManager.save(pack);
            }
        }
    }

}
