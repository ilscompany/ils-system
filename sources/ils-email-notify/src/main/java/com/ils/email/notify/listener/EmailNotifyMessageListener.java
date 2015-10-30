package com.ils.email.notify.listener;

import org.springframework.stereotype.Service;

import javax.jms.Message;
import javax.jms.MessageListener;

/**
 * Created by mara on 10/24/15.
 */
@Service("emailNotifyMessageListener")
public class EmailNotifyMessageListener implements MessageListener {

    public void onMessage(Message message) {

    }
}
