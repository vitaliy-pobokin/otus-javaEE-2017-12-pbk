package org.examples.pbk.otus.javaee.hw5.resources;

import org.examples.pbk.otus.javaee.hw5.EMF;
import org.examples.pbk.otus.javaee.hw5.dao.JpaAccountDao;
import org.examples.pbk.otus.javaee.hw5.model.Account;

import javax.annotation.security.DenyAll;
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

@Path("account")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AccountResource {

    private JpaAccountDao dao;

    @Inject
    public AccountResource(JpaAccountDao accountDao) {
        this.dao = accountDao;
    }

    @GET
    @DenyAll
    public Response findAll() {
        List<Account> accounts = runInTransaction(entityManager -> {
            dao.setEntityManager(entityManager);
            return dao.findAll();
        });
        return Response.ok(accounts).build();
    }

    @GET
    @Path("/{id}")
    @DenyAll
    public Response findById(@PathParam("id") long id) {
        Account account = runInTransaction(entityManager -> {
            dao.setEntityManager(entityManager);
            return dao.findById(id);
        });
        return Response.ok(account).build();
    }

    @POST
    @DenyAll
    public Response create(Account account) throws URISyntaxException {
        runInTransactionWithoutResult(entityManager -> {
            dao.setEntityManager(entityManager);
            dao.create(account);
        });
        return Response.created(new URI("/api/account/" + account.getId())).build();
    }

    @PUT
    @DenyAll
    public Response update(Account account) {
        runInTransactionWithoutResult(entityManager -> {
            dao.setEntityManager(entityManager);
            dao.update(account);
        });
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    @DenyAll
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
