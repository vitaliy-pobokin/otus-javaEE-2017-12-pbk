package org.examples.pbk.otus.javaee.hw11.ejb;

import org.examples.pbk.otus.javaee.hw11.entity.Item;
import org.examples.pbk.otus.javaee.hw11.exception.JpaBeanException;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateful
public class ItemJpaBean {

    @PersistenceContext(unitName = "persistence")
    private EntityManager em;

    private static final Logger logger = Logger.getLogger("ItemJpaBean");

    public void createItem(Item item) {
        em.persist(item);
        logger.log(Level.INFO, "Item {0} persisted", item.getItemId());
    }

    public List<Item> getItems() throws JpaBeanException {
        List<Item> items = em.createNamedQuery("selectAllItems").getResultList();
        if (items == null || items.size() == 0) {
            throw new JpaBeanException("Couldn't find items");
        }
        return items;
    }

    public Item findById(long itemId) throws JpaBeanException {
        Item item = em.find(Item.class, itemId);
        if (item == null) {
            throw new JpaBeanException("Couldn't find item with id " + itemId);
        }
        return item;
    }

    public void updateStock(long itemId, int quantity) throws JpaBeanException {
        try {
            Item item = findById(itemId);
            if (item != null) {
                int available = item.getStock();
                if (available - quantity >= 0) {
                    item.setStock(available - quantity);
                    em.merge(item);
                } else {
                    throw new JpaBeanException("Not enough " + item.getName() + " in stock");
                }
            }
        } catch (JpaBeanException e) {
            throw new JpaBeanException("Exception during stock update: " + e.getMessage());
        }
    }
}
