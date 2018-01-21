package org.examples.pbk.otus.json;

import javax.json.bind.adapter.JsonbAdapter;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class JsonDateAdapter implements JsonbAdapter<Date, String> {

    private final DateFormat dateFormat = new SimpleDateFormat("dd-mm-yyyy");

    @Override
    public String adaptToJson(Date date) throws Exception {
        synchronized (dateFormat) {
            return dateFormat.format(date);
        }
    }

    @Override
    public Date adaptFromJson(String s) throws Exception {
        synchronized (dateFormat) {
            return new Date(dateFormat.parse(s).getTime());
        }
    }
}
