package com.ils.commons.config;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.stereotype.Service;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Properties;

/**
 * Cette class est utilisee par tous les modules ILS
 * pour avoir acces a la configuration de l'application.
 * les parametres de l'application sont chargees lors du
 * demarrage du serveur d'application et ces parametres
 * sont rendus accessibles via JNDI
 *
 * Created by mara on 10/24/15.
 */
@Service("config")
public class JndiPropertyConfigurer extends PropertyPlaceholderConfigurer{

    private static final Logger logger = Logger.getLogger(JndiPropertyConfigurer.class);

    private static final String jndiPrefix = "properties/";

    private Context context;

    @Override
    protected String resolvePlaceholder(String placeholder, Properties props) {
        String value = resolveJndiPlaceholder(placeholder);
        if (value == null) {
            value = super.resolvePlaceholder(placeholder, props);
        }
        return value;
    }

    public String getProperty(String name){
        return resolveJndiPlaceholder(name);
    }

    private String resolveJndiPlaceholder(String placeholder) {

        String value = null;
        try {
            Object valueObject = getContext().lookup(jndiPrefix + placeholder);
            if (valueObject != null) {
                value =  String.valueOf(valueObject);
            }
        } catch (NamingException e) {
            logger.warn("an error occurred while looking up the value of the JNDI property : " + placeholder, e);
        }
        return value;
    }

    private Context getContext(){
        try {
            if (context == null) {
                context = new InitialContext();
            }
            return context;
        } catch (NamingException e) {
            String errorMsg = "an error occurred while loading JNDI initial context";
            logger.error(errorMsg, e);
            throw new RuntimeException(errorMsg, e);
        }
    }
}
