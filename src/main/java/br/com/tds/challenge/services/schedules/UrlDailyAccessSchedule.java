package br.com.tds.challenge.services.schedules;

import br.com.tds.challenge.models.Url;
import br.com.tds.challenge.services.UrlService;
import br.com.tds.challenge.services.utils.ConsoleLogUtil;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Component
public class UrlDailyAccessSchedule {
    //region INJECTIONS
    final UrlService urlService;

    public UrlDailyAccessSchedule(UrlService urlService) {
        this.urlService = urlService;
    }
    //endregion

    //region LOGIC
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(cron = "0 * * ? * *")
    public void calculateDailyAccessAverage() {
        List<Url> allUrls = urlService.findAllUrls();
        LocalDateTime currentDay = LocalDateTime.now();

        allUrls.forEach(url -> {
            Double passedDays = (double) url.getCreationDate().compareTo(currentDay) * -1;

            urlService.calculateDailyAccessAverage(url.getId(), passedDays, url.getNumberOfAccesses());
        });

        ConsoleLogUtil.log.info("DAILY ACCESS SCHEDULE: Finished urls daily average accesses update at {}", dateFormat.format(new Date()));
    }
    //endregion
}
