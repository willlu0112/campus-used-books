package com.example.campus_used_books.repository;

import com.example.campus_used_books.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    // 🌟 1. 根據書名模糊搜尋 (IgnoringCase 代表不區分英文大小寫)
    List<Book> findByTitleContainingIgnoreCase(String title);

    // 🌟 2. 根據課程名稱模糊搜尋
    List<Book> findByCourseNameContainingIgnoreCase(String courseName);

    // 🌟 3. 根據教授名稱模糊搜尋
    List<Book> findByProfessorContainingIgnoreCase(String professor);
}
