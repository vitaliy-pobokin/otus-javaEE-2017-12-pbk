package org.examples.pbk.otus.javaee.hw12.cdi.validator;

import org.examples.pbk.otus.javaee.hw12.model.Employee;
import org.examples.pbk.otus.javaee.hw12.resources.EmployeeResourceI;

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
public abstract class EmployeeResourceValidator implements EmployeeResourceI {

    @Inject
    @Delegate
    private EmployeeResourceI employeeResource;

    @Override
    public Response create(Employee employee) throws URISyntaxException {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Employee>> violations = validator.validate(employee);
        if (violations.isEmpty()) {
            return employeeResource.create(employee);
        } else {
            return Response.status(400, violations.toString()).build();
        }
    }

    @Override
    public Response update(Employee employee) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Employee>> violations = validator.validate(employee);
        if (violations.isEmpty()) {
            return employeeResource.update(employee);
        } else {
            return Response.status(400, violations.toString()).build();
        }
    }
}
