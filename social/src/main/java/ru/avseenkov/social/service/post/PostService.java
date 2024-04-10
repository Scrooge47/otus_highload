package ru.avseenkov.social.service.post;

import ru.avseenkov.social.dto.NewPostDto;
import ru.avseenkov.social.dto.PostDto;


public interface PostService {

    PostDto createPost(Long id, NewPostDto post);

    PostDto getPost(Long id);

    void deletePost(Long userId, Long id);

}
