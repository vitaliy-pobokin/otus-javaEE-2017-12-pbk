package org.examples.pbk.otus.javaee.hw9.listener;

import org.examples.pbk.otus.javaee.hw9.CacheManagerProvider;
import org.examples.pbk.otus.javaee.hw9.DatabaseStateManager;
import org.examples.pbk.otus.javaee.hw9.SessionFactoryProvider;
import org.examples.pbk.otus.javaee.hw9.resources.TransactionUtils;
import org.examples.pbk.otus.javaee.hw9.statistic.StatisticBean;

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
        initializeStatisticTables();
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        ServletContext sc = event.getServletContext();
        new DatabaseStateManager().saveDatabaseState(sc);
        CacheManagerProvider.dispose();
        SessionFactoryProvider.dispose();
    }

    private void initializeStatisticTables() {
        StatisticBean bean = new StatisticBean();
        TransactionUtils.runInTransactionWithoutResult(session -> {
            bean.setSession(session);
            bean.dropTable();
            bean.dropSequence();
            bean.createTable();
            bean.createSequence();
            bean.createProcedures();
        });
    }
}
