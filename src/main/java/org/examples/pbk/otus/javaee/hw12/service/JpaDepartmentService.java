package org.examples.pbk.otus.javaee.hw12.service;

import org.examples.pbk.otus.javaee.hw12.dao.DepartmentDao;
import org.examples.pbk.otus.javaee.hw12.model.Department;

import javax.inject.Inject;
import java.util.List;

public class JpaDepartmentService implements DepartmentService {

    @Inject
    private DepartmentDao dao;

    public JpaDepartmentService() {
    }

    @Override
    public List<Department> findAll() {
        return dao.findAll();
    }

    @Override
    public Department findById(long id) {
        return dao.findById(id);
    }

    @Override
    public void create(Department department) {
        dao.create(department);
    }

    @Override
    public void update(Department department) {
        dao.update(department);
    }

    @Override
    public void delete(long id) {
        dao.delete(id);
    }
}
