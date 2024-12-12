package com.example.studytracker.repository;

import com.example.studytracker.entity.MUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<MUser, String> {
}
