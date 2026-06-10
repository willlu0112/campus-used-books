package com.example.campus_used_books.controller;

import com.example.campus_used_books.entity.CourseComment;
import com.example.campus_used_books.repository.CourseCommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class CourseCommentController {

    @Autowired
    private CourseCommentRepository commentRepository;

    // 1. 查看特定課程與教授的評論頁面 (訪客也能看)
    @GetMapping("/comments")
    public String showComments(
            @RequestParam String courseName,
            @RequestParam String professor,
            Model model) {

        // 撈出這門課的所有評論
        List<CourseComment> comments = commentRepository.findByCourseNameAndProfessorOrderByCreatedAtDesc(courseName,
                professor);

        model.addAttribute("comments", comments);
        model.addAttribute("courseName", courseName);
        model.addAttribute("professor", professor);

        return "comments"; // 導向 templates/comments.html
    }

    // 2. 處理新增評論的請求 (必須登入，Spring Security 會防守)
    @PostMapping("/comments/add")
    public String addComment(
            @RequestParam String courseName,
            @RequestParam String professor,
            @RequestParam String content,
            Principal principal) {

        if (principal != null && content != null && !content.trim().isEmpty()) {
            CourseComment comment = new CourseComment();
            comment.setCourseName(courseName);
            comment.setProfessor(professor);
            comment.setContent(content);
            comment.setUsername(principal.getName()); // 自動綁定留言者
            comment.setCreatedAt(LocalDateTime.now()); // 紀錄當下時間

            commentRepository.save(comment);
        }

        // 留言完成後，重導向重新整理原本的評論頁面
        return "redirect:/comments?courseName=" + courseName + "&professor=" + professor;
    }
}