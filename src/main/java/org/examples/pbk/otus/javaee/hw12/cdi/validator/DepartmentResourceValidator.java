package org.examples.pbk.otus.javaee.hw12.cdi.validator;

import org.examples.pbk.otus.javaee.hw12.model.Department;
import org.examples.pbk.otus.javaee.hw12.resources.DepartmentResourceI;

import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.ws.rs.core.Response;
import java.net.URISyntaxException;
import java.util.Set;

@Decorator
public abstract class DepartmentResourceValidator implements DepartmentResourceI {

    @Inject
    @Delegate
    private DepartmentResourceI departmentResource;

    @Override
    public Response create(Department department) throws URISyntaxException {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Department>> violations = validator.validate(department);
        if (violations.isEmpty()) {
            return departmentResource.create(department);
        } else {
            return Response.status(400, violations.toString()).build();
        }
    }

    @Override
    public Response update(Department department) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Department>> violations = validator.validate(department);
        if (violations.isEmpty()) {
            return departmentResource.update(department);
        } else {
            return Response.status(400, violations.toString()).build();
        }
    }
}
