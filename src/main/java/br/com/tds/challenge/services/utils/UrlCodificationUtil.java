package br.com.tds.challenge.services.utils;

import org.springframework.stereotype.Component;

@Component
public class UrlCodificationUtil {
    //region VARIABLES
    private static final String allowedString = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final char[] allowedCharacters = allowedString.toCharArray();
    private static final int base = allowedCharacters.length;
    //endregion

    //region METHODS
    public static String encode(long urlId) {
        StringBuilder encodedString = new StringBuilder();

        if(urlId == 0) {
            return String.valueOf(allowedCharacters[0]);
        }

        while (urlId > 0) {
            encodedString.append(allowedCharacters[(int) (urlId % base)]);
            urlId = urlId / base;
        }

        return encodedString.reverse().toString();
    }

    public static long decode(String shortUrlComplement) {
        char[] characters = shortUrlComplement.toCharArray();

        long decoded = 0;

        //counter is used to avoid reversing input string
        int counter = 1;
        for (char character : characters) {
            decoded += allowedString.indexOf(character) * Math.pow(base, characters.length - counter);
            counter++;
        }

        return decoded;
    }
    //endregion
}
