package ru.avseenkov.social.dto;


import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewPostDto {

    @NotEmpty
    private String text;
}
