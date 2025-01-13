package com.example.studytracker.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.studytracker.entity.TimerRecord;

/**
 * タイマー記録のリポジトリインターフェース
 * タイマー記録の検索、登録、更新、削除の機能を提供する
 * @author Ritsu.Inoue
 */
@Repository
public interface TimerRecordRepository extends JpaRepository<TimerRecord, Long>{

    /**
     * 指定されたメインタスクIDに紐づく全てのタイマー記録を取得する
     * @param mainTaskId 検索対象のメインタスクID
     * @return タイマー記録のリスト。該当するレコードが存在しない場合は空のリスト
     */
    @Query(
        value = "SELECT * FROM timer_records WHERE main_task_id = :mainTaskId", nativeQuery = true
    )
    List<TimerRecord> findByMainTask(@Param("mainTaskId") Long mainTaskId);
}
