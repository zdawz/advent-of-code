import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Day10 {
    public static void main(String[] args) throws IOException {
        // Read input
        Path inputPath = Paths.get("day10/test.txt");
        List<String> lines = Files.readAllLines(inputPath);
        char[][] grid = new char[lines.size()][];
        int startRow = 0, startCol = 0;
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            int s = line.indexOf("S");
            if (s != -1) {
                startRow = i;
                startCol = s;
            }
            grid[i] = line.toCharArray();
        }

        final char NORTH = 'N';
        final char SOUTH = 'S';
        final char EAST = 'E';
        final char WEST = 'W';

        HashMap<Character, List<Character>> pipes = new HashMap<>();
        pipes.put('|', Arrays.asList(NORTH, SOUTH));
        pipes.put('-', Arrays.asList(EAST, WEST));
        pipes.put('L', Arrays.asList(NORTH, EAST));
        pipes.put('J', Arrays.asList(NORTH, WEST));
        pipes.put('7', Arrays.asList(SOUTH, WEST));
        pipes.put('F', Arrays.asList(SOUTH, EAST));
        pipes.put('.', Arrays.asList());
        pipes.put('S', Arrays.asList(NORTH, SOUTH, EAST, WEST));

        // Find the loop and count the total steps in it
        int steps = 0;
        int row = startRow;
        int col = startCol;
        char prevDir = NORTH;
        List<List<Integer>> loopCoords = new ArrayList<>();
        while (!(grid[row][col] == 'S' && steps > 0)) {
            List<Integer> coords = new ArrayList<>();
            coords.add(row);
            coords.add(col);
            loopCoords.add(coords);
            List<Character> dirs = pipes.get(grid[row][col]);
            for (char dir : dirs) {
                if (dir == NORTH && prevDir != NORTH) {
                    int newRow = row == 0 ? row : row - 1;
                    if (pipes.get(grid[newRow][col]).contains(SOUTH)) {
                        row = newRow;
                        prevDir = SOUTH;
                        break;
                    }
                } else if (dir == SOUTH && prevDir != SOUTH) {
                    int newRow = row == grid.length - 1 ? row : row + 1;
                    if (pipes.get(grid[newRow][col]).contains(NORTH)) {
                        row = newRow;
                        prevDir = NORTH;
                        break;
                    }
                } else if (dir == EAST && prevDir != EAST) {
                    int newCol = col == grid[0].length - 1 ? col : col + 1;
                    if (pipes.get(grid[row][newCol]).contains(WEST)) {
                        col = newCol;
                        prevDir = WEST;
                        break;
                    }
                } else if (dir == WEST && prevDir != WEST) {
                    int newCol = col == 0 ? col : col - 1;
                    if (pipes.get(grid[row][newCol]).contains(EAST)) {
                        col = newCol;
                        prevDir = EAST;
                        break;
                    }
                }
            }
            steps++;
        }

        System.out.println(loopCoords);

        // Iterate through entire grid to count tiles enclosed in the loop
        int tilesInLoop = 0;
        List<Integer> coords = new ArrayList<>();
        for (int i = 0; i < grid.length; i++) {
            boolean countTiles = false;
            for (int j = 0; j < grid[i].length; j++) {
                coords.add(i);
                coords.add(j);
                boolean onLoopTile = false;
                for (List<Integer> c : loopCoords) {
                    if (coords.equals(c)) {
                        onLoopTile = true;
                    }
                }
                if (onLoopTile) {
                    countTiles = !countTiles;
                } else if (countTiles) {
                    tilesInLoop++;
                }
                coords.clear();
            }
        }

        System.out.println("Part One Answer: " + Math.ceilDiv(steps, 2));
        System.out.println("Part Two Answer: " + tilesInLoop);
    }
}
