package org.example;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class State {
    private List<Cube> cubes;
    private int cost;
    private State previousState;

    public State(List<Cube> cubes, int cost, State previousState) {
        this.cubes = cubes;
        this.cost = cost;
        this.previousState = previousState;
    }

    public List<Cube> getCubes() {
        return cubes;
    }

    public void setCubes(List<Cube> cubes) {
        this.cubes = cubes;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public State getPreviousState() {
        return previousState;
    }

    public void setPreviousState(State previousState) {
        this.previousState = previousState;
    }

    public List<State> ucs(Table table) {
        // Define a comparator to order states based on their total cost
        Comparator<State> comparator = Comparator.comparing(State::getCost);

        // Create a priority queue to store the states that need to be expanded
        PriorityQueue<State> queue = new PriorityQueue<>(comparator);

        // Initialize the priority queue with the starting state
        State startState = new State(table.cubes, 0, null);
        queue.add(startState);

        // Loop until the priority queue is empty or the goal state is reached
        while (!queue.isEmpty()) {
            // Remove the state with the lowest cost from the priority queue
            State currentState = queue.poll();

            // Check if the goal state is reached
            if (isGoalState(currentState)) {
                // Return the path to the goal state
                return getPathToGoalState(currentState);
            }

            // Generate all possible successor states
            List<State> successorStates = generateSuccessorStates(currentState);

            // Calculate the total cost of each successor state and add it to the priority queue
            for (State successorState : successorStates) {
                int totalCost = currentState.getCost() + successorState.getMoveCost();
                successorState.setCost(totalCost);
                successorState.setPreviousState(currentState);
                queue.add(successorState);
            }
        }

        // If the goal state is not found, return an empty list
        return new ArrayList<>();
    }

    private int getMoveCost() {
        Movement move = new Movement();
    }

    private boolean isGoalState(State currentState) {
        boolean isGoal = true;
        List<Cube> sortedCubes = new ArrayList<>();
        sortCubeList(sortedCubes);
        for (Cube c: currentState.cubes) {
            for (Cube cs: sortedCubes) {
                if (c.getId() != cs.getId()) {
                    isGoal = false;
                    break;
                }
            }
        }
        return isGoal;
    }


    public void sortCubeList(List<Cube> cubes) {
        Comparator<Cube> compareByYCoordinate = Comparator.comparing(Cube::getyCoordinate);
        Comparator<Cube> compareByXCoordinate = Comparator.comparing(Cube::getxCoordinate);
        Comparator<Cube> compareByBothCoordinates = compareByYCoordinate.thenComparing(compareByXCoordinate);

        cubes.sort(compareByBothCoordinates);
    }
}
