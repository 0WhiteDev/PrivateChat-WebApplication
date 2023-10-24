package uk.whitedev.chat.utils;

import java.io.*;

public class Encryption {
    private static final StringBuilder builder = new StringBuilder();
    private static final String allowedChars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%^&*()-=_+[]{};:\"'\\/|<>,./?";

    public static String encrypt(Object input) {
        builder.setLength(0);

        for (char object : String.valueOf(input).toCharArray()) {
            if (allowedChars.contains(String.valueOf(object))) {
                builder.append(Character.toChars(object * 354));
            } else {
                builder.append(object);
            }
        }
        return builder.toString();
    }

    public static String decrypt(Object input) {
        builder.setLength(0);
        for (char object : String.valueOf(input).toCharArray()) {
            if (!isEmoji(String.valueOf(object))) {
                builder.append(Character.toChars(object / 354));
            } else {
                builder.append(object);
            }
        }
        return builder.toString();
    }

    public static boolean isEmoji(String input) {
        try (InputStream is = Encryption.class.getClassLoader().getResourceAsStream("emojis.txt");
             BufferedReader reader = new BufferedReader(new InputStreamReader(is))){
            String line = reader.readLine();
            while (line != null) {
                if (line.contains(input)) {
                    return true;
                }
                line = reader.readLine();
            }
            return false;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
