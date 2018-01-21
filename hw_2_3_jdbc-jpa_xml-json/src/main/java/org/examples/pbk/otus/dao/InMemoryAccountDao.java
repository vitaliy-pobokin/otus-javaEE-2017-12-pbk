package org.examples.pbk.otus.dao;

import org.examples.pbk.otus.dao.qualifiers.InMemory;
import org.examples.pbk.otus.model.Account;

import javax.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@ApplicationScoped
@InMemory
public class InMemoryAccountDao implements AccountDao {

    Map<Long, Account> memory = new TreeMap<>();

    @Override
    public List<Account> findAll() {
        return new ArrayList<>(memory.values());
    }

    @Override
    public Account findById(long id) {
        return memory.get(id);
    }

    @Override
    public void create(Account account) {
        memory.put(account.getId(), account);
    }

    @Override
    public void update(Account account) {
        memory.replace(account.getId(), account);
    }

    @Override
    public void delete(long id) {
        memory.remove(id);
    }
}
