package ru.avseenkov.social.service.user;

import org.springframework.security.core.userdetails.UserDetails;
import ru.avseenkov.social.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto getUser(Long id);

    UserDetails loadUserByUsername(String username);

    List<UserDto> getUsers();
}