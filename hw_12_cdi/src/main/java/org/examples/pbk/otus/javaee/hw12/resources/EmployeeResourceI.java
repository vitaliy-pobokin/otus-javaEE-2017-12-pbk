package org.examples.pbk.otus.javaee.hw12.resources;

import org.examples.pbk.otus.javaee.hw12.model.Employee;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URISyntaxException;

public interface EmployeeResourceI {
    Response findAll();
    Response filterEmployees(String name,
                             String job,
                             String city,
                             int ageFrom,
                             int ageTo,
                             UriInfo uriInfo);

    Response findById(long id);
    Response create(Employee employee) throws URISyntaxException;
    Response update(Employee employee);
    Response delete(long id);
}
