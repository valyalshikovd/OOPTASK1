package ru.vsu.cs.oop.valyalschikov_d_a.unit_tests;

import org.junit.jupiter.api.Assertions;
import ru.vsu.cs.oop.valyalschikov_d_a.quadtree.Point;
import ru.vsu.cs.oop.valyalschikov_d_a.quadtree.QuadTree;

import java.util.Arrays;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.*;

class QuadTreeTest {
    QuadTree<String> testQuadTree = new QuadTree<>(0,0, 200, 200, 4);

    @org.junit.jupiter.api.Test
    void add() {
        testQuadTree.add(new Point<String>(10, 10, "num1", "точка 1"));
        assertEquals(1, testQuadTree.size());
        testQuadTree.add(new Point<String>(30, 20, "num2", "точка 2"));
        assertEquals(2, testQuadTree.size());
        testQuadTree.add(new Point<String>(30, 30, "num3", "точка 3"));
        assertEquals(3, testQuadTree.size());
        testQuadTree.add(new Point<String>(40, 10, "num4", "точка 4"));
        assertEquals(4, testQuadTree.size());
        testQuadTree.add(new Point<String>(90, 90, "num5", "точка 5"));
        assertEquals(5, testQuadTree.size());
        Stack<int[]> stack = testQuadTree.getZones();
        for (int[] zone : stack){
            System.out.println(Arrays.toString(zone));
        }
        int counter = 0;
        while (!stack.isEmpty()){
            stack.pop();
            counter++;
        }
        assertEquals(9, counter);
        testQuadTree.add(new Point<String>(160, 160, "num6", "точка 6"));
        testQuadTree.add(new Point<String>(170, 170, "num7", "точка 7"));
        testQuadTree.add(new Point<String>(180, 180, "num8", "точка 8"));
        testQuadTree.add(new Point<String>(190, 190, "num9", "точка 9"));
        testQuadTree.add(new Point<String>(160, 170, "num10", "точка 10"));
        stack = testQuadTree.getZones();
        counter = 0;
        while (!stack.isEmpty()){
            stack.pop();
            counter++;
        }
        assertEquals(17, counter);
        assertEquals(10, testQuadTree.size());
    }

    @org.junit.jupiter.api.Test
    void getZones() {
        testQuadTree.add(new Point<String>(10, 10, "num1", "точка 1"));
        testQuadTree.add(new Point<String>(30, 20, "num2", "точка 2"));
        testQuadTree.add(new Point<String>(30, 30, "num3", "точка 3"));
        testQuadTree.add(new Point<String>(40, 10, "num4", "точка 4"));
        testQuadTree.add(new Point<String>(90, 90, "num5", "точка 5"));
        Stack<int[]> stack = testQuadTree.getZones();
        int counter = 0;
        while (!stack.isEmpty()){
            stack.pop();
            counter++;
        }
        assertEquals(9, counter);
    }

    @org.junit.jupiter.api.Test
    void remove() {
        testQuadTree.add(new Point<String>(10, 10, "num1", "точка 1"));
        testQuadTree.add(new Point<String>(30, 20, "num2", "точка 2"));
        testQuadTree.add(new Point<String>(30, 30, "num3", "точка 3"));
        testQuadTree.add(new Point<String>(40, 10, "num4", "точка 4"));
        testQuadTree.add(new Point<String>(90, 90, "num5", "точка 5"));
        testQuadTree.add(new Point<String>(95, 95, "num6", "точка 6"));
        testQuadTree.remove(new Point<>(95, 95, "num6", "точка 6"));
        assertEquals(5, testQuadTree.size());

        testQuadTree.remove(new Point<String>(90, 90, "num5", "точка 5"));

        assertEquals(4, testQuadTree.size());
        testQuadTree.remove(new Point<String>(40, 10, "num4", "точка 4"));

        assertEquals(3, testQuadTree.size());
        Stack<int[]> stack = testQuadTree.getZones();
        int counter = 0;
        while (!stack.isEmpty()){
            stack.pop();
            counter++;
        }
        assertEquals(5, counter);
    }
    @org.junit.jupiter.api.Test
    void getByPointCoords() {
        testQuadTree.add(new Point<String>(10, 10, "num1", "точка 1"));
        testQuadTree.add(new Point<String>(30, 20, "num2", "точка 2"));
        testQuadTree.add(new Point<String>(30, 30, "num3", "точка 3"));
        testQuadTree.add(new Point<String>(40, 10, "num4", "точка 4"));
        testQuadTree.add(new Point<String>(90, 90, "num5", "точка 5"));
        testQuadTree.add(new Point<String>(95, 95, "num6", "точка 6"));
        assertEquals(testQuadTree.getByPointCoords(40, 10),"точка 4" );
    }
}