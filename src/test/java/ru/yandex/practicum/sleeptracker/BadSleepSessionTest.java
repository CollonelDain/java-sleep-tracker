package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BadSleepSessionTest {
    private static BadSleepSession func;

    @BeforeAll
    static void init() {
        func = new BadSleepSession();
    }


    @Test
    public void shouldReturnZeroForEmptySleepingSessions() {
        var response = func.apply(List.of());

        assertEquals("Количество сессий с плохим качеством сна", response.getDescription());
        assertEquals(0, response.getResult());
    }

    @Test
    public void shouldReturnOneBadNight() {
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

        assertEquals("Количество сессий с плохим качеством сна", response.getDescription());
        assertEquals(1, response.getResult());
    }

    @Test
    public void shouldReturnAllBadNight() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(
                        SleepQuality.BAD,
                        LocalDateTime.of(2026,4,14,20,15),
                        LocalDateTime.of(2026,4,14,21,15)
                ),
                new SleepingSession(
                        SleepQuality.BAD,
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

        assertEquals("Количество сессий с плохим качеством сна", response.getDescription());
        assertEquals(3, response.getResult());
    }
}
