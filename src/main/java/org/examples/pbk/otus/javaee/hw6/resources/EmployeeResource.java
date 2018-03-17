package org.examples.pbk.otus.javaee.hw6.resources;

import org.examples.pbk.otus.javaee.hw6.dao.JpaEmployeeDao;
import org.examples.pbk.otus.javaee.hw6.model.Employee;
import org.examples.pbk.otus.javaee.hw6.service.EmployeeService;
import org.examples.pbk.otus.javaee.hw6.service.JpaEmployeeService;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Path("employee")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EmployeeResource {

    private EmployeeService service;

    @Inject
    public EmployeeResource(JpaEmployeeService service) {
        this.service = service;
    }

    @GET
    @RolesAllowed({"CEO", "ACC", "HRM", "USR"})
    public Response findAll() {
        List<Employee> employees = service.findAll();
        return Response.ok(employees).build();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({"CEO", "ACC", "HRM", "USR"})
    public Response findById(@PathParam("id") long id) {
        Employee employee = service.findById(id);
        return Response.ok(employee).build();
    }

    @POST
    @RolesAllowed({"CEO", "HRM"})
    public Response create(Employee employee) throws URISyntaxException {
        service.create(employee);
        return Response.created(new URI("/api/account/" + employee.getId())).build();
    }

    @PUT
    @RolesAllowed({"CEO", "ACC", "HRM"})
    public Response update(Employee employee) {
        service.update(employee);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed({"CEO", "HRM"})
    public Response delete(@PathParam("id") long id) {
        service.delete(id);
        return Response.ok().build();
    }
}
