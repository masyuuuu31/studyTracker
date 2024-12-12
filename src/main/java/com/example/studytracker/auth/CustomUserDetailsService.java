package com.example.studytracker.auth;

import java.util.Collections;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
public class CustomUserDetailsService implements UserDetailsService {


    private final PasswordEncoder passwordEncoder;

    public CustomUserDetailsService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        if ("tom@example.com".equals(userId)) {
            return new CustomUserDetails(
                userId,
                passwordEncoder.encode("password123"), // テスト用パスワード
                Collections.emptyList()
            );
        }
        // TODO Auto-generated method stub
        throw new UsernameNotFoundException("Unimplemented method 'loadUserByUsername'");
    }

}
