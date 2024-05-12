package ru.avseenkov.social.repository.dialog;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.avseenkov.social.model.Dialog;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
@AllArgsConstructor
public class DialogRepositoryImpl implements DialogRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Dialog save(Long from, Long to, Dialog dialog) {
        String sql = "INSERT INTO dialogs (\"from\" , \"to\", text, key_id) VALUES(?, ?, ?, ?)";

        String keyId = getKeyId(from, to);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con ->
        {
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setLong(1, from);
            ps.setLong(2, to);
            ps.setString(3, dialog.getText());
            ps.setString(4, keyId);
            return ps;
        }, keyHolder);

        dialog.setId(keyHolder.getKey().longValue());
        return dialog;
    }

    @Override
    public List<Dialog> getDialogs(Long from, Long to) {
        String sql = "SELECT * FROM dialogs where key_id = ?";
        String keyId = getKeyId(from, to);
        List<Dialog> dialogs = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Dialog.class), keyId).stream().toList();
        return dialogs;
    }

    private String getKeyId(Long id1, Long id2) {
        if (id1 > id2) return "" + id1 + id2;
        return "" + id2 + id1;
    }
}
