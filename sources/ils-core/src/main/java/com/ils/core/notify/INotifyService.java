package com.ils.core.notify;

import com.ils.core.notify.impl.UserNotifyMessage;

/**
 * Ce service permet de declencher l'envoi de messages
 * de notification aux clients. Ces messages de notification
 * sont envoyes soit par email, par SMS ou les deux en fonction
 * de la configuration de l'application.
 *
 * Created by mara on 10/25/15.
 */
public interface INotifyService {

    void notifyUser(UserNotifyMessage userNotifyMessage);

}
