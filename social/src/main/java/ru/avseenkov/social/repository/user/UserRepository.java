package ru.avseenkov.social.repository.user;

import ru.avseenkov.social.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    User save(User user);

    Optional<User> findById(Long id);

    Optional<User> findByUsername(String username);

    User getUserFromDb(Long id);

    List<User> getUsers();

    List<User> findUserByFistNameAndLastName(String first_name, String last_name);

    void addFriend(Long userId, Long userRequestedId);

    void removeFriend(Long userId, Long userRequestedId);

    List<User> getFriends(Long userId);
}
