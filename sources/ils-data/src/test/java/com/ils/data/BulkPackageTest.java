package com.ils.data;

import com.ils.data.exception.DataException;
import com.ils.data.model.*;
import org.junit.Test;
import org.springframework.test.annotation.DirtiesContext;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

/**
 * Created by mara on 10/12/15.
 */
public class BulkPackageTest extends TestBase {

    @Test
    @DirtiesContext
    public void testCreateBulk() {
        try {
            BulkPackage bulk = new BulkPackage();
            String bulkIdentifier = bulk.getIdentifier();
            bulk = bulkPackageRepository.save(bulk);

            assertNotNull(bulk);
            assertEquals(bulk, bulkPackageRepository.findByIdentifier(bulkIdentifier));

        } catch (DataException e) {
            e.printStackTrace();
            fail("No exception should be thrown when creating new package");
        }
    }

    @Test
    @DirtiesContext
    public void testUpdateBulk() {
        try {

            BulkPackage bulk = new BulkPackage();
            String bulkIdentifier = bulk.getIdentifier();
            bulk = bulkPackageRepository.save(bulk);
            assertNotNull(bulk);

            BulkPackage createdBulk = bulkPackageRepository.findByIdentifier(bulkIdentifier);
            assertNotNull(createdBulk);

            Timestamp now = Timestamp.from(Instant.now());
            Payment payment = createPayement();
            String comments = "bulk comment";
            float weight = 10.5f;
            createdBulk.setClearanceFees(payment);
            createdBulk.setShippingFee(payment);
            createdBulk.setExpectedArrivalDate(now);
            createdBulk.setComments(comments);
            createdBulk.setWeight(weight);
            bulkPackageRepository.save(createdBulk);

            IlsPackage ilsPackage1 = createIlsPackage(1);
            IlsPackage ilsPackage2 = createIlsPackage(2);
            ilsPackage1.setBulkPackage(createdBulk);
            ilsPackage2.setBulkPackage(createdBulk);
            packageRepository.save(ilsPackage1);
            packageRepository.save(ilsPackage2);

            // test of update
            BulkPackage updatedBulk = bulkPackageRepository.findByIdentifier(bulkIdentifier);
            assertNotNull(updatedBulk);
            assertEquals(createdBulk, updatedBulk);

            // test created packages
            assertEquals(updatedBulk, packageRepository.findByIdentifier(ilsPackage1.getIdentifier()).getBulkPackage());
            assertEquals(updatedBulk, packageRepository.findByIdentifier(ilsPackage2.getIdentifier()).getBulkPackage());

            // test other parameters
            assertEquals(now, updatedBulk.getExpectedArrivalDate());
            assertEquals(payment, updatedBulk.getClearanceFees());
            assertEquals(payment, updatedBulk.getShippingFee());
            assertEquals(comments, updatedBulk.getComments());

        } catch (DataException e) {
            e.printStackTrace();
            fail("No exception should be thrown when creating new package");
        }
    }

    @Test
    @DirtiesContext
    public void testDeleteBulk() {
        try {

            Payment payment = createPayement();
            BulkPackage bulk = new BulkPackage();
            String bulkIdentifier = bulk.getIdentifier();
            bulk.setClearanceFees(payment);
            bulk.setShippingFee(payment);
            bulk.setExpectedArrivalDate(Timestamp.from(Instant.now()));
            bulk.setComments("bulk comment");
            bulk.setWeight(10.5f);
            bulk = bulkPackageRepository.save(bulk);

            IlsPackage ilsPackage1 = createIlsPackage(1);
            IlsPackage ilsPackage2 = createIlsPackage(2);
            ilsPackage1.setBulkPackage(bulk);
            ilsPackage2.setBulkPackage(bulk);
            packageRepository.save(ilsPackage1);
            packageRepository.save(ilsPackage2);

            // delete the bulk (first remove it from related packages to avoid foreign key constraint violation)
            ilsPackage1.setBulkPackage(null);
            ilsPackage2.setBulkPackage(null);
            packageRepository.save(ilsPackage1);
            packageRepository.save(ilsPackage2);
            bulkPackageRepository.delete(bulk);
            assertNull(bulkPackageRepository.findByIdentifier(bulkIdentifier));

            // test that the remove doesn't remove packages
            assertEquals(ilsPackage1, packageRepository.findByIdentifier(ilsPackage1.getIdentifier()));
            assertNull(packageRepository.findByIdentifier(ilsPackage1.getIdentifier()).getBulkPackage());
            assertEquals(ilsPackage2, packageRepository.findByIdentifier(ilsPackage2.getIdentifier()));
            assertNull(packageRepository.findByIdentifier(ilsPackage2.getIdentifier()).getBulkPackage());

        } catch (DataException e) {
            e.printStackTrace();
            fail("No exception should be thrown");
        }
    }

    @Test
    @DirtiesContext
    public void testBulkStateLink() {
        try {
            BulkPackage bulk = new BulkPackage();
            String bulkIdentifier = bulk.getIdentifier();

            State state1 = new State();
            state1.setValue("created");
            state1.setDescription("package just being created");

            State state2 = new State();
            state2.setValue("shipped");
            state2.setDescription("package just being created");

            State state3 = new State();
            state3.setValue("delivered");
            state3.setDescription("package just being delivered");

            BulkPackageStateLink stateLink1 = new BulkPackageStateLink();
            stateLink1.setCreationDate(Timestamp.from(Instant.now()));
            stateLink1.setState(state1);
            stateLink1.setBulkPackage(bulk);

            BulkPackageStateLink stateLink2 = new BulkPackageStateLink();
            stateLink2.setCreationDate(Timestamp.from(Instant.now()));
            stateLink2.setState(state2);
            stateLink2.setBulkPackage(bulk);

            BulkPackageStateLink stateLink3 = new BulkPackageStateLink();
            stateLink3.setCreationDate(Timestamp.from(Instant.now()));
            stateLink3.setState(state3);
            stateLink3.setBulkPackage(bulk);

            bulk.addStateLinkList(stateLink1);
            bulk.addStateLinkList(stateLink2);
            bulk.addStateLinkList(stateLink3);

            bulkPackageRepository.save(bulk);

            bulk = bulkPackageRepository.findByIdentifier(bulkIdentifier);
            List<BulkPackageStateLink> links = bulkPackageStateLinkRepository.findByBulk(bulk);
            assertNotNull(links);
            assertThat(links.size(), is(3));

        } catch (DataException e) {
            e.printStackTrace();
            fail("No exception should be thrown");
        }
    }

    private Payment createPayement() {
        Payment payement = new Payment();
        payement.setComments("Payment comment");
        payement.setDiscount(0.5f);
        payement.setPaid(85.0f);
        payement.setPrice(100.0f);
        payement.setPaymentMethod(createPaymentMethod());
        return payement;
    }

    private PaymentMethod createPaymentMethod() {
        PaymentMethod method = new PaymentMethod();
        method.setDescription("method description");
        method.setValue("CB");
        return method;
    }

    private IlsPackage createIlsPackage(int index) throws DataException {
        IlsPackage ilsPackage = new IlsPackage();
        IlsUser user = createUser(index);
        ilsPackage.setSender(user);
        ilsPackage.setRecipient(user);
        return ilsPackage;
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
