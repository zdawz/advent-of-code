import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class Day12 {
    public static void main(String[] args) throws IOException {
        // Read input
        Path inputPath = Paths.get("2023/12/test.txt");
        List<String> lines = Files.readAllLines(inputPath);

        int totalArrangements = 0;
        for (String line : lines) {
            String[] lineParts = line.split(" ");
            char[] springs = lineParts[0].toCharArray();
            int[] damaged = Arrays.stream(lineParts[1].split(",")).mapToInt(Integer::parseInt).toArray();
            System.out.println(Arrays.toString(springs));
            System.out.println(Arrays.toString(damaged));
        }
        System.out.println("Part One Answer: " + totalArrangements);
    }
}
