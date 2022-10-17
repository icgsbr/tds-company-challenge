package br.com.tds.challenge.controllers;

import br.com.tds.challenge.dtos.UrlDTO;
import br.com.tds.challenge.models.Url;
import br.com.tds.challenge.services.UrlService;
import br.com.tds.challenge.services.utils.ConsoleLogUtil;
import br.com.tds.challenge.services.utils.UrlValidationUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/")
public class UrlController {
    //region INJECTIONS
    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }
    //endregion

    //region ENDPOINTS
    @GetMapping()
    public ResponseEntity<List<Url>> getAllUrls() {
        if (urlService.findAllUrls().isEmpty()) {
            ConsoleLogUtil.log.info("NO CONTENT: No url to be found");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(urlService.findAllUrls());
        }
        return ResponseEntity.status(HttpStatus.FOUND).body(urlService.findAllUrls());
    }

    @PostMapping("shorten")
    public ResponseEntity<String> convertToShortUrl(@RequestBody UrlDTO urlDTO) {
        return UrlValidationUtil.isUrlValid(urlDTO.getLongUrl()) ?
                ResponseEntity.status(HttpStatus.CREATED).body(urlService.convertToShortUrl(urlDTO)) :
                ResponseEntity.status(HttpStatus.CONFLICT).body("CONFLICT: Given url is not valid");
    }

    @GetMapping(value = "{shortUrlComplement}")
    public ResponseEntity<Void> getAndRedirect(@PathVariable String shortUrlComplement) {
        if (urlService.findByShortUrl(urlService.createShortUrl(shortUrlComplement)) != null) {
            String url = urlService.getOriginalUrl(shortUrlComplement);
            urlService.registerAccess(urlService.createShortUrl(shortUrlComplement));
            return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(url)).build();
        }
        return null;
    }

    @GetMapping(value = "{shortUrlComplement}/metrics")
    public ResponseEntity<ArrayList<String>> getUrlMetrics(@PathVariable String shortUrlComplement) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(urlService.showUrlMetrics(urlService.createShortUrl(shortUrlComplement)));
    }
    //endregion
}
