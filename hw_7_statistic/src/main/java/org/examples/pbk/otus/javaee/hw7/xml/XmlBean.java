package org.examples.pbk.otus.javaee.hw7.xml;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.enterprise.context.RequestScoped;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@RequestScoped
public class XmlBean {

    private XmlBean() {}

    public static Object unmarshal(File source, Class type) {
        try {
            JAXBContext context = JAXBContext.newInstance(type);
            return context.createUnmarshaller().unmarshal(source);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Object unmarshal(InputStream source, Class type) {
        try {
            JAXBContext context = JAXBContext.newInstance(type);
            return context.createUnmarshaller().unmarshal(source);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void marshall(Object source, OutputStream out) {
        try {
            JAXBContext context = JAXBContext.newInstance(source.getClass());
            context.createMarshaller().marshal(source, out);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public static void marshall(Object source, File out) {
        try {
            JAXBContext context = JAXBContext.newInstance(source.getClass());
            context.createMarshaller().marshal(source, out);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public static Document getDomRepresentation(InputStream source) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            return builder.parse(source);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
