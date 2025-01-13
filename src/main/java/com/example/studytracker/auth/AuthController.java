package com.example.studytracker.auth;


import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.csrf.InvalidCsrfTokenException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.studytracker.exception.DuplicateUserException;
import com.example.studytracker.exception.PasswordMismatchException;
import com.example.studytracker.form.auth.PasswordResetEmailForm;
import com.example.studytracker.form.auth.PasswordResetForm;
import com.example.studytracker.form.auth.UserLoginForm;
import com.example.studytracker.form.auth.UserRegistrationForm;
import com.example.studytracker.service.PasswordResetService;
import com.example.studytracker.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Controller
@RequiredArgsConstructor
/**
 * 認証関連の処理を担当するコントローラークラス
 * ユーザーの登録・認証機能を提供する
 * @author Ritsu.Inoue
 */
public class AuthController {

    private final UserService userService;
    private final PasswordResetService passwordResetService;

    /**
     * 新規会員登録画面を表示する
     * 
     * @param form 会員登録フォームのモデル
     * @return 会員登録画面のビュー名
     * @author Ritsu.Inoue
     */
    @GetMapping("/signup")
    public String signup(@ModelAttribute UserRegistrationForm form) {
        return "auth/signup";
    }
    
    /**
     * 会員登録処理を実行する
     * 
     * @param form 入力された会員登録情報
     * @param bindingResult バリデーション結果
     * @return 処理結果に応じた遷移先のビュー名
     *         - バリデーションエラー時: 会員登録画面
     *         - ユーザーID重複時: 会員登録画面（エラーメッセージ付き）
     *         - 登録成功時: ログイン画面へリダイレクト
     * @author Ritsu.Inoue
     */
    @PostMapping("/signup")
    public String postSignup(@Validated UserRegistrationForm form, BindingResult bindingResult, Model model) {
        
        // バリデーションエラーチェック
        if (bindingResult.hasErrors()) {
            return signup(form);
        }

        try {
            userService.registerUser(form);
            return "redirect:/login";

        } catch(DuplicateUserException e) {
            // ユーザーID重複エラーの場合
            model.addAttribute("errorMessage", e.getMessage());
            return signup(form);
        } catch(Exception e) {
            // 想定外のエラー：エラーログを出力してエラーページへ遷移
            log.error("ユーザー登録処理でエラーが発生しました。", e);
            // TODO
            return null;
        }

    }

    /**
     * ログイン画面を表示する
     * 
     * @return ログイン画面のビュー名
     * @author Ritsu.Inoue
     */
    @GetMapping("/login")
    public String login(@ModelAttribute UserLoginForm form) {
        return "auth/login";
    }

    /**
     * ログインフォームのバリデーションを実行する
     * バリデーション成功時はSpring Securityの認証処理にフォワードする
     * 
     * @param form バリデーション対象のフォーム
     * @param result バリデーション結果
     * @return バリデーション結果に応じた遷移先
     * @author Ritsu.Inoue
     */
    @PostMapping("/validate-login")
    public String validateLogin(@Validated UserLoginForm form, BindingResult result) {

        // バリデーションエラーがある場合
        if (result.hasErrors()) {
            return login(form);
        }
        
        return "forward:/login";
    }

    /**
     * パスワードリセット用メールアドレス入力画面を表示する
     * 
     * @param form メールアドレス入力フォーム
     * @return パスワードリセット画面のビュー名
     * @author Ritsu.Inoue
     */
    @GetMapping("/password/reset")
    public String showPasswordResetForm(@ModelAttribute PasswordResetEmailForm form) {
        return "auth/pass/resetting";
    }
    
    /**
     * パスワードリセット用トークンを発行する
     * 
     * @param form メールアドレス入力フォーム
     * @param result バリデーション結果
     * @param model ビューに渡すモデル
     * @return 処理結果に応じた遷移先のビュー名
     *         - バリデーションエラー時: パスワードリセット画面
     *         - ユーザー未存在時: パスワードリセット画面（エラーメッセージ付き）
     *         - 成功時: トークン表示画面
     * @author Ritsu.Inoue
     */
    @PostMapping("/password/reset")
    public String createPasswordResetToken(@Validated PasswordResetEmailForm form, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return showPasswordResetForm(form);
        }

        try {
            String email = form.getEmail();
            // tokenを作成
            String token = passwordResetService.createPasswordResetToken(email);
            
            PasswordResetForm passwordResetForm = new PasswordResetForm();
            passwordResetForm.setEmail(email);
            passwordResetForm.setToken(token);

            model.addAttribute("passwordResetForm", passwordResetForm);

            // パスワード再設定画面へ遷移
            return "auth/pass/resettingPass";
        } catch(UsernameNotFoundException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return showPasswordResetForm(form);
        } 
    }
    
    /**
     * パスワードの再設定を実行する
     * 
     * @param form パスワード再設定フォーム（トークン、メールアドレス、新パスワード情報を含む）
     * @param result バリデーション結果
     * @param model ビューに渡すモデル
     * @return 処理結果に応じた遷移先のビュー名
     *         - バリデーションエラー時: パスワード再設定画面（フォーム値を保持）
     *         - パスワード不一致時: パスワード再設定画面（エラーメッセージ付き）
     *         - トークン無効時: パスワードリセット初期画面
     *         - 成功時: ログイン画面
     * @author Ritsu.Inoue
     */
    @PostMapping("/password/reset/new")
    public String postMethodName(@Validated PasswordResetForm form, BindingResult result, Model model) {
        // バリデーションエラーチェック
        if (result.hasErrors()) {
        // フォームオブジェクトを保持して再表示
        model.addAttribute("passwordResetForm", form);
        return "auth/pass/resettingPass";
        };

        try {
            // パスワード再設定処理
            passwordResetService.resetPassword(form);

            // 成功時はログイン画面にリダイレクト
            return "redirect:/login";
        } catch(PasswordMismatchException e) {
            // エラーメッセージを設定
            model.addAttribute("errorMessage", e.getMessage());
            // フォームオブジェクトを保持して再表示
            model.addAttribute("passwordResetForm", form);
            return "auth/pass/resettingPass";
        } catch(InvalidCsrfTokenException e) {
            // トークンが無効の場合は最初からやり直し
            model.addAttribute("errorMessage", e.getMessage());
            return "redirect:/password/reset";
            
        } catch(Exception e) {
            // 想定外のエラー：エラーログを出力してエラーページへ遷移
            log.error("ユーザー登録処理でエラーが発生しました。", e);
            // TODO
            return null;
        }
    }
    

    /**
     * テスト用のユーザー一覧画面を表示する
     * 現在は空のリストを表示する実装となっている
     * 
     * @param model ビューに渡すモデル
     * @return ユーザー一覧画面のビュー名
     * @author Ritsu.Inoue
     */
    @GetMapping("/test/userList")
    public String showUserList(Model model) {
        model.addAttribute("userList", userService.findAllUsers());
        return "test/userList";
    }
}