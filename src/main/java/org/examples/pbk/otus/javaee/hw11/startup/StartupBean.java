package org.examples.pbk.otus.javaee.hw11.startup;

import org.examples.pbk.otus.javaee.hw11.ejb.CustomerJpaBean;
import org.examples.pbk.otus.javaee.hw11.ejb.ItemJpaBean;
import org.examples.pbk.otus.javaee.hw11.entity.Item;
import org.examples.pbk.otus.javaee.hw11.entity.Customer;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.math.BigDecimal;

@Singleton
@Startup
public class StartupBean {
    @EJB
    private ItemJpaBean itemBean;
    @EJB
    private CustomerJpaBean userBean;

    @PostConstruct
    void populateDatabase() {
        itemBean.createItem(new Item(
                "computer",
                new BigDecimal("10.01"),
                "powerful computer",
                2
        ));
        userBean.createCustomer(new Customer(
                "pbk",
                "111qqq222",
                "Smolensk"));
    }
}
