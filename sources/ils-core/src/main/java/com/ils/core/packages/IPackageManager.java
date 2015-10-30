package com.ils.core.packages;

import com.ils.core.exception.IlsCoreException;
import com.ils.data.model.BulkPackage;
import com.ils.data.model.IlsPackage;
import com.ils.data.model.PackageStateLink;
import com.ils.data.model.IlsUser;

import java.util.List;

/**
 * Created by mara on 10/11/15.
 */
public interface IPackageManager {

    void delete(IlsPackage ilsPackage) throws IlsCoreException;

    IlsPackage save(IlsPackage ilsPackage) throws IlsCoreException;

    IlsPackage findByIdentifier(String identifier) throws IlsCoreException;

    List<IlsPackage> findBySender(IlsUser sender) throws IlsCoreException;

    List<IlsPackage> findByRecipient(IlsUser recipient) throws IlsCoreException;

    List<IlsPackage> findByBulk(BulkPackage bulkPackage) throws IlsCoreException;

    List<PackageStateLink> findStateLinks(IlsPackage ilsPackage) throws IlsCoreException;
}
