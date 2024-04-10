package ru.avseenkov.social.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.avseenkov.social.dto.NewPostDto;
import ru.avseenkov.social.dto.PostDto;
import ru.avseenkov.social.model.User;
import ru.avseenkov.social.service.feed.FeedService;
import ru.avseenkov.social.service.post.PostService;

import java.util.List;

@RestController
@RequestMapping("post")
@AllArgsConstructor
public class PostController {
    private PostService postService;
    private FeedService feedService;

    @GetMapping("/get/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PostDto getPost(@PathVariable Long id) {
        return postService.getPost(id);
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public PostDto createPost(@AuthenticationPrincipal User user, @RequestBody NewPostDto postDto) {
        return postService.createPost(user.getId(), postDto);
    }

    @DeleteMapping("/delete/{post_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePost(@AuthenticationPrincipal User user, @PathVariable Long post_id) {
        postService.deletePost(user.getId(), post_id);
    }

    @GetMapping("/feed")
    @ResponseStatus(HttpStatus.OK)
    public List<PostDto> getFeed(@AuthenticationPrincipal User user, @RequestParam(defaultValue = "0") int offset, @RequestParam(defaultValue = "10") int limit) {
        return feedService.getFeed(user.getId(), offset, limit);
    }
}
