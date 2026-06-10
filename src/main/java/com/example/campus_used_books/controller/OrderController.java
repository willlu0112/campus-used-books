package com.example.campus_used_books.controller;

import com.example.campus_used_books.entity.Book;
import com.example.campus_used_books.entity.CartItem;
import com.example.campus_used_books.entity.UserOrder;
import com.example.campus_used_books.entity.UserOrderItem;
import com.example.campus_used_books.repository.BookRepository;
import com.example.campus_used_books.repository.CartItemRepository;
import com.example.campus_used_books.repository.UserOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class OrderController {

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private UserOrderRepository userOrderRepository;

    @Autowired
    private BookRepository bookRepository;

    @PostMapping("/checkout")
    public String checkout(Principal principal) {
        if (principal == null) {
            return "redirect:/login-page";
        }

        String username = principal.getName();
        List<CartItem> cartItems = cartItemRepository.findByBuyerUsername(username);

        if (cartItems.isEmpty()) {
            return "redirect:/cart";
        }

        UserOrder order = new UserOrder();
        order.setBuyerUsername(username);
        order.setOrderDate(LocalDateTime.now());

        List<UserOrderItem> orderItems = new ArrayList<>();
        List<Book> booksToDelete = new ArrayList<>(); // 🌟 新增：用來收集等一下要下架的書籍
        int total = 0;

        for (CartItem cartItem : cartItems) {
            UserOrderItem orderItem = new UserOrderItem();
            orderItem.setUserOrder(order);
            orderItem.setBookTitle(cartItem.getBook().getTitle());
            orderItem.setPrice(cartItem.getBook().getPrice());
            orderItem.setCoverImage(cartItem.getBook().getCoverImage());
            orderItems.add(orderItem);

            total += cartItem.getBook().getPrice();

            // 🌟 先把要刪除的書收集起來，不要在這裡直接 delete
            booksToDelete.add(cartItem.getBook());
        }

        order.setTotalPrice(total);
        order.setItems(orderItems);
        
        // 🌟 嚴格調整執行順序：
        userOrderRepository.save(order);         // 1. 先存檔訂單與明細
        cartItemRepository.deleteAll(cartItems); // 2. 先清空購物車明細 (解開與書本的關聯綁定)
        bookRepository.deleteAll(booksToDelete); // 3. 最後再把書本實體從資料庫移除

        return "redirect:/orders";
    }

    @GetMapping("/orders")
    public String viewOrders(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login-page";
        }

        String username = principal.getName();
        List<UserOrder> myOrders = userOrderRepository.findByBuyerUsernameOrderByOrderDateDesc(username);
        model.addAttribute("orders", myOrders);
        return "orders";
    }
}