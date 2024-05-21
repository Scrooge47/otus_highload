package ru.avseenkov.social.bootstrap;

import com.github.javafaker.Faker;
import io.tarantool.driver.api.TarantoolClient;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.avseenkov.social.utils.Helper;

import java.util.Locale;


@Component
@ConditionalOnProperty(prefix = "loadData", value = "dialog", havingValue = "true")
@AllArgsConstructor
public class DataLoader {

    private final TarantoolClient client;

//    @Bean
//    public CommandLineRunner loadDataUser(@Qualifier("base01JdbcTemplate") JdbcTemplate jdbcTemplate) {
//        return args -> {
//            Faker faker = new Faker(new Locale("ru-RU"));
//            Faker fakerEn = new Faker(new Locale("en-En"));
//            List<User> users = new ArrayList<>();
//            User user;
//            for (int i = 0; i < 2_000_000; i++) {
//                user = new User();
//                user.setFirstName(faker.name().firstName());
//                user.setLastName(faker.name().lastName());
//                user.setUsername(fakerEn.name().username() + i);
//                user.setAge((byte) faker.number().numberBetween(18, 120));
//                user.setGender((byte) faker.number().numberBetween(0, 1));
//                user.setPassword(faker.crypto().md5());
//                users.add(user);
//            }
//
//            String insert = "INSERT INTO users (username, first_name, last_name, age, password, gender) VALUES(?, ?, ?, ?, ?, ?)";
//
//            PreparedStatement ps = jdbcTemplate.getDataSource().getConnection().prepareStatement(insert);
//
//            for (User user1 : users) {
//                ps.setString(1, user1.getUsername());
//                ps.setString(2, user1.getFirstName().toUpperCase());
//                ps.setString(3, user1.getLastName().toUpperCase());
//                ps.setByte(4, user1.getAge());
//                ps.setString(5, user1.getPassword());
//                ps.setByte(6, user1.getGender());
//                ps.addBatch();
//            }
//
//            ps.executeLargeBatch();
//        };
//    }

    //    @Bean
//    public CommandLineRunner loadDataDialog(@Qualifier("base01JdbcTemplate") JdbcTemplate jdbcTemplate) {
//        return args -> {
//            Faker faker = new Faker(new Locale("ru-RU"));
//            Faker fakerEn = new Faker(new Locale("en-En"));
//            String insert = "INSERT INTO dialogs (\"from\", \"to\", key_Id, text) VALUES(?, ?, ?, ?)";
//
//
//            for (int i = 1; i < 100; i++) {
//                PreparedStatement ps = jdbcTemplate.getDataSource().getConnection().prepareStatement(insert);
//                for (int j = 1; j < 100; j++) {
//
//                    String keyId = Helper.getKeyId((long) i, (long) j);
//
//                    for (int k = 1; k < 1000; k++) {
//
//                        ps.setLong(1, i);
//                        ps.setLong(2, j);
//                        ps.setString(3, keyId);
//                        ps.setString(4, faker.lorem().paragraph());
//                        ps.addBatch();
//
//                    }
//
//                }
//                ps.executeLargeBatch();
//            }
//        };
//    }
//
//    @Bean
//    public CommandLineRunner loadDataDialogTarantool(@Qualifier("base01JdbcTemplate") JdbcTemplate jdbcTemplate) {
//        return args -> {
//            Faker faker = new Faker(new Locale("ru-RU"));
//
//            for (int i = 1; i < 100; i++) {
//
//                for (int j = 1; j < 100; j++) {
//
//                    String keyId = Helper.getKeyId((long) i, (long) j);
//
//                    for (int k = 1; k < 1000; k++) {
//
//                        String text = faker.lorem().paragraph();
//
//                        client.call("addDialog", i, j, text, keyId);
//                    }
//
//                }
//            }
//        };
//    }
}
