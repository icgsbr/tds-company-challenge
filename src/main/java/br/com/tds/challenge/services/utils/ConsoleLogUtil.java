package br.com.tds.challenge.services.utils;

import br.com.tds.challenge.services.schedules.UrlDailyAccessSchedule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Utility class to ease up spring boot log utilization
 */
@Component
public class ConsoleLogUtil {
    //region VARIABLES
    public static final Logger log = LoggerFactory.getLogger(UrlDailyAccessSchedule.class);
    //endregion
}
