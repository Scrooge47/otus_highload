package ru.avseenkov.social.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import ru.avseenkov.social.model.User;
import ru.avseenkov.social.repository.user.UserRepositoryImpl;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Transactional
class UserRepositoryImplTest {

    @Autowired
    UserRepositoryImpl userRepository;

    User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setUsername("username");
        user.setPassword("password");
        user.setFirstName("name");
        user.setLastName("surname");
    }

    @Test
    void save() {
    }

    @Test
    void saveUser() {
        User userDb = userRepository.save(user);
        Assertions.assertNotNull(user.getId());
    }

    @Test
    void findById() {
        User userDb = userRepository.save(user);
        User savedUser = userRepository.findById(userDb.getId()).get();
        Assertions.assertEquals(user.getLastName().toUpperCase(), savedUser.getLastName());
        Assertions.assertEquals(user.getId(), savedUser.getId());
        Assertions.assertEquals(user.getPassword(), savedUser.getPassword());

    }

    @Test
    void findByFistNameAndLastName() {
        User user1 = new User();
        user1.setUsername("username1");
        user1.setPassword("password");
        user1.setFirstName("name");
        user1.setLastName("surname");

        User userDb = userRepository.save(user);
        User userDb1 = userRepository.save(user1);

        List<User> users = userRepository.findUserByFistNameAndLastName("name", "sur");

        assertThat(users, hasSize(2));

    }

    @Test
    void findByFistNameAndLastNameNoResult() {
        User user1 = new User();
        user1.setUsername("username1");
        user1.setPassword("password");
        user1.setFirstName("name");
        user1.setLastName("surname");

        User userDb = userRepository.save(user);
        User userDb1 = userRepository.save(user1);

        List<User> users = userRepository.findUserByFistNameAndLastName("abramov", "sur");

        assertThat(users, hasSize(0));

    }

}