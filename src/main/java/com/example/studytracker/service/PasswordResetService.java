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
import com.example.studytracker.exception.PasswordMismatchException;
import com.example.studytracker.form.auth.PasswordResetForm;
import com.example.studytracker.repository.PasswordResetRepository;
import com.example.studytracker.repository.UserRepository;

import lombok.RequiredArgsConstructor;

/**
 * パスワードリセットに関する業務ロジックを提供するサービスクラス
 * トークンの生成、パスワードの再設定、有効期限管理の機能を提供する
 * @author Ritsu.Inoue
 */
@Service
@RequiredArgsConstructor
public class PasswordResetService {

    private final UserRepository userRepository;
    private final PasswordResetRepository passwordResetRepository;
    private final PasswordEncoder passwordEncoder;

    /** トークンの有効期限（時間） */
    private static final int TOKEN_VALID_HOURS = 24;

    /**
     * パスワードリセット用のトークンを生成する
     * 既存の有効なトークンは削除される
     * 
     * @param userId ユーザーID
     * @return 生成されたトークン文字列
     * @throws UsernameNotFoundException ユーザーが存在しない場合
     * @author Ritsu.Inoue
     */
    @Transactional
    public String createPasswordResetToken(String userId) {
        // 正しいユーザーIDかチェック
        MUser mUser = userRepository.findById(userId)
            .orElseThrow(() -> new UsernameNotFoundException("指定されたメールアドレスは登録されていません。"));
        
        // 既にユーザーが保持中の有効トークンを削除
        List<PasswordResetToken> tokens = passwordResetRepository.findByUserId(userId);
        for (PasswordResetToken token : tokens) {
            if (isValidToken(token.getExpiryDate())) {
                passwordResetRepository.deleteById(token.getId());
            }
        }

        // 新しいトークンを生成
        String token = generateUniqueToken();
        
        // トークンエンティティの作成と保存
        PasswordResetToken resetToken = new PasswordResetToken();
        resetToken.setToken(token);
        resetToken.setUser(mUser);
        resetToken.setExpiryDate(LocalDateTime.now().plusHours(TOKEN_VALID_HOURS));
        
        passwordResetRepository.save(resetToken);

        return token;
    }
    
    /**
     * パスワードを再設定する
     * 
     * @param form パスワード再設定用フォームobj
     * @throws InvalidTokenException トークンが無効な場合
     * @author Ritsu.Inoue
     */
    @Transactional
    public void resetPassword(PasswordResetForm form) {
        // 有効なトークンを取得
        PasswordResetToken resetToken = passwordResetRepository.findByToken(form.getToken())
            .orElseThrow(() -> new InvalidTokenException("無効なトークンです。"));
        
        // トークンの有効期限をチェック
        if (!isValidToken(resetToken.getExpiryDate())) {
            throw new InvalidTokenException("トークンの有効期限が切れています。");
        }

        // パスワード一致確認
        if (!form.getPassword().equals(form.getConfirmPassword())) {
            throw new PasswordMismatchException("パスワードが一致しません。");
        }
        
        // パスワードの更新
        MUser muser = resetToken.getUser();
        muser.setPassword(passwordEncoder.encode(form.getPassword()));
        userRepository.save(muser);
        
        // トークンを削除
        passwordResetRepository.deleteById(resetToken.getId());
    }

    /**
     * 重複しないトークンを生成する
     * 
     * @return ユニークなトークン文字列
     * @author Ritsu.Inoue
     */
    private String generateUniqueToken() {
        String token;
        do {
            token = UUID.randomUUID().toString();
        } while (passwordResetRepository.findByToken(token).isPresent());
        
        return token;
    }

    /**
     * トークンの有効期限をチェックする
     * 
     * @param expiryDate 有効期限
     * @return 有効期限内の場合はtrue
     * @author Ritsu.Inoue
     */
    private boolean isValidToken(LocalDateTime expiryDate) {
        return LocalDateTime.now().isBefore(expiryDate);
    }
}