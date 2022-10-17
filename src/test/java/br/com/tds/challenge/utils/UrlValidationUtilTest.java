package br.com.tds.challenge.utils;

import br.com.tds.challenge.services.utils.UrlValidationUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class UrlValidationUtilTest {

    @Test
    public void noHttpUrl() {
        assertFalse(UrlValidationUtil.isUrlValid("music.youtube.com/watch?v=vOqa8N9ZfO0&list=PLwB574QUeT-3lTNC8-DDXSfItPljokw2X"));
    }

    @Test
    public void conventionalUrlInput() {
        assertFalse(UrlValidationUtil.isUrlValid("www.youtube.com"));
    }

    @Test
    public void urlWithoutHttpAndComplement() {
        assertFalse(UrlValidationUtil.isUrlValid("github.com"));
    }

    @Test
    public void urlWithoutComplement() {
        assertTrue(UrlValidationUtil.isUrlValid("https://www.baeldung.com"));
    }

    @Test
    public void validAndCompleteUrl() {
        assertTrue(UrlValidationUtil.isUrlValid("https://github.com/tdscompany/desafio-backend-encurtador-url"));
    }
}
