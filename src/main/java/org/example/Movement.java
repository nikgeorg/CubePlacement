package org.example;

public class Movement {
    private int moveCost;

    public int moveCubeUp(Cube cube, int newYCoordinate) {
        int oldYCoordinate = cube.getyCoordinate();
        cube.setyCoordinate(newYCoordinate);
        return newYCoordinate - oldYCoordinate;
    }
    public double moveCubeDown(Cube cube, int newYCoordinate) {
        int oldYCoordinate = cube.getyCoordinate();
        cube.setyCoordinate(newYCoordinate);
        return 0.5 * (oldYCoordinate - newYCoordinate);
    }

    public double moveCubeInSameRow(Cube cube, int newXCoordinate) {
        cube.setxCoordinate(newXCoordinate);
        return 0.75;
    }

    public double moveCube(Cube cube, int newXCoordinate, int newYCoordinate) {
        int totalMoveCost = 0;
        if (cube.getxCoordinate() == newXCoordinate && cube.getyCoordinate() < newYCoordinate) {
            totalMoveCost += moveCubeUp(cube, newYCoordinate);
        }
        else if (cube.getxCoordinate() == newXCoordinate && cube.getyCoordinate() > newYCoordinate) {
            totalMoveCost += moveCubeDown(cube, newYCoordinate);
        }
        else if (cube.getxCoordinate() != newXCoordinate && cube.getyCoordinate() > newYCoordinate) {
            totalMoveCost += moveCubeInSameRow(cube, newXCoordinate);
            totalMoveCost += moveCubeDown(cube, newYCoordinate);
        }
        else if (cube.getxCoordinate() != newXCoordinate && cube.getyCoordinate() < newYCoordinate) {
            totalMoveCost += moveCubeInSameRow(cube, newXCoordinate);
            totalMoveCost += moveCubeUp(cube, newYCoordinate);
        }
        else if (cube.getxCoordinate() != newXCoordinate && cube.getyCoordinate() == newYCoordinate) {
            totalMoveCost += moveCubeInSameRow(cube, newXCoordinate);
        }
        else if (cube.getxCoordinate() == newXCoordinate && cube.getyCoordinate() == newYCoordinate) {
            return 0;
        }
        return totalMoveCost;
    }
}
