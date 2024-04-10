package ru.avseenkov.social.repository.feed;

import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import ru.avseenkov.social.model.Post;

import java.util.List;


@AllArgsConstructor
@Repository
public class CacheFeedRepositoryImpl implements CacheFeedRepository {

    private final RedisTemplate<String, Post> redisTemplate;

    @Override
    public List<Post> getFeed(Long userId, int offset, int limit) {
        ListOperations<String, Post> listOperations = redisTemplate.opsForList();
        return listOperations.range(userId.toString(), offset, limit);
    }

    @Override
    public void addPostInFeed(Long userId, Post post) {
        ListOperations<String, Post> listOperations = redisTemplate.opsForList();

        listOperations.leftPush(userId.toString(), post);
        if (listOperations.size(userId.toString()) > 1000) {
            listOperations.rightPop(userId.toString());
        }
    }

    @Override
    public void addFeed(Long userId, List<Post> posts) {
        ListOperations<String, Post> listOperations = redisTemplate.opsForList();
        listOperations.leftPushAll(userId.toString(), posts);
    }
}
