package com.example.studytracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.studytracker.entity.TaskComment;

/**
 * タスクコメントのリポジトリインターフェース
 * コメントの検索、登録、更新、削除の機能を提供する
 * @author Ritsu.Inoue
 */
@Repository
public interface TaskCommentRepository extends JpaRepository<TaskComment, Long> {
}
