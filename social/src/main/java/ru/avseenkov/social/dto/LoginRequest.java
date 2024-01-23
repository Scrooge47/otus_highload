package ru.avseenkov.social.dto;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {

    @Size(min = 4, max = 50)
    @NotNull
    private String username;

    @Size(min = 6, max = 255)
    @NotNull
    private String password;
}
