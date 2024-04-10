package ru.avseenkov.social.repository.post;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.avseenkov.social.exception.ModelNotFoundException;
import ru.avseenkov.social.model.Post;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;


@Repository
@AllArgsConstructor
public class PostRepositoryImpl implements PostRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Post getPostFromDb(Long id) {
        return findById(id).orElseThrow(() ->
                new ModelNotFoundException("Post with id = %s not found".formatted(id)));
    }

    @Override
    public Post save(Long userId, Post post) {
        String sql = "INSERT INTO posts (user_id , text) VALUES(?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con ->
        {
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setLong(1, userId);
            ps.setString(2, post.getText());
            return ps;
        }, keyHolder);

        post.setId(keyHolder.getKey().longValue());
        post.setUserId(userId);
        return post;
    }

    @Override
    public Optional<Post> findById(Long id) {
        String sql = "SELECT * FROM posts where id = ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Post.class), id).stream().findAny();
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM posts where id = ?";
        jdbcTemplate.update(sql, id);
    }

}
