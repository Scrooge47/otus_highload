package ru.avseenkov.social.repository;

import ru.avseenkov.social.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    User save(User user);

    Optional<User> findById(Long id);

    Optional<User> findByUsername(String username);

    User getUserFromDb(Long id);

    List<User> getUsers();
}
