import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day12 {
    public static void main(String[] args) throws IOException {
        // Read input
        Path inputPath = Paths.get("2023/12/test.txt");
        List<String> lines = Files.readAllLines(inputPath);

        int totalArrangements = 0;
        for (String line : lines) {
            String[] lineParts = line.split(" ");
            String springs = lineParts[0];
            List<Integer> damaged = Arrays.stream(lineParts[1].split(",")).map(Integer::valueOf)
                    .collect(Collectors.toList());
            totalArrangements += countArrangements(springs, damaged);
        }
        System.out.println("Part One Answer: " + totalArrangements);
    }

    private static int countArrangements(String springs, List<Integer> damaged) {
        List<String> unknownSpringGroups = new ArrayList<>();
        Pattern unknownSpringPattern = Pattern.compile("([\\?#]+)");
        Matcher unknownSpringMatcher = unknownSpringPattern.matcher(springs);
        while (unknownSpringMatcher.find()) {
            unknownSpringGroups.add(unknownSpringMatcher.group());
        }
        System.out.println(unknownSpringGroups);
        return 0;
    }
}
