package org.examples.pbk.otus.javaee.hw6.dao;

import org.examples.pbk.otus.javaee.hw6.model.Department;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

@ApplicationScoped
public class JpaDepartmentDao implements DepartmentDao{
    private EntityManager entityManager;

    @Override
    public List<Department> findAll() {
        CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Department> criteria = builder.createQuery(Department.class);
        criteria.from(Department.class);
        return entityManager.createQuery(criteria).getResultList();
    }

    @Override
    public Department findById(long id) {
        return getEntityManager().find(Department.class, id);
    }

    @Override
    public void create(Department department) {
        getEntityManager().persist(department);
    }

    @Override
    public void update(Department department) {
        getEntityManager().merge(department);
    }

    @Override
    public void delete(long id) {
        getEntityManager().remove(getEntityManager().find(Department.class, id));
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
