package com.example.studytracker.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.studytracker.entity.MUser;
import com.example.studytracker.entity.PasswordResetToken;
import com.example.studytracker.exception.InvalidTokenException;
import com.example.studytracker.repository.PasswordResetRepository;
import com.example.studytracker.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PasswordResetService {

    private final UserRepository userRepository;
    private final PasswordResetRepository passwordResetRepository;
    private final PasswordEncoder passwordEncoder;

    // トークン有効期限
    private static final int TOKEN_VALID_HOURS = 24;

    @Transactional
    public String createPasswordResetToken(String userId) {
        // 正しいユーザーIDかチェック
        MUser mUser = userRepository.findById(userId)
            .orElseThrow(() -> new UsernameNotFoundException("指定されたメールアドレスは登録されていません。"));
        
        // 既にユーザーが保持中の有効トークンを削除
        List<PasswordResetToken> tokens = passwordResetRepository.findByUserId(userId);
        for(PasswordResetToken token : tokens) {
            if (isValidToken(token.getExpiryDate())) {
                passwordResetRepository.deleteById(token.getId());
            }
        }

        String token = generateUniqueToken();
        
        // トークンエンティティの作成と保存
        PasswordResetToken resetToken = new PasswordResetToken();
        resetToken.setToken(token);
        resetToken.setUser(mUser);
        resetToken.setExpiryDate(LocalDateTime.now().plusHours(TOKEN_VALID_HOURS));
        
        passwordResetRepository.save(resetToken);

        return token;
    }
    
    @Transactional
    public void resetPassword(String token, String newPassword) {
        // 有効なトークンを取得
        PasswordResetToken resetToken = passwordResetRepository.findByToken(token)
            .orElseThrow(
                () -> new InvalidTokenException("無効なトークンです。")
            );
        
        // トークンの有効期限をチェック
        if (!isValidToken(resetToken.getExpiryDate())) {
            throw new InvalidTokenException("トークンの有効期限が切れています。");
        }
        
        // パスワードの更新
        MUser muser = resetToken.getUser();
        muser.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(muser);
        
        // トークンを削除
        passwordResetRepository.deleteById(resetToken.getId());
    }

    private String generateUniqueToken() {
        String token;
        
        do {
            // ランダムなトークンを生成
            token = UUID.randomUUID().toString();
    
            // トークンが既に存在している場合は再生成
        } while (passwordResetRepository.findByToken(token).isPresent());
        
        return token;
    }

    private boolean isValidToken(LocalDateTime expiryDate) {
        
        // 処理日時が有効期限内の場合
        if (LocalDateTime.now().isBefore(expiryDate)) {
            return true;
        }else {
            return false;
        }
    }

}
