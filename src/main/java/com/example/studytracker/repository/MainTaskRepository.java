package com.example.studytracker.repository;

import com.example.studytracker.entity.MainTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * メインタスクのリポジトリインターフェース
 * タスクの検索、登録、更新、削除の機能を提供する
 * @author Ritsu.Inoue
 */
@Repository
public interface MainTaskRepository extends JpaRepository<MainTask, Long> {
}
