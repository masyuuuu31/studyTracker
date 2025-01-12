package com.example.studytracker.constant;

public enum UserAuthority {

    GENERAL(0, "一般ユーザー"),
    ADMIN(1,"管理者");

    private final int code;
    private final String displayName;

    UserAuthority(int code, String displayName) {
        this.code = code;
        this.displayName = displayName;
    }

    public int getCode() {
        return code;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static UserAuthority getByCode(int code) {
        for (UserAuthority authority : values()) {
            if (authority.getCode() == code) {
                return authority;
            }
        }
        throw new IllegalArgumentException("Invalid authority code: " + code);
    }

}
