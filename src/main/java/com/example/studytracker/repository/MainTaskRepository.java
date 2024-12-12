package com.example.studytracker.repository;

import com.example.studytracker.entity.MainTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MainTaskRepository extends JpaRepository<MainTask, Long> {
}
