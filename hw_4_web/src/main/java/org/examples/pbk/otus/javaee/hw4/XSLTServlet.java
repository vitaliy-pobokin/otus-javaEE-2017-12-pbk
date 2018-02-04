package org.examples.pbk.otus.javaee.hw4;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

@WebServlet(urlPatterns = {"", "/news", "/about", "/employees", "/login"})
public class XSLTServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        StreamSource styleSource = new StreamSource(getServletContext().getResourceAsStream("/xslt/currencies.xsl"));
        String url = "http://www.cbr.ru/scripts/XML_daily.asp";
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        StreamSource xmlSource = new StreamSource(con.getInputStream());
        String servletPath = req.getServletPath();
        String pathName = getServletContext().getRealPath("/") + (servletPath.equals("") ? "index.html" : servletPath + ".html");
        Document document = Jsoup.parse(new File(pathName), "UTF-8");
        resp.setContentType("text/html;charset=UTF-8");
        try(PrintWriter pw = resp.getWriter()) {
            Transformer transformer = TransformerFactory.newInstance().newTransformer(styleSource);
            StringWriter stringWriter = new StringWriter();
            transformer.transform(xmlSource, new StreamResult(stringWriter));
            document.getElementById("currencies").html(stringWriter.toString());
            pw.write(document.toString());
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }
}
