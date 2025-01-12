package com.example.studytracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.studytracker.entity.TaskComment;

public interface TaskCommentRepository extends JpaRepository<TaskComment, Long> {
}
