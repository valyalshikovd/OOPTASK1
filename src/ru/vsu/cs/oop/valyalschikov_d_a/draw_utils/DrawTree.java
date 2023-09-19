package ru.vsu.cs.oop.valyalschikov_d_a.draw_utils;

import ru.vsu.cs.oop.valyalschikov_d_a.quadtree.Point;
import ru.vsu.cs.oop.valyalschikov_d_a.quadtree.QuadTree;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.Stack;

public class DrawTree {
    public void drawTree(QuadTree<?> tree, Graphics2D g){
        Stack<int[]> stackZone = tree.getZones();
        for(int[] zone : stackZone){
            if(zone.length != 4){
                continue;
            }
            Rectangle rectangle = new Rectangle(zone[0], zone[1], zone[2], zone[3]);
            g.draw(rectangle);
        }
        g.setColor(Color.red);
        for(Point pt : tree){
            Ellipse2D ellipse2D = new Ellipse2D.Double(pt.getX()-1, pt.getY()-1, 3, 3);
            g.draw(ellipse2D);
        }
        g.setColor(Color.black);
    }
}
