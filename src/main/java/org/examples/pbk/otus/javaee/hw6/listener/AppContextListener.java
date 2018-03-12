package org.examples.pbk.otus.javaee.hw6.listener;

import org.examples.pbk.otus.javaee.hw6.dao.JpaAccountDao;
import org.examples.pbk.otus.javaee.hw6.dao.JpaDepartmentDao;
import org.examples.pbk.otus.javaee.hw6.dao.JpaEmployeeDao;
import org.examples.pbk.otus.javaee.hw6.model.AccountWrapper;
import org.examples.pbk.otus.javaee.hw6.model.DepartmentWrapper;
import org.examples.pbk.otus.javaee.hw6.model.EmployeeWrapper;
import org.examples.pbk.otus.javaee.hw6.resources.TransactionUtils;
import org.examples.pbk.otus.javaee.hw6.xml.XmlBean;
import org.hibernate.SessionFactory;

import javax.persistence.Persistence;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.File;

//TODO: xsd schema validation
@WebListener
public class AppContextListener implements ServletContextListener {

    private static final String PERSISTENCE_UNIT_NAME = "persistence";
    public static final String ACCOUNTS_XML_PATH = "/WEB-INF/classes/database_state/accounts.xml";
    public static final String DEPARTMENTS_XML_PATH = "/WEB-INF/classes/database_state/departments.xml";
    public static final String EMPLOYEES_XML_PATH = "/WEB-INF/classes/database_state/employees.xml";

    private static SessionFactory sessionFactory;

    @Override
    public void contextInitialized(ServletContextEvent event) {
        sessionFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME).unwrap(SessionFactory.class);
        ServletContext sc = event.getServletContext();
        sc.setAttribute("ctx", sc.getContextPath());
        loadDatabaseState(sc);
    }

    private void loadDatabaseState(ServletContext servletContext) {
        loadAccountsXml(servletContext.getRealPath(ACCOUNTS_XML_PATH));
        loadDepartmentsXml(servletContext.getRealPath(DEPARTMENTS_XML_PATH));
        loadEmployeesXml(servletContext.getRealPath(EMPLOYEES_XML_PATH));
    }

    private void loadAccountsXml(String xmlFilePath) {
        AccountWrapper accounts = (AccountWrapper) XmlBean.unmarshal(new File(xmlFilePath), AccountWrapper.class);
        JpaAccountDao dao = new JpaAccountDao();
        TransactionUtils.runInTransactionWithoutResult(entityManager -> {
            dao.setEntityManager(entityManager);
            accounts.getAccounts().forEach(dao::create);
        });
    }

    private void loadDepartmentsXml(String xmlFilePath) {
        DepartmentWrapper departments = (DepartmentWrapper) XmlBean.unmarshal(new File(xmlFilePath), DepartmentWrapper.class);
        JpaDepartmentDao dao = new JpaDepartmentDao();
        TransactionUtils.runInTransactionWithoutResult(entityManager -> {
            dao.setEntityManager(entityManager);
            departments.getDepartments().forEach(dao::create);
        });
    }

    private void loadEmployeesXml(String xmlFilePath) {
        EmployeeWrapper employees = (EmployeeWrapper) XmlBean.unmarshal(new File(xmlFilePath), EmployeeWrapper.class);
        JpaEmployeeDao dao = new JpaEmployeeDao();
        TransactionUtils.runInTransactionWithoutResult(entityManager -> {
            dao.setEntityManager(entityManager);
            employees.getEmployees().forEach(dao::create);
        });
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {}

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
