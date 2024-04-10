package ru.avseenkov.social.service.feed;

import ru.avseenkov.social.dto.PostDto;

import java.util.List;

public interface FeedService {
    List<PostDto> getFeed(Long userId, int offset, int limit);

    void addPostToFeed(Long postId);
}
