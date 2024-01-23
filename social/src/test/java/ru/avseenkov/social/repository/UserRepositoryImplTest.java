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
        Assertions.assertEquals(user.getLastName(), savedUser.getLastName());
        Assertions.assertEquals(user.getId(), savedUser.getId());
        Assertions.assertEquals(user.getPassword(), savedUser.getPassword());

    }
}