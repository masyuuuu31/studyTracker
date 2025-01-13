package com.example.studytracker.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * タスクコメントを管理するエンティティクラス
 * データベースのtask_commentsテーブルとマッピングする
 * @author Ritsu.Inoue
 */
@Data
@Entity
@Table(name = "task_comments")
@NoArgsConstructor
@AllArgsConstructor
public class TaskComment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "timer_record_id", nullable = false)
    private TimerRecord timerRecord;

    @ManyToOne
    @JoinColumn(name = "sub_task_id", nullable = false)
    private SubTask subTask;

    @Column(name = "comment", nullable = false)
    private String comment;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
