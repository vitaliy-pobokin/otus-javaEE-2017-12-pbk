package org.examples.pbk.otus.javaee.hw12.cdi.events;

import org.examples.pbk.otus.javaee.hw12.cdi.events.qualifiers.Successful;
import org.examples.pbk.otus.javaee.hw12.cdi.events.qualifiers.Unsuccessful;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import java.util.logging.Level;
import java.util.logging.Logger;

@ApplicationScoped
public class LoginEventHandler {
    private static final Logger logger = Logger.getLogger(LoginEventHandler.class.getName());

    public void unsuccessfulLogin(@Observes @Unsuccessful LoginEvent event) {
        logger.log(Level.INFO, "LoginEventHandler - Unsuccessful: {0}", event.toString());
    }

    public void successfulLogin(@Observes @Successful LoginEvent event) {
        logger.log(Level.INFO, "LoginEventHandler - Successful: {0}", event.toString());
    }
}
