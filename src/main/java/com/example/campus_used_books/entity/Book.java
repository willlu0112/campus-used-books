package com.example.campus_used_books.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title; // 書名
    private Integer price; // 價格
    private String author; // 作者
    private String courseName; // 🌟 新增：對應課程
    private String professor; // 🌟 新增：指導教授
    private String ownerUsername; // 🌟 新增：這本書的上架者帳號名稱

    // --- 下面是基礎的 Getters 和 Setters ---

    public Book() {
        // JPA 規定必須要有一個空的建構子
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    // 🌟 新增 courseName 的 Getter / Setter
    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    // 🌟 新增 professor 的 Getter / Setter
    public String getProfessor() {
        return professor;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }

    public String getOwnerUsername() {
        return ownerUsername;
    }

    public void setOwnerUsername(String ownerUsername) {
        this.ownerUsername = ownerUsername;
    }

    private String coverImage; // 🌟 新增：儲存書本封面的圖片檔名

    // 🌟 新增 coverImage 的 Getter / Setter
    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }
}