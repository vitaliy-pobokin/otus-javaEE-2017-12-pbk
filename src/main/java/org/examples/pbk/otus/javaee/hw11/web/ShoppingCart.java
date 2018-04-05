package org.examples.pbk.otus.javaee.hw11.web;

import org.examples.pbk.otus.javaee.hw11.entity.Item;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Named("cart")
@SessionScoped
public class ShoppingCart implements Serializable {

    private Map<Long, ShoppingCartItem> cart;
    private int totalItems = 0;

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

    public int getTotalItems() {
        return totalItems;
    }
}
