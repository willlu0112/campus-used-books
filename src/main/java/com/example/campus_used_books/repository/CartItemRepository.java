package com.example.campus_used_books.repository;

import com.example.campus_used_books.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByBuyerUsername(String buyerUsername);
}
