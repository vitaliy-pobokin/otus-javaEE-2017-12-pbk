package org.examples.pbk.otus.javaee.hw7.resources;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.examples.pbk.otus.javaee.hw7.model.Account;
import org.examples.pbk.otus.javaee.hw7.service.JpaAccountService;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;

// TODO: expiration check, constants from configuration file
@Provider
public class AuthenticationFilter implements javax.ws.rs.container.ContainerRequestFilter {

    @Inject
    private JpaAccountService accountService;

    @Context
    private ResourceInfo resourceInfo;

    private static final String AUTHORIZATION_PROPERTY = "Authorization";
    private static final String AUTHENTICATION_SCHEME = "Bearer";
    private static final String SIGNATURE_KEY = "ShouldBeProducedNotFromString";
    private static final Response ACCESS_DENIED = Response.status(Response.Status.UNAUTHORIZED).build();
    private static final Response ACCESS_FORBIDDEN = Response.status(Response.Status.FORBIDDEN).build();

    @Override
    public void filter(ContainerRequestContext requestContext) {
        Method method = resourceInfo.getResourceMethod();
        if( ! method.isAnnotationPresent(PermitAll.class)) {
            if(method.isAnnotationPresent(DenyAll.class)) {
                requestContext.abortWith(ACCESS_FORBIDDEN);
                return;
            }
            final MultivaluedMap<String, String> headers = requestContext.getHeaders();
            final List<String> authorization = headers.get(AUTHORIZATION_PROPERTY);
            if(authorization == null || authorization.isEmpty()) {
                requestContext.abortWith(ACCESS_DENIED);
                return;
            }
            final String jwtToken = authorization.get(0).replaceFirst(AUTHENTICATION_SCHEME + " ", "");
            String subject = null;
            try {
                Algorithm algorithm = Algorithm.HMAC256(getSignatureKey());
                JWTVerifier verifier = JWT.require(algorithm)
                        .withIssuer("http://localhost")
                        .build();
                DecodedJWT jwt = verifier.verify(jwtToken);
                subject = jwt.getSubject();
            }  catch (JWTVerificationException e){
                //Invalid signature/claims
                requestContext.abortWith(ACCESS_DENIED);
                return;
            }
            if(method.isAnnotationPresent(RolesAllowed.class)) {
                RolesAllowed rolesAnnotation = method.getAnnotation(RolesAllowed.class);
                Set<String> rolesSet = new HashSet<String>(Arrays.asList(rolesAnnotation.value()));
                if( ! isUserAllowed(subject, rolesSet)) {
                    requestContext.abortWith(ACCESS_FORBIDDEN);
                    return;
                }
            }
        }
    }

    private boolean isUserAllowed(final String subject, final Set<String> rolesSet) {
        boolean isAllowed = false;

        Account account = null;
        try {
            long accountId = Long.parseLong(subject.substring(subject.lastIndexOf("/") + 1));
            account = accountService.findById(accountId);
        } catch (NoResultException | NonUniqueResultException e) {
            return false;
        }

        if(rolesSet.contains(account.getRole())) {
            isAllowed = true;
        }
        return isAllowed;
    }

    private byte[] getSignatureKey() {
        return SIGNATURE_KEY.getBytes();
    }
}
