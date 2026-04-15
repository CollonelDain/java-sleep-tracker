package ru.yandex.practicum.sleeptracker;

import java.time.Duration;
import java.time.LocalDateTime;

public class SleepingSession {
    private final LocalDateTime start;
    private final LocalDateTime end;
    private final SleepQuality quality;

    public SleepingSession(SleepQuality quality, LocalDateTime start, LocalDateTime end) {
        this.quality = quality;
        this.start = start;
        this.end = end;
    }

    @Override
    public String toString() {
        return "SleepingSession{" +
                "start=" + start +
                ", end=" + end +
                ", quality=" + quality +
                '}';
    }

    public Duration getDuration() {
        return Duration.between(start, end);
    }

    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public SleepQuality getQuality() {
        return quality;
    }

}
