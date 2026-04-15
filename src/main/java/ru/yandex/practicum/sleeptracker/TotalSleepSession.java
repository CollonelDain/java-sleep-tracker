package ru.yandex.practicum.sleeptracker;

import java.util.List;

public class TotalSleepSession implements SleepAnalyzer {
    @Override
    public SleepAnalysisResult<Integer> apply(List<SleepingSession> sessions) {
        return new SleepAnalysisResult<>(
                "Общее количество сессий сна",
                (sessions == null) ? 0 : sessions.size()
        );
    }
}
