import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Day8 {
    public static void main(String[] args) throws IOException {
        // Read input
        Path inputPath = Paths.get("day8/input.txt");
        List<String> lines = Files.readAllLines(inputPath);
        char[] instructions = lines.remove(0).toCharArray();
        lines.remove(0); // Remove empty line

        // Create network map
        HashMap<String, String[]> networkMap = new HashMap<>();
        for (String line : lines) {
            String[] nodeArray = line.split(" = ");
            String node = nodeArray[0];
            String[] nextNodes = nodeArray[1].replaceAll("\\(|\\)", "").split(", ");
            networkMap.put(node, nextNodes);
        }

        // Count steps to get from AAA to ZZZ
        int partOneAnswer = countSteps(networkMap, instructions, "AAA", "ZZZ");
        System.out.println("Part One Answer: " + partOneAnswer);

        // Count steps to get from **A to **Z
        List<Long> steps = new ArrayList<>();
        networkMap.forEach((node, nextNodes) -> {
            if (node.endsWith("A")) {
                steps.add((long) countSteps(networkMap, instructions, node, "Z"));
            }
        });

        // Find the LCM (Least Common Multiple) of the step counts from the nodes. The
        // LCM will be how long it would take to get to **Z for all nodes starting with
        // **A simultaneously
        long partTwoAnswer = steps.get(0);
        for (int i = 1; i < steps.size(); i++) {
            partTwoAnswer = lcm(partTwoAnswer, steps.get(i));
        }
        System.out.println("Part Two Answer: " + partTwoAnswer);
    }

    private static String getNextNode(HashMap<String, String[]> networkMap, char direction, String node) {
        String[] nextNodes = networkMap.get(node);
        if (direction == 'L') {
            return nextNodes[0];
        }
        return nextNodes[1];
    }

    private static int countSteps(HashMap<String, String[]> networkMap, char[] instructions, String startNode,
            String endNode) {
        int step = 0, totalSteps = 0;
        String nextNode = startNode;
        while (!nextNode.endsWith(endNode)) {
            char direction = instructions[step];
            // Find next node
            nextNode = getNextNode(networkMap, direction, nextNode);
            // Increment steps
            if (step == instructions.length - 1) {
                step = 0; // Reset at beginning of instructions
            } else {
                step++;
            }
            totalSteps++;
        }
        return totalSteps;
    }

    private static long gcd(long a, long b) {
        while (b > 0) {
            long temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    private static long lcm(long a, long b) {
        return (a * b) / gcd(a, b);
    }
}
