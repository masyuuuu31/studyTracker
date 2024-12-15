package com.example.studytracker.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.studytracker.entity.PasswordResetToken;


/**
 * パスワードリセットトークンのリポジトリインターフェース
 * トークンの検索、登録、削除の機能を提供する
 * @author Ritsu.Inoue
 */
@Repository
public interface PasswordResetRepository extends JpaRepository<PasswordResetToken, Long>{

    /**
     * トークン文字列に一致するパスワードリセットトークンを取得する
     * @param token 検索対象のトークン文字列
     * @return トークンエンティティ。該当するトークンが存在しない場合はEmpty
     */
    @Query(
        value = "SELECT * FROM password_reset_tokens WHERE token = :token",
        nativeQuery = true
    )
    Optional<PasswordResetToken>findByToken(@Param("token")String token);
    
    /**
     * 指定されたユーザーIDに紐づく全てのパスワードリセットトークンを取得する
     * @param userId 検索対象のユーザーID
     * @return パスワードリセットトークンのリスト。該当するトークンが存在しない場合は空のリスト
     */
    @Query(
        value = "SELECT * FROM password_reset_tokens WHERE user_id = :userId",
        nativeQuery = true
        )
    List<PasswordResetToken>findByUserId(@Param("userId")String userId);
}
