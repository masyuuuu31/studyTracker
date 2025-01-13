package com.example.studytracker.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.studytracker.entity.MainTask;
import com.example.studytracker.entity.SubTask;
import com.example.studytracker.entity.TimerRecord;
import com.example.studytracker.exception.TaskNotFoundException;
import com.example.studytracker.repository.MainTaskRepository;
import com.example.studytracker.repository.SubTaskRepository;
import com.example.studytracker.repository.TimerRecordRepository;

import lombok.RequiredArgsConstructor;

/**
 * タイマー機能に関する業務ロジックを提供するサービスクラス
 * タイマーの開始、停止、時間計算の機能を提供する
 * @author Ritsu.Inoue
 */
@Service
@RequiredArgsConstructor
public class TimerService {
    
    /**
     * 1時間あたりのナノ秒数（3600秒 × 10^9）
     * 時間計算時の除算に使用する定数
     */
    private static final BigDecimal NANOS_PER_HOUR = BigDecimal.valueOf(3600L * 1_000_000_000L);
    
    private final MainTaskRepository mainTaskRepository;
    private final SubTaskRepository subTaskRepository;
    private final TimerRecordRepository timerRecordRepository;
    private final TaskCommentService taskCommentService;

    /**
     * タイマーを開始する
     * @param mainTaskId メインタスクのID
     * @param startTime 開始時刻（フロントエンドのボタン押下時刻）
     * @throws TaskNotFoundException メインタスクが存在しない場合
     * @throws IllegalStateException タイマーが既に作動中の場合
     * @author Ritsu.Inoue
     */
    @Transactional
    public void startTimer(Long mainTaskId, LocalDateTime startTime) {
        // メインタスクの取得と存在確認
        MainTask mainTask = mainTaskRepository.findById(mainTaskId)
            .orElseThrow(() -> new TaskNotFoundException("指定されたメインタスク(ID: " + mainTaskId + ")が見つかりません。"));

        // タイマーが既に作動中の場合は例外をスロー
        if (mainTask.getActivTimerRecord() != null) {
            throw new IllegalStateException("タイマーは既に作動中です。メインタスクID: " + mainTaskId);
        }
        
        // タイマーを作成
        TimerRecord timerRecord = new TimerRecord();
        timerRecord.setMainTask(mainTask);
        timerRecord.setStartedAt(startTime);
        timerRecordRepository.save(timerRecord);

        // メインタスクにタイマーを設定
        mainTask.setActivTimerRecord(timerRecord);
        mainTaskRepository.save(mainTask);
    }

    /**
     * タイマーを停止する
     * @param mainTaskId メインタスクのID
     * @param subTaskId サブタスクのID
     * @param stopTime 停止時刻（フロントエンドのボタン押下時刻）
     * @throws TaskNotFoundException メインタスク（サブタスク）が存在しない場合
     * @throws IllegalStateException タイマーが作動していない場合
     * @author Ritsu.Inoue
     */
    @Transactional
    public void stopTimer(Long mainTaskId, Long subTaskId, LocalDateTime stopTime, String comment) {
        
        // メインタスクの取得と存在確認
        MainTask mainTask = mainTaskRepository.findById(mainTaskId)
            .orElseThrow(() -> new TaskNotFoundException("指定されたメインタスク(ID: " + mainTaskId + ")が見つかりません。"));

        // サブタスクの取得と存在確認
        SubTask subTask = subTaskRepository.findById(subTaskId)
            .orElseThrow(() -> new TaskNotFoundException("指定されたサブタスク(ID: " + subTaskId + ")が見つかりません。"));
        
        // アクティブタイマーの取得と存在確認
        TimerRecord activeTimer = mainTask.getActivTimerRecord();
        if (activeTimer == null) {
            throw new IllegalStateException("タイマーが作動していません。メインタスクID: " + mainTaskId);
        }

        // コメントが入力されている場合のみコメントを保存
        if (comment != null && !comment.trim().isEmpty()) {
            taskCommentService.createComment(activeTimer, subTask, comment);
        }
        
        // アクティブタイマーとサブタスクを紐付け
        activeTimer.setSubTask(subTask);

        // タイマー停止時刻を設定
        activeTimer.setFinishedAt(stopTime);
        
        // 作業時間を計算して設定
        BigDecimal hours = calculateHours(activeTimer.getStartedAt(), stopTime);
        activeTimer.setTotalTime(hours);

        // アクティブタイマーをクリア
        mainTask.setActivTimerRecord(null);
        
        // 変更を保存
        timerRecordRepository.save(activeTimer);
        mainTaskRepository.save(mainTask);
    }

    /**
     * メインタスクの合計作業時間を計算する
     * @param mainTaskId メインタスクのID
     * @return 合計作業時間（時間単位）
     * @throws TaskNotFoundException メインタスクが存在しない場合
     * @author Ritsu.Inoue
     */
    public BigDecimal calculateTotalTime(Long mainTaskId) {
        // メインタスクの存在確認（計算前に確認）
        if (!mainTaskRepository.existsById(mainTaskId)) {
            throw new TaskNotFoundException("指定されたメインタスク(ID: " + mainTaskId + ")が見つかりません。");
        }
        
        return timerRecordRepository.findByMainTask(mainTaskId)
            .stream()
            .map(TimerRecord::getTotalTime)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * 開始時刻と終了時刻から経過時間を時間単位で計算する
     * @param startTime 開始時刻
     * @param endTime 終了時刻
     * @return 経過時間（時間単位、小数点以下10桁、四捨五入）
     * @author Ritsu.Inoue
     */
    private BigDecimal calculateHours(LocalDateTime startTime, LocalDateTime endTime) {
        Duration duration = Duration.between(startTime, endTime);
        BigDecimal nanoSeconds = BigDecimal.valueOf(duration.toNanos());
        return nanoSeconds.divide(NANOS_PER_HOUR, 10, RoundingMode.HALF_UP);
    }
}