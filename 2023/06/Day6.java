import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class Day6 {
    public static void main(String[] args) throws IOException {
        // Read input
        Path inputPath = Paths.get("2023/06/input.txt");
        List<String> lines = Files.readAllLines(inputPath);
        String timeLine = lines.get(0).replaceAll("Time:\\s+", "");
        String distanceLine = lines.get(1).replaceAll("Distance:\\s+", "");

        // Part 1
        Integer[] times = Arrays.stream(timeLine.split("\\s+"))
                .map(Integer::parseInt)
                .toArray(Integer[]::new);
        Integer[] distances = Arrays.stream(distanceLine.split("\\s+"))
                .map(Integer::parseInt)
                .toArray(Integer[]::new);
        System.out.println("Part One Answer: " + partOne(times, distances));

        // Part 2
        double time = Double.parseDouble(timeLine.replaceAll("\\s", ""));
        double distance = Double.parseDouble(distanceLine.replaceAll("\\s", ""));
        System.out.println("Part Two Answer: " + String.format("%.0f", partTwo(time, distance)));
    }

    private static int partOne(Integer[] times, Integer[] distances) {
        int[] waysToWin = new int[times.length];
        for (int i = 0; i < times.length; i++) {
            int time = times[i];
            int record = distances[i];
            for (int timeHeld = 0; timeHeld <= time; timeHeld++) {
                int timeLeft = time - timeHeld;
                int distance = timeHeld * timeLeft;
                if (distance > record) {
                    waysToWin[i]++;
                }
            }
        }
        int product = 1;
        for (int num : waysToWin) {
            product = product * num;
        }
        return product;
    }

    private static double partTwo(double time, double record) {
        int waysToWin = 0;
        for (double timeHeld = 0; timeHeld <= time; timeHeld++) {
            double timeLeft = time - timeHeld;
            double distance = timeHeld * timeLeft;
            if (distance > record) {
                waysToWin++;
            }
        }
        return waysToWin;

        // I got some help from reddit with the code below. But I realized my
        // unoptimized solution works too, so I'll keep both.
        // Use quadratic formula to find upper and lower bounds for time held that ties
        // the record. All whole numbers between these bounds will beat the record.
        // distance = time held * (total time - time held)
        // d = h * (t - h)
        // d = th - h**2
        // h**2 - th + d = 0
        // double upperHeld = (time + Math.sqrt((time * time) - (4 * distance))) / 2;
        // double lowerHeld = (time - Math.sqrt((time * time) - (4 * distance))) / 2;
        // return Math.floor(upperHeld) - Math.ceil(lowerHeld) + 1;
    }
}
