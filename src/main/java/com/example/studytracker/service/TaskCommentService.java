package com.example.studytracker.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.studytracker.entity.SubTask;
import com.example.studytracker.entity.TaskComment;
import com.example.studytracker.entity.TimerRecord;
import com.example.studytracker.repository.TaskCommentRepository;

import lombok.RequiredArgsConstructor;

/**
 * タスクコメントに関する業務ロジックを提供するサービスクラス
 * コメントの作成、管理の機能を提供する
 * @author Ritsu.Inoue
 */
@Service
@RequiredArgsConstructor
public class TaskCommentService {

    private final TaskCommentRepository taskCommentRepository;

    /**
     * タイマー記録に紐付くコメントを作成する
     * @param timerRecord タイマー記録
     * @param subTask サブタスク
     * @param comment コメント内容
     * @author Ritsu.Inoue
     */
    @Transactional
    public void createComment(TimerRecord timerRecord, SubTask subTask, String comment) {

        TaskComment taskComment = new TaskComment();
        taskComment.setTimerRecord(timerRecord);
        taskComment.setSubTask(subTask);
        taskComment.setComment(comment);
        
        taskCommentRepository.save(taskComment);
    }
}
