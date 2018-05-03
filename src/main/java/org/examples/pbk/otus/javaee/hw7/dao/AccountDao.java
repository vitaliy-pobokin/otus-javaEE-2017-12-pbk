package org.examples.pbk.otus.javaee.hw7.dao;

import org.examples.pbk.otus.javaee.hw7.model.Account;

import java.util.List;

public interface AccountDao {
    List<Account> findAll();
    Account findById(long id);
    Account findByUsername(String username);
    void create(Account account);
    void update(Account account);
    void delete(long id);
}
