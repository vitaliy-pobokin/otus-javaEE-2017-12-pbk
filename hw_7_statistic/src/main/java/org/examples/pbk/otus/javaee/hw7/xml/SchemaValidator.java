package org.examples.pbk.otus.javaee.hw7.xml;

import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.stream.Collectors;

public class SchemaValidator {

    private Validator validator;

    public SchemaValidator(String... xsdFilePaths) throws SAXException {
        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        StreamSource[] xsdSources =
                Arrays.stream(xsdFilePaths)
                        .map(StreamSource::new)
                        .collect(Collectors.toList())
                        .toArray(new StreamSource[xsdFilePaths.length]);
        Schema schema = schemaFactory.newSchema(xsdSources);
        this.validator = schema.newValidator();
    }

    public void validate(InputStream inputStream) throws SAXException, IOException{
        synchronized (validator) {
            validator.validate(new StreamSource(inputStream));
        }
    }
}
