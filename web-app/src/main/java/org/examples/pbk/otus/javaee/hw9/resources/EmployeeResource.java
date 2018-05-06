package org.examples.pbk.otus.javaee.hw9.resources;

import io.swagger.annotations.*;
import org.ehcache.Cache;
import org.examples.pbk.otus.javaee.hw9.CacheManagerProvider;
import org.examples.pbk.otus.javaee.hw9.model.Employee;
import org.examples.pbk.otus.javaee.hw9.service.EmployeeService;
import org.examples.pbk.otus.javaee.hw9.service.JpaEmployeeService;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@SwaggerDefinition(
        info = @Info(
                title = "Employee Resource Swagger-generated API",
                description = "Employee Resource Description example",
                version = "1.0.0",
                termsOfService = "share and care",
                contact = @Contact(
                        name = "Vitaliy", email = "pbk.vitaliy@gmail.com",
                        url = "https://example.com"),
                license = @License(
                        name = "Apache 2.0",
                        url = "http://www.apache.org")),
        tags = {
                @Tag(name = "employee_resource", description = "Description Example"),
                @Tag(name = "department_resource", description = "Description Example"),
                @Tag(name = "login_resource", description = "Description Example"),
                @Tag(name = "news_resource", description = "Description Example"),
                @Tag(name = "currency_resource", description = "Description Example"),
                @Tag(name = "statistic_resource", description = "Description Example")
        },
        host = "localhost:3000",
        basePath = "/api",
        schemes = {SwaggerDefinition.Scheme.HTTP}
)
@Api(tags = "employee_resource", produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
@Path("employee")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EmployeeResource {

    private EmployeeService service;
    private Cache<String, List> filterCache;

    @Inject
    public EmployeeResource(JpaEmployeeService service) {
        this.service = service;
        this.filterCache = CacheManagerProvider.getCacheManager().getCache("filterEmployee", String.class, List.class);
    }

    @GET
    @RolesAllowed({"CEO", "ACC", "HRM", "USR"})
    @ApiOperation(value = "Find All Employees",
            produces = MediaType.APPLICATION_JSON,
            authorizations = {
            @Authorization(value = "Bearer", scopes = {})
            })
    @ApiResponses({
            @ApiResponse(code = 200, message = "List of Employees")
    })
    public Response findAll() {
        List<Employee> employees = service.findAll();
        return Response.ok(employees).build();
    }

    @GET
    @Path("/filter")
    @RolesAllowed({"CEO", "ACC", "HRM", "USR"})
    @ApiOperation(value = "Filter Employees by Query Parameters",
            produces = MediaType.APPLICATION_JSON,
            authorizations = {
                    @Authorization(value = "Bearer", scopes = {})
            })
    @ApiResponses({
            @ApiResponse(code = 200, message = "List of Employees")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "Name of Employee", dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "job", value = "Employee Job", dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "city", value = "Employee City", dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "ageFrom", value = "Age From", dataTypeClass = int.class, paramType = "query"),
            @ApiImplicitParam(name = "ageTo", value = "Age To", dataTypeClass = int.class, paramType = "query")
    })
    public Response filterEmployees(@QueryParam("name") String name,
                                    @QueryParam("job") String job,
                                    @QueryParam("city") String city,
                                    @QueryParam("ageFrom") int ageFrom,
                                    @QueryParam("ageTo") int ageTo,
                                    @Context UriInfo uriInfo) {
        List<Employee> employees;
        String query = uriInfo.getRequestUri().getRawQuery();
        if (query != null && (employees = filterCache.get(query)) != null) {
            System.out.println("Cache Hit!!!");
            return Response.ok(employees).build();
        } else {
            DetachedCriteria criteria = DetachedCriteria.forClass(Employee.class);
            if (name != null) {
                criteria.add(Restrictions.like("name", name, MatchMode.ANYWHERE));
            }
            if (job != null) {
                criteria.add(Restrictions.eq("job", job));
            }
            if (city != null) {
                criteria.createCriteria("department")
                        .add(Restrictions.eq("city", city));
            }
            if (ageFrom != 0) {
                criteria.add(Restrictions.gt("age", ageFrom));
            }
            if (ageTo != 0) {
                criteria.add(Restrictions.lt("age", ageTo));
            }
            employees = service.findEmployees(criteria);
            if (query != null) {
                filterCache.put(uriInfo.getRequestUri().getRawQuery(), employees);
            }
            return Response.ok(employees).build();
        }
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({"CEO", "ACC", "HRM", "USR"})
    @ApiOperation(value = "Get Employee by id",
            produces = MediaType.APPLICATION_JSON,
            authorizations = {
                    @Authorization(value = "Bearer", scopes = {})
            })
    @ApiResponses({
            @ApiResponse(code = 200, message = "Employee with requested id")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "Id of Employee", dataTypeClass = long.class, paramType = "path")
    })
    public Response findById(@PathParam("id") long id) {
        Employee employee = service.findById(id);
        return Response.ok(employee).build();
    }

    @POST
    @RolesAllowed({"CEO", "HRM"})
    @ApiOperation(value = "Create Employee",
            produces = MediaType.APPLICATION_JSON,
            consumes = MediaType.APPLICATION_JSON,
            authorizations = {
                    @Authorization(value = "Bearer", scopes = {})
            })
    @ApiResponses({
            @ApiResponse(code = 200, message = "URI of created Employee")
    })
    public Response create(@ApiParam(name = "employee", value = "Employee to create", required = true) Employee employee) throws URISyntaxException {
        service.create(employee);
        filterCache.clear();
        return Response.created(new URI("/api/account/" + employee.getId())).build();
    }

    @PUT
    @RolesAllowed({"CEO", "ACC", "HRM"})
    @ApiOperation(value = "Update Employee",
            produces = MediaType.APPLICATION_JSON,
            consumes = MediaType.APPLICATION_JSON,
            authorizations = {
                    @Authorization(value = "Bearer", scopes = {})
            })
    @ApiResponses({
            @ApiResponse(code = 200, message = "")
    })
    public Response update(@ApiParam(name = "employee", value = "Employee to update", required = true) Employee employee) {
        service.update(employee);
        filterCache.clear();
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed({"CEO", "HRM"})
    @ApiOperation(value = "Delete Employee by id",
            authorizations = {
                    @Authorization(value = "Bearer", scopes = {})
            })
    @ApiResponses({
            @ApiResponse(code = 200, message = "")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "Id of Employee", dataTypeClass = long.class, paramType = "path")
    })
    public Response delete(@PathParam("id") long id) {
        service.delete(id);
        filterCache.clear();
        return Response.ok().build();
    }
}
