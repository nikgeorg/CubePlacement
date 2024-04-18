 

import java.util.*;

public class State {
    private List<Cube> cubes;
    private List<Cube> destination;
    private Cube[][] cubeArr;
    private double cost;
    private State previousState;
    private int countExtensions=0;
    private double estimateCost = 0.0;



    public State(List<Cube> cubes,Cube[][] cubeArr, double cost, State previousState) {
        this.cubeArr = cubeArr;
        this.cubes = cubes;
        this.cost = cost;
        this.previousState = previousState;
    }

    // GETTERS & SETTERS
    public List<Cube> getCubes() {
        return cubes;
    }
    public Cube[][] getCubeArr(){
        return cubeArr;
    }
    public void setCubes(List<Cube> cubes) {
        this.cubes = cubes;
    }
    public int getCountExtensions(){return countExtensions;}
    public void setCountExtensions(int countExtensions){
        this.countExtensions = countExtensions;
    }
    public double getCost() {
        return cost;
    }
    public double getEstimateCost(){return estimateCost;}
    public void setEstimateCost(double newEstimate){ this.estimateCost = newEstimate;}
    public double setCost(double cost) {
        return this.cost = cost;
    }
    public State getPreviousState() {
        return previousState;
    }
    public void setPreviousState(State previousState) {
        this.previousState = previousState;
    }


    @Override
    public boolean equals(Object obj){
        if(obj ==null) return false;
        if(!(obj instanceof State)) return false;
        State other = (State) obj ;
        return checkStateEquality((State) obj,other);
    }

    // SUCCESSOR STATES GENERATOR
    private List<State> generateSuccessorStates(State state) {
        int K = cubeArr.length / 4;
        int searchXrow = 4 * K;

        List<State> nextStates = new ArrayList<>();
        int rows = cubeArr.length;
        int cols = cubeArr[0].length;

        for(int j=0; j < cols; j++){ if(j==1||j==2){searchXrow =K;}
            for(int i = 0; i<searchXrow; i++){

                if(state.cubeArr[i][j].getId() != 0 && isCubeFree(state,i,j)){
                    int flag=0;
                    Cube cubeFound = state.cubeArr[i][j];
                    Cube[][] tempTable = copyArray(state.cubeArr);
                    List<Cube> tempList = copyList(state.cubes);


                    levels:for(int l=0; l< cols; l++){
                        int searchXrow2;

                        if(l==1 || l==2){ searchXrow2=K;} else searchXrow2 = 4*K;
                        rows: for(int k=0; k< searchXrow2; k++){

                            if(state.cubeArr[k][l].getId() == 0){

                                if(cubeFound.getId() <=K){
                                    /* Σε κάθε περίπτωση που βρεθεί κενή θέση για κύβο που είναι μέσα στις Κ θέσεις και δεν είναι σε τελική κατάσταση μετακίνησε τον κύβο.
                                     * Ή αν βρεθεί κενή θέση η οποία είναι τελική κατάσταση για τον κύβο μετακίνησε τον*/
                                    if(isCubePosFinal(cubeFound,k,l) || k >= K && i<K && !isCubePosFinal(cubeFound,i,j)){
                                        double newCost =  update(cubeFound,tempList,tempTable,k,l,i,j);
                                        nextStates.add(new State(tempList,tempTable,newCost,state));
                                        break levels;
                                    }
                                }
                                else if(cubeFound.getId() >K && cubeFound.getId()<=2*K){
                                    if(l==0 && k>=K){
                                        // Σε κάθε περίπτωση που βρεθεί κενή θέση για κύβο που είναι μέσα στις Κ θέσεις και δεν είναι σε τελική κατάσταση μετακίνησε τον κύβο.
                                        if(i<K && !isCubeInFinalState(cubeFound,state)){
                                            double newCost = update(cubeFound,tempList,tempTable,k,l,i,j);
                                            flag =1;
                                            nextStates.add(new State(tempList,tempTable,newCost,state));
                                            break rows;
                                        }
                                    }
                                    else if(l==1 && isThereACubeBelow(state,k,l)){
                                        Cube cubeBelow = cubeBelow(state,k,l);
                                        /* Μετακίνησε τον κύβο στο 1ο επίπεδο σε περίπτωση που ο απο κάτω είναι σε τελική κατάσταση και η θέση τοποθέτησης
                                         * είναι τελική για τον κύβο*/
                                        if(isCubePosFinal(cubeBelow,k,l-1) && isCubePosFinal(cubeFound,k,l) ){
                                            if(flag ==1) {
                                                tempTable = copyArray(state.cubeArr);
                                                tempList = copyList(state.cubes);
                                            }
                                            double newCost =  update(cubeFound,tempList,tempTable,k,l,i,j);
                                            nextStates.add(new State(tempList,tempTable,newCost,state));
                                            flag = 0;
                                            break levels;
                                        }
                                    }
                                }else if(cubeFound.getId() > 2*K){

                                    /* Αν βρεθεί κενή θέση στο 1ο επίπεδο έξω από τις Κ θέσεις. */
                                    if((l==0) && k+1>K && i<K){
                                        if(!isCubeInFinalState(cubeFound,state)){
                                            double newCost = update(cubeFound,tempList,tempTable,k,l,i,j);
                                            nextStates.add(new State(tempList,tempTable,newCost,state));
                                            flag =1 ;
                                            break rows;
                                        }
                                    }
                                    // Αν βρεθεί κενή θέση στο 2ο επίπεδο με κύβο απο κάτω σε τελική θέση.
                                    else if(l==1 && isThereACubeBelow(state,k,l)){
                                        Cube cubeBelow = cubeBelow(state,k,l);
                                        if(isCubeInFinalState(cubeBelow,state)){
                                            double newCost = update(cubeFound,tempList,tempTable,k,l,i,j);
                                            nextStates.add(new State(tempList,tempTable,newCost,state));
                                            flag =1 ;
                                            break rows;
                                        }
                                    }else if(l==2 && isThereACubeBelow(state,k,l)){
                                        Cube cubeBelow = cubeBelow(state,k,l);
                                        Cube belowCubeBelow = cubeBelow(state,k,l-1);
                                        if(isCubePosFinal(belowCubeBelow,k,l-2) && isCubePosFinal(cubeBelow,k,l-1) && isCubePosFinal(cubeFound,k,l) ){
                                            if(flag==1) {
                                                tempTable = copyArray(state.cubeArr);
                                                tempList = copyList(state.cubes);
                                            }
                                            double newCost =  update(cubeFound,tempList,tempTable,k,l,i,j);
                                            nextStates.add(new State(tempList,tempTable,newCost,state));
                                            flag =0;
                                            break levels;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return nextStates;
    }

    // HELPER FUNCTIONS FOR SUCCESSOR STATES GENERATOR

    private double getMoveCost(Cube cube,int ny) {
        Movement move = new Movement();
        return move.moveCube(cube,ny);
    } // returns the moving cost

    /*updates the: {temporary table,cube list and cubeFound} in order to create a new State which will not affect the State globally*/
    private double update(Cube cubeFound,List<Cube> tempCubes,Cube[][] tempTable, int newX, int newY, int prevX, int prevY){
        // place cubeFound at the empty position of the array
        tempTable[newX][newY].setId(cubeFound.getId());
        tempTable[newX][newY].setCoordinates(newX+1,newY+1);
        double cost = getMoveCost(cubeFound,newY+1); // returns the movement's cost
        tempCubes.get(cubeFound.getId()-1).setCoordinates(newX+1,newY+1); // update cube list
        // update to 0 the previous position of cubeFound in the array
        tempTable[prevX][prevY].setCoordinates(0,0);
        tempTable[prevX][prevY].setId(0);
        return cost;
    }

    private boolean isCubeInFinalState(Cube cube,State state){
        int K = cubeArr.length/4;
        int x = cube.getXCoordinate()-1;
        int y = cube.getYCoordinate();
        if(cube.getId()>2*K && cube.getYCoordinate()==3){
            Cube cubeBelow = cubeBelow(state,x,y-1);
            Cube belowCubeBelow = cubeBelow(state,x,y-2);
            return isCubePosFinal(cubeBelow, x,1) && isCubePosFinal(belowCubeBelow, x, 0) && isCubePosFinal(cube,x,2);
        }
        else if(cube.getId()>K && cube.getId()<=2*K && cube.getYCoordinate()==2){
            Cube cubeBelow = cubeBelow(state,x,y-1);
            return isCubePosFinal(cubeBelow, x, y - 2) && isCubePosFinal(cube,x,y-1);
        }
        else if(cube.getId()<=K){
            return isCubePosFinal(cube, x, y);
        }
        return false;
    } // checks for a specific cube and state if cube is in final state


    private boolean isCubePosFinal(Cube cube,int x,int y){
        ArrayList<Integer> currCoordinates = new ArrayList<>(2);
        currCoordinates.add(x+1);
        currCoordinates.add(y+1);
        return destination.get(cube.getId()-1).getCoordinates().equals(currCoordinates);
    } // checks if coordinates (x,y) match with the cubes final state coordinates


    private boolean isThereACubeBelow(State state,int x, int y) {
        if ((y == 2 || y == 1) && x<=cubeArr.length/4) {
            return state.cubeArr[x][y-1].getId() != 0;
        }
        return false;
    } // checks if there is a cube below (x,y) coordinates for the specific state

    // For a specific state, return the cube below (x,y) coordinates if there is a cube below, else returns the cube of (x,y) coordinates
    private Cube cubeBelow(State state,int x,int y){
        if ((y == 2 || y == 1)) {
            return state.cubeArr[x][y-1];
        }
        return state.cubeArr[x][y];
    }

    // For specific state, check if a cube is Free
    private boolean isCubeFree(State state,int x,int y) {
        if (y == 2) { // A cube on 3rd level is free, only if it's not in final position and one of the cubes below is also not in final position
            return !isCubeInFinalState(state.cubeArr[x][y], state);
        }
        else
            return state.cubeArr[x][y + 1].getId() == 0; // if the cube above (x,y+1) has id=0 then cube in this current state is free
    }

    private  Cube[][] copyArray(Cube[][] original){
        Cube[][] copy = new Cube[original.length][original[0].length];
        for(int i=0;i< original.length;i++){
            for(int j=0; j < original[0].length; j++){
                copy[i][j] = new Cube(original[i][j]);
            }
        }
        return copy;
    }

    private List<Cube> copyList(List<Cube> original) {
        List<Cube> copiedCubes = new ArrayList<>();
        // Iterate over the original list of Cube objects and create a deep copy of each object
        for (Cube cube : original) {
            Cube copiedCube = new Cube(cube); // Use copy constructor to create a new Cube object with the same state
            copiedCubes.add(copiedCube); // Add the copied Cube object to the copiedCubes list
        }
        return copiedCubes;
    }



    // A* && UCS
    public List<State> aStar(Table table, State startState){

       PriorityQueue<State> Q = new PriorityQueue<>(Comparator.comparing(State::getEstimateCost));
       Map<State,State> cameFrom = new HashMap<>();
       Map<State,Double> visited = new HashMap<>();

       destination = table.getFinalStateCubeList();

       Q.add(startState);
       visited.put(startState, 0.0);

        while(!Q.isEmpty()){

            State currentState = Q.poll();

            if(isGoalState(currentState.getCubes())){
                setCountExtensions(visited.size());
                return getPathToFinalState(currentState,cameFrom);
            }

            List<State> successorStates = generateSuccessorStates(currentState);

            for(State neighbor: successorStates) {

                double newCost = currentState.getCost() + neighbor.getCost();

                if (!visited.containsKey(neighbor) || newCost < visited.get(neighbor)) {
                    visited.put(neighbor, newCost);
                    neighbor.setEstimateCost(newCost + heuristicCost(neighbor));
                    neighbor.setCost(newCost);
                    neighbor.setPreviousState(currentState);
                    cameFrom.put(neighbor, currentState);
                    Q.add(neighbor);
                }

            }
        }
    return new ArrayList<>();
    }

    public List<State> ucs(Table table,State startState) {

        Comparator<State> comparator = Comparator.comparing(State::getCost); // Define a comparator to order states based on their total cost
        PriorityQueue<State> queue = new PriorityQueue<>(comparator);  // Create a priority queue to store the states that need to be expanded
        Set<List<Cube>> explored = new HashSet<>(); // mark states that are already explored
        Map<State,State> cameFrom = new HashMap<>();

        destination = table.getFinalStateCubeList();

        queue.add(startState); // Initialize the priority queue with the starting state

        // Loop until the priority queue is empty or the goal state is reached
        while (!queue.isEmpty()) {
            // Remove the state with the lowest cost from the priority queue
            State currentState = queue.poll();


            // Check if the goal state is reached
            if (isGoalState(currentState.getCubes())) {
                setCountExtensions(explored.size());
                return getPathToFinalState(currentState,cameFrom);
            }


            List<State> successorStates = generateSuccessorStates(currentState);


            for (State child : successorStates) {
                double currCost = child.getCost();
                if (!checkStateEquality(currentState, child)) {
                    child.setCost(currentState.getCost() + currCost);
                    child.setPreviousState(currentState);
                    cameFrom.put(child, currentState);
                    queue.add(child);
                    explored.add(currentState.getCubes());
                } else if (child.getCost() > currentState.getCost() + currCost) { //explored.contains(child.cubes) &&
                    child.setPreviousState(currentState);
                    child.setCost(currentState.getCost() + currCost);
                    queue.remove(child);
                    queue.add(child);
                    }
                }
            }
        // If the goal state is not found, return an empty list
        return  new ArrayList<>();
    }

    //HEURISTIC FUNCTION
    private double heuristicCost(State currentState){
        double approximateCost=0.0;
        for(Cube c: currentState.cubes){
            if(!isCubeInFinalState(c,currentState)){
                int destinationY = destination.get(c.getId()-1).getYCoordinate();
                approximateCost += getMoveCost(c,destinationY);
            }
        }
        return approximateCost;
    }

    // HELPER FUNCTIONS FOR A* & UCS
    private List<State> getPathToFinalState(State finalState,Map<State,State> cameFrom) {
        List<State> path = new ArrayList<>();
        State currentState = finalState;
        while (currentState != null) {
            path.add(currentState);
            currentState = cameFrom.get(currentState);
        }
        Collections.reverse(path);
        return path;
    }

    private boolean checkStateEquality(State state, State other){
        if (!(state.getCost() == other.getCost())) {return false;}
        else {
            for (int i = 0; i < cubes.size(); i++) {
                if (!(state.getCubes().get(i).equals(other.getCubes().get(i))))
                    return false;
            }
        }
        return true;
    }

    private boolean isGoalState(List<Cube> cubeList){
        for(Cube c: cubeList){
            if(!c.equals(destination.get(c.getId()-1))) return false;
        }
        return true;
    }

   /* private static  boolean containsKeyValue(Map<List<Cube>,Double> map, State child) {
        for (List<Cube> key: map.keySet()) {
            if () {
                return true;
            }
        }
        return false;
    } */












    //PRINTS
    public void printCubeArray(Cube[][] table){
        for(int j=table[0].length-1; j>-1 ; j--){
            for(int i=0; i<table.length;i++){
                System.out.print(table[i][j].getId() + " ");
            }
            System.out.println();
        }
        System.out.println("------------------------");
    }
    public void printCubeList(List<Cube> cubeList){
        System.out.println("------------------------");
        for(Cube c: cubeList){
            System.out.printf("Cube[%d]:(%d,%d)\t" ,c.getId(),c.getXCoordinate(),c.getYCoordinate());
        }
        System.out.println("\n------------------------");
    }

}
