package com.example.studytracker.exception;

/**
 * ユーザーID重複時に発生する例外
 * @author Ritsu.Inoue
 */
public class DuplicateUserException extends RuntimeException{
    public DuplicateUserException(String message) {
        super(message);
    }
}
