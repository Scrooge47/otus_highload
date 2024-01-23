package ru.avseenkov.social.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {

    @Size(min = 5, max = 50)
    @NotNull
    private String username;

    @Size(min = 8, max = 255)
    @NotNull
    private String password;

    private String firstName;

    private String lastName;

    private byte age;

    private byte gender;

    private String interest;

    private String city;

}
