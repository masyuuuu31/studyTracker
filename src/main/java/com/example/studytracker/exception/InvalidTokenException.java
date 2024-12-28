package com.example.studytracker.exception;

/**
 * 無効なトークン指定時に発生する例外
 * @author Ritsu.Inoue
 */
public class InvalidTokenException extends RuntimeException{
    public InvalidTokenException(String message) {
        super(message);
    }
}
