package ru.avseenkov.social.utils;

public class Helper {

    public static String getKeyId(Long id1, Long id2) {
        if (id1 > id2) return "" + id1 + id2;
        return "" + id2 + id1;
    }
}
