package ru.yandex.practicum.sleeptracker;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public class SleepTrackerApp {
    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");
    static List<SleepAnalyzer> metrics = List.of(
            new TotalSleepSession(),
            new MaxSleepDuration(),
            new MinSleepDuration(),
            new AvgSleepDuration(),
            new BadSleepSession()
    );

    public static void main(String[] args) {
        Path log;
        try {
            if (args.length < 1) {
                throw new IllegalArgumentException("Не передан путь к файлу.");
            }

            String logStr = args[0];

            if (logStr == null || logStr.trim().isEmpty()) {
                throw new IllegalArgumentException("Путь к файлу пустой.");
            }

            log = Paths.get(logStr);

            if (!Files.exists(log) || Files.isDirectory(log)) {
                throw new FileNotFoundException(log.toString());
            }
        } catch (FileNotFoundException exc) {
            System.err.printf("Файл с именем %s не найден.%n", exc.getMessage());
            return;
        } catch (Exception exc) {
            System.err.println(exc.getMessage());
            return;
        }

        List<SleepingSession> sessions = parseLog(log);

        metrics.stream()
                .map(func -> func.apply(sessions))
                .map(SleepAnalysisResult::getFormatMessage)
                .forEach(System.out::println);

    }

    public static List<SleepingSession> parseLog(Path filePath) {
        try (Stream<String> lines = Files.lines(filePath)) {
            return lines
                    .filter(line -> !line.isBlank())
                    .map(SleepTrackerApp::parseLine)
                    .toList();
        } catch (IOException exc) {
            System.err.println("Ошибка при работе с файлом: " + exc.getMessage());
            return Collections.emptyList();
        }
    }

    private static SleepingSession parseLine(String line) {
        String[] partsLine = line.split(";");
        return new SleepingSession(
                SleepQuality.valueOf(partsLine[2]),
                LocalDateTime.parse(partsLine[0], formatter),
                LocalDateTime.parse(partsLine[1], formatter)
        );
    }
}
