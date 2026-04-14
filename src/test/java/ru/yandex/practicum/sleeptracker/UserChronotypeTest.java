package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserChronotypeTest {
    private static UserChronotype func;

    @BeforeAll
    static void init() {
        func = new UserChronotype();
    }

    @Test
    void shouldReturnLarkForAllLarkNights() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(
                        SleepQuality.GOOD,
                        LocalDateTime.of(2026, 4, 1, 21, 0),
                        LocalDateTime.of(2026, 4, 2, 6, 0)
                )
        );
        var response = func.apply(sessions);

        assertEquals("Хронотип пользователя", response.getDescription());
        assertEquals("ЖАВОРОНОК", response.getResult());
    }

    @Test
    void shouldReturnOwlWhenMajorityAreOwls() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(
                        SleepQuality.GOOD,
                        LocalDateTime.of(2026, 4, 1, 23, 30),
                        LocalDateTime.of(2026, 4, 2, 9, 30)
                ),
                new SleepingSession(
                        SleepQuality.GOOD,
                        LocalDateTime.of(2026, 4, 2, 23, 45),
                        LocalDateTime.of(2026, 4, 3, 10, 0)
                ),
                new SleepingSession(
                        SleepQuality.GOOD,
                        LocalDateTime.of(2026, 4, 3, 21, 0),
                        LocalDateTime.of(2026, 4, 4, 6, 0)
                )
        );
        var response = func.apply(sessions);

        assertEquals("Хронотип пользователя", response.getDescription());
        assertEquals("СОВА", response.getResult());
    }

    @Test
    void shouldReturnPigeonWhenCountsAreEqual() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(
                        SleepQuality.GOOD,
                        LocalDateTime.of(2026, 4, 1, 23, 30),
                        LocalDateTime.of(2026, 4, 2, 9, 30)
                ),
                new SleepingSession(
                        SleepQuality.GOOD,
                        LocalDateTime.of(2026, 4, 2, 21, 0),
                        LocalDateTime.of(2026, 4, 3, 6, 0)
                )
        );
        var response = func.apply(sessions);

        assertEquals("Хронотип пользователя", response.getDescription());
        assertEquals("ГОЛУБЬ", response.getResult());
    }

    @Test
    void shouldReturnPigeonForBoundaryTimes() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(
                        SleepQuality.GOOD,
                        LocalDateTime.of(2026, 4, 1, 22, 0),
                        LocalDateTime.of(2026, 4, 2, 6, 0)
                )
        );
        var response = func.apply(sessions);

        assertEquals("Хронотип пользователя", response.getDescription());
        assertEquals("ГОЛУБЬ", response.getResult());
    }
}
