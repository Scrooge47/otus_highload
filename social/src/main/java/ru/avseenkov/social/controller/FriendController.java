package ru.avseenkov.social.controller;


import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.avseenkov.social.model.User;
import ru.avseenkov.social.service.user.UserService;

@RestController
@AllArgsConstructor
@RequestMapping("/friend")
public class FriendController {

    private final UserService userService;

    @PutMapping("/set/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public void addFriend(@AuthenticationPrincipal User user, @PathVariable Long userId) {
        userService.addFriend(user.getId(), userId);

    }

    @DeleteMapping("/delete/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteFriend(@AuthenticationPrincipal User user, @PathVariable Long userId) {
        userService.deleteFriend(user.getId(), userId);
    }

}
