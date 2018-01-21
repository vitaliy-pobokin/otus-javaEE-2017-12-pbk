package org.examples.pbk.otus.dao;

import org.examples.pbk.otus.model.Account;

import java.util.List;

public interface AccountDao {
    List<Account> findAll();
    Account findById(long id);
    void create(Account account);
    void update(Account account);
    void delete(long id);
}
