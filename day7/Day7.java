package day7;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day7 {
    public static void main(String[] args) throws IOException {
        // Read input
        Path inputPath = Paths.get("day7/input.txt");
        List<String> lines = Files.readAllLines(inputPath);
        HashMap<String, Integer> hands = new HashMap<>();

        // Parse hands and bids from input
        for (String line : lines) {
            String[] splitLine = line.split(" ");
            hands.put(splitLine[0], Integer.parseInt(splitLine[1]));
        }

        // Order hands from weakest to strongest
        String[] partOneHandOrder = hands.keySet().toArray(new String[hands.size()]);
        String[] partTwoHandOrder = partOneHandOrder.clone();
        Arrays.sort(partOneHandOrder, new StrengthComparator(false));
        Arrays.sort(partTwoHandOrder, new StrengthComparator(true));

        // Sum up the winnings for each hand
        int partOneTotalWinnings = 0;
        int partTwoTotalWinnings = 0;
        int bid, rank;
        for (int i = 0; i < partOneHandOrder.length; i++) {
            rank = i + 1;
            // Part 1
            bid = hands.get(partOneHandOrder[i]);
            partOneTotalWinnings += bid * rank;
            // Part 2
            bid = hands.get(partTwoHandOrder[i]);
            partTwoTotalWinnings += bid * rank;
        }

        System.out.println("Part One Answer: " + partOneTotalWinnings);
        System.out.println("Part Two Answer: " + partTwoTotalWinnings);
    }
}

class StrengthComparator implements Comparator<String> {
    private static boolean useJokers = false;

    public StrengthComparator(boolean useJokers) {
        StrengthComparator.useJokers = useJokers;
    }

    @Override
    public int compare(String handA, String handB) {
        int compareResult = getType(handA) - getType(handB);
        if (compareResult == 0) {
            // Hands tied. Compare first card to determine winner
            return compareFirstCard(handA, handB);
        }
        return compareResult;
    }

    private static int getType(String hand) {
        HashMap<Character, Integer> cardMap = new HashMap<>();
        for (char card : hand.toCharArray()) {
            cardMap.putIfAbsent(card, 0);
            cardMap.put(card, cardMap.get(card) + 1);
        }
        if (useJokers) {
            Integer numJokers = cardMap.remove('J');
            if (numJokers != null) {
                // Add all jokers to the card with the most copies
                int maxCopies = 0;
                char mostCopiedCard = 'J'; // Pick arbitrary starting card
                for (Character card : cardMap.keySet()) {
                    int cardCopies = cardMap.get(card);
                    if (cardCopies > maxCopies) {
                        maxCopies = cardCopies;
                        mostCopiedCard = card;
                    }
                }
                cardMap.put(mostCopiedCard, maxCopies + numJokers);
            }
        }
        int uniqueChars = cardMap.size();
        if (uniqueChars == 1) {
            return 7; // Five of a kind
        }
        if (uniqueChars == 2) {
            if (cardMap.values().contains(4)) {
                return 6; // Four of a kind
            }
            return 5; // Full house
        }
        if (uniqueChars == 3) {
            if (cardMap.values().contains(3)) {
                return 4; // Three of a kind
            }
            return 3; // Two pair
        }
        if (uniqueChars == 4) {
            return 2; // One pair
        }
        return 1; // High card
    }

    private static int compareFirstCard(String handA, String handB) {
        if (handA.equals(handB)) {
            return 0; // Shortcut if hands are equal
        }
        Map<Character, Integer> cardRanks = Map.ofEntries(
                Map.entry('A', 13),
                Map.entry('K', 12),
                Map.entry('Q', 11),
                Map.entry('J', useJokers ? 0 : 10),
                Map.entry('T', 9),
                Map.entry('9', 8),
                Map.entry('8', 7),
                Map.entry('7', 6),
                Map.entry('6', 5),
                Map.entry('5', 4),
                Map.entry('4', 3),
                Map.entry('3', 2),
                Map.entry('2', 1));
        for (int i = 0; i < handA.length(); i++) {
            int cardARank = cardRanks.get(handA.charAt(i));
            int cardBRank = cardRanks.get(handB.charAt(i));
            int rankComparison = cardARank - cardBRank;
            if (rankComparison != 0) {
                return rankComparison;
            }
        }
        return 0; // Both hands are equivalent
    }
}
