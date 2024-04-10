package ru.avseenkov.social.service.post;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import ru.avseenkov.social.model.Post;
import ru.avseenkov.social.service.feed.FeedService;
import ru.avseenkov.social.utils.ApplicationContextProvider;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;


@AllArgsConstructor
@NoArgsConstructor
public class RedisPostSubscriber implements MessageListener {

    @Autowired
    private FeedService feedService;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(message.getBody());
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            Post post = (Post) objectInputStream.readObject();
            getFeedService().addPostToFeed(post.getId());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private FeedService getFeedService() {
        return ApplicationContextProvider.getApplicationContext().getBean(FeedService.class);
    }
}
