package org.examples.pbk.otus.javaee.hw12;

import io.swagger.jaxrs.config.BeanConfig;
import org.examples.pbk.otus.javaee.hw12.resources.*;
import org.examples.pbk.otus.javaee.hw12.statistic.StatisticResource;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;


@ApplicationPath("/api/")
public class JAXRSConfiguration extends Application {
    public JAXRSConfiguration() {
        BeanConfig beanConfig = new BeanConfig();
        beanConfig.setVersion("1.0");
        beanConfig.setSchemes(new String[] { "http" });
        beanConfig.setTitle("Web-app API");
    }

    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new HashSet<>();
        addRestResourceClasses(resources);
//        addFilterClasses(resources);
        return resources;
    }

    private void addFilterClasses(Set<Class<?>> resources) {
        resources.add(AuthenticationFilter.class);
    }

    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(AccountResource.class);
        resources.add(CurrencyResource.class);
        resources.add(DepartmentResource.class);
        resources.add(EmployeeResource.class);
        resources.add(LoginResource.class);
        resources.add(NewsResource.class);
        resources.add(StatisticResource.class);

        resources.add(io.swagger.jaxrs.listing.ApiListingResource.class);
        resources.add(io.swagger.jaxrs.listing.SwaggerSerializers.class);
    }
}
