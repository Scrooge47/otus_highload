package ru.avseenkov.social.repository.feed;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.avseenkov.social.model.Post;

import java.util.List;

@AllArgsConstructor
@Repository
public class FeedRepositoryImpl implements FeedRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Post> getFeed(Long userId) {
        String sql = "SELECT p.text, p.user_id FROM feeds as f INNER JOIN posts as p ON f.post_id = p.id  WHERE  f.user_id = ?  ORDER BY p.created_at LIMIT 1000";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper(Post.class), userId).stream().toList();
    }

    @Override
    public void addToFeed(Long userId, Long postId) {
        String sql = "INSERT INTO feeds (user_id, post_id) VALUES(?, ?)";
        jdbcTemplate.update(sql, userId, postId);
    }
}
