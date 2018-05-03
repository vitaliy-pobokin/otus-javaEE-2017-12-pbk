package org.examples.pbk.otus.javaee.hw7.resources;

import org.examples.pbk.otus.javaee.hw7.model.Account;
import org.examples.pbk.otus.javaee.hw7.service.AccountService;
import org.examples.pbk.otus.javaee.hw7.service.JpaAccountService;

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

    private AccountService service;

    @Inject
    public AccountResource(JpaAccountService service) {
        this.service = service;
    }

    @GET
    @DenyAll
    public Response findAll() {
        List<Account> accounts = service.findAll();
        return Response.ok(accounts).build();
    }

    @GET
    @Path("/{id}")
    @DenyAll
    public Response findById(@PathParam("id") long id) {
        Account account = service.findById(id);
        return Response.ok(account).build();
    }

    @POST
    @DenyAll
    public Response create(Account account) throws URISyntaxException {
        service.create(account);
        return Response.created(new URI("/api/account/" + account.getId())).build();
    }

    @PUT
    @DenyAll
    public Response update(Account account) {
        service.update(account);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    @DenyAll
    public Response delete(@PathParam("id") long id) {
        service.delete(id);
        return Response.ok().build();
    }
}
