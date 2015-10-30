package com.ils.data;

import com.ils.data.exception.DataException;
import com.ils.data.model.Address;
import com.ils.data.model.IlsUser;

import org.junit.Test;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

/**
 * Created by mara on 7/12/15.
 */
public class UserRepositoryTest extends TestBase {

    @Test
    @DirtiesContext
    public void testInsert() {
        try {
            IlsUser user = userRepository.save(createUser("firstname", "lastname", "email1@ils.com", "phone"));
            assertNotNull(user);
            assertNotNull(user.getId());

        } catch (DataException e) {
            e.printStackTrace();
            fail("No exception should be thrown when inserting a user");
        }
    }

    @Test
    @DirtiesContext
    public void testUpdate() {
        try {
            IlsUser user = userRepository.save(createUser("firstname1", "lastname1", "email2@ils.com", "phone1"));
            assertNotNull(user);
            assertNotNull(user.getId());

            user.setPhone("1111111111");
            user.getAddress().setCity("conakry");
            user.getAddress().setCountry("Guinee");
            user = userRepository.save(user);
            assertNotNull(user);

            assertEquals("1111111111", user.getPhone());
            assertEquals("conakry", user.getAddress().getCity());
            assertEquals("Guinee", user.getAddress().getCountry());

        } catch (DataException e) {
            e.printStackTrace();
            fail("No exception should be thrown when updating a user");
        }
    }

    @Test
    @DirtiesContext
    public void testFindByFirstName() {
        try {
            IlsUser user = userRepository.save(createUser("toto", "titi", "toto@ils.com", "0123456789"));
            assertNotNull(user);
            assertNotNull(user.getId());

            List<IlsUser> savedUsers = userRepository.findByFirstName("toto");
            assertNotNull(savedUsers);
            assertEquals(1, savedUsers.size());
            assertEquals(user, savedUsers.get(0));

            IlsUser user2 = createUser("toto", "fapengou", "fapengou@ils.com", "6666666666");
            user2 = userRepository.save(user2);
            assertNotNull(user2);
            assertNotNull(user2.getId());

            List<IlsUser> savedUsers2 = userRepository.findByFirstName("toto");
            assertNotNull(savedUsers2);
            assertThat(savedUsers2.size(), is(2));
            assertThat(savedUsers2, contains(
                    allOf(
                            hasProperty("firstname", is("toto")),
                            hasProperty("lastname", is("titi")),
                            hasProperty("phone", is("0123456789")),
                            hasProperty("email", is("toto@ils.com"))
                    ),
                    allOf(
                            hasProperty("firstname", is("toto")),
                            hasProperty("lastname", is("fapengou")),
                            hasProperty("phone", is("6666666666")),
                            hasProperty("email", is("fapengou@ils.com"))
                    )

            ));

            List<IlsUser> emptyUsers = userRepository.findByFirstName("blabla");
            assertNotNull(emptyUsers);
            assertTrue(emptyUsers.isEmpty());

        } catch (DataException e) {
            e.printStackTrace();
            fail("No exception should be thrown");
        }
    }

    @Test
    @DirtiesContext
    public void testFindByLastName() {
        try {
            IlsUser user = userRepository.save(createUser("toto", "tito", "toto1@ils.com", "01234567890"));
            assertNotNull(user);
            assertNotNull(user.getId());

            List<IlsUser> savedUsers = userRepository.findByLastName("tito");
            assertNotNull(savedUsers);
            assertEquals(1, savedUsers.size());
            assertEquals(user, savedUsers.get(0));

            IlsUser user2 = createUser("Montaigne", "tito", "fapengou@ils.com", "5555555555");
            user2.setAddress(createAddress());
            user2 = userRepository.save(user2);
            assertNotNull(user2);
            assertNotNull(user2.getId());

            List<IlsUser> savedUsers2 = userRepository.findByLastName("tito");
            assertNotNull(savedUsers2);
            assertEquals(2, savedUsers2.size());
            assertThat(savedUsers2.size(), is(2));
            assertThat(savedUsers2, contains(
                    allOf(
                            hasProperty("firstname", is("toto")),
                            hasProperty("lastname", is("tito")),
                            hasProperty("phone", is("01234567890")),
                            hasProperty("email", is("toto1@ils.com"))
                    ),
                    allOf(
                            hasProperty("firstname", is("Montaigne")),
                            hasProperty("lastname", is("tito")),
                            hasProperty("phone", is("5555555555")),
                            hasProperty("email", is("fapengou@ils.com"))
                    )

            ));


            List<IlsUser> emptyUsers = userRepository.findByLastName("blabla");
            assertNotNull(emptyUsers);
            assertTrue(emptyUsers.isEmpty());

        } catch (DataException e) {
            e.printStackTrace();
            fail("No exception should be thrown");
        }
    }

    @Test
    @DirtiesContext
    public void testFindByPhoneNumber() {
        try {
            IlsUser user = userRepository.save(createUser("oncool", "oncool", "toto1@ils.com", "9876543210"));
            assertNotNull(user);
            assertNotNull(user.getId());

            IlsUser savedUser = userRepository.findByPhoneNumber("9876543210");
            assertNotNull(savedUser);
            assertNotNull(savedUser.getId());
            assertEquals(user, savedUser);
            assertEquals(user.getId(), savedUser.getId());

            IlsUser nonExistingUser = userRepository.findByPhoneNumber("unknown_phonenumber");
            assertNull(nonExistingUser);

        } catch (DataException e) {
            e.printStackTrace();
            fail("No exception should be thrown");
        }
    }

    @Test
    @DirtiesContext
    public void testFindByEmail() {
        try {
            IlsUser user = userRepository.save(createUser("totolito", "Korben", "toti@ils.com", "9876543210"));
            assertNotNull(user);
            assertNotNull(user.getId());

            IlsUser savedUser = userRepository.findByEmail("toti@ils.com");
            assertNotNull(savedUser);
            assertNotNull(savedUser.getId());
            assertEquals(user, savedUser);
            assertEquals(user.getId(), savedUser.getId());

            IlsUser nonExistingUser = userRepository.findByEmail("unknown_email");
            assertNull(nonExistingUser);

        } catch (DataException e) {
            e.printStackTrace();
            fail("No exception should be thrown");
        }
    }

    @Test
    @DirtiesContext
    public void testFindAll() {
        try {
            userRepository.save(createUser("toto", "fapengou", "xfapengou@ils.com", "8888888888"));
            userRepository.save(createUser("toto", "fapengou", "yfapengou@ils.com", "7777777777"));
            userRepository.save(createUser("toto", "fapengou", null, "6666666666"));
            userRepository.save(createUser("toto", "fapengou", null, "0000000000"));
            List<IlsUser> allUsers = userRepository.findAll();
            assertThat(allUsers.size(), is(4));

        } catch (DataException e) {
            e.printStackTrace();
            fail("No exception should be thrown");
        }
    }

    private IlsUser createUser(String firstname, String lastname, String email, String phone) {
        IlsUser user = new IlsUser();
        user.setUsername(phone);
        user.setPassword("password");        
        user.setFirstname(firstname);
        user.setLastname(lastname);
        user.setEmail(email);
        user.setPhone(phone);
        user.setAddress(createAddress());
        return user;
    }

    private Address createAddress() {
        Address address = new Address();
        address.setAddress("230 avenue du general leclerc");
        address.setCity("viroflay");
        address.setCountry("France");
        address.setZipcode(78220);
        address.setAdditionalAddress("3eme etage");
        return address;
    }
}
