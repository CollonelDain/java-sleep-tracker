package ru.yandex.practicum.sleeptracker;

import java.time.Duration;
import java.util.List;

public class MaxSleepDuration implements SleepAnalyzer {
    @Override
    public SleepAnalysisResult<Long> apply(List<SleepingSession> sessions) {
        Duration maxDuration = sessions.stream()
                .map(SleepingSession::getDuration)
                .max(Duration::compareTo)
                .orElse(Duration.ZERO);

        return new SleepAnalysisResult<>(
                "Максимальная продолжительность сессии сна (в минутах)",
                maxDuration.toMinutes()
        );
    }
}
