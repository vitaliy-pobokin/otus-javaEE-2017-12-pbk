package org.examples.pbk.otus.javaee.hw6.resources;

import org.examples.pbk.otus.javaee.hw6.dao.JpaAccountDao;
import org.examples.pbk.otus.javaee.hw6.model.Account;

import javax.annotation.security.DenyAll;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

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
        List<Account> accounts = TransactionUtils.runInTransaction(entityManager -> {
            dao.setEntityManager(entityManager);
            return dao.findAll();
        });
        return Response.ok(accounts).build();
    }

    @GET
    @Path("/{id}")
    @DenyAll
    public Response findById(@PathParam("id") long id) {
        Account account = TransactionUtils.runInTransaction(entityManager -> {
            dao.setEntityManager(entityManager);
            return dao.findById(id);
        });
        return Response.ok(account).build();
    }

    @POST
    @DenyAll
    public Response create(Account account) throws URISyntaxException {
        TransactionUtils.runInTransactionWithoutResult(entityManager -> {
            dao.setEntityManager(entityManager);
            dao.create(account);
        });
        return Response.created(new URI("/api/account/" + account.getId())).build();
    }

    @PUT
    @DenyAll
    public Response update(Account account) {
        TransactionUtils.runInTransactionWithoutResult(entityManager -> {
            dao.setEntityManager(entityManager);
            dao.update(account);
        });
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    @DenyAll
    public Response delete(@PathParam("id") long id) {
        TransactionUtils.runInTransactionWithoutResult(entityManager -> {
            dao.setEntityManager(entityManager);
            dao.delete(id);
        });
        return Response.ok().build();
    }
}
