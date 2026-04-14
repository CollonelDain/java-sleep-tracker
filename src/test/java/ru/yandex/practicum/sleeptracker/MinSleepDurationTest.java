package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MinSleepDurationTest {
    private static MinSleepDuration func;

    @BeforeAll
    static void init() {
        func = new MinSleepDuration();
    }


    @Test
    public void shouldReturnZeroForEmptySleepingSessions() {
        var response = func.apply(List.of());

        assertEquals("Минимальная продолжительность сессии сна (в минутах)", response.getDescription());
        assertEquals(0, response.getResult());
    }

    @Test
    public void shouldReturnMinDuration() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(
                        SleepQuality.GOOD,
                        LocalDateTime.of(2026,4,14,20,15),
                        LocalDateTime.of(2026,4,14,21,15)
                ),
                new SleepingSession(
                        SleepQuality.NORMAL,
                        LocalDateTime.of(2026,4,15,20,15),
                        LocalDateTime.of(2026,4,15,22,15)
                ),
                new SleepingSession(
                        SleepQuality.BAD,
                        LocalDateTime.of(2026,4,16,20,15),
                        LocalDateTime.of(2026,4,16,20,45)
                )
        );
        var response = func.apply(sessions);

        assertEquals("Минимальная продолжительность сессии сна (в минутах)", response.getDescription());
        assertEquals(30, response.getResult());
    }

    @Test
    public void shouldReturnMaxDurationForEqualsDuration() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(
                        SleepQuality.GOOD,
                        LocalDateTime.of(2026,4,14,20,15),
                        LocalDateTime.of(2026,4,14,21,15)
                ),
                new SleepingSession(
                        SleepQuality.NORMAL,
                        LocalDateTime.of(2026,4,15,20,15),
                        LocalDateTime.of(2026,4,15,21,15)
                ),
                new SleepingSession(
                        SleepQuality.BAD,
                        LocalDateTime.of(2026,4,16,20,15),
                        LocalDateTime.of(2026,4,17,20,45)
                )
        );
        var response = func.apply(sessions);

        assertEquals("Минимальная продолжительность сессии сна (в минутах)", response.getDescription());
        assertEquals(60, response.getResult());
    }
}
