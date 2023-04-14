package org.example;

public class Movement {
    private int moveCost;

    public int moveCubeUp(Cube cube) {
        int oldYCoordinate = cube.getyCoordinate();
        cube.setyCoordinate(cube.getyCoordinate()+1);
        int newYCoordinate = cube.getyCoordinate();
        return newYCoordinate - oldYCoordinate;
    }
    public double moveCubeDown(Cube cube) {
        int oldYCoordinate = cube.getyCoordinate();
        cube.setyCoordinate(cube.getyCoordinate()+1);
        int newYCoordinate = cube.getyCoordinate();
        return 0.5 * (oldYCoordinate - newYCoordinate);
    }

    public double moveCubeInSameRow(Cube cube) {
        int oldXCoordinate = cube.getxCoordinate();
        cube.setxCoordinate(cube.getxCoordinate()+1);
        int newXCoordinate = cube.getxCoordinate();
        return 0.75;
    }

}
