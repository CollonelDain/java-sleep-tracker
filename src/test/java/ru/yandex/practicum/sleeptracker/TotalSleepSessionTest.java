package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TotalSleepSessionTest {
    private static TotalSleepSession func;

    @BeforeAll
    static void init() {
        func = new TotalSleepSession();
    }


    @Test
    public void shouldReturnZeroForEmptySleepingSessions() {
        var response = func.apply(List.of());

        assertEquals("Общее количество сессий сна", response.getDescription());
        assertEquals(0, response.getResult());
    }

    @Test
    public void shouldReturnThreeSleepingSession() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(
                        SleepQuality.GOOD,
                        LocalDateTime.of(2026,4,14,20,15),
                        LocalDateTime.of(2026,4,15,3,15)
                ),
                new SleepingSession(
                        SleepQuality.NORMAL,
                        LocalDateTime.of(2026,4,15,20,15),
                        LocalDateTime.of(2026,4,16,3,15)
                ),
                new SleepingSession(
                        SleepQuality.BAD,
                        LocalDateTime.of(2026,4,16,20,15),
                        LocalDateTime.of(2026,4,17,3,15)
                )
        );
        var response = func.apply(sessions);

        assertEquals("Общее количество сессий сна", response.getDescription());
        assertEquals(3, response.getResult());
    }
}
