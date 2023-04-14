package org.example;
import java.util.Scanner;

public class Main {
    public static void main(String[] args)
    {
        int K = 0;
        Scanner input = new Scanner(System.in);
        System.out.println("Enter an integer: ");
        K = input.nextInt();

        // Initializing the table here
        int L = 4*K;
        Table table = new Table();
        table.setNumRows(3);
        table.setNumColumns(L);
    }
}