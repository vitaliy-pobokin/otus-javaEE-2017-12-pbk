package org.examples.pbk.otus.javaee.hw7.resources;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import org.examples.pbk.otus.javaee.hw7.model.Account;
import org.examples.pbk.otus.javaee.hw7.model.Credentials;
import org.examples.pbk.otus.javaee.hw7.model.User;
import org.examples.pbk.otus.javaee.hw7.service.JpaAccountService;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
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
public class LoginResource {
    private static final long EXPIRATION_PERIOD = 1000 * 60 * 60 * 24 * 7;
    private static final String SIGNATURE_KEY = "ShouldBeProducedNotFromString";

    private static final String AUTHORIZATION_PROPERTY = "Authorization";
    private static final String AUTHENTICATION_SCHEME = "Bearer";

    private JpaAccountService accountService;

    @Inject
    public LoginResource(JpaAccountService accountService) {
        this.accountService = accountService;
    }

    @POST
    @PermitAll
    public Response login(Credentials credentials, @Context HttpServletRequest request) {
        String username = credentials.getUsername();
        String password = credentials.getPassword();
        Account account = null;
        try {
            account = accountService.findByUsername(username);
        } catch (NoResultException | NonUniqueResultException e) {
        }
        if (account != null && account.getPassword().equals(password)) {
            String token = createJwtToken(account);
            User user = getUser(account);
            return Response.ok(user).header(AUTHORIZATION_PROPERTY, AUTHENTICATION_SCHEME + " " + token).build();
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
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
