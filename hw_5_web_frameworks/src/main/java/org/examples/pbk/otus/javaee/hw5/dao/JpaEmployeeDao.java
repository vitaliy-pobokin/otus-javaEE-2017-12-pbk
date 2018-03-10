package org.examples.pbk.otus.javaee.hw5.dao;

import org.examples.pbk.otus.javaee.hw5.model.Employee;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

@ApplicationScoped
public class JpaEmployeeDao implements EmployeeDao {
    private EntityManager entityManager;

    @Override
    public List<Employee> findAll() {
        CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Employee> criteria = builder.createQuery(Employee.class);
        criteria.from(Employee.class);
        return entityManager.createQuery(criteria).getResultList();
    }

    @Override
    public Employee findById(long id) {
        return getEntityManager().find(Employee.class, id);
    }

    @Override
    public void create(Employee employee) {
        getEntityManager().persist(employee);
    }

    @Override
    public void update(Employee employee) {
        getEntityManager().merge(employee);
    }

    @Override
    public void delete(long id) {
        getEntityManager().remove(getEntityManager().find(Employee.class, id));
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
