package com.ils.sms.notify.listener;

import com.ils.commons.json.JsonParser;
import com.ils.core.notify.impl.UserNotifyMessage;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQSession;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.TransportConnector;
import org.apache.activemq.broker.region.policy.PolicyEntry;
import org.apache.activemq.broker.region.policy.PolicyMap;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.jms.*;

/**
 * Created by mara on 10/24/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:ils-sms-notify-spring-context.xml", "classpath:ils-commons-spring-context.xml"})
public abstract class TestBase {

    protected BrokerService broker;
    protected TransportConnector connector;

    @Autowired
    protected JsonParser parser;

    @Autowired
    protected CachingConnectionFactory connectionFactory;

    @Autowired
    @Qualifier("jmsQueueTemplate")
    protected JmsTemplate jmsQueueTemplate;

    @Value("${jms.broker.url}")
    protected String jmsBrokerUrl;

    @Before
    public void setUp() throws Exception {
        broker = createBroker();
        broker.start();
    }

    @After
    public void tearDown() throws Exception {
        if (connector != null) {
            connector.stop();
        }
        if (broker != null) {
            broker.stop();
        }
    }

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

    protected TextMessage readFromGatewaysQueue() throws JMSException {
        jmsQueueTemplate.setReceiveTimeout(1000);
        Message message = jmsQueueTemplate.receive("gateways.notify.queue");
        if (message != null) {
            if (!(message instanceof TextMessage)) {
                Assert.fail("invalid JMS message type, expected TextMessage, but found " + message.getClass());
            }
            return (TextMessage) message;
        }
        return null;
    }

    protected TextMessage toJmsTextMessage(UserNotifyMessage notifyMessage) {

        ActiveMQConnectionFactory amqConnectionFactory = null;
        ActiveMQConnection conn = null;
        ActiveMQSession session = null;
        TextMessage textMessage = null;
        try {
            amqConnectionFactory = (ActiveMQConnectionFactory) connectionFactory.getTargetConnectionFactory();
            conn = (ActiveMQConnection) amqConnectionFactory.createConnection();
            session = (ActiveMQSession) conn.createSession(true, Session.AUTO_ACKNOWLEDGE);
            return session.createTextMessage(parser.toJson(notifyMessage));

        } catch (JMSException e) {
            Assert.fail();
            throw new RuntimeException(e);

        } finally {
            if (session != null) {
                try {
                    session.close();
                } catch (JMSException e) {
                    // ignore
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (JMSException e) {
                    // ignore
                }
            }
        }
    }
}
