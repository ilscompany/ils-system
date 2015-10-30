package com.ils.sms.notify.listener;

import com.ils.core.notify.impl.UserNotifyMessage;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jms.JMSException;
import javax.jms.TextMessage;

import static org.junit.Assert.*;

public class SmsNotifyMessageListenerTest extends TestBase {

    private static final String COUNTRY_CODE_PROPERTY_NAME = "country_code";

    @Autowired
    private SmsNotifyMessageListener listener;

    @Test
    public void testListenerNotNull() throws JMSException {
        assertNotNull(listener);
    }

    @Test
    public void testMessageNotSentIfNotEnabled() throws JMSException {
        UserNotifyMessage notifyMessage = new UserNotifyMessage();
        Boolean previousValue = listener.isSmsNotifyEnabled();
        listener.setSmsNotifyEnabled(false);
        listener.onMessage(toJmsTextMessage(notifyMessage));
        assertNull(readFromGatewaysQueue());
        listener.setSmsNotifyEnabled(previousValue);
    }

    @Test
    public void testSendMessageIfAllOK() throws JMSException {
        UserNotifyMessage notifyMessage = new UserNotifyMessage();
        notifyMessage.setEmail("email");
        notifyMessage.setCountryCode("country");
        notifyMessage.setPhone("phone");
        notifyMessage.setText("text");

        listener.onMessage(toJmsTextMessage(notifyMessage));

        TextMessage message = readFromGatewaysQueue();
        assertNotNull(message);

        assertEquals(notifyMessage, parser.toObject(message.getText(), UserNotifyMessage.class));

        // test de la propriete JMS country code
        assertEquals(notifyMessage.getCountryCode(), message.getStringProperty(COUNTRY_CODE_PROPERTY_NAME));
    }

    @Test
    public void testMessageNotSentIfCountryCodeNull() throws JMSException {
        UserNotifyMessage notifyMessage = new UserNotifyMessage();
        notifyMessage.setPhone("phone");
        notifyMessage.setEmail("email");
        notifyMessage.setText("text");
        listener.onMessage(toJmsTextMessage(notifyMessage));
        assertNull(readFromGatewaysQueue());
    }

    @Test
    public void testMessageNotSentIfCountryCodeNullString() throws JMSException {
        UserNotifyMessage notifyMessage = new UserNotifyMessage();
        notifyMessage.setPhone("phone");
        notifyMessage.setEmail("email");
        notifyMessage.setText("text");
        notifyMessage.setCountryCode("null");
        listener.onMessage(toJmsTextMessage(notifyMessage));
        assertNull(readFromGatewaysQueue());
    }

    @Test
    public void testMessageNotSentIfCountryCodeEmpty() throws JMSException  {
        UserNotifyMessage notifyMessage = new UserNotifyMessage();
        notifyMessage.setPhone("phone");
        notifyMessage.setEmail("email");
        notifyMessage.setText("text");
        notifyMessage.setCountryCode("");
        listener.onMessage(toJmsTextMessage(notifyMessage));
        assertNull(readFromGatewaysQueue());
    }

    @Test
    public void testMessageNotSentIfPhoneNull() throws JMSException {
        UserNotifyMessage notifyMessage = new UserNotifyMessage();
        notifyMessage.setEmail("email");
        notifyMessage.setText("text");
        notifyMessage.setCountryCode("country");
        listener.onMessage(toJmsTextMessage(notifyMessage));
        assertNull(readFromGatewaysQueue());
    }

    @Test
    public void testMessageNotSentIfPhoneNullString() throws JMSException {
        UserNotifyMessage notifyMessage = new UserNotifyMessage();
        notifyMessage.setEmail("email");
        notifyMessage.setText("text");
        notifyMessage.setCountryCode("country");
        notifyMessage.setPhone("null");
        listener.onMessage(toJmsTextMessage(notifyMessage));
        assertNull(readFromGatewaysQueue());
    }

    @Test
    public void testMessageNotSentIfPhoneEmpty() throws JMSException {
        UserNotifyMessage notifyMessage = new UserNotifyMessage();
        notifyMessage.setEmail("email");
        notifyMessage.setText("text");
        notifyMessage.setCountryCode("country");
        notifyMessage.setPhone("");
        listener.onMessage(toJmsTextMessage(notifyMessage));
        assertNull(readFromGatewaysQueue());
    }

    @Test
    public void testMessageNotSentIfTextNull() throws JMSException {
        UserNotifyMessage notifyMessage = new UserNotifyMessage();
        notifyMessage.setEmail("email");
        notifyMessage.setCountryCode("country");
        notifyMessage.setPhone("phone");
        listener.onMessage(toJmsTextMessage(notifyMessage));
        assertNull(readFromGatewaysQueue());
    }

    @Test
    public void testMessageNotSentIfTextNullString() throws JMSException {
        UserNotifyMessage notifyMessage = new UserNotifyMessage();
        notifyMessage.setEmail("email");
        notifyMessage.setCountryCode("country");
        notifyMessage.setPhone("phone");
        notifyMessage.setText("null");
        listener.onMessage(toJmsTextMessage(notifyMessage));
        assertNull(readFromGatewaysQueue());
    }

    @Test
    public void testMessageNotSentIfTextEmpty() throws JMSException {
        UserNotifyMessage notifyMessage = new UserNotifyMessage();
        notifyMessage.setEmail("email");
        notifyMessage.setCountryCode("country");
        notifyMessage.setPhone("phone");
        notifyMessage.setText("");
        listener.onMessage(toJmsTextMessage(notifyMessage));
        assertNull(readFromGatewaysQueue());
    }

}
