package org.examples.pbk.otus.javaee.hw5.dao;

import org.examples.pbk.otus.javaee.hw5.model.Account;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@ApplicationScoped
public class JpaAccountDao implements AccountDao {

    private EntityManager entityManager;

    @Override
    public List<Account> findAll() {
        CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Account> criteria = builder.createQuery(Account.class);
        criteria.from(Account.class);
        return entityManager.createQuery(criteria).getResultList();
    }

    @Override
    public Account findById(long id) {
        return getEntityManager().find(Account.class, id);
    }

    @Override
    public Account findByUsername(String username) {
        CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Account> criteria = builder.createQuery(Account.class);
        Root<Account> from = criteria.from(Account.class);
        criteria.where(builder.equal(from.get("username"), username));
        TypedQuery<Account> query = getEntityManager().createQuery(criteria);
        return query.getSingleResult();
    }

    @Override
    public void create(Account account) {
        getEntityManager().persist(account);
    }

    @Override
    public void update(Account account) {
        getEntityManager().merge(account);
    }

    @Override
    public void delete(long id) {
        getEntityManager().remove(getEntityManager().find(Account.class, id));
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    private EntityManager getEntityManager() {
        if (entityManager == null) {
            throw new RuntimeException("EntityManager wasn't set");
        }
        return entityManager;
    }
}
