package org.examples.pbk.otus.javaee.hw12.resources;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import io.swagger.annotations.*;
import org.examples.pbk.otus.javaee.hw12.cdi.InvocationTimeMeasurementInterceptor;
import org.examples.pbk.otus.javaee.hw12.cdi.events.LoginEvent;
import org.examples.pbk.otus.javaee.hw12.cdi.events.qualifiers.Successful;
import org.examples.pbk.otus.javaee.hw12.cdi.events.qualifiers.Unsuccessful;
import org.examples.pbk.otus.javaee.hw12.model.Account;
import org.examples.pbk.otus.javaee.hw12.model.Credentials;
import org.examples.pbk.otus.javaee.hw12.model.User;
import org.examples.pbk.otus.javaee.hw12.service.AccountService;

import javax.annotation.security.PermitAll;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Date;

// TODO: expiration check, constants from configuration file
@Path("login")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Interceptors(InvocationTimeMeasurementInterceptor.class)
@Api(tags = "login_resource", produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
public class LoginResource {
    private static final long EXPIRATION_PERIOD = 1000 * 60 * 60 * 24 * 7;
    private static final String SIGNATURE_KEY = "ShouldBeProducedNotFromString";

    private static final String AUTHORIZATION_PROPERTY = "Authorization";
    private static final String AUTHENTICATION_SCHEME = "Bearer";

    @Inject
    private AccountService accountService;

    @Inject
    @Successful
    private Event<LoginEvent> successfulLoginEvent;

    @Inject
    @Unsuccessful
    private Event<LoginEvent> unsuccessfulLoginEvent;

    public LoginResource() {
    }

    @POST
    @PermitAll
    @ApiOperation(value = "Login with Credentials",
            produces = MediaType.APPLICATION_JSON,
            consumes = MediaType.APPLICATION_JSON)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Logged in User Info"),
            @ApiResponse(code = 401, message = "Login failed")
    })
    public Response login(@ApiParam(name = "credentials", value = "Credentials to check", required = true) Credentials credentials, @Context HttpServletRequest request) {
        String username = credentials.getUsername();
        String password = credentials.getPassword();
        Account account = null;
        try {
            account = accountService.findByUsername(username);
        } catch (NoResultException | NonUniqueResultException e) {
        } finally {
            LoginEvent payload = new LoginEvent(credentials, request.getRemoteAddr());
            if (account != null && account.getPassword().equals(password)) {
                String token = createJwtToken(account);
                User user = getUser(account);
                successfulLoginEvent.fire(payload);
                return Response.ok(user).header(AUTHORIZATION_PROPERTY, AUTHENTICATION_SCHEME + " " + token).build();
            }
            unsuccessfulLoginEvent.fire(payload);
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    private String createJwtToken(Account account) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(getSignatureKey());
            return JWT.create()
                    .withIssuer("http://localhost")
                    .withSubject("accounts/" + account.getId())
                    .withExpiresAt(getExpirationDate())
                    .sign(algorithm);
        } catch (JWTCreationException e) {
            throw new RuntimeException(e);
        }
    }

    private User getUser(Account account) {
        User user = new User();
        user.setId(account.getId());
        user.setUsername(account.getUsername());
        user.setRole(account.getRole());
        return user;
    }

    private byte[] getSignatureKey() {
        return SIGNATURE_KEY.getBytes();
    }

    private Date getExpirationDate() {
        long currentTime = System.currentTimeMillis();
        return new Date(currentTime + EXPIRATION_PERIOD);
    }
}
