package day4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class Day4 {
    public static void main(String[] args) throws IOException {
        // Read input
        Path inputPath = Paths.get("day4/input.txt");
        List<String> cards = Files.readAllLines(inputPath);

        // Iterate over cards
        int totalPoints = 0;
        int totalCards = 0;
        int[] wins = new int[cards.size()];
        int[] numCards = new int[cards.size()];
        for (int i = 0; i < cards.size(); i++) {
            // Part 1
            String card = cards.get(i);
            wins[i] = countWins(card);
            if (wins[i] > 0) {
                totalPoints += Math.pow(2, wins[i] - 1);
            }
            // Part 2
            int numCopies = numCards[i] + 1; // Always count original of each card
            int numWins = wins[i];
            for (int j = i + 1; j < i + 1 + numWins && j < wins.length; j++) {
                numCards[j] += numCopies; // Add one new copy per each existing copy
            }
            totalCards += numCopies;
        }
        System.out.println("Part One Answer: " + totalPoints);
        System.out.println("Part Two Answer: " + totalCards);
    }

    private static int countWins(String card) {
        String[] cardParts = card.replaceAll("Card\\s+\\d+:", "").split("\\|");
        List<String> winningNums = Arrays.asList(cardParts[0].trim().split("\\s+"));
        List<String> ourNums = Arrays.asList(cardParts[1].trim().split("\\s+"));
        int wins = 0;
        for (String num : ourNums) {
            if (winningNums.contains(num)) {
                wins++;
            }
        }
        return wins;
    }
}
