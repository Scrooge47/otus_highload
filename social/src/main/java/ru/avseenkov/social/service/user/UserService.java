package ru.avseenkov.social.service.user;

import org.springframework.security.core.userdetails.UserDetails;
import ru.avseenkov.social.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto getUser(Long id);

    UserDetails loadUserByUsername(String username);

    List<UserDto> getUsers();

    List<UserDto> findUserByFirstNameAndLastName(String first_name, String last_name);

    void addFriend(Long userId, Long userRequestedId);

    void deleteFriend(Long userId, Long userRequestedId);
}
