package org.examples.pbk.otus.javaee.hw11.startup;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.examples.pbk.otus.javaee.hw11.ejb.CustomerJpaBean;
import org.examples.pbk.otus.javaee.hw11.ejb.ItemJpaBean;
import org.examples.pbk.otus.javaee.hw11.entity.Item;
import org.examples.pbk.otus.javaee.hw11.entity.Customer;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.io.File;
import java.net.URL;

@Singleton
@Startup
public class StartupBean {
    @EJB
    private ItemJpaBean itemBean;
    @EJB
    private CustomerJpaBean userBean;

    @PostConstruct
    void populateDatabase() {
        URL url = this.getClass().getClassLoader().getResource("/items/items.json");
        ObjectMapper mapper = new ObjectMapper();
        try {
            Item[] items = mapper.readValue(new File(url.toURI()), Item[].class);
            for (Item item : items) {
                item.setStock(5);
                itemBean.createItem(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
