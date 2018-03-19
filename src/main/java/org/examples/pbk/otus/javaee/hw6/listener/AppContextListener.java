package org.examples.pbk.otus.javaee.hw6.listener;

import org.examples.pbk.otus.javaee.hw6.CacheManagerProvider;
import org.examples.pbk.otus.javaee.hw6.DatabaseStateManager;
import org.examples.pbk.otus.javaee.hw6.SessionFactoryProvider;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class AppContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {
        SessionFactoryProvider.init();
        CacheManagerProvider.init();
        ServletContext sc = event.getServletContext();
        sc.setAttribute("ctx", sc.getContextPath());
        new DatabaseStateManager().loadDatabaseState(sc);
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        ServletContext sc = event.getServletContext();
        new DatabaseStateManager().saveDatabaseState(sc);
        CacheManagerProvider.dispose();
        SessionFactoryProvider.dispose();
    }
}
