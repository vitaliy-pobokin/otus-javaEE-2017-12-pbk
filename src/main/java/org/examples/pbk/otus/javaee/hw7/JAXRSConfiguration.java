package org.examples.pbk.otus.javaee.hw7;

import org.examples.pbk.otus.javaee.hw7.resources.AuthenticationFilter;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;

@ApplicationPath("/api/")
public class JAXRSConfiguration extends ResourceConfig {
    public JAXRSConfiguration() {
        packages(false, "org.examples.pbk.otus.javaee.hw7.resources");
        packages(false, "org.examples.pbk.otus.javaee.hw7.statistic");
        register(AuthenticationFilter.class);
    }
}
