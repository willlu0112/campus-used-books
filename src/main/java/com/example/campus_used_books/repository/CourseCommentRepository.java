package com.example.campus_used_books.repository;

import com.example.campus_used_books.entity.CourseComment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CourseCommentRepository extends JpaRepository<CourseComment, Long> {

    // 🌟 核心查詢：根據「課程名稱」與「教授名字」精準抓出所有評論，並讓最新的評論排在最前面
    List<CourseComment> findByCourseNameAndProfessorOrderByCreatedAtDesc(String courseName, String professor);
}