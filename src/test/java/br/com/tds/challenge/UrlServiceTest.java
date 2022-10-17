package br.com.tds.challenge;

import br.com.tds.challenge.dtos.UrlDTO;
import br.com.tds.challenge.models.Url;
import br.com.tds.challenge.repositories.UrlRepository;
import br.com.tds.challenge.services.UrlService;
import br.com.tds.challenge.services.utils.UrlCodificationUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UrlServiceTest {
    @Mock
    UrlRepository mockUrlRepository;

    @InjectMocks
    UrlService urlService;

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
//    TODO: Adjust Tests
//    @Test
//    public void convertToShortUrlTest() {
//        Url url = new Url();
//        url.setLongUrl("https://github.com/AnteMarin/UrlShortener-API");
//        url.setCreationDate(LocalDate.now());
//        url.setId(16L);
//
//        when(mockUrlRepository.save(any(Url.class))).thenReturn(url);
//        when(urlService.encode(url.getId())).thenReturn("f");
//
//        UrlDTO urlRequest = new UrlDTO();
//        urlRequest.setLongUrl("https://github.com/AnteMarin/UrlShortener-API");
//
//        assertEquals("f", urlService.convertToShortUrl(urlRequest));
//    }
//
//    @Test
//    public void getOriginalUrlTest() {
//        when(urlService.decode("h")).thenReturn((long) 7);
//
//        Url url = new Url();
//        url.setLongUrl("https://github.com/AnteMarin/UrlShortener-API");
//        url.setCreationDate(LocalDate.now());
//        url.setId(7L);
//
//        when(mockUrlRepository.findById((long) 7)).thenReturn(java.util.Optional.of(url));
//        assertEquals("https://github.com/AnteMarin/UrlShortener-API", urlService.getOriginalUrl("h"));
//
//    }
}
