package ru.avseenkov.social.repository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.avseenkov.social.exception.ModelNotFoundException;
import ru.avseenkov.social.model.User;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final JdbcTemplate jdbcTemplate;
    private final JdbcTemplate jdbcTemplate02;

    public UserRepositoryImpl(@Qualifier("base01JdbcTemplate") JdbcTemplate jdbcTemplate,
                              @Qualifier("base02JdbcTemplate") JdbcTemplate jdbcTemplate02) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcTemplate02 = jdbcTemplate02;
    }

    @Override
    public Optional<User> findById(Long id) {
        String sql = "SELECT * FROM users where id = ?";

        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class), id).stream().findAny();
    }

    @Override
    public User getUserFromDb(Long id) {
        return findById(id).orElseThrow(() ->
                new ModelNotFoundException("User with id = %s not found".formatted(id)));
    }

    @Override
    public User save(User user) {
        String sql = "INSERT INTO users (username, password, first_name, last_name, age, gender, interest, city) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con ->
        {
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getFirstName().toUpperCase());
            ps.setString(4, user.getLastName().toUpperCase());
            ps.setByte(5, user.getAge());
            ps.setByte(6, user.getGender());
            ps.setString(7, user.getInterest());
            ps.setString(8, user.getCity());

            return ps;
        }, keyHolder);

        user.setId(keyHolder.getKey().longValue());
        return user;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        String sql = "SELECT * FROM users where username = ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class), username).stream().findAny();
    }

    @Override
    public List<User> getUsers() {
        String sql = "SELECT * FROM users";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class));
    }

    @Override
    public List<User> findUserByFistNameAndLastName(String first_name, String last_name) {
        String sql = "SELECT * FROM users WHERE first_name LIKE ? AND last_name LIKE ?";
        return jdbcTemplate02.query(sql, new BeanPropertyRowMapper<>(User.class), first_name.toUpperCase() + "%", last_name.toUpperCase() + "%");
    }
}
