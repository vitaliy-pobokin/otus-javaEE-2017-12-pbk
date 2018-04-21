package org.examples.pbk.otus.javaee.hw8;

import org.examples.pbk.otus.javaee.hw8.resources.AuthenticationFilter;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;

@ApplicationPath("/api/")
public class JAXRSConfiguration extends ResourceConfig {
    public JAXRSConfiguration() {
        packages(false, "org.examples.pbk.otus.javaee.hw8.resources");
        register(AuthenticationFilter.class);
    }
}
