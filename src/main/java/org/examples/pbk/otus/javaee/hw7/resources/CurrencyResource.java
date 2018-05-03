package org.examples.pbk.otus.javaee.hw7.resources;

import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.examples.pbk.otus.javaee.hw7.model.Currencies;

import javax.annotation.security.PermitAll;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URL;

@Path("currency")
@Produces(MediaType.APPLICATION_JSON)
public class CurrencyResource {

    private static final String CURRENCIES_XML_URL = "http://www.cbr.ru/scripts/XML_daily.asp";

    @GET
    @PermitAll
    public Response getAll() throws Exception {
        JacksonXmlModule module = new JacksonXmlModule();
        module.setDefaultUseWrapper(false);
        XmlMapper xmlMapper = new XmlMapper(module);
        URL url = new URL(CURRENCIES_XML_URL);
        Currencies currencies = xmlMapper.readValue(url, Currencies.class);
        return Response.ok(currencies).build();
    }
}
