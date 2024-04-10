package ru.avseenkov.social.repository.feed;

import ru.avseenkov.social.model.Post;

import java.util.List;

public interface CacheFeedRepository {

    List<Post> getFeed(Long userId, int offset, int limit);

    void addPostInFeed(Long userId, Post post);

    void addFeed(Long userId, List<Post> posts);

}
