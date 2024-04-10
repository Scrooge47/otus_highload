package ru.avseenkov.social.service.feed;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.avseenkov.social.dto.PostDto;
import ru.avseenkov.social.mapper.PostMapper;
import ru.avseenkov.social.model.Post;
import ru.avseenkov.social.model.User;
import ru.avseenkov.social.repository.feed.CacheFeedRepository;
import ru.avseenkov.social.repository.feed.FeedRepository;
import ru.avseenkov.social.repository.post.PostRepository;
import ru.avseenkov.social.repository.user.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class FeedServiceImpl implements FeedService {

    private final CacheFeedRepository cacheFeedRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final FeedRepository feedRepository;

    @Override
    public List<PostDto> getFeed(Long userId, int offset, int limit) {
        List<Post> posts = cacheFeedRepository.getFeed(userId, offset, limit);
        if (!posts.isEmpty()) return PostMapper.mapToPostDto(posts);
        posts = feedRepository.getFeed(userId);
        if (posts.isEmpty()) return new ArrayList<>();
        cacheFeedRepository.addFeed(userId, posts);
        posts = cacheFeedRepository.getFeed(userId, offset, limit);
        return PostMapper.mapToPostDto(posts);
    }

    @Override
    public void addPostToFeed(Long postId) {

        Post post = postRepository.getPostFromDb(postId);
        List<User> friends = userRepository.getFriends(post.getUserId());

        for (User friend : friends) {
            cacheFeedRepository.addPostInFeed(friend.getId(), post);
            feedRepository.addToFeed(friend.getId(), postId);
        }
    }
}
