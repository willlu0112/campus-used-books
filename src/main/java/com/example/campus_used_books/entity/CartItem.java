package com.example.campus_used_books.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "CART_ITEMS")
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String buyerUsername;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getBuyerUsername() { return buyerUsername; }
    public void setBuyerUsername(String buyerUsername) { this.buyerUsername = buyerUsername; }
    public Book getBook() { return book; }
    public void setBook(Book book) { this.book = book; }
}
