package org.examples.pbk.otus.javaee.hw9;

import org.examples.pbk.otus.javaee.hw9.resources.AuthenticationFilter;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;

@ApplicationPath("/api/")
public class JAXRSConfiguration extends ResourceConfig {
    public JAXRSConfiguration() {
        packages(false, "org.examples.pbk.otus.javaee.hw9.resources");
        packages(false, "org.examples.pbk.otus.javaee.hw9.statistic");
        register(AuthenticationFilter.class);
    }
}
