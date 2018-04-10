package org.examples.pbk.otus.javaee.hw11.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "STORE_ORDER")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private long orderId;
    @ManyToOne
    private Customer customer;
    @OneToMany(mappedBy = "order")
    private List<OrderPosition> positions;

    public Order() {
        positions = new ArrayList<>();
    }

    public Order(Customer customer) {
        this();
        this.customer = customer;
    }

    public Order(Customer customer, List<OrderPosition> positions) {
        this.customer = customer;
        this.positions = positions;
    }

    public void addOrderPosition(OrderPosition orderPosition) {
        positions.add(orderPosition);
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long id) {
        this.orderId = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<OrderPosition> getPositions() {
        return positions;
    }

    public void setPositions(List<OrderPosition> items) {
        this.positions = items;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return orderId == order.orderId &&
                Objects.equals(customer, order.customer) &&
                Objects.equals(positions, order.positions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, customer, positions);
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", customer=" + customer +
                ", positions=" + positions +
                '}';
    }
}
