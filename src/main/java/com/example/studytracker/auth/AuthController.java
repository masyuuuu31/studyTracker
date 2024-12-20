package com.example.studytracker.auth;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.studytracker.exception.DuplicateUserException;
import com.example.studytracker.form.UserLoginForm;
import com.example.studytracker.form.UserRegistrationForm;
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

    /**
     * 新規会員登録画面を表示する
     * 
     * @param form 会員登録フォームのモデル
     * @return 会員登録画面のビュー名
     * @author Ritsu.Inoue
     */
    @GetMapping("/signup")
    public String signup(@ModelAttribute UserRegistrationForm form) {
        return "signup";
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
        return "login";
    }

    /**
     * ログインフォームのバリデーションを実行する
     * バリデーション成功時はSpring Securityの認証処理にフォワードする
     * 
     * @param form バリデーション対象のフォーム
     * @param result バリデーション結果
     * @return バリデーション結果に応じた遷移先
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
