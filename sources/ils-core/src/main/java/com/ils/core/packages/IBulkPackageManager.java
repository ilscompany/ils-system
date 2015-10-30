package com.ils.core.packages;

import com.ils.core.exception.IlsCoreException;
import com.ils.data.model.BulkPackage;
import com.ils.data.model.BulkPackageStateLink;
import com.ils.data.model.State;

import java.util.List;

/**
 * Created by mara on 10/11/15.
 */
public interface IBulkPackageManager {

    void delete(BulkPackage bulkPackage) throws IlsCoreException;

    BulkPackage save(BulkPackage bulkPackage) throws IlsCoreException;

    BulkPackage findByIdentifier(String identifier) throws IlsCoreException;

    List<BulkPackageStateLink> findStateLinks(BulkPackage bulkPackage) throws IlsCoreException;

    void updateChildState(BulkPackage bulkPackage, State state) throws IlsCoreException;
}
