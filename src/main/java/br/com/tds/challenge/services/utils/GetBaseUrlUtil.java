package br.com.tds.challenge.services.utils;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;

/**
 * Utility class to get API base url
 */
@Component
public class GetBaseUrlUtil {
    /**
     * Get base url from API request URI
     * @param request
     * @return String
     */
    public static String getBaseUrl(HttpServletRequest request) {

        return ServletUriComponentsBuilder
                .fromRequestUri(request)
                .replacePath(null)
                .build()
                .toUriString();
    }
}
