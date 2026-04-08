package ru.yandex.practicum.sleeptracker;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.LongStream;

public class SleeplessNights implements SleepAnalyzer {
    @Override
    public SleepAnalysisResult<Integer> apply(List<SleepingSession> sessions) {
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

        List<LocalDate> nightsWithSleep = sessions.stream()
                .flatMap(session -> {
                    LocalDate startDate = session.getStart().toLocalDate();
                    LocalDate endDate = session.getEnd().toLocalDate();
                    return LongStream.rangeClosed(0, ChronoUnit.DAYS.between(startDate, endDate))
                            .mapToObj(startDate::plusDays)
                            .filter(date -> {
                                LocalDateTime nightStart = date.atStartOfDay();
                                LocalDateTime nightEnd = nightStart.plusHours(6);
                                return session.getStart().isBefore(nightEnd) && session.getEnd().isAfter(nightStart);
                            });
                })
                .distinct()
                .toList();

        return new SleepAnalysisResult<>("Количество бессонных ночей", allNights.size() - nightsWithSleep.size());
    }
}
