package com.example.studytracker.constant;

/**
 * タスクのステータスを管理する列挙型
 * @author Ritsu.Inoue
 */
public enum TaskStatus {
 
    NOT_STARTED(0, "未着手"),
    IN_PROGRESS(1, "進行中"),
    COMPLETED(2, "完了"),
    PENDING(3, "保留");

    private final int code;
    private final String displayName;

    TaskStatus(int code, String displayName) {
        this.code = code;
        this.displayName = displayName;
    }

    public int getCode() {
        return code;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static TaskStatus getByCode(int code) {
        for (TaskStatus status : values()) {
            if (status.getCode() == code) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid status code: " + code);
    }
}
