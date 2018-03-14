package org.examples.pbk.otus.javaee.hw6;

import org.examples.pbk.otus.javaee.hw6.model.AccountWrapper;
import org.examples.pbk.otus.javaee.hw6.model.DepartmentWrapper;
import org.examples.pbk.otus.javaee.hw6.model.EmployeeWrapper;
import org.examples.pbk.otus.javaee.hw6.resources.TransactionUtils;
import org.examples.pbk.otus.javaee.hw6.xml.XmlBean;
import org.hibernate.ReplicationMode;

import javax.servlet.ServletContext;
import java.io.File;

public class DatabaseStateManager {

    public static final String ACCOUNTS_XML_PATH = "/WEB-INF/classes/database_state/accounts.xml";
    public static final String DEPARTMENTS_XML_PATH = "/WEB-INF/classes/database_state/departments.xml";
    public static final String EMPLOYEES_XML_PATH = "/WEB-INF/classes/database_state/employees.xml";

    public void loadDatabaseState(ServletContext servletContext) {
        loadAccountsXml(servletContext.getRealPath(ACCOUNTS_XML_PATH));
        loadDepartmentsXml(servletContext.getRealPath(DEPARTMENTS_XML_PATH));
        loadEmployeesXml(servletContext.getRealPath(EMPLOYEES_XML_PATH));
    }

    private void loadAccountsXml(String xmlFilePath) {
        AccountWrapper accounts = (AccountWrapper) XmlBean.unmarshal(new File(xmlFilePath), AccountWrapper.class);
        TransactionUtils.runInTransactionWithoutResult(session -> {
            accounts.getAccounts().forEach(account -> session.replicate(account, ReplicationMode.EXCEPTION));
        });
    }

    private void loadDepartmentsXml(String xmlFilePath) {
        DepartmentWrapper departments = (DepartmentWrapper) XmlBean.unmarshal(new File(xmlFilePath), DepartmentWrapper.class);
        TransactionUtils.runInTransactionWithoutResult(session -> {
            departments.getDepartments().forEach(department -> session.replicate(department, ReplicationMode.EXCEPTION));
        });
    }

    private void loadEmployeesXml(String xmlFilePath) {
        EmployeeWrapper employees = (EmployeeWrapper) XmlBean.unmarshal(new File(xmlFilePath), EmployeeWrapper.class);
        TransactionUtils.runInTransactionWithoutResult(session -> {
            employees.getEmployees().forEach(employee -> session.replicate(employee, ReplicationMode.EXCEPTION));
        });
    }
}
