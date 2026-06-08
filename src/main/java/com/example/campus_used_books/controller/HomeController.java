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

    // 💡 修改 3：處理新增書籍表單（自動綁定目前登入的人為擁有者）
    @PostMapping("/addBook")
    public String addBook(Book book, java.security.Principal principal) {
        // principal.getName() 可以直接抓到目前登入的使用者帳號 (例如 "apple")
        if (principal != null) {
            book.setOwnerUsername(principal.getName());
        }
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

    // 💡 修改 7-1：前往編輯頁面（後端嚴格防守攔截）
    @GetMapping("/editBook")
    public String editBook(@RequestParam Long id, Model model, java.security.Principal principal) {
        Book book = bookRepository.findById(id).get();

        // 🌟 核心防守邏輯：比對目前登入者 與 書本擁有者
        if (principal == null || !book.getOwnerUsername().equals(principal.getName())) {
            // 如果沒登入，或是登入的人不是上架者，直接不給看，退回首頁！
            return "redirect:/";
        }

        model.addAttribute("book", book);
        return "edit";
    }

    // 💡 修改 7-2：處理更新書籍（確保即便是發送 POST，也只有本人能改）
    @PostMapping("/updateBook")
    public String updateBook(Book book, java.security.Principal principal) {
        // 先從資料庫撈出這本書原本的樣子，確認擁有者是誰
        Book originalBook = bookRepository.findById(book.getId()).get();

        if (principal != null && originalBook.getOwnerUsername().equals(principal.getName())) {
            // 只有本人才可以把修改後的資料存進去
            // 因為前端表單可能沒傳擁有者欄位，我們要幫它把原本的擁有者塞回去，避免變空值
            book.setOwnerUsername(originalBook.getOwnerUsername());
            bookRepository.save(book);
        }
        return "redirect:/";
    }
}