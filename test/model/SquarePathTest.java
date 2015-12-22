/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.Ignore;
import static org.junit.Assert.*;

/**
 *
 * @author Ben Polson
 */
public class SquarePathTest {
    private SquarePath field;
    private int xMax;
    private int xMin;
    private int yMax;
    private int yMin;
    private int width;
    private int height;
    private int currX;
    private int currY;
    private int currCount;
    private int buff;
    
    public SquarePathTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        field = new SquarePath();
        xMax = 0;
        xMin = 0;
        yMax = 0;
        yMin = 0;
        width = 1;
        height = 1;
        currX = 0;
        currY = 0;
        currCount = 0;
        buff = 10;
    }
    
    @After
    public void tearDown() {
        field = null;
    }

    /**
     * Test of getValue method, of class SquarePath.
     */
    @Ignore
    public void testGetValue() {
        System.out.println("getValue");
        int x = 0;
        int y = 0;
        SquarePath instance = new SquarePath();
        int expResult = 0;
        int result = instance.getValue(x, y);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testConstructor() {
        System.out.println("Simple Stuff");
        this.checkState();
    }
    
    @Test
    public void testSimplePush() {
        System.out.println("Push North");
        field.pushNorth();
        xMax = 0;
        xMin = 0;
        yMax = 1;
        yMin = 0;
        width = 1;
        height = 2;
        currX = 0;
        currY = 0;
        currCount = 0;
        buff = 10;
        this.checkState();
        System.out.println("Push East");
        field.pushEast();
        field.pushEast();
        xMax = 2;
        xMin = 0;
        yMax = 1;
        yMin = 0;
        width = 3;
        height = 2;
        currX = 0;
        currY = 0;
        currCount = 0;
        buff = 10;
        this.checkState();
        System.out.println("Push South-West");
        field.pushWest();
        field.pushSouth();
        field.pushWest();
        field.pushSouth();
        field.pushWest();
        field.pushSouth();
        field.pushWest();
        xMax = 2;
        xMin = -4;
        yMax = 1;
        yMin = -3;
        width = 7;
        height = 5;
        currX = 0;
        currY = 0;
        currCount = 0;
        buff = 10;
        this.checkState();
        System.out.println("Check status");
        assertEquals(field.getStatus(0, 0) & SquarePath.INSIDE, 
                SquarePath.INSIDE);
        assertEquals(field.getStatus(1, 1) & SquarePath.INSIDE,
                SquarePath.INSIDE);
        assertEquals(field.getStatus(2, 2) & SquarePath.OUT_BOUNDS,
                SquarePath.OUT_BOUNDS);
        assertEquals(field.getStatus(10, 10) & SquarePath.OUT_BUFF,
                SquarePath.OUT_BUFF);
        assertEquals(field.getValue(1, 1), (SquarePath.OUT_PATH) * -1);
        assertEquals(field.getValue(2, 2), 
                (SquarePath.OUT_PATH | SquarePath.OUT_BOUNDS) * -1);
        assertEquals(field.getValue(10, 10), 
                (SquarePath.OUT_PATH | 
                        SquarePath.OUT_BOUNDS | 
                        SquarePath.OUT_BUFF) * -1);
    }
    
    @Test
    public void testNewBuff() {
        field.pushNorth();
        field.pushNorth();
        field.pushNorth();
        field.pushNorth();
        field.pushNorth();
        field.pushNorth();
        field.pushNorth();
        xMax = 0;
        xMin = 0;
        yMax = 7;
        yMin = 0;
        width = 1;
        height = 8;
        currX = 0;
        currY = 0;
        currCount = 0;
        buff = 10;
        this.checkState();
        field.pushNorth();
        xMax = 0;
        xMin = 0;
        yMax = 8;
        yMin = 0;
        width = 1;
        height = 9;
        currX = 0;
        currY = 0;
        currCount = 0;
        buff = 20;
        this.checkState();
    }
    
    @Test
    public void testMove() {
        field.pushNorth();
        field.pushEast();
        field.pushEast();
        field.pushSouth();
        field.pushSouth();
        field.pushSouth();
        field.pushWest();
        field.pushWest();
        field.pushWest();
        field.pushWest();
        int move = field.moveNorth();
        xMax = 2;
        xMin = -4;
        yMax = 1;
        yMin = -3;
        width = 7;
        height = 5;
        currX = 0;
        currY = 1;
        currCount = 1;
        buff = 10;
        this.checkState();
        assertEquals(move, currCount);
        move = field.moveNorth();
        this.checkState();
        assertEquals(move, -1);
        field.moveEast();
        move = field.moveSouth();
        currX = 1;
        currY = 0;
        currCount = 3;
        this.checkState();
        assertEquals(move, currCount);
        move = field.moveWest();
        this.checkState();
        assertEquals(move, -1);
        field.moveSouth();
        field.moveWest();
        move = field.moveWest();
        currX = -1;
        currY = -1;
        currCount = 6;
        this.checkState();
        assertEquals(move, currCount);
    }
    
    @Test
    public void testGetQuad() {
        System.out.println("getQuad");
        assertEquals(SquarePath.getQuad(0, 0), 0);
        assertEquals(SquarePath.getQuad(0, 3), 0);
        assertEquals(SquarePath.getQuad(7, 0), 0);
        assertEquals(SquarePath.getQuad(2, 2), 0);
        assertEquals(SquarePath.getQuad(0, -2), 1);
        assertEquals(SquarePath.getQuad(5, -1), 1);
        assertEquals(SquarePath.getQuad(-6, 0), 2);
        assertEquals(SquarePath.getQuad(-3, 3), 2);
        assertEquals(SquarePath.getQuad(-1, -12), 3);
    }
    
    private void checkState() {
        assertEquals(field.getXMax(), xMax);
        assertEquals(field.getXMin(), xMin);
        assertEquals(field.getYMax(), yMax);
        assertEquals(field.getYMin(), yMin);
        assertEquals(field.getWidth(), width);
        assertEquals(field.getHeight(), height);
        assertEquals(field.getCurrX(), currX);
        assertEquals(field.getCurrY(), currY);
        assertEquals(field.getCurrCount(), currCount);
        assertEquals(field.getValue(field.getCurrX(), field.getCurrY()), 
                field.getCurrCount());
        assertEquals(field.getBuff(), buff);
    }
}
