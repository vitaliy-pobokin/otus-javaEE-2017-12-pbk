package org.examples.pbk.otus.javaee.hw5;

import org.examples.pbk.otus.javaee.hw5.resources.AuthenticationFilter;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/api/")
public class JAXRSConfiguration extends ResourceConfig {
    public JAXRSConfiguration() {
        packages(false, "org.examples.pbk.otus.javaee.hw5.resources");
        register(AuthenticationFilter.class);
    }
}
