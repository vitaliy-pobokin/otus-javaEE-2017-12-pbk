package org.examples.pbk.otus.javaee.hw7.dao;

import org.examples.pbk.otus.javaee.hw7.model.Account;
import org.hibernate.Session;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@ApplicationScoped
public class JpaAccountDao implements AccountDao {

    private Session session;

    @Override
    public List<Account> findAll() {
        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        CriteriaQuery<Account> criteria = builder.createQuery(Account.class);
        criteria.from(Account.class);
        return session.createQuery(criteria).getResultList();
    }

    @Override
    public Account findById(long id) {
        return getSession().find(Account.class, id);
    }

    @Override
    public Account findByUsername(String username) {
        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        CriteriaQuery<Account> criteria = builder.createQuery(Account.class);
        Root<Account> from = criteria.from(Account.class);
        criteria.where(builder.equal(from.get("username"), username));
        TypedQuery<Account> query = getSession().createQuery(criteria);
        return query.getSingleResult();
    }

    @Override
    public void create(Account account) {
        getSession().persist(account);
    }

    @Override
    public void update(Account account) {
        getSession().merge(account);
    }

    @Override
    public void delete(long id) {
        getSession().remove(getSession().find(Account.class, id));
    }

    public void setSession(Session session) {
        this.session = session;
    }

    private Session getSession() {
        if (session == null) {
            throw new RuntimeException("Session wasn't set");
        }
        return session;
    }
}
