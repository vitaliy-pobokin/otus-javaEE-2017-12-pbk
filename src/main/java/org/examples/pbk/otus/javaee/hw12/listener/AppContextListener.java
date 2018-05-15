package org.examples.pbk.otus.javaee.hw12.listener;

import org.examples.pbk.otus.javaee.hw12.CacheManagerProvider;
import org.examples.pbk.otus.javaee.hw12.DatabaseStateManager;
import org.examples.pbk.otus.javaee.hw12.statistic.StatisticBean;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class AppContextListener implements ServletContextListener {

    @Inject
    private StatisticBean bean;

    @Inject
    private DatabaseStateManager stateManager;

    @Override
    public void contextInitialized(ServletContextEvent event) {
        CacheManagerProvider.init();
        ServletContext sc = event.getServletContext();
        sc.setAttribute("ctx", sc.getContextPath());
        stateManager.loadDatabaseState(sc);
        initializeStatisticTables();
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        ServletContext sc = event.getServletContext();
        stateManager.saveDatabaseState(sc);
        CacheManagerProvider.dispose();
    }

    private void initializeStatisticTables() {
        bean.dropTable();
        bean.dropSequence();
        bean.createTable();
        bean.createSequence();
        bean.createProcedures();
    }
}
