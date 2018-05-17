package org.examples.pbk.otus.javaee.hw12.cdi;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("invocation_time")
@Produces(MediaType.APPLICATION_JSON)
public class InvocationTimeResource {

    @Inject
    private InvocationTimeMarkerDao dao;

    @GET
    @Path("/average")
    @RolesAllowed({"CEO"})
    public Response getAverageInvocationTime() {
        List<AverageInvocationTimeDTO> list = dao.getAverageInvocationTime();
        JsonArrayBuilder builder = Json.createArrayBuilder();
        for (AverageInvocationTimeDTO elem : list) {
            builder.add(
                    Json.createObjectBuilder()
                            .add("class", elem.getClassName())
                            .add("method", elem.getMethodName())
                            .add("avg_time", elem.getAverageInvocationTime())
                            .build());
        }
        return Response.ok(builder.build()).build();
    }
}
