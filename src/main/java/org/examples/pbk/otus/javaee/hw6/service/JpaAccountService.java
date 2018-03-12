package org.examples.pbk.otus.javaee.hw6.service;

import org.examples.pbk.otus.javaee.hw6.dao.JpaAccountDao;
import org.examples.pbk.otus.javaee.hw6.model.Account;
import org.examples.pbk.otus.javaee.hw6.resources.TransactionUtils;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class JpaAccountService implements AccountService {

    private JpaAccountDao dao;

    public JpaAccountService() {
        this.dao = new JpaAccountDao();
    }

    @Override
    public List<Account> findAll() {
        return TransactionUtils.runInTransaction(entityManager -> {
            dao.setEntityManager(entityManager);
            return dao.findAll();
        });
    }

    @Override
    public Account findById(long id) {
        return TransactionUtils.runInTransaction(entityManager -> {
            dao.setEntityManager(entityManager);
            return dao.findById(id);
        });
    }

    @Override
    public Account findByUsername(String username) {
        return TransactionUtils.runInTransaction(entityManager -> {
            dao.setEntityManager(entityManager);
            return dao.findByUsername(username);
        });
    }

    @Override
    public void create(Account account) {
        TransactionUtils.runInTransactionWithoutResult(entityManager -> {
            dao.setEntityManager(entityManager);
            dao.create(account);
        });
    }

    @Override
    public void update(Account account) {
        TransactionUtils.runInTransactionWithoutResult(entityManager -> {
            dao.setEntityManager(entityManager);
            dao.update(account);
        });
    }

    @Override
    public void delete(long id) {
        TransactionUtils.runInTransactionWithoutResult(entityManager -> {
            dao.setEntityManager(entityManager);
            dao.delete(id);
        });
    }
}
