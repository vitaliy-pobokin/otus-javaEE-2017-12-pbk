package org.examples.pbk.otus.javaee.hw7.resources;

import org.examples.pbk.otus.javaee.hw7.model.Department;
import org.examples.pbk.otus.javaee.hw7.service.DepartmentService;
import org.examples.pbk.otus.javaee.hw7.service.JpaDepartmentService;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Path("department")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DepartmentResource {

    private DepartmentService service;

    @Inject
    public DepartmentResource(JpaDepartmentService service) {
        this.service = service;
    }

    @GET
    @RolesAllowed({"CEO", "ACC", "HRM", "USR"})
    public Response findAll() {
        List<Department> departments = service.findAll();
        return Response.ok(departments).build();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({"CEO", "ACC", "HRM", "USR"})
    public Response findById(@PathParam("id") long id) {
        Department department = service.findById(id);
        return Response.ok(department).build();
    }

    @POST
    @RolesAllowed({"CEO"})
    public Response create(Department department) throws URISyntaxException {
        service.create(department);
        return Response.created(new URI("/api/account/" + department.getId())).build();
    }

    @PUT
    @RolesAllowed({"CEO"})
    public Response update(Department department) {
        service.update(department);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed({"CEO"})
    public Response delete(@PathParam("id") long id) {
        service.delete(id);
        return Response.ok().build();
    }
}
