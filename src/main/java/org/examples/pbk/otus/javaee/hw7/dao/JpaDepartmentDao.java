package org.examples.pbk.otus.javaee.hw7.dao;

import org.examples.pbk.otus.javaee.hw7.model.Department;
import org.hibernate.Session;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

@ApplicationScoped
public class JpaDepartmentDao implements DepartmentDao{
    private Session session;

    @Override
    public List<Department> findAll() {
        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        CriteriaQuery<Department> criteria = builder.createQuery(Department.class);
        criteria.from(Department.class);
        return session.createQuery(criteria).getResultList();
    }

    @Override
    public Department findById(long id) {
        return getSession().find(Department.class, id);
    }

    @Override
    public void create(Department department) {
        getSession().persist(department);
    }

    @Override
    public void update(Department department) {
        getSession().merge(department);
    }

    @Override
    public void delete(long id) {
        getSession().remove(getSession().find(Department.class, id));
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
