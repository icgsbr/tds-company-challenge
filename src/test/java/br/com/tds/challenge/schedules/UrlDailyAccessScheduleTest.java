package br.com.tds.challenge.schedules;

import br.com.tds.challenge.services.schedules.UrlDailyAccessSchedule;
import org.awaitility.Durations;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class UrlDailyAccessScheduleTest {
    @Mock
    private UrlDailyAccessSchedule urlDailyAccessSchedule;

    @Test
    public void work_success() {
        for (int i = 0; i < 10; i++) {
            urlDailyAccessSchedule.callDailyAccessAverageCalculation();
        }
        await().atMost(Durations.ONE_SECOND).untilAsserted(() -> verify(urlDailyAccessSchedule, times(10)).callDailyAccessAverageCalculation());
    }
}
