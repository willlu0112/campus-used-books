package com.example.campus_used_books.controller;

import com.example.campus_used_books.entity.Book;
import com.example.campus_used_books.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;

@Controller
public class HomeController {

    @Autowired
    private BookRepository bookRepository;

    @GetMapping("/")
    public String home(Model model) { 
        model.addAttribute("books", bookRepository.findAll()); 
        return "index"; 
    }

    @PostMapping("/addBook")
    public String addBook(Book book) {
        bookRepository.save(book);
        return "redirect:/";
    }
}