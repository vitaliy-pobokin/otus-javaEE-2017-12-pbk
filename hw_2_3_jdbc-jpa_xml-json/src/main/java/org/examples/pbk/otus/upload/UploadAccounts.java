package org.examples.pbk.otus.upload;

import org.examples.pbk.otus.dao.AccountDao;
import org.examples.pbk.otus.dao.qualifiers.Jdbc;
import org.examples.pbk.otus.xml.SchemaValidator;
import org.examples.pbk.otus.xml.XmlBean;
import org.examples.pbk.otus.model.AccountWrapper;
import org.xml.sax.SAXException;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;

@WebServlet("/upload_accounts")
@MultipartConfig
public class UploadAccounts extends HttpServlet {

    @Inject @Jdbc
    AccountDao dao;

    @Inject
    XmlBean xmlBean;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("path", req.getContextPath() + req.getServletPath());
        req.getRequestDispatcher("upload.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Part filePart = req.getPart("file");
        InputStream fileContent = filePart.getInputStream();

        try {
            new SchemaValidator(getServletContext().getRealPath("/") + "xsd/accounts_schema.xsd").validate(fileContent);
        } catch (SAXException e) {
            req.setAttribute("message", e.toString());
            req.getRequestDispatcher("error.jsp").forward(req, resp);
        }

        fileContent = filePart.getInputStream();
        AccountWrapper accounts = (AccountWrapper) xmlBean.unmarshal(fileContent, AccountWrapper.class);
        accounts.getAccounts().forEach(dao::create);
        req.setAttribute("accounts", dao.findAll());
        req.getRequestDispatcher("accounts.jsp").forward(req, resp);
    }
}
