package org.examples.pbk.otus.javaee.hw6.listener;

import org.examples.pbk.otus.javaee.hw6.DatabaseStateManager;
import org.hibernate.SessionFactory;

import javax.persistence.Persistence;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

//TODO: xsd schema validation
@WebListener
public class AppContextListener implements ServletContextListener {

    private static final String PERSISTENCE_UNIT_NAME = "persistence";

    private static SessionFactory sessionFactory;

    @Override
    public void contextInitialized(ServletContextEvent event) {
        sessionFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME).unwrap(SessionFactory.class);
        ServletContext sc = event.getServletContext();
        sc.setAttribute("ctx", sc.getContextPath());
        new DatabaseStateManager().loadDatabaseState(sc);
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {}

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
