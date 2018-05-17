package org.examples.pbk.otus.javaee.hw12.service;

import org.examples.pbk.otus.javaee.hw12.model.Account;

import java.util.List;

public interface AccountService {
    List<Account> findAll();
    Account findById(long id);
    Account findByUsername(String username);
    void create(Account account);
    void update(Account account);
    void delete(long id);
}
