package org.examples.pbk.otus.javaee.hw11.ejb;

import org.examples.pbk.otus.javaee.hw11.entity.Customer;
import org.examples.pbk.otus.javaee.hw11.exception.JpaBeanException;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateful
public class CustomerJpaBean {

    @PersistenceContext(unitName = "persistence")
    private EntityManager em;

    private static final Logger logger = Logger.getLogger("CustomerJpaBean");

    public void createCustomer(Customer customer) {
        em.persist(customer);
        logger.log(Level.INFO, "Customer {0} persisted", customer.getUserId());
    }

    public Customer findById(long userId) throws JpaBeanException {
        Customer customer = em.find(Customer.class, userId);
        if (customer == null) {
            throw new JpaBeanException("Couldn't find customer with id " + userId);
        }
        return customer;
    }
}
