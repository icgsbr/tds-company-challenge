package br.com.tds.challenge.services.utils;

import org.springframework.stereotype.Component;

import java.net.URL;

/**
 * Utility class to validate url
 */
@Component
public class UrlValidationUtil {
    /**
     * Check whether url is valid/real or invalid/not real
     * @param url
     * @return boolean
     */
    public static boolean isUrlValid(String url) {
        try {
            new URL(url).toURI();
            ConsoleLogUtil.log.info("URL VALIDATION: VALID URL");
            return true;
        } catch (Exception e) {
            ConsoleLogUtil.log.info("URL VALIDATION: INVALID URL");
            return false;
        }
    }
}
