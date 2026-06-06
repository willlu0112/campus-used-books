package com.example.campus_used_books.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity // 🌟 這個標記非常重要！它會告訴系統：「請幫我把這個 Java 類別變成資料庫裡的一張表」
public class Book {

    @Id // 標記這是主鍵 (Primary Key)
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 讓 ID 自動從 1 遞增 (1, 2, 3...)
    private Long id;

    private String title;   // 書名
    private Integer price;  // 價格
    private String author;  // 作者

    // --- 下面是基礎的 Getters 和 Setters (讓系統能讀取和修改資料) ---
    
    public Book() {
        // JPA 規定必須要有一個空的建構子
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public Integer getPrice() { return price; }
    public void setPrice(Integer price) { this.price = price; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
}
