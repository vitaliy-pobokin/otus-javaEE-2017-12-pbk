package org.examples.pbk.otus.javaee.hw7.xml;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class XmlDateAdapter extends XmlAdapter<String, Date> {

    private final DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

    @Override
    public Date unmarshal(String v) throws Exception {
        synchronized (dateFormat) {
            return dateFormat.parse(v);
        }
    }

    @Override
    public String marshal(Date v) throws Exception {
        synchronized (dateFormat) {
            return dateFormat.format(v);
        }
    }
}
