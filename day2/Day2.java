package day2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Day2 {

    public static boolean isGamePossible(String[] rounds) {
        HashMap<String, Integer> colorCount = new HashMap<>();
        colorCount.put("red", 12);
        colorCount.put("green", 13);
        colorCount.put("blue", 14);
        for (String round : rounds) {
            Pattern roundPattern = Pattern.compile("(\\d+) (\\w+)");
            Matcher roundMatcher = roundPattern.matcher(round);
            while (roundMatcher.find()) {
                String color = roundMatcher.group(2);
                int count = Integer.parseInt(roundMatcher.group(1));
                int newCount = colorCount.get(color) - count;
                if (newCount < 0) {
                    return false; // Too many of a certain color appeared. The game is impossible
                }
            }
        }
        return true;
    }

    public static int getFewestCubesPower(String[] rounds) {
        HashMap<String, Integer> fewestCubesByColor = new HashMap<>();
        fewestCubesByColor.put("red", 0);
        fewestCubesByColor.put("green", 0);
        fewestCubesByColor.put("blue", 0);
        for (String round : rounds) {
            Pattern roundPattern = Pattern.compile("(\\d+) (\\w+)");
            Matcher roundMatcher = roundPattern.matcher(round);
            while (roundMatcher.find()) {
                String color = roundMatcher.group(2);
                int count = Integer.parseInt(roundMatcher.group(1));
                int currentFewestCubes = fewestCubesByColor.get(color);
                fewestCubesByColor.put(color, Math.max(currentFewestCubes, count));
            }
        }
        return fewestCubesByColor.get("red")
                * fewestCubesByColor.get("green")
                * fewestCubesByColor.get("blue");
    }

    public static void main(String[] args) throws IOException {
        // Read input
        Path inputPath = Paths.get("day2/input.txt");
        List<String> games = Files.readAllLines(inputPath);
        // Initialize variables
        int possibleGamesSum = 0;
        int fewestCubePowersSum = 0;
        // Loop over games
        for (String game : games) {
            // Parse game string
            Pattern gamePattern = Pattern.compile("Game (\\d+): (.*)");
            Matcher gameMatcher = gamePattern.matcher(game);
            gameMatcher.find();
            int gameNumber = Integer.parseInt(gameMatcher.group(1));
            String[] rounds = gameMatcher.group(2).split(";");
            if (isGamePossible(rounds)) {
                possibleGamesSum += gameNumber;
            }
            fewestCubePowersSum += getFewestCubesPower(rounds);
        }
        System.out.println("Part One Answer: " + possibleGamesSum);
        System.out.println("Part Two Answer: " + fewestCubePowersSum);
    }
}
