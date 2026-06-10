package com.example.campus_used_books.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "USER_ORDER_ITEMS")
public class UserOrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private UserOrder userOrder;

    private String bookTitle;
    private int price;
    private String coverImage;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public UserOrder getUserOrder() { return userOrder; }
    public void setUserOrder(UserOrder userOrder) { this.userOrder = userOrder; }
    public String getBookTitle() { return bookTitle; }
    public void setBookTitle(String bookTitle) { this.bookTitle = bookTitle; }
    public int getPrice() { return price; }
    public void setPrice(int price) { this.price = price; }
    public String getCoverImage() { return coverImage; }
    public void setCoverImage(String coverImage) { this.coverImage = coverImage; }
}
