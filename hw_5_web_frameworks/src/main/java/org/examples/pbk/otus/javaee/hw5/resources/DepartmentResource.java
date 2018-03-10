package org.examples.pbk.otus.javaee.hw5.resources;

import org.examples.pbk.otus.javaee.hw5.EMF;
import org.examples.pbk.otus.javaee.hw5.dao.JpaDepartmentDao;
import org.examples.pbk.otus.javaee.hw5.model.Department;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

@Path("department")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DepartmentResource {

    private JpaDepartmentDao dao;

    @Inject
    public DepartmentResource(JpaDepartmentDao departmentDao) {
        this.dao = departmentDao;
    }

    @GET
    @RolesAllowed({"CEO", "ACC", "HRM", "USR"})
    public Response findAll() {
        List<Department> departments = runInTransaction(entityManager -> {
            dao.setEntityManager(entityManager);
            return dao.findAll();
        });
        return Response.ok(departments).build();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({"CEO", "ACC", "HRM", "USR"})
    public Response findById(@PathParam("id") long id) {
        Department department = runInTransaction(entityManager -> {
            dao.setEntityManager(entityManager);
            return dao.findById(id);
        });
        return Response.ok(department).build();
    }

    @POST
    @RolesAllowed({"CEO"})
    public Response create(Department department) throws URISyntaxException {
        runInTransactionWithoutResult(entityManager -> {
            dao.setEntityManager(entityManager);
            dao.create(department);
        });
        return Response.created(new URI("/api/account/" + department.getId())).build();
    }

    @PUT
    @RolesAllowed({"CEO"})
    public Response update(Department department) {
        runInTransactionWithoutResult(entityManager -> {
            dao.setEntityManager(entityManager);
            dao.update(department);
        });
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed({"CEO"})
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
