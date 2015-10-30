package com.ils.sms.notify.listener;

import com.ils.commons.config.JndiPropertyConfigurer;
import com.ils.commons.json.JsonParser;
import com.ils.core.notify.impl.UserNotifyMessage;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.jms.*;
import javax.jms.IllegalStateException;

/**
 * Cette class est responsable de l'envoi des messages de notification
 * aux clients par SMS. Un message de notification, pour etre envoye
 * par SMS doit d'abord transiter par un gateway ILS qui se chargera
 * in fine d'envoyer le message au destinataire sous forme de SMS.
 * Les echanges de messages entre les gateways et le serveur d'application
 * passent par une queue JMS. Tous les messages quelque soit le pays du
 * destinataire sont envoyes dans la meme queue JMS.
 * <p>
 * Pour filtrer les messages qui sont destines a un pays specifique,
 * chaque message dans la queue porte une propriete correspondant au code
 * du pays de destination. Les requetes REST des gateways se base sur ce
 * parametre pour ne selectionner que les messages qui leur sont
 * destines.
 * <p>
 * <p>
 * Created by mara on 10/21/15.
 */
@Service("smsNotifyMessageListener")
public class SmsNotifyMessageListener implements MessageListener {

    private static final Logger logger = Logger.getLogger(SmsNotifyMessageListener.class);

    private static final String COUNTRY_CODE_PROPERTY_NAME = "country_code";

    @Autowired
    @Qualifier("config")
    private JndiPropertyConfigurer config;

    @Autowired
    @Qualifier("jmsQueueTemplate")
    private JmsTemplate jmsTemplate;

    @Autowired
    @Qualifier("gatewaysNotifyQueue")
    private Queue gatewaysNotifyQueue;

    @Autowired
    private JsonParser parser;

    @Value("${sms.notify.enabled}")
    private Boolean smsNotifyEnabled;

    public void onMessage(Message message) {

        // envoi de notification par sms active ?
        if (isSmsNotifyEnabled()) {

            debug("start processing new user notify message by SMS");
            String textMessage = getMessageText(message);
            debug("notification message being processed : " + textMessage);

            // conversion du message JSON
            UserNotifyMessage acqMessage = (UserNotifyMessage) parser.toObject(textMessage, UserNotifyMessage.class);

            // envoi du message dans la queue de diffusion aux gateways
            sendMessage(acqMessage);

        } else {
            debug("new client notification message received but sending notifications by SMS is not enabled");
        }
    }

    private String getMessageText(Message message) {
        try {
            if (message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) message;
                return textMessage.getText();

            } else {
                String error = "Unsupported JMS message type received, TextMessage expected but found : " + message.getClass();
                logger.error(error);
                throw new IllegalStateException(error);
            }

        } catch (JMSException e) {
            String error = "an error occurred while processing an incoming notification message : " + e.getMessage();
            logger.error(error, e);
            throw new RuntimeException(error);
        }
    }

    private void sendMessage(final UserNotifyMessage acqMessage) {

        if (allowSendingMessage(acqMessage)) {
            jmsTemplate.send(gatewaysNotifyQueue, new MessageCreator() {

                public Message createMessage(Session session) throws JMSException {
                    String text = parser.toJson(acqMessage);
                    TextMessage textMessage = session.createTextMessage(text);
                    textMessage.setStringProperty(COUNTRY_CODE_PROPERTY_NAME, acqMessage.getCountryCode());
                    return textMessage;
                }
            });
        }
    }

    private boolean allowSendingMessage(final UserNotifyMessage acqMessage){

        if (acqMessage == null) {
            logger.error("the message cannot be sent because it is null");
            return false;
        }
        if (acqMessage.getCountryCode() == null
                || acqMessage.getCountryCode().trim().equals("")
                || acqMessage.getCountryCode().trim().equalsIgnoreCase("null")) {
            logger.error("the message cannot be sent because country code is undefined");
            return false;
        }
        if (acqMessage.getPhone() == null
                || acqMessage.getPhone().trim().equals("")
                || acqMessage.getPhone().trim().equalsIgnoreCase("null")) {
            logger.error("the message cannot be sent because user phone number is undefined");
            return false;
        }
        if (acqMessage.getText() == null
                || acqMessage.getText().trim().equals("")
                || acqMessage.getText().trim().equalsIgnoreCase("null")) {
            logger.error("the message cannot be sent because notification content text is undefined");
            return false;
        }
        return true;
    }

    public boolean isSmsNotifyEnabled() {
        return smsNotifyEnabled;
    }

    public void setSmsNotifyEnabled(boolean smsNotifyEnabled) {
        this.smsNotifyEnabled = smsNotifyEnabled;
    }

    private void debug(String text) {
        if (logger.isDebugEnabled()) {
            logger.debug(text);
        }
    }

}
