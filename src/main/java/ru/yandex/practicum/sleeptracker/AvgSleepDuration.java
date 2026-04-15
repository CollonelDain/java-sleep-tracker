package ru.yandex.practicum.sleeptracker;

import java.time.Duration;
import java.util.List;

public class AvgSleepDuration implements SleepAnalyzer {
    @Override
    public SleepAnalysisResult<Long> apply(List<SleepingSession> sessions) {
        long sumSleepDuration = sessions.stream()
                .map(SleepingSession::getDuration)
                .mapToLong(Duration::toMinutes)
                .sum();
        int countSleepSession = sessions.size();

        return new SleepAnalysisResult<>(
                "Средняя продолжительность сессии сна (в минутах)",
                (countSleepSession == 0) ? 0 : sumSleepDuration / countSleepSession
        );
    }
}
