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

    // 💡 修改：處理新增書籍表單（支援圖片上傳）
    @PostMapping("/addBook")
    public String addBook(
            Book book,
            @RequestParam("imageFile") org.springframework.web.multipart.MultipartFile imageFile,
            java.security.Principal principal) {

        // 1. 自動綁定上架者
        if (principal != null) {
            book.setOwnerUsername(principal.getName());
        }

        // 🌟 2. 處理圖片上傳邏輯
        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                // 決定要把圖片存到專案裡的哪個實際路徑 (存放在 static/uploads 資料夾)
                String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/uploads/";

                // 防呆：如果 uploads 資料夾不存在，自動建立它
                java.io.File dir = new java.io.File(uploadDir);
                if (!dir.exists()) {
                    dir.mkdirs();
                }

                // 為了防止不同使用者上傳「相同檔名」的圖片導致覆蓋 (例如大家都傳 cover.png)
                // 我們用時間戳記幫圖片重新命名 (例如：1717830000_book.png)
                String originalFilename = imageFile.getOriginalFilename();
                String fileName = System.currentTimeMillis() + "_" + originalFilename;

                // 實體檔案寫入電腦硬碟中
                java.nio.file.Path filePath = java.nio.file.Paths.get(uploadDir + fileName);
                java.nio.file.Files.copy(imageFile.getInputStream(), filePath,
                        java.nio.file.StandardCopyOption.REPLACE_EXISTING);

                // 把這個「獨一無二的檔名」存進 Book 物件，準備寫入資料庫
                book.setCoverImage(fileName);

            } catch (java.io.IOException e) {
                e.printStackTrace();
                // 如果上傳出錯，可以給一個預設圖片檔名
                book.setCoverImage("default.png");
            }
        } else {
            // 如果使用者沒傳圖片，給一張預設圖
            book.setCoverImage("default.png");
        }

        // 3. 存入 H2 資料庫
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

    @GetMapping("/search")
    public String searchBooks(
            @RequestParam String type,
            @RequestParam String keyword,
            Model model) {

        // 建立一個 List 用來裝等一下搜尋出來的書
        java.util.List<Book> searchResults;

        // 根據前端傳過來的 type 決定要用哪一種搜尋
        switch (type) {
            case "title":
                searchResults = bookRepository.findByTitleContainingIgnoreCase(keyword);
                break;
            case "course":
                searchResults = bookRepository.findByCourseNameContainingIgnoreCase(keyword);
                break;
            case "professor":
                searchResults = bookRepository.findByProfessorContainingIgnoreCase(keyword);
                break;
            default:
                // 如果防呆防到未知的 type，就預設撈全部
                searchResults = bookRepository.findAll();
                break;
        }

        // 把搜尋結果和當初搜的關鍵字打包，丟給新網頁 search-results.html
        model.addAttribute("books", searchResults);
        model.addAttribute("keyword", keyword);
        model.addAttribute("searchType", type);

        return "search-results"; // 導向新建立的搜尋結果頁
    }
}