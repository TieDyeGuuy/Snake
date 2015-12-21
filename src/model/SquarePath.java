/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;

/**
 *
 * @author Ben Polson
 */
public class SquarePath {
    private int[][][] field;
    private int xMax;
    private int xMin;
    private int yMax;
    private int yMin;
    private int currX;
    private int currY;
    private int currCount;
    
    public SquarePath() {
        xMax = 0;
        xMin = 0;
        yMax = 0;
        yMin = 0;
        field = new int[4][10][10];
        for (int[][] quad : field) {
            for (int[] x : quad) {
                for (int y = 0; y < x.length; y++) {
                    x[y] = -2;
                }
            }
        }
        field[0][0][0] = 0;
        currX = 0;
        currY = 0;
        currCount = 0;
    }
    public SquarePath(SquarePath copy) {
        this.xMax = copy.xMax;
        this.xMin = copy.xMin;
        this.yMax = copy.yMax;
        this.yMin = copy.yMin;
        this.field = SquarePath.plantField(copy.field, copy.getBuff());
        this.currX = copy.currX;
        this.currY = copy.currY;
        this.currCount = copy.currCount;
    }
    
    /* if a coordinate is (-1, 5) each negative is treated as a 1, and the
       quadrant is interpreted in binary. In other words, any of the following
       coordinates:
       (0, 0)
       (4, 0)
       (0, 6)
       (2, 3)
       would be in quadrant 0.
       Coordinates of the form:
       (3, -7)
       (0, -2)
       would be in quadrant 1.
       Coordinates:
       (-4, 9)
       (-1, 0)
       are in quadrant 2 and that leaves anything of the form
       (-2, -6) in quadrant 3.*/
    public int getValue(int x, int y) {
        return field[SquarePath.getQuad(x, y)][Math.abs(x)][Math.abs(y)];
    }
    private void setValue(int x, int y, int value) {
        field[SquarePath.getQuad(x, y)][Math.abs(x)][Math.abs(y)] = value;
    }
    private static int getQuad(int x, int y) {
        return ((2 * x + 1) / Math.abs(2 * x + 1) - 1) / (-1) + 
                ((2 * y + 1) / Math.abs(2 * y + 1) - 1) / (-2);
    }
    private int getBuff() {
        return field[0].length;
    }
    public void pushNorth() {
        yMax++;
        if(yMax * 4 / (this.getBuff() * 3) >= 1) {
            field = plantField(field, this.getBuff() * 2);
        }
    }
    public void pushEast() {
        xMax++;
        if(xMax * 4 / (this.getBuff() * 3) >= 1) {
            field = plantField(field, this.getBuff() * 2);
        }
    }
    public void pushSouth() {
        yMin--;
        if(yMin * (-4) / (this.getBuff() * 3) >= 1) {
            field = plantField(field, this.getBuff() * 2);
        }
    }
    public void pushWest() {
        xMin--;
        if(xMin * (-4) / (this.getBuff() * 3) >= 1) {
            field = plantField(field, this.getBuff() * 2);
        }
    }
    public int moveNorth() {
        if (currY < yMax && this.getValue(currX, currY + 1) < 0) {
            currY++;
            currCount++;
            this.setValue(currX, currY, currCount);
            return currCount;
        } else {
            return -1;
        }
    }
    public int moveEast() {
        if (currX < xMax && this.getValue(currX + 1, currY) < 0) {
            currX++;
            currCount++;
            this.setValue(currX, currY, currCount);
            return currCount;
        } else {
            return -1;
        }
    }
    public int moveSouth() {
        if (currY > yMin && this.getValue(currX, currY - 1) < 0) {
            currY--;
            currCount++;
            this.setValue(currX, currY, currCount);
            return currCount;
        } else {
            return -1;
        }
    }
    public int moveWest() {
        if (currX > xMin && this.getValue(currX - 1, currY) < 0) {
            currX--;
            currCount++;
            this.setValue(currX, currY, currCount);
            return currCount;
        } else {
            return -1;
        }
    }
    /* does not catch nonfields and if newBuff is smaller than the old
       buffer.*/
    private static int[][][] plantField(int[][][] smallField, int newBuff) {
        int[][][] bigField = new int[4][newBuff][newBuff];
        for (int quad = 0; quad < smallField.length; quad++) {
            for (int x = 0; x < newBuff; x++) {
                for (int y = 0; y < newBuff; y++) {
                    if (x < smallField[quad].length && 
                            y < smallField[quad].length) {
                        bigField[quad][x][y] = smallField[quad][x][y];
                    } else {
                        bigField[quad][x][y] = -2;
                    }
                }
            }
        }
        return null;
    }
}
