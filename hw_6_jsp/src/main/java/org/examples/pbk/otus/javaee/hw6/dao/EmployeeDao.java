package org.examples.pbk.otus.javaee.hw6.dao;

import org.examples.pbk.otus.javaee.hw6.model.Employee;
import org.hibernate.criterion.DetachedCriteria;

import java.util.List;

public interface EmployeeDao {
    List<Employee> findAll();
    List<Employee> findEmployees(DetachedCriteria dcr);
    Employee findById(long id);
    void create(Employee employee);
    void update(Employee employee);
    void delete(long id);
}
