package ru.avseenkov.social.mapper;

import ru.avseenkov.social.dto.NewPostDto;
import ru.avseenkov.social.dto.PostDto;
import ru.avseenkov.social.model.Post;

import java.util.ArrayList;
import java.util.List;

public class PostMapper {

    public static Post postFromPostDto(PostDto postDto) {
        Post post = new Post();
        post.setText(postDto.getText());
        post.setId(postDto.getId());
        return post;
    }

    public static Post postFromNewPostDto(NewPostDto postDto) {
        Post post = new Post();
        post.setText(postDto.getText());
        return post;
    }

    public static PostDto postDtoFromPost(Post post) {
        PostDto postDto = new PostDto();
        postDto.setText(post.getText());
        postDto.setId(post.getId());
        postDto.setUserId(post.getUserId());
        return postDto;
    }

    public static NewPostDto NewPostDtoFromPost(Post post) {
        NewPostDto postDto = new NewPostDto();
        postDto.setText(post.getText());
        return postDto;
    }

    public static List<PostDto> mapToPostDto(Iterable<Post> posts) {
        List<PostDto> postsDto = new ArrayList<>();
        for (Post post : posts) postsDto.add(postDtoFromPost(post));
        return postsDto;
    }
}
