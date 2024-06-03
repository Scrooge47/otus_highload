package com.avseenkov.dialogs.dto;


import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewDialogDto {

    @NotEmpty
    private String text;
}
