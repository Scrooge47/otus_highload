package ru.avseenkov.social.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import ru.avseenkov.social.model.Post;
import ru.avseenkov.social.service.post.RedisPostSubscriber;

@Configuration
public class RedisConfig {

    @Value("${spring.data.redis.host}")
    private String REDIS_HOST;
    @Value("${spring.data.redis.port}")
    private Integer REDIS_PORT;

    @Bean
    public JedisConnectionFactory jedisConnection() {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(REDIS_HOST, REDIS_PORT);
        return new JedisConnectionFactory(config);
    }

    @Bean
    public RedisTemplate<String, Post> redisTemplate() {
        final RedisTemplate<String, Post> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnection());
        return template;
    }

    @Bean
    public MessageListenerAdapter messageListener() {
        return new MessageListenerAdapter(new RedisPostSubscriber());
    }

    @Bean
    public RedisMessageListenerContainer redisContainer() {
        RedisMessageListenerContainer container
                = new RedisMessageListenerContainer();
        container.setConnectionFactory(jedisConnection());
        container.addMessageListener(messageListener(), topic());
        return container;
    }

    @Bean
    public ChannelTopic topic() {
        return new ChannelTopic("postQueue");
    }
}
