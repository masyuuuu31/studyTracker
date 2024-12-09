package com.example.studytracker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    /**
     * トップページへ遷移
     */
    @GetMapping("/top")
    public String top() {
        // メインタスクを取得する
        return null;
    }


}
