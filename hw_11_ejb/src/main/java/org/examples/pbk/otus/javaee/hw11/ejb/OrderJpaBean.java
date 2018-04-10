package org.examples.pbk.otus.javaee.hw11.ejb;

import org.examples.pbk.otus.javaee.hw11.entity.Customer;
import org.examples.pbk.otus.javaee.hw11.entity.Item;
import org.examples.pbk.otus.javaee.hw11.entity.Order;
import org.examples.pbk.otus.javaee.hw11.entity.OrderPosition;
import org.examples.pbk.otus.javaee.hw11.exception.JpaBeanException;
import org.examples.pbk.otus.javaee.hw11.web.ShoppingCart;
import org.examples.pbk.otus.javaee.hw11.web.ShoppingCartItem;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateful
@Transactional
public class OrderJpaBean {
    @PersistenceContext(unitName = "persistence")
    private EntityManager em;

    @EJB
    private ItemJpaBean itemBean;

    private static Logger logger = Logger.getLogger("OrderJpaBean");

    public void createOrder(Customer customer, ShoppingCart cart) throws JpaBeanException {
        Order order = new Order();
        try {
            order.setCustomer(customer);
            em.persist(order);

            for (ShoppingCartItem cartItem : cart.getCartItems()) {
                OrderPosition orderPosition = new OrderPosition();
                Item item = cartItem.getItem();
                orderPosition.setItem(item);
                int itemQuantity = cartItem.getItemQuantity();
                orderPosition.setQuantity(itemQuantity);
                orderPosition.setOrder(order);
                order.addOrderPosition(orderPosition);
                itemBean.updateStock(item.getItemId(), itemQuantity);
                em.persist(orderPosition);
            }
        } catch (Exception e) {
            throw new JpaBeanException("Error creating order: " + order.getOrderId() + ". " + e.getMessage());
        }
        logger.log(Level.INFO, "Order {0} persisted", order.getOrderId());
    }


}
