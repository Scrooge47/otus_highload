package ru.avseenkov.social.repository.post;

import ru.avseenkov.social.model.Post;

import java.util.List;
import java.util.Optional;

public interface PostRepository {

    Post save(Long userId, Post post);

    Optional<Post> findById(Long id);

    Post getPostFromDb(Long id);

    void delete(Long id);

}
