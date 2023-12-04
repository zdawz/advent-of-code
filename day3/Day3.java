package day3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day3 {
    public static void main(String[] args) throws IOException {
        // Read input
        Path inputPath = Paths.get("./input.txt");
        List<String> lines = Files.readAllLines(inputPath);

        // Part 1
        int partNumberSum = 0;
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            Pattern numberPattern = Pattern.compile("\\d+");
            Matcher numberMatcher = numberPattern.matcher(line);
            while (numberMatcher.find()) {
                int startIndex = numberMatcher.start();
                int endIndex = numberMatcher.end();
                int partNumber = Integer.parseInt(numberMatcher.group());
                if (findAdjacentSymbol(lines, line, startIndex, endIndex)) {
                    partNumberSum += partNumber;
                    continue;
                }
                if (i > 0) {
                    String prevLine = lines.get(i - 1);
                    if (findAdjacentSymbol(lines, prevLine, startIndex, endIndex)) {
                        partNumberSum += partNumber;
                        continue;
                    }
                }
                if (i < lines.size() - 1) {
                    String nextLine = lines.get(i + 1);
                    if (findAdjacentSymbol(lines, nextLine, startIndex, endIndex)) {
                        partNumberSum += partNumber;
                        continue;
                    }
                }
            }
        }

        // Part 2
        int gearRatioSum = 0;
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            Pattern starPattern = Pattern.compile("\\*");
            Matcher starMatcher = starPattern.matcher(line);
            while (starMatcher.find()) {
                int startIndex = starMatcher.start();
                int endIndex = starMatcher.end();
                List<Integer> adjacentNumbers = new ArrayList<>();
                findAdjacentNumbers(lines, line, startIndex, endIndex, adjacentNumbers);
                if (i > 0) {
                    String prevLine = lines.get(i - 1);
                    findAdjacentNumbers(lines, prevLine, startIndex, endIndex, adjacentNumbers);
                }
                if (i < lines.size() - 1) {
                    String nextLine = lines.get(i + 1);
                    findAdjacentNumbers(lines, nextLine, startIndex, endIndex, adjacentNumbers);
                }
                if (adjacentNumbers.size() == 2) {
                    gearRatioSum += adjacentNumbers.get(0) * adjacentNumbers.get(1);
                }
            }
        }

        System.out.println("Part One Answer: " + partNumberSum);
        System.out.println("Part Two Answer: " + gearRatioSum);
    }

    private static boolean findAdjacentSymbol(List<String> lines, String line, int startIndex, int endIndex) {
        int adjacentStart = startIndex > 0 ? startIndex - 1 : startIndex;
        int adjacentEnd = endIndex < lines.size() - 1 ? endIndex + 1 : endIndex;
        Pattern symbolPattern = Pattern.compile("[^\\w\\s\\d.]");
        Matcher symbolMatcher = symbolPattern.matcher(line);
        while (symbolMatcher.find()) {
            int symbolStart = symbolMatcher.start();
            int symbolEnd = symbolMatcher.end();
            // The symbol is adjacent if its range overlaps with the adjacent range
            if (symbolStart < adjacentEnd && adjacentStart < symbolEnd) {
                return true;
            }
        }
        return false;
    }

    private static void findAdjacentNumbers(List<String> lines, String line, int startIndex, int endIndex,
            List<Integer> adjacentNumbers) {
        Pattern numberPattern = Pattern.compile("\\d+");
        Matcher numberMatcher = numberPattern.matcher(line);
        while (numberMatcher.find()) {
            int numberStart = numberMatcher.start();
            int numberEnd = numberMatcher.end();
            int adjacentStart = startIndex >= 1 ? startIndex - 1 : startIndex;
            int adjacentEnd = endIndex < lines.size() - 1 ? endIndex + 1 : endIndex;
            // The number is adjacent if its range overlaps with the adjacent range
            if (numberStart < adjacentEnd && adjacentStart < numberEnd) {
                adjacentNumbers.add(Integer.parseInt(numberMatcher.group()));
            }
        }
    }
}
