package com.example.studytracker.entity;

import java.time.LocalDateTime;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * メインタスク情報を管理するエンティティクラス
 * データベースのmain_taskテーブルとマッピングする
 * @author Ritsu.Inoue
 */
@Data
@Entity
@Table(name = "main_task")
@NoArgsConstructor
@AllArgsConstructor
public class MainTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "deadline")
    private LocalDateTime deadline;

    @Column(name = "percent")
    private Double percent;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private MUser author;

    @OneToOne
    @JoinColumn(name = "active_timer_id")
    private TimerRecord activTimerRecord;

    @Column(name = "status")
    private Integer status;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
