package ru.avseenkov.social.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
public class Post implements Serializable {
    private Long id;

    private String text;

    private Long userId;

    private LocalDateTime createdAt;
}
