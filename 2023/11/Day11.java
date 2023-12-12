import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Day11 {
    public static void main(String[] args) throws IOException {
        // Read input
        Path inputPath = Paths.get("2023/11/input.txt");
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
            List<List<Integer>> newGalaxies = new ArrayList<>();
            String line = lines.get(i);
            for (Integer j = 0; j < line.length(); j++) {
                if (line.charAt(j) == '#') {
                    List<Integer> coords = new ArrayList<>();
                    coords.add(i);
                    coords.add(j);
                    newGalaxies.add(coords);
                    emptyCols.remove(j);
                }
            }
            if (newGalaxies.isEmpty()) {
                emptyRows.add(i);
            }
            galaxies.addAll(newGalaxies);
        }

        int partOneLength = 0;
        long partTwoLength = 0;
        for (int i = 0; i < galaxies.size() - 1; i++) {
            List<Integer> galaxyA = galaxies.get(i);
            int rowA = galaxyA.get(0);
            int colA = galaxyA.get(1);
            for (int j = i + 1; j < galaxies.size(); j++) {
                List<Integer> galaxyB = galaxies.get(j);
                int rowB = galaxyB.get(0);
                int colB = galaxyB.get(1);
                // Count rows between
                int startRow = rowA < rowB ? rowA : rowB;
                int endRow = rowA >= rowB ? rowA : rowB;
                for (int k = startRow; k < endRow; k++) {
                    if (emptyRows.contains(k)) {
                        partOneLength += 2;
                        partTwoLength += 1000000;
                    } else {
                        partOneLength++;
                        partTwoLength++;
                    }
                }
                // Count columns between
                int startCol = colA < colB ? colA : colB;
                int endCol = colA >= colB ? colA : colB;
                for (int k = startCol; k < endCol; k++) {
                    if (emptyCols.contains(k)) {
                        partOneLength += 2;
                        partTwoLength += 1000000;
                    } else {
                        partOneLength++;
                        partTwoLength++;
                    }
                }
            }
        }
        System.out.println("Part One Answer: " + partOneLength);
        System.out.println("Part Two Answer: " + partTwoLength);
    }
}
