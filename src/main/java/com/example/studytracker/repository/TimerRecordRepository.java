package com.example.studytracker.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.studytracker.entity.TimerRecord;

public interface TimerRecordRepository extends JpaRepository<TimerRecord, Long>{

    @Query(
        value = "SELECT * FROM timer_records WHERE main_task_id = :mainTaskId", nativeQuery = true
    )
    List<TimerRecord> findByMainTask(@Param("mainTaskId") Long mainTaskId);
}
