package com.ils.core;

import static org.junit.Assert.*;

import com.ils.core.exception.IlsCoreException;
import com.ils.data.model.*;
import static org.hamcrest.Matchers.is;
import org.junit.Test;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

/**
 * Created by mara on 10/16/15.
 */
public class BulkPackageManagerTest extends TestBase{

    @Test
    @DirtiesContext
    public void testUpdateChildState(){
        try {

            BulkPackage bulk = new BulkPackage();
            bulk = bulkPackageManager.save(bulk);

            IlsPackage ilsPackage1 = createIlsPackage(1);
            IlsPackage ilsPackage2 = createIlsPackage(2);
            ilsPackage1.setBulkPackage(bulk);
            ilsPackage2.setBulkPackage(bulk);
            packageManager.save(ilsPackage1);
            packageManager.save(ilsPackage2);

            State state1 = new State();
            state1.setValue("CREATED");
            state1.setDescription("JUST CREATED");

            bulkPackageManager.updateChildState(bulk, state1);

            List<PackageStateLink> links1 = packageManager.findStateLinks(ilsPackage1);
            assertNotNull(links1);
            assertThat(links1.size(), is(1));
            assertEquals(state1, links1.get(0).getState());

            List<PackageStateLink> links2 = packageManager.findStateLinks(ilsPackage2);
            assertNotNull(links2);
            assertThat(links2.size(), is(1));
            assertEquals(state1, links2.get(0).getState());

            State state2 = new State();
            state2.setValue("SHIPPED");
            state2.setDescription("JUST SHIPPED");

            bulkPackageManager.updateChildState(bulk, state2);

            links1 = packageManager.findStateLinks(ilsPackage1);
            assertNotNull(links1);
            assertThat(links1.size(), is(2));

            links2 = packageManager.findStateLinks(ilsPackage2);
            assertNotNull(links2);
            assertThat(links2.size(), is(2));

        } catch (Exception e) {
            e.printStackTrace();
            fail("No exception should be thrown");
        }
    }

    private IlsPackage createIlsPackage(int index) throws IlsCoreException {
        IlsPackage ilsPackage = new IlsPackage();
        IlsUser user = createUser(index);
        ilsPackage.setSender(user);
        ilsPackage.setRecipient(user);
        return ilsPackage;
    }

    private IlsUser createUser(int index) throws IlsCoreException {
        IlsUser user = new IlsUser();
        user.setUsername("username_" + index);
        user.setPassword("password_" + index);
        user.setFirstname("firstname_" + index);
        user.setLastname("lastname_" + index);
        user.setEmail("email1@ils.com" + index);
        user.setPhone("phone_" + index);
        user.setAddress(createAddress());
        return userManager.save(user);
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
