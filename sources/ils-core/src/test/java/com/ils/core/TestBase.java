package com.ils.core;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.ils.commons.json.JsonParser;
import com.ils.core.packages.IBulkPackageManager;
import com.ils.core.packages.IPackageManager;
import com.ils.core.user.IUserManager;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.TransportConnector;
import org.apache.activemq.broker.region.policy.PolicyEntry;
import org.apache.activemq.broker.region.policy.PolicyMap;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
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
@ContextConfiguration(locations = {"classpath:ils-data-spring-context.xml",
        "classpath:ils-core-spring-context.xml", "classpath:ils-commons-spring-context.xml"})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
public abstract class TestBase {

    protected BrokerService broker;
    protected TransportConnector connector;

    @Autowired
    protected IBulkPackageManager bulkPackageManager;

    @Autowired
    protected IPackageManager packageManager;

    @Autowired
    protected IUserManager userManager;

    @Autowired
    protected CachingConnectionFactory connectionFactory;

    @Autowired
    @Qualifier("jmsTopicTemplate")
    protected JmsTemplate jmsTemplate;

    @Autowired
    protected JsonParser parser;

    @Value("${jms.broker.url}")
    protected String jmsBrokerUrl;

    protected BrokerService createBroker() throws Exception {
        BrokerService service = new BrokerService();
        service.setPersistent(false);
        service.setUseJmx(false);

        // Setup policy
        PolicyMap policyMap = new PolicyMap();
        PolicyEntry policy = new PolicyEntry();
        policy.setConsumersBeforeDispatchStarts(1);
        policyMap.setDefaultEntry(policy);
        service.setDestinationPolicy(policyMap);
        connector = service.addConnector(jmsBrokerUrl);
        return service;
    }

}
