package com.avseenkov.dialogs.dto;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DialogDto {

    @NotNull
    private String from;

    @NotNull
    private String to;

    @NotEmpty
    private String text;

}
