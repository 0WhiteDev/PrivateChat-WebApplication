package uk.whitedev.chat.utils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Random;

public class TokenActions {
    public static String generateToken(){
        int length = 40;
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(characters.length());
            char randomChar = characters.charAt(randomIndex);
            stringBuilder.append(randomChar);
        }
        return stringBuilder.toString();
    }

    public static int generateChannelId(){
        Random random = new Random();
        StringBuilder generatedNumber = new StringBuilder();
        for (int i = 0; i < 9; i++) {
            int digit = random.nextInt(10);
            generatedNumber.append(digit);
        }
        return Integer.parseInt(generatedNumber.toString());
    }

    public static String getTokenFromCookie(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token-cookie")) {
                    return cookie.getValue();
                }
            }
        }
        return "no-token";
    }
}
