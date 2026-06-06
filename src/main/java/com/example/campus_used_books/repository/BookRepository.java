package com.example.campus_used_books.repository;

import com.example.campus_used_books.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
