package com.example.campus_used_books.repository;

import com.example.campus_used_books.entity.UserOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UserOrderRepository extends JpaRepository<UserOrder, Long> {
    List<UserOrder> findByBuyerUsernameOrderByOrderDateDesc(String buyerUsername);
}
