package ru.avseenkov.social.bootstrap;

import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.avseenkov.social.model.User;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


@Component
@ConditionalOnProperty(prefix = "loadData", value = "user", havingValue = "true")
public class DataLoader {

    @Bean
    public CommandLineRunner loadDataUser(@Qualifier("base01JdbcTemplate") JdbcTemplate jdbcTemplate) {
        return args -> {
            Faker faker = new Faker(new Locale("ru-RU"));
            Faker fakerEn = new Faker(new Locale("en-En"));
            List<User> users = new ArrayList<>();
            User user;
            for (int i = 0; i < 2_000_000; i++) {
                user = new User();
                user.setFirstName(faker.name().firstName());
                user.setLastName(faker.name().lastName());
                user.setUsername(fakerEn.name().username() + i);
                user.setAge((byte) faker.number().numberBetween(18, 120));
                user.setGender((byte) faker.number().numberBetween(0, 1));
                user.setPassword(faker.crypto().md5());
                users.add(user);
            }

            String insert = "INSERT INTO users (username, first_name, last_name, age, password, gender) VALUES(?, ?, ?, ?, ?, ?)";

//            jdbcTemplate.batchUpdate(insert, users, 100000, (ps, user1) -> {
//                ps.setString(1, user1.getUsername());
//                ps.setString(2, user1.getFirstName());
//                ps.setString(3, user1.getLastName());
//                ps.setByte(4, user1.getAge());
//                ps.setString(5, user1.getPassword());
//            });

            PreparedStatement ps = jdbcTemplate.getDataSource().getConnection().prepareStatement(insert);

            for (User user1 : users) {
                ps.setString(1, user1.getUsername());
                ps.setString(2, user1.getFirstName().toUpperCase());
                ps.setString(3, user1.getLastName().toUpperCase());
                ps.setByte(4, user1.getAge());
                ps.setString(5, user1.getPassword());
                ps.setByte(6, user1.getGender());
                ps.addBatch();
            }

            ps.executeLargeBatch();
        };
    }
}
