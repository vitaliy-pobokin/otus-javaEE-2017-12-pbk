package org.examples.pbk.otus.javaee.hw7.service;

import org.examples.pbk.otus.javaee.hw7.dao.JpaDepartmentDao;
import org.examples.pbk.otus.javaee.hw7.model.Department;
import org.examples.pbk.otus.javaee.hw7.resources.TransactionUtils;

import java.util.List;

public class JpaDepartmentService implements DepartmentService {

    private JpaDepartmentDao dao;

    public JpaDepartmentService() {
        this.dao = new JpaDepartmentDao();
    }

    @Override
    public List<Department> findAll() {
        return TransactionUtils.runInTransaction(session -> {
            dao.setSession(session);
            return dao.findAll();
        });
    }

    @Override
    public Department findById(long id) {
        return TransactionUtils.runInTransaction(session -> {
            dao.setSession(session);
            return dao.findById(id);
        });
    }

    @Override
    public void create(Department department) {
        TransactionUtils.runInTransactionWithoutResult(session -> {
            dao.setSession(session);
            dao.create(department);
        });
    }

    @Override
    public void update(Department department) {
        TransactionUtils.runInTransactionWithoutResult(session -> {
            dao.setSession(session);
            dao.update(department);
        });
    }

    @Override
    public void delete(long id) {
        TransactionUtils.runInTransactionWithoutResult(session -> {
            dao.setSession(session);
            dao.delete(id);
        });
    }
}
