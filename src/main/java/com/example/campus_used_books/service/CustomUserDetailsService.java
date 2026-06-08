package com.example.campus_used_books.service;

import com.example.campus_used_books.entity.User;
import com.example.campus_used_books.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 當使用者輸入帳號密碼登入時，去資料庫找有沒有這個帳號
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("找不到該用戶: " + username));

        // 把我們資料庫的 User 轉成 Spring Security 認證系統看得懂的格式
        return org.springframework.security.core.userdetails.User.withUsername(user.getUsername())
                .password(user.getPassword())
                .roles("USER")
                .build();
    }
}