package com.avseenkov.dialogs.model;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Dialog {

    private Long id;

    private Long from;

    private Long to;

    private String text;

    private LocalDateTime createdAt;
}
