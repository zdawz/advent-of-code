package day5;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Day5 {
    public static void main(String[] args) throws IOException {
        // Read input
        Path inputPath = Paths.get("day5/input.txt");
        String almanac = Files.readString(inputPath);

        // Convert the almanac into a list of seeds and a list of maps
        String[] almanacParts = almanac.split("\n\n");
        String[] seeds = almanacParts[0].replaceFirst("seeds: ", "").split(" ");
        String[][] maps = new String[7][];
        for (int i = 1; i < 8; i++) {
            maps[i - 1] = parseMap(almanacParts[i]);
        }

        // Part 1
        double minLocation = Double.MAX_VALUE;
        for (String seed : seeds) {
            double src = Double.parseDouble(seed);
            for (String[] map : maps) {
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
        String[][] seedsWithRanges = getSeedsWithRanges(seeds);
        for (String[] seedAndRange : seedsWithRanges) {
            double startSeed = Double.parseDouble(seedAndRange[0]);
            double range = Double.parseDouble(seedAndRange[1]);
            for (double seed = startSeed; seed < startSeed + range; seed++) {
                double src = seed;
                for (String[] map : maps) {
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

    private static String[] parseMap(String mapString) {
        // Remove first line of mapString and split into an array
        return mapString.replaceFirst(".*\n", "").split("\n");
    }

    private static double getDst(String[] map, double src) {
        // Get destination given the source and map
        for (int i = 0; i < map.length; i++) {
            String[] splitMapLine = map[i].split(" ");
            double dstStart = Double.parseDouble(splitMapLine[0]);
            double srcStart = Double.parseDouble(splitMapLine[1]);
            double range = Double.parseDouble(splitMapLine[2]);
            double distanceFromStart = src - srcStart;
            if (distanceFromStart >= 0 && distanceFromStart < range) {
                return dstStart + distanceFromStart;
            }
        }
        return -1;
    }

    private static String[][] getSeedsWithRanges(String[] seeds) {
        // Turn 1D Seeds array into 2D array with seeds and ranges
        int rows = seeds.length / 2;
        int columns = 2;
        String[][] seedsWithRanges = new String[rows][columns];

        int index = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                seedsWithRanges[i][j] = seeds[index++];
            }
        }

        return seedsWithRanges;
    }
}
