package org.example;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {
        int K;
        Scanner input = new Scanner(System.in);
        System.out.println("Enter an integer: ");
        K = input.nextInt();


        // Initializing the table here
        int L = 4 * K;
        int N = 3 * K;
        Table table = new Table();
        table.setNumRows(3);
        table.setNumColumns(L);

        // adding the ids of the cubes here
        List<Integer> ids = IntStream.rangeClosed(1, N)
                .boxed().collect(Collectors.toList());
        Collections.shuffle(ids);
        int counter = 0;
        for (int r = 1; r <= 3; r++) {
            for (int c = 1; c <= K; c++) {
                if (counter == N) break;
                Integer id = ids.get(counter);
                table.cubes.add(new Cube(id, c, r));
                counter++;
                }
            if (counter == N) break;
            }

        printCubes(table, K);
        System.out.println(table.isValidCondition());
    }

    public static void printCubes(Table table, int K) {
        for (int r = 3; r >= 1; r--) {
            // add a line break after each row
            if (r != 1) {
                for (int col = 1; col <= K; col++) {
                    isCubeFound(table, r, col);
                }
            } else {
                for (int col = 1; col <= table.getNumColumns(); col++) {
                    isCubeFound(table, r, col);
                }
            }
            System.out.print("\n"); // add a line break after each row
        }
    }

    private static void isCubeFound(Table table, int r, int col) {
        boolean cubeFound = false;
        for (Cube c : table.cubes) {
            if (c.getxCoordinate() == col && c.getyCoordinate() == r) {
                System.out.print("[" + c.getId() + "] ");
                cubeFound = true;
                break;
            }
        }
        if (!cubeFound) {
            System.out.print("   ");
        }
    }

}