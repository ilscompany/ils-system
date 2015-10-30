package com.ils.core.notify.impl;

import com.ils.commons.json.JsonParser;
import com.ils.core.notify.INotifyService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.jms.*;

/**
 * Created by mara on 10/25/15.
 */
@Service
public class NotifyService implements INotifyService {

    private static final Logger logger = Logger.getLogger(NotifyService.class);

    @Autowired
    @Qualifier("jmsTopicTemplate")
    private JmsTemplate jmsTemplate;

    @Autowired
    @Qualifier("notifyAcqTopic")
    private Topic notifyAcqTopic;

    @Autowired
    private JsonParser parser;

    @Value("${notify.service.enabled}")
    private Boolean notifyServiceEnabled;

    public void notifyUser(UserNotifyMessage userNotifyMessage) {

        // envoi du message de notification si le service est active
        if (userNotifyMessage != null && isNotifyServiceEnabled()){

            debug("start sending acq notification message to both email service and sms service");
            sendMessage(userNotifyMessage);

        }else {
            debug("notification service is requested but sending notification to users is not enabled," +
                    " please see the value of config parameter [notify.service.enabled]");
        }
    }

    private void sendMessage(final UserNotifyMessage acqMessage) {
        jmsTemplate.send(notifyAcqTopic, new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                String text = parser.toJson(acqMessage);
                return session.createTextMessage(text);
            }
        });
    }

    public boolean isNotifyServiceEnabled() {
        return notifyServiceEnabled;
    }

    public void setNotifyServiceEnabled(boolean notifyServiceEnabled) {
        this.notifyServiceEnabled = notifyServiceEnabled;
    }

    private void debug(String text) {
        if (logger.isDebugEnabled()) {
            logger.debug(text);
        }
    }
}
