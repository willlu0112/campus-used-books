package com.example.campus_used_books.controller;

import com.example.campus_used_books.entity.Book;
import com.example.campus_used_books.entity.CartItem;
import com.example.campus_used_books.repository.BookRepository;
import com.example.campus_used_books.repository.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
public class CartController {

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private BookRepository bookRepository;

    @GetMapping("/cart")
    public String viewCart(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login-page";
        }
        String username = principal.getName();
        List<CartItem> myCart = cartItemRepository.findByBuyerUsername(username);
        int totalPrice = myCart.stream().mapToInt(item -> item.getBook().getPrice()).sum();

        model.addAttribute("cartItems", myCart);
        model.addAttribute("totalPrice", totalPrice);
        return "cart";
    }

    @PostMapping("/cart/add")
    public String addToCart(@RequestParam Long bookId, Principal principal) {
        if (principal == null) {
            return "redirect:/login-page";
        }
        Book book = bookRepository.findById(bookId).orElse(null);
        if (book != null) {
            CartItem item = new CartItem();
            item.setBuyerUsername(principal.getName());
            item.setBook(book);
            cartItemRepository.save(item);
        }
        return "redirect:/cart";
    }

    @PostMapping("/cart/remove")
    public String removeFromCart(@RequestParam Long cartItemId, Principal principal) {
        if (principal != null) {
            cartItemRepository.deleteById(cartItemId);
        }
        return "redirect:/cart";
    }
}