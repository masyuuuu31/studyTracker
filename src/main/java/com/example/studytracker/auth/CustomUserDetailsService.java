package com.example.studytracker.auth;

import java.util.Collections;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.studytracker.repository.UserRepository;

import lombok.RequiredArgsConstructor;

/**
 * Spring Securityのユーザー認証に必要な情報を提供するサービスクラス
 * データベースに格納されているユーザー情報をSpring Securityで使用可能な形式に変換する
 * @author Ritsu.Inoue
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * ユーザーIDに基づいてユーザー情報を読み込む
     * 
     * @param userId 検索対象のユーザーID
     * @return UserDetails 認証に必要なユーザー情報
     * @throws UsernameNotFoundException ユーザーが見つからない場合
     * @author Ritsu.Inoue
     */
    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        return userRepository.findById(userId)
        .map(
            user -> 
                new CustomUserDetails(
                    user.getId(),
                    user.getPassword(),
                    Collections.emptyList()
                )
        )
            .orElseThrow(
                () -> new UsernameNotFoundException("Unimplemented method 'loadUserByUsername'" + userId)
            );
    }
}
