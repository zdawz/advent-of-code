package day1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

/**
 * Day1
 */
public class Day1 {

    public static void main(String[] args) throws IOException {
        // Read input
        Path inputPath = Paths.get("day1/input.txt");
        List<String> lines;
        lines = Files.readAllLines(inputPath);

        // Part 1
        int calibrationSum = 0;
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            String calibrationValue = "";
            // Find first digit
            for (int j = 0; j < line.length(); j++) {
                char lineChar = line.charAt(j);
                if (Character.isDigit(lineChar)) {
                    calibrationValue += lineChar;
                    break;
                }
            }
            // Find last digit (or first again)
            for (int j = line.length() - 1; j >= 0; j--) {
                char lineChar = line.charAt(j);
                if (Character.isDigit(lineChar)) {
                    calibrationValue += lineChar;
                    break;
                }
            }
            calibrationSum += Integer.parseInt(calibrationValue);
        }
        System.out.printf("Part One Answer: %s%n", calibrationSum);

        // Part 2
        calibrationSum = 0;
        String[] digitsArray = { "one", "two", "three", "four", "five", "six", "seven", "eight", "nine" };
        List<String> digits = Arrays.asList(digitsArray);
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            String calibrationValue = "";
            // Find first digit
            outerloop: for (int j = 0; j < line.length(); j++) {
                char lineChar = line.charAt(j);
                if (Character.isDigit(lineChar)) {
                    calibrationValue += lineChar;
                    break outerloop;
                } else {
                    for (int k = j; k < line.length() && k < j + 5; k++) {
                        // Check if there is a spelled out digit
                        String word = line.substring(j, k + 1);
                        int indexOfDigit = digits.indexOf(word);
                        if (indexOfDigit != -1) {
                            calibrationValue += Integer.toString(indexOfDigit + 1);
                            break outerloop;
                        }
                    }
                }
            }
            // Find last digit (or first again)
            outerloop: for (int j = line.length() - 1; j >= 0; j--) {
                char lineChar = line.charAt(j);
                if (Character.isDigit(lineChar)) {
                    calibrationValue += lineChar;
                    break outerloop;
                } else {
                    for (int k = j; k >= 0 && k >= j - 5; k--) {
                        // Check if there is a spelled out digit
                        String word = line.substring(k, j + 1);
                        int indexOfDigit = digits.indexOf(word);
                        if (indexOfDigit != -1) {
                            calibrationValue += Integer.toString(indexOfDigit + 1);
                            break outerloop;
                        }
                    }
                }
            }
            calibrationSum += Integer.parseInt(calibrationValue);
        }
        System.out.printf("Part Two Answer: %s%n", calibrationSum);
    }
}