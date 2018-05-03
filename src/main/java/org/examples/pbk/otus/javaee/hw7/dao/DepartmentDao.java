package org.examples.pbk.otus.javaee.hw7.dao;

import org.examples.pbk.otus.javaee.hw7.model.Department;

import java.util.List;

public interface DepartmentDao {
    List<Department> findAll();
    Department findById(long id);
    void create(Department department);
    void update(Department department);
    void delete(long id);
}
