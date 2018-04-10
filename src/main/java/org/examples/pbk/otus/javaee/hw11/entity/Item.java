package org.examples.pbk.otus.javaee.hw11.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "STORE_ITEM")
@NamedQuery(
        name = "selectAllItems",
        query = "SELECT i FROM Item i ORDER BY itemId")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private long itemId;
    private String name;
    private BigDecimal price;
    @Column(length = 2048)
    private String description;
    private String imageLink;
    private int stock;

    public Item() {
    }

    public Item(String name, BigDecimal price, String description, int stock) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.stock = stock;
    }

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long id) {
        this.itemId = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return itemId == item.itemId &&
                stock == item.stock &&
                Objects.equals(name, item.name) &&
                Objects.equals(price, item.price) &&
                Objects.equals(description, item.description) &&
                Objects.equals(imageLink, item.imageLink);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemId, name, price, description, imageLink, stock);
    }

    @Override
    public String toString() {
        return "Item{" +
                "itemId=" + itemId +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", imageLink='" + imageLink + '\'' +
                ", stock=" + stock +
                '}';
    }
}
