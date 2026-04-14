package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SleeplessNightsTest {
    private static SleeplessNights func;

    @BeforeAll
    static void init() {
        func = new SleeplessNights();
    }

    @Test
    void shouldReturnZeroForSessionCoversFullNight() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(
                        SleepQuality.GOOD,
                        LocalDateTime.of(2026, 4, 1, 23, 0),
                        LocalDateTime.of(2026, 4, 2, 7, 0)
                )
        );
        var response = func.apply(sessions);

        assertEquals("Количество бессонных ночей", response.getDescription());
        assertEquals(0, response.getResult());
    }

    @Test
    void shouldReturnOneForSessionIsDaytimeOnly() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(
                        SleepQuality.GOOD,
                        LocalDateTime.of(2026, 4, 1, 10, 0),
                        LocalDateTime.of(2026, 4, 2, 12, 0)
                )
        );
        var response = func.apply(sessions);

        assertEquals("Количество бессонных ночей", response.getDescription());
        assertEquals(1, response.getResult());
    }

    @Test
    void shouldNotCountNightForSessionEndsExactlyAtSix() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(
                        SleepQuality.GOOD,
                        LocalDateTime.of(2026, 4, 1, 23, 0),
                        LocalDateTime.of(2026, 4, 2, 6, 0)
                )
        );
        var response = func.apply(sessions);

        assertEquals("Количество бессонных ночей", response.getDescription());
        assertEquals(0, response.getResult());
    }

    @Test
    void shouldDetectOneSleeplessNightBetweenTwoSessions() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(
                        SleepQuality.GOOD,
                        LocalDateTime.of(2026, 4, 1, 22, 0),
                        LocalDateTime.of(2026, 4, 2, 2, 0)
                ),
                new SleepingSession(
                        SleepQuality.GOOD,
                        LocalDateTime.of(2026, 4, 3, 10, 0),
                        LocalDateTime.of(2026, 4, 3, 12, 0)
                )
        );
        var response = func.apply(sessions);

        assertEquals("Количество бессонных ночей", response.getDescription());
        assertEquals(1, response.getResult());
    }
}
