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
    public void convertToShortUrlTest() {
        Url url = new Url("https://github.com/tdscompany/desafio-backend-encurtador-url");
        url.setId(5L);

        when(mockUrlRepository.save(any(Url.class))).thenReturn(url);
        assertEquals("f", UrlCodificationUtil.encode(url.getId()));

        UrlDTO urlDTO = new UrlDTO("https://github.com/tdscompany/desafio-backend-encurtador-url");

        assertEquals("http://localhost:8080/f", urlService.convertToShortUrl(urlDTO));
    }

    @Test
    public void getOriginalUrlTest() {
        assertEquals(7L, UrlCodificationUtil.decode("h"));

        Url url = new Url("https://github.com/tdscompany/desafio-backend-encurtador-url");
        url.setId(7L);

        when(mockUrlRepository.findById(7L)).thenReturn(java.util.Optional.of(url));
        assertEquals("https://github.com/tdscompany/desafio-backend-encurtador-url", urlService.getOriginalUrl("h"));
    }

    @Test
    public void dailyAccessCalculation() {
        Url url = new Url("https://www.youtube.com/");
        url.setId(1L);

        Double passedDays = 2.0;
        Integer numberOfAccesses = 10;

        double result = (numberOfAccesses / passedDays);

        assertEquals("5.0", Double.toString(result));

        urlService.calculateDailyAccessAverage(url.getId(), passedDays, numberOfAccesses);
    }
}
