package ru.avseenkov.social.service.post;

import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;
import ru.avseenkov.social.model.Post;

@Service
@AllArgsConstructor
public class RedisMessagePublisher implements PostPublisher {

    private final RedisTemplate<String, Post> redisTemplate;
    private final ChannelTopic topic;

    @Override
    public void publish(Post post) {
        redisTemplate.convertAndSend(topic.getTopic(), post);
    }
}
