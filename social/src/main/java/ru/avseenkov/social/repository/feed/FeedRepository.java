package ru.avseenkov.social.repository.feed;


import ru.avseenkov.social.model.Post;

import java.util.List;

public interface FeedRepository {

    List<Post> getFeed(Long userId);

    void addToFeed(Long userId, Long postId);
}
