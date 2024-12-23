package com.example.studytracker.form.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

/**
 * パスワードリセット用メールアドレス入力フォーム
 * パスワードリセットの初期段階で使用する
 * @author Ritsu.Inoue
 */
@Data
public class PasswordResetEmailForm {
    @NotEmpty
    @Email
    private String email;
}
