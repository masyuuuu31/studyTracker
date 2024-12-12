package com.example.studytracker.service;

import com.example.studytracker.entity.MUser;
import com.example.studytracker.form.UserRegistrationForm;
import com.example.studytracker.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    /**
     * 会員登録
     */
    @Transactional
    public boolean registerUser(UserRegistrationForm userRegistrationForm) {
        MUser mUser = new MUser();
        mUser.setId(userRegistrationForm.getUserId());
        mUser.setName(userRegistrationForm.getUserName());
        mUser.setPassword(userRegistrationForm.getPassword());

        // 登録実行
        userRepository.save(mUser);
        return true;
    }

    /**
     * 入力されたユーザーIDが有効か判定する
     */
    public boolean isUserIdAvailable(String userId) {
        return !userRepository.existsById(userId);
    }

}
