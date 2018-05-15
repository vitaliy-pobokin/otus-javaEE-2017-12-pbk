package org.examples.pbk.otus.javaee.hw12.service;

import org.examples.pbk.otus.javaee.hw12.dao.EmployeeDao;
import org.examples.pbk.otus.javaee.hw12.model.Employee;
import org.hibernate.criterion.DetachedCriteria;

import javax.inject.Inject;
import java.util.List;

public class JpaEmployeeService implements EmployeeService {

    @Inject
    private EmployeeDao dao;

    public JpaEmployeeService() {
    }

    @Override
    public List<Employee> findAll() {
            return dao.findAll();
    }

    @Override
    public List<Employee> findEmployees(DetachedCriteria dcr) {
        return dao.findEmployees(dcr);
    }

    @Override
    public Employee findById(long id) {
        return dao.findById(id);
    }

    @Override
    public void create(Employee employee) {
        dao.create(employee);
    }

    @Override
    public void update(Employee employee) {
        dao.update(employee);
    }

    @Override
    public void delete(long id) {
        dao.delete(id);
    }
}
