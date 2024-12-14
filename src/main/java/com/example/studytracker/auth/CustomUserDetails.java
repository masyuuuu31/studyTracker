package com.example.studytracker.auth;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

/**
 * Spring Securityで使用するユーザー情報を保持するクラス
 * Userクラスを継承し、認証に必要な情報を提供する
 * @author Ritsu.Inoue
 */
public class CustomUserDetails extends User{
    public CustomUserDetails(String userId, String password, Collection<? extends GrantedAuthority> authorities) {
        super(userId, password, authorities);
    }
}   
