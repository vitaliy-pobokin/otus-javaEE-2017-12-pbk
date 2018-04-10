package org.examples.pbk.otus.javaee.hw11.web;

import org.examples.pbk.otus.javaee.hw11.ejb.ItemJpaBean;
import org.examples.pbk.otus.javaee.hw11.entity.Item;
import org.examples.pbk.otus.javaee.hw11.exception.JpaBeanException;

import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.util.List;

@Named("shop")
@ApplicationScoped
public class Shop {
    @EJB
    private ItemJpaBean itemJpaBean;

    public Shop() {

    }

    public List<Item> getAllItems() {
        List<Item> items = null;
        try {
            items = itemJpaBean.getItems();
        } catch (JpaBeanException e) {
            e.printStackTrace();
        }
        return items;
    }
}
