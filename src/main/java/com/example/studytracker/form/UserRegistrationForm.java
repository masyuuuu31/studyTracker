package com.example.studytracker.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserRegistrationForm {

    @NotBlank
    @Email
    private String userId;

    @NotBlank
    private String userName;

    @NotBlank
    private String password;
}
