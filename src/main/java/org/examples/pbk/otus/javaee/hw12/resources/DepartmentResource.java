package org.examples.pbk.otus.javaee.hw12.resources;

import io.swagger.annotations.*;
import org.examples.pbk.otus.javaee.hw12.model.Department;
import org.examples.pbk.otus.javaee.hw12.service.DepartmentService;
import org.examples.pbk.otus.javaee.hw12.service.JpaDepartmentService;

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
@Api(tags = "department_resource", produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
public class DepartmentResource {

    @Inject
    private DepartmentService service;

    public DepartmentResource() {
    }

    @GET
    @RolesAllowed({"CEO", "ACC", "HRM", "USR"})
    @ApiOperation(value = "Find All Departments",
            produces = MediaType.APPLICATION_JSON,
            authorizations = {
                    @Authorization(value = "Bearer", scopes = {})
            })
    @ApiResponses({
            @ApiResponse(code = 200, message = "List of Departments")
    })
    public Response findAll() {
        List<Department> departments = service.findAll();
        return Response.ok(departments).build();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({"CEO", "ACC", "HRM", "USR"})
    @ApiOperation(value = "Get Department by id",
            produces = MediaType.APPLICATION_JSON,
            authorizations = {
                    @Authorization(value = "Bearer", scopes = {})
            })
    @ApiResponses({
            @ApiResponse(code = 200, message = "Department with requested id")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "Id of Department", dataTypeClass = long.class, paramType = "path")
    })
    public Response findById(@PathParam("id") long id) {
        Department department = service.findById(id);
        return Response.ok(department).build();
    }

    @POST
    @RolesAllowed({"CEO"})
    @ApiOperation(value = "Create Department",
            produces = MediaType.APPLICATION_JSON,
            consumes = MediaType.APPLICATION_JSON,
            authorizations = {
                    @Authorization(value = "Bearer", scopes = {})
            })
    @ApiResponses({
            @ApiResponse(code = 200, message = "URI of created Department")
    })
    public Response create(@ApiParam(name = "department", value = "Department to create", required = true) Department department) throws URISyntaxException {
        service.create(department);
        return Response.created(new URI("/api/account/" + department.getId())).build();
    }

    @PUT
    @RolesAllowed({"CEO"})
    @ApiOperation(value = "Update Department",
            produces = MediaType.APPLICATION_JSON,
            consumes = MediaType.APPLICATION_JSON,
            authorizations = {
                    @Authorization(value = "Bearer", scopes = {})
            })
    @ApiResponses({
            @ApiResponse(code = 200, message = "")
    })
    public Response update(@ApiParam(name = "department", value = "Department to create", required = true) Department department) {
        service.update(department);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed({"CEO"})
    @ApiOperation(value = "Delete Department by id",
            authorizations = {
                    @Authorization(value = "Bearer", scopes = {})
            })
    @ApiResponses({
            @ApiResponse(code = 200, message = "")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "Id of Department", dataTypeClass = long.class, paramType = "path")
    })
    public Response delete(@PathParam("id") long id) {
        service.delete(id);
        return Response.ok().build();
    }
}
