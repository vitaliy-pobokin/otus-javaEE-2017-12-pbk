package org.examples.pbk.otus.javaee.hw9.service;

import org.examples.pbk.otus.javaee.hw9.model.Department;

import java.util.List;

public interface DepartmentService {
    List<Department> findAll();
    Department findById(long id);
    void create(Department department);
    void update(Department department);
    void delete(long id);
}
