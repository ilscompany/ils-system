package com.ils.data;

import com.ils.data.exception.DataException;
import com.ils.data.model.*;
import org.junit.Test;
import org.springframework.test.annotation.DirtiesContext;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by mara on 10/12/15.
 */
public class PackageTest extends TestBase {

    @Test
    @DirtiesContext
    public void testCreateNewPackage() {
        try {
            IlsUser sender = createUser(0);
            IlsUser recipient = createUser(1);
            IlsPackage ilsPackage = new IlsPackage();
            String packageIdentifier = ilsPackage.getIdentifier();
            ilsPackage.setSender(sender);
            ilsPackage.setRecipient(recipient);

            ilsPackage = packageRepository.save(ilsPackage);
            assertNotNull(ilsPackage);

            ilsPackage = packageRepository.findByIdentifier(packageIdentifier);
            assertNotNull(ilsPackage);
            assertEquals(ilsPackage.getSender(), sender);
            assertEquals(ilsPackage.getRecipient(), recipient);

        } catch (DataException e) {
            e.printStackTrace();
            fail("No exception should be thrown when creating new package");
        }
    }

    @Test
    @DirtiesContext
    public void testUpdatePackage() {
        try {
            IlsUser sender = createUser(0);
            IlsUser recipient = createUser(1);
            IlsPackage ilsPackage = new IlsPackage();
            String packageIdentifier = ilsPackage.getIdentifier();
            ilsPackage.setSender(sender);
            ilsPackage.setRecipient(recipient);

            packageRepository.save(ilsPackage);
            ilsPackage = packageRepository.findByIdentifier(packageIdentifier);

            BulkPackage bulk = new BulkPackage();
            bulk = bulkPackageRepository.save(bulk);
            ilsPackage.setBulkPackage(bulk);

            packageRepository.save(ilsPackage);

            IlsPackage ilsPackageUpdated = packageRepository.findByIdentifier(packageIdentifier);
            assertNotNull(ilsPackageUpdated);
            assertEquals(ilsPackageUpdated.getSender(), sender);
            assertEquals(ilsPackageUpdated.getRecipient(), recipient);
            assertEquals(bulk, ilsPackageUpdated.getBulkPackage());

        } catch (DataException e) {
            e.printStackTrace();
            fail("No exception should be thrown when creating new package");
        }
    }

    @Test
    @DirtiesContext
    public void testDeletePackage(){
        try {
            IlsUser sender = createUser(0);
            IlsUser recipient = createUser(1);
            IlsPackage ilsPackage = new IlsPackage();
            String packageIdentifier = ilsPackage.getIdentifier();
            ilsPackage.setSender(sender);
            ilsPackage.setRecipient(recipient);

            BulkPackage bulk = new BulkPackage();
            String bulkIdentifier = bulk.getIdentifier();
            bulk = bulkPackageRepository.save(bulk);
            ilsPackage.setBulkPackage(bulk);

            State state1 = new State();
            state1.setValue("created");
            state1.setDescription("package just being created");

            PackageStateLink stateLink1 = new PackageStateLink();
            stateLink1.setCreationDate(Timestamp.from(Instant.now()));
            stateLink1.setState(state1);

            State state2 = new State();
            state2.setValue("created");
            state2.setDescription("package just being created");

            PackageStateLink stateLink2 = new PackageStateLink();
            stateLink2.setCreationDate(Timestamp.from(Instant.now()));
            stateLink2.setState(state2);

            ilsPackage.addStateLink(stateLink1);
            ilsPackage.addStateLink(stateLink2);

            ilsPackage = packageRepository.save(ilsPackage);

            assertEquals(ilsPackage, packageRepository.findByIdentifier(packageIdentifier));
            packageRepository.delete(ilsPackage);
            assertNull(packageRepository.findByIdentifier(packageIdentifier));

            // assert that related users are not deleted
            assertEquals(sender, userRepository.findByUsername("username_0"));
            assertEquals(recipient, userRepository.findByUsername("username_1"));

            // assert that related bulk is not deleted
            assertEquals(bulk, bulkPackageRepository.findByIdentifier(bulkIdentifier));

        } catch (DataException e) {
            e.printStackTrace();
            fail("No exception should be thrown");
        }
    }

    @Test
    @DirtiesContext
    public void testFindBySender(){

        try {
            // first create 2 package with the same sender
            IlsUser sender = createUser(0);
            IlsUser recipient = createUser(1);

            IlsPackage ilsPackage1 = new IlsPackage();
            ilsPackage1.setSender(sender);
            ilsPackage1.setRecipient(recipient);

            IlsPackage ilsPackage2 = new IlsPackage();
            ilsPackage2.setSender(sender);
            ilsPackage2.setRecipient(recipient);

            packageRepository.save(ilsPackage1);
            packageRepository.save(ilsPackage2);

            List<IlsPackage> packages = packageRepository.findBySender(sender);
            assertNotNull(packages);
            assertEquals(2, packages.size());
            assertThat(packages, contains(ilsPackage1, ilsPackage2));

        } catch (DataException e) {
            e.printStackTrace();
            fail("No exception should be thrown");
        }

    }

    @Test
    @DirtiesContext
    public void testFindByRecipient(){

        try {
            // first create 3 package with the same recipient
            IlsUser sender = createUser(0);
            IlsUser recipient = createUser(1);

            IlsPackage ilsPackage1 = new IlsPackage();
            ilsPackage1.setSender(sender);
            ilsPackage1.setRecipient(recipient);

            IlsPackage ilsPackage2 = new IlsPackage();
            ilsPackage2.setSender(sender);
            ilsPackage2.setRecipient(recipient);

            IlsPackage ilsPackage3 = new IlsPackage();
            ilsPackage3.setSender(sender);
            ilsPackage3.setRecipient(recipient);

            packageRepository.save(ilsPackage1);
            packageRepository.save(ilsPackage2);
            packageRepository.save(ilsPackage3);

            List<IlsPackage> packages = packageRepository.findByRecipient(recipient);
            assertNotNull(packages);
            assertEquals(3, packages.size());
            assertThat(packages, contains(ilsPackage1, ilsPackage2, ilsPackage3));

        } catch (DataException e) {
            e.printStackTrace();
            fail("No exception should be thrown");
        }
    }

    @Test
    @DirtiesContext
    public void testFindByBulk(){

        try {
            // first create 3 package in the same bulk package
            IlsUser sender = createUser(0);
            IlsUser recipient = createUser(1);

            IlsPackage ilsPackage1 = new IlsPackage();
            ilsPackage1.setSender(sender);
            ilsPackage1.setRecipient(recipient);

            IlsPackage ilsPackage2 = new IlsPackage();
            ilsPackage2.setSender(sender);
            ilsPackage2.setRecipient(recipient);

            IlsPackage ilsPackage3 = new IlsPackage();
            ilsPackage3.setSender(sender);
            ilsPackage3.setRecipient(recipient);

            BulkPackage bulk = new BulkPackage();
            bulk = bulkPackageRepository.save(bulk);

            ilsPackage1.setBulkPackage(bulk);
            ilsPackage2.setBulkPackage(bulk);
            ilsPackage3.setBulkPackage(bulk);

            packageRepository.save(ilsPackage1);
            packageRepository.save(ilsPackage2);
            packageRepository.save(ilsPackage3);

            List<IlsPackage> packages = packageRepository.findByBulk(bulk);
            assertNotNull(packages);
            assertEquals(3, packages.size());
            assertThat(packages, contains(ilsPackage1, ilsPackage2, ilsPackage3));

        } catch (DataException e) {
            e.printStackTrace();
            fail("No exception should be thrown");
        }
    }

    @Test
    @DirtiesContext
    public void testPackageStateLink(){
        try {
            IlsUser sender = createUser(0);
            IlsUser recipient = createUser(1);
            IlsPackage ilsPackage = new IlsPackage();
            String packageIdentifier = ilsPackage.getIdentifier();
            ilsPackage.setSender(sender);
            ilsPackage.setRecipient(recipient);

            State state1 = new State();
            state1.setValue("created");
            state1.setDescription("package just being created");

            PackageStateLink stateLink1 = new PackageStateLink();
            stateLink1.setCreationDate(Timestamp.from(Instant.now()));
            stateLink1.setState(state1);
            stateLink1.setIlsPackage(ilsPackage);

            State state2 = new State();
            state2.setValue("shipped");
            state2.setDescription("package just being created");

            State state3 = new State();
            state3.setValue("delivered");
            state3.setDescription("package just being delivered");

            PackageStateLink stateLink2 = new PackageStateLink();
            stateLink2.setCreationDate(Timestamp.from(Instant.now()));
            stateLink2.setState(state2);
            stateLink2.setIlsPackage(ilsPackage);

            PackageStateLink stateLink3 = new PackageStateLink();
            stateLink3.setCreationDate(Timestamp.from(Instant.now()));
            stateLink3.setState(state3);
            stateLink3.setIlsPackage(ilsPackage);

            ilsPackage.addStateLink(stateLink1);
            ilsPackage.addStateLink(stateLink2);
            ilsPackage.addStateLink(stateLink3);

            packageRepository.save(ilsPackage);

            ilsPackage = packageRepository.findByIdentifier(packageIdentifier);
            List<PackageStateLink> links = packageStateLinkRepository.findByPackage(ilsPackage);
            assertNotNull(links);
            assertThat(links.size(), is(3));

        } catch (DataException e) {
            e.printStackTrace();
            fail("No exception should be thrown");
        }
    }

    private IlsUser createUser(int index) throws DataException {
        IlsUser user = new IlsUser();
        user.setUsername("username_" + index);
        user.setPassword("password_" + index);
        user.setFirstname("firstname_" + index);
        user.setLastname("lastname_" + index);
        user.setEmail("email1@ils.com" + index);
        user.setPhone("phone_" + index);
        user.setAddress(createAddress());
        return userRepository.save(user);
    }

    private Address createAddress() {
        Address address = new Address();
        address.setAddress("address");
        address.setCity("City");
        address.setCountry("Country");
        address.setZipcode(00000);
        address.setAdditionalAddress("additional address");
        return address;
    }
}
