package org.examples.pbk.otus.hw3;

import org.json.JSONObject;
import org.json.XML;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;
import java.nio.file.Paths;

@WebServlet("/xml_to_json")
@MultipartConfig
public class XmlToJsonConverterServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("path", req.getContextPath() + req.getServletPath());
        req.getRequestDispatcher("upload.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Part filePart = req.getPart("file");
        InputStream fileContent = filePart.getInputStream();
        String xmlString = convertToString(fileContent, "UTF-8");
        JSONObject jsonRepresentation = XML.toJSONObject(xmlString);
        int prettyPrintIdentFactor = 4;
        String jsonString = jsonRepresentation.toString(prettyPrintIdentFactor);
        String fileNameWithExt = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
        String fileNameWithoutExt = fileNameWithExt.substring(0, fileNameWithExt.lastIndexOf('.'));
        FileWriter fileWriter = new FileWriter(getServletContext().getRealPath("/") + fileNameWithoutExt + ".json");
        fileWriter.write(jsonString);
        fileWriter.close();
        resp.getOutputStream().print(jsonString);
    }

    private String convertToString(InputStream is, String charsetName) {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, length);
            }
            return byteArrayOutputStream.toString(charsetName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
