package com.example.studytracker.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.example.studytracker.constant.TaskStatus;
import com.example.studytracker.entity.MainTask;
import com.example.studytracker.repository.MainTaskRepository;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

/**
 * メインタスクに関する業務ロジックを提供するサービスクラス
 * タスクの作成、更新、状態管理の機能を提供する
 * @author Ritsu.Inoue
 */
@Service
@RequiredArgsConstructor
public class MainTaskService {

    private final MainTaskRepository mainTaskRepository;
    private final UserService userService;

    /**
     * メインタスクを新規作成する
     * @param title タスクのタイトル
     * @param deadline タスクの期限（null許容）
     * @author Ritsu.Inoue
     */
    @Transactional
    public void createMainTask(String title, LocalDateTime deadline) {

        MainTask mainTask = new MainTask();

        // タイトル設定
        mainTask.setTitle(title);
        // 期限の設定
        mainTask.setDeadline(deadline);
        // ログイン中のユーザーを取得
        mainTask.setAuthor(userService.getCurrentUser());
        // 初期値の設定
        mainTask.setStatus(TaskStatus.NOT_STARTED.getCode());
        mainTask.setPercent(0.0);

        // 新規作成
        mainTaskRepository.save(mainTask);
    }

}
