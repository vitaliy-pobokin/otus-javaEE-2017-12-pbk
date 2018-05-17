package org.examples.pbk.otus.javaee.hw12;

import org.examples.pbk.otus.javaee.hw12.model.*;
import org.examples.pbk.otus.javaee.hw12.service.JpaAccountService;
import org.examples.pbk.otus.javaee.hw12.service.JpaDepartmentService;
import org.examples.pbk.otus.javaee.hw12.service.JpaEmployeeService;
import org.examples.pbk.otus.javaee.hw12.xml.XmlBean;
import org.hibernate.ReplicationMode;
import org.hibernate.Session;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletContext;
import javax.transaction.Transactional;
import java.io.File;
import java.util.List;

@Stateless
public class DatabaseStateManager {

    public static final String ACCOUNTS_XML_PATH = "/WEB-INF/classes/database_state/accounts.xml";
    public static final String DEPARTMENTS_XML_PATH = "/WEB-INF/classes/database_state/departments.xml";
    public static final String EMPLOYEES_XML_PATH = "/WEB-INF/classes/database_state/employees.xml";

    @PersistenceContext(unitName = "persistence")
    private EntityManager em;

    public void loadDatabaseState(ServletContext servletContext) {
//        loadAccountsXml(servletContext.getRealPath(ACCOUNTS_XML_PATH));
        loadDepartmentsXml(servletContext.getRealPath(DEPARTMENTS_XML_PATH));
        loadEmployeesXml(servletContext.getRealPath(EMPLOYEES_XML_PATH));
    }

    public void saveDatabaseState(ServletContext servletContext) {
        saveAccountsToXml(servletContext.getRealPath(ACCOUNTS_XML_PATH));
        saveDepartmentsToXml(servletContext.getRealPath(DEPARTMENTS_XML_PATH));
        saveEmployeesToXml(servletContext.getRealPath(EMPLOYEES_XML_PATH));
    }

    private void loadAccountsXml(String xmlFilePath) {
        AccountWrapper accounts = (AccountWrapper) XmlBean.unmarshal(new File(xmlFilePath), AccountWrapper.class);
        accounts.getAccounts().forEach(account -> em.unwrap(Session.class).replicate(account, ReplicationMode.EXCEPTION));
    }

    private void loadDepartmentsXml(String xmlFilePath) {
        DepartmentWrapper departments = (DepartmentWrapper) XmlBean.unmarshal(new File(xmlFilePath), DepartmentWrapper.class);
        departments.getDepartments().forEach(department -> em.unwrap(Session.class).replicate(department, ReplicationMode.EXCEPTION));
    }

    private void loadEmployeesXml(String xmlFilePath) {
        EmployeeWrapper employees = (EmployeeWrapper) XmlBean.unmarshal(new File(xmlFilePath), EmployeeWrapper.class);
        employees.getEmployees().forEach(employee -> em.unwrap(Session.class).replicate(employee, ReplicationMode.EXCEPTION));
    }

    private void saveAccountsToXml(String xmlFilePath) {
        List<Account> accounts = new JpaAccountService().findAll();
        AccountWrapper wrapper = new AccountWrapper();
        wrapper.setAccounts(accounts);
        XmlBean.marshall(wrapper, new File(xmlFilePath));
    }

    private void saveDepartmentsToXml(String xmlFilePath) {
        List<Department> departments = new JpaDepartmentService().findAll();
        DepartmentWrapper wrapper = new DepartmentWrapper();
        wrapper.setDepartments(departments);
        XmlBean.marshall(wrapper, new File(xmlFilePath));
    }

    private void saveEmployeesToXml(String xmlFilePath) {
        List<Employee> employees = new JpaEmployeeService().findAll();
        EmployeeWrapper wrapper = new EmployeeWrapper();
        wrapper.setEmployees(employees);
        XmlBean.marshall(wrapper, new File(xmlFilePath));
    }
}
