 

import java.util.ArrayList;
import java.util.Arrays;

public class Cube {

    private int id, xCoordinate, yCoordinate;


    public Cube(int id, int xCoordinate, int yCoordinate) {
        this.id = id;
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
    }
    //copy constructor
    public Cube(Cube other) {
        this.id = other.id;
        this.xCoordinate = other.xCoordinate;
        this.yCoordinate = other.yCoordinate;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getXCoordinate() {
        return xCoordinate;
    }

    public int getYCoordinate() {
        return yCoordinate;
    }

    public ArrayList<Integer> getCoordinates(){
        ArrayList<Integer> coordinates = new ArrayList<>(2);
        coordinates.add(getXCoordinate());
        coordinates.add(getYCoordinate());
        return coordinates;
    }

    public void setCoordinates(int x,int y){
        this.xCoordinate = x;
        this.yCoordinate = y;
    }


    public boolean equals(Object obj){
        if(obj ==null) return false;
        if(!(obj instanceof Cube)) return false;
        Cube other = (Cube) obj;
        return this.yCoordinate == other.yCoordinate && this.xCoordinate == other.xCoordinate && this.id == other.getId();
    }

}

