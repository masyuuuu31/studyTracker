package com.example.studytracker.exception;

/**
 * パスワード不一致時に発生する例外
 * @author Ritsu.Inoue
 */
public class PasswordMismatchException extends RuntimeException{
    public PasswordMismatchException(String message) {
        super(message);
    }
}
