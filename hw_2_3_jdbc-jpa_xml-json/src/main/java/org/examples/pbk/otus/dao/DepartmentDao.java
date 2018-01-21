package org.examples.pbk.otus.dao;

import org.examples.pbk.otus.model.Department;

import java.util.List;

public interface DepartmentDao {
    List<Department> findAll();
    Department findById(long id);
    void create(Department department);
    void update(Department department);
    void delete(long id);
}
