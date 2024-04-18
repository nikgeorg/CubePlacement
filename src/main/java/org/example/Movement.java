 

public class Movement {


    public double moveCubeUp(Cube cube, int newYCoordinate) {
        int oldYCoordinate = cube.getYCoordinate();
        return newYCoordinate - oldYCoordinate;
    }

    public double moveCubeDown(Cube cube, int newYCoordinate) {
        int oldYCoordinate = cube.getYCoordinate();
        return 0.5 * (oldYCoordinate - newYCoordinate);
    }

    public double moveCubeInSameRow() {
        return 0.75;
    }

    public double moveCube(Cube cube, int newYCoordinate) {
        double totalMoveCost = 0;
        if (cube.getYCoordinate() < newYCoordinate) {
            totalMoveCost += moveCubeUp(cube, newYCoordinate);
        }
        else if (cube.getYCoordinate() > newYCoordinate) {
            totalMoveCost += moveCubeDown(cube, newYCoordinate);
        }
        else if (cube.getYCoordinate() == newYCoordinate){
            totalMoveCost+=moveCubeInSameRow();
        }
        return totalMoveCost;
    }


}
