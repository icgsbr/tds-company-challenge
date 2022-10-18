package br.com.tds.challenge.controllers;

import br.com.tds.challenge.dtos.UrlDTO;
import br.com.tds.challenge.models.Url;
import br.com.tds.challenge.services.UrlService;
import br.com.tds.challenge.services.utils.ConsoleLogUtil;
import br.com.tds.challenge.services.utils.GetBaseUrlUtil;
import br.com.tds.challenge.services.utils.UrlValidationUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * API controller class
 */
@RestController
@RequestMapping("/api")
@Api(value = "Url Shortener API REST")
@CrossOrigin(origins = "*")
public class UrlController {
    //region INJECTIONS
    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }
    //endregion

    //region ENDPOINTS
    @GetMapping("/urls")
    @ApiOperation(value = "Returns all urls in the database in form of an url list")
    public ResponseEntity<List<Url>> getAllUrls() {
        if (urlService.findAllUrls().isEmpty()) {
            ConsoleLogUtil.log.info("NO CONTENT: No url to be found");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(urlService.findAllUrls());
        }
        return ResponseEntity.status(HttpStatus.FOUND).body(urlService.findAllUrls());
    }

    @PostMapping("/url/shorten")
    @ApiOperation(value = "Transform given url into a shorter version")
    public ResponseEntity<String> convertToShortUrl(HttpServletRequest request, @RequestBody UrlDTO urlDTO) {
        urlService.updateBaseUrl(GetBaseUrlUtil.getBaseUrl(request));

        return UrlValidationUtil.isUrlValid(urlDTO.getLongUrl()) ?
                ResponseEntity.status(HttpStatus.CREATED).body(urlService.convertToShortUrl(urlDTO)) :
                ResponseEntity.status(HttpStatus.CONFLICT).body("CONFLICT: Given url is not valid");
    }

    @GetMapping(value = "url/{shortUrlComplement}")
    @ApiOperation(value = "Accesses the original url path")
    public ResponseEntity<Void> getAndRedirect(@PathVariable String shortUrlComplement) {
        if (urlService.findByShortUrl(urlService.createShortUrl(shortUrlComplement)) != null) {
            String url = urlService.getOriginalUrl(shortUrlComplement);
            urlService.registerAccess(urlService.createShortUrl(shortUrlComplement));
            return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(url)).build();
        }
        return null;
    }

    @GetMapping(value = "url/{shortUrlComplement}/metrics")
    @ApiOperation(value = "Get given url access metrics and analytics")
    public ResponseEntity<ArrayList<String>> getUrlMetrics(@PathVariable String shortUrlComplement) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(urlService.showUrlMetrics(urlService.createShortUrl(shortUrlComplement)));
    }
    //endregion
}
