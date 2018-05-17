package org.examples.pbk.otus.javaee.hw12.cdi;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class JpaInvocationTimeMarkerDao implements InvocationTimeMarkerDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(InvocationTimeMarker marker) {
        em.persist(marker);
    }

    @Override
    public List<AverageInvocationTimeDTO> getAverageInvocationTime() {
        return em.createQuery(
                "SELECT new org.examples.pbk.otus.javaee.hw12.cdi.AverageInvocationTimeDTO(" +
                        "m.className, m.methodName, AVG (m.invocationTime)) " +
                        "FROM org.examples.pbk.otus.javaee.hw12.cdi.InvocationTimeMarker m " +
                        "GROUP BY m.className, m.methodName", AverageInvocationTimeDTO.class).getResultList();
    }
}
