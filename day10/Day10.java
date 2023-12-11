package day10;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Day10 {
    private final char NORTH = 'N';
    private final char SOUTH = 'S';
    private final char EAST = 'E';
    private final char WEST = 'W';

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

        HashMap<Character, List<Character>> pipes = new HashMap<>();
        pipes.put('|', Arrays.asList('N', 'S'));
        pipes.put('-', Arrays.asList('E', 'W'));
        pipes.put('L', Arrays.asList('N', 'E'));
        pipes.put('J', Arrays.asList('N', 'W'));
        pipes.put('7', Arrays.asList('W', 'W'));
        pipes.put('F', Arrays.asList('S', 'E'));
        pipes.put('.', Arrays.asList());
        pipes.put('S', Arrays.asList('N', 'S', 'E', 'W'));

        // Count the total steps in the loop
        int steps = 1;
        int[] nextTile = getNextTile(grid, pipes, startRow, startCol, null);
        int row = nextTile[0];
        int col = nextTile[1];
        while (grid[row][col] != 'S') {
            nextTile = getNextTile(grid, pipes, row, col, );
            row = nextTile[0];
            col = nextTile[1];
            steps++;
        }

        System.out.println(steps);

        // Furthest you can go is halfway
        steps = Math.ceilDiv(steps, 2);

        System.out.println(steps);
    }

    private static boolean checkNorth(char[][] grid, HashMap<Character, List<Character>> pipes, int row, int col) {
        if (row != 0 && pipes.get(grid[row - 1][col]).contains('S')) {
            return true;
        }
        return false;
    }

    private static boolean checkSouth(char[][] grid, HashMap<Character, List<Character>> pipes, int row, int col) {
        if (row != grid.length - 1 && pipes.get(grid[row - 1][col]).contains('N')) {
            return true;
        }
        return false;
    }

    private static boolean checkEast(char[][] grid, HashMap<Character, List<Character>> pipes, int row, int col) {
        if (col != grid[0].length - 1 && pipes.get(grid[row][col + 1]).contains('W')) {
            return true;
        }
        return false;
    }

    private static boolean checkWest(char[][] grid, HashMap<Character, List<Character>> pipes, int row, int col) {
        if (col != 0 && pipes.get(grid[row][col - 1]).contains('E')) {
            return true;
        }
        return false;
    }

    private static int[] getNextTile(char[][] grid, HashMap<Character, List<Character>> pipes, int row, int col,
            char tile) {
        int nextRow = row;
        int nextCol = col;

        // Define rows and columns
        int northRow = row - 1;
        int southRow = row + 1;
        int eastCol = col + 1;
        int westCol = col - 1;

        List<Character> validDirectons = pipes.get(grid[row][col]);

    }
}
