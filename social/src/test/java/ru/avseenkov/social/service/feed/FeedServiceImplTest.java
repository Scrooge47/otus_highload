package ru.avseenkov.social.service.feed;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.jdbc.JdbcTestUtils;
import ru.avseenkov.social.dto.PostDto;
import ru.avseenkov.social.mapper.PostMapper;
import ru.avseenkov.social.model.Post;
import ru.avseenkov.social.model.User;
import ru.avseenkov.social.repository.feed.CacheFeedRepositoryImpl;
import ru.avseenkov.social.repository.feed.FeedRepositoryImpl;
import ru.avseenkov.social.repository.post.PostRepositoryImpl;
import ru.avseenkov.social.repository.user.UserRepositoryImpl;
import ru.avseenkov.social.service.post.PostServiceImpl;

import java.util.ArrayList;
import java.util.List;


@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class FeedServiceImplTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    UserRepositoryImpl userRepository;

    @Autowired
    PostServiceImpl postService;

    @Autowired
    PostRepositoryImpl postRepository;

    @Autowired
    FeedServiceImpl feedService;

    @Autowired
    CacheFeedRepositoryImpl cacheFeedRepository;

    @Autowired
    FeedRepositoryImpl feedRepository;

    List<User> users;

    List<Post> posts;

    @BeforeEach
    @AfterEach
    void clearDataBase() {
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "friends", "feeds", "posts", "users");
    }


    @BeforeEach
    void setUp() {


    }

    @Test
    public void getFeedFromDbWithCache() throws InterruptedException {

        users = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            User user = new User();
            user.setUsername("username" + i);
            user.setPassword("password");
            user.setFirstName("first_name" + i);
            user.setLastName("last_name" + i);

            User userDb = userRepository.save(user);
            users.add(userDb);
        }

        userRepository.addFriend(users.get(0).getId(), users.get(1).getId());
        userRepository.addFriend(users.get(0).getId(), users.get(2).getId());

        posts = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            Post post = new Post();
            post.setText("post_post_" + i);
            posts.add(post);
        }

        postService.createPost(users.get(1).getId(), PostMapper.NewPostDtoFromPost(posts.get(0)));
        postService.createPost(users.get(1).getId(), PostMapper.NewPostDtoFromPost(posts.get(1)));
        postService.createPost(users.get(2).getId(), PostMapper.NewPostDtoFromPost(posts.get(2)));

        Thread.sleep(2000);
        List<PostDto> postsDtoList = feedService.getFeed(users.get(0).getId(), 0, 3);
        Assertions.assertEquals(postsDtoList.size(), 3);

        List<Post> cachePosts = cacheFeedRepository.getFeed(users.get(0).getId(), 1, 3);
        Assertions.assertEquals(cachePosts.size(), 2);

    }

    @Test
    public void getFeedFromDb() {

        users = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            User user = new User();
            user.setUsername("username" + i);
            user.setPassword("password");
            user.setFirstName("first_name" + i);
            user.setLastName("last_name" + i);

            User userDb = userRepository.save(user);
            users.add(userDb);
        }

        userRepository.addFriend(users.get(0).getId(), users.get(1).getId());
        userRepository.addFriend(users.get(0).getId(), users.get(2).getId());

        posts = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            Post post = new Post();
            post.setText("post_post_" + i);
            posts.add(post);
        }

        Post post = postRepository.save(users.get(1).getId(), posts.get(0));
        feedRepository.addToFeed(users.get(0).getId(), post.getId());
        post = postRepository.save(users.get(1).getId(), posts.get(1));
        feedRepository.addToFeed(users.get(0).getId(), post.getId());
        post =postRepository.save(users.get(2).getId(), posts.get(2));
        feedRepository.addToFeed(users.get(0).getId(), post.getId());

        List<PostDto> postsDtoList = feedService.getFeed(users.get(0).getId(), 0, 3);
        Assertions.assertEquals(postsDtoList.size(), 3);

        List<Post> cachePosts = cacheFeedRepository.getFeed(users.get(0).getId(), 1, 3);
        Assertions.assertEquals(cachePosts.size(), 2);

    }
}