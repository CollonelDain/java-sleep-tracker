package ru.yandex.practicum.sleeptracker;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

public class SleepTrackerApp {
    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Введите адрес файла: ");
        Path log = Paths.get(sc.nextLine());
        try {
            if (!Files.exists(log) || Files.isDirectory(log)) { throw new FileNotFoundException(log.toString()); }
        } catch (FileNotFoundException exc) {
            System.err.printf("Файл с именем %s не найден.%n", exc.getMessage());
            return;
        }

        List<SleepingSession> sessions = parseLog(log);

        List<SleepAnalyzer> metrics = List.of(
                new TotalSleepSession()
        );

        metrics.forEach(func -> System.out.println(func.analyze(sessions)));

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
