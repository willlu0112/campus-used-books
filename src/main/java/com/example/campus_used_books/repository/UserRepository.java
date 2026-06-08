package com.example.campus_used_books.repository;

import com.example.campus_used_books.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // 讓後端可以透過帳號去資料庫搜尋有沒有這個人
    Optional<User> findByUsername(String username);
}