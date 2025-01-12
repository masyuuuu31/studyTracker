package com.example.studytracker.service;

import com.example.studytracker.auth.CustomUserDetails;
import com.example.studytracker.constant.UserAuthority;
import com.example.studytracker.entity.MUser;
import com.example.studytracker.exception.DuplicateUserException;
import com.example.studytracker.form.auth.UserRegistrationForm;
import com.example.studytracker.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.security.core.Authentication;

/**
 * ユーザー関連のビジネスロジックを提供するサービスクラス
 * ユーザーの登録や存在チェックなどの機能を提供する
 * @author Ritsu.Inoue
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 新規ユーザーを登録する
     * フォームの情報をもとにユーザーエンティティを作成し、データベースに保存する
     * 
     * @param userRegistrationForm ユーザー登録フォームの情報
     * @return 登録成功時はtrue
     * @throws RuntimeException データベース操作で例外が発生した場合
     * @author Ritsu.Inoue
     */
    @Transactional
    public boolean registerUser(UserRegistrationForm form) {

        String userId = form.getUserId();
        String encodedPass;

        // ユーザーID重複チェック
        if (userRepository.existsById(userId)) {
            throw new DuplicateUserException("既に登録済みのメールアドレスです。");
        } 

        MUser mUser = new MUser();
        mUser.setId(form.getUserId());
        mUser.setName(form.getUserName());

        // パスワードをエンコード
        encodedPass = passwordEncoder.encode(form.getPassword());
        mUser.setPassword(encodedPass);

        // 権限 : 一般
        mUser.setAuthority(UserAuthority.GENERAL.getCode());

        // 登録実行
        userRepository.save(mUser);
        return true;
    }

    /**
     * 全ユーザーを取得する
     * @return ユーザーエンティティのリスト
     * @author Ritsu.Inoue
     */
    public List<MUser> findAllUsers() {
        return userRepository.findAll();
    }

    /**
     * 認証情報から現在ログイン中のユーザーを取得する
     * 
     * @param
     * @return
     * @throws
     * @author Ritsu.Inoue
     */
    public MUser getCurrentUser() {
        // SecurityContextから認証情報を取得
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        // 認証情報からユーザー詳細を取得
        CustomUserDetails userDetails = (CustomUserDetails)auth.getPrincipal();
        
        // ユーザー詳細からuserIdを取得する
        String userId = userDetails.getUsername();

        // userIdを基にDBからユーザー情報を取得
        return userRepository.findById(userId)
            .orElseThrow(() -> new UsernameNotFoundException("ユーザーが見つかりません"));
    }
}
