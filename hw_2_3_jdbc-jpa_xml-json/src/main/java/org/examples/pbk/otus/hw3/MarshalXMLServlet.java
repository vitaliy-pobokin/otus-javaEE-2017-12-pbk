package org.examples.pbk.otus.hw3;

import org.examples.pbk.otus.dao.AccountDao;
import org.examples.pbk.otus.dao.DepartmentDao;
import org.examples.pbk.otus.dao.EmployeeDao;
import org.examples.pbk.otus.dao.qualifiers.Jdbc;
import org.examples.pbk.otus.model.AccountWrapper;
import org.examples.pbk.otus.model.DepartmentWrapper;
import org.examples.pbk.otus.xml.XmlBean;
import org.examples.pbk.otus.model.EmployeeWrapper;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet (urlPatterns = {"/marshal_employees", "/marshal_accounts", "/marshal_departments"})
public class MarshalXMLServlet extends HttpServlet {

    @Inject @Jdbc
    EmployeeDao employeeDao;

    @Inject @Jdbc
    AccountDao accountDao;

    @Inject @Jdbc
    DepartmentDao departmentDao;

    @Inject
    XmlBean xmlBean;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String urlPattern = req.getServletPath();
        switch (urlPattern) {
            case "/marshal_employees":
                EmployeeWrapper employeeWrapper = new EmployeeWrapper();
                employeeWrapper.setEmployees(employeeDao.findAll());
                xmlBean.marshall(employeeWrapper, resp.getOutputStream());
                break;
            case "/marshal_accounts":
                AccountWrapper accountWrapper = new AccountWrapper();
                accountWrapper.setAccounts(accountDao.findAll());
                xmlBean.marshall(accountWrapper, resp.getOutputStream());
                break;
            case "/marshal_departments":
                DepartmentWrapper departmentWrapper = new DepartmentWrapper();
                departmentWrapper.setDepartments(departmentDao.findAll());
                xmlBean.marshall(departmentWrapper, resp.getOutputStream());
        }
    }
}
