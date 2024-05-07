package org.example.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private User user;

    @Column(name = "order_date")
    private String orderDate;

    @Column(name = "status")
    private String status;

    @Column(name = "total_cost")
    private int totalCost;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems;

    public Order() {
    }

    public Order(String orderDate, String status, int totalCost, User user, List<OrderItem> orderItems) {
        this.orderDate = orderDate;
        this.status = status;
        this.totalCost = totalCost;
        this.user = user;
        this.orderItems = orderItems;
    }

    public Order(Long id, String orderDate, String status, int totalCost, User user, List<OrderItem> orderItems) {
        this.id = id;
        this.orderDate = orderDate;
        this.status = status;
        this.totalCost = totalCost;
        this.user = user;
        this.orderItems = orderItems;
    }

    public int getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(int totalCost) {
        this.totalCost = totalCost;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}