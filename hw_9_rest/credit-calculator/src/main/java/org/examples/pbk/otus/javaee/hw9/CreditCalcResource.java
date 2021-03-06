package org.examples.pbk.otus.javaee.hw9;

import io.swagger.annotations.*;

import javax.json.Json;
import javax.json.stream.JsonGenerator;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

@SwaggerDefinition(
        info = @Info(
                title = "Loan Calculator Swagger-generated API",
                description = "User Resource Description example",
                version = "1.0.0",
                termsOfService = "share and care",
                contact = @Contact(
                        name = "Vitaliy", email = "pbk.vitaliy@gmail.com",
                        url = "https://example.com"),
                license = @License(
                        name = "Apache 2.0",
                        url = "http://www.apache.org")),
        tags = {
                @Tag(name = "Loan Calculator Swagger-generated API",
                        description = "Description Example")
        },
        host = "localhost:3000",
        basePath = "/api",
        schemes = {SwaggerDefinition.Scheme.HTTP}
)
@Api(tags = "Loan Calculator Swagger-generated API", consumes = MediaType.APPLICATION_FORM_URLENCODED)
@Path("/credit_calc")
public class CreditCalcResource {
    private static final Logger logger = Logger.getLogger(CreditCalcResource.class.getName());

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces("application/credit_payment-v1+json; charset=UTF-8")
    @ApiOperation(value = "Calculate Differential Credit Payments",
        produces = "application/credit_payment-v1+json; charset=UTF-8")
    @ApiResponses({
            @ApiResponse(code = 200, message = "List of Credit Payments")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sum", value = "Loan Amount", dataTypeClass = BigDecimal.class, required = true, paramType = "form"),
            @ApiImplicitParam(name = "percent", value = "Interest Rate per Year", dataTypeClass = double.class, required = true, paramType = "form"),
            @ApiImplicitParam(name = "period", value = "Loan Term in Months", dataTypeClass = int.class, required = true, paramType = "form"),
            @ApiImplicitParam(name = "start_date", value = "Loan Start Date", dataTypeClass = String.class, required = true, paramType = "form")
    })
    public Response calculateDiffCreditPayments(
            @FormParam("sum") BigDecimal sum,
            @FormParam("percent") double percent,
            @FormParam("period") int period,
            @FormParam("start_date") String startDate
    ) {
        LocalDate date = LocalDate.parse(startDate);
        StreamingOutput stream = new StreamingOutput() {
            @Override
            public void write(OutputStream os) throws IOException, WebApplicationException {
                Writer writer = new BufferedWriter(new OutputStreamWriter(os));
                JsonGenerator jsonGenerator = Json.createGenerator(writer);
                jsonGenerator.writeStartArray();
                BigDecimal debtBalance = sum;
                BigDecimal debtPayment = sum.divide(BigDecimal.valueOf(period), 2, BigDecimal.ROUND_HALF_EVEN);
                for (int i = 1; i <= period; i++) {
                    LocalDate paymentDate = date.plusMonths(i);
                    BigDecimal accrualPayment = calculateAccrualPayment(date.plusMonths(i - 1), paymentDate, debtBalance, percent).setScale(2, BigDecimal.ROUND_HALF_EVEN);
                    BigDecimal payment = debtPayment.add(accrualPayment).setScale(2, BigDecimal.ROUND_HALF_EVEN);
                    if (i == period && debtPayment.compareTo(debtBalance) > 0) {
                        debtPayment = debtBalance.setScale(2, BigDecimal.ROUND_HALF_EVEN);
                        payment = debtPayment.add(accrualPayment).setScale(2, BigDecimal.ROUND_HALF_EVEN);
                    }
                    debtBalance = debtBalance.subtract(debtPayment).setScale(2, BigDecimal.ROUND_HALF_EVEN);
                    jsonGenerator.writeStartObject()
                            .write("month", i)
                            .write("payment", payment)
                            .write("debt_payment", debtPayment)
                            .write("accrual_payment", accrualPayment)
                            .write("debt_balance", debtBalance)
                            .write("payment_date", paymentDate.toString())
                            .writeEnd();
                    if (i == period && debtBalance.compareTo(BigDecimal.ZERO) > 0) {
                        payment = debtBalance;
                        debtPayment = BigDecimal.ZERO;
                        accrualPayment = BigDecimal.ZERO;
                        debtBalance = BigDecimal.ZERO;
                        jsonGenerator.writeStartObject()
                                .write("month", i)
                                .write("payment", payment)
                                .write("debt_payment", debtPayment)
                                .write("accrual_payment", accrualPayment)
                                .write("debt_balance", debtBalance)
                                .write("payment_date", paymentDate.toString())
                                .writeEnd();
                    }
                }
                jsonGenerator.writeEnd().flush();
            }
        };
        return Response.ok(stream).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces("application/credit_payment-v2+json; charset=UTF-8")
    @ApiOperation(value = "Calculate Annuitant Credit Payments",
            produces = "application/credit_payment-v2+json; charset=UTF-8")
    @ApiResponses({
            @ApiResponse(code = 200, message = "List of Credit Payments")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sum", value = "Loan Amount", dataTypeClass = BigDecimal.class, required = true, paramType = "form"),
            @ApiImplicitParam(name = "percent", value = "Interest Rate per Year", dataTypeClass = double.class, required = true, paramType = "form"),
            @ApiImplicitParam(name = "period", value = "Loan Term in Months", dataTypeClass = int.class, required = true, paramType = "form"),
            @ApiImplicitParam(name = "start_date", value = "Loan Start Date", dataTypeClass = String.class, required = true, paramType = "form")
    })
    public Response calculateAnnuitantCreditPayments(
            @FormParam("sum") BigDecimal sum,
            @FormParam("percent") double percent,
            @FormParam("period") int period,
            @FormParam("start_date") String startDate
    ) {
        LocalDate date = LocalDate.parse(startDate);
        StreamingOutput stream = new StreamingOutput() {
            @Override
            public void write(OutputStream os) throws IOException, WebApplicationException {
                Writer writer = new BufferedWriter(new OutputStreamWriter(os));
                JsonGenerator jsonGenerator = Json.createGenerator(writer);
                jsonGenerator.writeStartArray();
                BigDecimal debtBalance = sum;
                BigDecimal monthlyPercent = BigDecimal.valueOf(percent/12/100);
                BigDecimal payment = sum.multiply(monthlyPercent).divide(BigDecimal.valueOf(1).subtract(BigDecimal.valueOf(1).divide(monthlyPercent.add(BigDecimal.valueOf(1)).pow(period), 10, BigDecimal.ROUND_HALF_EVEN)), 10, BigDecimal.ROUND_HALF_EVEN).setScale(2, BigDecimal.ROUND_HALF_EVEN);
                for (int i = 1; i <= period; i++) {
                    LocalDate paymentDate = date.plusMonths(i);
                    BigDecimal accrualPayment = calculateAccrualPayment(date.plusMonths(i - 1), paymentDate, debtBalance, percent).setScale(2, BigDecimal.ROUND_HALF_EVEN);
                    BigDecimal debtPayment = payment.subtract(accrualPayment).setScale(2, BigDecimal.ROUND_HALF_EVEN);
                    if (i == period && debtPayment.compareTo(debtBalance) > 0) {
                        debtPayment = debtBalance.setScale(2, BigDecimal.ROUND_HALF_EVEN);
                        payment = debtPayment.add(accrualPayment).setScale(2, BigDecimal.ROUND_HALF_EVEN);
                    }
                    debtBalance = debtBalance.subtract(debtPayment).setScale(2, BigDecimal.ROUND_HALF_EVEN);
                    jsonGenerator.writeStartObject()
                            .write("month", i)
                            .write("payment", payment)
                            .write("debt_payment", debtPayment)
                            .write("accrual_payment", accrualPayment)
                            .write("debt_balance", debtBalance)
                            .write("payment_date", paymentDate.toString())
                            .writeEnd();
                    if (i == period && debtBalance.compareTo(BigDecimal.ZERO) > 0) {
                        payment = debtBalance;
                        debtPayment = BigDecimal.ZERO;
                        accrualPayment = BigDecimal.ZERO;
                        debtBalance = BigDecimal.ZERO;
                        jsonGenerator.writeStartObject()
                                .write("month", i)
                                .write("payment", payment)
                                .write("debt_payment", debtPayment)
                                .write("accrual_payment", accrualPayment)
                                .write("debt_balance", debtBalance)
                                .write("payment_date", paymentDate.toString())
                                .writeEnd();
                    }
                }
                jsonGenerator.writeEnd().flush();
            }
        };
        return Response.ok(stream).build();
    }

    private BigDecimal calculateAccrualPayment(LocalDate startDate, LocalDate endDate, BigDecimal debtAmount, double percent) {
        BigDecimal accrualPayment = new BigDecimal(0);
        for(int i = 1; i <= (endDate.toEpochDay() - startDate.toEpochDay()); i++) {
            accrualPayment = accrualPayment.add(debtAmount.multiply(BigDecimal.valueOf(percent/100/startDate.plusDays(i).lengthOfYear())));
        }
        return accrualPayment;
    }
}
