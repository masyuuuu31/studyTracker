package com.example.studytracker.auth;

import com.example.studytracker.entity.MUser;
import com.example.studytracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        // userIdでユーザーを検索（userIdはメールアドレス）
        return userRepository.findById(userId)
                .map(
                        user -> org.springframework.security.core.userdetails.User
                                .withUsername(user.getName())
                                .password(user.getPassword())
                                .roles("USER")
                                .build())
                .orElseThrow(() -> new UsernameNotFoundException("User not found with userId :" + userId));
    }
}
