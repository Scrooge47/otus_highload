package ru.avseenkov.social.dto;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostDto {

    @NotNull
    private Long id;

    @NotEmpty
    private String text;

    @NotEmpty
    private Long userId;
}
