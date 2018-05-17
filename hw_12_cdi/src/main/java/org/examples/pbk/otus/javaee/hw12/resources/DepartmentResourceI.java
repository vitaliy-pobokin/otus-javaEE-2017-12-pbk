package org.examples.pbk.otus.javaee.hw12.resources;

import org.examples.pbk.otus.javaee.hw12.model.Department;

import javax.ws.rs.core.Response;
import java.net.URISyntaxException;

public interface DepartmentResourceI {
    Response findAll();
    Response findById(long id);
    Response create(Department department) throws URISyntaxException;
    Response update(Department department);
    Response delete(long id);
}
