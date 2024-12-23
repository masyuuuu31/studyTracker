package com.example.studytracker.form.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserLoginForm {

    @NotBlank
    @Email
    private String userId;

    @NotBlank
    @Size(min = 12, max = 128)
    private String password;
}
