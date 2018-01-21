package org.examples.pbk.otus.hw2;

import org.examples.pbk.otus.dao.EmployeeDao;
import org.examples.pbk.otus.dao.qualifiers.InMemory;
import org.examples.pbk.otus.dao.qualifiers.Jdbc;
import org.examples.pbk.otus.model.Employee;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet ("/employee")
public class EmployeeServlet extends HttpServlet {

    @Inject @Jdbc
    EmployeeDao dao;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String empId = req.getParameter("id");
        if (empId == null) {
            List<Employee> employees = dao.findAll();
            if (employees == null || employees.isEmpty()) {
                resp.sendRedirect(getServletContext().getContextPath() + "/upload_employees");
            } else {
                req.setAttribute("employees", employees);
                req.getRequestDispatcher("employees.jsp").forward(req, resp);
            }
        } else {
            long id = -1;
            try {
                id = Long.parseLong(empId);
            } catch (NumberFormatException e) {
                req.setAttribute("message", "Wrong id parameter");
                req.getRequestDispatcher("error.jsp").forward(req, resp);
            }
            Employee employee = dao.findById(id);
            if (employee == null) {
                req.setAttribute("message", "Employee not found");
                req.getRequestDispatcher("error.jsp").forward(req, resp);
            }
            req.setAttribute("employee", employee);
            req.getRequestDispatcher("employee.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String empId = req.getParameter("id");
        if (empId == null) {
            resp.sendRedirect(getServletContext().getContextPath() + "/employee");
        }
        long id = -1;
        try {
            id = Long.parseLong(empId);
        } catch (NumberFormatException e) {
            req.setAttribute("message", "Wrong id parameter");
            req.getRequestDispatcher("error.jsp").forward(req, resp);
        }
        Employee employee = dao.findById(id);
        if (employee == null) {
            req.setAttribute("message", "Employee not found");
            req.getRequestDispatcher("error.jsp").forward(req, resp);
        }
        if (req.getParameter("delete") != null) {
            dao.delete(id);
        } else {
            Map<String,String[]> parameters = req.getParameterMap();
            updateEmployeeFields(employee, parameters);
            dao.update(employee);
        }
        resp.sendRedirect(getServletContext().getContextPath() + "/employee");
    }

    private void updateEmployeeFields(Employee employee, Map<String, String[]> requestParameters) {
        employee.setName(requestParameters.get("name")[0]);
        employee.setEmail(requestParameters.get("email")[0]);
        employee.setPhone(requestParameters.get("phone")[0]);
        employee.setJob(requestParameters.get("job")[0]);
        employee.setSalary(Integer.parseInt(requestParameters.get("salary")[0]));
    }
}
