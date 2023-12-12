import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Day11 {
    public static void main(String[] args) throws IOException {
        // Read input
        Path inputPath = Paths.get("day11/test.txt");
        List<String> lines = Files.readAllLines(inputPath);

        // Initialize variables
        List<List<Integer>> galaxies = new ArrayList<>();
        List<Integer> emptyRows = new ArrayList<>();
        List<Integer> emptyCols = new ArrayList<>();
        for (int i = 0; i < lines.get(0).length(); i++) {
            emptyCols.add(i);
        }

        // Loop through "image"
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            List<Integer> galaxyIndices = new ArrayList<>();
            for (Integer j = 0; j < line.length(); j++) {
                if (line.charAt(j) == '#') {
                    galaxyIndices.add(j);
                    emptyCols.remove(j);
                }
            }
            if (galaxyIndices.isEmpty()) {
                emptyRows.add(i);
            }
            galaxies.add(galaxyIndices);
        }

        System.out.println(galaxies);
        System.out.println(emptyRows);
        System.out.println(emptyCols);
    }
}
