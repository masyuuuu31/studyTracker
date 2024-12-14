package com.example.studytracker.service;

import com.example.studytracker.entity.MUser;
import com.example.studytracker.exception.DuplicateUserException;
import com.example.studytracker.form.UserRegistrationForm;
import com.example.studytracker.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        if (!isUserIdAvailable(userId)) {
            throw new DuplicateUserException("既に登録済みのメールアドレスです。");
        } 

        MUser mUser = new MUser();
        mUser.setId(form.getUserId());
        mUser.setName(form.getUserName());

        // パスワードをエンコード
        encodedPass = passwordEncoder.encode(form.getPassword());
        mUser.setPassword(encodedPass);

        // 権限
        mUser.setAuthority(1);

        // 登録実行
        userRepository.save(mUser);
        return true;
    }

    /**
     * 指定されたユーザーIDが使用可能か確認する
     * すでに存在するユーザーIDの場合はfalseを返す
     * 
     * @param userId チェック対象のユーザーID
     * @return ユーザーIDが使用可能な場合はtrue、すでに存在する場合はfalse
     * @author Ritsu.Inoue
     */
    public boolean isUserIdAvailable(String userId) {
        return !userRepository.existsById(userId);
    }


    /**
     * 全ユーザーを取得する
     * @return ユーザーエンティティのリスト
     * @author Ritsu.Inoue
     */
    public List<MUser> findAllUsers() {
        return userRepository.findAll();
    }
}
