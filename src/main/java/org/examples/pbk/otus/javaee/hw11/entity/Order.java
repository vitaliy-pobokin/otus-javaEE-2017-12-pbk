package org.examples.pbk.otus.javaee.hw11.entity;

import javax.persistence.*;
import java.math.BigDecimal;
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
    private BigDecimal total;

    public Order() {
    }

    public Order(Customer customer, List<OrderPosition> positions) {
        this.customer = customer;
        this.positions = positions;
        this.total = countTotal(positions);
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

    public BigDecimal getTotal() {
        return total;
    }

    private BigDecimal countTotal(List<OrderPosition> positions) {
        BigDecimal total = BigDecimal.ZERO;
        if (positions != null && positions.size() != 0) {
            for (OrderPosition position : positions) {
                BigDecimal itemPrice = position.getItem().getPrice();
                int itemQuantity = position.getQuantity();
                total.add(itemPrice.multiply(new BigDecimal(itemQuantity)));
            }
        }
        return total;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(orderId, order.orderId) &&
                Objects.equals(customer, order.customer) &&
                Objects.equals(positions, order.positions) &&
                Objects.equals(total, order.total);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, customer, positions, total);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + orderId +
                ", customer=" + customer +
                ", items=" + positions +
                ", total=" + total +
                '}';
    }
}
