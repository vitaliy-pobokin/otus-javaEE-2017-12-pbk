package org.examples.pbk.otus.javaee.hw12.service;

import org.examples.pbk.otus.javaee.hw12.dao.AccountDao;
import org.examples.pbk.otus.javaee.hw12.model.Account;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class JpaAccountService implements AccountService {

    @Inject
    private AccountDao dao;

    public JpaAccountService() {
    }

    @Override
    public List<Account> findAll() {
        return dao.findAll();
    }

    @Override
    public Account findById(long id) {
        return dao.findById(id);
    }

    @Override
    public Account findByUsername(String username) {
        return dao.findByUsername(username);
    }

    @Override
    public void create(Account account) {
        dao.create(account);
    }

    @Override
    public void update(Account account) {
        dao.update(account);
    }

    @Override
    public void delete(long id) {
        dao.delete(id);
    }
}
