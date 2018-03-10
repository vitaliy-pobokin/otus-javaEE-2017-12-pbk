package org.examples.pbk.otus.javaee.hw5.resources;

import org.examples.pbk.otus.javaee.hw5.EMF;
import org.examples.pbk.otus.javaee.hw5.dao.JpaEmployeeDao;
import org.examples.pbk.otus.javaee.hw5.model.Employee;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

@Path("employee")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EmployeeResource {

    private JpaEmployeeDao dao;

    @Inject
    public EmployeeResource(JpaEmployeeDao employeeDao) {
        this.dao = employeeDao;
    }

    @GET
    @RolesAllowed({"CEO", "ACC", "HRM", "USR"})
    public Response findAll() {
        List<Employee> employees = runInTransaction(entityManager -> {
            dao.setEntityManager(entityManager);
            return dao.findAll();
        });
        return Response.ok(employees).build();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({"CEO", "ACC", "HRM", "USR"})
    public Response findById(@PathParam("id") long id) {
        Employee employee = runInTransaction(entityManager -> {
            dao.setEntityManager(entityManager);
            return dao.findById(id);
        });
        return Response.ok(employee).build();
    }

    @POST
    @RolesAllowed({"CEO", "HRM"})
    public Response create(Employee employee) throws URISyntaxException {
        runInTransactionWithoutResult(entityManager -> {
            dao.setEntityManager(entityManager);
            dao.create(employee);
        });
        return Response.created(new URI("/api/account/" + employee.getId())).build();
    }

    @PUT
    @RolesAllowed({"CEO", "ACC", "HRM"})
    public Response update(Employee employee) {
        runInTransactionWithoutResult(entityManager -> {
            dao.setEntityManager(entityManager);
            dao.update(employee);
        });
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed({"CEO", "HRM"})
    public Response delete(@PathParam("id") long id) {
        runInTransactionWithoutResult(entityManager -> {
            dao.setEntityManager(entityManager);
            dao.delete(id);
        });
        return Response.ok().build();
    }

    private <R> R runInTransaction(Function<EntityManager, R> function) {
        EntityManager em = EMF.createEntityManager();
        em.getTransaction().begin();
        R result = function.apply(em);
        em.getTransaction().commit();
        em.close();
        return result;
    }

    private void runInTransactionWithoutResult(Consumer<EntityManager> consumer) {
        EntityManager em = EMF.createEntityManager();
        em.getTransaction().begin();
        consumer.accept(em);
        em.getTransaction().commit();
        em.close();
    }
}
