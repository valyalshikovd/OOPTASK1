package ru.vsu.cs.oop.valyalschikov_d_a;

import java.awt.*;

public class QuadTree {
    private QuadTreeNode root;
    private int maxCounterObject;
    private Zone zone;
    public QuadTreeNode getRoot() {
        return root;
    }

    public void setRoot(QuadTreeNode root, int maxCounterObject) {
        this.root = root;
        this.maxCounterObject = maxCounterObject;
    }

    public QuadTree(int x, int y, int height, int width, int maxCounterObject) {
        root = null;
        this.maxCounterObject = maxCounterObject;
        this.zone = new Zone(x,y,height,width);
    }
    public void add(Value value){
        addEl(value);
    }
    public void add(int[] value){
        if(value.length != 2){
            return;
        }
        addEl(new Value(value[0], value[1], "null"));
    }
    private void addEl(Value value){
        if(root == null){
            root = new QuadTreeNode( maxCounterObject, null, zone, "0");
            root.addValue(value);
            return;
        }
        root.addValue(value);
    }
    public void consoleWrite(){
        if(root == null){
            System.out.println("ru.vsu.cs.oop.valyalschikov_d_a.QuadTree is a void");
        }
        root.write();
    }
    public void draw(Graphics2D g){
        if(root != null){
            root.draw(g);
        }
    }
    public void remove(Value value){
        root.remove(value);
    }
    public QuadTreeNode find(String desc){
        String[] ar = desc.split("_");
        QuadTreeNode curr = root;
        for (String s : ar) {
            switch (s) {
                case "nw": curr = curr.getNW();
                case "ne": curr = curr.getNE();
                case "se": curr = curr.getSE();
                case "sw": curr = curr.getSW();
                    break;
            }
        }
        return curr;
    }
}
