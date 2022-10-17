package br.com.tds.challenge.utils;

import br.com.tds.challenge.services.utils.UrlCodificationUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class UrlCodificationUtilTest {
    @Test
    public void encode_lessThan62() {
        assertEquals("k", UrlCodificationUtil.encode(10));
    }

    @Test
    public void encode_moreThan62() {
        assertEquals("bq", UrlCodificationUtil.encode(78));
    }

    @Test
    public void decode_singleCharacter() {
        assertEquals(11, UrlCodificationUtil.decode("l"));
    }
}
