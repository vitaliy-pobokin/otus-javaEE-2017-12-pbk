package org.examples.pbk.otus.javaee.hw7.resources;

import org.ehcache.Cache;
import org.examples.pbk.otus.javaee.hw7.CacheManagerProvider;
import org.examples.pbk.otus.javaee.hw7.model.Employee;
import org.examples.pbk.otus.javaee.hw7.service.EmployeeService;
import org.examples.pbk.otus.javaee.hw7.service.JpaEmployeeService;
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
    public Response findAll() {
        List<Employee> employees = service.findAll();
        return Response.ok(employees).build();
    }

    @GET
    @Path("/filter")
    @RolesAllowed({"CEO", "ACC", "HRM", "USR"})
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
    public Response findById(@PathParam("id") long id) {
        Employee employee = service.findById(id);
        return Response.ok(employee).build();
    }

    @POST
    @RolesAllowed({"CEO", "HRM"})
    public Response create(Employee employee) throws URISyntaxException {
        service.create(employee);
        filterCache.clear();
        return Response.created(new URI("/api/account/" + employee.getId())).build();
    }

    @PUT
    @RolesAllowed({"CEO", "ACC", "HRM"})
    public Response update(Employee employee) {
        service.update(employee);
        filterCache.clear();
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed({"CEO", "HRM"})
    public Response delete(@PathParam("id") long id) {
        service.delete(id);
        filterCache.clear();
        return Response.ok().build();
    }
}
