 
import java.util.*;


public class Main {

    public static void main(String[] args) {
        int K;
        double UCScost;
        double Acost;
        Scanner input = new Scanner(System.in);
        System.out.println("\nHello! The program requires user's good intentions, so don't give extreme or wrong inputs where asked.");
        System.out.println("Let's start!\n");
        System.out.print("Enter integer K: ");
        K = input.nextInt();
        List<State> finalPath;


        int L = 4 * K;
        int N = 3 * K;


        Table table = new Table(L, 3, N);

        System.out.println("(1): for random table initialization \t (2): for manual table initialization ");
        System.out.print("Choice: ");
        int choice = input.nextInt();

        if (choice == 2) {
            table.setInitialStateManually();
        } else if (choice == 1) {
            table.initializeRandomCubeTable(N);
        }

        System.out.println("Table Array: ");
        table.printCubeArray(table.getTableArr());


        System.out.println("(1): UCS \t (2): A* \t (3): Both");
        System.out.print("Choice: ");
        int choice2 = input.nextInt();
        State state = new State(table.getCubes(), table.getTableArr(), 0.0, null);
        State stateV2 = new State(table.getCubes(), table.getTableArr(), 0.0, null);

        switch ((choice2)) {
            case 1:
                System.out.println("\nUCS finding shortest path...");
                finalPath = state.ucs(table, state);
                UCScost = printFinalPath(finalPath);
                System.out.println("TOTAL STATE EXTENSIONS=: " + state.getCountExtensions());
                System.out.println("TOTAL COST=: " + UCScost);
                break;

            case 2:
                System.out.println("\nA* finding shortest path...");
                finalPath = state.aStar(table, state);
                Acost = printFinalPath(finalPath);
                System.out.println("TOTAL STATE EXTENSIONS=: " + state.getCountExtensions());
                System.out.println("TOTAL COST=: " + Acost);
                break;

            case 3:
                Thread thread1 = new Thread(() -> {
                    // run UCS algorithm
                    System.out.println("\nUCS finding shortest path...");
                    List<State> finalPathUCS = stateV2.ucs(table, stateV2);
                    double costUCS = printFinalPath(finalPathUCS);
                    System.out.println("TOTAL STATE EXTENSIONS=: " + stateV2.getCountExtensions());
                    System.out.println("TOTAL COST=: " + costUCS);
                });

                Thread thread2 = new Thread(() -> {
                // run A* algorithm
                    System.out.println("\nA* finding shortest path...");
                    List<State> finalPathA = state.aStar(table, state);
                    double costA = printFinalPath(finalPathA);
                    System.out.println("TOTAL STATE EXTENSIONS=: " + state.getCountExtensions());
                    System.out.println("TOTAL COST=: " + costA);
                });

                thread1.start(); // start UCS thread

                try {thread1.join();} catch (InterruptedException e) {e.printStackTrace();}

                thread2.start();// start A* thread
                break;
        }
    }


    private static double printFinalPath(List<State> path) {
        System.out.println("\nFINAL PATH");

        double finalCost = 0;
        for (State state : path) {
            System.out.println("State cost =: " + state.getCost());
            System.out.println("------------------------");
            state.printCubeArray(state.getCubeArr());
            finalCost = state.getCost();
        }
        return finalCost;
    }



}