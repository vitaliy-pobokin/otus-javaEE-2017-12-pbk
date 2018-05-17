package org.examples.pbk.otus.javaee.hw12.dao;

import org.examples.pbk.otus.javaee.hw12.model.Employee;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

@Stateless
public class JpaEmployeeDao implements EmployeeDao {

    @PersistenceContext(unitName = "persistence")
    private EntityManager em;

    @Override
    public List<Employee> findAll() {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Employee> criteria = builder.createQuery(Employee.class);
        criteria.from(Employee.class);
        return em.createQuery(criteria).getResultList();
    }

    @Override
    public List<Employee> findEmployees(DetachedCriteria dcr) {
        return dcr.getExecutableCriteria(em.unwrap(Session.class)).list();
    }

    @Override
    public Employee findById(long id) {
        return em.find(Employee.class, id);
    }

    @Override
    public void create(Employee employee) {
        em.persist(employee);
    }

    @Override
    public void update(Employee employee) {
        em.merge(employee);
    }

    @Override
    public void delete(long id) {
        em.remove(em.find(Employee.class, id));
    }
}
