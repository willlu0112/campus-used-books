package com.example.campus_used_books.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "users") // H2 資料庫中，User 常是關鍵字，所以資料表命名為 users
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username; // 登入帳號

    @Column(nullable = false)
    private String password; // 登入密碼（會加密儲存）

    // Getter 和 Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}