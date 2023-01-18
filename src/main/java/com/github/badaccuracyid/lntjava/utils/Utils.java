package com.github.badaccuracyid.lntjava.utils;

import java.util.List;

public class Utils {

    private static final String[] letters = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

    public static String randomIDGenerator() {
        // MM-XXXX
        StringBuilder id = new StringBuilder();
        for (int i = 0; i < 2; i++) {
            id.append(letters[(int) (Math.random() * letters.length)]);
        }
        id.append("-");
        for (int i = 0; i < 4; i++) {
            id.append((int) (Math.random() * 10));
        }
        return id.toString();
    }

    public static String joinList(List<String> list) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            builder.append(list.get(i));
            if (i != list.size() - 1) {
                builder.append(", ");
            }
        }
        return builder.toString();
    }
}
