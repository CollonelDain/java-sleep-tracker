package ru.yandex.practicum.sleeptracker;

import java.util.List;

public class TotalSleepSession implements SleepAnalyzer {
    @Override
    public String analyze(List<SleepingSession> sessions) {
        return String.valueOf(sessions.size());
    }
}
