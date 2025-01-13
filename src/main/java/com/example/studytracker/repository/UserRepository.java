package com.example.studytracker.repository;

import com.example.studytracker.entity.MUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * ユーザー情報のリポジトリインターフェース
 * ユーザーの検索、登録、更新、削除の機能を提供する
 * @author Ritsu.Inoue
 */
@Repository
public interface UserRepository extends JpaRepository<MUser, String> {
}
