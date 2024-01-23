package ru.avseenkov.social.utils;

import ru.avseenkov.social.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ObjectFromResultSet {
    public static User makeUser(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setId(rs.getLong("id"));
        user.setCity(rs.getString("city"));
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password"));
        user.setAge(rs.getByte("age"));
        user.setGender(rs.getByte("gender"));
        user.setFirstName(rs.getString("first_name"));
        user.setLastName(rs.getString("last_name"));
        user.setInterest(rs.getString("interest"));
        return user;
    }
}
