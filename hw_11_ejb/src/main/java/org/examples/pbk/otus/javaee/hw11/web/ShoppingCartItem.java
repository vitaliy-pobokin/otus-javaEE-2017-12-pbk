package org.examples.pbk.otus.javaee.hw11.web;

import org.examples.pbk.otus.javaee.hw11.entity.Item;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Objects;

@Named
@SessionScoped
public class ShoppingCartItem implements Serializable {
    private Item item;
    private int itemQuantity;

    public ShoppingCartItem() {
    }

    public ShoppingCartItem(Item item) {
        this.item = item;
        this.itemQuantity = 1;
    }

    public ShoppingCartItem(Item item, int itemQuantity) {
        this.item = item;
        this.itemQuantity = itemQuantity;
    }

    public void incrementQuantity() {
        itemQuantity++;
    }

    public void incrementQuantityBy(int quantity) {
        itemQuantity += quantity;
    }

    public void decrementQuantity() {
        if (itemQuantity > 1) {
            itemQuantity--;
        }
    }

    public Item getItem() {
        return item;
    }

    public int getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(int itemQuantity) {
        this.itemQuantity = itemQuantity;
    }
}
