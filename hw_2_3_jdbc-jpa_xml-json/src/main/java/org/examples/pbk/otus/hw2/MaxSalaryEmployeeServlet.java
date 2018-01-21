package org.examples.pbk.otus.hw2;

import org.examples.pbk.otus.dao.PreparedStatements;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/max_salary")
public class MaxSalaryEmployeeServlet extends HttpServlet {

    @Inject
    PreparedStatements preparedStatements;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long id = preparedStatements.getMaxSalaryEmployeeId();
        resp.sendRedirect(getServletContext().getContextPath() + "/employee?id=" + id);
    }
}
