package ru.avseenkov.social.service.post;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import ru.avseenkov.social.dto.NewPostDto;
import ru.avseenkov.social.dto.PostDto;
import ru.avseenkov.social.exception.ConflictException;
import ru.avseenkov.social.mapper.PostMapper;
import ru.avseenkov.social.model.Post;
import ru.avseenkov.social.model.User;
import ru.avseenkov.social.repository.post.PostRepository;
import ru.avseenkov.social.repository.user.UserRepository;

@Service
@AllArgsConstructor
@Primary
public class PostServiceImpl implements PostService {
    private UserRepository userRepository;
    private PostRepository postRepository;

    private PostPublisher postPublisher;

    @Override
    public PostDto createPost(Long userId, NewPostDto post) {
        User user = userRepository.getUserFromDb(userId);
        Post postDb = postRepository.save(user.getId(), PostMapper.postFromNewPostDto(post));
        postPublisher.publish(postDb);
        return PostMapper.postDtoFromPost(postDb);
    }

    @Override
    public PostDto getPost(Long id) {
        return PostMapper.postDtoFromPost(postRepository.getPostFromDb(id));
    }

    @Override
    public void deletePost(Long userId, Long id) {
        User user = userRepository.getUserFromDb(userId);
        Post post = postRepository.getPostFromDb(id);

        if (user.getId() != post.getUserId()) {
            throw new ConflictException("User can delete only own posts");
        }

        postRepository.delete(post.getId());
    }
}
