import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day9 {
    public static void main(String[] args) throws IOException {
        // Read input
        Path inputPath = Paths.get("day9/input.txt");
        List<String> lines = Files.readAllLines(inputPath);

        // Find sum of extrapolated values
        long futureValuesSum = 0;
        long pastValuesSum = 0;
        for (String line : lines) {
            List<int[]> sequences = new ArrayList<>();
            sequences.add(parseSequence(line)); // Initial sequence
            boolean allZeros = false;
            for (int i = 0; !allZeros; i++) {
                int[] nextSequence = getNextSequence(sequences.get(i));
                sequences.add(nextSequence);
                allZeros = isAllZeros(nextSequence);
            }
            // Sequence containing all zeros was found
            // Get the extrapolated value and add it to the sum
            long[] extrapolatedValues = getExtrapolatedValues(sequences);
            futureValuesSum += extrapolatedValues[0];
            pastValuesSum += extrapolatedValues[1];
        }
        System.out.println("Part One Answer: " + futureValuesSum);
        System.out.println("Part Two Answer: " + pastValuesSum);
    }

    private static int[] parseSequence(String line) {
        return Arrays.stream(line.split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();
    }

    private static int[] getNextSequence(int[] sequence) {
        int[] nextSequence = new int[sequence.length - 1];
        for (int i = 0; i < sequence.length - 1; i++) {
            nextSequence[i] = sequence[i + 1] - sequence[i];
        }
        return nextSequence;
    }

    private static boolean isAllZeros(int[] arr) {
        boolean allZeros = true;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] != 0) {
                allZeros = false;
            }
        }
        return allZeros;
    }

    private static long[] getExtrapolatedValues(List<int[]> sequences) {
        long futureValue = 0, pastValue = 0;
        for (int i = sequences.size() - 1; i >= 0; i--) {
            int[] sequence = sequences.get(i);
            int firstValue = sequence[0];
            int lastValue = sequence[sequence.length - 1];
            futureValue += lastValue;
            if (i % 2 == 0) {
                pastValue += firstValue;
            } else {
                pastValue -= firstValue;
            }
        }
        return new long[] { futureValue, pastValue };
    }
}
