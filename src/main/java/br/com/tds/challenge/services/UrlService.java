package br.com.tds.challenge.services;

import br.com.tds.challenge.dtos.UrlDTO;
import br.com.tds.challenge.models.Url;
import br.com.tds.challenge.repositories.UrlRepository;
import br.com.tds.challenge.services.utils.ConsoleLogUtil;
import br.com.tds.challenge.services.utils.UrlCodificationUtil;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class UrlService {
    //region INJECTIONS
    private final UrlRepository urlRepository;

    public UrlService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }
    //endregion

    //region VARIABLES
    private String baseUrl;
    //endregion

    //region METHODS
    public List<Url> findAllUrls() {
        return urlRepository.findAll();
    }

    public Url findByShortUrl(String shortUrl) {
        return urlRepository.findByShortUrl(shortUrl).orElseThrow(() ->
                new EntityNotFoundException("There is no entity with " + shortUrl));
    }

    public void updateBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String createShortUrl(String shortUrlComplement) {
        if (Objects.equals(this.baseUrl, "http://localhost:8080")) {
          return this.baseUrl + "/api/url/" + shortUrlComplement;
        }

        return this.baseUrl + "/" + shortUrlComplement;
    }

    public void updateLastAccessDate(Long id, LocalDateTime lastAccess) {
        urlRepository.updateLastAccessDate(id, lastAccess);
    }

    public void updateAccessQuantity(Long id) {
        urlRepository.updateNumberOfAccesses(id);
    }

    @Modifying
    @Transactional
    public String convertToShortUrl(UrlDTO urlDTO) {
        Url url = new Url(urlDTO.getLongUrl());
        Url entity = urlRepository.save(url);
        String shortUrlComplement = UrlCodificationUtil.encode(entity.getId());

        urlRepository.updateShortUrl(entity.getId(), createShortUrl(shortUrlComplement));

        return createShortUrl(shortUrlComplement);
    }

    public String getOriginalUrl(String shortUrlComplement) {
        Long id = UrlCodificationUtil.decode(shortUrlComplement);
        Url entity = urlRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("There is no entity with " + shortUrlComplement));

        return entity.getLongUrl();
    }

    @Modifying
    public void registerAccess(String shortUrl) {
        if (urlRepository.findByShortUrl(shortUrl).isPresent()) {
            updateAccessQuantity(urlRepository.findByShortUrl(shortUrl).get().getId());
            updateLastAccessDate(urlRepository.findByShortUrl(shortUrl).get().getId(), LocalDateTime.now());
            ConsoleLogUtil.log.info("ACCESS REGISTRATION: Access registered successfully");
        } else {
            ConsoleLogUtil.log.info("ACCESS REGISTRATION: Not registered. Entity not found");
        }
    }

    @Modifying
    public void calculateDailyAccessAverage(Long id, Double passedDays, Integer numberOfAccesses) {
        if (passedDays > 0) {
            Double result = numberOfAccesses / passedDays;
            urlRepository.updateDailyAccessAverage(id, result);
        }

        urlRepository.updateDailyAccessAverage(id, (double) numberOfAccesses);
    }

    public ArrayList<String> showUrlMetrics(String shortUrl) {
        Url url = findByShortUrl(shortUrl);

        ArrayList<String> metricsArrayList = new ArrayList<>();

        metricsArrayList.add("Creation Date: "
                + url.getCreationDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        metricsArrayList.add("Average Daily Access: "
                + url.getDailyAccessAverage().toString());
        metricsArrayList.add(url.getLastAccessDate() == null ?
                "Last Access Date: Not Accessed Yet" :
                "Last Access Date: " + url.getLastAccessDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        metricsArrayList.add("Total Accesses: "
                + url.getNumberOfAccesses().toString());

        return metricsArrayList;
    }
    //endregion
}
