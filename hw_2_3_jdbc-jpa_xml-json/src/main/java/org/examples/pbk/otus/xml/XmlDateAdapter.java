package org.examples.pbk.otus.xml;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class XmlDateAdapter extends XmlAdapter<String, Date> {

    private final DateFormat dateFormat = new SimpleDateFormat("dd-mm-yyyy");

    @Override
    public Date unmarshal(String v) throws Exception {
        synchronized (dateFormat) {
            return new Date(dateFormat.parse(v).getTime());
        }
    }

    @Override
    public String marshal(Date v) throws Exception {
        synchronized (dateFormat) {
            return dateFormat.format(v);
        }
    }
}
