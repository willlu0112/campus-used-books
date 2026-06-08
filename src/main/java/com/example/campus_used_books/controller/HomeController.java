package com.example.campus_used_books.controller;

import com.example.campus_used_books.entity.Book;
import com.example.campus_used_books.entity.User;
import com.example.campus_used_books.repository.BookRepository;
import com.example.campus_used_books.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // 1. 首頁
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("books", bookRepository.findAll());
        return "index";
    }

    // 2. 前往獨立的「上架二手書」頁面
    @GetMapping("/add-page")
    public String showAddPage() {
        return "add-book";
    }

    // 3. 處理新增書籍表單
    @PostMapping("/addBook")
    public String addBook(Book book) {
        bookRepository.save(book);
        return "redirect:/";
    }

    // 4. 前往「會員登入」頁面
    @GetMapping("/login-page")
    public String loginPage() {
        return "login"; // 導向 templates/login.html
    }

    // 5. 前往「會員註冊」頁面
    @GetMapping("/register-page")
    public String registerPage() {
        return "register"; // 導向 templates/register.html
    }

    // 6. 處理註冊請求：填寫完按完成後，自動回到登入頁面
    @PostMapping("/register")
    public String register(@RequestParam String username, @RequestParam String password) {
        User user = new User();
        user.setUsername(username);
        // 🌟 註冊時，密碼一定要透過加密器加密後才能存進 H2 資料庫
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);

        return "redirect:/login-page"; // 💡 註冊完成後，自動引導回到登入頁面！
    }

    // 7. 刪除、編輯、更新功能維持原樣
    @PostMapping("/deleteBook")
    public String deleteBook(@RequestParam Long id) {
        bookRepository.deleteById(id);
        return "redirect:/";
    }

    @GetMapping("/editBook")
    public String editBook(@RequestParam Long id, Model model) {
        Book book = bookRepository.findById(id).get();
        model.addAttribute("book", book);
        return "edit";
    }

    @PostMapping("/updateBook")
    public String updateBook(Book book) {
        bookRepository.save(book);
        return "redirect:/";
    }
}