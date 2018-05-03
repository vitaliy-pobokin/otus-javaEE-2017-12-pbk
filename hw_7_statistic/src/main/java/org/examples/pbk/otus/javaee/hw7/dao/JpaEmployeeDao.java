package org.examples.pbk.otus.javaee.hw7.dao;

import org.examples.pbk.otus.javaee.hw7.model.Employee;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

@ApplicationScoped
public class JpaEmployeeDao implements EmployeeDao {
    private Session session;

    @Override
    public List<Employee> findAll() {
        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        CriteriaQuery<Employee> criteria = builder.createQuery(Employee.class);
        criteria.from(Employee.class);
        return session.createQuery(criteria).getResultList();
    }

    @Override
    public List<Employee> findEmployees(DetachedCriteria dcr) {
        return dcr.getExecutableCriteria(getSession()).list();
    }

    @Override
    public Employee findById(long id) {
        return getSession().find(Employee.class, id);
    }

    @Override
    public void create(Employee employee) {
        getSession().persist(employee);
    }

    @Override
    public void update(Employee employee) {
        getSession().merge(employee);
    }

    @Override
    public void delete(long id) {
        getSession().remove(getSession().find(Employee.class, id));
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
