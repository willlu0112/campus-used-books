package com.example.campus_used_books.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "USER_ORDERS")
public class UserOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String buyerUsername;
    private int totalPrice;
    private LocalDateTime orderDate;

    @OneToMany(mappedBy = "userOrder", cascade = CascadeType.ALL)
    private List<UserOrderItem> items;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getBuyerUsername() { return buyerUsername; }
    public void setBuyerUsername(String buyerUsername) { this.buyerUsername = buyerUsername; }
    public int getTotalPrice() { return totalPrice; }
    public void setTotalPrice(int totalPrice) { this.totalPrice = totalPrice; }
    public LocalDateTime getOrderDate() { return orderDate; }
    public void setOrderDate(LocalDateTime orderDate) { this.orderDate = orderDate; }
    public List<UserOrderItem> getItems() { return items; }
    public void setItems(List<UserOrderItem> items) { this.items = items; }
}
