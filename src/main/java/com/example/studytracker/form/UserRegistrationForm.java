package com.example.studytracker.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRegistrationForm {

    @NotBlank
    @Email
    private String userId;

    @NotBlank
    private String userName;

    @NotBlank
    @Size(min = 12, max = 128)
    private String password;
}
