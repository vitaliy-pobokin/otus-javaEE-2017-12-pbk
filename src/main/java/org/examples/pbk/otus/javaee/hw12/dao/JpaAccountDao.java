package org.examples.pbk.otus.javaee.hw12.dao;

import org.examples.pbk.otus.javaee.hw12.model.Account;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Stateless
public class JpaAccountDao implements AccountDao {

    @PersistenceContext(unitName = "persistence")
    private EntityManager em;

    @Override
    public List<Account> findAll() {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Account> criteria = builder.createQuery(Account.class);
        criteria.from(Account.class);
        return em.createQuery(criteria).getResultList();
    }

    @Override
    public Account findById(long id) {
        return em.find(Account.class, id);
    }

    @Override
    public Account findByUsername(String username) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Account> criteria = builder.createQuery(Account.class);
        Root<Account> from = criteria.from(Account.class);
        criteria.where(builder.equal(from.get("username"), username));
        TypedQuery<Account> query = em.createQuery(criteria);
        return query.getSingleResult();
    }

    @Override
    public void create(Account account) {
        em.persist(account);
    }

    @Override
    public void update(Account account) {
        em.merge(account);
    }

    @Override
    public void delete(long id) {
        em.remove(em.find(Account.class, id));
    }
}
