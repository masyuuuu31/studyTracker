package com.example.studytracker.service;

import org.springframework.stereotype.Service;

import com.example.studytracker.constant.TaskStatus;
import com.example.studytracker.entity.MainTask;
import com.example.studytracker.entity.SubTask;
import com.example.studytracker.exception.TaskNotFoundException;
import com.example.studytracker.repository.MainTaskRepository;
import com.example.studytracker.repository.SubTaskRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.transaction.annotation.Transactional;

/**
 * サブタスクに関する業務ロジックを提供するサービスクラス
 * サブタスクの作成、更新、状態管理の機能を提供する
 * @author Ritsu.Inoue
 */
@Service
@RequiredArgsConstructor
public class SubTaskService {

    private final SubTaskRepository subTaskRepository;
    private final MainTaskRepository mainTaskRepository;

    /**
     * サブタスクを新規作成する
     * @param mainTaskId　紐付けるメインタスクのID
     * @param title　サブタスクのタイトル
     * @throws TaskNotFoundException メインタスクが存在しない場合
     * @author Ritsu.Inoue
     */
    @Transactional
    public void createSubTask(Long mainTaskId, String title) {
        // メインタスクの存在確認
        MainTask mainTask = mainTaskRepository.findById(mainTaskId)
            .orElseThrow(() -> new TaskNotFoundException("指定されたメインタスクが見つかりません。"));
        
        SubTask subTask = new SubTask();
        // タイトル設定
        subTask.setTitle(title);
        // メインタスクとの紐付け
        subTask.setTarget(mainTask);
        // 初期値の設定
        subTask.setStatus(TaskStatus.NOT_STARTED.getCode());

        // 新規作成
        subTaskRepository.save(subTask);
    }
}
