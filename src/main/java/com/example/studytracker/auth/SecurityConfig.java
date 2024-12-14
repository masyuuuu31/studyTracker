package com.example.studytracker.auth;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Spring Securityの設定クラス
 * アプリケーションのセキュリティ設定を定義する
 * @author Ritsu.Inoue
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.formLogin(login -> login
                .loginProcessingUrl("/login") // ログイン処理を行うURL
                .loginPage("/login") // カスタムログインページのURL
                .defaultSuccessUrl("/test/userList", true) // ログイン成功後のURL
                .failureUrl("/login?error") // ログイン失敗時のURL
                .usernameParameter("userId") // ユーザーID項目の名前
                .passwordParameter("password") // パスワード項目の名前
                .permitAll()

        ).logout(logout -> logout
                .logoutSuccessUrl("/login")

        ).authorizeHttpRequests(authz -> authz
                // 静的リソースは常にアクセス可能
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                // ログイン・サインアップページは未認証でアクセス可能
                .requestMatchers("/login", "/signup", "/validate-login").permitAll()
                .anyRequest().authenticated()
        );
        return http.build();
    }

    @Bean
    protected PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
