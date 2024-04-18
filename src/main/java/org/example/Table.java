 

import java.util.*;

public class Table {
    private int numRows, numColumns; // numRows = L , numColumns = y
    private int N;
    private Set<ArrayList<Integer>> takenCoordinates; // set of taken coordinates upon initialization of cube table
    private Cube[][] tableArr, finalTableArr; // tableArr->initial table, finalTableArr-> destination
    private ArrayList<Cube> cubes; // list of cubes with the initial coordinates
    private ArrayList<Cube> finalStateCubeList; // the end result
    private Set<Integer> ids; // list of available ids for cubes
    private Random random;

    // TABLE CONSTRUCTOR
    public Table(int numRows,int numColumns,int N){
        this.N = N;
        this.numRows = numRows;
        this.random = new Random();
        this.numColumns = numColumns;
        this.tableArr = new Cube[numRows][numColumns];
        this.finalTableArr = new Cube[numRows][numColumns];
        this.cubes = new ArrayList<>(N);
        this.takenCoordinates = new HashSet<>(N);
        this.ids = new HashSet<>(N);
        setUpFinalState(N,numColumns);
        for(int number=1;number<=N;number++){
            ids.add(number);
            cubes.add(new Cube(number,0,0)); /* creating List of cubes with (0,0) coordinates.
                                                                   let user initialize for each cube what the coordinates will be */
        }
    }

    // GETTERS & SETTERS
    public List<Cube> getCubes(){
        return cubes;
    }
    public List<Cube> getFinalCubeList(){return finalStateCubeList;}
    public Cube[][] getTableArr() {
        return tableArr;
    }
    public Cube[][] getFinalTableArr(){return finalTableArr;}
    public void setTableArr(Cube[][] newCubeArr){
        this.tableArr = newCubeArr;
    }
    public ArrayList<Cube> getFinalStateCubeList(){
        return finalStateCubeList;
    }
    public int getNumRows() {
        return numRows;
    }

    public void setUpFinalState(int N, int Y){
        int K = N/3;
        int cubeNumber=1;
        int L = 4*K;
        this.finalStateCubeList = new ArrayList<>(N);
        for(int cols = 1; cols <= Y; cols++){
            for(int rows = 1; rows <=K; rows++){
                finalStateCubeList.add(new Cube(cubeNumber, rows, cols));
                finalTableArr[rows -1][cols -1] = new Cube(cubeNumber, rows, cols);
                tableArr[rows-1][cols-1] = new Cube(0,0,0);
                cubeNumber++;
            }
        }
        // adding 0 cubes in empty indexes of the tableArr
        for(int cols = 1; cols <=Y; cols++){
            for(int rows = K+1; rows <=L; rows++){
                finalTableArr[rows-1][cols-1] = new Cube(0,0,0);
                tableArr[rows-1][cols-1] = new Cube(0,0,0);
            }
        }
        System.out.println("What the final table should look like: ");
        printCubeArray(finalTableArr);
        System.out.println("Table currently: ");
        printCubeArray(tableArr);
    }


    // HELPER METHODS
    public boolean isThereACubeBelow(int x, int y) {
        if ((y == 3 || y == 2) && x<=getNumRows()/4) {
            return tableArr[x-1][y-2].getId() != 0;
        }
        return false;
    }

    public boolean isCubeOnTable(Cube cube) {
        return cube.getYCoordinate() == 1;
    }

    private boolean isCubeInValidCoordinates(Cube c){
        /*if(!isCubeOnTable(c)){
            return isThereACubeBelow(c.getXCoordinate(), c.getYCoordinate());
        } */
        // if cube is on 1st level, its x must be between 1 and L, where L = number of table available positions
        if ((c.getYCoordinate() == 1 && c.getXCoordinate() < 1) || (c.getYCoordinate() == 1 && c.getXCoordinate() > getNumRows())) {
            return false;
        }
        // if cube is on 2nd or 3rd level, its x must be between 1 and K, and the table has L positions available for x, L is 4 * K, Number of cubes is 3 * K,
        // eventually cube must be between 1 and K. getNumRows = L.
        else if ((c.getYCoordinate() == 2 || c.getYCoordinate() == 3) && (c.getXCoordinate() < 1 || c.getXCoordinate() > getNumRows()/4)){
            return false;
        }
        return true;
    }

    private void clearStates(List<Cube> list,Cube[][] table){
        for(Cube c: list){
            c.setCoordinates(0,0);
        }
        for(int cols = 1; cols <= table[0].length; cols++){
            for(int rows = 1; rows <=table.length; rows++){
                table[rows-1][cols-1] = new Cube(0,0,0);
            }
        }
    }

    private boolean areCoordinatesFree(int x,int y){
        ArrayList<Integer> coordinates = new ArrayList<>(2);
        coordinates.add(x);
        coordinates.add(y);
        return !takenCoordinates.contains(coordinates);
    }

    private boolean isCubeBelow(int x,int y){
        ArrayList<Integer> coordinates = new ArrayList<>(2);
        coordinates.add(x);
        coordinates.add(y-1);
        return takenCoordinates.contains(coordinates);
    }

    private boolean isValidCondition() {
        for (Cube c : cubes) {
            if(!isCubeOnTable(c)){
                if(!isCubeBelow(c.getXCoordinate(),c.getYCoordinate())) return false;
            }
            // if cube is on 1st level, its x must be between 1 and L, where L = number of table available positions
            if ((c.getYCoordinate() == 1 && c.getXCoordinate() < 1) || (c.getYCoordinate() == 1 && c.getXCoordinate() > getNumRows())) {
                return false;
            }
            // if cube is on 2nd or 3rd level, its x must be between 1 and K, and the table has L positions available for x, L is 4 * K, Number of cubes is 3 * K,
            // eventually cube must be between 1 and K. getNumRows = L.
            else if ((c.getYCoordinate() == 2 || c.getYCoordinate() == 3) && (c.getXCoordinate() < 1 || c.getXCoordinate() > getNumRows()/4)){
                return false;
            }
        }
        return true;
    }

    // INITIALIZATION
    public void setInitialStateManually() {
        int countInsertedCubes = 0;
        String[] coordinates;
        System.out.println("-------------------------");
        System.out.print("Initialize Table\n");
        System.out.println("-------------------------");
        System.out.println("You have to enter two numbers separated by comma.");
        Scanner sc = new Scanner(System.in);
        while (countInsertedCubes < N) {
            for (Cube c : cubes) {
                while (true) {
                    System.out.printf("Give coordinates x,y for Cube[%d]: ", c.getId());
                    String input = sc.nextLine();
                    coordinates = input.split(",");
                    int x = Integer.parseInt(coordinates[0].trim());
                    int y = Integer.parseInt(coordinates[1].trim());
                    ArrayList<Integer> newCoordinates = new ArrayList<>(2);
                    newCoordinates.add(x);
                    newCoordinates.add(y);
                    System.out.println("New coordinates: " + newCoordinates);
                    if (!takenCoordinates.contains(newCoordinates)) {
                        c.setCoordinates(x, y);
                        if (isCubeInValidCoordinates(c)) {
                            cubes.set(c.getId() - 1, c);
                            tableArr[x - 1][y - 1] = c;
                            takenCoordinates.add(newCoordinates);
                            printCubeArray(tableArr);
                            break;
                        }else{
                            c.setCoordinates(0,0);
                            System.out.println("You gave wrong coordinates.\n Current table: ");
                            printCubeArray(tableArr);
                        }
                    } else {
                        System.out.println("Coordinates already exist. Current table: ");
                        printCubeArray(tableArr);
                    }
                }
                countInsertedCubes++;
            }
            if(!isValidCondition()){
                countInsertedCubes = 0;
                System.out.println("Your table is invalid. Initialize all over again!");
                takenCoordinates.clear();
                clearStates(cubes,tableArr);
            }
        }
    }
    public void initializeRandomCubeTable(int N){
        int K = N/3;
        int L = 4*K;
        int state; // 0 | 1 . When 1 we insert cube, else nothing. In that way we can set the cube table randomly
        int countCubeInsertions =1;
        // cube table
        while(countCubeInsertions<=N){
            for(int y=1;y<=3;y++){
                if(y==1){ // place cubes starting from the table
                    for(int x=1;x<=L;x++){
                        state = random.nextInt(2);
                        if(state==1 && !ids.isEmpty()){
                            int randomIndex = new Random().nextInt(ids.size());
                            int randomNumber = (int)ids.toArray()[randomIndex];
                            if(areCoordinatesFree(x,y)){
                                countCubeInsertions = cubeInsertion(countCubeInsertions,cubes,tableArr, x, y, randomNumber);
                            }
                        }
                    }
                }else{
                    for(int x=1;x<=K;x++){
                        state = random.nextInt(2);
                        if(state==1 && !ids.isEmpty()){
                            if(isCubeBelow(x,y) && areCoordinatesFree(x,y)){
                                int randomIndex = new Random().nextInt(ids.size());
                                int randomNumber = (int) ids.toArray()[randomIndex];
                                countCubeInsertions = cubeInsertion(countCubeInsertions,cubes,tableArr, x, y, randomNumber);
                            }
                        }
                    }
                }
            }
        }
    }

    private int cubeInsertion(int countCubeInsertions, List<Cube> initialCubeList, Cube[][] initialCubeArray,int x,int y, int randomNumber){
        ArrayList<Integer> nCoord = new ArrayList<>(2);
        nCoord.add(x); nCoord.add(y);
        initialCubeList.get(randomNumber-1).setCoordinates(x,y);
        initialCubeArray[x-1][y-1].setId(randomNumber);
        initialCubeArray[x-1][y-1].setCoordinates(x,y);
        countCubeInsertions++;
        takenCoordinates.add(nCoord);
        ids.remove(randomNumber);
        return countCubeInsertions;
    }

    // PRINTS
    public void printCubeList(List<Cube> cubeList){
        System.out.println("<Cube> : <x,y>");
        System.out.println("------------------------");
        for(Cube c: cubeList){
            System.out.printf("Cube[%d] : (%d,%d)\n" ,c.getId(),c.getXCoordinate(),c.getYCoordinate());
        }
        System.out.println("------------------------");
    }
    public void printCubeArray(Cube[][] table){
        System.out.println("------------------------");
        for(int j=table[0].length-1; j>-1 ; j--){
            for(int i=0; i<table.length;i++){
                System.out.print(table[i][j].getId() + " ");
            }
            System.out.println();
        }
        System.out.println("------------------------");
    }
}
