package org.examples.pbk.otus.javaee.hw11.web;

import org.examples.pbk.otus.javaee.hw11.ejb.CustomerJpaBean;
import org.examples.pbk.otus.javaee.hw11.ejb.OrderJpaBean;
import org.examples.pbk.otus.javaee.hw11.entity.Customer;
import org.examples.pbk.otus.javaee.hw11.entity.Item;
import org.examples.pbk.otus.javaee.hw11.exception.JpaBeanException;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Named("cart")
@SessionScoped
public class ShoppingCart implements Serializable {

    private Map<Long, ShoppingCartItem> cart;
    private int totalItems = 0;

    @EJB
    private OrderJpaBean orderBean;

    @Inject
    private Login loginBean;

    @Inject
    private Error errorBeam;

    public ShoppingCart() {
        this.cart = new HashMap<>();
    }

    public void add(long itemId, Item item) {
        if (cart.containsKey(itemId)) {
            cart.get(itemId).incrementQuantity();
        } else {
            cart.put(itemId, new ShoppingCartItem(item));
        }
        totalItems++;
    }

    public void add(long itemId, Item item, int quantity) {
        if (cart.containsKey(itemId)) {
            cart.get(itemId).incrementQuantityBy(quantity);
        } else {
            cart.put(itemId, new ShoppingCartItem(item, quantity));
        }
        totalItems += quantity;
    }

    public void remove(long itemId) {
        if (cart.containsKey(itemId)) {
            totalItems -= cart.get(itemId).getItemQuantity();
            cart.remove(itemId);
        }
    }

    public List<ShoppingCartItem> getCartItems() {
        List<ShoppingCartItem> items = new ArrayList<>();
        items.addAll(cart.values());
        return items;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public BigDecimal getTotal() {
        BigDecimal total = new BigDecimal(0);
        List<ShoppingCartItem> items = getCartItems();
        if (items != null && items.size() > 0) {
            for (ShoppingCartItem item : items) {
                BigDecimal itemPrice = item.getItem().getPrice();
                int itemQuantity = item.getItemQuantity();
                total = total.add(itemPrice.multiply(new BigDecimal(itemQuantity)));
            }
        }
        return total;
    }

    public void clear() {
        this.cart = new HashMap<>();
        this.totalItems = 0;
    }

    public String buy() {
        try {
            List<ShoppingCartItem> items = getCartItems();
            if (items != null && items.size() > 0) {
                if (loginBean.isCustomerAuthenticated()) {
                    orderBean.createOrder(loginBean.getAuthenticatedCustomer(), this);
                    clear();
                    return "index.xhtml";
                } else {
                    return "login.xhtml";
                }
            }
        } catch (JpaBeanException e) {
            errorBeam.setMessage(e.getMessage());
        }
        return "error.xhtml";
    }
}
