package ru.avseenkov.social.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    private Long id;

    private String firstName;

    private String lastName;

    private byte age;

    private byte gender;

    private String interest;

    private String city;
}
