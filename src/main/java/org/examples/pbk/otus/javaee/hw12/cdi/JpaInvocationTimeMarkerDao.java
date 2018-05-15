package org.examples.pbk.otus.javaee.hw12.cdi;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class JpaInvocationTimeMarkerDao implements InvocationTimeMarkerDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(InvocationTimeMarker marker) {
        em.persist(marker);
    }
}
