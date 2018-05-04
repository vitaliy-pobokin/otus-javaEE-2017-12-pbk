package org.examples.pbk.otus.javaee.hw9;

import javax.json.Json;
import javax.json.stream.JsonGenerator;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@Path("/credit_calc")
public class CreditCalcResource {
    private static final Logger logger = Logger.getLogger(CreditCalcResource.class.getName());

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces("application/credit_payment-v1+json")
    public Response calculateDiffCreditPayments(
            @FormParam("sum") int sum,
            @FormParam("percent") double percent,
            @FormParam("period") int period
    ) {

        StreamingOutput stream = new StreamingOutput() {
            @Override
            public void write(OutputStream os) throws IOException,
                    WebApplicationException {
                Writer writer = new BufferedWriter(new OutputStreamWriter(os));
                JsonGenerator jsonGenerator = Json.createGenerator(writer);
                jsonGenerator.writeStartArray();
                for (int i = 1; i <= period; i++) {
                    double payment = sum/period + sum*(period - i + 1)*percent/100/12/period;
                    jsonGenerator.writeStartObject();
                    jsonGenerator.write("month", i);
                    jsonGenerator.write("payment", payment);
                    jsonGenerator.writeEnd();
                }
                jsonGenerator.writeEnd().flush();
            }
        };
        logger.log(Level.INFO, "v1");
        return Response.ok(stream).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces("application/credit_payment-v2+json")
    public Response calculateAnnuitantCreditPayments(
            @FormParam("sum") int sum,
            @FormParam("percent") double percent,
            @FormParam("period") int period
    ) {

        StreamingOutput stream = new StreamingOutput() {
            @Override
            public void write(OutputStream os) throws IOException,
                    WebApplicationException {
                Writer writer = new BufferedWriter(new OutputStreamWriter(os));
                JsonGenerator jsonGenerator = Json.createGenerator(writer);
                jsonGenerator.writeStartArray();
                for (int i = 1; i <= period; i++) {
                    double payment = sum*percent/12/100/(1 - 1/Math.pow((1 + percent/12/100), period));
                    jsonGenerator.writeStartObject();
                    jsonGenerator.write("month", i);
                    jsonGenerator.write("payment", payment);
                    jsonGenerator.writeEnd();
                }
                jsonGenerator.writeEnd().flush();
            }
        };
        logger.log(Level.INFO, "v2");
        return Response.ok(stream).build();
    }
}
