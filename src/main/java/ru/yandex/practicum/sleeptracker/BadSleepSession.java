package ru.yandex.practicum.sleeptracker;

import java.util.List;

public class BadSleepSession implements SleepAnalyzer {
    @Override
    public SleepAnalysisResult<Long> apply(List<SleepingSession> sessions) {
        Long badSleep = sessions.stream()
                .filter(x -> x.getQuality().toString().equals("BAD"))
                .count();

        return new SleepAnalysisResult<>(
                "Количество сессий с плохим качеством сна",
                badSleep
        );
    }
}