package com.example.studytracker.form.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * パスワード再設定フォーム
 * パスワードリセットの最終段階で使用する
 * @author Ritsu.Inoue
 */
@Data
public class PasswordResetForm {

    @NotBlank
    @Size(min = 12, max = 128)
    private String password;

    @NotBlank
    @Size(min = 12, max = 128)
    private String confirmPassword;

    private String email;
    private String token;
}
