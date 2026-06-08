package com.example.campus_used_books.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    // 1. 密碼加密器
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 2. 規定哪些網頁要登入才能看
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        // 💡 主頁(/)、資料庫後台、註冊相關頁面、登入頁面通通開放，不需登入就能看
                        .requestMatchers("/", "/h2-console/**", "/login-page", "/register-page", "/register")
                        .permitAll()
                        // 💡 剩餘的所有功能（例如：點擊上架賣書、編輯、刪除）通通必須登入才能操作！
                        .anyRequest().authenticated())
                .formLogin(login -> login
                        .loginPage("/login-page") // 我們的自訂登入網址
                        .loginProcessingUrl("/login") // 按下確認登入後，讓 Spring Security 自動處理驗證的網址
                        .defaultSuccessUrl("/", true) // 登入成功後，自動跳轉回首頁
                        .permitAll())
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/") // 點擊登出後回到主頁
                );

        // 為了讓你們能繼續正常連入 H2 Database 控制台，必須關閉這兩項預設防護
        http.csrf(csrf -> csrf.disable());
        http.headers(headers -> headers.frameOptions(frame -> frame.disable()));

        return http.build();
    }
}