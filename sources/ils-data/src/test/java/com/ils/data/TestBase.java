package com.ils.data;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.ils.data.repository.*;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

/**
 * Created by mara on 6/21/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:ils-data-spring-context.xml"})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
public abstract class TestBase {

    @Autowired
    protected AddressRepository addressRepository;

    @Autowired
    protected BulkPackageRepository bulkPackageRepository;

    @Autowired
    protected BulkPackageStateLinkRepository bulkPackageStateLinkRepository;

    @Autowired
    protected PackageRepository packageRepository;

    @Autowired
    protected PackageStateLinkRepository packageStateLinkRepository;

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected PaymentRepository paymentRepository;

    @Autowired
    protected PaymentMethodRepository paymentMethodRepository;

    @Autowired
    protected StateRepository stateRepository;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

}
