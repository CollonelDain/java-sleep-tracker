package ru.yandex.practicum.sleeptracker;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.LongStream;

public class UserChronotype implements SleepAnalyzer {
    @Override
    public SleepAnalysisResult<String> apply(List<SleepingSession> sessions) {
        SleepingSession firstSession = sessions.getFirst();
        SleepingSession lastSession = sessions.getLast();

        LocalDate firstNight = firstSession.getStart().getHour() < 12
                ? firstSession.getStart().toLocalDate()
                : firstSession.getStart().toLocalDate().plusDays(1);
        LocalDate lastNight = lastSession.getEnd().getHour() > 0
                ? lastSession.getEnd().toLocalDate()
                : lastSession.getEnd().toLocalDate().minusDays(1);


        List<LocalDate> allNights = LongStream.rangeClosed(0, ChronoUnit.DAYS.between(firstNight, lastNight))
                .mapToObj(firstNight::plusDays)
                .toList();

        List<Chronotype> allNightsChronotype = allNights.stream()
                .map(night -> classifyNight(sessions, night))
                .filter(Objects::nonNull)
                .toList();

        int owl = Collections.frequency(allNightsChronotype, Chronotype.OWL);
        int lark = Collections.frequency(allNightsChronotype, Chronotype.LARK);
        int pigeon = Collections.frequency(allNightsChronotype, Chronotype.PIGEON);

        if (owl > lark && owl > pigeon) return new SleepAnalysisResult<>("Хронотип пользователя", Chronotype.OWL.getTitle());
        if (lark > owl && lark > pigeon) return new SleepAnalysisResult<>("Хронотип пользователя", Chronotype.LARK.getTitle());
        return new SleepAnalysisResult<>("Хронотип пользователя", Chronotype.PIGEON.getTitle());
    }

    private Chronotype classifyNight(List<SleepingSession> sessions, LocalDate night) {
        List<SleepingSession> nightSessions = sessions.stream()
                .filter(session -> isIntersectNight(session, night))
                .toList();

        if (nightSessions.isEmpty()) {
            return null;
        }

        LocalDateTime startNight = nightSessions.stream()
                .map(SleepingSession::getStart)
                .min(LocalDateTime::compareTo)
                .orElseThrow();

        LocalDateTime endNight = nightSessions.stream()
                .map(SleepingSession::getEnd)
                .max(LocalDateTime::compareTo)
                .orElseThrow();

        LocalTime startTime = startNight.toLocalTime();
        LocalTime endTime = endNight.toLocalTime();

        return (startTime.isBefore(LocalTime.of(22, 0)) && endTime.isBefore(LocalTime.of(7, 0)))
                ? Chronotype.LARK
                : (startTime.isAfter(LocalTime.of(23, 0)) && endTime.isAfter(LocalTime.of(9, 0)))
                ? Chronotype.OWL
                : Chronotype.PIGEON;
    }

    private boolean isIntersectNight(SleepingSession session, LocalDate night) {
        LocalDateTime nightStart = night.atStartOfDay();
        LocalDateTime nightEnd = nightStart.plusHours(6);
        return session.getStart().isBefore(nightEnd) && session.getEnd().isAfter(nightStart);
    }
}