package org.examples.pbk.otus.javaee.hw7.service;

import org.examples.pbk.otus.javaee.hw7.dao.JpaEmployeeDao;
import org.examples.pbk.otus.javaee.hw7.model.Employee;
import org.examples.pbk.otus.javaee.hw7.resources.TransactionUtils;
import org.hibernate.criterion.DetachedCriteria;

import java.util.List;

public class JpaEmployeeService implements EmployeeService {

    private JpaEmployeeDao dao;

    public JpaEmployeeService() {
        this.dao = new JpaEmployeeDao();
    }

    @Override
    public List<Employee> findAll() {
        return TransactionUtils.runInTransaction(session -> {
            dao.setSession(session);
            return dao.findAll();
        });
    }

    @Override
    public List<Employee> findEmployees(DetachedCriteria dcr) {
        return TransactionUtils.runInTransaction(session -> {
            dao.setSession(session);
            return dao.findEmployees(dcr);
        });
    }

    @Override
    public Employee findById(long id) {
        return TransactionUtils.runInTransaction(session -> {
            dao.setSession(session);
            return dao.findById(id);
        });
    }

    @Override
    public void create(Employee employee) {
        TransactionUtils.runInTransactionWithoutResult(session -> {
            dao.setSession(session);
            dao.create(employee);
        });
    }

    @Override
    public void update(Employee employee) {
        TransactionUtils.runInTransactionWithoutResult(session -> {
            dao.setSession(session);
            dao.update(employee);
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
