package org.examples.pbk.otus.hw3;

import org.examples.pbk.otus.xml.XmlBean;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@WebServlet("/above_average")
@MultipartConfig
public class AboveAverageServlet extends HttpServlet {

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
        Document document = xmlBean.getDomRepresentation(fileContent);

        double average = getAverageSalary(document);
        removeEmployeeWithSalaryBelowAverage(document, average);

        getXmlFromDom(document, resp.getOutputStream());
    }

    private double getAverageSalary(Document document) {
        NodeList salaries = getNodeListByXPath(document, "/employees/employee/salary");
        int total = 0;
        for (int i = 0; i < salaries.getLength(); i++) {
            Element salary = (Element) salaries.item(i);
            total += Integer.valueOf(salary.getTextContent());
        }
        return (double) (total/salaries.getLength());
    }

    private void removeEmployeeWithSalaryBelowAverage(Document document, double average) {
        NodeList employees = getNodeListByXPath(document, "/employees/employee");

        for (int i = 0; i < employees.getLength(); i++) {
            Element employee = (Element) employees.item(i);
            Element salary = (Element) employee.getElementsByTagName("salary").item(0);
            int salaryValue = Integer.valueOf(salary.getTextContent());
            if (salaryValue < average) {
                employees.item(i).getParentNode().removeChild(employees.item(i));
            }
        }
    }

    private NodeList getNodeListByXPath(Document document, String xPath) {
        XPathFactory xpathFactory = XPathFactory.newInstance();
        XPath xpath = xpathFactory.newXPath();
        try {
            XPathExpression expr = xpath.compile(xPath);
            return (NodeList) expr.evaluate(document, XPathConstants.NODESET);
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void getXmlFromDom(Document document, OutputStream out) {
        TransformerFactory tFactory = TransformerFactory.newInstance();
        try {
            Transformer transformer = tFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(out);
            transformer.transform(source, result);
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }
}
