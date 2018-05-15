package org.examples.pbk.otus.javaee.hw12.dao;

import org.examples.pbk.otus.javaee.hw12.model.Department;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

@Stateless
public class JpaDepartmentDao implements DepartmentDao{

    @PersistenceContext(unitName = "persistence")
    private EntityManager em;

    @Override
    public List<Department> findAll() {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Department> criteria = builder.createQuery(Department.class);
        criteria.from(Department.class);
        return em.createQuery(criteria).getResultList();
    }

    @Override
    public Department findById(long id) {
        return em.find(Department.class, id);
    }

    @Override
    public void create(Department department) {
        em.persist(department);
    }

    @Override
    public void update(Department department) {
        em.merge(department);
    }

    @Override
    public void delete(long id) {
        em.remove(em.find(Department.class, id));
    }
}
