package com.ils.core;

import com.ils.core.notify.impl.NotifyService;
import com.ils.core.notify.impl.UserNotifyMessage;
import static org.junit.Assert.*;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQSession;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jms.*;

/**
 * Created by mara on 10/25/15.
 */
public class NotifyServiceTest extends TestBase {

    @Autowired
    private NotifyService service;

    @Test
    public void testServiceNotNull(){
        assertNotNull(service);
    }

    @Test
    public void testNotifNotSendIfNotifObjectNull() throws JMSException {
        assertNull(waitAndSend(null));
    }

    @Test
    public void testNotifNotSendIfServiceNotEnabled() throws JMSException {
        boolean oldNotifyServiceEnabled = service.isNotifyServiceEnabled();
        service.setNotifyServiceEnabled(false);
        assertNull(waitAndSend(new UserNotifyMessage()));
        service.setNotifyServiceEnabled(oldNotifyServiceEnabled);
    }

    @Test
    public void testNotifSendIfServiceEnabled() throws JMSException {

        UserNotifyMessage userNotifyMessage = new UserNotifyMessage();
        userNotifyMessage.setCountryCode("fr");
        userNotifyMessage.setEmail("email");
        userNotifyMessage.setPhone("phone");
        userNotifyMessage.setText("text");

        TextMessage message = waitAndSend(userNotifyMessage);

        assertNotNull(message);
        assertEquals(userNotifyMessage, parser.toObject(message.getText(), UserNotifyMessage.class));
    }

    private TextMessage waitAndSend(UserNotifyMessage userNotifyMessage) {

        ActiveMQConnectionFactory amqConnectionFactory = null;
        ActiveMQConnection conn = null;
        ActiveMQSession session = null;
        MessageConsumer consumer = null;
        TextMessage textMessage = null;
        try {
            amqConnectionFactory = (ActiveMQConnectionFactory) connectionFactory.getTargetConnectionFactory();
            conn = (ActiveMQConnection) amqConnectionFactory.createConnection();
            session = (ActiveMQSession) conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination dest = session.createTopic("notify.acq.topic");
            consumer = session.createConsumer(dest);
            conn.start();
            service.notifyUser(userNotifyMessage);
            Message message = consumer.receive(1000);
            conn.stop();
            if (message != null) {
                if (!(message instanceof TextMessage)) {
                    Assert.fail("invalid JMS message type, expected TextMessage, but found " + message.getClass());
                }
                return (TextMessage) message;
            }
            return null;


        } catch (JMSException e) {
            Assert.fail();
            throw new RuntimeException(e);

        } finally {
            if (consumer != null) {
                try {
                    consumer.close();
                } catch (JMSException e) {
                    // ignore
                }
            }
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
}
