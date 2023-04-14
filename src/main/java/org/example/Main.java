package org.example;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        int K;
        Scanner input = new Scanner(System.in);
        Random rand = new Random();
        Set<Integer> set = new LinkedHashSet<>();
        System.out.println("Enter an integer: ");
        K = input.nextInt();


        // Initializing the table here
        int L = 4 * K;
        int N = 3 * K;
        Table table = new Table();
        table.setNumRows(3);
        table.setNumColumns(L);
        while (set.size() <= N) {
            set.add(rand.nextInt(1, N));
        }
        for (int r = 1; r <= 3; r++) {
            for (int c = 1; c <= K; c++) {
                for (Integer i : set) {
                    table.cubes.add(new Cube(i, c, r));
                    break;
                }
            }
        }

        // printCubes(table);
    }

    public static void printCubes(Table table) {
        for (int r = 3; r >= 1; r--) {
            for (int col = 1; col <= table.getNumColumns(); col++) {
                for (Cube c : table.cubes) {
                    if (c.getxCoordinate() == col && c.getyCoordinate() == r) {
                        System.out.print("[" + c.getId() + "]");
                    }
                }
            }
            System.out.print("\n");
        }
    }
}