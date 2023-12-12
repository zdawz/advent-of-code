import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Day5 {
    public static void main(String[] args) throws IOException {
        // Read input
        Path inputPath = Paths.get("2023/05/input.txt");
        String almanac = Files.readString(inputPath);

        // Convert the almanac into a list of seeds and a list of maps
        String[] almanacParts = almanac.split("\n\n");
        String[] seeds = almanacParts[0].replaceFirst("seeds: ", "").split(" ");
        double[][][] maps = new double[7][][];
        for (int i = 1; i < 8; i++) {
            maps[i - 1] = parseMap(almanacParts[i]);
        }

        // Part 1
        double minLocation = Double.MAX_VALUE;
        for (String seed : seeds) {
            double src = Double.parseDouble(seed);
            for (double[][] map : maps) {
                double dst = getDst(map, src);
                if (dst != -1) {
                    src = dst;
                }
            }
            minLocation = Math.min(minLocation, src);
        }
        System.out.println("Part One Answer: " + String.format("%.0f", minLocation));

        // Part 2
        minLocation = Double.MAX_VALUE;
        double[][] seedsWithRanges = getSeedsWithRanges(seeds);
        double totalSeeds = 0;
        for (double[] seedAndRange : seedsWithRanges) {
            totalSeeds += seedAndRange[0] + seedAndRange[1] - 1;
        }
        for (double[] seedAndRange : seedsWithRanges) {
            double startSeed = seedAndRange[0];
            double range = seedAndRange[1];
            double endSeed = startSeed + range;
            for (double seed = startSeed; seed < endSeed; seed++) {
                totalSeeds--;
                System.out.print(String.format("%.0f", totalSeeds) + " seeds left \r");
                double src = seed;
                for (double[][] map : maps) {
                    double dst = getDst(map, src);
                    if (dst != -1) {
                        src = dst;
                    }
                }
                minLocation = Math.min(minLocation, src);
            }
        }
        System.out.println("Part Two Answer: " + String.format("%.0f", minLocation));
    }

    private static double[][] parseMap(String mapString) {
        // Turn mapString into a 2D array
        String[] mapLines = mapString.replaceFirst(".*\n", "").split("\n");
        double[][] map = new double[mapLines.length][3];
        for (int i = 0; i < mapLines.length; i++) {
            String[] line = mapLines[i].split(" ");
            for (int j = 0; j < 3; j++) {
                map[i][j] = Double.parseDouble(line[j]);
            }
        }
        return map;
    }

    private static double getDst(double[][] map, double src) {
        // Get destination given the source and map
        for (int i = 0; i < map.length; i++) {
            double dstStart = map[i][0];
            double srcStart = map[i][1];
            double range = map[i][2];
            double distanceFromStart = src - srcStart;
            if (distanceFromStart >= 0 && distanceFromStart < range) {
                return dstStart + distanceFromStart;
            }
        }
        return -1;
    }

    private static double[][] getSeedsWithRanges(String[] seeds) {
        // Turn 1D Seeds array into 2D array with seeds and ranges
        int rows = seeds.length / 2;
        int columns = 2;
        double[][] seedsWithRanges = new double[rows][columns];

        int index = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                seedsWithRanges[i][j] = Double.parseDouble(seeds[index++]);
            }
        }

        return seedsWithRanges;
    }
}
