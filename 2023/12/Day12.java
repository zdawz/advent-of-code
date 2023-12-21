import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class Day12 {
    public static void main(String[] args) throws IOException {
        // Read input
        Path inputPath = Paths.get("2023/12/input.txt");
        List<String> lines = Files.readAllLines(inputPath);

        int partOneTotal = 0;
        int partTwoTotal = 0;
        for (String line : lines) {
            String[] lineParts = line.split(" ");
            String springs = lineParts[0];
            int[] damagedGroupLengths = Arrays.stream(lineParts[1].split(",")).mapToInt(Integer::parseInt).toArray();

            String[] s = new String[5];
            int[][] d = new int[5][];
            for (int i = 0; i < 5; i++) {
                s[i] = springs;
                d[i] = damagedGroupLengths;
            }
            String unfoldedSprings = String.join("?", s);
            int[] unfoldedDamagedGroupLengths = Arrays.stream(d)
                    .flatMapToInt(Arrays::stream)
                    .toArray();

            partOneTotal += countArrangements(springs, damagedGroupLengths);
            partTwoTotal += countArrangements(unfoldedSprings, unfoldedDamagedGroupLengths);
        }
        System.out.println("Part One Answer: " + partOneTotal);
        System.out.println("Part Two Answer: " + partTwoTotal);
    }

    private static int countArrangements(String springs, int[] damagedGroupLengths) {
        if (springs.length() == 0) {
            return damagedGroupLengths.length == 0 ? 1 : 0;
        }
        if (damagedGroupLengths.length == 0) {
            return springs.indexOf('#') == -1 ? 1 : 0;
        }

        int arrangements = 0;

        char nextSpring = springs.charAt(0);
        int damagedGroupLength = damagedGroupLengths[0];

        if (nextSpring == '.' || nextSpring == '?') {
            arrangements += countArrangements(springs.substring(1, springs.length()), damagedGroupLengths.clone());
        }

        if (nextSpring == '#' || nextSpring == '?') {
            boolean enoughSpringsLeft = damagedGroupLength <= springs.length();
            if (enoughSpringsLeft) {
                boolean damagedGroupIsContiguous = springs.substring(0, damagedGroupLength).indexOf('.') == -1;
                if (damagedGroupIsContiguous) {
                    boolean endsWithOperational = damagedGroupLength == springs.length()
                            || springs.charAt(damagedGroupLength) != '#';
                    if (endsWithOperational) {
                        String nextSprings = damagedGroupLength <= springs.length() - 1
                                ? springs.substring(damagedGroupLength + 1, springs.length())
                                : "";
                        int[] nextDamagedGroupLengths = Arrays.copyOfRange(damagedGroupLengths, 1,
                                damagedGroupLengths.length);
                        arrangements += countArrangements(nextSprings, nextDamagedGroupLengths);
                    }
                }
            }
        }

        return arrangements;
    }
}
