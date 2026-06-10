package com.example.campus_used_books.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "course_comments")
public class CourseComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String courseName; // 對應課程名稱
    private String professor; // 對應指導教授

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content; // 評論內容

    private String username; // 留評論的學生帳號
    private LocalDateTime createdAt; // 評價時間

    // 空建構子 (JPA 規範)
    public CourseComment() {
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        return;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getProfessor() {
        return professor;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}