package org.examples.pbk.otus.javaee.hw5.service;

import org.examples.pbk.otus.javaee.hw5.EMF;
import org.examples.pbk.otus.javaee.hw5.dao.JpaAccountDao;
import org.examples.pbk.otus.javaee.hw5.model.Account;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

@ApplicationScoped
public class JpaAccountService implements AccountService {

    private JpaAccountDao dao;

    public JpaAccountService() {
        this.dao = new JpaAccountDao();
    }

    @Override
    public List<Account> findAll() {
        return runInTransaction(entityManager -> {
            dao.setEntityManager(entityManager);
            return dao.findAll();
        });
    }

    @Override
    public Account findById(long id) {
        return runInTransaction(entityManager -> {
            dao.setEntityManager(entityManager);
            return dao.findById(id);
        });
    }

    @Override
    public Account findByUsername(String username) {
        return runInTransaction(entityManager -> {
            dao.setEntityManager(entityManager);
            return dao.findByUsername(username);
        });
    }

    @Override
    public void create(Account account) {
        runInTransactionWithoutResult(entityManager -> {
            dao.setEntityManager(entityManager);
            dao.create(account);
        });
    }

    @Override
    public void update(Account account) {
        runInTransactionWithoutResult(entityManager -> {
            dao.setEntityManager(entityManager);
            dao.update(account);
        });
    }

    @Override
    public void delete(long id) {
        runInTransactionWithoutResult(entityManager -> {
            dao.setEntityManager(entityManager);
            dao.delete(id);
        });
    }

    private <R> R runInTransaction(Function<EntityManager, R> function) {
        EntityManager em = EMF.createEntityManager();
        em.getTransaction().begin();
        R result = function.apply(em);
        em.getTransaction().commit();
        em.close();
        return result;
    }

    private void runInTransactionWithoutResult(Consumer<EntityManager> consumer) {
        EntityManager em = EMF.createEntityManager();
        em.getTransaction().begin();
        consumer.accept(em);
        em.getTransaction().commit();
        em.close();
    }
}
