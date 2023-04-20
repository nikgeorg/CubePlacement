package org.example;

import java.util.ArrayList;

public class Table {
    private int numRows, numColumns;

    public int getNumRows() {
        return numRows;
    }

    public void setNumRows(int numRows) {
        this.numRows = numRows;
    }

    public int getNumColumns() {
        return numColumns;
    }

    public void setNumColumns(int numColumns) {
        this.numColumns = numColumns;
    }
    Cube[][] tableArr = new Cube[numRows][numColumns];
    ArrayList<Cube> cubes = new ArrayList<>();

    public boolean getCubeStatus(Cube cube) {
        return cube.isFree();
    }
    // Insert the coordinates of the desired cube here
    public boolean isCubeFree(int row, int column) {
        if (row == 1 || row == 0) {
            // A cube exists above the current cube, so it is not free
            return tableArr[row + 1][column].getId() == 0;
        }
        // If we reach here, there are no cubes above the current cube, so it is free
        return true;
    }

    public boolean isCubeOnAnotherCube(int row, int column) {
            if (row == 1 || row == 2) {
                return tableArr[row - 1][column].getId() != 0;
            }
            return false;
    }
    public boolean isCubeOnTable(Cube cube) {
        return cube.getxCoordinate() == 1;
    }

    public void setCubeStatus(Cube cube) {
        cube.setFree(isCubeFree(cube.getxCoordinate(), cube.getyCoordinate()));
    }

    public boolean isValidCondition() {
        for (Cube c: cubes) {
            if (!(isCubeOnTable(c) || isCubeOnAnotherCube(c.getxCoordinate() -1, c.getyCoordinate() -1))) return false;
            // if cube is on 1st row, its x must be between 1 and L, where L = number of table columns
            else if ((c.getyCoordinate() == 1 && c.getxCoordinate() < 1) || c.getyCoordinate() == 1 && c.getxCoordinate() > getNumColumns()) {
                return false;
            }
            // if cube is on 2nd or 3rd row, its y must be between 1 and K, and the table has L positions available for x, L is 4 * K, cubes are 3 * K,
            // eventually cube must be between 1 and 3/4*numColumns
            else if ((c.getyCoordinate() == 2 || c.getyCoordinate() == 3) && c.getxCoordinate() < 1 || c.getxCoordinate() > 0.75 * getNumColumns()) {
                return false;
            }
        }
        return true;
    }

    public void initializeCubeArr() {
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numColumns; col++) {
                for (Cube c: cubes) {
                    tableArr[row][col] = c;
                }
            }
        }
    }

}
