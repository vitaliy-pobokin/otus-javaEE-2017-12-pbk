package org.examples.pbk.otus.dao;

import org.examples.pbk.otus.model.Employee;

import java.util.List;

public interface EmployeeDao {
    List<Employee> findAll();
    Employee findById(long id);
    void create(Employee employee);
    void update(Employee employee);
    void delete(long id);
}
