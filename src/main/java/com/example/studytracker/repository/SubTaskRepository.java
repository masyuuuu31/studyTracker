package com.example.studytracker.repository;

import com.example.studytracker.entity.SubTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * サブタスクのリポジトリインターフェース
 * サブタスクの検索、登録、更新、削除の機能を提供する
 * @author Ritsu.Inoue
 */
@Repository
public interface SubTaskRepository extends JpaRepository<SubTask, Long> {
}
