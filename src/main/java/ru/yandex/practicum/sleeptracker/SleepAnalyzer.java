package ru.yandex.practicum.sleeptracker;

import java.util.List;

@FunctionalInterface
public interface SleepAnalyzer {
    String analyze(List<SleepingSession> sessions);
}
