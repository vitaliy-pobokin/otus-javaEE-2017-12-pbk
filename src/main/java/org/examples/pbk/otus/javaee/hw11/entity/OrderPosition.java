package org.examples.pbk.otus.javaee.hw11.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "STORE_ORDER_POSITION")
public class OrderPosition {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private long orderPositionId;
    @ManyToOne
    @JoinColumn(name = "ORDERID", nullable = false)
    private Order order;
    @ManyToOne
    private Item item;
    private int quantity;

    public OrderPosition() {
    }

    public OrderPosition(Item item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }

    public long getOrderPositionId() {
        return orderPositionId;
    }

    public void setOrderPositionId(long orderPositionId) {
        this.orderPositionId = orderPositionId;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderPosition that = (OrderPosition) o;
        return orderPositionId == that.orderPositionId &&
                quantity == that.quantity &&
                Objects.equals(item, that.item);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderPositionId, item, quantity);
    }

    @Override
    public String toString() {
        return "OrderPosition{" +
                "orderPositionId=" + orderPositionId +
                ", item=" + item +
                ", quantity=" + quantity +
                '}';
    }
}
