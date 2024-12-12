package com.example.studytracker.auth;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.studytracker.form.UserRegistrationForm;
import com.example.studytracker.service.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    /**
     * 会員登録機能
     */
    // 新規会員登録画面へ遷移
    @GetMapping("/signup")
    public String signup(@ModelAttribute UserRegistrationForm userRegistrationForm) {
        return "signup";
    }

    //会員登録
    @PostMapping("/signup")
    public String postSignup(@Validated UserRegistrationForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return signup(form);
        }

        // メールアドレスが有効か確認する
        if (!userService.isUserIdAvailable(form.getUserId())) {
            return signup(form);
        }

        userService.registerUser(form);
        return "redirect:/login";
    }

    /**
     * ログイン機能
     */
    // ログイン画面へ遷移
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/")
    public String success() {
        return "hello";
    }
    
    
}
