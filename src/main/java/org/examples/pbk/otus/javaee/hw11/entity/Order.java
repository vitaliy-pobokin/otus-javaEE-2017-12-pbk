package org.examples.pbk.otus.javaee.hw11.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "STORE_ORDER")
public class Order {
    @Id
    private long id;
    @ManyToOne
    private User user;
    @ManyToMany
    private List<Item> items;
    private BigDecimal total;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id == order.id &&
                Objects.equals(user, order.user) &&
                Objects.equals(items, order.items) &&
                Objects.equals(total, order.total);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, user, items, total);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", user=" + user +
                ", items=" + items +
                ", total=" + total +
                '}';
    }
}
