package org.examples.pbk.otus.javaee.hw6;

import org.examples.pbk.otus.javaee.hw6.resources.AuthenticationFilter;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;

@ApplicationPath("/api/")
public class JAXRSConfiguration extends ResourceConfig {
    public JAXRSConfiguration() {
        packages(false, "org.examples.pbk.otus.javaee.hw6.resources");
        register(AuthenticationFilter.class);
    }
}
