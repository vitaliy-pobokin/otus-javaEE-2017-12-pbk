package org.examples.pbk.otus.hw3;

import org.examples.pbk.otus.dao.EmployeeDao;
import org.examples.pbk.otus.dao.qualifiers.Jdbc;
import org.examples.pbk.otus.json.JsonDateAdapter;
import org.examples.pbk.otus.json.JsonEmployeesAdapter;
import org.examples.pbk.otus.model.Employee;
import org.examples.pbk.otus.model.EmployeeWrapper;

import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

@WebServlet("/json")
@MultipartConfig
public class JsonDeserializerServlet extends HttpServlet {

    @Inject @Jdbc
    EmployeeDao dao;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("path", req.getContextPath() + req.getServletPath());
        req.getRequestDispatcher("upload.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Part filePart = req.getPart("file");
        InputStream fileContent = filePart.getInputStream();
        JsonbConfig config = new JsonbConfig()
                .withAdapters(new JsonDateAdapter(), new JsonEmployeesAdapter());
        Jsonb jsonb = JsonbBuilder.create(config);
        EmployeeWrapper employees = jsonb.fromJson(fileContent, EmployeeWrapper.class);
        PrintWriter writer = resp.getWriter();
        for (Employee employee : employees.getEmployees()) {
            if (employee.getId()%2 != 0) {
                writer.println(employee);
            }
        }
    }
}
